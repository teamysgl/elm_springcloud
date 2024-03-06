<template>
	<div class="wrapper">
		<!-- header部分 -->
		<header>
			<p>积分明细</p>
		</header>
		<!-- 订单列表部分 -->
		<h3>可用积分({{ sum }})</h3>
		<p style="color: gray;">每批积分将在获得后180天过期</p>
		<ul class="order">
			<table>
				<thead>
					<tr style="background-color: #0097FF;color: aliceblue;">
						<th>积分</th>
						<th>获得时间</th>
						<th>过期时间</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="item in availablePointArr" style="background-color: whitesmoke;">
						<td>{{ item.pointNum }}</td>
						<td>{{ item.pointDate }}</td>
						<td style="color: green">{{ item.outDate }}</td>
					</tr>
				</tbody>
			</table>
		</ul>
		<h3>过期积分<i class="fa fa-caret-down" @click="detailetShow()"></i></h3>
		<ul class="order" v-show="isShowDetailet">
			<table>
				<thead>
					<tr style="background-color: gray;color: aliceblue;">
						<th>积分</th>
						<th>获得时间</th>
						<th>过期时间</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="item in outDatePointArr" style="background-color: whitesmoke;">
						<td>{{ item.pointNum }}</td>
						<td>{{ item.pointDate }}</td>
						<td style="color: red">{{ item.outDate }}</td>
					</tr>
				</tbody>
			</table>
		</ul>
		<!-- 底部菜单部分 -->
		<Footer></Footer>
	</div>
</template>
<script>
	import Footer from '../components/Footer.vue';

	export default {
		name: 'Point',
		data() {
			return {
				availablePointArr: [],
				outDatePointArr: [],
				user: {},
				isShowDetailet: 0
			}
		},
		computed: {
			sum() {
				return this.availablePointArr.reduce((accumulator, currentValue) => accumulator + currentValue.pointNum, 0);
			}
		},
		created() {
			this.user = this.$getSessionStorage('user');

			this.$axios.get('PointController/AvailablePoints',{
				params:{
					userId: this.user.userId
				},
				headers: {
					Authorization:this.user.password
				}
			}).then(response => {
				let result = response.data;
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
				let result = response.data;
				this.outDatePointArr = result;
			}).catch(error => {
				console.error(error);
			});
		},
		methods: {
			detailetShow(){
				this.isShowDetailet=!this.isShowDetailet;
			}
		},
		components: {
			Footer
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
		background-color: #0097FF;
		color: #fff;
		font-size: 4.8vw;
		position: fixed;
		left: 0;
		top: 0;
		z-index: 1000;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	/****************** 历史订单列表部分 ******************/
	.wrapper h3 {
		margin-top: 12vw;
		box-sizing: border-box;
		padding: 4vw;
		font-size: 5vw;
		font-weight: 300;
	}

	.wrapper .order {
		width: 100%;
	}

	.wrapper .order li {
		width: 100%;
	}

	.wrapper .order li .order-info {
		box-sizing: border-box;
		padding: 2vw 4vw;
		font-size: 4vw;
		color: #666;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .order li .order-info .order-info-right {
		display: flex;
	}

	.wrapper .order li .order-info .order-info-right .order-info-right-icon {
		background-color: #f90;
		color: #fff;
		border-radius: 3px;
		margin-left: 2vw;
		user-select: none;
		cursor: pointer;
	}

	.wrapper .order li .order-detailet {
		width: 100%;
	}

	.wrapper .order li .order-detailet li {
		width: 100%;
		box-sizing: border-box;
		padding: 1vw 4vw;
		color: #666;
		font-size: 3vw;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .order table {
		width: 100%;
	}
</style>