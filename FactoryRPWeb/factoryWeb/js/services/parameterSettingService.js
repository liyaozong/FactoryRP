//参数设置
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
}])