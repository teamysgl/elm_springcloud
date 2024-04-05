<template>
	<div class="wrapper">
		<!-- header部分 -->
		<header>
			<i class="fa fa-angle-left" @click="back"></i>
			<p>我的券包</p>
		</header>

		<div class="points-total">
			<div class="num">
				<!-- <img src="../assets/吃货豆.png">
				<p style="color: goldenrod;">{{ sum }}</p> -->
			</div>
			<div class="text">
				<p>券包有效期三天，记得使用哦~</p>
			</div>
		</div>

		<div class="points-detail">
			<div class="top">
				<p :class="{ active: isShouzhi }" @click="toggleTab('shouzhi')">可用积分</p>
				<p :class="{ active: !isShouzhi }" @click="toggleTab('duihuan')">已过期积分</p>
			</div>
			<div class="border">
				<ul class="get-pay" v-show="isShouzhi">
					<li class="li1">
						<ul class="details">
							<li>
								<h4>获取时间</h4>
							</li>
							<li>
								<h4>券名</h4>
							</li>
							<li>
								<h4>过期时间</h4>
							</li>
						</ul>
					</li>
					<li class="li1">
						<ul class="details"  v-for="item in availablePointArr">
							<li>
								<h4>{{item.couponDate}}</h4>
							</li>
							<li>
								<p>满{{ item.limitNum }}减{{item.minusNum}}券</p>
							</li>
							<li>
								<p style="color: green;">{{ item.outDate }}</p>
							</li>
						</ul>
					</li>
				</ul>
				<ul class="duihuan" v-show="!isShouzhi">
					<li class="li1">
						<ul class="details">
							<li>
								<h4>获取时间</h4>
							</li>
							<li>
								<h4>过期数量</h4>
							</li>
							<li>
								<h4>过期时间</h4>
							</li>
						</ul>
					</li>
					<li class="li1">
						<ul class="details"  v-for="item in outDatePointArr">
							<li>
								<h4>{{item.pointDate}}</h4>
							</li>
							<li>
								<p>{{ item.pointNum }}</p>
							</li>
							<li>
								<p style="color: red;">{{ item.outDate }}</p>
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
		name: 'PointsDetails',
		data() {
			return {
				isShouzhi: true,
				availablePointArr: [],
				outDatePointArr: [],
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
			console.log("PointsDetail, Here");
			this.user = this.$getSessionStorage('user');

			// this.$axios.get('PointController/AvailablePoints',{
			// 	params:{
			// 		userId: this.user.userId
			// 	},
			// 	headers: {
			// 		Authorization:this.user.password
			// 	}
			// }).then(response => {
			// 	// 积分系统新增的改动
			// 	let result = response.data.result;
			// 	this.availablePointArr = result;
			// 	console.log(response);
			// 	 // console.log(this.user.userId+" "+this.user.password);
			// }).catch(error => {
			// 	console.error(error);
			// });
			
			this.$axios.get(`CouponController/getCouponByUserId/${this.user.userId}`)
			.then(response=>{
				let result = response.data.result;
				this.availablePointArr = result;
				
			}).catch(error => {
				console.error(error);
			});

			this.$axios.get('PointController/OutofDatePoints',{
				params:{
					userId: this.user.userId
				},
				headers: {
					Authorization:this.user.password
				}
			}).then(response => {
				let result = response.data.result;
				this.outDatePointArr = result;
				console.log("outDatePoint:\n"+result)
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

	.wrapper .points-total {
		padding-top: 20vw;
	}

	.wrapper .points-total .num {
		display: flex;
		justify-content: center;
		align-items: center;
		margin-bottom: 1vw;
	}

	.wrapper .points-total .num p {
		font-size: 6vw;
	}

	.wrapper .points-total .num img {
		width: 8vw;
		height: 8vw;
	}

	.wrapper .points-total .text {
		display: flex;
		justify-content: center;
		align-items: center;
		font-size: 2.8vw;
	}

	.wrapper .points-detail {
		padding-top: 6vw;
	}

	.wrapper .points-detail .top {
		display: flex;
		justify-content: space-around;
		align-items: center;
		font-size: 3.5vw;
		padding-bottom: 2vw;
	}

	.wrapper .points-detail .top p {
		font-weight: normal;
		user-select: none;
		cursor: pointer;
	}

	.wrapper .points-detail .top p.active {
		font-weight: bold;
		user-select: none;
		cursor: pointer;
	}

	.wrapper .points-detail .border {
		width: 100%;
		padding: 2vw 2vw 0vw 2vw;
		box-sizing: border-box;
	}

	.wrapper .points-detail .border .get-pay {
		width: 100%;
		background-color: #fff;
		border-radius: 2vw;
	}

	.wrapper .points-detail .border .get-pay .li1 {
		padding: 2vw 0vw;
	}

	.wrapper .points-detail .border .get-pay .details {
		display: flex;
		margin: 0vw 2vw;
	}

	.wrapper .points-detail .border .get-pay .details li {
		width: 34%;
		font-size: 2.8vw;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.wrapper .points-detail .border .duihuan {
		width: 100%;
		background-color: #fff;
		border-radius: 2vw;
	}

	.wrapper .points-detail .border .duihuan .li1 {
		padding: 2vw 0vw;
	}

	.wrapper .points-detail .border .duihuan .details {
		display: flex;
		margin: 0vw 2vw;
	}

	.wrapper .points-detail .border .duihuan .details li {
		width: 34%;
		font-size: 2.8vw;
		display: flex;
		justify-content: center;
		align-items: center;
	}
</style>