authorizationApp.factory('repairOrderModelService', function($resource, $log, UrlService) {


    var allOrderList=[];
    var curHandleOrder = {};

    var queryOrder = function(queryParam, successFunc){
//        alert(1);
        var OrderInfo = $resource(UrlService.getUrl('repairOrderServe') + 'workOrderManager/listWorkTemplate');
        OrderInfo.get(queryParam, function(data){
//            alert(data);
            allOrderList.length = 0;

            if(data.obj!=null&&data.status==0){
                data.obj.forEach(function(item) {
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
