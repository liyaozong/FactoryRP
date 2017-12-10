/**
 * Created by jayvenLee on 2017/11/11.
 */
authorizationApp.controller('repairOrderModelController',function ($scope,$http,UrlService,$filter, $resource, $location, $state,repairOrderModelService) {
//配置分页基本参数
//    alert(1)
    $scope.WebURL=UrlService.getUrl('repairOrderServe');
    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 15
    };
    $scope.orderList = repairOrderModelService.getOrderList();
//    console.log($scope.orderList);
    /*查询所有用户 start*/
    $scope.onQuery=function () {
        repairOrderModelService.queryOrder({
            name:$scope.QueryName,
            currentPage: $scope.paginationConf.currentPage,
            itemsPerPage: $scope.paginationConf.itemsPerPage
        }, function(response){
            if(response.totalCount>=1){
                $scope.paginationConf.totalItems = response.totalCount;
            }else{
                $scope.paginationConf.totalItems = 0;
                $scope.orderList.length = 0;
            }
        });
    };
    /*查询所有用户 end*/

    /*编辑 功能点 start editfunctionBalance*/
    $scope.editfunctionBalance=function(res){
        popupDiv('editfunctionBalance');
        $scope.id=res.id;
        $scope.editModelName=res.name;
        $scope.editStatusList=res.statusList.join(' ');
        $scope.editRemark=res.remark;
        /*根据ID 保存当前信息 start updateSaveFunction*/
        $scope.updateSaveFunction=function(){
            if($scope.editModelName==null||$scope.editModelName==''||$scope.editModelName==undefined){
                $("#editModelName").focus();
            }else if($scope.editStatusList==null||$scope.editStatusList==''||$scope.editStatusList==undefined){
                $("#editStatusList").focus();
            }else{
                $scope.editStatusList=$scope.editStatusList.split(' ');
                var pa={
                    id:res.id,
                    name:$scope.editModelName,
                    remark:$scope.editRemark,
                    statusList:$scope.editStatusList
                };
                var par={params:pa};
                $http.post($scope.WebURL+'workOrderManager/workOrderTemplteAdd',pa).success(function(response){
                    hideDiv("editfunctionBalance");
                    popupDiv('SaveSuccess');
                    $(".Message").html(response.message);
                }).error(function(response){
                    console.log(response.message);
                });
            }
        };
        /*根据ID 保存当前信息 end updateSaveFunction*/

    };
    /*编辑 功能点 end editfunctionBalance*/
    /*新增 功能点 start addfunctionBalance*/
    $scope.addfunctionBalance=function(){
        if($scope.modelName==null||$scope.modelName==''||$scope.modelName==undefined){
            $("#modelName").focus();
        }else if($scope.statusList==null||$scope.statusList==''||$scope.statusList==undefined){
            $("#statusList").focus();
        }else{
            $scope.statusList=$scope.statusList.split(' ');
            var pa={
                name:$scope.modelName,
                remark:$scope.remark,
                statusList:$scope.statusList
            };
            var par={params:pa};
            $http.post($scope.WebURL+'workOrderManager/workOrderTemplteAdd',pa).success(function(response){
                hideDiv("functionBalance");
                popupDiv('SaveSuccess');
                $(".Message").html(response.message);
            }).error(function(response){
                console.log(response.message);
            });
        }
    };
    /*新增 功能点 end addfunctionBalance*/
    /*删除 功能点 start deleteFunction*/
    $scope.deletefunctionBalance=function(res){
        popupDiv('deleteFunction');
        $scope.modelNameShow=res.name;
        var pa={
            code:res.id
        };
        var par={params:pa};
//        console.log(121121);
//        console.log(pa);
        $scope.deleteFunction=function(){
            $http.get($scope.WebURL+'workOrderManager/removeWorkOrderTemplte/'+res.id).success(function(response){
                hideDiv("deleteFunction");
                popupDiv('SaveSuccess');
                $(".Message").html(response.message);
            }).error(function(response){
                console.log(response.message);
            });
        };
    };
    /*删除 功能点 end deleteFunction*/

    /*处理之后保留在当前页面start*/
    $scope.reload=function(){
        $scope.paginationConf.currentPage = $(".page-total input").val();
        $scope.onQuery();
    };
    /*处理之后保留在当前页面end*/

    /***************************************************************
     当页码和页面记录数发生变化时监控后台查询
     如果把currentPage和itemsPerPage分开监控的话则会触发两次后台事件。
     ***************************************************************/
    $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.onQuery);
});