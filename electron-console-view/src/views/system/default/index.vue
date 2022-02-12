<template>
  <div class="page page-default">
    <h1>默认首页</h1>
    <el-button @click="getData">请求数据</el-button>
  </div>
</template>

<script>
// import store from '@/store';

// const oidcInfo = store ? store.getters.oidcInfo : undefined;

export default {
  name: "default-index",
  components: {},
  data() {
    return {};
  },
  computed: {},
  mounted() {
    this.init();
  },
  methods: {
    init() {
      // this.reduceCountKeys(this.a,this.b);
    },
    logError() {},
    async getData() {
      // const resData = await userApi.userInfo();
      // const resData = await this.$api.userApi.userInfo({},{loading:true});
      // console.warn('======resData=======',resData)
      const params = {
        pageNo: 1,
        pageSize: 10,
      };
      const req1 = this.$api.demoApi.getList(params);
      const req2 = this.$api.demoApi.getProduct(params);

      req1
        .then((data1) => {
          console.warn("======data1=======", data1);
        })
        .catch(() => {});
      req2
        .then((data2) => {
          console.warn("======data2=======", data2);
        })
        .catch(() => {});
      // Promise.all([req1,req2]).then(resData=>{
      //   console.warn('======resData=======',resData)
      // }).catch(e=>{
      //
      // });
      // axios.post('/api/serviceStationReturnBill/pageQueryStationReturnBill',params1,{
      //   headers: { 'Authorization': `${oidcInfo.token_type} ${oidcInfo.access_token}`,'LoginTag':true},
      // })
      // const resData = await this.$api.stationInfoApi.pageQueryServiceStation(params,{loading:true});
      // console.warn('======resData=======',resData)
    },

    // 统计数组元素重复次数
    reduceCount() {
      const arr = ["A", "B", "C", "A"];
      const sum = arr.reduce((prev, cur) => {
        // console.log(prev, cur, index, arr);
        if (cur in prev) {
          prev[cur]++;
        } else {
          prev[cur] = 1;
        }
        return prev;
      }, {});
      console.log(arr, sum);
    },
    // 数组元素对象根据key查找重复元素
    reduceCountKeys(arr1, arr2, key = "sku") {
      const arr = [].concat(arr1, arr2);
      const itemKey = key;
      const repeatKey = [];
      const repeatItem = arr.reduce((prev, cur) => {
        // console.log(prev, cur, index, arr);
        if (prev[cur[itemKey]]) {
          repeatKey.push(cur[itemKey]);
        } else {
          prev[cur[itemKey]] = cur[itemKey];
        }
        return prev;
      }, {});
      console.log(arr, repeatItem, repeatKey);
    },

    // 数组去重
    reduceRepeatSimple() {
      const arr = [1, 2, 3, 4, 4, 1];
      const newArr = arr.reduce((pre, cur) => {
        if (!pre.includes(cur)) {
          return pre.concat(cur);
        } else {
          return pre;
        }
      }, []);
      console.log(newArr);
    },

    // 降维
    reuceArr() {
      const arr = [
        [0, 1],
        [2, 3],
        [4, 5],
      ];
      const newArr = arr.reduce((pre, cur) => {
        return pre.concat(cur);
      }, []);
      console.log(newArr); // [0, 1, 2, 3, 4, 5]
    },
  },
};
</script>

<style scoped></style>
