<template>
	<div class="wrapper">

		<!-- header部分 -->
		<header>
			<i class="fa fa-angle-left" @click="back"></i>
			<p>用户登陆</p>
		</header>

		<!-- 表单部分 -->
		<ul class="form-box">
			<li>
				<div class="title">
					手机号码：
				</div>
				<div class="content">
					<input type="text" v-model="userId" placeholder="手机号码">
				</div>
			</li>
			<li v-show="numNull" style="color: red;font-size: 3vw;">
				手机号码不能为空！
			</li>
			<li v-show="numError" style="color: red;font-size: 3vw;">
				手机号码不合规！
			</li>
			<li>
				<div class="title">
					密码：
				</div>
				<div class="content">
					<input type="password" v-model="password" placeholder="密码">
				</div>
			</li>
			<li v-show="passNull" style="color: red;font-size: 3vw;">
				密码不能为空！
			</li>
		</ul>

		<div class="button-login">
			<button @click="login">登陆</button>
		</div>
		<div class="button-register">
			<button @click="register">去注册</button>
		</div>

		<Footer></Footer>
	</div>
</template>

<script>
	import Footer from '../components/Footer.vue';

	export default {
		name: 'Login',
		data() {
			return {
				userId: '',
				password: '',
				numNull: false,
				numError: false,
				passNull: false
			}
		},
		methods: {
			back() {
				this.$router.go(-1);
			},
			login() {
				if (this.userId == '') {
					this.numNull = true;
					setTimeout(() => {
						this.numNull = false;
					}, 2000);
					return;
				}
				if (this.password == '') {
					this.passNull = true;
					setTimeout(() => {
						this.passNull = false;
					}, 2000);
					return;
				} else {
					const userIdString = this.userId.toString();
					if (userIdString.length === 11 && !isNaN(userIdString) && userIdString.startsWith('1')) {
						// 数字以1开头，有11位，且全为数字
					} else {
						this.numError = true;
						setTimeout(() => {
							this.numError = false;
						}, 2000);
						return;
					}
				}

				//登录请求
				let url = `UserController/getUserByIdByPass/${this.userId}/${this.password}`
				this.$axios.get(url).then(response => {
					let user = response.data.result;
					if (user == null) {
						alert('用户名或密码不正确！');
					} else {
						//sessionstorage有容量限制，为了防止数据溢出，所以不将userImg数据放入session中

						this.$setSessionStorage('user', user);
						this.$router.go(-1);
					}
				}).catch(error => {
					console.error(error);
				});
			},
			register() {
				this.$router.push({
					path: 'register'
				});
			}
		},
		components: {
			Footer
		}
	}
</script>

<style scoped>
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


	/****************** 表单部分 ******************/
	.wrapper .form-box {
		width: 100%;
		margin-top: 12vw;
	}

	.wrapper .form-box li {
		box-sizing: border-box;
		padding: 4vw 3vw 0 3vw;
		display: flex;
		align-items: center;
	}

	.wrapper .form-box li .title {
		flex: 0 0 18vw;
		font-size: 3vw;
		font-weight: 700;
		color: #666;
	}

	.wrapper .form-box li .content {
		flex: 1;
	}

	.wrapper .form-box li .content input {
		border: none;
		outline: none;
		width: 100%;
		height: 4vw;
		font-size: 3vw;
	}

	.wrapper .button-login {
		width: 100%;
		box-sizing: border-box;
		padding: 4vw 3vw 0 3vw;
	}

	.wrapper .button-login button {
		width: 100%;
		height: 10vw;
		font-size: 3.8vw;
		font-weight: 700;
		color: #fff;
		background-color: #38CA73;
		border-radius: 4px;

		border: none;
		outline: none;
	}

	.wrapper .button-register {
		width: 100%;
		box-sizing: border-box;
		padding: 4vw 3vw 0 3vw;
	}

	.wrapper .button-register button {
		width: 100%;
		height: 10vw;
		font-size: 3.8vw;
		font-weight: 700;
		/*与上面登陆按钮不同的只有颜色、背景色、边框不同*/
		color: #666;
		background-color: #EEE;
		border: solid 1px #DDD;
		border-radius: 4px;

		border: none;
		outline: none;
	}

	.wrapper .button-login button:hover {
		background-color: #539e31;
		/* 鼠标悬停时按钮颜色变化 */
	}

	.wrapper .button-register button:hover {
		background-color: #9a9e9a;
		/* 鼠标悬停时按钮颜色变化 */
	}
</style>