authorizationApp.factory('functionManageService', function($resource, $log, UrlService) {


    var allOrderList=[];
    var curHandleOrder = {};

    var queryOrder = function(queryParam, successFunc){
//        alert(1);
//        var OrderInfo = $resource(UrlService.getUrl('authorization') + 'employee/queryPermissions');
        var OrderInfo = $resource(UrlService.getUrl('authorizationNew') + 'queryCorporateMenu');
        OrderInfo.get(queryParam, function(data){
//            alert(data);
            allOrderList.length = 0;
            if(data.errorCode=='000000'&&data.data!=null&&data.data!=''&&data.data){
                data.data.forEach(function(item) {
                    allOrderList.push(item);
//                    console.log(allOrderList)
                });
            }

            successFunc(data);
        }, function(err){

        });
    };

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
