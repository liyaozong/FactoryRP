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
        queryAlldDeviceTroubleType:_queryAlldDeviceTroubleType,
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
