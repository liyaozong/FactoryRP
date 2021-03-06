//查询所有企业用户
myApp.factory("queryCorporateAllUser",["AppHttp","FF_API",function(AppHttp,FF_API){
    var queryCorporateAllUser={};
    queryCorporateAllUser.getData=function(){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.queryCorporateAllUserPath
        });
    };

    return queryCorporateAllUser;
}])
    //
    .factory("userManageMent",["AppHttp","FF_API",function(AppHttp,FF_API){
    var userManageMent={};
    //新增企业用户
    userManageMent.addUser=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.addUserPath,
            data:data
        });
    };

    //删除某个用户
    userManageMent.deleteUser=function(id){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.deleteUserPath+'?id='+id
        });
    };

    //为用户添加角色
    userManageMent.addUserRole=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.addUserRolePath,
            data:data
        });
    };

    //为用户删除角色
    userManageMent.deleteUserRoleByUserId=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.deleteUserRoleByUserIdPath,
            data:data
        });
    };

    //根据用户查询角色
    userManageMent.queryRoleByUserId=function(id){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.queryRoleByUserIdPath+'?userId='+id
        });
    };

    //根据企业标示查询角色
    userManageMent.queryRoles=function(requestSeqNo){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.queryRolesPath+'?requestSeqNo='+requestSeqNo
        });
    };

    //新增角色
    userManageMent.addRole=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.addRolePath,
            data:data
        });
    };

    //删除角色
    userManageMent.deleteRole=function(id){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.deleteRolePath+'?roleId='+id
        });
    };

    //为角色添加菜单
    userManageMent.addMenuRole=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.addMenuRolePath,
            data:data
        });
    };

    //为角色删除菜单
    userManageMent.deleteMenuRoleByRoleId=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.deleteMenuRoleByRoleIdPath,
            data:data
        });
    };

    //根据角色ID查询菜单
    userManageMent.queryByRoleId=function(roleId){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.queryByRoleIdPath+'?roleId='+roleId
        });
    };

    //查询企业菜单
    userManageMent.queryCorporateMenu=function(){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.queryCorporateMenuPath
        });
    };

    //添加菜单
    userManageMent.addMenu=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.addMenuPath,
            data:data
        });
    };

    //删除菜单
    userManageMent.deleteMenu=function(id){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.deleteMenuPath+'?menuId='+id
        });
    };


    return userManageMent;
}]);
