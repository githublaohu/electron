import request from '@/common/utils/request'

//根据实例IP、项目ID、告警码查询异常告警信息
export function queryExceptionAlarmInfoList(params) {
  return request.postApi(`/exceptionAlarmInfo/queryExceptionAlarmInfoList`,params)
}


//查询项目ID
export function queryProjectInfoList() {
  return request.postApi(`/project/queryProjectInfoList`)
}




