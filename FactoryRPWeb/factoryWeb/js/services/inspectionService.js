//点巡检

//巡检标准
myApp.factory("spotInspectionStandard",["AppHttp","FF_API",function(AppHttp,FF_API){
    var spotInspectionStandard={};
    //分页查询巡检计划
    spotInspectionStandard.list=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.spotInspectionStandardFindByPagePath,
            data:data
        });
    };
    //新增巡检标准
    spotInspectionStandard.addSpotInspectionStandard=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.addSpotInspectionStandardPath,
            data:data
        });
    };
    //查询点巡检记录方式
    spotInspectionStandard.getRecordType=function(){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.queryAllSpotInspectionItemsRecordTypePath
        });
    };
    //查询点巡检标准详情
    spotInspectionStandard.queryInspectionStandardDetail=function(standardId){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.queryInspectionStandardDetailPath+'?standardId='+standardId
        });
    };
    //单个删除巡检标准
    spotInspectionStandard.deleteInspectionStandard=function(standardId){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.deleteInspectionStandardPath+'?standardId='+standardId
        });
    };
    //批量删除巡检标准
    spotInspectionStandard.deleteSpotInspectionStandardByIds=function(standardId){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.deleteSpotInspectionStandardByIdsPath+'?ids='+standardId
        });
    };

    return spotInspectionStandard;
}]);

//巡检计划
myApp.factory("inspectionPlan",["AppHttp","FF_API",function(AppHttp,FF_API){
    var inspectionPlan={};
    //巡检时间
    inspectionPlan.getxjsj=function(){
        return AppHttp({
            method: 'get',
            url: FF_API.baseApi + FF_API.getxjsjPath
        });
    };
    //巡检时间
    inspectionPlan.getxjlx=function(){
        return AppHttp({
            method: 'get',
            url: FF_API.baseApi + FF_API.getxjlxPath
        });
    };
    //巡检计划执行时间类型
    inspectionPlan.queryAllSpotInspectionPlanRecycleType=function(){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.queryAllSpotInspectionPlanRecycleTypePath
        });
    };
    //根据部门ID查询巡检标准
    inspectionPlan.queryStanardByDeviceId=function(deviceId){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.queryStanardByDeviceIdPath+'?deviceId='+deviceId
        });
    };

    return inspectionPlan;
}]);