authorizationApp.factory('roleManageService', function($resource, $log, UrlService) {

    var allOrderList=[];
    var curHandleOrder = {};

    var queryOrder = function(queryParam, successFunc){
//        var OrderInfo = $resource(UrlService.getUrl('authorization') + 'employee/getRolesByVo');
        var OrderInfo = $resource(UrlService.getUrl('authorizationNew') + 'role/role/keywords');
        OrderInfo.save(queryParam, function(data){
            allOrderList.length = 0;

            if(data.obj.rowCount>=1&&data.status==0&&data.obj.datas!=null){
                data.obj.datas.forEach(function(item) {
                    allOrderList.push(item);
                });
            }

            successFunc(data);
        }, function(err){

        });
    };
    /*查询角色列表  end*/
    /*根据角色id号查询角色信息 editRole start*/
    var _queryRoleDetail = function(roleId, successFunc, failFunc){
        var roleDetailInfo = $resource(UrlService.getUrl('authorization') + 'employee/getRoleAndPermissionsByRoleId');

        return roleDetailInfo.get({'roleId':roleId}, function(data){
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*根据角色id号查询角色信息 editRole end*/
    /*新增角色  start*/
    var _addRoleInfo = function(para, successFunc){
        var addRole = $resource(UrlService.getUrl('authorization') + 'employee/addRoleByVo');

        addRole.get(para,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*新增角色 end*/
    /*查询组（group）权限列表信息以及列表下的功能点  start*/
    var _queryGroupListInfo = function(itemsPerPage,successFunc){
        var queryGroupList = $resource(UrlService.getUrl('authorization') + 'employee/getPermissionAndChildrenVos');

        queryGroupList.get({"itemsPerPage":itemsPerPage},function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*查询组（group）权限列表信息以及列表下的功能点 end*/
    /*查询所有菜单  start*/
    var _queryAllMenus = function(para, successFunc){
        var AllMenus = $resource(UrlService.getUrl('authorizationNew') + 'permission');

        AllMenus.get(para,function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {

        });
    };
    /*查询所有菜单 end*/
    return {
        queryRoleDetail:_queryRoleDetail,
        queryGroupListInfo:_queryGroupListInfo,
        addRoleInfo:_addRoleInfo,
        queryAllMenus:_queryAllMenus,
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

