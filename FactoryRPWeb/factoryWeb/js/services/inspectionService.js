//点巡检

//巡检计划
myApp.factory("spotInspectionStandard",["AppHttp","FF_API",function(AppHttp,FF_API){
    var spotInspectionStandard={};
    spotInspectionStandard.list=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.spotInspectionStandardFindByPagePath,
            data:data
        });
    };

    return spotInspectionStandard;
}])