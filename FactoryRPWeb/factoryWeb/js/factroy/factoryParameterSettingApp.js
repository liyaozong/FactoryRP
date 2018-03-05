/**
 * Created by Administrator on 2016/5/25.
 */
var factoryParameterSettingApp = angular.module('myApp.factoryParameterSetting', [ 'ui.router','tm.pagination','ngResource'])
    .constant('FF_API', {
        base: '/',      //工程路径
        baseTpl: 'views/'                       //模板路径
    })
    .config(function ($stateProvider, $urlRouterProvider,FF_API) {
        $stateProvider
            //设备类型设置
            .state("main.parameterSetting", {
                url: "/parameterSetting",
                showName:"参数设置",
                params: {'needAll': null},
                templateUrl: "views/factory/right.html",
                controller: 'factoryParameterSettingController'
            })
            .state("main.contactCompany", {
                showName:"通用设置-往来单位",
                tabShow:true,
                url: "/contactCompany",
                views:{
                    'content@main':{
                        templateUrl:FF_API.baseTpl+'factory/ContactCompany.html',
                        controller:'contactCompanySettingController'
                    }
                }
            })
            .state("main.repairGroup", {
                showName:"通用设置-往来单位",
                tabShow:true,
                url: "/repairGroup",
                views:{
                    'content@main':{
                        templateUrl:FF_API.baseTpl+'factory/repairGroup.html',
                        controller:'contactCompanySettingController'
                    }
                }
            })
            .state("main.deviceManage", {
                showName:"设备管理",
                tabShow:true,
                url: "/deviceManage",
                views:{
                    'content@main':{
                        templateUrl:FF_API.baseTpl+'factory/deviceManage.html',
                        controller:'deviceManageController'
                    }
                }
            })
            .state("main.sparePartsManage", {
                showName:"备件仓库-配件台账",
                tabShow:true,
                url: "/sparePartsManage",
                views:{
                    'content@main':{
                        templateUrl:FF_API.baseTpl+'tpls/sparePartsManage.html',
                        controller:'sparePartsManageController'
                    }
                }
            })
            .state("main.modelToolsManage", {
                showName:"工装模具-工装工具总账",
                tabShow:true,
                url: "/modelToolsManage",
                views:{
                    'content@main':{
                        templateUrl:FF_API.baseTpl+'factory/deviceManage.html',
                        controller:'modelToolsManageController'
                    }
                }
            })
            .state("main.otherOptionsSetting", {
                showName:"参数设置-其他选项设置",
                tabShow:true,
                url: "/otherOptionsSetting",
                views:{
                    'content@main':{
                        templateUrl:FF_API.baseTpl+'factory/otherOptionsSetting.html',
                        controller:'otherOptionsSettingController'
                    }
                }
            })
            .state("main.InternalRepair", {
                showName:"故障报修（内修）",
                tabShow:true,
                url: "/InternalRepair",
                views:{
                    'content@main':{
                        templateUrl:FF_API.baseTpl+'factory/internalRepair.html',
                        controller:'InternalRepairController'
                    }
                }
            })
    });