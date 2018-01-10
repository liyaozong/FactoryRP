//参数设置

//设备类型设置
myApp.factory("deviceType",["AppHttp","FF_API",function(AppHttp,FF_API){
    var deviceType={};
    deviceType.list=function(){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.deviceTypeListPath
        });
    };
    deviceType.addSameDeviceType=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.addSameDeviceTypePath,
            data:data
        });
    };
    deviceType.addSubDeviceType=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.addSubDeviceTypePath,
            data:data
        });
    };
    deviceType.deleteDeviceType=function(data){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.deleteDeviceTypePath+'?id='+data
        });
    };
    deviceType.updateDeviceType=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.updateDeviceTypePath,
            data:data
        });
    };

    return deviceType;
}]);

//备件类型设置
myApp.factory("deviceSpares",["AppHttp","FF_API",function(AppHttp,FF_API){
    var deviceSpares={};
    deviceSpares.list=function(){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.queryAllDeviceSparesTypePath
        });
    };
    deviceSpares.addSameDeviceType=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.addSameDeviceSparesPath,
            data:data
        });
    };
    deviceSpares.addSubDeviceType=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.addSubDeviceSparesPath,
            data:data
        });
    };
    deviceSpares.deleteDeviceType=function(data){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.deleteDeviceSparesTypePath+'?id='+data
        });
    };
    deviceSpares.updateDeviceType=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.updateDeviceSparesPath,
            data:data
        });
    };

    return deviceSpares;
}]);

//审核流程设置
myApp.factory("deviceProcess",['$q',"AppHttp","FF_API",function($q,AppHttp,FF_API){
    var deviceProcess={};
    //分页查询
    deviceProcess.list=function(data){
        var d=$q.defer();
        AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.deviceProcessFindByPagePath,
            data:data
        }).success(function(data) {
            d.resolve(data);
        });

        return d.promise;
    };
    //新增
    deviceProcess.addDeviceProcess=function(data){
        var d=$q.defer();
        AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.addDeviceProcessPath,
            data:data
        }).success(function(data) {
            d.resolve(data);
        });

        return d.promise;
    };

    //查询所有流程类型集合
    deviceProcess.queryAllDecviceProcessType=function(){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.queryAllDecviceProcessTypePath
        });
    };

    return deviceProcess;
}]);