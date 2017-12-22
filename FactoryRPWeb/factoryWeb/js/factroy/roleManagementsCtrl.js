//角色管理
myApp.controller('roleManagementsCtrl',['$filter','$rootScope','$location','$scope','$cookies','userManageMent',function($filter,$rootScope,$location,$scope,$cookies,userManageMent){


    var requestSeqNo=$cookies.get('corporateIdentify');
    userManageMent.queryRoles(requestSeqNo).success(function (data) {
        console.log(data.data);
        $scope.roleLists=data.data;
    });

    //新增角色
    $scope.addRole=function () {
        popupDiv('addRolePop');
        $scope.addRoleSure=function () {
            var addRoleReq={
                roleCode:$scope.roleCode,
                roleDescription:$scope.roleDescription,
                roleName:$scope.roleName,
                requestTime:$filter('date')(new Date(),'yyyy-MM-dd HH:mm:ss')
            };

            // console.log(userAddReq);
            userManageMent.addRole(addRoleReq).success(function (data) {
                // console.log(data,'data');
                if(data.errorCode=='000000'){
                    hideDiv('addRolePop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }else{
                    hideDiv('addRolePop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }
            })
        }
    };

    //为角色添加菜单
    $scope.addMenuRole=function (id,name) {
        // console.log(id,name);
        $scope.addMenuRoleName=name;
        //每次进入获取角色菜单列表
        userManageMent.queryCorporateMenu().success((function (data) {
            $scope.menuRoleLists=data.data;
            popupDiv('addMenuRolePop');
        }));
        var arr=[];
        $scope.addMenuRoleSure=function () {
            $('.menuRoleCheckBox:checked').each(function (i,n) {
                // console.log(i,$(n).prop('id'));
                arr.push($(n).prop('id'));
            });

            var po={
                requestTime:$filter('date')(new Date(),'yyyy-MM-dd HH:mm:ss'),
                menuIdList:arr,
                roleId:id,
                remark:$scope.remark
            };
            userManageMent.addMenuRole(po).success((function (data) {
                if(data.errorCode=='000000'){
                    hideDiv('addMenuRolePop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }else{
                    hideDiv('addMenuRolePop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }
            }));
        }
    };

    //为角色删除菜单
    $scope.delMenuRole=function (id,name) {
        // console.log(id,name);
        $scope.delMenuRoleName=name;
        //每次进入获取角色菜单列表
        userManageMent.queryByRoleId(id).success((function (data) {
            $scope.delmenuRoleLists=data.data.menuList;
            popupDiv('delMenuRolePop');
        }));
        var arr=[];
        $scope.delMenuRoleSure=function () {
            $('.delmenuRoleCheckBox:checked').each(function (i,n) {
                // console.log(i,$(n).prop('id'));
                arr.push($(n).prop('id'));
            });

            var po={
                requestTime:$filter('date')(new Date(),'yyyy-MM-dd HH:mm:ss'),
                menuList:arr,
                roleId:id
            };
            userManageMent.deleteMenuRoleByRoleId(po).success((function (data) {
                if(data.errorCode=='000000'){
                    hideDiv('delMenuRolePop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }else{
                    hideDiv('delMenuRolePop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }
            }));
        }
    };

    //删除用户
    $scope.deleteRole=function(id,name){
        console.log(id);
        $scope.deleteName=name;
        popupDiv('deleteRolePop');
        $scope.deleteRoleSure=function(){
            userManageMent.deleteRole(id).success(function (data) {
                // console.log(data,'data');
                if(data.errorCode=='000000'){
                    hideDiv('deleteRolePop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }else{
                    hideDiv('deleteRolePop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }
            })
        }
    };
}]);
