consumerToBankApp.factory('needSendToBankLoanListService', function($resource, $log, UrlService) {
    var allOrderList=[];
    var curHandleOrder = {};

    var queryOrder = function(queryParam, successFunc){
        console.log(queryParam);
        var OrderQuery = $resource(UrlService.getUrl('xfdbaodan') + 'loanOrderBankController/queryEntities');
        OrderQuery.save(queryParam, function(data){
            allOrderList.length = 0;
//            console.log(data);
//            console.log(queryParam);
            if(data.obj.totalCount>=1){
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
    /*根据登录人 查询角色 start*/
//    var _queryEmployees = function(needAll, successFunc, failFunc){
//        var Employees = $resource(UrlService.getUrl('consumerNormal') + 'loan/findOperatorList');
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
    return {
        productList:_productList,
        queryFlagBz: _queryFlagBz,
        deliverList: _deliverList,
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