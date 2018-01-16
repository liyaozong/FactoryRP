//参数设置
// 设备类型设置控制器
myApp.controller('menuManagementsCtrl',['$filter','$http','$location','$scope','functionManageService','UrlService','$cookies','$resource','$state','userManageMent',function($filter,$http,$location,$scope,functionManageService,UrlService,$cookies,$resource,$state,userManageMent){

    $scope.WebURL=UrlService.getUrl('authorizationNew');
//    $scope.corporateIdentify=$cookies.get('corporateIdentify');
    $scope.requestSeqNo=$cookies.get('requestSeqNo');
    $scope.corporateIdentify='32132132132213';
    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 15
    };
    /*编辑 功能点 start editfunctionBalance*/
    $scope.editfunctionBalance=function(id,name,orderNumber,parentId,url,remark){
        popupDiv('editfunctionBalance');
        // console.log(id,name,orderNumber,parentId,url,remark);
        $scope.id=id;
        $scope.editName=name;
        $scope.editSortValue=orderNumber;
        $scope.editParentsNode=parentId;
//        $scope.parentName=res.parentName;
        $scope.editUrl=url;
        $scope.editMenuRemark=remark==undefined||remark=='undefined'||remark==null?'':remark;
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
    $scope.addType='group';//
    $scope.addMenu=function () {
        // $scope.corporateIdentify='';
        $scope.addName='';
        $scope.addSortValue='';
        $scope.addParentsNode='';
        $scope.menuRemark='';
        // $scope.requestSeqNo='';
        $scope.addUrl='';
        popupDiv('functionBalance');
    };
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
    $scope.deletefunctionBalance=function(id,name){
        $scope.functionName=name;
        popupDiv('deleteFunction');
        var pa={
            id:id
        };
        var par={params:pa};
//        console.log(121121);
//        console.log(pa);
        $scope.deleteFunction=function(){
            userManageMent.deleteMenu(id).success(function (data) {
                if(data.errorCode=='000000'){
                    hideDiv('deleteFunction');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }else{
                    hideDiv('deleteFunction');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }
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
}]);
