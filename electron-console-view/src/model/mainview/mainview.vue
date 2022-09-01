<template>
    <!-- main -->
        <!--  queryform -->
    <div class="main-page">
        <div class="filter-input" v-if ="queryFormModel.isUser">
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
        </div>
        <el-button @click="queryFormModel.request" type="primary" size="small">查询</el-button>
        <el-button @click="addModel.isVisible = true" type="primary" size="small">添加</el-button>
        <!--  table -->
        <el-table :data="tableView.__columnsData"  style="width: 100%">
                <el-table-column  v-for="(conmns , index) in tableView.__columns"  :prop="conmns.key" :label="conmns.title" width="300" />
                <el-table-column fixed="right" label="操作"  width="100">
                        <template slot-scope="scope">
                                <el-button v-if="queryModel.isUser"    @click="queryModel.showView(scope.row)"     type="text" size="small">查看</el-button>
                                <el-button v-if="updateModel.isUser" @click="updateModel.showView(scope.row)"   type="text" size="small">编辑</el-button>
                                <el-button v-if="tableView.deleteView(scope.row)" type="text" size="small">作废</el-button>
                                <el-button v-if="tableView.activationView(scope.row)" type="text" size="small">激活</el-button>
                        </template>
                </el-table-column>
        </el-table>
        <el-pagination background layout="prev, pager, next" 
                current-change="queryFormModel.request" 
                :page-size="tableView.__pagination.pageSize" 
                :current-page="tableView.__pagination.currentPage"
                 :total="tableView.__pagination.total" />
        <!--  add -->
        <el-drawer title="添加" :visible.sync="addModel.isVisible" :direction="direction">
                <el-form :rules="addModel.rules" :model="addModel.formData" :ref="addModel.refsName"  size="small" label-width="100px" label-position="left" >
                        <div  v-for="(item , index) in addModel.dataModeBehavior" :key="index"  v-if = "item.__isHide == false">
                                <div v-if="item.__if ==null">
                                        <el-form-item :label="item.__title" :prop="item.__key" >
                                                <el-input v-model="addModel.formData[item.__key]" v-if="item.__type === 'input'  " :placeholder="item.__title" maxlength="64"  style="width:62%" />
                                                <el-select v-if="item.__type === 'selects' " v-model="addModel.formData[item.__key]">
                                                        <el-option v-for="(selectValue , index) in item.__values"  :key="index"  :label="selectValue.label"  :value="selectValue.value"/>
                                                </el-select>
                                        </el-form-item>
                                </div>
                                <div v-if="item.__if !=null">
                                        <el-form-item v-if=" addModel.formData[item['if']] == addModel.formData['ifValue']" :label="item.title" :prop="item.key" >
                                                <el-input v-model="addModel.formData[item.key]" v-if="(item['type'] == null || item.type === 'input') " :placeholder="item.title" maxlength="64"  style="width:62%" />
                                                <el-select v-if="item.type === 'selects' " v-model="addModel.formData[item.key]">
                                                        <el-option v-for="(selectValue , index) in item.__values"  :key="index"  :label="selectValue.label"  :value="selectValue.value"/>
                                                </el-select>
                                        </el-form-item>
                                </div>
                        </div>
                        <div>
                                  <span  slot="footer">
                                        <el-button type="success" @click="addModel.request" size="small">确认</el-button>
                                </span>
                        </div>
                </el-form>
              
        </el-drawer>
        <!--  update -->
        <el-drawer title="修改" :visible.sync="updateModel.isVisible" :direction="direction">
                        <el-form :rules="updateModel.rules" :model="updateModel.formData" :ref="updateModel.formData"  size="small" label-width="100px" label-position="left" >
                        <div  v-for="(item , index) in updateModel.dataModeBehavior" :key="index" v-if = "item.__isHide == false">
                                <div v-if="item.__if ==null">
                                        <el-form-item :label="item.__title" :prop="item.__key" >
                                                <el-input v-model="updateModel.formData[item.__key]" v-if="item.__type === 'input' " :placeholder="item.__title" maxlength="64"  style="width:62%" />
                                                <el-select v-if="item.type === 'selects' " v-model="updateModel.formData[item.__key]">
                                                        <el-option v-for="(selectValue , index) in item.__values"  :key="index"  :label="selectValue.label"  :value="selectValue.value"/>
                                                </el-select>
                                        </el-form-item>
                                </div>
                                <div v-if="item.__if !=null">
                                        <el-form-item v-if=" updateModel.formData[item['__if']] == item['__ifValue']" :label="item.__title" :prop="item.__key" >
                                                <el-input v-model="updateModel.formData[item.__key]" v-if="(item['__type'] == null || item.__type === 'input') " :placeholder="item.__title" maxlength="64"  style="width:62%" />
                                                <el-select v-if="item.type === 'selects' " v-model="updateModel.formData[item.__key]">
                                                        <el-option v-for="(selectValue , index) in item.__values"  :key="index"  :label="selectValue.label"  :value="selectValue.value"/>
                                                </el-select>
                                        </el-form-item>
                                </div>
                        </div>
                        <span slot="footer">
                                <el-button type="success" @click="updateModel.request" size="small">确认</el-button>
                        </span>
                </el-form>
        </el-drawer>
        <!--  query -->
        <el-drawer title="详情" :visible.sync="queryModel.isVisible" :direction="direction" size="80%">
                <el-row>
                        <el-col :span="8">
                                <div class="filter-input">
                                        <div v-for="(item , index) in queryModel.dataModeBehavior" :key="index">
                                                <div class="filter-input" >
                                                        <span>{{item.__title}}：{{queryModel.formData[item.__key]}}</span>
                                                </div>
                                        </div>
                                </div>
                        </el-col>
                        <el-col :span="16" v-if="slaveModel != null">
                                <div class="filter-input">
                                        <div v-for="(item , index) in slaveModel.queryFormModel.dataModeBehavior" :key="index">
                        
                                                <div class="filter-input" v-if="item.__type === 'input' && item.__isHide == false">
                                                        <span>{{item.__title}}：</span>
                                                        <el-input v-model="slaveModel.queryFormModel.formData[item.__key]" class="real-input" size="small" />
                                                </div>
                                                <div class="filter-input" v-if="item.__type === 'selects' && item.__isHide == false">
                                                        <span>{{item.__title}}：</span>
                                                        <el-select v-model="slaveModel.queryFormModel.formData[item.__key]">
                                                                <el-option  v-for="(selectValue , index) in item.__values" :key="index"  :label="selectValue.label"  :value="selectValue.value"  />
                                                        </el-select>
                                                </div>
                                        </div>
                                </div>
                                <el-button @click="slaveModel.queryFormModel.request" type="primary" size="small">查询</el-button>
                                <el-button @click="slaveModel.addModel.isVisible = true" type="primary" size="small">添加</el-button>
                                <!--  table -->
                                <el-table :data="slaveModel.tableView.__columnsData"  style="width: 100%">
                                        <el-table-column  v-for="(conmns , index) in slaveModel.tableView.__columns"  :prop="conmns.key" :label="conmns.title" width="300" />
                                        <el-table-column fixed="right" label="操作"  width="100">
                                                <template slot-scope="scope">
                                                        <el-button v-if="slaveModel.queryModel.isUser"    @click="slaveModel.queryModel.showView(scope.row)"     type="text" size="small">查看</el-button>
                                                        <el-button v-if="slaveModel.updateModel.isUser" @click="slaveModel.updateModel.showView(scope.row)"   type="text" size="small">编辑</el-button>
                                                        <el-button v-if="slaveModel.tableView.deleteView(scope.row)" type="text" size="small">作废</el-button>
                                                        <el-button v-if="slaveModel.tableView.activationView(scope.row)" type="text" size="small">激活</el-button>
                                                </template>
                                        </el-table-column>
                                </el-table>
                        </el-col>
                </el-row>
                <el-drawer  v-if="slaveModel" title="添加" :visible.sync="slaveModel.addModel.isVisible" :direction="direction" :append-to-body="true">
                        <el-form :rules="slaveModel.addModel.rules" :model="slaveModel.addModel.formData" :ref="slaveModel.addModel.refsName"  size="small" label-width="100px" label-position="left" >
                                <div  v-for="(item , index) in slaveModel.addModel.dataModeBehavior" :key="index"  v-if ="item.__isHide == false">
                                        <div v-if="item.__if ==null">
                                                <el-form-item :label="item.__title" :prop="item.__key" >
                                                        <el-input v-model="slaveModel.addModel.formData[item.__key]" v-if="item.__type === 'input'  " :placeholder="item.__title" maxlength="64"  style="width:62%" />
                                                        <el-select v-if="item.__type === 'selects' " v-model="slaveModel.addModel.formData[item.__key]">
                                                                <el-option v-for="(selectValue , index) in item.__values"  :key="index"  :label="selectValue.label"  :value="selectValue.value"/>
                                                        </el-select>
                                                </el-form-item>
                                        </div>
                                        <div v-if="item.__if !=null">
                                                <el-form-item v-if=" slaveModel.addModel.formData[item['if']] == slaveModel.addModel.formData['ifValue']" :label="item.title" :prop="item.key" >
                                                        <el-input v-model="slaveModel.addModel.formData[item.key]" v-if="(item['type'] == null || item.type === 'input') " :placeholder="item.title" maxlength="64"  style="width:62%" />
                                                        <el-select v-if="item.type === 'selects' " v-model="slaveModel.addModel.formData[item.key]">
                                                                <el-option v-for="(selectValue , index) in item.__values"  :key="index"  :label="selectValue.label"  :value="selectValue.value"/>
                                                        </el-select>
                                                </el-form-item>
                                        </div>
                                </div>
                                <div>
                                        <span  slot="footer">
                                                <el-button type="success" @click="slaveModel.addModel.request" size="small">确认</el-button>
                                        </span>
                                </div>
                        </el-form>
              
                </el-drawer>
        </el-drawer>
        <el-drawer title="子项详情" :visible.sync="slaveModel.queryModel.isVisible" :direction="direction" size="30%">
                <el-row>
                        <el-col :span="8">
                                <div class="filter-input">
                                        <div v-for="(item , index) in slaveModel.queryModel.dataModeBehavior" :key="index">
                                                <div class="filter-input" >
                                                        <span>{{item.__title}}：{{slaveModel.queryModel.formData[item.__key]}}</span>
                                                </div>
                                        </div>
                                </div>
                        </el-col>
                </el-row>
        </el-drawer >
        </div>
</template>

<script>

import modelManage from "../modelManage"
var viewModel = null;
//
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
        beforeCreate(){
             
        },
        data() {
           viewModel = modelManage.getViewModel(this);
           viewModel.setViewContextData("info", this.info);
           viewModel.setViewContextData("view",this.view);
           viewModel.tableView.__columnsData = [];
           viewModel.initialization();
           viewModel["direction"] = "rtl";
           return viewModel;
        },
        mounted() {
                this.queryFormModel.request();
        },
        methods: {
                
        }
}

</script>