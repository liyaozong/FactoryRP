//点巡检

//巡检计划
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

    return spotInspectionStandard;
}]);