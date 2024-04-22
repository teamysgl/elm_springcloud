<template>
	<div class="wrapper">

		<!-- header部分 -->
		<header>
			<i class="fa fa-angle-left" @click="back"></i>
			<p>商家列表</p>
		</header>

		<!-- 商家列表部分 -->
		<ul class="business">

			<li v-for="item in businessArr" @click="toBusinessInfo(item.businessId)">
				<div class="border">
					<div class="business-img">
						<img :src="item.businessImg">
						<div class="business-img-quantity" v-show="item.quantity>0">{{item.quantity}}</div>
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
	import Footer from '../components/Footer.vue';

	export default {
		name: 'BusinessList',
		data() {
			return {
				orderTypeId: this.$route.query.orderTypeId,
				businessArr: [],
				user: {}
			}
		},
		created() {
			this.user = this.$getSessionStorage('user');

			//根据orderTypeId查询商家信息
			let url = `BusinessController/listBusinessByOrderTypeId/${this.orderTypeId}`
			this.$axios.get(url).then(response => {
				this.businessArr = response.data.result;
				console.log(response.data.message);
				//判断是否登录
				if (this.user != null) {
					this.listCart();
				}
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
			listCart() {
				let url = `CartController/listCart/${this.user.userId}`
				this.$axios.get(url).then(response => {
					let cartArr = response.data.result;
					//遍历所有食品列表
					for (let businessItem of this.businessArr) {
						businessItem.quantity = 0;
						for (let cartItem of cartArr) {
							if (cartItem.businessId == businessItem.businessId) {
								businessItem.quantity += cartItem.quantity;
							}
						}
					}
					this.businessArr.sort();
				}).catch(error => {
					console.error(error);
				});
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
		animation: fadeIn 1.0s ease forwards;
		opacity: 0;
		/* 初始透明度为0 */
		transition: background-color 0.3s, transform 0.3s;
	}

	.wrapper .business li:hover .border {
		background-color: #E0F2FE;
		transform: scale(1.05);
	}

	.wrapper .business li:active .border {
		border: 2px solid #FF5722;
		transform: scale(0.95);
	}

	@keyframes fadeIn {
		from {
			opacity: 0;
		}

		to {
			opacity: 1;
		}
	}

	.wrapper .business li .border .business-img {
		position: relative;
	}

	.wrapper .business li .border .business-img img {
		width: 20vw;
		height: 20vw;
		border-radius: 2vw;
		transition: transform 0.3s;
	}

	.wrapper .business li .border:hover .business-img img {
		transform: scale(1.1);
	}

	.wrapper .business li .border:active .business-img img {
		transform: scale(0.9);
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