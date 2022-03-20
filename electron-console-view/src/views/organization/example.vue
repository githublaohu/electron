<!--
 Copyright (c) [Year] [name of copyright holder]
 [Software Name] is licensed under Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
          http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
-->
<template>

  <div class="page-container demoForm-container">
    <!--条件查询-->
    <el-form :inline="true" :model="filter" class="filter-form-mod">
      <div class="filter-form-row">
        <el-form-item label-width="100px" label="网络地址：" class="col-filter-item">
          <el-input v-model="filter.networkAddress" placeholder="请输入网络地址" clearable size="small"/>
        </el-form-item>
        <el-form-item class="col-filter-item filter-form-submit-row">
          <div class="filter-form-submit">
            <el-button size="small" type="primary" @click="queryModelList">查询</el-button>
          </div>
        </el-form-item>
      </div>
    </el-form>
        <div class="m-table-mod">
            <d2-crud :columns="columns" :data="data" :options="options"  :pagination="pagination" />
        </div>
    </div>

</template>

<script>
import { validateEnglish } from "../../common/utils/validateRules";
import exampleApi from "../../api/example";

var vueData = {};


vueData.pagination = {     currentPage: 1 ,pageSize: 10, total: 0 };
vueData.filter = { networkAddress : ""};
vueData.columns = [
  {title:"网络地址",key:"networkAddress"},
  {title:"端口",key:"port"},
  {title:"协议",key:"protocol"},
  {title:"语言",key:"language"},
  {title:"PRC类型",key:"RPCType"},
  {title:"实例版本号",key:"version"},
  {title:"client版本号",key:"clientVersion"},
   {title:"上线时间",key:"createTime"},
  {title:"下线时间",key:"updateTime"}
];

var mounted = {}
methods.queryModelList = function(){
    exampleApi.queryNodeBaseListByOiId(this.filter,this.pagination).then(res => {
        this.data = res.data;
        this.pagination.total = res.total;
      });
}

export default {
  props: {
    isDialog: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return vueData;
   },
created(){
      vueData.organization = this.getOrganization();
      vueData.filter.oiId =  vueData.organization.getOiId;
},
  mounted() {
    
  },
  methods: methods
};
</script>