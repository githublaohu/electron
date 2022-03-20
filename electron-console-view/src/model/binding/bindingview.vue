<template>
    <div class="main-page">
        <!-- 绑定视图 -->
        <el-col :span="2">
            <!-- 列表 -->
            <el-tree :data="bindingView.__treeData" :props="bindingView.__mapper" accordion @node-click="treeClick"/>
        </el-col>
        <el-col :span="22">
                <div v-if="queryFormModel.isUser">
                    <div v-for="(item , index) in queryFormModel.dataModeBehavior" :key="index">
                            
                            <div class="filter-input" v-if="item.__type === 'input' && item.__isHide == false">
                                    <span>{{item.__title}}：</span>
                                    <el-input v-model="queryFormModel.formData[item.__key]" class="real-input" size="small" />
                            </div>
                            <div class="filter-input" v-if="item.__type === 'selects' && item.__isHide == false">
                                    <span>{{item.__title}}：</span>
                                    <el-select v-model="queryFormModel.formData[item.__key]">
                                            <el-option  v-for="(selectValue , index) in item.__values" :key="index"  :label="selectValue.label"  :value="selectValue.value"  />
                                    </el-select>
                            </div>
                    </div>
                    <el-button @click="queryFormModel.request" type="primary" size="small">查询</el-button>
                </div>
               <el-button @click="showBindingView" type="primary" size="small">绑定</el-button>
        <!--  table -->
                <el-table :data="tableView.__columnsData"  style="width: 100%">
                        <el-table-column  v-for="(conmns , index) in tableView.__columns"  :prop="conmns.key" :label="conmns.title" width="300" />
                        <el-table-column fixed="right" label="操作"  width="100">
                                <template slot-scope="scope">
                                        <el-button v-if="tableView.deleteView(scope.row)"  @click="unbind(scope.$index, scope.row)" type="text" size="small">解绑</el-button>
                                </template>
                        </el-table-column>
                </el-table>
                <el-pagination background layout="prev, pager, next"  current-change="queryFormModel.request" 
                :page-size="tableView.__pagination.pageSize" 
                :current-page="tableView.__pagination.currentPage"
                 :total="tableView.__pagination.total" />
        </el-col>
        <el-drawer title="绑定" :visible.sync="addModel.isVisible" direction="rtl" size="70%">
             <el-table :data="bindingViewModel.tableView.__columnsData"  style="width: 100%">
                        <el-table-column  v-for="(conmns , index) in bindingViewModel.tableView.__columns"  :prop="conmns.key" :label="conmns.title" width="300" />
                        <el-table-column fixed="right" label="操作"  width="100">
                                <template slot-scope="scope">
                                        <el-button  @click="bind(scope.$index, scope.row)" type="text" size="small">绑定</el-button>
                                </template>
                        </el-table-column>
                </el-table>
                <el-pagination background layout="prev, pager, next"  current-change="bindingViewModel.queryFormModel.request" 
                :page-size="bindingViewModel.tableView.__pagination.pageSize" 
                :current-page="bindingViewModel.tableView.__pagination.currentPage"
                 :total="bindingViewModel.tableView.__pagination.total" />
        </el-drawer>
    </div>

</template>

<script>
import modelManage from "../modelManage"
/**
 * 1. 只有绑定与查询关系 —— 完成
 * 2. 有维度绑定，目前提供当维度绑定
 * 3. 已绑定视图与未绑定视图
 * 4. 绑定说明
 * 5. 主视图只有列表与表格 —— 完成
 * 6. 可以查询详情
 *
 * 主视图
 *  1. 菜单
 *  2. 已经绑定表单
 * 副视图
 *  1. 被绑定视图
 *  2. 
 *
 */

/**
* id
* label
* children
* isChildren
 */
function treeClick(data){
    if(data[this.bindingView.__mapper["children"]] != null){
        return;
    }
    this.setViewContextData(this.bindingView.__rowKey,data);
    this.initialization();
    this.queryFormModel.request();
    // 请求数据
    this.bindingData = data;
    this.bindingViewModel = modelManage.getViewModel(this, data["resourcePath"]);
    this.bindingViewModel.setViewContextData("info", this.info);
    this.bindingViewModel.setViewContextData("view",this.view);
    this.bindingViewModel.setViewContextData(this.bindingView.__rowKey,data);
    this.bindingViewModel.initialization();
    //this.bindingViewModel.queryFormModel.request();
}

function bind(index , row){
    this.appointInitialization("addModel",row);
    this.addModel.request();
}

function unbind(index , row){
    this.appointInitialization("updateModel",row);
    this.updateModel.request();
}

function showBindingView(){
        this.addModel.isVisible = true;
        this.bindingViewModel.queryFormModel.request();
}

export default {
    props: {
                info: {
                        type: Object,
                        default: null
                },
                view: {
                        type: Object,
                        default: null
                }
    },
    data() {
        var viewModel = modelManage.getViewModel(this);
        viewModel.setViewContextData("info", this.info);
        viewModel.setViewContextData("view",this.view);
        viewModel.initialization();
        //viewModel.treeClick = treeClick;
        viewModel.bindingViewModel  = viewModel;
        return viewModel;
    },
    mounted() {
        this.queryFormModel.request();
    },
    methods: {
            treeClick: treeClick,
            bind : bind,
            unbind: unbind,
            showBindingView:showBindingView
    }
}

</script>