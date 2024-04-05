<template>
  <div class="wrapper">
    <!-- header部分 -->
    <header>
      <i class="fa fa-angle-left" @click="back"></i>
      <p>在线支付</p>
    </header>
    <!-- 订单信息部分 -->
    <h3>订单信息：</h3>
    <div class="order-info">
      <p>
        {{orders.business.businessName}}
        <i class="fa fa-caret-down" @click="detailetShow"></i>
      </p>
      <p>&#165;{{orders.orderTotal}}</p>
    </div>
    <!-- 订单明细部分 -->
    <ul class="order-detailet" v-show="isShowDetailet">
      <li v-for="item in orders.list">
        <p>{{item.food.foodName}} x {{item.quantity}}</p>
        <p>&#165;{{item.food.foodPrice*item.quantity}}</p>
      </li>
      <li>
        <p>配送费</p>
        <p>&#165;{{orders.business.deliveryPrice}}</p>
      </li>
      <li>
        <p>消费券减免</p>
        <p>-&#165;{{reduction}}</p>
      </li>
      <!-- 积分系统新增显示 -->
      <li>
        <p>获得积分</p>
        <p>{{orders.orderTotal*10}}</p>
      </li>
    </ul>
    <!-- 支付方式部分 -->
    <ul class="payment-type">
      <li>
        <img src="../assets/img/alipay.png">
        <i class="fa fa-check-circle"></i>
      </li>
      <li>
        <img src="../assets/img/wechat.png">
      </li>
    </ul>
    <div class="payment-button">
      <button  :style="{ backgroundColor: isPayed ? 'gray' :  '#38CA73'}" @click="pay">确认支付</button>
    </div>
    <h2 v-show="paySuccess" style="color: #0097FF;text-align: center;">
      支付成功！
    </h2>
    <!-- 底部菜单部分 -->
    <Footer></Footer>
  </div>
</template>
<script>
import Footer from '../components/Footer.vue';
export default {
  name: 'Payment',
  data() {
    return {
      orderId: this.$route.query.orderId,
      userId: this.$route.query.userId,
      reduction: this.$route.query.reduction,
      orders: {
        business: {}
      },
      isShowDetailet: false,
      paySuccess: false,
      isPayed: false
    }
  },
  created() {
    let url=`OrdersController/getOrdersById/${this.orderId}`
    this.$axios.get(url).then(response => {
      this.orders = response.data.result;
    }).catch(error => {
      console.error(error);
    });
  },
  mounted() {
    //这里的代码是实现：一旦路由到在线支付组件，就不能回到订单确认组件。
    //先将当前url添加到history对象中
    history.pushState(null, null, document.URL);
    //popstate事件能够监听history对象的变化
    window.onpopstate = () => {
      this.$router.push({
        path: '/index'
      });
    }
  },
  destroyed() {
    window.onpopstate = null;
  },
  computed: {
    pointGot(){
      let res=this.orders.orderTotal+this.reduction;
      return Math.floor(res);
    }
  },
  methods: {
    detailetShow() {
      this.isShowDetailet = !this.isShowDetailet;
    },
    pay() {
      // this.$axios.put('OrdersController/Orders', this.$qs.stringify({
      // 	orderId: this.orderId,
      // 	userId: this.userId,
      // 	orderTotal: this.orders.orderTotal,
      // 	reduction: this.reduction
      // }),{
      // 	headers: {
      // 		Authorization:this.$getSessionStorage('user').password
      // 	}
      // }).then(response => {
      // 	let orderId = response.data;
      // 	if (orderId > 0) {
      // 		this.paySuccess=true;
      // 		this.isPayed=true;
      // 		setTimeout(() => {
      // 			this.$router.push({
      // 			path: '/orderList'
      // 		});
      // 		}, 500);
      // 	} else {
      // 		alert('支付失败！');
      // 	}
      // }).catch(error => {
      // 	console.error(error);
      // });

      console.log("Kicked");
      let url=`OrdersController/Orders/${this.orderId}`
      this.$axios.put(url)
          .then(
              response =>
              {
                console.log(response);
                console.log("支付成功");
                let url=`PointController/addPointByPointId/${this.userId}/${this.orders.orderDate}/${this.orders.orderTotal*10}`
                console.log(url);
                this.$axios.post(url).then(response =>
                {
                  console.log(response.data);
                });
                this.$router.push({path:'/orderList'});
              }
          )
          .catch(error =>
              {
                console.error(error);
              }
          );


    }
  },
  components: {
    Footer
  }
}
</script>
<style scoped>
/****************** 总容器 ******************/
.wrapper {
  width: 100%;
  height: 100%;
}

/****************** header部分 ******************/
.wrapper header {
  width: 100%;
  height: 12vw;
  background-color: #01B0F2;
  font-size: 4.8vw;
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
  margin: 0vw 0vw 0vw 3vw;
  font-size: 6vw;
  user-select: none;
  cursor: pointer;
}

.wrapper header p {
  margin: 0vw 0vw 0vw 33.5vw;
}

/****************** 订单信息部分 ******************/
.wrapper h3 {
  margin-top: 12vw;
  box-sizing: border-box;
  padding: 4vw 4vw 0;
  font-size: 4vw;
  font-weight: 300;
  color: #999;
}

.wrapper .order-info {
  box-sizing: border-box;
  padding: 4vw;
  font-size: 4vw;
  color: #666;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.wrapper .order-info p:last-child {
  color: orangered;
}

/****************** 订单明细部分 ******************/
.wrapper .order-detailet {
  width: 100%;
}

.wrapper .order-detailet li {
  width: 100%;
  box-sizing: border-box;
  padding: 1vw 4vw;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.wrapper .order-detailet li p {
  font-size: 3vw;
  color: #666;
}

/****************** 支付方式部分 ******************/
.wrapper .payment-type {
  width: 100%;
}

.wrapper .payment-type li {
  width: 100%;
  box-sizing: border-box;
  padding: 4vw;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.wrapper .payment-type li img {
  width: 33vw;
  height: 8.9vw;
}

.wrapper .payment-type li .fa-check-circle {
  font-size: 5vw;
  color: #38CA73;
}

.wrapper .payment-button {
  width: 100%;
  box-sizing: border-box;
  padding: 4vw;
}

.wrapper .payment-button button {
  width: 100%;
  height: 10vw;
  border: none;
  font-size: 3vw;
  /*去掉外轮廓线*/
  outline: none;
  border-radius: 4px;
  color: #fff;
}
</style>