<template>
	<ul class="footer">
		<li :class="{ active: activeTab === 'index' }" @click="toggleActive('index')">
			<i class="fa fa-home" :class="{ 'jump': activeTab === 'index'}"></i>
			<p>首页</p>
		</li>
		<li :class="{ active: activeTab === 'discover' }" @click="toggleActive('discover')">
			<i class="fa fa-compass"></i>
			<p>发现</p>
		</li>
		<li :class="{ active: activeTab === 'orderList' }" @click="toggleActive('orderList')">
			<i class="fa fa-file-text-o" :class="{ 'jump': activeTab === 'orderList'}"></i>
			<p>订单</p>
		</li>
		<li :class="{ active: activeTab === 'my' }" @click="toggleActive('my')">
			<i class="fa fa-user-o" :class="{ 'jump': activeTab === 'my'}"></i>
			<p>我的</p>
		</li>
	</ul>
</template>

<script>
	export default {
		name: 'Footer',
		created() {
			this.activeTab = this.$route.path.slice(1);
		},
		methods: {
			toggleActive(tab) {
				this.activeTab = tab;
				this.$router.push({
					path: `/${tab}`
				});
			}
		}
	};
</script>

<style>
	.jump {
		animation: jumpAnimation 0.5s;
	}

	@keyframes jumpAnimation {
		0% {
			transform: translateY(0);
		}

		50% {
			transform: translateY(-1.2vw);
		}

		100% {
			transform: translateY(0);
		}
	}

	.footer {
		width: 100%;
		height: 14vw;
		border-top: solid 1px #DDD;
		background-color: #fff;
		position: fixed;
		left: 0;
		bottom: 0;
		display: flex;
		justify-content: space-around;
		align-items: center;
	}

	.footer li {
		/*li本身的尺寸完全由内容撑起*/
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		color: #999;
		user-select: none;
		cursor: pointer;
	}

	.footer li p {
		font-size: 2.8vw;
	}

	.footer li i {
		font-size: 6vw;
	}

	.footer li.active {
		color: #0097ff;
	}
</style>