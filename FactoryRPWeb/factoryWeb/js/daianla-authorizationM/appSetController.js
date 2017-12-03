/**
 * Created by caolongping on 2016/5/18.
 */
authorizationApp.controller('appSetController',function ($scope,$http,UrlService,$filter, $resource, $location, $state,appSetService) {
//配置分页基本参数
//    alert(1)
    $scope.WebURL=UrlService.getUrl('LoanUser');
    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 15
    };
    $scope.orderList = appSetService.getOrderList();
//    console.log(123);
//    console.log($scope.orderList);
    /*查询所有敏感词 start*/

    $scope.queryAllwords=function () {
//        console.log($scope.Date);
        appSetService.queryOrder({
            msg:$scope.QueryName,
            currentPage: $scope.paginationConf.currentPage,
            itemsPerPage: $scope.paginationConf.itemsPerPage
        }, function(response){
            if(response.obj.totalCount>=1){
                $scope.paginationConf.totalItems = response.obj.totalCount;
            }else{
                $scope.paginationConf.totalItems = 0;
                $scope.orderList.length = 0;
            }
        });
    };
    /*查询所有敏感词 end*/



    /*新增 敏感词 start addfunctionBalance*/

    $scope.addfunctionBalance=function(){
        if($scope.addName==null||$scope.addName==''||$scope.addName==undefined){
            $("#addName").focus();
        }else{
            var pa={
                msg:$scope.addName
            };
            var par={params:pa};
            $http.get($scope.WebURL+'pdSensitiveController/save',par).success(function(response){
                hideDiv("functionBalance");
                popupDiv('SaveSuccess');
                $(".Message").html(response.message);
            }).error(function(response){
                console.log(response.message);
            });
        }
    };
    /*新增 敏感词 end addfunctionBalance*/
    /*删除 敏感词 start deleteFunction*/
    $scope.deletefunctionBalance=function(res){
        popupDiv('deleteFunction');
        var pa={
            id:res.id
        };
        var par={params:pa};
//        console.log(121121);
//        console.log(pa);
        $scope.deleteFunction=function(){
            $http.get($scope.WebURL+'pdSensitiveController/deleteById',par).success(function(response){
                hideDiv("deleteFunction");
                popupDiv('SaveSuccess');
                $(".Message").html(response.message);
            }).error(function(response){
                console.log(response.message);
            });
        };
    };
    /*删除 敏感词 end deleteFunction*/


    /***************************************************************
     当页码和页面记录数发生变化时监控后台查询
     如果把currentPage和itemsPerPage分开监控的话则会触发两次后台事件。
     ***************************************************************/
    $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.queryAllwords);
});