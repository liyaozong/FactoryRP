consumerToBankApp.factory('ToBankHadSubmitListService', function($resource, $log, UrlService) {
    var allOrderList=[];
    var curHandleOrder = {};

    var queryOrder = function(queryParam, successFunc){
        console.log(queryParam);
        var OrderQuery = $resource(UrlService.getUrl('consumerNormal') + 'loan/viewAllUpgradeOrderList');
        OrderQuery.save(queryParam, function(data){
            allOrderList.length = 0;
//            console.log(data);
//            console.log(queryParam);
            if(data.totalCount>=1){
                data.obj.forEach(function(item) {
                    if(item.userPhone==''||item.userPhone==null||item.userPhone==undefined){
                        item.userPhone='';
                    }else {
                        item.userPhone = item.userPhone.substring(0, 3) + '****'+item.userPhone.substring(7, 11);//手机号加密显示
                    }
                    if(item.userName==''||item.userName==null||item.userName==undefined){
                        item.userName='';
                    }else {
                        item.userName = item.userName.substr(0,item.userName.length-1) + '*';//用户姓名加密
                    }
                    allOrderList.push(item);
                });
            }

            successFunc(data);
        }, function(err){

        });
    };

    /************备注红旗定义 start********/
    var _queryFlagBz = function(params, successFunc){
        var queryFlagBz = $resource(UrlService.getUrl('consumerNormal') + 'loan/addOrderRemark');
        queryFlagBz.get(params,  function (data) {
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
    /**********产品名称 end*********/
    /*根据登录人 查询角色 start*/
    var _queryEmployees = function(needAll, successFunc, failFunc){
        var Employees = $resource(UrlService.getUrl('consumerNormal') + 'loan/findOperatorList');

        return Employees.get({'needAll':1}, function(data){
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
    return {
        queryFlagBz: _queryFlagBz,
//        productName:_productName,
        queryEmployees:_queryEmployees,
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
