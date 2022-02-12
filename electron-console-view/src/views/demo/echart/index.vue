<template>
  <he-page class="demo-echart-page">
    <h1>图表演示</h1>
    <v-chart :options="chartOpts" />
  </he-page>
</template>

<script>
// 手动引入 ECharts 各模块来减小打包体积
// https://github.com/ecomfe/vue-echarts/blob/master/README.zh_CN.md
import ECharts from "vue-echarts";
import "echarts/lib/chart/bar";
import "echarts/lib/component/tooltip";

export default {
  components: {
    "v-chart": ECharts,
  },
  props: {},
  data() {
    return {
      chartOpts: {
        title: {
          text: "ECharts 入门示例",
        },
        tooltip: {},
        legend: {
          data: ["销量"],
        },
        xAxis: {
          data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"],
        },
        yAxis: {},
        series: [
          {
            name: "销量",
            type: "bar",
            data: [5, 20, 36, 10, 10, 20],
          },
        ],
      },
    };
  },
  computed: {},
  mounted() {},
  methods: {},
};
</script>

<style lang="scss" scoped>
.demo-echart-page {
  /**
     * 默认尺寸为 600px×400px，如果想让图表响应尺寸变化，可以像下面这样
     * 把尺寸设为百分比值（同时请记得为容器设置尺寸）。
     */
  .echarts {
    width: 600px;
    height: 400px;
  }
}
</style>
