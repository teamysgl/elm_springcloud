<template>
  <div class="wrapper">
    <!-- header部分 -->
    <header>
      <i class="fa fa-angle-left" @click="back"></i>
      <p>商家信息</p>
    </header>

    <!-- 商家logo部分 -->
    <div class="business-logo">
      <img :src="business.businessImg"/>
    </div>

    <!-- 商家信息部分 -->
    <div class="business-info">
      <h1>{{ business.businessName }}</h1>
      <p>&#165;{{ business.starPrice }}起送 &#165;{{ business.deliveryPrice }}配送</p>
      <p>{{ business.businessExplain }}</p>
    </div>
    <!-- 添加收藏按钮 -->
    <div v-if="this.isCollected" @click="toggleCollect(business)" class="collect-button0">
      取消收藏
    </div>
    <div v-if="!this.isCollected" @click="toggleCollect(business)" class="collect-button1">
      收藏
    </div>

    <!-- 食品列表部分 -->
    <ul class="food">
      <li v-for="(item,index) in foodArr">
        <div class="food-left">
          <img :src="item.foodImg">
          <div class="food-left-info">
            <h3>{{ item.foodName }}</h3>
            <p>{{ item.foodExplain }}</p>
            <p>&#165;{{ item.foodPrice }}</p>
          </div>
        </div>
        <div class="food-right">
          <div>
            <i class="fa fa-minus-circle" @click="minus(index)" v-show="item.quantity!=0"></i>
          </div>
          <p><span v-show="item.quantity!=0">{{ item.quantity }}</span></p>
          <div>
            <i class="fa fa-plus-circle" @click="add(index)"></i>
          </div>
        </div>
      </li>
    </ul>


    <!-- 购物车部分 -->
    <div class="cart">
      <div class="cart-left">
        <div class="cart-left-icon"
             :style="totalQuantity==0?'background-color:#505051;':'background-color:#01B0F2;'">
          <i class="fa fa-shopping-cart"></i>
          <div class="cart-left-icon-quantity" v-show="totalQuantity!=0">
            {{ totalQuantity }}
          </div>
        </div>
        <div class="cart-left-info">
          <p>&#165;{{ totalPrice }}</p>
          <p>另需配送费{{ business.deliveryPrice }}元</p>
        </div>
      </div>
      <div class="cart-right">
        <!-- 不够起送费 -->
        <div class="cart-right-item" v-show="totalSettle<business.starPrice"
             style="background-color: #444;cursor: default;">
          &#165;{{ business.starPrice }}起送
        </div>
        <!-- 达到起送费 -->
        <div class="cart-right-item" @click="toOrder" v-show="totalSettle>=business.starPrice">
          去结算
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BusinessInfo',
  data() {
    return {
      businessId: this.$route.query.businessId,
      business: {},
      foodArr: [],
      user: {},
      longPress: false,
      pressTimer: null,
      interval: 200,
      clickTimeouts: [], // 用于存储每个按钮的定时器
      isCollected: false
    }
  },
  created() {
    this.user = this.$getSessionStorage('user');
    //根据businessId查询商家信息
    let url = `BusinessController/getBusinessById/${this.businessId}`
    this.$axios.get(url).then(response => {
      this.business = response.data.result;
      this.foodArr = this.business.foodList;
      for (let i = 0; i < this.foodArr.length; i++) {
        this.foodArr[i].quantity = 0;
      }
      //如果已登录，那么需要去查询购物车中是否已经选购了某个食品
      if (this.user != null) {
        this.listCart();
      }
    }).catch(error => {
      console.error(error);
    });
    this.listCollect()
  },
  methods: {
    back() {
      this.$router.go(-1);
    },
    async listCollect() {
      let url = `CollectController/listCollect/${this.user.userId}`
      await this.$axios.get(url).then(response => {
        this.collectList = response.data.result;
      }).catch(error => {
        console.error(error);
      });
      for (let i of this.collectList) {
        if (`${i.businessId}` === this.businessId) {
          this.isCollected = true
          break
        }
      }
    },
    listCart() {
      let url = `CartController/listCart/${this.user.userId}/${this.businessId}`
      this.$axios.get(url).then(response => {
        let cartArr = response.data.result;
        //遍历所有食品列表
        for (let foodItem of this.foodArr) {
          foodItem.quantity = 0;
          for (let cartItem of cartArr) {
            if (cartItem.foodId == foodItem.foodId) {
              foodItem.quantity = cartItem.quantity;
            }
          }
        }
        this.foodArr.sort();
      }).catch(error => {
        console.error(error);
      });
    },
    add(index) {
      //首先做登录验证
      if (this.user == null) {
        this.$router.push({
          path: '/login'
        });
        return;
      }

      if (this.foodArr[index].quantity == 0) {
        //做insert
        this.savaCart(index);
      } else {
        //做update
        this.updateCart(index, 1);
      }
    },
    minus(index) {
      //首先做登录验证
      if (this.user == null) {
        this.$router.push({
          path: '/login'
        });
        return;
      }
      if (this.foodArr[index].quantity > 1) {
        //做update
        this.updateCart(index, -1);
      } else {
        //做delete
        this.removeCart(index);
      }
    },
    clearTimers() {
      clearTimeout(this.pressTimer);
      this.longPress = false;
      this.interval = 200;
    },
    savaCart(index) {
      let url = `CartController/saveCart/${this.user.userId}/${this.businessId}/${this.foodArr[index].foodId}`
      this.$axios.post(url).then(response => {
        if (response.data.result == 1) {
          //此食品数量要更新为1；
          this.foodArr[index].quantity = 1;
          this.foodArr.sort();
        } else {
          alert('向购物车中添加食品失败！');
        }
      }).catch(error => {
        console.error(error);
      });
    },
    updateCart(index, num) {
      let url = `CartController/updateCart/${this.user.userId}/${this.businessId}/${this.foodArr[index].foodId}/${this.foodArr[index].quantity + num}`
      this.$axios.put(url).then(response => {
        if (response.data.result == 1) {
          //此食品数量要更新为1或-1；
          this.foodArr[index].quantity += num;
          this.foodArr.sort();
        } else {
          alert('向购物车中更新食品失败！');
        }
      }).catch(error => {
        console.error(error);
      });
    },
    removeCart(index) {
      let url = `CartController/removeCart/${this.user.userId}/${this.businessId}/${this.foodArr[index].foodId}`
      this.$axios.delete(url).then(response => {
        if (response.data.result == 1) {
          //此食品数量要更新为0；视图的减号和数量要消失
          this.foodArr[index].quantity = 0;
          this.foodArr.sort();
        } else {
          alert('从购物车中删除食品失败！');
        }
      }).catch(error => {
        console.error(error);
      });
    },
    toOrder() {
      this.$router.push({
        path: '/orders',
        query: {
          businessId: this.business.businessId
        }
      });
    },
    toggleCollect(business) {
      this.isCollected = !this.isCollected; // 切换收藏状态
      let method = this.isCollected ? 'post' : 'delete';
      let url = `CollectController/${method === 'post' ? 'add' : 'delete'}Collect/${this.user.userId}/${this.businessId}`;
      this.$axios({
        method: method,
        url: url
      }).then().catch(error => {
        console.error(error);
      });
    }
  },
  computed: {
    //食品总价格
    totalPrice() {
      let total = 0;
      for (let item of this.foodArr) {
        total += item.foodPrice * item.quantity;
      }
      return total;
    },
    //食品总数量
    totalQuantity() {
      let quantity = 0;
      for (let item of this.foodArr) {
        quantity += item.quantity;
      }
      return quantity;
    },
    //结算总价格
    totalSettle() {
      return this.totalPrice + this.business.deliveryPrice;
    }
  }
}
</script>

<style scoped>
/****************总容器***************/
.wrapper {
  width: 100%;
  height: 100%;
}

/****************header部分***************/
.wrapper header {
  width: 100%;
  height: 14vw;
  /* 增大header高度 */
  background-color: #01B0F2;
  font-size: 5vw;
  /* 调整字体大小 */
  color: #fff;

  z-index: 10;

  display: flex;
  justify-content: flex-start;
  align-items: center;

  position: fixed;
  left: 0;
  top: 0;
}

.wrapper header i {
  margin: 0vw 0vw 0vw 4vw;
  /* 调整返回按钮的间距 */
  font-size: 7vw;
  /* 调整返回按钮的大小 */
  user-select: none;
  cursor: pointer;
}

.wrapper header p {
  margin: 0vw 0vw 0vw 35vw;
  /* 调整标题的间距 */
}

/****************商家logo部分***************/
.wrapper .business-logo {
  width: 100%;
  height: 40vw;
  /* 增大商家logo部分高度 */
  padding-top: 14vw;
  /* 调整商家logo上方间距 */

  display: flex;
  justify-content: center;
  align-items: center;
}

.wrapper .business-logo img {
  width: 45vw;
  /* 增大商家logo图片尺寸 */
  height: 35vw;
  /* 调整商家logo图片尺寸 */
  border-radius: 2.5vw;
  /* 调整边框圆角 */
}

/****************商家信息部分***************/
.wrapper .business-info {
  width: 100%;
  height: 25vw;
  /* 增大商家信息部分高度 */

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.wrapper .business-info h1 {
  font-size: 6vw;
  /* 调整商家名称字体大小 */

}

.wrapper .business-info p {
  font-size: 4vw;
  /* 调整商家信息字体大小 */
  color: #666;
  margin-top: 1.5vw;
  /* 调整商家信息顶部间距 */
}

/****************食品信息部分***************/
.wrapper .food {
  width: 100%;
  padding-bottom: 16vw;
  /* 增大底部填充 */
}

.wrapper .food li {
  width: 100%;
  box-sizing: border-box;
  padding: 3vw;
  /* 调整内边距 */
  user-select: none;

  display: flex;
  justify-content: space-between;
  align-items: center;

  transition: all 0.3s ease;
  /* 添加过渡效果 */
}

.wrapper .food li:hover {
  background-color: #f4f4f4;
  /* 鼠标悬停时背景色变化 */
}

.wrapper .food li .food-left {
  display: flex;
  align-items: center;
}

.wrapper .food li .food-left img {
  width: 22vw;
  /* 增大食品图片尺寸 */
  height: 22vw;
  /* 增大食品图片尺寸 */
  border-radius: 1.5vw;
  /* 调整边框圆角 */
  transition: all 0.3s ease;
  /* 添加过渡效果 */
}

.wrapper .food li .food-left img:hover {
  transform: scale(1.05);
  /* 鼠标悬停时放大图片 */
}

.wrapper .food li .food-left .food-left-info {
  margin-left: 4vw;
  /* 调整左侧信息的间距 */

}

.wrapper .food li .food-left .food-left-info h3 {
  font-size: 4vw;
  /* 调整标题字体大小 */
  color: #555;
}

.wrapper .food li .food-left .food-left-info p {
  font-size: 3.2vw;
  /* 调整文本字体大小 */
  color: #888;
  margin-top: 2.5vw;
  /* 调整顶部间距 */
}

.wrapper .food li .food-right {
  width: 18vw;
  /* 增大右侧部分宽度 */
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.wrapper .food li .food-right .fa-minus-circle,
.wrapper .food li .food-right .fa-plus-circle {
  font-size: 6vw;
  /* 调整按钮图标大小 */
  color: #999;
  cursor: pointer;
  transition: all 0.3s ease;
  /* 添加过渡效果 */
}

.wrapper .food li .food-right .fa-minus-circle:hover,
.wrapper .food li .food-right .fa-plus-circle:hover {
  color: #01B0F2;
  /* 鼠标悬停时按钮颜色变化 */
}

.wrapper .food li .food-right p {
  font-size: 4vw;
  /* 调整数量文本字体大小 */
  color: #333;
}

/****************** 购物车部分 ******************/
.wrapper .cart {
  width: 100%;
  height: 14vw;
  position: fixed;
  left: 0;
  bottom: 0;
  display: flex;
}

.wrapper .cart .cart-left {
  flex: 4;
  background-color: #505051;
  display: flex;
}

.wrapper .cart .cart-left .cart-left-icon {
  width: 16vw;
  height: 16vw;
  box-sizing: border-box;
  border: solid 1.6vw #444;
  border-radius: 8vw;
  background-color: #01B0F2;
  font-size: 7vw;
  color: #fff;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: -4vw;
  margin-left: 3vw;
  position: relative;
}

.wrapper .cart .cart-left .cart-left-icon-quantity {
  width: 5vw;
  height: 5vw;
  border-radius: 2.5vw;
  background-color: red;
  color: #fff;
  font-size: 3.6vw;
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  right: -1.5vw;
  top: -1.5vw;
}

.wrapper .cart .cart-left .cart-left-info p:first-child {
  font-size: 4.5vw;
  color: #fff;
  margin-top: 1vw;
}

.wrapper .cart .cart-left .cart-left-info p:last-child {
  font-size: 2.8vw;
  color: #AAA;
}

.wrapper .cart .cart-right {
  flex: 1.4;
  background-color: #505051;
  padding: 2vw 2vw;
}

/*达到起送费时的样式*/
.wrapper .cart .cart-right .cart-right-item {
  width: 100%;
  height: 100%;
  background-color: #01B0F2;
  color: #fff;
  font-size: 4.5vw;
  font-weight: 700;
  user-select: none;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 8vw;
}

/*不够起送费时的样式（只有背景色和鼠标样式的区别）*/
/*
 .wrapper .cart .cart-right .cart-right-item{
 width: 100%;
 height: 100%;
 background-color: #535356;
 color: #fff;
 font-size: 4.5vw;
 font-weight: 700;
 user-select: none;

 display: flex;
 justify-content: center;
 align-items: center;
 }
 */

/****************收藏按钮***************/
.wrapper .collect-button0 {
  position: fixed;
  font-size: 4vw;
  color: white;
  background-color: gray;
  align-items: center;
  justify-content: center;
  padding: 1vw;
  right: 0;
  top: 15vw;
  border-radius: 10vw 0 0 10vw;
}

.wrapper .collect-button1 {
  position: fixed;
  font-size: 4vw;
  color: white;
  background-color: #01B0F2;
  align-items: center;
  justify-content: center;
  padding: 1vw;
  right: 0;
  top: 15vw;
  border-radius: 10vw 0 0 10vw;
}
</style>