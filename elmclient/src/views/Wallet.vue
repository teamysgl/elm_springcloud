<template>
	<div class="wrapper">
		<!-- header部分 -->
		<header>
			<i class="fa fa-angle-left" @click="back"></i>
			<p>我的钱包</p>
		</header>

		<div class="wallet-total">
			<div class="num">
				<img src="../assets/钱包.png">
				<p style="color: black;">{{balance}}</p>
			</div>
			<div class="text">
				<p></p>
			</div>
		</div>

		<div class="wallet-detail">
			<div class="top">
				<p :class="{ active: isShouzhi }" @click="toggleTab('shouzhi')">钱包流水</p>

			</div>
			<div class="border">
				<ul class="get-pay" v-show="isShouzhi">
					<li class="li1">
						<ul class="details">
							<li class="x">
								<h4>交易时间</h4>
							</li>
							<li class="y">
								<h4>交易额</h4>
							</li>
							<li class="y">
								<h4>余额</h4>
							</li>
							<li class="y">
								<h4>交易类型</h4>
							</li>
						</ul>
					</li>
					<li class="li1">
						<ul class="details" v-for="item in availablePointArr">
							<li class="x">
								<h4>{{item.transactionTime}}</h4>
							</li>
							<li class="y">
								<p>{{ item.transactionNum }}</p>
							</li>
							<li class="y">
								<p>{{ item.balance }}</p>
							</li>
							<li class="y">
								<p style="color: green;">{{ item.transactionType }}</p>
							</li>
						</ul>
					</li>
				</ul>

			</div>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'walletDetails',
		data() {
			return {
				isShouzhi: true,
				availablePointArr: [],
				balance: [],
				user: {}
			};
		},
		computed: {
			sum() {
				return this.availablePointArr.reduce((accumulator, currentValue) => accumulator + currentValue.pointNum,
					0);
			}
		},
		created() {
			this.user = this.$getSessionStorage('user');

			this.$axios.get(`WalletController/listWallets`, {
				params: {
					userId: this.user.userId
				},
				headers: {
					Authorization: this.user.password
				}
			}).then(response => {
				// 积分系统新增的改动
				let result = response.data.result;
				this.availablePointArr = result;
				console.log(response);
				// console.log(this.user.userId+" "+this.user.password);
			}).catch(error => {
				console.error(error);
			});
			
			let url = `WalletController/getWalletBalance/${this.user.userId}`
			this.$axios.get(url).then(response => {
				let result = response.data.result;
				this.balance = result;
				console.log("balance:\n" + result)
			}).catch(error => {
				console.error(error);
			});
		},
		methods: {
			back() {
				this.$router.go(-1);
			},
			toggleTab(tab) {
				this.isShouzhi = tab === 'shouzhi';
			},
		},
	};
</script>

<style scoped>
	.wrapper {
		width: 100%;
		height: 100%;
		background-color: #F4F8Fb;
	}

	/****************** header ******************/
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
		margin: 0vw 0vw 0vw 32vw;
	}

	.wrapper .wallet-total {
		padding-top: 20vw;
	}

	.wrapper .wallet-total .num {
		display: flex;
		justify-content: center;
		align-items: center;
		margin-bottom: 1vw;
	}

	.wrapper .wallet-total .num p {
		font-size: 6.5vw;
		padding-left: 2vw;
	}

	.wrapper .wallet-total .num img {
		width: 12vw;
		height: 12vw;
	}

	.wrapper .wallet-total .text {
		display: flex;
		justify-content: center;
		align-items: center;
		font-size: 2.8vw;
	}

	.wrapper .wallet-detail {
		padding-top: 6vw;
	}

	.wrapper .wallet-detail .top {
		display: flex;
		justify-content: space-around;
		align-items: center;
		font-size: 3.5vw;
		padding-bottom: 2vw;
	}

	.wrapper .wallet-detail .top p {
		font-weight: normal;
		user-select: none;
		cursor: pointer;
	}

	.wrapper .wallet-detail .top p.active {
		font-weight: bold;
		user-select: none;
		cursor: pointer;
	}

	.wrapper .wallet-detail .border {
		width: 100%;
		padding: 2vw 2vw 0vw 2vw;
		box-sizing: border-box;
	}

	.wrapper .wallet-detail .border .get-pay {
		width: 100%;
		background-color: #fff;
		border-radius: 2vw;
	}

	.wrapper .wallet-detail .border .get-pay .li1 {
		padding: 2vw 0vw;
	}

	.wrapper .wallet-detail .border .get-pay .details {
		display: flex;
		padding: 0vw 2vw 1vw 2vw;
	}

	.wrapper .wallet-detail .border .get-pay .details .x{
		width: 37%;
		font-size: 2.8vw;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	
	.wrapper .wallet-detail .border .get-pay .details .y{
		width: 21%;
		font-size: 2.8vw;
		display: flex;
		justify-content: center;
		align-items: center;
	}
</style>