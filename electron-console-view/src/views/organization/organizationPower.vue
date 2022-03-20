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
        <el-form-item label-width="100px" label="组织名：" class="col-filter-item">
          <el-input v-model="filter.blName" placeholder="请输入组织名或者组织英文名" clearable size="small"/>
        </el-form-item>
        <el-form-item class="col-filter-item filter-form-submit-row">
          <div class="filter-form-submit">
            <el-button size="small" type="primary" @click="queryModelList">查询</el-button>
          </div>
        </el-form-item>
      </div>
    </el-form>
    <!--表格功能区域-->
    <div v-if="!isDialog" class="table-btnGroup-ctrl">
      <el-row>
        <el-button type="primary" size="mini" @click="addVisible = !addVisible">新增</el-button>
      </el-row>
    </div>
    <div class="m-table-mod">
         <d2-crud :columns="columns" :data="data" :options="options"  :pagination="pagination" @row-click="routerOrganization" >
            <el-button @click="deleteModel" class="header-button"  size="small" slot="header"  type="success" >移除</el-button>
         </d2-crud>
    </div>

<el-drawer title="添加" :visible.sync="addVisible" :direction="direction" :before-close="handleClose">
  <el-form :model="form" ref="form">
    <el-form-item label="用户" prop="oiType">
          <el-input v-model="form.oiType" placeholder="请填写组织类型" style="width:62%"/>
    </el-form-item>
    <el-form-item label="用户角色" prop="oiExplain">
          <el-select v-model="form.opPower" placeholder="请选择">
              <el-option v-for="item in options":key="item.value" :label="item.label":value="item.value">
              </el-option>
        </el-select>
    </el-form-item>
        <el-button type="success" @click="addModel" size="small">添加</el-button>
      </el-form>
</el-drawer>
  </div>
</template>

<script>
/**
查询用户信息
添加权限
修改权限
查询权限
作废权限

 */
import { validateEnglish } from "../../common/utils/validateRules";
import organizationApi from "../../api/organizationPower";


var vueData = {      dialogTitle: "新增",addVisible: false, updateVisible: false};

vueData.data = []
vueData.pagination = {     currentPage: 1 ,pageSize: 10, total: 0 };
vueData.filter = { oiName : ""};
vueData.form = { oi.uiId:"",opName : "" , organizationName:"",organizationEnglistName:"",organizationTypeEnum:"",opPower: 0}
vueData.update =  { opName : "" , organizationName:"",organizationEnglistName:"",organizationTypeEnum:"",opPower: 0}
vueData.columns = [
  {title:"用户名",key:"opName"},
  {title:"项目名",key:"organizationName"},
  {title:"项目英文名",key:"organizationEnglistName"},
  {title:"项目类型",key:"organizationTypeEnum"},
  {title:"用户权限",key:"opPower"},
  {title:"创建时间",key:"createTime"}
]
 
vueData.rules = {}

vueData.organization = {}

var methods = {};

methods.addModel = function(){
   this.$refs.form.validate(valid => {
        if (!valid) return;
        organizationApi.insertOrganizationInfo(this.form).then(res => {
            this.addVisible = false;
            this.queryModelList();
        })
      })
}

methods.updateModel = function(){

}

methods.deleteModel = function({ row }){
    organizationApi.updateOrganizationPowerStatusByOpId(row).then(res => {
        this.queryModelList();
    });
}

methods.queryModelList = function(){
    organizationApi.queryOrganizationInfoByTypeAndSuperior(this.filter,this.pagination).then(res => {
        this.data = res.data;
        this.pagination.total = res.total;
      });
}
methods.queryModel = function(){

}

methods.routerOrganization = function( row, event, column){
    vueData.update = row;
    vueData.updateVisible = true;
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
    this.queryModelList();
  },
  methods: methods
};
</script>
<style rel="stylesheet/scss" lang="scss">
@import "../../assets/styles/mixin";
.demoForm-container {
  padding: 10px 20px;
  /*查询条件表单*/
  .filter-form-mod {
    padding: 10px;
    border-bottom: 1px solid #ddd;
    font-size: 14px;

    .create-time-picker {
      width: 230px;
    }

    .col-filter-item {
      margin-bottom: 10px;
    }

    .filter-data-picker {
      width: 160px;
    }

    .filter-form-submit-row {
      text-align: center;
      max-width: 300px;
      margin: 0 auto;
    }
  }

  .table-btnGroup-ctrl {
    padding: 20px 20px 20px 0px;
    button {
      margin-left: 0;
      margin-right: 5px;
    }
  }
  /*表格*/
  .station-table-mod {
    padding: 10px 20px;
    .col-table-item .cell {
      max-height: 23px;
      @include text-overflow;
    }

    .station-pagination {
      margin-top: 0;
    }
  }
  /**弹窗添加/编辑**/
  .addEdit-demoForm-dialog {
    .col-dialog-item {
      margin-bottom: 15px;
    }

    .addEdit-station-wrap {
      max-height: 400px;
      overflow-y: auto;
      padding: 10px 20px;
    }

    .dialog-btn {
      margin-right: 20px;
    }

    .dialogForm-row {
      display: flex;
      align-items: center;
    }

    .dialogForm-data-picker {
      width: 185px;
    }
  }
  .filter-input {
    margin-top: 20px;
    .real-input {
      width: 220px;
      margin: 0 10px;
    }
  }
}
</style>
