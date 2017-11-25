authorizationApp.factory('teamManageService', function($resource, $log, UrlService) {


    var allOrderList=[];
    var curHandleOrder = {};

    var queryOrder = function(queryParam, successFunc){
//        alert(1);
        var OrderInfo = $resource(UrlService.getUrl('authorization') + 'employee/queryPermissions');
        OrderInfo.get(queryParam, function(data){
//            alert(data);
            allOrderList.length = 0;

            if(data.obj.totalCount>=1){
                data.obj.permissionVos.forEach(function(item) {
                    allOrderList.push(item);
//                    console.log(allOrderList)
                });
            }

            successFunc(data);
        }, function(err){

        });
    };
     /*新增小组 start*/
    var _addGroup = function(parmas,postData,successFunc, failFunc){
        var addGroup = $resource(UrlService.getUrl('authorizationNewFenDan') + '/employeeGroup');

        return addGroup.save(parmas, postData,function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*新增小组 end*/
      /*获取所有小组 start*/
    var _getAllGroup = function(parmas, successFunc, failFunc){
        var getAllGroup = $resource(UrlService.getUrl('authorizationNewFenDan') + '/employeeGroup');
        return getAllGroup.get(parmas, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*获取所有小组 end*/
     /*获取业务类型 start*/
    var _getListProductType = function(parmas, successFunc, failFunc){
        var getListProductType  = $resource(UrlService.getUrl('authorizationNewFenDan') + '/distributionRecord/listProductType');
        return getListProductType .get(parmas, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*获取业务类型 end*/
     /*查询部门 start*/
    var _getDepartment = function(parmas, successFunc, failFunc){
        var getDepartment = $resource(UrlService.getUrl('authorizationNewFenDan') + '/department');
        return getDepartment.get(parmas, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*查询部门 end*/
    /*员工查询 start*/
    var _getEmployee = function(parmas, successFunc, failFunc){
        var getEmployee = $resource(UrlService.getUrl('authorizationNewFenDan') + '/employee');
        return getEmployee.get(parmas, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*员工查询 end*/
     /*区域查询 start*/
    var _getListProvince = function(parmas, successFunc, failFunc){
        var getListProvince = $resource(UrlService.getUrl('authorizationNewFenDan') + '/commonConfig/listProvince');
        return getListProvince.get(parmas, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*区域查询 end*/
     /*删除小组列表 start*/
    var _removeGroup = function(parmas, postData,code,successFunc, failFunc){
        var removeGroup = $resource(UrlService.getUrl('authorizationNewFenDan') + '/employeeGroup/remove/'+code);
        return removeGroup.save(parmas,postData, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*删除小组列表 end*/
     /*查询小组详情 start*/
    var _getGroupInfo = function(parmas,code,successFunc, failFunc){
        var getGroupInfo = $resource(UrlService.getUrl('authorizationNewFenDan') + 'employeeGroup/'+code);
        return getGroupInfo.get(parmas,function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*查询小组详情 end*/
    return {
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
        },
        addGroup:_addGroup,
        getAllGroup:_getAllGroup,
        getDepartment:_getDepartment,
        getListProvince:_getListProvince,
        removeGroup:_removeGroup,
        getGroupInfo:_getGroupInfo,
        getListProductType :_getListProductType,
        getEmployee:_getEmployee


    };
});
