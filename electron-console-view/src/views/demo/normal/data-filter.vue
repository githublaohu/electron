<template>
  <he-table-filter
    :model="filterFrom"
    :rules="filterRules"
    @submit="filterSubmit"
  >
    <el-col :xs="24" :sm="12" :md="6">
      <el-form-item
        label="项目名称"
        class="g-filter-col-item"
        label-width="82px"
        prop="name"
      >
        <el-input
          placeholder="请输入项目名称"
          v-model="filterFrom.name"
          clearable
        ></el-input>
      </el-form-item>
    </el-col>

    <el-col :xs="24" :sm="12" :md="6">
      <el-form-item
        label="项目分组"
        class="g-filter-col-item"
        label-width="82px"
        prop="group"
      >
        <el-select v-model="filterFrom.group" clearable placeholder="全部">
          <el-option
            v-for="item in selectOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
    </el-col>

    <el-col :xs="24" :sm="12" :md="6">
      <el-form-item
        label="优先级"
        class="g-filter-col-item"
        label-width="82px"
        prop="level"
      >
        <el-select v-model="filterFrom.level" clearable placeholder="全部">
          <el-option
            v-for="item in levelOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
    </el-col>

    <el-col :xs="24" :sm="12" :md="6">
      <el-form-item
        label="优先级"
        class="g-filter-col-item"
        label-width="82px"
        prop="level"
      >
        <el-select v-model="filterFrom.level" clearable placeholder="全部">
          <el-option
            v-for="item in levelOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
    </el-col>

    <el-col :xs="24" :sm="12" :md="6">
      <el-form-item
        label="优先级"
        class="g-filter-col-item"
        label-width="82px"
        prop="level"
      >
        <el-select v-model="filterFrom.level" clearable placeholder="全部">
          <el-option
            v-for="item in levelOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
    </el-col>

    <el-col :xs="24" :sm="12" :md="6">
      <el-form-item
        label="优先级"
        class="g-filter-col-item"
        label-width="82px"
        prop="level"
      >
        <el-select v-model="filterFrom.level" clearable placeholder="全部">
          <el-option
            v-for="item in levelOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
    </el-col>
  </he-table-filter>
</template>

<script>
export default {
  name: "data-filter",
  components: {},
  data() {
    return {
      filterRules: {},
      filterFrom: {
        name: "",
        week: "",
        month: "",
        group: "",
        level: "",
        classifySystem: "",
      },
    };
  },
  computed: {
    selectOptions() {
      return [
        {
          label: "电商线",
          value: "1",
        },
        {
          label: "物流线",
          value: "2",
        },
        {
          label: "资金线",
          value: "3",
        },
        {
          label: "供应链线",
          value: "4",
        },
        {
          label: "基础/中间件线",
          value: "5",
        },
      ];
    },
    levelOptions() {
      return [
        {
          label: "高",
          value: "1",
        },
        {
          label: "中",
          value: "2",
        },
        {
          label: "低",
          value: "3",
        },
      ];
    },
    progressTypeOptions() {
      return [
        {
          label: "计划",
          value: "1",
        },
        {
          label: "实际",
          value: "2",
        },
      ];
    },
  },
  mounted() {},
  methods: {
    filterSubmit(filter) {
      console.warn("=filterSubmit=", filter);
    },
  },
};
</script>

<style lang="scss" scoped>
.g-filter-content {
}
.col-date-picker {
  width: 185px;
}
</style>
