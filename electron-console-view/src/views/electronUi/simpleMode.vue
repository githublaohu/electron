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
    <dynamic-form :modeModel="viewModel.queryModel"  :ModelClick="ModelClick"/ >
      <el-button v-if="viewModel.addModel.isModel" @click="viewModel.addModel.isVisible = !viewModel.addModel.isVisiblel"
       class="header-button"  size="small" slot="header"  type="success" >添加</el-button>
    <div>
     <d2-crud ref="crud" :columns="viewModel.columns" :data="viewModel.columnsData" :pagination="viewModel.pagination"
      @edit="editRow" @pagination-current-change="paginationCurrentChange" >
        <el-button  v-if="viewModel.updateModel.isModel" @click="viewModel.updateModel.isVisible = !viewModel.updateModel.isVisiblel" 
        class="header-button"  size="small" slot="header"  type="success" >修改</el-button>
     </d2-crud>
    </div>
    <!-- 添加 -->
    <div>   
        <drawer  />
    </div>
    <!-- 修改 -->
    <div>   
        <drawer  />
    </div>
</template>
<script>
import dynamicForm from './dynamicForm';
import drawer from './drawer';

</script>