//查询所有企业用户
myApp.factory("queryCorporateAllUser",["AppHttp","FF_API",function(AppHttp,FF_API){
    console.log('hhhhhh')
    var queryCorporateAllUser={};
    queryCorporateAllUser.getData=function(){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.queryCorporateAllUserPath
        });
    };

    return queryCorporateAllUser;
}])
