/**
 * Created by jayvenLee on 2017/11/11.
 */
authorizationApp.controller('roleManageController',function ($scope,$filter, $resource, $location,UrlService, $state,roleManageService,AuthorizationService,$http) {

    var subDepartMent = {};
    $scope.WebURL=UrlService.getUrl('authorizationNew'); 
    //配置分页基本参数 start
    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 15
    };
    //配置分页基本参数 end
    $scope.orderList = roleManageService.getOrderList();
    $scope.menuPerssions = AuthorizationService.getMenuNewPerssions();
    $scope.queryGroupList=$scope.menuPerssions;
    console.log($scope.menuPerssions);
    /*查询角色列表 start*/
    $scope.onQuery=function () {
        roleManageService.queryOrder({
            pageNo: $scope.paginationConf.currentPage,
            pageSize: $scope.paginationConf.itemsPerPage,
            keyword:$scope.roleName
        }, function(response){
            if(response.obj.rowCount>=1){
                $scope.paginationConf.totalItems = response.totalCount;
            }else{
                $scope.paginationConf.totalItems = 0;
                $scope.orderList.length = 0;
            }
        });

    };
    /*查询角色列表 end*/
    /*根据角色id号查询角色信息 editEmployee start*/

    $scope.editRoleBalance=function(res){
        $scope.editroleN=res.name;
        $scope.editroleCode=res.code;
        $scope.editStatus=res.status;
        popupDiv('editroleBalance');
        console.log(res)
        if(res.permissionCodes!=''&&res.permissionCodes!=null&&res.permissionCodes!=undefined){
            $scope.roles=res.permissionCodes;
        }else{
            $scope.roles=[];
        }
        console.log($scope.roles);
        /*员工所拥有的功能 复选框选中 start*/
        var inputVal=$(".editRolefunction input[type=checkbox]");
        for(var i=0;i<inputVal.length;i++){
//                        console.log(inputVal.eq(i).val());
//                        console.log($scope.roles);
            if($scope.roles.indexOf(inputVal.eq(i).val().toString())!=-1){
//                            console.log($scope.roles);
                inputVal.eq(i).prop('checked',true);
            }
        }
        /*员工所拥有的功能 复选框选中 end*/

        /*修改员工信息 start upateEmployee */
        $scope.upateRoleInfo=function(){
            /*添加角色 信息 start*/
            $scope.NewRoles=[];
            $(".editRolefunction input[type=checkbox]").each(function(){
//                var data={};
                if(this.checked){
//                    console.log($(this).val());
//                    $scope.NewRoles.push($(this).val());
//                    data.id=$(this).val();
//                    console.log(1010);
//                    console.log(data);
//                    console.log(2020);
                    $scope.NewRoles.push($(this).val());
//                    console.log($scope.NewRoles);
                }
            });
//            alert($scope.NewRoles);
            /*添加角色 信息 end*/
            if($scope.editroleN==''||$scope.editroleN==null||$scope.editroleN==undefined){
                $("#editRoleName").focus();
            }else if($scope.editStatus==''||$scope.editStatus==null||$scope.editStatus==undefined){
                $("#editStatus").focus();
            }else{
                var pa={
                    "code":$scope.editroleCode,
                    "name":$scope.editroleN,
                    "status":$scope.editStatus,
                    "permissionCodes":$scope.NewRoles
//                    roles:$scope.NewRoles
                };
//                console.log('what the **');
//                console.log(pa);
//                console.log(pa1);
                var par={params:pa};
                $http.post($scope.WebURL+'role',pa).success(function(response){
                    hideDiv('editroleBalance');
                    popupDiv('SaveSuccess');
                    $(".Message").html(response.message);
                }).error(function(response){
                    console.log(response.message);
                })
            }

        };
        /*修改员工信息 end upateEmployee */
    };
    /*根据角色id号查询角色信息 editEmployee end*/



    /*查询组（group）权限列表信息以及列表下的功能点  start*/
//        roleManageService.queryGroupListInfo(100,function(data){
//            if(data.status==0){
//                if(data!=null){
//                    //TODO-业务逻辑
//                    $scope.queryGroupList = data.obj;//顶级菜单
//                }else{
//                    console.log(data.message);
//                }
//            }else{
//                console.log("调用接口失败！");
//            }
//        }, function(err){
//            console.log(err)
//        });
    /*查询组（group）权限列表信息以及列表下的功能点  end*/

    /*新增角色信息 start*/
    $scope.NewempStatus='0';
    $scope.addNewRole=function(){
        /*添加功能点 信息 start*/
        $scope.NewRoless=[];
        $(".addRolefunction input[type=checkbox]").each(function(){
            var data={};
            if(this.checked){
//                console.log($(this).val());
//                    $scope.NewRoles.push($(this).val());
//                data.id=$(this).val();
//                    console.log(1010);
//                console.log(data);
//                    console.log(2020);
                $scope.NewRoless.push($(this).val());
//                console.log($scope.NewRoless);
            }
        });
//            alert($scope.NewRoles);
        /*添加功能点 信息 end*/
        if($scope.roleN==''||$scope.roleN==null||$scope.roleN==undefined){
            $("#addroleName").focus();
        }else if($scope.status==''||$scope.status==null||$scope.status==undefined){
            $("#status").focus();
        }else{
            var pa={
                "name":$scope.roleN,
                "status":$scope.status,
                "permissionCodes":$scope.NewRoless
//                    roles:$scope.NewRoles
            };
//            console.log('what the **');
//            console.log(pa);
//                console.log(pa1);
            var par={params:pa};
            $http.post($scope.WebURL+'role',pa).success(function(response){
                hideDiv('roleBalance');
                popupDiv('SaveSuccess');
             $(".Message").html(response.message);
            }).error(function(response){
                console.log(response.message);
            })
        }
    };

    /*新增角色信息 end*/
    /*删除角色 deleteRole start*/
    $scope.deleteRole=function(res){
        popupDiv("deleterole");
        $scope.roleNames=res.name;
        var pa={
            code:res.code
        };
        $scope.deleteRoles=function(){
            var par={params:pa};
            $http.post($scope.WebURL+'role/remove',pa).success(function(response){
                hideDiv('deleterole');
                popupDiv('SaveSuccess');
                $(".Message").html(response.message);
            }).error(function(response){
                console.log(response.message);
            })
        }
    };
    /*删除角色 deleteRole end*/
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


