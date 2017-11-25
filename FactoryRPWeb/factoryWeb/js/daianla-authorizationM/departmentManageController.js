/**
 * Created by caolongping on 2016/5/18.
 */
authorizationApp.controller('departmentManageController',function ($scope,$http,$filter,UrlService, $resource, $location, $state,departmentManageService) {

//    $scope.WebURL=UrlService.getUrl('authorization');
    $scope.WebURL=UrlService.getUrl('authorizationNew');
    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 15
    };
    $scope.orderList = departmentManageService.getOrderList();
//    console.log(123);
//    console.log($scope.orderList);
    /*查询所有用户 start*/
    $scope.onQuery=function () {
        departmentManageService.queryOrder({
            name:$scope.depName
//            currentPage: $scope.paginationConf.currentPage,
//            itemsPerPage: $scope.paginationConf.itemsPerPage
        }, function(response){
            if(response.totalCount>=1){
//                $scope.paginationConf.totalItems = response.obj.totalCount;
                $scope.orderList=response.obj;
            }else{
                console.log(response.message);
//                $scope.paginationConf.totalItems = 0;
//                $scope.orderList.length = 0;
            }
        });
    };
    /*查询所有用户 end*/

    /*修改部门信息 updateDepartMent start */
    $scope.updateDepartMent=function(res){
        popupDiv("editdepartMentBalance");
        $scope.departNowId=res.code;
        $scope.departmentParent=res.parentCode;
        $scope.deptName=res.name;
        /*查询销售部门人员list start*/
//            var pa={
//                itemsPerPage:10000
//            };
//            var par={params:pa};
            $http.get($scope.WebURL+'department').success(function(response){
                $scope.departments=response.obj;
//                console.log(100000);
//                console.log(response.obj.departmentVos);
            }).error(function(response){
                console.log(response.message);
            });
        /*查询销售部门人员list end*/
        /*保存修改后的部门信息 start departmentParentSubmit*/
        $scope.departmentParentSubmit=function(){
            if($scope.deptName==''||$scope.deptName==null||$scope.deptName==undefined){
                $("#deptName").focus();
            }else{
                var pa={
                    code:$scope.departNowId,
                    name:$scope.deptName,
                    parentCode:$scope.departmentParent
                };
//            console.log(pa);
                var par={params:pa};
                $http.post($scope.WebURL+'department',pa).success(function(response){
                    hideDiv("editdepartMentBalance");
                    popupDiv('SaveSuccess');
                    $(".Message").html(response.message);
                })
            }
        };
        /*保存修改后的部门信息 end departmentParentSubmit*/
    };
    /*修改部门信息 updateDepartMent end */

    /*新增部门信息 start departmentParentSubmit*/
    $scope.addDepartment=function(){
        popupDiv('departMentBalance');
        /*查询所有部门list start*/
//            var pa={
//                itemsPerPage:10000
//            };
//            var par={params:pa};
            $http.get($scope.WebURL+'department').success(function(response){
                $scope.departments=response.obj;
//                console.log(100000);
//                console.log(response.obj.departmentVos);
            }).error(function(response){
                console.log(response.message);
            });
        /*查询所有部门list end*/
        $scope.addDepartMent=function(){
            var pa={
                name:$scope.NewDeptName,
                parentCode:$scope.NewDepartmentParent
//                depParentId:$scope.NewDepartmentParent
            };
//            console.log(pa);
            var par={params:pa};
            $http.post($scope.WebURL+'department',pa).success(function(response){
                hideDiv("departMentBalance");
                popupDiv('SaveSuccess');
                $(".Message").html(response.message);
            })
        };
    };
    /*新增部门信息 end departmentParentSubmit*/

    /*删除当前部门 start*/
    $scope.deleteDepart=function(res){
        popupDiv('deletedepart');
        $scope.depNameNow=res.name;//当前部门
        $scope.deleteDeptNow=function(){
            var pa={
                code:res.code
            };
            var par={params:pa};
            $http.post($scope.WebURL+'department/remove',pa).success(function(response){
                hideDiv('deletedepart');
                popupDiv('SaveSuccess');
                $(".Message").html(response.message);
            }).error(function(response){
                console.log(response.message);
            })
        };
    };
    /*删除当前部门 end*/
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