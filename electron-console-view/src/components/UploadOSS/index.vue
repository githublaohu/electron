<template>
  <div class="UploadOSS">
    <el-upload
      ref="$uploadOSS"
      :name="uploadName"
      :action="uploadUrl"
      :show-file-list="false"
      :on-preview="handlePreview"
      :on-remove="handleRemove"
      :on-change="handleChange"
      :before-upload="beforeUpload"
      :on-error="uploadFail"
      :on-success="uploadSuccess"
      :file-list="fileList"
      :auto-upload="autoUpload"
      :limit="fileLimit"
      :data="params"
      :http-request="uploadRequest"
      class="file-uploader"
    >
      <div
        v-if="imageUrl"
        :style="{ backgroundImage: 'url('+imageUrl+')', backgroundPosition: 'center center', backgroundSize: 'contain',backgroundRepeat:'no-repeat'}"
        class="file-previw"/>
      <i v-else class="el-icon-plus file-uploader-icon"/>
    </el-upload>
    <el-button v-if="!autoUpload" :disabled="processing" type="primary" class="upload-oss-btn" size="mini" @click="submitUpload">
      {{ submitTips }}<i class="el-icon-upload el-icon--right"/>
    </el-button>
  </div>
</template>

<script>
import request from '@/common/utils/request'
const imgTypes = ['.jpg', '.jpeg', '.png', '.gif']
export default {
  name: 'UploadOSS',
  components: {},
  props: {
    uploadName: {
      type: String,
      default: 'imgData'
    },
    uploadUrl: {
      type: String,
      default: ''
    },
    // 上传文件类型
    fileType: {
      type: Array,
      default: function() {
        return imgTypes
      }
    },
    // 上传最大尺寸(KB)
    fileMaxSize: {
      type: Number,
      default: 1024
    },
    // 上传个数
    fileLimit: {
      type: Number,
      default: null
    },
    // 是否开启自动上传
    autoUpload: {
      type: Boolean,
      default: true
    },
    // 编辑状态图片
    imageUrl: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      fileList: [],
      errorResults: [],
      withCredentials: true,
      // 是否上传中
      processing: false,
      params: {}
    }
  },
  computed: {
    submitTips: function() {
      return this.processing ? '正在上传' : '上传'
    }
  },
  watch: {

  },
  // 挂载
  mounted() {

  },
  // 销毁
  destroyed() {

  },
  methods: {
    // 自定义上传请求
    async uploadRequest(item) {
      console.log('=uploadRequest=', item)
      try {
        const formData = new FormData()
        formData.append(`${this.uploadName}`, item.file)
        if (!this.processing) return false
        this.processing = true
        const uploadRes = await request.post(item.action, formData, {
          headers: { 'Content-Type': 'multipart/form-data' }
        })
        return uploadRes
      } catch (e) {
        return e
      }
    },
    submitUpload() {
      this.$refs.$uploadOSS.submit()
    },
    handlePreview(file) {
      // 可以通过 file.response 拿到服务端返回数据
      console.log(file)
    },
    handleChange(file, fileList) {
      console.log('=handleChange=')
      // 错误的文件也包含在fileList中
      this.processing = false
      const checkedStatus = this.checkFileType(file)
      if (!checkedStatus) {
        const tmpArr = fileList.filter(item => {
          return item.name !== file.name
        })
        this.fileList = tmpArr
        return false
      }
      this.$emit('on-handle-change', file)
    },
    handleRemove(file, fileList) {
      console.log('====handleRemove====', file, fileList)
    },
    // 上传前检查
    async beforeUpload(file) {
      console.log('=beforeUpload=')
      const checkedStatus = this.checkFileType(file)
      if (!checkedStatus) {
        this.processing = false
        return false
      } else {
        this.processing = true
        this.params = {
          imgData: file
        }
        // console.log('正在处理中,请稍后...', this.params)
      }
    },
    // 上传成功
    uploadSuccess(response) {
      this.processing = false
      this.$refs.$uploadOSS.clearFiles()
      this.$emit('on-upload-success', response)
    },
    // 上传错误
    uploadFail(err) {
      if (Array.isArray(err)) {
        this.errorResults = err
      }
      this.processing = false
      this.$message({
        message: `上传失败!`,
        type: 'error'
      })
      console.warn(`${JSON.stringify(err)}`)
      const t = setTimeout(() => {
        this.errorResults = []
        t && clearTimeout(t)
      }, 3000)
      this.$emit('on-upload-fail', err)
    },
    // 文件限制
    checkFileType(file) {
      const typeFileExtend = this.fileType.join(',')// 设置文件格式
      const fileExtend = file.name.substring(file.name.lastIndexOf('.')).toLowerCase()
      const notSupport = typeFileExtend.indexOf(fileExtend) <= -1
      // 大小判断
      const fileKB = file.size / 1024 // 转化为字节KB
      const isLtKB = fileKB < this.fileMaxSize

      if (notSupport) {
        this.$message.error(`上传文件格式错误,上传文件只能是 ${typeFileExtend}格式!`)
        return false
      }
      if (!isLtKB) {
        this.$message.error(`上传文件大小不能超过 ${this.fileMaxSize}KB !`)
        return false
      }
      return !notSupport && isLtKB
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
    .UploadOSS{
       display: flex;
       align-items: center;
      .file-previw{
        max-width: 100%;
        width: 48px;
        height: 48px;
      }
      .file-uploader{
        height: 50px;
        margin-right: 10px;
      }
      .file-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
      }
      .file-uploader .el-upload:hover {
        border-color: #409EFF;
      }
      .file-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 48px;
        height: 48px;
        line-height: 48px;
        text-align: center;
      }
    }
</style>
