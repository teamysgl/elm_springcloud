<template>
	<div class="wrapper">

		<!------header部分------>
		<div class="header">
			<img src="../assets/img/头像.png" v-show="!islogin">
			<div v-show="islogin">
				<div v-if="user">
					<!-- 在这里使用 user 对象的属性 -->
					<img :src="this.user.userImg" />
				</div>

			</div>
			<div class="login">
				<div class="box" v-show="!islogin">
					<div class="line">
						<h2 @click="tologin">登录</h2>
						<h2>/</h2>
						<h2 @click="toregister">注册</h2>
					</div>
				</div>
				<div class="box" v-show="islogin">
					<div v-if="user">
						<!-- 在这里使用 user 对象的属性 -->
						<h2 style="font-size: 4.5vw;">{{this.user.userName}}</h2>
					</div>
					<div class="line">
						<p>我的关注</p>
						<p @click="toaddress()">收货地址</p>
					</div>
				</div>
			</div>

			<div class="reset">
				<img src="../assets/img/设置.png" />
			</div>
		</div>

		<!------吃货豆部分------>
		<div class="bean">
			<img src="../assets/img/皇冠.png" />
			<div class="info">
				<h2>白银会员</h2>
				<p></p>
			</div>
			<div class="raise">
				<button>立即升级 &#62;</button>
			</div>
		</div>

		<!------详细列表部分------>
		<ul class="information">

			<div class="money">
				<li>
					<div class="savemoney" @click="toPointsdetails">
						<img src="../assets/img/积分商城.png">
						<p>积分明细</p>
						<span>&#62;</span>
					</div>
				</li>

				<li>
					<div class="red" @click="toWallet">
						<img src="../assets/img/红包.png">
						<p>我的钱包</p>
						<span>&#62;</span>
					</div>
				</li>
			</div>

			<li @click="toaddress()">
				<div class="address">
					<img src="../assets/img/收货地址.png">
					<p>收货地址</p>
					<span>&#62;</span>
				</div>
			</li>

			<li>
				<div class="favourite">
					<img src="../assets/img/爱心.png">
					<p>我的收藏</p>
					<span>&#62;</span>
				</div>
			</li>

			<div class="cooperate">
				<li>
					<div class="judgement">
						<img src="../assets/img/点赞.png">
						<p>欢迎评分</p>
						<span>&#62;</span>
					</div>
				</li>

				<li>
					<div class="onlinehelp">
						<img src="../assets/img/客服.png">
						<p>线上客服</p>
						<span>&#62;</span>
					</div>
				</li>

				<li>
					<div class="onlinehelp">
						<img src="../assets/img/握手.png">
						<p>加盟合作</p>
						<span>&#62;</span>
					</div>
				</li>

			</div>

		</ul>

		<div class="quit">
			<p @click="toquit()">退出当前账号</p>
		</div>

		<Footer></Footer>
	</div>
</template>

<script>
	import Footer from '../components/Footer.vue';
	export default {
		name: 'My',
		data() {
			return {
				user: {},
				islogin: false
			}
		},
		components: {
			Footer
		},
		created() {
			this.user = this.$getSessionStorage('user');
			if (this.user != null) {
				this.islogin = true;
			}
		},
		methods: {
			tologin() {
				this.$router.push({
					path: '/login'
				});
			},
			toregister() {
				this.$router.push({
					path: '/register'
				});
			},
			toPointsdetails() {
				this.$router.push({
					path: '/pointsdetails'
				});
			},
			toWallet() {
				this.$router.push({
					path: '/wallet'
				});
			},
			toquit() {
				this.$removeSessionStorage('user');
				this.user = null;
				this.islogin = false;
				window.location.reload();
				// 可以添加其他清理逻辑或导航到适当的位置
			},
			toaddress() {
				this.$router.push({
					path: '/userAddress'
				});
			}
		}
	};
</script>

<style scoped>
	/****************** 总容器 ******************/
	.wrapper {
		width: 100%;
		height: 100%;
	}

	/******************头部******************/
	.wrapper .header {
		width: 100%;
		height: 25vw;
		background-color: #01B0F2;

		display: flex;
		align-items: center;

		border-radius: 0 0 3vw 3vw;
	}

	.wrapper .header img {
		width: 15vw;
		height: 15vw;
		border-radius: 50%;
		margin-left: 8vw;
	}

	.wrapper .header .login {
		display: flex;
		margin-bottom: 2vw;

		flex-direction: column;
	}

	.wrapper .header .login .box {
		display: flex;
		flex-direction: column;
	}

	.wrapper .header .login .box .line {
		display: flex;
	}


	.wrapper .header .login h2 {
		color: whitesmoke;
		margin-left: 4vw;
		margin-top: 3.5vw;
		user-select: none;
		cursor: pointer;
	}

	.wrapper .header .login p {
		font-size: 2.8vw;
		color: whitesmoke;
		margin-left: 4vw;
		margin-top: 1vw;
		user-select: none;
		cursor: pointer;
	}

	.wrapper .header .reset img {
		width: 8vw;
		height: 8vw;
		margin-left: 30vw;
	}

	/******************吃货豆部分******************/
	.wrapper .bean {
		width: 100%;
		height: 20vw;
		background-color: midnightblue;

		border-radius: 4vw 4vw 0 0;
		margin-top: 2vw;
		display: flex;
	}

	.wrapper .bean img {
		width: 6vw;
		height: 6vw;
		margin-top: 4vw;
		margin-left: 10vw;
	}

	.wrapper .bean .info {
		display: flex;
		flex-direction: column;
	}

	.wrapper .bean .info h2 {
		color: silver;
		font-weight: 350;
		margin-left: 2vw;
		margin-top: 4vw;
	}

	.wrapper .bean .info p {
		font-size: 3vw;
		color: silver;
		font-weight: 800;

		margin-top: 2vw;
	}

	.wrapper .bean .raise button {
		background-color: midnightblue;
		font-size: 4vw;
		margin-top: 6.5vw;
		margin-left: 35vw;
		color: bisque;
		border: none;
	}

	/******************详细信息部分******************/
	.wrapper .information {
		width: 100%;

	}

	.wrapper .information .money {
		border-bottom: solid 15px #e7e7e8;
	}

	.wrapper .information .cooperate {
		border-top: solid 15px #e7e7e8;
	}

	.wrapper .information li {
		width: 100%;
		box-sizing: border-box;
		padding: 2.5vw;
		user-select: none;
		border-bottom: solid 3px #e7e7e8;
		padding-top: 2vw;
		display: flex;
		align-items: center;
	}

	.wrapper .information li img {
		width: 6vw;
		height: 6vw;
		margin-left: 5vw;
	}

	.wrapper .information li p {
		font-size: 4vw;
		margin-left: 5vw;
	}

	.wrapper .information li span {
		font-size: 30px;
		margin-left: 50vw;
		color: darkgrey;
	}

	.wrapper .information li div {
		display: flex;
		align-items: center;
	}

	.wrapper .quit {
		display: flex;
		align-items: center;
		justify-content: center;
		padding: 6vw 0vw 20vw 0vw;
	}

	.wrapper .quit p {
		padding: 1.5vw;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 5vw;
		color: black;
		border-radius: 1vw;
		user-select: none;
		cursor: pointer;
		border: solid 2px #000000;
	}
</style>