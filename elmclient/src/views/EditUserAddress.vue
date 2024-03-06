<template>
	<div class="wrapper">
		<!-- header部分 -->
		<header>
			<i class="fa fa-angle-left" @click="back"></i>
			<p>编辑送货地址</p>
		</header>
		<!-- 表单部分 -->
		<ul class="form-box">
			<li>
				<div class="title">
					联系人：
				</div>
				<div class="content">
					<input type="text" v-model="deliveryAddress.contactName" placeholder="联系人
姓名">
				</div>
			</li>
			<li v-show="nameVisible" style="color: red;font-size: 3vw;">
				联系人姓名不能为空！
			</li>
			<li>
				<div class="title">
					性别：
				</div>
				<div class="content" style="font-size: 3vw;">
					<input type="radio" v-model="deliveryAddress.contactSex" value="1" style="width:6vw;height:3.2vw;"
						checked>男
					<input type="radio" v-model="deliveryAddress.contactSex" value="0" style="width:6vw;height:3.2vw;">女
				</div>
			</li>
			<li>
				<div class="title">
					电话：
				</div>
				<div class="content">
					<input type="tel" v-model="deliveryAddress.contactTel" placeholder="电话">
				</div>
			</li>
			<li v-show="phoneVisible" style="color: red;font-size: 3vw;">
				联系人电话不能为空！
			</li>
			<li v-show="phoneError" style="color: red;font-size: 3vw">
				联系人电话不合规！
			</li>
			<li>
				<div class="title">
					收货地址：
				</div>
				<div class="content">
					<input type="text" v-model="deliveryAddress.address" placeholder="收货地址">
				</div>
			</li>
			<li v-show="addrVisible" style="color: red;font-size: 3vw">
				联系人地址不能为空！
			</li>
		</ul>
		<div class="button-add">
			<button @click="editUserAddress">更新</button>
		</div>
		<!-- 底部菜单部分 -->
		<Footer></Footer>
	</div>
</template>
<script>
	import Footer from '../components/Footer.vue';
	export default {
		name: 'EditUserAddress',
		data() {
			return {
				businessId: this.$route.query.businessId,
				daId: this.$route.query.daId,
				user: {},
				deliveryAddress: {},
				nameVisible: false,
				phoneVisible: false,
				phoneError: false,
				addrVisible: false
			}
		},
		created() {
			this.user = this.$getSessionStorage('user');
      let url=`DeliveryAddressController/getDeliveryAddressById/${this.daId}`
			this.$axios.get(url).then(response => {
				this.deliveryAddress = response.data.result;
			}).catch(error => {
				console.error(error);
			});
		},
		components: {
			Footer
		},
		methods: {
			back() {
				this.$router.go(-1);
			},
			editUserAddress() {
				if (this.deliveryAddress.contactName == '') {
					this.nameVisible=true;
					setTimeout(() => {
					this.nameVisible = false;
					}, 2000);
					return;
				}
				if (this.deliveryAddress.contactTel == '') {
					this.phoneVisible=true;
					setTimeout(() => {
					this.phoneVisible = false;
					}, 2000);
					return;
				} else {
					const contactTelString = this.deliveryAddress.contactTel.toString();
					if (contactTelString.length === 11 && !isNaN(contactTelString) && contactTelString.startsWith('1')) {
						// 数字以1开头，有11位，且全为数字
					} else {
						this.phoneError=true;
						setTimeout(() => {
						this.phoneError = false;
						}, 2000);
						return;
					}
				}
				if (this.deliveryAddress.address == '') {
					this.addrVisible=true;
					setTimeout(() => {
					this.addrVisible = false;
					}, 2000);
					return;
				}
        let url=`DeliveryAddressController/updateDeliveryAddress/${this.daId}/${this.deliveryAddress.contactName}/${this.deliveryAddress.contactSex}/${this.deliveryAddress.contactTel}/${this.deliveryAddress.address}`
				this.$axios.put(url).then(response => {
					if (response.data.result > 0) {
						this.$router.push({
							path: '/userAddress',
							query: {
								businessId: this.businessId
							}
						});
					} else {
						alert('更新地址失败！');
					}
				}).catch(error => {
					console.error(error);
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

	/*************** （表单信息） ***************/
	.wrapper .form-box {
		width: 100%;
		margin-top: 12vw;
	}

	.wrapper .form-box li {
		box-sizing: border-box;
		padding: 4vw 3vw 0vw 3vw;
		display: flex;
	}

	.wrapper .form-box li .title {
		flex: 0 0 18vw;
		font-size: 3vw;
		font-weight: 700;
		color: #666;
	}

	.wrapper .form-box li .content {
		flex: 1;
		display: flex;
		align-items: center;
	}

	.wrapper .form-box li .content input {
		border: none;
		outline: none;
		width: 100%;
		height: 4vw;
		font-size: 3vw;
	}

	.wrapper .button-add {
		box-sizing: border-box;
		padding: 4vw 3vw 0vw 3vw;
	}

	.wrapper .button-add button {
		width: 100%;
		height: 10vw;
		font-size: 3.8vw;
		font-weight: 700;
		border: none;
		outline: none;
		border-radius: 4px;
		color: #fff;
		background-color: #38CA73;
	}
</style>