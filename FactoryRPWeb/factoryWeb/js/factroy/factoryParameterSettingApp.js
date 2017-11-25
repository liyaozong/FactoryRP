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
    ;
