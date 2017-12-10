/**
 * Created by jayvenLee on 2017/11/11.
 */
authorizationApp.controller('userManagementController',function ($scope,$http,UrlService,$filter, $resource, $location, $state,userManagementService) {
    //配置分页基本参数

    $scope.WebURL=UrlService.getUrl('authorizationNew');
    /*查询所有部门 list start queryDepartments*/
//    $scope.DepartmentDetail= userManagementService.queryDepartmentDetail($scope.userpersonId,  function(data){
    $scope.DepartmentDetail= userManagementService.queryDepartmentDetail(function(data){
        if(data.status==0){
            if(data.obj!=null){
                //TODO-业务逻辑
//                $scope.DepartmentDetailList = data.obj.departmentVos;
                $scope.DepartmentDetailList = data.obj;
//                console.log("DepartmentDetailList");
//                console.log($scope.DepartmentDetailList);
            }else{
                console.log(data.message);
            }
        }else{
            console.log(data.message);
        }
    }, function(err){
        console.log(err)
    });
    /*查询所有部门 list end queryDepartments*/
    /*查询所有角色信息 list start getRolesByVo*/
//    $scope.RolesDetail= userManagementService.queryRolesByVo($scope.userpersonId,  function(data){
    $scope.RolesDetail= userManagementService.queryRolesByVo({},function(data){
        if(data.status==0){
            if(data.obj!=null){
                //TODO-业务逻辑
//                $scope.RolesDetailList = data.obj.vos;
                $scope.RolesDetailList = data.obj;
                console.log("RolesDetailList");
//                console.log($scope.RolesDetailList);
            }else{
                console.log(data.message);
            }
        }else{
            console.log(data.message);
        }
    }, function(err){
        console.log(err)
    });
    /*查询所有角色信息 list end getRolesByVo*/
    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 15
    };
    $scope.orderList = userManagementService.getOrderList();
//    console.log(123);
//    console.log($scope.orderList);
    /*查询所有用户 start*/
    $scope.onQuery=function () {
        $scope.Date=$filter('date')($scope.createdDate,'yyyy-MM-dd');;
//        console.log($scope.Date);
        userManagementService.queryOrder({
//            keywords:$scope.queryName,
            userName:$scope.userName,
            userPhone:$scope.userPhone,
            pageNo: $scope.paginationConf.currentPage,
            pageSize: $scope.paginationConf.itemsPerPage
        }, function(response){
            if(response.obj.rowCount>=1){
                $scope.paginationConf.totalItems = response.obj.rowCount;
            }else{
                $scope.paginationConf.totalItems = 0;
                $scope.orderList.length = 0;
            }
        });
    };
    /*查询所有用户 end*/

    /*清空已选择的角色 start*/
    $scope.clearAllChecked=function(){
        $(".editUserBalance ul li input[type='checkbox']").removeAttr("checked");
    };
//    $scope.CheckedAll=function(){
//        $(".editUserBalance ul li input[type='checkbox']").attr("checked",'checked');
//    };
//    $scope.CheckedBackAll=function(){
//        $(".editUserBalance ul li input[type='checkbox']").each(function(){//反选
//            if($(this).attr("checked")){
//                $(this).removeAttr("checked");
//            }else{
//                $(this).attr("checked",'checked');
//            }
//        });
//    };
    /*清空已选择的角色 end*/
    /*根据员工id号查询员工信息 editEmployee start*/

    $scope.editUserBalance=function(res){
//        $(".editUserBalance ul li input").removeAttr('checked');
        $scope.Name=res.name;//用户名
        $scope.Email=res.email;//邮箱
        $scope.phone=res.phone;//手机号码
        $scope.empStatus=res.empStatus.toString();//有效无效

        $scope.role=[];
        $scope.userpersonId=res.phone;
        popupDiv('editUserBalance');
        $scope.userPersonInfo= userManagementService.queryUserDetail($scope.userpersonId,  function(data){
            if(data.status==0){
                if(data.obj!=null){
                    //TODO-业务逻辑
                    $scope.userPersonInfo = data.obj;
                    /*获取当前员工 拥有的角色信息 start*/
                    data.obj.roles.forEach(function(res){
                        $scope.role.push(res.code);//用户拥有的角色ID;
//                        console.log(res.id);
                    });
                    /*获取当前员工 拥有的角色信息 end*/
//                    console.log($scope.roles);
                    /*员工所拥有的角色 复选框选中 start*/
                    var inputVal=$(".editUserBalance ul li input");
                    for(var i=0;i<inputVal.length;i++){
//                        console.log(inputVal.eq(i).val());
//                        console.log($scope.roles);
                        if($scope.role.indexOf(inputVal.eq(i).val())!=-1){
//                            console.log($scope.roles);
                            inputVal.eq(i).attr('checked','checked');
                        }
                    }
                    /*员工所拥有的角色 复选框选中 end*/
                }else{
                    console.log(data.message);
                }
            }else{
                console.log(data.message);
            }
        }, function(err){
            console.log(err)
        });
        /*修改员工信息 start upateEmployee */
        $scope.upateEmployeeInfo=function(){
            /*添加角色 信息 start*/
            $scope.NewRoles=[];
            $(".editUserBalance ul li input[type=checkbox]").each(function(){
                var data={};
                if(this.checked){
//                    console.log($(this).val());
//                    $scope.NewRoles.push($(this).val());
                    data.id=$(this).val();
//                    console.log(1010);
//                    console.log(data);
//                    console.log(2020);
//                    $scope.NewRoles.push(data);
                    $scope.NewRoles.push($(this).val());
//                    console.log($scope.NewRoles);
                }
            });
//            alert($scope.NewRoles);
            /*添加角色 信息 end*/
            if($scope.Name==''||$scope.Name==null||$scope.Name==undefined){
                $("#Name").focus();
            }else if($scope.Email==''||$scope.Email==null||$scope.Email==undefined){
                $("#Email").focus();
            }else if($scope.phone==''||$scope.phone==null||$scope.phone==undefined){
                $("#phone").focus();
            }else if($scope.empStatus==''||$scope.empStatus==null||$scope.empStatus==undefined){
                $("#empStatus").focus();
            }else{
                var pa={
                    "code":res.code,
                    "name":$scope.Name,
                    "email":$scope.Email,
                    "phone":$scope.phone,
                    "empStatus":$scope.empStatus,
                    "roleCodes":$scope.NewRoles
                };
//                console.log('what the');
//                console.log(pa);
//                console.log(pa1);
                var par={params:pa};
                $http.post($scope.WebURL+'employee',pa).success(function(response){
                    hideDiv('editUserBalance');
                    popupDiv('SaveSuccess');
                    $(".Message").html(response.message);
                }).error(function(response){
                    console.log(response.message);
                })
            }

        };
        /*修改员工信息 end upateEmployee */
    };
    /*根据员工id号查询员工信息 editEmployee end*/
    /*重置密码 resetUserPassword start*/
    $scope.resetUserPassword=function(res){
        popupDiv('resetUserBalance');
        $scope.userNameNow=res.name;//当前用户姓名
        var pa={
            employId:res.id
        };
        $scope.resetPasswordSure=function(){
            var par={params:pa};
            $http.get($scope.WebURL+'employee/resetPassword',par).success(function(response){
                hideDiv('resetUserBalance');
                popupDiv('SaveSuccess');
                $(".Message").html(response.message);
            }).error(function(response){
                console.log(response.message);
            })
        };
    };
    /*重置密码 resetUserPassword end*/
    /*删除员工 deleteEmployeeById start*/
    $scope.deleteUser=function(res){
        popupDiv("deleteUser");
        $scope.userNameNow=res.name;//当前用户姓名
        var pa={
            code:res.code
        };
        $scope.deleteUserSure=function(){
            var par={params:pa};
            $http.post($scope.WebURL+'employee/remove',pa).success(function(response){
                hideDiv('deleteUser');
                popupDiv('SaveSuccess');
                $(".Message").html(response.message);
            }).error(function(response){
                console.log(response.message);
            })
        }
    };
    /*删除员工 deleteEmployeeById end*/
    /*新增新员工信息 start addNewUser*/
    $scope.NewempStatus='0';
    $scope.addNewUser=function(){
        /*添加角色 信息 start*/
        $scope.NewRoless=[];
        $(".userBalance ul li input[type=checkbox]").each(function(){
            var data={};
            if(this.checked){
//                console.log($(this).val());
//                    $scope.NewRoles.push($(this).val());
                data.id=$(this).val();
//                    console.log(1010);
//                console.log(data);
//                    console.log(2020);
//                $scope.NewRoless.push(data);
                $scope.NewRoless.push($(this).val());
//                console.log($scope.NewRoless);
            }
        });
//            alert($scope.NewRoles);
        /*添加角色 信息 end*/
        if($scope.NewName==''||$scope.NewName==null||$scope.NewName==undefined){
            $("#NewName").focus();
        }else if($scope.NewEmail==''||$scope.NewEmail==null||$scope.NewEmail==undefined){
            $("#NewEmail").focus();
        }else if($scope.Newphone==''||$scope.Newphone==null||$scope.Newphone==undefined){
            $("#Newphone").focus();
        }else if($scope.NewempStatus==''||$scope.NewempStatus==null||$scope.NewempStatus==undefined){
            $("#NewempStatus").focus();
        }else{
            var pa={
                "name":$scope.NewName,
                "email":$scope.NewEmail,
                "phone":$scope.Newphone,
                "empStatus":$scope.NewempStatus,
                "roleCodes":$scope.NewRoless
//                    roles:$scope.NewRoles
            };
//            console.log(pa);
//                console.log(pa1);
            var par={params:pa};
            $http.post($scope.WebURL+'employee',pa).success(function(response){
                hideDiv('userBalance');
                popupDiv('SaveSuccess');
                $(".Message").html(response.message);
            }).error(function(response){
                console.log(response.message);
            })
        }
    };
    /*新增新员工信息 end addNewUser*/

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