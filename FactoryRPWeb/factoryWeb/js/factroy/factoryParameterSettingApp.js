/**
 * Created by Administrator on 2016/5/25.
 */
var factoryParameterSettingApp = angular.module('myApp.factoryParameterSetting', [ 'ui.router','tm.pagination','ngResource'])
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.parameterSetting", {
                url: "/parameterSetting",
                showName:"参数设置",
                params: {'needAll': null},
                templateUrl: "views/factory/right.html",
                controller: 'factoryParameterSettingController'
            })
    })
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.contactCompany", {
                url: "/contactCompany",
                showName:"通用设置-往来单位",
                params: {'needAll': null},
                templateUrl: "views/factory/ContactCompany.html",
                controller: 'contactCompanySettingController'
            })
    })
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.repairGroup", {
                url: "/repairGroup",
                showName:"通用设置-维修工段/班组",
                params: {'needAll': null},
                templateUrl: "views/factory/repairGroup.html",
                controller: 'contactCompanySettingController'
            })
    })
	.config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.deviceManage", {
                url: "/deviceManage",
                showName:"设备管理",
                params: {'needAll': null},
                templateUrl: "views/factory/deviceManage.html",
                controller: 'deviceManageController'
            })
    })
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.sparePartsManage", {
                url: "/sparePartsManage",
                showName:"备件仓库-配件台账",
                params: {'needAll': null},
                templateUrl: "views/factory/deviceManage.html",
                controller: 'sparePartsManageController'
            })
    })
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.modelToolsManage", {
                url: "/modelToolsManage",
                showName:"工装模具-工装工具总账",
                params: {'needAll': null},
                templateUrl: "views/factory/deviceManage.html",
                controller: 'modelToolsManageController'
            })
    });
