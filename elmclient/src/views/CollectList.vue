<template>
  <div class="wrapper">

    <!-- header部分 -->
    <header>
      <i class="fa fa-angle-left" @click="back"></i>
      <p>我的收藏</p>

    </header>
    <!-- 收藏列表 -->
    <ul class="business">
      <li v-for="item in businessArr" @click="toBusinessInfo(item.businessId)">
        <div class="border">
          <div class="business-img">
            <img :src="item.businessImg">
          </div>
          <div class="business-info">
            <h3>{{item.businessName}}</h3>
            <p>&#165;{{item.starPrice}}起送 | &#165;{{item.deliveryPrice}}配送</p>
            <p>{{item.businessExplain}}</p>
          </div>
        </div>
      </li>

    </ul>
    <Footer></Footer>
  </div>
</template>

<script>
import Footer from "../components/Footer.vue";
export default {
  name: 'CollectList',
  data() {
    return {
      collectList: [],
      businessArr: [],
      user: {}
    }
  },
  created() {
    this.user = this.$getSessionStorage('user');

    this.listCollect();
  },
  components: {
    Footer
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
      this.listBusinesses()
    },
    listBusinesses(){
      for(let collect of this.collectList){
        let url=`BusinessController/getBusinessById/${collect.businessId}`
        this.$axios.get(url).then(response => {
          this.businessArr.push(response.data.result);
        }).catch(error => {
          console.error(error);
        });
      }
    },
    toBusinessInfo(businessId) {
      this.$router.push({
        path: '/businessInfo',
        query: {
          businessId: businessId
        }
      });
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

/****************商家列表部分***************/
.wrapper .business {
  width: 100%;
  margin-top: 12vw;
  padding-bottom: 14vw;
}

.wrapper .business li {
  width: 100%;
  padding: 2vw 2vw 0vw 2vw;
  background-color: #F4F8Fb;
  box-sizing: border-box;
}

.wrapper .business li .border {
  width: 100%;
  box-sizing: border-box;
  padding: 2vw;
  user-select: none;
  background-color: #fff;
  cursor: pointer;
  border-radius: 2vw;

  display: flex;
  align-items: center;
}

.wrapper .business li .border .business-img {
  position: relative;
}

.wrapper .business li .border .business-img img {
  width: 20vw;
  height: 20vw;
  border-radius: 2vw;
}

.wrapper .business li .border .business-img .business-img-quantity {
  width: 5vw;
  height: 5vw;
  background-color: red;
  color: #fff;
  font-size: 3.6vw;
  border-radius: 2.5vw;

  display: flex;
  justify-content: center;
  align-items: center;

  position: absolute;
  right: -1.5vw;
  top: -1.5vw;
}

.wrapper .business li .border .business-info {
  margin-left: 3vw;
}

.wrapper .business li .border .business-info h3 {
  font-size: 3.8vw;
  color: #555;
}

.wrapper .business li .border .business-info p {
  font-size: 3vw;
  color: #888;
  margin-top: 2vw;
}
</style>