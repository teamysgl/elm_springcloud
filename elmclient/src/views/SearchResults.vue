<template>
	<div class="wrapper">

		<!-- header部分 -->
		<header>
			<i class="fa fa-angle-left" @click="back"></i>
			<p>搜索结果</p>
		</header>

		<!-- search部分 -->
		<div class="search">
			<!-- 当滚动条超过上面的定位块时，search-fixed-top块变成固定在顶部。 -->
			<div class="search-fixed-top" ref="fixedBox">
				<!-- 搜索框部分中间的白框 -->
				<div class="search-box">
					<i @click="toSearch()" :class="{'fa fa-search': true, 'blue-icon': keyword!==''}"></i>
					<input type="text" v-model="keyword" @keydown.enter="toSearch()" @input="toSearch()"
						placeholder="搜索饿了么商家、商品名称">
				</div>
			</div>
		</div>

		<!-- 分类 -->
		<div class="category">
			<div @click="curTab=0;this.$setSessionStorage('defaultTab', 0);" :class="[curTab === 0 ? 'cur' : 'other']">
				商家</div>
			<div @click="curTab=1;this.$setSessionStorage('defaultTab', 1);" :class="[curTab === 1 ? 'cur' : 'other']">
				食品</div>
		</div>

		<!-- 未找到 -->
		<div v-if="curTab===0&&businessArr.length===0||curTab===1&&foodArr.length===0" class="not-found">
			未找到任何结果，请尝试其他关键词</div>

		<!-- 商家列表部分 -->
		<ul v-if="curTab===0" class="business">
			<li v-for="item in businessArr" @click="toBusinessInfo(item.businessId)">
				<div class="border">
					<div class="business-img">
						<img :src="item.businessImg">
						<div class="business-img-quantity" v-show="item.quantity>0">{{ item.quantity }}</div>
					</div>
					<div class="business-info">
						<h3 v-html="highlightKeyword(item.businessName)"></h3>
						<p>&#165;{{ item.starPrice }}起送 | &#165;{{ item.deliveryPrice }}配送</p>
						<p v-html="highlightKeyword(item.businessExplain)"></p>
						<p v-html="highlightKeyword(item.businessAddress)"></p>
					</div>
				</div>
			</li>
		</ul>

		<!-- 食品列表部分 -->
		<ul v-if="curTab===1" class="food">
			<li v-for="(item,index) in foodArr" @click="toBusinessInfo(item.businessId)">
				<div class="food-left">
					<img :src="item.foodImg">
					<div class="food-left-info">
						<h3 v-html="highlightKeyword(item.foodName)"></h3>
						<p v-html="highlightKeyword(item.foodExplain)"></p>
						<p>&#165;{{item.foodPrice}}</p>
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
		name: 'SearchResults',
		data() {
			return {
				keyword: this.$route.query.keyword,
				businessArr: [],
				foodArr: [],
				user: {},
				curTab: this.$getSessionStorage('defaultTab') === null ? 0 : this.$getSessionStorage('defaultTab')
			}
		},
		created() {
			this.user = this.$getSessionStorage('user');

			this.listBusinessAndFood();
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
			listBusinessAndFood() {
				//根据keyword查询商家信息
				let url = `SearchController/listBusinessByKeyword/${this.keyword}`
				this.$axios.get(url).then(response => {
					this.businessArr = response.data.result;
					//判断是否登录
					if (this.user != null) {
						this.listCart();
					}
				}).catch(error => {
					console.error(error);
				});

				url = `SearchController/listFoodByKeyword/${this.keyword}`
				this.$axios.get(url).then(response => {
					this.foodArr = response.data.result;
					for (let i = 0; i < this.foodArr.length; i++) {
						this.foodArr[i].quantity = 0;
					}
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
			},
			highlightKeyword(text) {
				let res = text
				for (let i of this.$route.query.keyword) {
					if (/\s/.test(i)) continue
					let regex = new RegExp(i, 'g')
					res = res.replace(regex, `<span style="color: #01B0F2;">${i}</span>`)
				}
				return res
			},
			toSearch() {
				if (this.keyword === '') return;
				this.$router.replace({
					path: '/searchResults',
					query: {
						keyword: this.keyword
					}
				});
				this.user = this.$getSessionStorage('user');

				this.listBusinessAndFood();
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

	/****************** search ******************/
	.wrapper .search {
		width: 100%;
		height: 13vw;
		position: fixed;
		z-index: 10;
		display: flex;
		top: 12vw;
	}

	.wrapper .search .search-fixed-top {
		width: 100%;
		height: 13vw;
		background-color: #01B0F2;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.wrapper .search .search-fixed-top .search-box {
		width: 90%;
		height: 9vw;
		background-color: #fff;
		border-radius: 4vw;
		display: flex;
		justify-content: center;
		align-items: center;
		font-size: 3.5vw;
		color: #AEAEAE;
		font-family: "宋体";
		/*此样式是让文本选中状态无效*/
		user-select: none;
	}

	.wrapper .search .search-fixed-top .search-box input {
		border: none;
		outline: none;
		height: 4vw;
		font-size: 3vw;
	}

	.wrapper .search .search-fixed-top .search-box .fa-search {
		margin-right: 1vw;
	}

	.blue-icon {
		color: #01B0F2;
	}

	/****************分类***************/
	.wrapper .category {
		display: flex;
		position: fixed;
		top: 25vw;
		width: 100%;
		align-items: center;
		color: #fff;
		font-size: 4vw;
		z-index: 10;
	}

	.wrapper .category .cur {
		display: flex;
		width: 50%;
		justify-content: center;
		background-image: linear-gradient(to bottom, #01B0F2ff, #01B0F260);
		padding: 1vw;
	}

	.wrapper .category .other {
		display: flex;
		width: 50%;
		justify-content: center;
		background-color: #01B0F2;
		padding: 1vw;
	}

	/****************未找到部分***************/
	.wrapper .not-found {
		display: flex;
		justify-content: center;
		align-items: center;
		margin-top: 35vw;
		font-size: 3.5vw;
		color: #01B0F2;
	}

	/****************商家列表部分***************/
	.wrapper .business {
		width: 100%;
		margin-top: 30vw;
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
	}

	.wrapper .business li .border .business-img {
		position: relative;
	}

	.wrapper .business li .border .business-img img {
		width: 20vw;
		height: 20vw;
		border-radius: 2vw;
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

	/****************食品信息部分***************/
	.wrapper .food {
		width: 100%;
		margin-top: 30vw;
		padding-bottom: 14vw;
	}

	.wrapper .food li {
		width: 100%;
		box-sizing: border-box;
		padding: 2.5vw;
		user-select: none;

		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .food li .food-left {
		display: flex;
		align-items: center;
	}

	.wrapper .food li .food-left img {
		width: 20vw;
		height: 20vw;
		border-radius: 1vw;
	}

	.wrapper .food li .food-left .food-left-info {
		margin-left: 3vw;

	}

	.wrapper .food li .food-left .food-left-info h3 {
		font-size: 3.8vw;
		color: #555;
	}

	.wrapper .food li .food-left .food-left-info p {
		font-size: 3vw;
		color: #888;
		margin-top: 2vw;
	}

	.wrapper .food li .food-right {
		width: 16vw;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .food li .food-right .fa-minus-circle {
		font-size: 5.5vw;
		color: #999;
		cursor: pointer;
	}

	.wrapper .food li .food-right p {
		font-size: 3.6vw;
		color: #333;
	}

	.wrapper .food li .food-right .fa-plus-circle {
		font-size: 5.5vw;
		color: #01B0F2;
		cursor: pointer;
	}
</style>