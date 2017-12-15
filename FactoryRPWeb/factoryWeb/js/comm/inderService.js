//分页业务类
myApp.factory('quService', function ($http) {
    var curHandleOrder = null;

    var list1 = function (postData1) {
        var pa1 = {params: postData1};
        return $http.get('http://192.168.6.204:8080/daianla-order-web/orderController/selectDeclarationOrderList', pa1);
    };

    var _setCurOrder = function(order) {
        curHandleOrder = order;
    };

    var _getCurOrder = function()
    {
        return curHandleOrder;
    }

    return {
        list1: function (postData1)
        {
            return list1(postData1);

        },
        setCurOrder:_setCurOrder,
        getCurOrder:_getCurOrder
    };
})
