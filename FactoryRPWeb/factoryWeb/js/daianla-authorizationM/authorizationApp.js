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
                showName:"功能点管理",
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
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.areaManagel", {
                url: "/areaManagel",
                showName:"区域管理",
                templateUrl: "views/daianla-authorizationManagement/areaManagel.html",
                controller: 'areaManagelController'
            })
    })
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.appCheckSet", {
                url: "/appCheckSet",
                showName:"app数据关键词设置",
                templateUrl: "views/daianla-authorizationManagement/appCheck.html",
                controller: 'appSetController'
            })
    })

    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.teamManage", {
                url: "/teamManage",
                showName:"小组管理",
                templateUrl: "views/daianla-authorizationManagement/teamManage.html",
                controller: 'teamManageController'
            })
    })
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.repairOrderModel", {
                url: "/repairOrderModel",
                showName:"工单模板",
                templateUrl: "views/daianla-authorizationManagement/repairOrderModelManage.html",
                controller: 'repairOrderModelController'
            })
    });