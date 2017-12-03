consumerToBankApp.factory('ToBankLoanOrderDetailService', function($resource, $log, UrlService) {
    var allOrderList=[];
    var curHandleOrder = {};

    var queryOrder = function(queryParam, successFunc){
        console.log(queryParam);
        var OrderQuery = $resource(UrlService.getUrl('xfdbaodan') + 'order/queryOrderForType');
        OrderQuery.save(queryParam, function(data){
            allOrderList.length = 0;
//            console.log(data);
//            console.log(queryParam);
            if(data.totalCount>=1){
                data.obj.list.forEach(function(item) {
                    allOrderList.push(item);
                });
            }

            successFunc(data);
        }, function(err){

        });




    };
    /************备注红旗定义 start********/
    var _queryFlagBz = function(params, successFunc){
        var queryFlagBz = $resource(UrlService.getUrl('xfdbaodan') + 'order/editRemark');
        queryFlagBz.save(params,  function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /**********备注红旗定义 end*********/
    /*********转交列表查询 start*******/
    var _deliverList= function(params,successFunc, failFunc){
        var deliverList = $resource(UrlService.getUrl('manageAudit') + 'managerInfo/takeManager');
        return deliverList.save(params,function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*********转交列表查询 end*******/
    /*根据登录人 查询角色 start*/
    //var _queryEmployees = function(needAll, successFunc, failFunc){
    //    var Employees = $resource(UrlService.getUrl('consumerNormal') + 'loan/findOperatorList');
    //
    //    return Employees.get({'needAll':0}, function(data){
    //        if(successFunc){
    //            successFunc(data);
    //        }
    //    }, function(err){
    //        if(failFunc){
    //            failFunc(err);
    //        }
    //    });
    //};
    /*根据登录人 查询角色 end*/
    /************产品列表 start********/
    var _productList = function(successFunc){
        var productList = $resource(UrlService.getUrl('xfdbaodan') + 'product/queryAll');
        productList.get(function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /**********产品列表 end*********/
    /************认证项列表 start********/
    var _queryAuthConfig = function(successFunc){
        var AuthConfig = $resource(UrlService.getUrl('xfdbaodan') + 'loanAuthConfigController/queryLoanAuthConfig');
        AuthConfig.get(function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /**********认证项列表 end*********/

    /*店铺列表 start*/
    var _queryStoryList = function(params,successFunc){
        var queryStoryList = $resource(UrlService.getUrl('manageAudit') + 'shop/ownersAllShops');
        queryStoryList.get(params,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /*店铺列表 end*/
    /* 添加放款条件 start*/
    var _addLoanConditions = function(params, successFunc, failFunc){
        var addCommLoaner = $resource(UrlService.getUrl('manageAudit') + 'loanorder/detail/addtermsloan');

        return addCommLoaner.save(params, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*添加放款条件 end*/
    /*查询放款条件 start*/
    var _queryLoanConditions = function(userId, successFunc, failFunc){
        var queryCommLoaner = $resource(UrlService.getUrl('manageAudit') + 'loanorder/detail/termsloan');

        return queryCommLoaner.get({'userId':userId}, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*查询放款条件 end*/
    /*删除放款条件 start*/
    var _deleteLoanConditions = function(id, successFunc, failFunc){
        var deleteCommLoaner = $resource(UrlService.getUrl('manageAudit') + 'loanorder/detail/deltermsloan');

        return deleteCommLoaner.get({'id':id}, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*删除放款条件 end*/
    /* 添加个人共同借款人（cns） start*/
    var _addPersonalCommonLoaner = function(params, successFunc, failFunc){
        var addPersonalCommonLoaner = $resource(UrlService.getUrl('manageAudit') + 'coBorrower/detail/addcoborrower');

        return addPersonalCommonLoaner.save(params, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*添加个人共同借款人（cns） end*/

    /*查询共同借款人（cns） start*/
    var _queryPersonalCommonLoaner = function(parss, successFunc, failFunc){
        var queryPersonalCommonLoaner = $resource(UrlService.getUrl('manageAudit') + 'coBorrower/detail/coborrower');

        return queryPersonalCommonLoaner.get(parss, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*查询共同借款人（cns） end*/

    /*删除共同借款人（cns） start*/
    var _deletePersonalCommonLoaner = function(id, successFunc, failFunc){
        var deletePersonalCommonLoaner = $resource(UrlService.getUrl('manageAudit') + 'coBorrower/detail/delcoborrower');

        return deletePersonalCommonLoaner.get({'id':id}, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*删除共同借款人（cns） end*/

    /*查询授信结果 start*/
    var _creditResults = function(id, successFunc, failFunc){
//      var creditResults = $resource("http://192.168.1.40:8080/" + 'credit/result');
        var creditResults = $resource(UrlService.getUrl('manageAuditCredit') + 'result');
        return creditResults.get({'id':id}, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*查询授信结果 end*/
    /*获取实际控制人列表（共同借款人+申请人） start*/
    var _queryRealController = function(parss, successFunc, failFunc){
        var queryRealControllers = $resource(UrlService.getUrl('manageAudit') + 'coBorrower/listRealController');

        return queryRealControllers.get(parss, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*获取实际控制人列表（共同借款人+申请人） end*/
    /*查询企业 company/findCompany start*/
    var _queryCompanyInfo = function(parss, successFunc, failFunc){
        var queryCompanyInfos = $resource(UrlService.getUrl('manageAudit') + 'company/findCompany');

        return queryCompanyInfos.save(parss, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*查询企业 end*/
    /*删除企业 start*/
    var _deleteCompany = function(par, successFunc, failFunc){
        var deleteCompanys = $resource(UrlService.getUrl('manageAudit') + 'company/deleteCompany');

        return deleteCompanys.save(par, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*删除企业 end*/
    /*企业（店铺信息）查询接口-审批后台 company/findCompany start*/
    var _queryWithdrawCompanyInfo = function(parss, successFunc, failFunc){
        var queryWithdrawCompanyInfos = $resource(UrlService.getUrl('withdrawService') + 'ccbShareHolder/findCcbElaonData');

        return queryWithdrawCompanyInfos.get(parss, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*企业（店铺信息）查询接口-审批后台 end*/
    /*股东信息接口(值修改股东信息) start*/
    var _updateShareHolders = function(parss, successFunc, failFunc){
        var updateShareHolders = $resource(UrlService.getUrl('withdrawService') + 'ccbShareHolder/modifyShareHolders');

        return updateShareHolders.post(parss, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*股东信息接口(值修改股东信息) end*/
    /*放款环节-审批提交 start*/
    var _auditSubmitOrder = function(parss, successFunc, failFunc){
        var auditSubmitOrder = $resource(UrlService.getUrl('withdrawService') + 'ccbShareHolder/auditSubmit');

        return auditSubmitOrder.post(parss, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };

    /*放款环节-审批提交 end*/
    /*放款环节 详情页备注列表查询 start*/
    var _queryRemarkList = function(parss, successFunc, failFunc){
        var queryRemarkLists = $resource(UrlService.getUrl('withdrawService') + 'withdrawOrderRemark/listRemarkForOrder');

        return queryRemarkLists.get(parss, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*放款环节 详情页备注列表查询 end*/
    /*放款环节 添加备注 start*/
    var _addRemark = function(parss, successFunc, failFunc){
        var addRemarks = $resource(UrlService.getUrl('withdrawService') + 'withdrawOrderRemark/writeRemark');

        return addRemarks.save(parss, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*放款环节 添加备注 end*/
    /*添加股东 start*/
    var _addLoanShareHolder = function(userId,sellerNick,pa, successFunc, failFunc){
        var LoanShareHolders = $resource(UrlService.getUrl('manageAudit') + 'company/addShareHolders/'+userId+'/'+sellerNick);

        return LoanShareHolders.save(pa,function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*添加股东 end*/
    /*删除股东 start*/
    var _deleteLoanShareHolder = function(userId,sellerNick,shareHolderName, successFunc, failFunc){
        var deleteLoanShareHolders = $resource(UrlService.getUrl('manageAudit') + 'company/addShareHolders/'+userId+'/'+sellerNick+'/'+shareHolderName);

        return deleteLoanShareHolders.save(function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*删除股东 end*/
    /*获取店铺指标 manageAudit1 start*/
    var _findShopItem = function(par, successFunc, failFunc){
        var findShopItems = $resource(UrlService.getUrl('manageAudit') + 'company/findShopItems');

        return findShopItems.save(par,function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*获取店铺指标 end*/
    /*确认推送建行信息 start*/
    var _saveConfirmCcbPush = function(par, successFunc, failFunc){
        var saveConfirmCcbPushs = $resource(UrlService.getUrl('withdrawService') + 'ccbShareHolder/saveConfirmCcbPushInfo');

        return saveConfirmCcbPushs.save(par,function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*确认推送建行信息 end*/
    return {
        productList:_productList,
        queryFlagBz: _queryFlagBz,
        deliverList: _deliverList,
        queryAuthConfig: _queryAuthConfig,
        queryStoryList: _queryStoryList,
        addLoanConditions: _addLoanConditions,
        queryLoanConditions: _queryLoanConditions,
        addPersonalCommonLoaner: _addPersonalCommonLoaner,
        deleteLoanConditions: _deleteLoanConditions,
        queryPersonalCommonLoaner: _queryPersonalCommonLoaner,
        deletePersonalCommonLoaner: _deletePersonalCommonLoaner,
        queryRealController: _queryRealController,
        queryCompanyInfo: _queryCompanyInfo,
        queryWithdrawCompanyInfo: _queryWithdrawCompanyInfo,
        updateShareHolders: _updateShareHolders,
        auditSubmitOrder: _auditSubmitOrder,
        deleteCompany: _deleteCompany,
        creditResults:_creditResults,
        queryRemarkList:_queryRemarkList,
        addLoanShareHolder:_addLoanShareHolder,
        deleteLoanShareHolder:_deleteLoanShareHolder,
        findShopItem:_findShopItem,
        saveConfirmCcbPush:_saveConfirmCcbPush,
        addRemark:_addRemark,
//        productName:_productName,
//        queryEmployees:_queryEmployees,
        queryOrder: function(queryParam, successFunc) {
            queryOrder(queryParam, successFunc);

        },

        getOrderList: function() {
            return  allOrderList;
        },

        getCurHandleOrder: function() {
            return  curHandleOrder;
        },

        setCurHandleOrder: function(order) {
            curHandleOrder = order;
        }
    };
});


