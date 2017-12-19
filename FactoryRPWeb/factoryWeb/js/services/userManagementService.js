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
    userManageMent.deleteUser=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.deleteUserPath,
            data:data
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

    //根据企业标示查询角色
    userManageMent.queryRoles=function(requestSeqNo){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.queryRolesPath+'?requestSeqNo='+requestSeqNo
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


    return userManageMent;
}]);
