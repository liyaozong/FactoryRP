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
    });
