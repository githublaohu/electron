<template>
  <div class="areaLink">
    <el-select
      class="area-link-item"
      size="small"
      v-model="provinceId"
      :clearable="isClearable"
      :disabled="initDisabled"
      @clear="onClearRest"
      @change="choseProvince"
      placeholder="省级地区">
      <el-option
        v-for="item in province"
        :key="item['orgAreaId']"
        :label="item['orgAreaName']"
        :value="item['orgAreaId']">
      </el-option>
    </el-select>
    <el-select
      class="area-link-item"
      size="small"
      v-model="cityId"
      @change="choseCity"
      placeholder="市级地区"
      :disabled="initOptDisabled?!cityId:initDisabled">
      <el-option
        v-for="item in city"
        :key="item['orgAreaId']"
        :label="item['orgAreaName']"
        :value="item['orgAreaId']">
      </el-option>
    </el-select>
    <el-select
      class="area-link-item"
      size="small"
      v-model="blockId"
      @change="choseBlock"
      :disabled="isShowAll?false:!blockId"
      placeholder="区级地区">
      <el-option
        v-for="item in block"
        :key="item['orgAreaId']"
        :label="item['orgAreaName']"
        :value="item['orgAreaId']">
      </el-option>
    </el-select>
  </div>
</template>

<script>
  import { stationInfoService } from '../../api'
  const allOption={
    createUserId: null,
    level: 1,
    modifyUserId: null,
    modifyUserName: "",
    orgAreaFullName: "全部",
    orgAreaId: '',
    orgAreaName: "全部",
    parentId: 0,
    sortId: null
  };

  export default {
    props: {
      // 是否添加全部
      isShowAll:{
        type:Boolean,
        default:false
      },
      // 是否编辑状态
      isEdit:{
        type:Boolean,
        default:false
      },
      // 是否重置
      isRest:{
        type:Boolean,
        default:false
      },
      isClearable:{
        type:Boolean,
        default:true
      },
      url:{
        type:String,
        default:''
      },
      initProvinceId:{
        type:String|Number,
        default:''
      },
      initCityId:{
        type:String|Number,
        default:''
      },
      initBlockId:{
        type:String|Number,
        default:''
      },
      initDisabled:{
        type:Boolean,
        default:false
      },
      // 禁用优先于initDisabled
      initOptDisabled:{
        type:Boolean,
        default:false
      },
    },
    data () {
      return {
        province:[],
        city:[],
        block:[],

        provinceId:'',
        cityId:'',
        blockId:'',

        currentProvince:null,
        currentCity:null,
        currentBlock:null
      }
    },
    watch:{
      isRest:function(n,o){
        if(n){
          console.log('=开始重置省市区=');
          this.province=[];
          this.city=[];
          this.block=[];
          this.onClearRest();
        }else {
          console.warn('===isRest===',this);
          if(this.province.length===0 && !this.isEdit){
            this.init();
          }
        }
      },
      initProvinceId(n,o){
        this.provinceId = n;
      },
      initCityId(n,o){
        this.cityId = n;
      },
      initBlockId(n,o){
        this.blockId = n;
        if(this.isEdit){
          this.initEdit();
        }else {
          this.init();
        }
      }
    },
    methods:{
      init:function(){
        this.provinceId = this.initProvinceId;
        this.cityId = this.initCityId;
        this.blockId = this.initBlockId;
        Promise.all([
          this.getProvinceData(0),
          this.getCityData(this.provinceId || 11),
          this.getBlockData(this.cityId || 1101)
        ]).then(res=>{
          this.outPutAreaLink();
        })
      },
      initEdit:async function(){
        if(this.provinceId && this.cityId && this.blockId){
          await this.getProvinceData(0);
          await this.getCityData(this.provinceId);
          await this.getBlockData(this.cityId);
          this.outPutAreaLink();
        }
      },
      // 加载省数据
      getProvinceData: function(parentId){
       return new Promise(async(resolve, reject) => {
         const response = await stationInfoService.getRegionData(parentId);
         if (response) {
           // if(this.isShowAll){
           //   response.unshift(allOption)
           // }
           this.province = response;
           if(this.provinceId){
             this.currentProvince = this.province.find(item=>item['orgAreaId'] === this.provinceId)
           }
           resolve({
             provinceId:this.provinceId,
             currentProvince:this.currentProvince
           });
         }else{
           console.log("==getProvinceData null==")
           //reject(response);
         }
       })
      },
      // 加载城市数据
      getCityData: function(parentId,refresh = false){
       return new Promise(async (resolve, reject) => {
         const response = await stationInfoService.getRegionData(parentId);
         if (response) {
           if(this.isShowAll && response.constructor===Array){
             response.unshift(allOption)
           }
           this.city = response;

           if(this.cityId){
             this.currentCity = this.city.find(item=>item['orgAreaId'] === this.cityId)
           }else if(!this.cityId && refresh){
             this.cityId = this.city[0]['orgAreaId'];
             this.currentCity =this.city[0];
           }else {

           }
           resolve({
             cityId:this.cityId,
             currentCity:this.currentCity
           });
         }else{
           console.log("==getCityData null==")
           //reject(response);
         }
       })
      },
      // 加载区域数据
      getBlockData: function(parentId,refresh = false){
       return new Promise(async (resolve, reject) => {
         const response = await stationInfoService.getRegionData(parentId);
         if (response) {
           if(this.isShowAll && response.constructor===Array){
             response.unshift(allOption)
           }
           this.block = response;
           if(this.blockId){
             this.currentBlock = this.block.find(item=>item['orgAreaId'] === this.blockId)
           }else if(!this.blockId && refresh) {
             this.blockId = this.block[0]['orgAreaId'];
             this.currentBlock = this.block[0];
           }
           resolve({
             blockId:this.blockId,
             currentBlock:this.currentBlock
           });
         }else{
           console.log("==getBlockData null==")
           //reject(response);
         }
       })
      },
      // 选省
      choseProvince:async function(e) {
        this.city = [];
        this.block = [];
        this.cityId = '';
        this.blockId = '';
        this.currentCity = null;
        this.currentBlock = null;
        this.currentProvince = this.province.find(item=>item['orgAreaId'] === this.provinceId);
        if(this.isShowAll){
          this.outPutAreaLink('选省');
        }
        await this.getCityData(this.provinceId,!!e);
        await this.getBlockData(this.cityId,!!e);
        if(!this.isShowAll){
          this.outPutAreaLink('选省');
        }
      },
      // 选市
      choseCity:async function(e) {
        this.currentCity = this.city.find(item=>item['orgAreaId'] === e);
        this.block = [];
        this.blockId = '';
        this.currentBlock = null;
        await this.getBlockData(this.cityId,!!e);
        this.outPutAreaLink('选市')
      },
      // 选区
      choseBlock:function(e) {
        this.currentBlock = this.block.find(item=>item['orgAreaId'] === e);
        this.outPutAreaLink('选区')
      },
      outPutAreaLink:function (tips) {
        console.log('outPutAreaLink:',tips);
        const data = {
          province:{
            ...this.currentProvince,
            id:this.provinceId,
          },
          city:{
            ...this.currentCity,
            id:this.cityId
          },
          county:{
            ...this.currentBlock,
            id:this.blockId
          }
        };
        this.$emit('outAreaLink',data)
      },
      // 清空重置
      onClearRest:function () {
        this.currentProvince = null;
        this.currentCity = null;
        this.currentBlock = null;
        this.provinceId = '';
        this.cityId = '';
        this.blockId = '';
        this.outPutAreaLink();
      }
    },
    created:function(){

    },
    mounted(){
      if(this.isEdit){
        this.initEdit();
      }else {
        this.init();
      }
    },
    destroy(){

    }
  }
</script>

<style rel="stylesheet/scss" lang="scss">
  .areaLink {
    .el-input.is-disabled .el-input__inner{
      //background: none;
      color: #666;
    }
    .el-input.is-disabled .el-input__inner::placeholder,
    .el-input__inner::placeholder{
      color: #666;
    }
    .area-link-item{
      width: 140px;
      margin-right: 5px;
    }
  }
</style>
