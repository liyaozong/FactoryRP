/**
 * Created by Administrator on 2016/5/26.
 */
var myApp = angular.module('myApp.Common', [
    'ui.router',
    'ngResource'
]).factory('UrlService', function($resource, $log) {
    var allUrlInfo=[];
    /*开发环境 start*/
    allUrlInfo.push({name: 'xfdMessage', url:'http://192.168.6.238:8015/message-service/'});//短信模板 测试环境
    allUrlInfo.push({name: 'baseConfigProductModel', url:'http://192.168.6.238:8002/loan-audit-service/modelRoleController/'});//产品模型配置 测试环境
    allUrlInfo.push({name: 'xfdAudit', url:'http://192.168.6.238:8002/loan-audit-service/auditOrderController/'});//消费贷审批4.0 测试环境
    allUrlInfo.push({name: 'authorizationNew',  url:'http://192.168.6.238:8016/security-service/'});//权限管理 测试环境
    allUrlInfo.push({name: 'authorizationNewFenDan',  url:'http://192.168.6.238:8016/security-service/'});//权限管理小组 测试环境
    allUrlInfo.push({name: 'manageAudit', url:'http://192.168.6.238:8003/loan-audit-store-service/'});//经营贷审批 测试
    allUrlInfo.push({name: 'manageAuditCredit', url:'http://192.168.6.238:8003/loan-audit-store-service/credit/'});//经营贷审批授信 测试环境
    allUrlInfo.push({name: 'manageAuditAuditreport', url:'http://192.168.6.238:8003/loan-audit-store-service/auditreport/'});//经营贷风控报告 测试环境
    allUrlInfo.push({name: 'repairOrderServe', url:'http://192.168.6.238:8017/job-manage-service/'});//工单问题管理相关 测试环境
    allUrlInfo.push({name: 'withdrawService', url:'http://192.168.6.238:8005/loan-withdraw-service/'});//吴伟 放款环节 测试环境
    allUrlInfo.push({name: 'commitmentService', url:'http://192.168.6.238:8005/loan-withdraw-service/commitmentLetter/'});//承诺函 王伟
    allUrlInfo.push({name: 'commitmentServiceUpload', url:'http://192.168.6.238:8005/loan-withdraw-service/uploadFile/'});//承诺函 王伟
    allUrlInfo.push({name: 'withdrawBaseService', url:'http://192.168.6.238:8027/loan-common-service/'});//吴伟 放款基础服务 测试环境
    allUrlInfo.push({name: 'baseConfigPhone', url:'http://192.168.6.238:8019/data-repository-web/phonetitles/'});//号码标签 丁武军
    allUrlInfo.push({name: 'baseConfigBlack', url:'http://192.168.6.238:8019/data-repository-web/blacks/'});//黑名单 丁武军
    allUrlInfo.push({name: 'baseConfigOperations', url:'http://192.168.6.238:8019/data-repository-web/operations/'});//运营商 丁武军
    allUrlInfo.push({name: 'baseConfigPrivacy', url:'http://192.168.6.238:8019/data-repository-web/privacy/'});//移动设备 丁武军
    allUrlInfo.push({name: 'baseConfigLoanApp', url:'http://192.168.6.238:8019/data-repository-web/apps/'});//贷款APP 丁武军
    allUrlInfo.push({name: 'baseConfigBankInfo', url:'http://192.168.6.238:8019/data-repository-web/flows/'});//银行流水 丁武军
    allUrlInfo.push({name: 'baseConfigUserAuth', url:'http://192.168.6.238:8019/data-repository-web/users/'});//用户认证信息 丁武军
    allUrlInfo.push({name: 'baseConfigTaoBaoInfo', url:'http://192.168.6.238:8019/data-repository-web/taobaos/'});//淘宝认证信息 cns
    allUrlInfo.push({name: 'loanAfterManageService', url:'http://192.168.6.238:8011/loan-post-service/'});//贷后管理 陈飞
    /*开发环境 end*/

    /*测试环境 start*/ 
//    allUrlInfo.push({name: 'xfdMessage', url:'http://192.168.6.50:8015/message-service/'});//短信模板 测试环境
//    allUrlInfo.push({name: 'baseConfigProductModel', url:'http://192.168.6.50:8002/loan-audit-service/modelRoleController/'});//产品模型配置 测试环境
//    allUrlInfo.push({name: 'xfdAudit', url:'http://192.168.6.50:8002/loan-audit-service/auditOrderController/'});//消费贷审批4.0 测试环境
//    allUrlInfo.push({name: 'authorizationNew',  url:'http://192.168.6.51:8016/security-service/'});//权限管理 测试环境
//    allUrlInfo.push({name: 'authorizationNewFenDan',  url:'http://192.168.6.51:8016/security-service/'});//权限管理小组 测试环境
//    allUrlInfo.push({name: 'manageAudit', url:'http://192.168.6.50:8003/loan-audit-store-service/'});//经营贷审批 测试
//    allUrlInfo.push({name: 'manageAuditCredit', url:'http://192.168.6.50:8003/loan-audit-store-service/credit/'});//经营贷审批授信 测试环境
//    allUrlInfo.push({name: 'manageAuditAuditreport', url:'http://192.168.6.50:8003/loan-audit-store-service/auditreport/'});//经营贷风控报告 测试环境
//    allUrlInfo.push({name: 'repairOrderServe', url:'http://192.168.6.52:8083/job-manage-service/'});//工单问题管理相关 测试环境
//    allUrlInfo.push({name: 'withdrawService', url:'http://192.168.6.50:8005/loan-withdraw-service/'});//吴伟 放款环节 测试环境
//    allUrlInfo.push({name: 'withdrawBaseService', url:'http://192.168.6.51:8027/loan-common-service/'});//吴伟 放款基础服务 测试环境
//    allUrlInfo.push({name: 'baseConfigPhone', url:'http://192.168.6.50:8106/data-repository-web/phonetitles/'});//号码标签 cns
//    allUrlInfo.push({name: 'baseConfigBlack', url:'http://192.168.6.50:8106/data-repository-web/blacks/'});//黑名单 cns
//    allUrlInfo.push({name: 'baseConfigOperations', url:'http://192.168.6.50:8106/data-repository-web/operations/'});//运营商 cns
//    allUrlInfo.push({name: 'baseConfigPrivacy', url:'http://192.168.6.50:8106/data-repository-web/privacy/'});//移动设备 cns
//    allUrlInfo.push({name: 'baseConfigLoanApp', url:'http://192.168.6.50:8106/data-repository-web/apps/'});//贷款APP cns
//    allUrlInfo.push({name: 'baseConfigBankInfo', url:'http://192.168.6.50:8106/data-repository-web/flows/'});//银行流水 cns
//    allUrlInfo.push({name: 'baseConfigUserAuth', url:'http://192.168.6.50:8106/data-repository-web/users/'});//用户认证信息 cns
//    allUrlInfo.push({name: 'baseConfigTaoBaoInfo', url:'http://192.168.6.50:8106/data-repository-web/taobaos/'});//淘宝认证信息 cns
    /*测试环境 end*/

    /*本地 start*/
//    allUrlInfo.push({name: 'baseConfigPhone', url:'http://192.168.5.203:8080/data-repository-web/phonetitles/'});//号码标签 丁武军
//    allUrlInfo.push({name: 'baseConfigBlack', url:'http://192.168.5.203:8080/data-repository-web/blacks/'});//黑名单 丁武军
//    allUrlInfo.push({name: 'baseConfigOperations', url:'http://192.168.5.203:8080/data-repository-web/operations/'});//运营商 丁武军
//    allUrlInfo.push({name: 'baseConfigPrivacy', url:'http://192.168.5.203:8080/data-repository-web/privacy/'});//移动设备 丁武军
//    allUrlInfo.push({name: 'baseConfigLoanApp', url:'http://192.168.5.203:8080/data-repository-web/apps/'});//贷款APP 丁武军
//    allUrlInfo.push({name: 'baseConfigBankInfo', url:'http://192.168.5.203:8080/data-repository-web/flows/'});//银行流水 丁武军
//    allUrlInfo.push({name: 'baseConfigUserAuth', url:'http://192.168.5.203:8080/data-repository-web/users/'});//用户认证信息 丁武军
//    allUrlInfo.push({name: 'loanAfterManageService', url:'http://192.168.2.110:9080/loan-post-service/'});//贷后管理 陈飞
//    allUrlInfo.push({name: 'commitmentService', url:'http://192.168.1.96:80/commitmentLetter/'});//承诺函 王伟
//    allUrlInfo.push({name: 'commitmentServiceUpload', url:'http://192.168.1.96:80/uploadFile/'});//承诺函 王伟
//    allUrlInfo.push({name: 'withdrawBaseService', url:'http://192.168.2.147:8080/loan-common-service/'});//吴伟 放款基础服务 测试环境
//    allUrlInfo.push({name: 'withdrawService', url:'http://192.168.2.147:8080/loan-withdraw-service/'});//吴伟 放款环节
//    allUrlInfo.push({name: 'withdrawService1', url:'http://192.168.4.194:8080/loan-withdraw-service/'});//吴伟 放款环节
//    allUrlInfo.push({name: 'xfdMessage', url:'http://192.168.1.94:9080/message/'});//短信模板 mouliu
//    allUrlInfo.push({name: 'baseConfigProductModel', url:'http://192.168.1.94:8090/loan-audit-service/modelRoleController/'});//产品模型配置 牟柳
//    allUrlInfo.push({name: 'xfdAudit', url:'http://192.168.2.175:8090/loan-audit/auditOrderController/'});//消费贷审批4.0 牟柳
//    allUrlInfo.push({name: 'authorizationNew',  url:'http://192.168.1.94:9080/security-service/'});//权限管理 mouliu
//    allUrlInfo.push({name: 'authorizationNewFenDan',  url:'http://192.168.1.94:9080/security-service/'});//权限管理小组 测试环境
//    allUrlInfo.push({name: 'manageAudit1', url:'http://192.168.4.194:8080/loan-audit-store-service/'});//经营贷审批 王伟
//    allUrlInfo.push({name: 'manageAuditCredit', url:'http://192.168.1.53:8080/credit/'});//经营贷审批授信 王伟
//    allUrlInfo.push({name: 'manageAuditAuditreport', url:'http://192.168.1.53:8080/auditreport/'});//经营贷风控报告 王伟
//    allUrlInfo.push({name: 'manageAudit', url:'http://192.168.4.175:8080/loan-audit-store-service/'});//经营贷审批 吴伟
//    allUrlInfo.push({name: 'manageAuditCredit', url:'http://192.168.1.246:8080/loan-audit-store-service/credit/'});//经营贷审批授信 吴伟
    /*本地 end*/
    return {
        getUrl: function(module) {
            for (var i=0; i<allUrlInfo.length; i++){
                item = allUrlInfo[i];
                if(item.name.toLowerCase() == module.toLowerCase()){
                    return item.url;
                }
            }
        }
    };
});







