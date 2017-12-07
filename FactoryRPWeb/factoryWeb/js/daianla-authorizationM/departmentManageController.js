/**
 * Created by caolongping on 2016/5/18.
 */
authorizationApp.controller('departmentManageController',function ($scope,$cookies,$http,$filter,UrlService, $resource, $location, $state,departmentManageService) {

//    $scope.WebURL=UrlService.getUrl('authorization');
//    $scope.corporateIdentify=$cookies.get('corporateIdentify');
    $scope.corporateIdentify='32132132132213';
    $scope.WebURL=UrlService.getUrl('factoryServe');
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
            name:$scope.depName,
            corporateIdentify:$scope.corporateIdentify
//            currentPage: $scope.paginationConf.currentPage,
//            itemsPerPage: $scope.paginationConf.itemsPerPage
        }, function(response){
            if(response.data!=''&&response.data!=null&&response.data!=undefined&&response.errorCode=='000000'){
//                $scope.paginationConf.totalItems = response.obj.totalCount;
                $scope.orderList=response.data;
            }else{
                console.log(response.errorMessage);
//                $scope.paginationConf.totalItems = 0;
//                $scope.orderList.length = 0;
            }
        });
    };
    /*查询所有用户 end*/

    /*查子集 start*/
    $scope.showSub=function(res,$event){
        $scope.subList=[];
        $scope.orderList.forEach(function(item){
            if(item.parentId==res.id){
                $scope.subList.push(item);
            }
        });
        console.log($scope.subList);
        if($scope.subList.length>0){
            $($event.target).parent().parent().find('div').toggleClass('hide');
            $($event.target).parent().parent().siblings().find('subContainer').addClass('hide');
            if($($event.target).parent().parent().find('div').hasClass('hide')){
                $($event.target).html('+')
            }else{
                $($event.target).html('-')
            }
        }
    };
    /*查子集 end*/

    /*修改部门信息 updateDepartMent start */
    $scope.updateDepartMent=function(res){
        popupDiv("editdepartMentBalance");
        $scope.editDepartmentParent=res.parentId;
        $scope.editNewSamDeptName=res.name;
        $scope.editNewSamDeptCode=res.code;
        $scope.id=res.id;

        /*保存修改后的部门信息 start departmentParentSubmit*/
        $scope.departmentParentSubmit=function(){
//            if($scope.editDepartmentParent==''||$scope.editDepartmentParent==null||$scope.editDepartmentParent==undefined){
//                $("#editDepartmentParent").focus();
//            }else
            if($scope.editNewSamDeptName==''||$scope.editNewSamDeptName==null||$scope.editNewSamDeptName==undefined){
                $("#editNewSamDeptName").focus();
            }else if($scope.editNewSamDeptCode==''||$scope.editNewSamDeptCode==null||$scope.editNewSamDeptCode==undefined){
                $("#editNewSamDeptCode").focus();
            }else{
                var pa={
                    id:res.id,
                    code:$scope.editNewSamDeptCode,
                    corporateIdentify:$scope.corporateIdentify,
                    name:$scope.editNewSamDeptName,
                    parentId:$scope.editDepartmentParent
                };
//            console.log(pa);
                var par={params:pa};
                $http.post($scope.WebURL+'department/updateDept',pa).success(function(response){
                    hideDiv("editdepartMentBalance");
                    popupDiv('SaveSuccess');
                    $(".Message").html(response.errorMessage);
                })
            }
        };
        /*保存修改后的部门信息 end departmentParentSubmit*/
    };
    /*修改部门信息 updateDepartMent end */
    /*新增 同级部门 start*/
    $scope.addSamDepartment=function(){
        popupDiv('departSamMentBalance');
        console.log($(".tableListDiv ul li input[name='mainId']:checked").val());
        if($scope.NewSamDepartmentParent==''||$scope.NewSamDepartmentParent==null||$scope.NewSamDepartmentParent==undefined){
            $scope.NewSamDepartmentParent='-1';
        }
        $scope.addSamDepartMentSure=function(){
            if($scope.NewSamDeptName==''||$scope.NewSamDeptName==null||$scope.NewSamDeptName==undefined){
                $("#NewSamDeptName").focus()
            }else if($scope.NewSamDeptCode==''||$scope.NewSamDeptCode==null||$scope.NewSamDeptCode==undefined){
                $("#NewSamDeptCode").focus()
            }else{
                var pa={
                    code:$scope.NewSamDeptCode,
                    corporateIdentify:$scope.corporateIdentify,
                    parentId:$scope.NewSamDepartmentParent,
                    name:$scope.NewSamDeptName
//                parentCode:$scope.NewDepartmentParent
//                depParentId:$scope.NewDepartmentParent
                };
//            console.log(pa);
                var par={params:pa};
                $http.post($scope.WebURL+'department/addSameDept',pa).success(function(response){
                    hideDiv("departSamMentBalance");
                    popupDiv('SaveSuccess');
                    $(".Message").html(response.errorMessage);
                })
            }
        };
    };
    /*新增 同级部门 end*/
    /*新增 下级部门信息 start departmentParentSubmit*/
    $scope.addDepartment=function(){
        popupDiv('departMentBalance');
        $scope.addDepartMent=function(){
            if($scope.NewDepartmentParent==''||$scope.NewDepartmentParent==null||$scope.NewDepartmentParent==undefined){
                $("#NewDepartmentParent").focus();
            }else if($scope.NewDeptName==''||$scope.NewDeptName==null||$scope.NewDeptName==undefined){
                $("#NewDeptName").focus();
            }else if($scope.NewDeptCode==''||$scope.NewDeptCode==null||$scope.NewDeptCode==undefined){
                $("#NewDeptCode").focus();
            }else{
                var pa={
                    code:$scope.NewDeptCode,
                    corporateIdentify:$scope.corporateIdentify,
                    name:$scope.NewDeptName,
                    parentId:$scope.NewDepartmentParent
//                depParentId:$scope.NewDepartmentParent
                };
//            console.log(pa);
                var par={params:pa};
                $http.post($scope.WebURL+'department/addSubDept',pa).success(function(response){
                    hideDiv("departMentBalance");
                    popupDiv('SaveSuccess');
                    $(".Message").html(response.errorMessage);
                })
            }
        };
    };
    /*新增部门信息 end departmentParentSubmit*/

    /*删除当前部门 start*/
    $scope.deleteDepart=function(res){
        popupDiv('deletedepart');
        $scope.depNameNow=res.name;//当前部门
        $scope.deleteDeptNow=function(){
            var pa={
                id:res.id
            };
            var par={params:pa};
            $http.get($scope.WebURL+'department/deleteDept',par).success(function(response){
                hideDiv('deletedepart');
                popupDiv('SaveSuccess');
                $(".Message").html(response.errorMessage);
            }).error(function(response){
                console.log(response.errorMessage);
            })
        };
    };
    /*删除当前部门 end*/
    /*处理之后保留在当前页面start*/
//    $scope.reload=function(){
//        $scope.paginationConf.currentPage = $(".page-total input").val();
//        $scope.onQuery();
//    };
    /*处理之后保留在当前页面end*/
    /***************************************************************
     当页码和页面记录数发生变化时监控后台查询
     如果把currentPage和itemsPerPage分开监控的话则会触发两次后台事件。
     ***************************************************************/
    $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.onQuery);

});