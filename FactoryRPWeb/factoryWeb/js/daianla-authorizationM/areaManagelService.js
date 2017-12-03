authorizationApp.factory('areaManagelService', function($resource, $log, UrlService) {

    /*查询区域列表  start*/
    var allOrderList=[];
    var curHandleOrder = {};

    var queryOrder = function(queryParam, successFunc){
        var OrderInfo = $resource(UrlService.getUrl('authorization') + 'employee/getDepArea');
        OrderInfo.get(queryParam, function(data){
            allOrderList.length = 0;

            if(data.obj.totalCount>=1){
                data.obj.voList.forEach(function(item) {
                    allOrderList.push(item);
                });
            }

            successFunc(data);
        }, function(err){

        });
    };
    /*查询区域列表  end*/
    /************查询所有省 start********/
    var _queryProvince = function(successFunc){
        var queryProvince = $resource(UrlService.getUrl('authorization') + 'employee/getProvinces');
        queryProvince.get(function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /**********查询所有省 end*********/
    /************销售部门 start********/
    var _manager = function(successFunc){
        var manager = $resource(UrlService.getUrl('authorization') + 'employee/getSalesDepartmentList');
        manager.get(function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /**********销售部门 end*********/

    /*根据区域id号查询区域信息 editRole start*/
    var _queryAreaDetail = function(roleId, successFunc, failFunc){
        var AreaDetail = $resource(UrlService.getUrl('authorization') + 'employee/getDepArea');

        return AreaDetail.get({'roleId':roleId}, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*根据区域id号查询区域信息 editRole end*/
    return {
        queryAreaDetail:_queryAreaDetail,
        manager:_manager,
        queryProvince:_queryProvince,
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