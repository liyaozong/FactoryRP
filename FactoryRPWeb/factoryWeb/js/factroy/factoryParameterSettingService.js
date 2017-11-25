factoryParameterSettingApp.factory('factoryParameterSettingService', function($resource, $log, UrlService) {
    var allOrderList=[];
    var curHandleOrder = {};

    var queryOrder = function(queryParam, successFunc){
        console.log(queryParam);
        var OrderQuery = $resource(UrlService.getUrl('withdrawService') + 'order/listOrderForAudit');
        OrderQuery.save(queryParam, function(data){
            allOrderList.length = 0;
//            console.log(data);
//            console.log(queryParam);
            if(data.status==0&&data.obj.totalCount>=1){
                data.obj.dataItem.forEach(function(item) {
                    allOrderList.push(item);
                });
            }

            successFunc(data);
        }, function(err){

        });
    };
    /*从总配置中查询优啦产品信息和渠道产品信息 start withdrawBaseService*/
    var _queryListProduct = function(params, successFunc){
        var listProduct = $resource(UrlService.getUrl('withdrawBaseService') + 'config/listProduct');
        listProduct.get(params,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*从总配置中查询优啦产品信息和渠道产品信息 end withdrawBaseService*/
    /* 查询订单列表状态 start*/
    var _queryListOrderStatus = function(params, successFunc){
        var listOrderStatus = $resource(UrlService.getUrl('withdrawService') + 'order/listOrderStatus');
        listOrderStatus.get(params,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /* 查询订单列表状态 end*/
    /************备注红旗定义 start********/
    var _queryFlagBz = function(params, successFunc){
        var queryFlagBz = $resource(UrlService.getUrl('withdrawService') + 'order/updateMark');
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
    /*根据登录人 查询角色 start*/
//    var _queryEmployees = function(needAll, successFunc, failFunc){
//        var Employees = $resource(UrlService.getUrl('consumerNormal') + 'loan/findOperatorList');
////        var Employees = $resource('http://192.168.6.34:8085/consumption-audit-web/web/loan/findOperatorList');
//
//        return Employees.get({'needAll':0}, function(data){
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
    };
    /*********转交列表查询 end*******/
    /*根据登录人 查询部门 start*/
//    var _queryDepName = function(queryParam, successFunc, failFunc){
//        var DepNames = $resource(UrlService.getUrl('LoanUser') + 'employee/queryEmployees');
////        var Employees = $resource('http://192.168.6.34:8085/consumption-audit-web/web/loan/findOperatorList');
//
//        return DepNames.get(queryParam, function(data){
//            if(successFunc){
//                successFunc(data);
//            }
//        }, function(err){
//            if(failFunc){
//                failFunc(err);
//            }
//        });
//    };
    /*根据登录人 查询部门 end*/
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

    return {
        productList:_productList,
        queryFlagBz: _queryFlagBz,
        deliverList: _deliverList,
        queryListProduct: _queryListProduct,
        queryListOrderStatus: _queryListOrderStatus,
        queryAuthConfig: _queryAuthConfig,
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
