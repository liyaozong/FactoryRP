/**
 * Created by caolongping on 2016/5/18.
 */
authorizationApp.controller('functionManageController',function ($scope,$http,UrlService,$filter, $resource, $location,$cookies, $state,functionManageService) {
//配置分页基本参数
//    alert(1)
//    $scope.WebURL=UrlService.getUrl('authorization');
    $scope.WebURL=UrlService.getUrl('authorizationNew');
//    $scope.corporateIdentify=$cookies.get('corporateIdentify');
    $scope.requestSeqNo=$cookies.get('requestSeqNo');
    $scope.corporateIdentify='32132132132213';
    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 15
    };
    $scope.orderList = functionManageService.getOrderList();
//    console.log(123);
//    console.log($scope.orderList);
    /*查询所有用户 start*/
    $scope.onQuery=function () {
        $scope.Date=$filter('date')($scope.createdDate,'yyyy-MM-dd');;
//        console.log($scope.Date);
        functionManageService.queryOrder({
            keyword:$scope.QueryName,
            corporateIdentify:$scope.corporateIdentify
//            pageNo: $scope.paginationConf.currentPage,
//            pageSize: $scope.paginationConf.itemsPerPage
        }, function(response){
//            if(response.obj.totalCount>=1){
//                $scope.paginationConf.totalItems = response.obj.totalCount;
//            }else{
//                $scope.paginationConf.totalItems = 0;
//                $scope.orderList.length = 0;
//            }

            if(response.data.length>=1){
//                $scope.paginationConf.totalItems = response.obj.rowCount;
                $scope.orderList=response.data;
            }else{
//                $scope.paginationConf.totalItems = 0;
                $scope.orderList.length = 0;
//                $scope.orderList=response.obj.datas;
            }
        });
    };
    /*查询所有用户 end*/



    /*编辑 功能点 start editfunctionBalance*/
    $scope.editfunctionBalance=function(res){
        popupDiv('editfunctionBalance');
//        $scope.id=res.id;
        $scope.id=res.id;
        $scope.editName=res.name;
        $scope.editSortValue=res.orderNumber;
        $scope.editParentsNode=res.parentId;
//        $scope.parentName=res.parentName;
        $scope.editUrl=res.url;
        $scope.editMenuRemark=res.remark;
        /*根据ID 保存当前信息 start updateSaveFunction*/
        $scope.updateSaveFunction=function(){
            var pa={
                id:$scope.id,
                corporateIdentify:$scope.corporateIdentify,
                name:$scope.editName,
                orderNumber:$scope.editSortValue,
                parentId:$scope.editParentsNode,
                remark:$scope.editMenuRemark,
                requestSeqNo:$scope.requestSeqNo,
                url:$scope.editUrl
            };
            var par={params:pa};
//            $http.get($scope.WebURL+'employee/updatePermissionByVo',par).success(function(response){
            $http.post($scope.WebURL+'addMenu',pa).success(function(response){
                hideDiv("editfunctionBalance");
                popupDiv('SaveSuccess');
                $(".Message").html(response.message);
            }).error(function(response){
                console.log(response.message);
            });
        };
        /*根据ID 保存当前信息 end updateSaveFunction*/

    };
    /*编辑 功能点 end editfunctionBalance*/
    /*新增 功能点 start addfunctionBalance*/
    $scope.addType='group';//默认类型为组
    $scope.addfunctionBalance=function(){
        if($scope.addName==null||$scope.addName==''||$scope.addName==undefined){
            $("#addName").focus();
        }else if($scope.addSortValue==null||$scope.addSortValue==''||$scope.addSortValue==undefined){
            $("#addSortValue").focus();
        }else if($scope.menuRemark==null||$scope.menuRemark==''||$scope.menuRemark==undefined){
            $("#menuRemark").focus();
        }else{
            var pa={
                corporateIdentify:$scope.corporateIdentify,
                name:$scope.addName,
                orderNumber:$scope.addSortValue,
                parentId:$scope.addParentsNode,
                remark:$scope.menuRemark,
                requestSeqNo:$scope.requestSeqNo,
                url:$scope.addUrl
            };
            var par={params:pa};
//            $http.get($scope.WebURL+'employee/savePermissionByVo',par).success(function(response){
            $http.post($scope.WebURL+'addMenu',pa).success(function(response){
                hideDiv("functionBalance");
                popupDiv('SaveSuccess');
                $(".Message").html(response.errorMessage);
            }).error(function(response){
                console.log(response.errorMessage);
            });
        }
    };
    /*新增 功能点 end addfunctionBalance*/
    /*删除 功能点 start deleteFunction*/
    $scope.deletefunctionBalance=function(res){
        $scope.functionName=res.name;
        popupDiv('deleteFunction');
        var pa={
//            id:res.id
            code:res.code
        };
        var par={params:pa};
//        console.log(121121);
//        console.log(pa);
        $scope.deleteFunction=function(){
//            $http.get($scope.WebURL+'employee/deletePermissionById',par).success(function(response){
            $http.post($scope.WebURL+'permission/remove',pa).success(function(response){
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