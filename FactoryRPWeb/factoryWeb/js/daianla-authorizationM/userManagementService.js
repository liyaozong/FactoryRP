authorizationApp.factory('userManagementService', function($resource, $log, UrlService) {
    var allOrderList=[];
    var curHandleOrder = {};

    var queryOrder = function(queryParam, successFunc){
//        var OrderInfo = $resource(UrlService.getUrl('authorization') + 'employee/queryEmployees');
        var OrderInfo = $resource(UrlService.getUrl('authorizationNew') + 'employee/employes');
//      var OrderInfo = $resource('http://192.168.1.235/security-service/employee');
        OrderInfo.save(queryParam,function(data){
            allOrderList.length = 0;

            if(data.obj.rowCount>=1){
                data.obj.datas.forEach(function(item) { 
                    allOrderList.push(item);
//                    console.log(allOrderList)
                });
            }

            successFunc(data);
        }, function(err){

        });
    };
    /*根据员工id号查询员工信息 editEmployee start*/
//    var _queryUserDetail = function(id, successFunc, failFunc){
    var _queryUserDetail = function(phone, successFunc, failFunc){
//        var UserDetailInfo = $resource(UrlService.getUrl('authorization') + 'employee/getEmployeeAndRoleVo');
        var UserDetailInfo = $resource(UrlService.getUrl('authorizationNew') + 'employee/'+phone);

//        return UserDetailInfo.get({'employeeId':id}, function(data){
        return UserDetailInfo.get(function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*根据员工id号查询员工信息 editEmployee end*/
    /*查询所有部门 list start queryDepartments*/
    var _queryDepartmentDetail = function(id, successFunc, failFunc){
//        var queryDepartments = $resource(UrlService.getUrl('authorization') + 'employee/queryDepartments');
        var queryDepartments = $resource(UrlService.getUrl('authorizationNew') + 'department');

//        return queryDepartments.get({'itemsPerPage':1000}, function(data){
        return queryDepartments.get(function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*查询所有部门 list end queryDepartments*/
    /*查询所有角色信息 list start getRolesByVo*/
    var _queryRolesByVo = function(id, successFunc, failFunc){
//        var queryRoles = $resource(UrlService.getUrl('authorization') + 'employee/getRolesByVo');
        var queryRoles = $resource(UrlService.getUrl('authorizationNew') + 'role');
//        return queryRoles.get({'itemsPerPage':1000}, function(data){
        return queryRoles.get({},function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*查询所有角色信息 list end getRolesByVo*/
    /**/
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
        queryUserDetail:_queryUserDetail,
        queryDepartmentDetail:_queryDepartmentDetail,
        queryRolesByVo:_queryRolesByVo

    };
});
