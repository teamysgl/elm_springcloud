<template>
  <!------总容器------>
  <div class="wrapper">

    <header>
      <i class="fa fa-angle-left" @click="back"></i>
      <p>添加地址</p>
    </header>

    <div class="shangbiao">
      <img src="../assets/elmlogo.jpg">
    </div>
    <!------自动识别------>
    <div class="identify">
      <input type="text" placeholder="  粘贴地址,智能识别填写" v-model="addressInfo">
      <button @click="fill">
        <p>AI识别</p>
      </button>
    </div>

    <!------列表------>
    <ul class="form">
      <li>
        <div class="title">
          收货人：
        </div>
        <div class="content">
          <input type="text" placeholder="  收货人姓名" v-model="deliveryAddress.contactName">
        </div>
      </li>

      <li>
        <div class="gender" style="font-size: 3.5vw">
          <input type="radio" name="sex" checked style="width: 8vw;height: 4.2vw;">先生
          <input type="radio" name="sex" style="width: 8vw;height: 4.2vw;">女士
        </div>
      </li>

      <li>
        <div class="title">
          手机号：
        </div>
        <div class="content">
          <input type="text" placeholder="  填写收获人手机号码" v-model="deliveryAddress.contactTel">
        </div>
      </li>

      <li>
        <div class="title">
          地址：
        </div>
        <div class="content">
          <input type="text" placeholder="  填写详细地址,精确到门牌号" v-model="deliveryAddress.address">
        </div>
      </li>

    </ul>

    <!------确认修改------>
    <div class="ok">
      <button @click="addUserAddress">
        <p>保存修改</p>
      </button>

    </div>
  </div>
</template>
<script>
import Footer from '../components/Footer.vue';
export default {
  name: 'AddUserAddress',
  data() {
    return {
      businessId: this.$route.query.businessId,
      user: {},
      addressInfo: '',
      deliveryAddress: {
        contactName: '',
        contactSex: 1,
        contactTel: '',
        address: ''
      },
      nameVisible: false,
      phoneVisible: false,
      phoneError: false,
      addrVisible: false
    }
  },
  created() {
    this.user = this.$getSessionStorage('user');
  },
  components: {
    Footer
  },
  methods: {
    back() {
      this.$router.go(-1);
    },
    addUserAddress() {
      if (this.deliveryAddress.contactName == '') {
        this.nameVisible = true;
        setTimeout(() => {
          this.nameVisible = false;
        }, 2000);
        return;
      }
      if (this.deliveryAddress.contactTel == '') {
        this.phoneVisible = true;
        setTimeout(() => {
          this.phoneVisible = false;
        }, 2000);
        return;
      } else {
        const contactTelString = this.deliveryAddress.contactTel.toString();
        if (contactTelString.length === 11 && !isNaN(contactTelString) && contactTelString.startsWith('1')) {
          // 数字以1开头，有11位，且全为数字
        } else {
          this.phoneError = true;
          setTimeout(() => {
            this.phoneError = false;
          }, 2000);
          return;
        }
      }
      if (this.deliveryAddress.address == '') {
        this.addrVisible = true;
        setTimeout(() => {
          this.addrVisible = false;
        }, 2000);
        return;
      }

      let url =
          `DeliveryAddressController/saveDeliveryAddress/${this.deliveryAddress.contactName}/${this.deliveryAddress.contactSex}/${this.deliveryAddress.contactTel}/${this.deliveryAddress.address}/${this.user.userId}`
      this.$axios.post(url).then(response => {
        if (response.data.result > 0) {
          this.$router.push({
            path: '/userAddress',
            query: {
              businessId: this.businessId
            }
          });
        } else {
          alert('新增地址失败！');
        }
      }).catch(error => {
        console.error(error);
      });
    },
    fill() {
      this.$axios.get('DeliveryAddressController/AddressInfo', {
        params: {
          addressInfo: this.addressInfo
        }
      }).then(response => {
        this.deliveryAddress.contactName = response.data[0];
        this.deliveryAddress.contactTel = response.data[1];
        this.deliveryAddress.address = response.data[2];
      }).catch(error => {
        console.error(error);
      });
    }
  }
}
</script>
<style scoped>
/****************** 总容器 ******************/
.wrapper {
  width: 100vw;
  position: absolute;
}

/******head部分********/
.wrapper header {
  width: 100%;
  height: 12vw;
  background-color: #01B0F2;
  font-size: 5.5vw;
  color: #fff;
  z-index: 10;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  left: 0;
  top: 0;
  transition: all 0.3s ease;
}

.wrapper header i {
  margin-right: 3vw;
  font-size: 7vw;
  user-select: none;
  cursor: pointer;
}

.wrapper header p {
  margin-left: 3vw;
}


.wrapper .shangbiao {
  width: 100vw;
  height: 20vw;
  margin-top: 14vw;
  display: flex;
  justify-content: center;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}


.wrapper .shangbiao img {
  width: 30vw;
  height: 25vw;
}

/*******自动识别********/
.wrapper .identify {
  width: 100vw;
  height: 18vw;

  margin-top: 2vw;
  display: flex;
  align-items: center;
  justify-content: center;
}

.wrapper .identify input {
  width: 80%;
  height: 10vw;
  font-size: 4.5vw;
  background-color: #d4ebfe;
  border-radius: 5vw;
  outline: none;
  border: none;
  padding: 0 2vw;
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
}

.wrapper .identify button {
  width: 16vw;
  height: 10vw;
  border-radius: 5vw;
  margin-left: 3vw;
  background-color: #01B0F2;
  border: none;
  outline: none;
  cursor: pointer;
  transition: all 0.3s ease;
}

.wrapper .identify button p {
  font-size: 4vw;
  color: #fff;
}

.wrapper .identify button:hover {
  transform: scale(1.1);
}



/******列表******/
.wrapper .form {
  width: 100vw;
  margin-top: 2vw;
}

.wrapper .form li {
  box-sizing: border-box;
  padding: 3vw 3vw 0 3vw;
  display: flex;
  align-items: center;
}

.wrapper .form li .title {
  flex: 0 0 20vw;
  font-size: 4.5vw;
  font-weight: 500;
  color: #333;
  margin-left: 3vw;
}

.wrapper .form li .content {
  flex: 1;
}

.wrapper .form li .content input {
  outline: none;
  width: 90%;
  height: 7vw;
  font-size: 4vw;
  border: none;
  border-bottom: 1px solid #ccc;
}

.wrapper .form li .gender {
  width: 100vw;
  height: 6vw;
  display: flex;
  justify-content: center;
}

.wrapper .ok {
  height: 20vw;
  width: 100vw;
  display: flex;
  align-items: center;
  justify-content: center;
}

.wrapper .ok button {
  width: 35vw;
  height: 10vw;
  border-radius: 5vw;
  background-color: #4ba0fa;
  border: none;
  margin-top: 6vw;
  font-size: 4.5vw;
  font-weight: 600;
  color: #fff;
}

.wrapper .ok button:hover {
  background-color: #3687d8;
}
</style>