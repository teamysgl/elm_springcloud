<template>
	<div class="wrapper">
		<!-- header部分 -->
		<header>
			<i class="fa fa-angle-left" @click="back"></i>
			<p>确认订单</p>
		</header>
		<!-- 订单信息部分 -->
		<div class="order-info">
			<div class="box">
				<h5>订单配送至：</h5>
				<div class="order-info-address" @click="toUserAddress">
					<p>{{ deliveryaddress != null ? deliveryaddress.address : '请选择收货地址' }}</p>
					<i class="fa fa-angle-right"></i>
				</div>
				<p>{{ deliveryaddress != null ? deliveryaddress.contactName : '' }}
					{{ deliveryaddress != null ? getSexText(deliveryaddress.contactSex) : '' }}
					{{ deliveryaddress != null ? deliveryaddress.contactTel : '' }}
				</p>
			</div>
		</div>
		<h3>{{ business.businessName }}</h3>
		<!-- 订单明细部分 -->
		<div class="detail">
			<ul class="order-detailed">
				<li v-for="item in cartArr">
					<div class="order-detailed-left">
						<img :src="item.food.foodImg">
						<p>{{ item.food.foodName }} x {{ item.quantity }}</p>
					</div>
					<p>&#165;{{ item.food.foodPrice * item.quantity }}</p>
				</li>
			</ul>
			<div class="order-deliveryfee">
				<p>配送费</p>
				<p>&#165;{{ business.deliveryPrice }}</p>
			</div>

			<div class="coupon-select" @click="toCouponSelect">
				<p>选择消费券</p>
				<p>{{ getCouponName() }}</p>
			</div>
		</div>
		<!-- 合计部分 -->
		<div class="total">
			<div class="total-left">
				&#165;{{ finalPrice }}
			</div>
			<div class="box">
				<div class="total-right" @click="toPayment">
					提交订单
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'Orders',
		data() {
			return {
				businessId: this.$route.query.businessId,
				business: {},
				user: {},
				cartArr: [],
				deliveryaddress: {},
				availablePointArr: [],
				couponUsed: {},

			}
		},
		created() {
			// 首先初始化finalPrice
			// finalPrice=totalPrice;

			this.user = this.$getSessionStorage('user');
			this.deliveryaddress = this.$getLocalStorage(this.user.userId);
			this.couponUsed = this.$getLocalStorage(this.businessId);
			//查询当前商家
			let businessUrl = `BusinessController/getBusinessById/${this.businessId}`
			this.$axios.get(businessUrl).then(response => {
				this.business = response.data.result;
			}).catch(error => {
				console.error(error);
			});

			//查询当前用户在购物车中的当前商家食品列表
			let cartUrl = `CartController/listCart/${this.user.userId}/${this.businessId}`
			this.$axios.get(cartUrl).then(response => {
				this.cartArr = response.data.result;
			}).catch(error => {
				console.error(error);
			});
		},
		computed: {
			totalPrice() {
				let totalPrice = 0;
				for (let cartItem of this.cartArr) {
					totalPrice += cartItem.food.foodPrice * cartItem.quantity;
				}
				totalPrice += this.business.deliveryPrice;
				//积分券包界面新增
				// if (this.couponUsed != null) {
				// 	totalPrice -= this.couponUsed.minusNum;
				// }
				return totalPrice;
			},
			finalPrice() {
				let totalPrice = 0;
				for (let cartItem of this.cartArr) {
					totalPrice += cartItem.food.foodPrice * cartItem.quantity;
				}
				totalPrice += this.business.deliveryPrice;
				//积分券包界面新增
				if (this.couponUsed != null) {
					totalPrice -= this.couponUsed.minusNum;
				}
				return totalPrice;
			}

		},
		methods: {
			back() {
				this.$router.go(-1);
			},
			getSexText(sex) {
				return sex === 1 ? '先生' : '女士';
			},
			toUserAddress() {
				this.$router.push({
					path: '/userAddress',
					query: {
						businessId: this.businessId
					}
				});
			},
			toPayment() {
				if (this.deliveryaddress == null) {
					alert('请选择送货地址！');
					return;
				}

				//创建订单
				let finalPrice = this.totalPrice;
				if (this.couponUsed != null) {
					finalPrice -= this.couponUsed.minusNum;
				}
				let url =
					`OrdersController/createOrders/${this.user.userId}/${this.businessId}/${this.deliveryaddress.daId}/${this.finalPrice}`
				this.$axios.post(url).then(response => {
					let orderId = response.data.result;
					if (orderId > 0) {
						//积分系统新增代码
						if (this.couponUsed != null) {
							this.$axios.delete(
								`CouponController/deleteCouponByCouponId/${this.couponUsed.couponId}`);
						}
						this.$router.push({
							path: '/payment',
							query: {
								orderId: orderId,
								userId: this.user.userId,
								reduction: this.couponUsed === null ? 0 : this.couponUsed.minusNum
							}
						});
						this.$setLocalStorage(this.businessId,null);
						console.log("this.$removeSessionStorage(this.businessId)");
					} else {
						alert('创建订单失败！');
					}
				}).catch(error => {
					console.error(error);
				});
			},
			toCouponSelect() {
				this.$router.push({
					path: '/CouponSelect',
					query: {
						businessId: this.businessId,
						totalPrice: this.totalPrice
					}
				});
			},
			getCouponName() {
				if (this.couponUsed == null) {
					return "不使用优惠券";
				} else {
					return "满" + this.couponUsed.limitNum + "减" + this.couponUsed.minusNum + "优惠券";
				}
			}
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
	.wrapper .order-info {
		/*注意这里，不设置高，靠内容撑开。因为地址有可能折行*/
		width: 100%;
		background-color: #01B0F2;
		box-sizing: border-box;
		padding: 2vw 2vw;
		color: #fff;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .order-info .box {
		/*注意这里，不设置高，靠内容撑开。因为地址有可能折行*/
		width: 100%;
		margin-top: 12vw;
		background-color: #fff;
		box-sizing: border-box;
		padding: 2vw;
		color: black;
		border-radius: 2vw;
	}

	.wrapper .order-info h5 {
		font-size: 3vw;
		font-weight: 300;
	}

	.wrapper .order-info .order-info-address {
		width: 100%;
		display: flex;
		justify-content: space-between;
		align-items: center;
		font-weight: 700;
		user-select: none;
		cursor: pointer;
		margin: 1vw 0;
	}

	.wrapper .order-info .order-info-address p {
		width: 90%;
		font-size: 5vw;
	}

	.wrapper .order-info .order-info-address i {
		font-size: 6vw;
	}

	.wrapper .order-info p {
		font-size: 3.5vw;
	}

	.wrapper h3 {
		box-sizing: border-box;
		padding: 3vw;
		font-size: 4vw;
		color: #666;
		border-bottom: solid 1px #DDD;
	}

	/****************** 订单明细部分 ******************/
	.wrapper .detail {
		width: 100%;
		padding-bottom: 14vw;
	}

	.wrapper .order-detailed {
		width: 100%;
	}

	.wrapper .order-detailed li {
		width: 100%;
		height: 16vw;
		box-sizing: border-box;
		padding: 3vw;
		color: #666;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .order-detailed li .order-detailed-left {
		display: flex;
		align-items: center;
	}

	.wrapper .order-detailed li .order-detailed-left img {
		width: 10vw;
		height: 10vw;
	}

	.wrapper .order-detailed li .order-detailed-left p {
		font-size: 3.5vw;
		margin-left: 3vw;
	}

	.wrapper .order-detailed li p {
		font-size: 3.5vw;
	}

	.wrapper .order-deliveryfee {
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

	.wrapper .coupon-select {
		width: 100%;
		height: 16vw;
		box-sizing: border-box;
		padding: 3vw;
		color: #666;
		display: flex;
		justify-content: space-between;
		align-items: center;
		font-size: 3.5vw;
		cursor: pointer;
	}

	/****************** 订单合计部分 ******************/
	.wrapper .total {
		width: 100%;
		height: 14vw;
		position: fixed;
		left: 0;
		bottom: 0;
		display: flex;
	}

	.wrapper .total .total-left {
		flex: 2;
		background-color: #505051;
		color: #fff;
		font-size: 4.5vw;
		font-weight: 700;
		user-select: none;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.wrapper .total .box {
		flex: 1;
		background-color: #505051;
		padding: 2vw 2vw;
	}

	.wrapper .total .box .total-right {
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
</style>