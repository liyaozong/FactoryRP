/**
 * Created by Administrator on 2016/5/25.
 */
var authorizationApp = angular.module('myApp.authorizationManagement', [ 'ui.router','tm.pagination','ngResource'])

.config(function ($stateProvider, $urlRouterProvider) {
    $stateProvider
        .state("main.userManagement", {
            url: "/userManagement",
            showName:"用户管理",
            templateUrl: "views/daianla-authorizationManagement/userManage.html",
            controller: 'userManagementController'
            })
    })
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.roleManage", {
                url: "/roleManage",
                showName:"角色管理",
                templateUrl: "views/daianla-authorizationManagement/roleManage.html",
                controller: 'roleManageController'
            })
    })

    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.functionManage", {
                url: "/functionManage",
                showName:"菜单管理",
                templateUrl: "views/daianla-authorizationManagement/functionManage.html",
                controller: 'functionManageController'
            })
    })
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.departmentManage", {
                url: "/departmentManage",
                showName:"部门管理",
                templateUrl: "views/daianla-authorizationManagement/departmentManage.html",
                controller: 'departmentManageController'
            })
    })
    ;