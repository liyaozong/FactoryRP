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
    return {
        saveContactCompany:_saveContactCompany,
        deleteContactCompany:_deleteContactCompany,
        addRepairGroup:_addRepairGroup,
        queryRepairGroupList:_queryRepairGroupList,
        deleteRepairGroup:_deleteRepairGroup,
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
