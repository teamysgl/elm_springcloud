
<!-- 积分商城，整个篇的代码全部由积分系统0更新 -->
<template>
	<div class ="wrapper">
		<header>
			<i class="fa fa-angle-left" @click="back"></i>
			<p>积分商城</p>
		</header>
		<!-- 用来容纳剩余积分的盒子 -->
		<div class="rest-box">
			<p class="rest-point">剩余积分</p>
			<p class="rest-num">{{sum}}</p>
		</div>
		<ul class="coupon-list">
			<li class="coupon-unit">
				<div class ="coupon-card">
					<p class="coupon-title">满50减40购物券</p>
					<div class ="devide-line"></div>
					<p class="coupon-intro">结算时如果总额满50元，可以优惠40元。</p>
			
					<!-- 这个地方多套一层保证右对齐 -->
					<div class="purchase-area">
						<div class="purchase-button" @click="buy50coupon">
						<p class="purchase-button-word">立即购买</p>
						</div>
					</div>
				</div>
			</li>
			
			<li class="coupon-unit">
				<div class ="coupon-card">
					<p class="coupon-title">满20减15购物券</p>
					<div class ="devide-line"></div>
					<p class="coupon-intro">结算时如果总额满20元，可以优惠15元。</p>
			
					<!-- 这个地方多套一层保证右对齐 -->
					<div class="purchase-area">
						<div class="purchase-button" @click="buy20coupon">
						<p class="purchase-button-word">立即购买</p>
						</div>
					</div>
				</div>
			</li>
			
			<li class="coupon-unit">
				<div class ="coupon-card">
					<p class="coupon-title">满10减8购物券</p>
					<div class ="devide-line"></div>
					<p class="coupon-intro">结算时如果总额10元，可以优惠8元。</p>
			
					<!-- 这个地方多套一层保证右对齐 -->
					<div class="purchase-area">
						<div class="purchase-button" @click="buy10coupon">
						<p class="purchase-button-word">立即购买</p>
						</div>
					</div>
				</div>
			</li>
		</ul>
		
			
		
		
		
		
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

			this.$axios.get('PointController/AvailablePoints',{
				params:{
					userId: this.user.userId
				},
				headers: {
					Authorization:this.user.password
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
			buy50coupon(){
				console.log("buy50coupon");
				console.log(this.sum);
				if(this.sum>1000)
				{
					console.log("50点券购买成功");
					this.substractPoint(1000);
					this.$axios.post(`/CouponController/saveCoupon/${this.user.userId}/50/40`)
				}
				
			},
			buy20coupon()
			{
				console.log("buy20coupon");
				if(this.sum>800)
				{
					console.log("20点券购买成功");
					this.substractPoint(800);
					this.$axios.post(`/CouponController/saveCoupon/${this.user.userId}/20/15`)
				}
				

			},
			buy10coupon()
			{
				console.log("buy10coupon");
				if(this.sum>600)
				{
					console.log("10点券购买成功");
					this.$axios.post(`/CouponController/saveCoupon/${this.user.userId}/10/8`)
					this.substractPoint(600);
				}
				

			},
			// 扣除积分
			substractPoint(substractNum)
			{
				var subNum=substractNum;
				
				for(const availablePoint of this.availablePointArr)
				{
					console.log("subnum="+subNum+"pointNum="+availablePoint.pointNum)
					if(subNum===0)
					{
						break;
					}
					
					if(subNum<availablePoint.pointNum)
					{
						console.log("单条剩余积分 "+(availablePoint.pointNum-subNum));
						this.$axios.post(`/PointController/updatePointNumById/${availablePoint.pointId}/${availablePoint.pointNum-subNum}`);
						subNum=0;
					}
					
					if(subNum>=availablePoint.pointNum)
					{
						subNum=subNum-availablePoint.pointNum;
						this.$axios.delete(`/PointController/deletePointByPointId/${availablePoint.pointId}`);
						
					}
				}
				this.$router.go(0);
				// 
				// while(substractNum!=0)
				// {
					
				// }
			}
			
		},
	};
</script>

<style>
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
	
	.wrapper .rest-box{
		width: 80vw;
		height: 10vw;
		margin-top: 12vw;
		display: flex;
		justify-content: center;
		text-align: center;
	}
	.wrapper .rest-box .rest-point
	{
		font-size: 6vw;
	}
	
	.wrapper .rest-box .rest-num
	{
		font-size: 6vw;
		color: #FFAA00;
	}
	
	.wrapper .coupon-card {
		margin-top: 2vw;
		margin-left: 5vw;
		margin-right: 5vw;
		background-color: #01B0F2;
		border-radius: 10px;
	}
	
	.wrapper .coupon-card .coupon-title{
		padding:3vw 0vw 3vw 3vw;
		font-size: 6vw;
		color: #F4F8Fb;
	}
	
	.wrapper .coupon-card .devide-line{
		background-color: #F4F8Fb;
		width: 80vw;
		height: 2px;
		
	}
	
	.wrapper .coupon-card .coupon-intro{
		padding:3vw 0vw 3vw 3vw;

		font-size: 4vw;
		color: #F4F8Fb;
	}
	
	.wrapper .coupon-card  .purchase-area{
		display: flex;
		justify-content: flex-end;
	}
	.wrapper .coupon-card .purchase-button{
		width: 20vw;
		height: 8vw;
		background-color: #00dd00;
		border-radius: 10px;
		margin-right: 3vw;
		margin-bottom: 3vw;
		text-align: center;
		display: flex;
		justify-content: center;
		cursor: pointer;
	}
	.wrapper .coupon-card .purchase-button .purchase-button-word{
		color: #F4F8Fb;
		font-size: 4vw;
		margin: auto;
	}
</style>