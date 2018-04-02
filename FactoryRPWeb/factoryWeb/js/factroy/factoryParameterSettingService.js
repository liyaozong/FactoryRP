factoryParameterSettingApp.factory('factoryParameterSettingService', function($resource, $log, UrlService) {
    var allOrderList=[];
    var curHandleOrder = {};
    //分页查询往来单位列表
    var queryOrder = function(queryParam, successFunc){
        console.log(queryParam);
        var OrderQuery = $resource(UrlService.getUrl('factoryServe') + 'contactCompany/list');
        OrderQuery.save(queryParam, function(data){
            allOrderList.length = 0;
//            console.log(data);
//            console.log(queryParam);
            if(data.errorCode=='000000'&&data.data.totalCount>=1){
                data.data.list.forEach(function(item) {
                    allOrderList.push(item);
                });
            }

            successFunc(data);
        }, function(err){

        });
    };
    /* 查看所有设备字典code start*/
    var _viewDeviceDicCode = function(Param,successFunc){
        var viewDeviceDicCodes = $resource(UrlService.getUrl('mainServe') + 'viewDeviceDicCode');
        viewDeviceDicCodes.get(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /* 查看所有设备字典code end*/
    /*新增或修改往来单位 start*/
    var _saveContactCompany = function(Param,successFunc){
        var saveContactCompanys = $resource(UrlService.getUrl('factoryServe') + 'contactCompany/save');
        saveContactCompanys.save(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*新增或修改往来单位 end*/

    /*删除往来单位 start*/
    var _deleteContactCompany = function(Param,successFunc){
        var deleteContactCompany = $resource(UrlService.getUrl('factoryServe') + 'contactCompany/deleteContactCompany');
        deleteContactCompany.get(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*删除往来单位 end*/

    /*添加维修工段/班组列表 start*/
    var _addRepairGroup = function(Param,successFunc){
        var addRepairGroup = $resource(UrlService.getUrl('factoryServe') + 'repairGroup/add');
        addRepairGroup.get(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*添加维修工段/班组列表 end*/
    /*查询维修工段/班组列表 start*/
    var _queryRepairGroupList = function(Param,successFunc){
        var queryRepairGroupList = $resource(UrlService.getUrl('factoryServe') + 'repairGroup/list');
        queryRepairGroupList.get(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*查询维修工段/班组列表 end*/
    /*批量删除维修工段/班组 start*/
    var _deleteRepairGroup = function(Param,successFunc){
        var deleteRepairGroup = $resource(UrlService.getUrl('factoryServe') + 'repairGroup/delete');
        deleteRepairGroup.get(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*批量删除维修工段/班组 end*/

    /*分页查询设备信息列表 start*/
    var _deviceListInfo = function(Param,successFunc){
        var deviceListInfos = $resource(UrlService.getUrl('factoryServe') + 'deviceInfo/list');
        deviceListInfos.save(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*分页查询设备信息列表 end*/
    /*单个查询设备信息 编辑时候使用 start*/
    var _queryOneDeviceListInfo = function(Param,successFunc){
        var queryOneDeviceListInfo = $resource(UrlService.getUrl('factoryServe') + 'deviceInfo/get');
        queryOneDeviceListInfo.get(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*单个查询设备信息 编辑时候使用 end*/
    /*新增或者修改设备信息 start*/
    var _saveDeviceInfo = function(Param,successFunc){
        var saveDeviceInfos = $resource(UrlService.getUrl('factoryServe') + 'deviceInfo/save');
        saveDeviceInfos.save(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*新增或者修改设备信息 end*/
    /*批量删除设备信息 start*/
    var _batchDeleteDeviceInfo = function(Param,successFunc){
        var batchDeleteDeviceInfos = $resource(UrlService.getUrl('factoryServe') + 'deviceInfo/batchDelete');
        batchDeleteDeviceInfos.get(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*批量删除设备信息 end*/
    /*查询所有生产厂商供应商 start*/
    var _queryAllCompany = function(Param,successFunc){
        var queryAllCompanys = $resource(UrlService.getUrl('factoryServe') + 'contactCompany/list');
        queryAllCompanys.save(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*查询所有生产厂商供应商 end*/
    /*根据code查询设备参数列表 start*/
    var _queryDeviceDictionary = function(Param,successFunc){
        var queryDeviceDictionarys = $resource(UrlService.getUrl('factoryServe') + 'deviceParameterDictionary/findByCode');
        queryDeviceDictionarys.get(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*根据code查询设备参数列表 end*/
    /*查询设备类型列表 start*/
    var _queryDeviceTypeList = function(Param,successFunc){
        var queryDeviceTypeLists = $resource(UrlService.getUrl('factoryServe') + 'deviceType/list');
        queryDeviceTypeLists.get(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*查询设备类型列表 end*/
    /*故障报修/故障提出接口 start*/
    var _addTroubleRecords = function(Param,successFunc){
        var addTroubleRecords = $resource(UrlService.getUrl('troubleRecordServe') + 'troubleRecord/add');
        addTroubleRecords.save(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*故障报修/故障提出接口 end*/
    /*批量删除故障信息 start*/
    var _batchDeleteTroubleRecords = function(Param,successFunc){
        var batchDeleteTroubleRecords = $resource(UrlService.getUrl('troubleRecordServe') + 'troubleRecord/batchDelete');
        batchDeleteTroubleRecords.get(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*批量删除故障信息 end*/
    /*设备对应的故障列表 start*/
    var _queryTroubleRecordLists = function(Param,successFunc){
        var queryTroubleRecordLists = $resource(UrlService.getUrl('troubleRecordServe') + 'troubleRecord/list');
        queryTroubleRecordLists.save(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*设备对应的故障列表 end*/
    /*查询所有故障类型类型 start*/
    var _queryAlldDeviceTroubleType = function(Param,successFunc){
        var queryAlldDeviceTroubleTypes = $resource(UrlService.getUrl('factoryServe') + 'deviceTroubleType/queryAlldDeviceTroubleType');
        queryAlldDeviceTroubleTypes.get(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*查询所有故障类型类型 end*/
    /* 查询所有设备状态 start*/
    var _queryAllDeviceStatus = function(Param,successFunc){
        var queryAllDeviceStatus = $resource(UrlService.getUrl('mainServe') + 'queryAllDeviceStatus');
        queryAllDeviceStatus.get(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /* 查询所有设备状态 end*/
    /*设备对应的维修计划列表 start*/
    var _queryMaintenanceRecordLists = function(Param,successFunc){
        var queryMaintenanceRecordLists = $resource(UrlService.getUrl('troubleRecordServe') + 'maintainPlan/listForDevice');
        queryMaintenanceRecordLists.save(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*设备对应的维修计划列表 end*/
    /*查询全部维修计划 start*/
    var _queryMaintenanceRecordAllLists = function(Param,successFunc){
        var queryMaintenanceRecordAllLists = $resource(UrlService.getUrl('troubleRecordServe') + 'maintainPlan/list');
        queryMaintenanceRecordAllLists.save(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*查询全部维修计划 end*/
    /*添加维修计划 start*/
    var _addMaintainPlan = function(Param,successFunc){
        var addMaintainPlan = $resource(UrlService.getUrl('troubleRecordServe') + 'maintainPlan/add');
        addMaintainPlan.save(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*添加维修计划 end*/
    /*根据主键查询保养计划 start*/
    var _queryMaintenanceRecordById = function(Param,successFunc){
        var queryMaintenanceRecordById = $resource(UrlService.getUrl('troubleRecordServe') + 'maintainPlan/get');
        queryMaintenanceRecordById.get(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*根据主键查询保养计划 end*/
    /*查询企业所有用户信息 start*/
    var _queryCorporateAllUser = function(Param,successFunc){
        var queryCorporateAllUser = $resource(UrlService.getUrl('troubleRecordServe') + 'api/authorization/queryCorporateAllUser');
        queryCorporateAllUser.get(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*查询企业所有用户信息 end*/
    /*批量删除保养计划 start*/
    var _batchDeleteMaintenanceRecord = function(Param,successFunc){
        var batchDeleteMaintenanceRecord = $resource(UrlService.getUrl('troubleRecordServe') + 'maintainPlan/batchDelete');
        batchDeleteMaintenanceRecord.get(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*批量删除保养计划 end*/

    /*查询当前登陆人待审核的故障列表 start*/
    var _queryMyWaitAuditList = function(Param,successFunc){
        var queryMyWaitAuditList = $resource(UrlService.getUrl('troubleRecordServe') + 'troubleRecord/myWaitAuditList');
        queryMyWaitAuditList.save(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*查询当前登陆人待审核的故障列表 end*/
    /*查询所有备件信息 start*/
    var _queryAllSpareParts = function(Param,successFunc){
        var queryAllSpareParts = $resource(UrlService.getUrl('troubleRecordServe') + 'api/spareParts/findByPage');
        queryAllSpareParts.save(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*查询所有备件信息 end*/
    /*执行保养计划 start*/
    var _maintainPlanSubmit = function(Param,successFunc){
        var maintainPlanSubmit = $resource(UrlService.getUrl('troubleRecordServe') + 'maintainPlan/submit');
        maintainPlanSubmit.save(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*执行保养计划 end*/
    /*查询审核详情 start*/
    var _getTroubleRecordDetail = function(Param,successFunc){
        var getTroubleRecordDetail = $resource(UrlService.getUrl('troubleRecordServe') + 'troubleRecord/getDetail');
        getTroubleRecordDetail.get(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*查询审核详情 end*/
    /*审核提交 start*/
    var _auditTroubleRecord = function(Param,successFunc){
        var auditTroubleRecord = $resource(UrlService.getUrl('troubleRecordServe') + 'troubleRecord/audit');
        auditTroubleRecord.save(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*审核提交 end*/
    /*根据设备查询关联的备件信息--WEB/MOBILE start*/
    var _findRealSparts = function(Param,successFunc){
        var findRealSparts = $resource(UrlService.getUrl('troubleRecordServe') + 'deviceSpareRel/findRelSparts');
        findRealSparts.save(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*根据设备查询关联的备件信息--WEB/MOBILE end*/
    /*保存设备关联的备件信息--WEB start*/
    var _saveSpareRel = function(Param,successFunc){
        var saveSpareRel = $resource(UrlService.getUrl('troubleRecordServe') + 'deviceSpareRel/saveSpareRel');
        saveSpareRel.save(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*保存设备关联的备件信息--WEB end*/
    /*保存备件关联的设备信息--WEB start*/
    var _saveDeviceRel = function(Param,successFunc){
        var saveDeviceRel = $resource(UrlService.getUrl('troubleRecordServe') + 'deviceSpareRel/saveDeviceRel');
        saveDeviceRel.save(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*保存备件关联的设备信息--WEB end*/
    /*批量删除关联信息--WEB start*/
    var _batchDeleteDeviceSpareRel = function(Param,successFunc){
        var batchDeleteDeviceSpareRel = $resource(UrlService.getUrl('troubleRecordServe') + 'deviceSpareRel/batchDelete');
        batchDeleteDeviceSpareRel.get(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*批量删除关联信息--WEB end*/
    /*查询备件类型  start*/
    var _queryAllDeviceSparesType= function(Param,successFunc){
        var queryAllDeviceSparesType = $resource(UrlService.getUrl('troubleRecordServe') + 'api/deviceSparesType/queryAllDeviceSparesType');
        queryAllDeviceSparesType.get(Param,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*查询备件类型 end*/
    return {
        saveContactCompany:_saveContactCompany,
        deleteContactCompany:_deleteContactCompany,
        addRepairGroup:_addRepairGroup,
        queryRepairGroupList:_queryRepairGroupList,
        deleteRepairGroup:_deleteRepairGroup,
        deviceListInfo:_deviceListInfo,
        saveDeviceInfo:_saveDeviceInfo,
        queryAllCompany:_queryAllCompany,
        batchDeleteDeviceInfo:_batchDeleteDeviceInfo,
        viewDeviceDicCode:_viewDeviceDicCode,
        queryDeviceDictionary:_queryDeviceDictionary,
        queryDeviceTypeList:_queryDeviceTypeList,
        queryOneDeviceListInfo:_queryOneDeviceListInfo,
        addTroubleRecords:_addTroubleRecords,
        batchDeleteTroubleRecords:_batchDeleteTroubleRecords,
        queryTroubleRecordLists:_queryTroubleRecordLists,
        queryMaintenanceRecordLists:_queryMaintenanceRecordLists,
        queryMaintenanceRecordAllLists:_queryMaintenanceRecordAllLists,
        queryAlldDeviceTroubleType:_queryAlldDeviceTroubleType,
        queryAllDeviceStatus:_queryAllDeviceStatus,
        queryCorporateAllUser:_queryCorporateAllUser,
        queryMaintenanceRecordById:_queryMaintenanceRecordById,
        addMaintainPlan:_addMaintainPlan,
        queryMyWaitAuditList:_queryMyWaitAuditList,
        batchDeleteMaintenanceRecord:_batchDeleteMaintenanceRecord,
        queryAllSpareParts:_queryAllSpareParts,
        maintainPlanSubmit:_maintainPlanSubmit,
        getTroubleRecordDetail:_getTroubleRecordDetail,
        auditTroubleRecord:_auditTroubleRecord,
        findRealSparts:_findRealSparts,
        saveSpareRel:_saveSpareRel,
        saveDeviceRel:_saveDeviceRel,
        queryAllDeviceSparesType:_queryAllDeviceSparesType,
        batchDeleteDeviceSpareRel:_batchDeleteDeviceSpareRel,
        queryOrder: function(queryParam, successFunc) {
            queryOrder(queryParam, successFunc);

        },

        getOrderList: function() {
            return  allOrderList;
        },

        getCurHandleOrder: function() {
            return  curHandleOrder;
        },

        setCurHandleOrder: function(order) {
            curHandleOrder = order;
        }
    };
});
