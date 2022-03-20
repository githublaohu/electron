<template>
  <div>
    <div id="map-container" class="mapsWrap">
      正在加载地图,请稍后...
    </div>
  </div>
</template>
<script>
  import {loadJs} from '@/common/utils'

  const defaultGps = {
    latitude:28.21,
    longitude:113.00
  };

  export default {
    name: "QQMap",
    props: {
      edit:{
        type: Boolean,
        default: false,
      },
      // 必填key
      mapKey: {
        type: String,
        default: ""
      },
      //纬度 parseFloat 28.223806
      latitude: {
        type: Number|String,
        default: 0,
      },
      //经度 parseFloat 112.9207
      longitude: {
        type: Number|String,
        default: 0,
      },
      // 区域城市
      region: {
        type: String,
        default: ''
      },
      //*编辑初始化地址 并不同步
      address: {
        type: String,
        default: ""
      },
      //缩放18
      zoom: {
        type: Number,
        default: 14
      },
      isMark: {
        type: Boolean,
        default: false
      },
      //周围标注
      markList: {
        type: Array,
        default: function () {
          return []
        }
      }
    },
    computed: {},
    data() {
      return {
        QMap:null,
        geocoder:null,
        isOnGetGps:true,
        markersArray:[]
      }
    },
    created() {

    },
    // 挂载
    mounted() {
      if (typeof(qq) == 'object') {
        this.createMap(this.latitude,this.longitude);
      } else {
        this.loadQMap();
      }
    },
    // 销毁
    destroyed() {

    },
    watch:{
      address:function (n,o) {
        if(!n){
          if(!this.edit){
            if (typeof(qq) == 'object') {
              this.createMap(this.latitude,this.longitude);
            } else {
              this.loadQMap();
            }
          }
          return
        }else {
          this.search(n)
        }
      }
    },
    methods: {
      //地图签名http://map.qq.com/api/js?v=2.exp&libraries=convertor&key=OF5BZ-FQZKI-HIYGM-5NCLV-OJHIE-Q7BPV
      MapLoader(k) {
        return new Promise(function (resolve, reject) {
          window.initTheMap = function () {
            console.log('QQmap加载完成!');
            resolve(qq.maps)
          };
          let script = document.createElement('script');
          script.type = 'text/javascript';
          script.async = false;
          script.$map = true;
          script.src = 'http://map.qq.com/api/js?v=2.exp&libraries=convertor&callback=initTheMap&key=' + k;
          //script.onload = resolve
          if (script.onerror) {
            reject(script.onerror);
          }
          script.onerror = reject;
          // 避免重复引入
          const scripts = document.getElementsByTagName("script");
          let hasLoaded = false;
          for (let index = 0; index < scripts.length; index++) {
            if (scripts[index].$map) {
              hasLoaded = true
              return;
            }
          }
          if(!hasLoaded){
            document.head.appendChild(script)
          }else {
            console.log('已加载qqMap');
          }
          console.log('====MapLoader=====');
          //document.head.appendChild(script)
        })
      },
      async loadQMap(){
        !this.QMap?this.QMap = await this.MapLoader(this.mapKey):'';
        this.createMap(this.latitude,this.longitude);
      },
      async createMap(latitude,longitude){
        const lat = latitude || defaultGps.latitude;
        const long = longitude || defaultGps.longitude;
        if(!this.QMap){
          console.warn('===加载地图失败==')
          return
        }
        //腾讯地图
        let center = new this.QMap.LatLng(parseFloat(lat), parseFloat(long));
        //地图初始化
        this.MAPS = new this.QMap.Map("map-container", {
          center: center,
          zoom: this.zoom
        });
        if(this.edit){
          const t =setTimeout(()=>{
            this.search(this.address);
            clearTimeout(t)
          },500)
        }else {
          this.createMarker(center);
        }
      },
      createMarker(position){
        //创建一个Marker
        const marker = new this.QMap.Marker({
          //设置Marker的位置坐标
          position: position,
          //设置显示Marker的地图
          map: this.MAPS
        });
        marker.setAnimation(this.QMap.MarkerAnimation.DOWN);
        this.markersArray.push(marker);
      },
      search(address){
        //地址解析类 todo 编辑状态有问题
        console.log('地址解析GPS',this.QMap,address);
        if(this.QMap){
          this.geocoder = new this.QMap.Geocoder({
            complete : (result)=>{
              console.log("==search result==",result.detail.location,result);
              let _currentLocation = result.detail.location;
              // 创建一个Marker
              this.MAPS.setCenter(result.detail.location);
              this.MAPS.setZoom(this.zoom);
              this.clearMarker();
              this.createMarker(_currentLocation);

              const {lat,lng} = _currentLocation;
              if(this.isOnGetGps){
                this.$emit('onGetGps',{latitude:parseFloat(lat),longitude:parseFloat(lng)})
              }
            }
          });
        }else {
          console.warn('search QMap init fail!')
        }
        console.log('geocoder:',this.geocoder);
        this.geocoder && this.geocoder.getLocation(address);
      },
      //清除覆盖层
      clearMarker() {
        if (this.markersArray && this.markersArray.length>0) {
          for (let i in this.markersArray) {
            this.markersArray[i].setMap(null);
          }
        }
      },
      /**
       * 请求地址解析成坐标
       * @param QMap
       */
      async getAddressToMap(QMap) {
        console.log('getAddressToMap init...');
        try {
          const geoUrl = `https://apis.map.qq.com/ws/geocoder/v1/`;
          const gpsRes = await this.$jsonp(geoUrl, {
            key: this.mapKey,
            region: this.region,
            address: this.address,
            output: 'jsonp',
          });
          if(gpsRes.result){
            const {location, address_components} = gpsRes.result;
            // this.gpsInitMap(QMap, location['lat'], location['lng']);
          }else {
            console.log('getAddressToMap 初始化失败！');
          }
        } catch (e) {
          console.warn('getAddressToMap error:', e)
        }
      },
      /**
       * 腾讯h5定位库
       * https://lbs.qq.com/tool/component-geolocation.html
       */
      geolocationInit() {
        loadJs('https://3gimg.qq.com/lightmap/components/geolocation/geolocation.min.js').then(() => {
          console.log('====loadJs geolocation ok!====')
        }).catch(e => {
          console.log('====loadJs geolocation error:====', e)
        })
      }
    },
    components: {}
  }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
  .mapsWrap {
    min-height: 240px;
    width: 100%;
    margin: 0 auto;
  }
</style>
