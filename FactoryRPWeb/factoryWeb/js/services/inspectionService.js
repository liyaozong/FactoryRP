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
    //新增巡检计划
    inspectionPlan.addSpotInspectionPlan=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.addSpotInspectionPlanPath,
            data:data
        });
    };
    //编辑巡检计划
    inspectionPlan.editSpotInspectionPlan=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.editSpotInspectionPlanPath,
            data:data
        });
    };
    //分页查询巡检计划
    inspectionPlan.spotInspectionPlanFindByPage=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.spotInspectionPlanFindByPagePath,
            data:data
        });
    };
    //执行巡检计划
    inspectionPlan.executeSpotInspectionPlan=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.executeSpotInspectionPlanPath,
            data:data
        });
    };
    //查询巡检记录详情
    inspectionPlan.querySpotInspectionRecordDetailByRecordId=function(recordId,planId){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.querySpotInspectionRecordDetailByRecordIdPath+'?recordId='+recordId+'&planId='+planId
        });
    };
    //分页查询巡检计划
    inspectionPlan.QuerySpotInspectionPlanDetailByPlanId=function(id){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.QuerySpotInspectionPlanDetailByPlanIdPath+'?planId='+id
        });
    };
    //单个删除巡检计划
    inspectionPlan.deleteSpotInspectionPlanDetailByPlanId=function(id){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.deleteSpotInspectionPlanDetailByPlanIdPath+'?planId='+id
        });
    };
    //批量删除巡检计划
    inspectionPlan.deleteSpotInspectionStandardByIdsIP=function(id){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.deleteSpotInspectionStandardByIdsIPPath+'?ids='+id
        });
    };
    //批量删除巡检计划
    inspectionPlan.querySpotInspectionRecordByPlanId=function(id){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.querySpotInspectionRecordByPlanIdPath+'?planId='+id
        });
    };
    //通过巡检标准ID和巡检计划ID进行查询巡检项目相关数据
    inspectionPlan.findSpotInspectionStandardItemByStandardIdAndPlanId=function(standardId,planId){
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.findSpotInspectionStandardItemByStandardIdAndPlanIdPath+'?standardId='+standardId+'&planId='+planId
        });
    };
    //单个获取图片路径
    inspectionPlan.viewItem=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.viewItemPath,
            data:data
        });
    };

    return inspectionPlan;
}]);