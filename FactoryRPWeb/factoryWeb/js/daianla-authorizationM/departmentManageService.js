authorizationApp.factory('departmentManageService', function($resource, $log, UrlService) {
    var allOrderList=[];
    var curHandleOrder = {};
    /*获取部门list start*/
    var queryOrder = function(queryParam, successFunc){
//        var OrderInfo = $resource(UrlService.getUrl('authorization') + 'employee/queryDepartments');
        var OrderInfo = $resource(UrlService.getUrl('factoryServe') + 'department/list');
        OrderInfo.get(queryParam, function(data){
            allOrderList.length = 0;

            if(data.data!=''&&data.data!=null&&data.data!=undefined&&data.errorCode=='000000'){
                data.data.forEach(function(item) {
                    allOrderList.push(item);
                });
            }

            successFunc(data);
        }, function(err){

        });
    };
    /*获取部门list end*/
    return {
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
