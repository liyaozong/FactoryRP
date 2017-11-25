authorizationApp.factory('departmentManageService', function($resource, $log, UrlService) {
    var allOrderList=[];
    var curHandleOrder = {};
    /*获取部门list start*/
    var queryOrder = function(queryParam, successFunc){
//        var OrderInfo = $resource(UrlService.getUrl('authorization') + 'employee/queryDepartments');
        var OrderInfo = $resource(UrlService.getUrl('authorizationNew') + 'department');
        OrderInfo.get(queryParam, function(data){
            allOrderList.length = 0;

            if(data.obj.totalCount>=1){
                data.obj.departmentVos.forEach(function(item) {
                    allOrderList.push(item);
                    console.log(allOrderList)
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
