/**
 * 上传图片
 */
import request from '@/common/utils/request'
/**
 * 上传图片
 * @param imgData
 * @param compress
 * @param fileDirName
 */
export function uploadImgs(imgData, fileDirName, compress = false) {
  return request({
    url: '/uploadImgs',
    method: 'post',
    params: { imgData, compress, fileDirName }
  })
}

