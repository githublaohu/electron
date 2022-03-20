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
      <!-- 添加 -->
      <div>
      <el-form :rules="modeBehavior.rules" :model="modeBehavior.dataModel" :ref="modeBehavior.refsName"  size="small" label-width="100px" label-position="left" >
        <div  v-for="(item , index) in data" :key="index">
            <div v-if="item.if ==null &&  ">
              <el-form-item :label="item.title" :prop="item.key"  v-if=" modeBehavior.dataModel[item.if] == item.ifValue">
                  <el-input v-model="item.key" v-if="item.type === 'input') " 
                                      :placeholder="item.title" maxlength="64"  style="width:62%" 
                                      :disabled="item.isDisabled"
                                      />
                  <el-select v-if="item.type === 'selects' " v-model="item.key",:disabled="item.isDisabled">
                      <el-option v-for="(selectValue , index) in item.selects"  :key="index"  :label="selectValue.label"  :value="selectValue.value"/>
                  </el-select>
              </el-form-item>
            </div>
        </div>
    </el-form>
      <span v-if="!isView" slot="footer">
        <el-button type="success" @click="handleClick" size="small">确认</el-button>
      </span>
    </div>
</template>

<script>
  export default {
    name: 'dynamic-form',
    props: {
      modeBehavior: Object,
      behaviorClick: Function,
      divided: Boolean,
      icon: String
    },

    methods: {
      handleClick(e) {
        this.behaviorClick(modeBehavior.dataModel , modeBehavior);
      }
    }
  };

  </script>