//角色管理
myApp.controller('roleManagementsCtrl',['$filter','$rootScope','$location','$scope','$cookies','userManageMent',function($filter,$rootScope,$location,$scope,$cookies,userManageMent){


    var requestSeqNo=$cookies.get('corporateIdentify');
    userManageMent.queryRoles(requestSeqNo).success(function (data) {
        // console.log(data.data);
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

    //添加菜单选择事件
    $scope.chengedRole=function (id) {
        var str='';
        console.log('changeRole',id);
        var flog=$('#'+id).is(':checked');
        var thisId=$('#'+id).attr('id');
        // console.log(thisId);
        if(flog){
            str+='<div class="subRoleDiv">';
            $scope.menuRoleLists.forEach(function (n) {
                // console.log(n.parentId==thisId);
                if(n.parentId==thisId){
                    console.log(n);
                    str+='<li class="userRoleLi">';
                    str+='<input ng-change="chengedRole('+n.id+')"  ng-model="'+n.modelN+'"  type="checkbox" roleId="'+n.id+'" id="'+n.id+'" class="menuRoleCheckBox">';
                    str+='<label for="'+n.id+'">'+n.name+'</label>';
                    str+='</li>';
                }
            });
            str+='</div>';
            $('#'+id).parent().append(str);
        }else {
            $('#'+id).parent().find('div').remove();
        }
    };

    //角色删除菜单选择事件
    $scope.chengedRoleDel=function (id) {
        var str='';
        // console.log('changeRole',id);
        var flog=$('#'+id).is(':checked');
        var thisId=$('#'+id).attr('id');
        // console.log(thisId);
        if(flog){
            str+='<div class="subRoleDiv">';
            $scope.delmenuRoleLists.forEach(function (n) {
                // console.log(n.parentId==thisId);
                if(n.parentId==thisId){
                    // console.log(n);
                    str+='<li class="userRoleLi">';
                    str+='<input ng-change="chengedRoleDel('+n.id+')"  ng-model="'+n.modelN+'"  type="checkbox" roleId="'+n.id+'" id="'+n.id+'" class="delmenuRoleCheckBox">';
                    str+='<label for="'+n.id+'">'+n.name+'</label>';
                    str+='</li>';
                }
            });
            str+='</div>';
            $('#'+id).parent().append(str);
        }else {
            $('#'+id).parent().find('div').remove();
        }
    };

    //为角色添加菜单
    $scope.addMenuRole=function (id,name) {
        // console.log(id,name);
        $scope.addMenuRoleName=name;
        //每次进入获取角色菜单列表
        userManageMent.queryCorporateMenu().success((function (data) {
            $scope.menuRoleLists=data.data;
            // console.log($scope.menuRoleLists);
            $scope.menuRoleLists.forEach(function (n) {
                $scope.menuRoleLists.modelN='model'+n.id;
            });
            popupDiv('addMenuRolePop');
        }));
        var arr=[];
        $scope.addMenuRoleSure=function () {
            $('.menuRoleCheckBox:checked').each(function (i,n) {
                arr.push($(n).attr('id'));
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
            $scope.delmenuRoleLists.forEach(function (n) {
                $scope.delmenuRoleLists.modelN='model'+n.id;
            });
            popupDiv('delMenuRolePop');
        }));
        var arr=[];
        $scope.delMenuRoleSure=function () {
            $('.delmenuRoleCheckBox:checked').each(function (i,n) {
                arr.push($(n).attr('id'));
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
        // console.log(id);
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
