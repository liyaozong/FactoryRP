consumerToBankApp.factory('ToBankOrderDetailService', function($resource, $log, UrlService) {
    //当前操作的订单
    var curHandleOrder = null;
    //店铺列表
    var storeListInfos = [];
    //店铺认证列表
    var storeAuthList = [];

    var allDataInfo = {};

    var _getStoreListInfo =  function() {
        return storeListInfos;
    };

    var _getAllDataInfo =  function() {
        return allDataInfo;
    };

    var _setCurHandleOrder = function(order){
        if(order != curHandleOrder){
            destory();
        }
        curHandleOrder = order;
    };

    var _getCurHandleOrder = function(order){
        return curHandleOrder;
    };

    var destory = function(){
        storeListInfos = [];
        storeAuthList = [];
        allDataInfo = {};
    };

    var _init = function(){
        destory();
    };
    /*根据用户id号查询出被执行人、文书网上的信息 end*/
//    var allOrderList=[];
//    var queryOrder = function(queryParam,successFunc){
//        console.log(queryParam);
////        var OrderQuery = $resource(UrlService.getUrl('bankServeAddress') + 'transactionFlow/debtList');
//        var queryOrder = $resource('http://192.168.6.200:9076/daianla_company/courtDecisionMessageSeachController/seachMessageByCourtDecision');
//        queryOrder.get(queryParam, function(data){
//            allOrderList.length = 0;
////            console.log(data);
////            console.log(queryParam);
//            if(data.success==true&&data.obj!=null){
//                data.obj.forEach(function(item) {
//                    allOrderList.push(item);
////                    console.log(item);
//                });
//            }
//
//            successFunc(data);
//        }, function(err){
//
//        });
//    };
//    var _queryBaseInfo = function(userId, successFunc, failFunc){
//        var baseInfo = $resource(UrlService.getUrl('operation') + 'declarationController/selectSimpleCustomerInfo');
//
//        return baseInfo.get({'userId':userId}, function(data){
//            if(successFunc){
//                successFunc(data);
//            }
//        }, function(err){
//            if(failFunc){
//                failFunc(err);
//            }
//        });
//    };
    /*个人资料列表 start*/
    //var _queryPersonInfo = function(orderId, successFunc, failFunc){
    //    var PersonInfo = $resource(UrlService.getUrl('operation') + 'declarationController/selectSimpleUserAuthInfoList');
    //
    //    return PersonInfo.get({'orderId':curHandleOrder.id}, function(data){
    //        if(successFunc){
    //            successFunc(data);
    //        }
    //    }, function(err){
    //        if(failFunc){
    //            failFunc(err);
    //        }
    //    });
    //};
    /*个人资料列表 end*/
    /*当前订单所有备注信息查询 start*/
    //var _queryOrderRemark = function(orderId, successFunc, failFunc){
    //    var OrderRemark = $resource(UrlService.getUrl('LoanOrder') + 'orderLog/queryLogByOrderId');
    //
    //    return OrderRemark.get({'orderId':curHandleOrder.id}, function(data){
    //        if(successFunc){
    //            successFunc(data);
    //        }
    //    }, function(err){
    //        if(failFunc){
    //            failFunc(err);
    //        }
    //    });
    //};
    /*当前订单所有备注信息查询 end*/
    /*订单状态 start*/
    //var _queryOrderStatus = function(orderId, successFunc, failFunc){
    //    var orderStatus = $resource(UrlService.getUrl('operation') + 'declarationController/selectOrderStatusTitleBar');
    //
    //    return orderStatus.get({'orderId':curHandleOrder.id}, function(data){
    //        if(successFunc){
    //            successFunc(data);
    //        }
    //    }, function(err){
    //        if(failFunc){
    //            failFunc(err);
    //        }
    //    });
    //};
    /*订单状态 end*/


    /*根据登录人 查询角色 start*/
    var _queryEmployees = function(orderId, successFunc, failFunc){
        var Employees = $resource(UrlService.getUrl('authorization') + 'user/getEmployeesByCurrentId');

        return Employees.get({}, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*根据登录人 查询角色 end*/

    /*********银行列表 start*******/
//    var _queryBankList= function(successFunc, failFunc){
//        var BankList = $resource(UrlService.getUrl('consumerNormal') + 'loan/viewAllBankOrg');
////        var BankList = $resource('http://192.168.6.24:8082/web/loan/viewAllBankOrg');
//
//        return BankList.save(function(data){
//            if(successFunc){
//                successFunc(data);
//            }
//        }, function(err){
//            if(failFunc){
//                failFunc(err);
//            }
//        });
//    }
    /*********银行列表 end*******/
    /*根据登录人 查询角色 start*/
//    var _queryEmployees = function(successFunc, failFunc){
//        var Employees = $resource(UrlService.getUrl('consumerNormal') + 'loan/viewAllRiskControlHandler');
////        var Employees = $resource('http://192.168.6.24:8082/web/loan/viewAllRiskControlHandler');
//
//        return Employees.get(function(data){
//            if(successFunc){
//                successFunc(data);
//            }
//        }, function(err){
//            if(failFunc){
//                failFunc(err);
//            }
//        });
//    };
    /*根据登录人 查询角色 end*/
    /*查看客户异常信息 start*/
//    var _queryUserAbnormal = function(userId, successFunc, failFunc){
//        var UserAbnormal = $resource(UrlService.getUrl('consumerNormal') + 'loan/viewUserAbnormalInfo');
////        var UserAbnormal = $resource('http://192.168.6.27:8082/consumption-audit-web/web/loan/viewUserAbnormalInfo');
//        return UserAbnormal.save({'userId':userId}, function(data){
//            if(successFunc){
//                successFunc(data);
//            }
//        }, function(err){
//            if(failFunc){
//                failFunc(err);
//            }
//        });
//    };
    /*查看客户异常信息 end*/

    /*********文件类型列表 start*******/
    //var _queryFileTypeList= function(successFunc, failFunc){
    //    var FileTypeList = $resource(UrlService.getUrl('bankServeAddress') + 'sysConfig/fileTypeList');
    //    return FileTypeList.save(function(data){
    //        if(successFunc){
    //            successFunc(data);
    //        }
    //    }, function(err){
    //        if(failFunc){
    //            failFunc(err);
    //        }
    //    });
    //};
    /*查看客户反欺诈异常信息 start*/
//    var _queryAntiFraudInfo = function(userId, successFunc, failFunc){
//        var AntiFraudInfo = $resource(UrlService.getUrl('consumerNormal') + 'loan/CreditDataVaildate/viewAntiFraudInfo');
////        var UserAbnormal = $resource('http://192.168.6.27:8082/consumption-audit-web/web/loan/viewUserAbnormalInfo');
//        return AntiFraudInfo.save({'userId':userId}, function(data){
//            if(successFunc){
//                successFunc(data);
//            }
//        }, function(err){
//            if(failFunc){
//                failFunc(err);
//            }
//        });
//    };
    /*查看客户反欺诈异常信息 end*/
    /*查看认证项列表 start order/queryOrderInfo*/
    var _queryAuditListInfo = function(userId, successFunc, failFunc){
        var AuditListInfo = $resource(UrlService.getUrl('baseConfigUserAuth') + 'listUserAuthItemInfosByUserId');
//        var UserAbnormal = $resource('http://192.168.6.27:8082/consumption-audit-web/web/loan/viewUserAbnormalInfo');
        return AuditListInfo.get({'userId':userId}, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*查看认证项列表 end*/

    /*********备注列表查询 start*******/
    var _queryRemarkList= function(orderId, successFunc, failFunc){
        var RemarkList = $resource(UrlService.getUrl('xfdbaodan') + 'orderLog/queryLogByOrderId');
//        var RemarkList = $resource('http://192.168.6.24:8082/web/loan/viewUpgradeOrderRemark');

        return RemarkList.save({'orderId':orderId}, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    }
    /*********备注列表查询 end*******/
    /*********查询银行列表 start*******/
    var _queryBankListS= function(orderId,successFunc, failFunc){
        var BankLists = $resource(UrlService.getUrl('xfdbaodan') + 'organizationList/queryAll');
        return BankLists.get({"orderId":orderId},function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    }
    /*********查询银行列表 end*******/
    /*********转交列表查询 start*******/
    var _deliverList= function(type,successFunc, failFunc){
        var deliverList = $resource(UrlService.getUrl('xfdbaodan') + 'organizationList/queryAllUser');
        return deliverList.get({"type":true},function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    }
    /*********转交列表查询 end*******/
    /************查询消费贷通话记录 start********/
    var _filterCallRecord = function(paragrams,successFunc){
        var filterCallRecords = $resource(UrlService.getUrl('xfdbaodan') + 'privacy/queryCallRecord');
        filterCallRecords.save(paragrams,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /**********查询消费贷通话记录 end*********/
    /************查询消费贷短信记录 start********/
    var _filterMessageRecord = function(paragrams,successFunc){
        var filterMessageRecords = $resource(UrlService.getUrl('xfdbaodan') + 'privacy/queryUserSms');
        filterMessageRecords.save(paragrams,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /**********查询消费贷短信记录 end*********/

    /************查询消费贷手机联系人 start********/
    var _filterContractRecord = function(paragrams,successFunc){
        var filterContractRecords = $resource(UrlService.getUrl('xfdbaodan') + 'privacy/queryContactInfo');
        filterContractRecords.save(paragrams,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /**********查询消费贷手机联系人 end*********/

    /************查询消费贷地理位置 start********/
    var _filterLocationRecord = function(paragrams,successFunc){
        var filterLocationRecords = $resource(UrlService.getUrl('xfdbaodan') + 'privacy/queryAddressInfo');
        filterLocationRecords.save(paragrams,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /**********查询消费贷地理位置 end*********/
    /*查看客户人脸相关信息 start */
    var _viewIdCardInformation = function(par,successFunc){
        var viewIdCardInformation = $resource(UrlService.getUrl('xfdbaodan') + 'web/loan/viewIdCardInfo');
        viewIdCardInformation.get(par,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /*查看客户人脸相关信息 end */
    /*工资流水详情 start */
    var _salaryWateringRun = function(parsd,successFunc){
        var salaryWateringRun = $resource(UrlService.getUrl('xfdbaodan') + 'bankFlowController/queryBankFlowRecord');
        salaryWateringRun.save(parsd,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /*工资流水详情 end */
    /*工资流水概要 start */
    var _salaryWateringAb = function(pas,successFunc){
        var salaryWateringAb = $resource(UrlService.getUrl('xfdbaodan') + 'bankFlowController/queryBankFlowMonthDetail');
        salaryWateringAb.save(pas,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /*工资流水概要 end */
    /*重新检验反欺诈规则 start */
    var _aganistcheckout = function(userId,successFunc){
        var aganistcheckout = $resource(UrlService.getUrl('xfdbaodan') + 'web/loan/reSendFraudRuleCheckMsg');
        aganistcheckout.get({"userId":userId},function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /*重新检验反欺诈规则 end */
    /*查询设备信息 start */
    var _queryEquipment = function(userId,successFunc){
        var queryEquipment = $resource(UrlService.getUrl('xfdbaodan') + 'web/loan/getDeviceNameByUserId');
        queryEquipment.get({"userId":userId},function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /*查询设备信息 end */
    /*查询隐私数据 start */
    var _queryPrivacy = function(parr,successFunc){
        var queryPrivacyS = $resource(UrlService.getUrl('xfdbaodan') + 'web/loan/queryUserMainInfo');
        queryPrivacyS.save(parr,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /*查询隐私数据 end */
    /*查询电话标签 start */
    var _queryPhoneLabel = function(fuzzyTitle,successFunc){
        var queryPhoneLabels = $resource(UrlService.getUrl('baseConfigPhone') + 'titles');
        queryPhoneLabels.save({"fuzzyTitle":fuzzyTitle},function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /*查询电话标签 end */
    /*查询运营商通话记录 start */
    var _operatorRecord = function(oprecords,successFunc){
        var operatorRecords = $resource(UrlService.getUrl('xfdbaodan') + 'web/loan/queryOperatorsCalls');
        operatorRecords.save(oprecords,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /*查询运营商通话记录 end */
    /*查询运营商短信记录 start */
    var _operatorMsgRecord = function(operatorMsg,successFunc){
        var operatorMsgRecords = $resource(UrlService.getUrl('xfdbaodan') + 'web/loan/queryOperatorsSmses');
        operatorMsgRecords.save(operatorMsg,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /*查询运营商短信记录 end */
    /*查询用户卡银行卡号 start */
    var _queryBankcard = function(userId,successFunc){
        var queryBankcardS = $resource(UrlService.getUrl('xfdbaodan') + 'basicDataController/queryUserBankCardNo');
        queryBankcardS.get({"userId":userId},function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /*查询用户卡银行卡号 end */
    /*sy--客户填写公司名称注册情况列表 start */
    var _queryCompanyInformation = function(userId,successFunc){
        var queryCompanyInformation = $resource(UrlService.getUrl('xfdbaodan') + 'basicDataController/queryCompanyInformation');
        queryCompanyInformation.get(userId,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /*sy--客户填写公司名称注册情况列表 end */
    /*sy--客户填写公司名称公司验证 start */
    var _checkCompany = function(userId,successFunc){
        var checkCompany = $resource(UrlService.getUrl('xfdbaodan') + 'basicDataController/checkCompany');
        checkCompany.get(userId,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /*sy--客户填写公司名称公司验证 end */
    /*查询用户基本信息 start*/
    var _queryUserBase = function(userId,successFunc){
        var queryUserBases = $resource(UrlService.getUrl('baseConfigUserAuth') + 'info');
        queryUserBases.get({'userId':userId},function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /*查询用户基本信息 end*/
    return {
        init:_init,
        queryOrder: function(queryParam, successFunc) {
            queryOrder(queryParam, successFunc);

        },
        getOrderList: function() {
            return  allOrderList;
        },
        setCurHandleOrder:_setCurHandleOrder,
        getCurHandleOrder:_getCurHandleOrder,
        viewIdCardInformation:_viewIdCardInformation,
        //queryAntiFraudInfo:_queryAntiFraudInfo,
        getStoreListInfo:_getStoreListInfo,
        queryUserBase:_queryUserBase,
        //queryBaseInfo:_queryBaseInfo,
        filterCallRecord:_filterCallRecord,
        filterMessageRecord:_filterMessageRecord,
        filterContractRecord:_filterContractRecord,
        filterLocationRecord:_filterLocationRecord,
        getAllDataInfo:_getAllDataInfo,
        //queryPersonInfo:_queryPersonInfo,
        //queryOrderStatus:_queryOrderStatus,
        //queryOrderRemark:_queryOrderRemark,
        queryRemarkList:_queryRemarkList,
        //queryBankList:_queryBankList,
        queryBankListS:_queryBankListS,
        //queryFileTypeList:_queryFileTypeList,
        //queryUserAbnormal:_queryUserAbnormal,
        queryEmployees:_queryEmployees,
        deliverList:_deliverList,
        queryAuditListInfo:_queryAuditListInfo,
        salaryWateringRun:_salaryWateringRun,
        salaryWateringAb:_salaryWateringAb,
        aganistcheckout:_aganistcheckout,
        queryEquipment:_queryEquipment,
        queryPrivacy:_queryPrivacy,
        queryPhoneLabel:_queryPhoneLabel,
        operatorRecord:_operatorRecord,
        operatorMsgRecord:_operatorMsgRecord,
        queryBankcard:_queryBankcard,
        queryCompanyInformation:_queryCompanyInformation,
        checkCompany:_checkCompany
    };
});


