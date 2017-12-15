/**
 * Created by Administrator on 2016/5/25.
 */
var authorizationApp = angular.module('myApp.authorizationManagement', [ 'ui.router','tm.pagination','ngResource'])
/*服务端接口地址*/
.constant('FF_API', {
    base: '/',      //工程路径
    baseTpl: 'views/'                       //模板路径
})
.config(function ($stateProvider, $urlRouterProvider ,FF_API) {
    $stateProvider
        .state("main.functionManage", {
            showName:"菜单管理",
            tabShow:true,
            url: "/functionManage",
            views:{
                'content@main':{
                    templateUrl:FF_API.baseTpl+'daianla-authorizationManagement/functionManage.html',
                    controller:'functionManageController'
                }
            }
        })
        .state("main.roleManage", {
            showName:"角色管理",
            tabShow:true,
            url: "/roleManage",
            views:{
                'content@main':{
                    templateUrl:FF_API.baseTpl+'daianla-authorizationManagement/roleManage.html',
                    controller:'roleManageController'
                }
            }
        })
        .state("main.departmentManage", {
            showName:"部门管理",
            tabShow:true,
            url: "/departmentManage",
            views:{
                'content@main':{
                    templateUrl:FF_API.baseTpl+'daianla-authorizationManagement/departmentManage.html',
                    controller:'departmentManageController'
                }
            }
        })
        .state("main.userManagement", {
            showName:"用户管理",
            tabShow:true,
            url: "/userManagement",
            views:{
                'content@main':{
                    templateUrl:FF_API.baseTpl+'daianla-authorizationManagement/userManage.html',
                    controller:'userManagementController'
                }
            }
        })
    })
    ;