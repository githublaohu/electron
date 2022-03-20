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
  <div class="main-page">
    <div class="filter-input">
      <span>策略名称：</span>
      <el-input v-model="filter.siName" class="real-input" size="small" />
    </div>
    <div v-for="(item , index) in data" :key="index">
      <div class="filter-input" v-if="item.query != null && item.type === 'input'">
      <span>{{item.title}}：</span>
        <el-input v-model="filter[item.query]" class="real-input" size="small" />
      </div>
      <div class="filter-input" v-if="item.query != null && item.type === 'selects'">
        <span>{{item.title}}：</span>
        <el-select v-model="filter[item.query]">
          <el-option  v-for="(selectValue , index) in item.selects" :key="index"  :label="selectValue.label"  :value="selectValue.value"  />
        </el-select>
      </div>
    </div>
    <el-button @click="getList" type="primary" size="small">查询</el-button>
    <!-- 主表格 -->
    <d2-crud ref="crud" :columns="columns" :data="list" :pagination="pagination" :rowHandle="rowHandle"
      @edit="editRow" @pagination-current-change="paginationCurrentChange" >
      <el-button @click="dialogVisible = !dialogVisible" class="header-button"  size="small" slot="header"  type="success" >新增</el-button>
      
    </d2-crud>
    <!-- 主添加代码 -->
    <el-dialog @close="clearDialog" :title="dialogTitle" :visible.sync="dialogVisible" width="650px" >
      <el-form :rules="rules" :model="form" ref="form"  size="small" label-width="100px" label-position="left" >
        <el-form-item label="策略名称: " prop="siName">
          <el-input v-model="form.siName" placeholder="请填写策略名称" maxlength="64" style="width:62%" />
        </el-form-item>

        <el-form-item label="策略说明: " prop="siStrategyDescription">
          <el-input v-model="form.siStrategyDescription" placeholder="请填写策略说明" maxlength="64" style="width:62%" />
        </el-form-item>
        
        <div  v-for="(item , index) in data" :key="index">
            <div v-if="item.if ==null">
             <el-form-item :label="item.title" :prop="item.key" >
                <el-input v-model="form[item.key]" v-if="(item['type'] == null || item.type === 'input') " :placeholder="item.title" maxlength="64"  style="width:62%" />
                  <el-select v-if="item.type === 'selects' " v-model="form[item.key]">
                      <el-option v-for="(selectValue , index) in item.selects"  :key="index"  :label="selectValue.label"  :value="selectValue.value"/>
                  </el-select>
              </el-form-item>
            </div>
          <div v-if="item.if !=null">
            <el-form-item v-if=" form[item['if']] == item['ifValue']" :label="item.title" :prop="item.key" >
              <el-input v-model="form[item.key]" v-if="(item['type'] == null || item.type === 'input') " :placeholder="item.title" maxlength="64"  style="width:62%" />
              <el-select v-if="item.type === 'selects' " v-model="form[item.key]">
                <el-option v-for="(selectValue , index) in item.selects"  :key="index"  :label="selectValue.label"  :value="selectValue.value"/>
              </el-select>
            </el-form-item>
          </div>
        </div>
      </el-form>
      <span v-if="!isView" slot="footer">
        <el-button type="success" @click="addRow" size="small">确认</el-button>
      </span>
    </el-dialog>
    <!-- 主详情视图 -->
    <el-drawer title="我是外面的 Drawer" :visible.sync="drawer" size="80%" @open="from.init()">
  <div>
   <!-- 主详情 -->

  <!-- 从表格  作废，详情-->
    <d2-crud ref="crud" :columns="columns" :data="list" :pagination="pagination" :rowHandle="rowHandle"
      @edit="editRow" @pagination-current-change="paginationCurrentChange" >
      <el-button @click="dialogVisible = !dialogVisible" class="header-button"  size="small" slot="header"  type="success" >新增</el-button>
    </d2-crud>
    <!-- 从添加代码 -->
   <el-drawer title="我是里面的" :append-to-body="true" :before-close="handleClose" :visible.sync="innerDrawer">
      <el-form-item label="策略说明: " prop="siStrategyDescription">
          <el-input v-model="form.siStrategyDescription" placeholder="请填写策略说明" maxlength="64" style="width:62%" />
        </el-form-item>
        
        <div  v-for="(item , index) in data" :key="index">
            <div v-if="item.if ==null">
              <el-form-item :label="item.title" :prop="item.key" >
                  <el-input v-model="form[item.key]" v-if="(item['type'] == null || item.type === 'input') " :placeholder="item.title" maxlength="64"  style="width:62%" />
                  <el-select v-if="item.type === 'selects' " v-model="form[item.key]">
                      <el-option v-for="(selectValue , index) in item.selects"  :key="index"  :label="selectValue.label"  :value="selectValue.value"/>
                  </el-select>
              </el-form-item>
            </div>
          <div v-if="item.if !=null">
            <el-form-item v-if=" form[item['if']] == item['ifValue']" :label="item.title" :prop="item.key" >
              <el-input v-model="form[item.key]" v-if="(item['type'] == null || item.type === 'input') " :placeholder="item.title" maxlength="64"  style="width:62%" />
              <el-select v-if="item.type === 'selects' " v-model="form[item.key]">
                <el-option v-for="(selectValue , index) in item.selects"  :key="index"  :label="selectValue.label"  :value="selectValue.value"/>
              </el-select>
            </el-form-item>
   </el-drawer>
  </div>
</el-drawer>

  </div>
</template>

<script>

import abilityInfoApi from "../../api/abilityInfo";
import operation from "../../config/strategyConfig.js";

/**
*  添加
*  详情
*  子添加
 **/
var vueData = {      dialogTitle: "新增",addVisible: false, detailsVisible: false,addFromVisible: false};

var methods = {};

methods.addModel = function(){
   this.$refs.form.validate(valid => {
        if (!valid) return;
        abilityInfoApi.insertOrganizationInfo(this.form).then(res => {
            this.addVisible = false;
            this.queryModelList();
        })
      })
}

methods.deleteModel = function(){

}

methods.queryModelList = function(){
    abilityInfoApi.queryOrganizationInfoByTypeAndSuperior(this.filter,this.pagination).then(res => {
        this.data = res.data;
        this.pagination.total = res.total;
      });
}
methods.queryModel = function(){

}

methods.from = {};


methods.from.addModel = function(){
  
}

methods.from.deleteModel = function(){
  
}

methods.from.queryModelList = function(){
    abilityInfoApi.queryOrganizationInfoByTypeAndSuperior(this.filter,this.pagination).then(res => {
        this.data = res.data;
        this.pagination.total = res.total;
      });
}

methods.from.queryModelList = function(){
      organizationApi.queryOrganizationInfoByTypeAndSuperior(this.from.filter,this.from.pagination).then(res => {
        this.data = res.data;
        this.pagination.total = res.total;
      });
}

function copy() {
  return Object.assign({}, strategyCommonDetail);
}

export default {
  data() {
    var data = operation.getMainStrategyView(this.$route.name);
    data.strategyName = this.$route.meta.title;
    console.log(data);
    return data;
  },
  mounted() {
    this.getList();
  },
  methods: methods    
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.main-page {
  padding: 10px 20px;
  color: #666;
  font-size: 14px;
}
.filter-input {
  margin-top: 20px;
  .real-input {
    width: 220px;
    margin: 0 10px;
  }
}
.header-button {
  margin: 20px;
}
</style>