<template>
  <div class="wrapper">
    <!-- header部分 -->
    <header>
      <i class="fa fa-angle-left" @click="back"></i>
      <p>消费券选择</p>
    </header>
    <!-- 地址列表部分 -->
    <ul class="addresslist">
      <li v-for="item in CouponList" v-show="this.totalPrice>=item.limitNum">
        <!-- 				<div class="addresslist-left" @click="setDeliveryAddress(item)">
                  <h3>{{item.contactName}}{{getSexText(item.contactSex)}} {{item.contactTel}}
                  </h3>
                  <p>{{item.address}}</p>
                </div>
                <div class="addresslist-right">
                  <i class="fa fa-edit" @click="editUserAddress(item.daId)"></i>
                  <i class="fa fa-remove" @click="removeUserAddress(item.daId)"></i>
                </div> -->
        <div class="Coupon-Intro" @click="useThisCoupon(item)">
          <p class="Coupon-Name">满{{item.limitNum}}减{{item.minusNum}}优惠券</p>
          <p class="Coupon-Outdate">有效日期：{{item.outDate}}</p>
        </div>
      </li>

      <li>
        <div class="Coupon-Intro" @click="notUseCoupon()">
          <p class="Coupon-Name">不使用消费券</p>
          <p class="Coupon-Outdate">-</p>
        </div>
      </li>
    </ul>
    <!-- 新增地址部分 -->
    <!-- <div class="addbtn" @click="toAddUserAddress">
      <i class="fa fa-plus-circle"></i>
      <p>新增收货地址</p>
    </div> -->
    <!-- 底部菜单部分 -->
    <Footer></Footer>
  </div>
</template>
<script>
import Footer from '../components/Footer.vue';

export default {
  name: 'UserAddress',
  data() {
    return {
      businessId: this.$route.query.businessId,
      user: {},
      deliveryAddressArr: [],
      CouponList:[],
      totalPrice:this.$route.query.totalPrice,
    }
  },
  created() {
    this.user = this.$getSessionStorage('user');

    this.listDeliveryAddressByUserId();
    this.getCouponByUserId(this.user.userId);
  },
  components: {
    Footer
  },
  methods: {
    back() {
      this.$router.go(-1);
    },
    getSexText(sex) {
      return sex === 1 ? '先生' : '女士';
    },
    listDeliveryAddressByUserId() {
      //查询送货地址
      let url=`DeliveryAddressController/listDeliveryAddressByUserId/${this.user.userId}`
      this.$axios.get(url).then(response => {
        this.deliveryAddressArr = response.data.result;
      }).catch(error => {
        console.error(error);
      });
    },
    setDeliveryAddress(deliveryAddress) {
      //把用户选择的默认送货地址存储到localStorage中
      this.$setLocalStorage(this.user.userId, deliveryAddress);
      this.$router.push({
        path: '/orders',
        query: {
          businessId: this.businessId
        }
      });
    },
    toAddUserAddress() {
      this.$router.push({
        path: '/addUserAddress',
        query: {
          businessId: this.businessId
        }
      });
    },
    editUserAddress(daId) {
      this.$router.push({
        path: '/editUserAddress',
        query: {
          businessId: this.businessId,
          daId: daId
        }
      });
    },
    removeUserAddress(daId) {
      if (!confirm('确认要删除此送货地址吗？')) {
        return;
      }
      let url=`DeliveryAddressController/removeDeliveryAddress/${daId}`
      this.$axios.delete(url).then(response => {
        if (response.data.result > 0) {
          let deliveryAddress = this.$getLocalStorage(this.user.userId);
          if (deliveryAddress != null && deliveryAddress.daId == daId) {
            this.$removeLocalStorage(this.user.userId);
          }
          this.listDeliveryAddressByUserId();
        } else {
          alert('删除地址失败！');
        }
      }).catch(error => {
        console.error(error);
      });
    },
    getCouponByUserId(userId)
    {
      this.$axios.get(`CouponController/getCouponByUserId/${userId}`).then(response =>{
        this.CouponList=response.data.result;
      })
    },
    useThisCoupon(Coupon)
    {
      this.$setLocalStorage(this.$route.query.businessId,Coupon);
      this.$router.push({
        path: '/orders',
        query: {
          businessId: this.businessId
        }
      });
    },
    notUseCoupon()
    {

      this.$setLocalStorage(this.$route.query.businessId,null)
      this.$router.push({
        path: '/orders',
        query: {
          businessId: this.businessId
        }
      });
    }
  }
}
</script>
<style scoped>
/*************** 总容器 ***************/
.wrapper {
  width: 100%;
  height: 100%;
  background-color: #F5F5F5;
}

/*************** header ***************/
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


/*************** addresslist ***************/
.wrapper .addresslist {
  width: 100%;
  padding-top: 12vw;
  background-color: #fff;
}

.wrapper .addresslist li {
  width: 100%;
  box-sizing: border-box;
  border-bottom: solid 1px #DDD;
  padding: 3vw;
  display: flex;
}

.wrapper .addresslist li .addresslist-left {
  flex: 5;
  /*左边这块区域是可以点击的*/
  user-select: none;
  cursor: pointer;
}

.wrapper .addresslist li .addresslist-left h3 {
  font-size: 4.6vw;
  font-weight: 300;
  color: #666;
}

.wrapper .addresslist li .addresslist-left p {
  font-size: 4vw;
  color: #666;
}

.wrapper .addresslist li .addresslist-right {
  flex: 1;
  font-size: 5.6vw;
  color: #999;
  cursor: pointer;
  display: flex;
  justify-content: space-around;
  align-items: center;
}

/*************** 新增地址部分 ***************/
.wrapper .addbtn {
  width: 100%;
  height: 14vw;
  border-top: solid 1px #DDD;
  border-bottom: solid 1px #DDD;
  background-color: #fff;
  margin-top: 4vw;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 4.5vw;
  color: #01B0F2;
  user-select: none;
  cursor: pointer;
}

p {
  margin-left: 2vw;
}

.wrapper .Coupon-Intro{
  cursor: pointer;
  width: 100%;
  height: 16vw;
  box-sizing: border-box;
  padding: 3vw;
  color: #666;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 3.5vw;
}
</style>