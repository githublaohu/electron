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
     <el-drawer title="添加操作" :visible.sync="drawer" size="80%">
        <dynamic-form :modeBehavior="modeBehavior"  :behaviorClick="behaviorClick"/ >
        <!-- 子数据表格 -->
        <d2-crud ref="crud" :columns="columns" :data="list" :pagination="viewModel.pagination" :rowHandle="rowHandle"
           @edit="editRow" @pagination-current-change="paginationCurrentChange" >
           <el-button v-if="viewModel.addBehavior.isModel" @click="dialogVisible = !dialogVisible" class="header-button"  size="small" slot="header"  type="success" >新增</el-button>
           <el-button v-if="viewModel.updateBehavior.isModel" @click="dialogVisible = !dialogVisible" class="header-button"  size="small" slot="header"  type="success" >新增</el-button>
          </d2-crud>
            
           <!-- 子数据添加 -->
           <el-drawer title="添加操作" :visible.sync="viewModel.addBehavior.isVisible" size="80%">
                <dynamic-form :modeBehavior="viewModel.addBehavior"  :behaviorClick="requestModel.addModel"/ >
           </el-dramwer>
             <!-- 子数据修改 -->     
           <el-drawer title="修改操作"  :visible.sync="viewModel.updateBehavio.isVisible" size="80%">
                <dynamic-form :modeBehavior="viewModel.updateBehavior"  :behaviorClick="requestModel.updateModel"/ >
           </el-dramwer>

     </el-dramwer>
    <div>

</template>
<script>
import dynamicForm from './dynamicForm';

  export default {
    name: 'dynamic-form',
    props: {
      modeBehavior: Object,
      behaviorClick: Function,
      viewModel: Object,
      requestModel:Object
    },

    methods: {
      handleClick(e) {
        this.behaviorClick(modeBehavior.dataModel , modeBehavior);
      }
    }
  };

</script>