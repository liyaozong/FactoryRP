'use strict';

// Declare app level module which depends on views, and components
var myApp = angular.module('myApp', [
    'ui.router',
    'ngResource',
    'ngCookies',
    'myApp.Common',
    'angularFileUpload',
    'myApp.authorizationManagement',//权限管理
    'myApp.factoryParameterSetting'//参数设置
])
/*服务端接口地址*/
    .constant('FF_API', {
        baseApi: '/',      //本地环境工程路径
        base: 'http://47.96.28.88:9550',      //开发环境工程路径
        // base: 'http://39.104.71.127:9550',      //测试环境工程路径
        baseTpl: 'views/'                 ,      //模板路径
        queryCorporateAllUserPath:'/api/authorization/queryCorporateAllUser' ,  //查询所有企业用户
        departmentListPath:'/api/department/list' ,  //查询所有企业用户
        addUserPath:'/api/authorization/addUser',   //添加企业用户
        addUserRolePath:'/api/authorization/addUserRole',   //为用户添加角色
        queryRoleByUserIdPath:'/api/authorization/queryRoleByUserId',   //为用户查询角色
        deleteUserRoleByUserIdPath:'/api/authorization/deleteUserRoleByUserId',   //为用户删除角色
        queryRolesPath:'/api/authorization/queryRoles',   //根据企业标示查询角色
        queryCorporateMenuPath:'/api/authorization/queryCorporateMenu',   //根据企业标示查询菜单
        addRolePath:'/api/authorization/addRole',   //新增角色
        addMenuPath:'/api/authorization/addMenu',   //根据企业标示查询菜单
        queryByRoleIdPath:'/api/authorization/queryByRoleId',   //根据角色ID查询菜单
        addMenuRolePath:'/api/authorization/addMenuRole',   //为角色添加菜单
        deleteMenuRoleByRoleIdPath:'/api/authorization/deleteMenuRoleByRoleId',   //为角色删除菜单
        deleteUserPath:'/api/authorization/deleteUser' ,  //删除某个用户
        deleteRolePath:'/api/authorization/deleteRole' ,  //删除某个角色
        deleteMenuPath:'/api/authorization/deleteMenu' ,  //删除某个菜单
        deviceTypeListPath:'/api/deviceType/list' ,  //查询设备类型列表
        queryAllDeviceSparesTypePath:'/api/deviceSparesType/queryAllDeviceSparesType' ,  //查询备件类型列表
        addSameDeviceTypePath:'/api/deviceType/addSameDeviceType' ,  //添加同级设备类型
        addSameDeviceSparesPath:'/api/deviceSparesType/addSameDeviceSpares' ,  //添加同级备件类型
        deleteDeviceTypePath:'/api/deviceType/deleteDeviceType' ,  //删除设备类型
        deleteDeviceSparesTypePath:'/api/deviceSparesType/deleteDeviceSparesType' ,  //删除备件类型
        updateDeviceTypePath:'/api/deviceType/updateDeviceType' ,  //修改设备类型
        updateDeviceSparesPath:'/api/deviceSparesType/updateDeviceSpares' ,  //修改备件类型
        addSubDeviceTypePath:'/api/deviceType/addSubDeviceType' ,  //添加下级设备类型
        addSubDeviceSparesPath:'/api/deviceSparesType/addSubDeviceSpares',   //添加下级备件类型
        findByPagePath:'/api/spareParts/findByPage' ,  //查询备件
        addSparePartsPath:'/api/spareParts/addSpareParts' ,  //新增备件
        editSparePartsPath:'/api/spareParts/editSpareParts' ,  //编辑备件
        deleteSparePartsByIdPath:'/api/spareParts/deleteSparePartsById' ,  //单个删除备件
        deleteSparePartsByIdsPath:'/api/spareParts/deleteSparePartsByIds' ,  //批量删除备件
        queryAlldDeviceTroubleTypePath:'/api/deviceTroubleType/queryAlldDeviceTroubleType' ,  //查询所有故障类型
        addSameDeviceTroubleTypePath:'/api/deviceTroubleType/addSameDeviceTroubleType' ,  //添加同级故障类型
        addSubDeviceTroubleTypePath:'/api/deviceTroubleType/addSubDeviceTroubleType' ,  //添加下级故障类型
        deleteDeviceTroubleTypePath:'/api/deviceTroubleType/deleteDeviceTroubleType' ,  //删除故障类型
        updateDeviceTroubleTypePath:'/api/deviceTroubleType/updateDeviceTroubleType' ,  //修改故障类型
        contactCompanyListPath:'/api/contactCompany/list',   //查询往来单位
        findByCodePath:'/api/deviceParameterDictionary/findByCode'  , //查询设备参数
        addDeviceProcessPath:'/api/deviceProcess/addDeviceProcess'  , //新增审核流程
        queryAllDecviceProcessTypePath:'/api/deviceProcess/queryAllDecviceProcessType'  , //查询所有流程类型集合
        queryProcessDetailPath:'/api/deviceProcess/queryProcessDetail'  , //查询审核流程详细数据
        deviceProcessFindByPagePath:'/api/deviceProcess/findByPage',   //分页查询审核流程
        queryAllSpotInspectionItemsRecordTypePath:'/main/queryAllSpotInspectionItemsRecordType',   //分页查询巡检标准
        queryInspectionStandardDetailPath:'/api/spotInspectionStandard/queryInspectionStandardDetail',//查询巡检标准详情
        deleteInspectionStandardPath:'/api/spotInspectionStandard/deleteInspectionStandard',//单个删除巡检标准
        deleteSpotInspectionStandardByIdsPath:'/api/spotInspectionStandard/deleteSpotInspectionStandardByIds',//批量删除巡检标准
        addSpotInspectionStandardPath:'/api/spotInspectionStandard/addSpotInspectionStandard',//新增巡检标准
        spotInspectionStandardFindByPagePath:'/api/spotInspectionStandard/findByPage',   //分页查询巡检标准
        queryAllSpotInspectionPlanRecycleTypePath:'/main/queryAllSpotInspectionPlanRecycleType',   //巡检计划执行时间类型
        queryStanardByDeviceIdPath:'/api/spotInspectionStandard/queryStanardByDeviceId',   //根据部门ID查询巡检标准
        addSpotInspectionPlanPath:'/api/spotInspectionPlan/addSpotInspectionPlan',   //新增点检计划
        spotInspectionPlanFindByPagePath:'/api/spotInspectionPlan/findByPage',   //新增点检计划
        editSpotInspectionPlanPath:'/api/spotInspectionPlan/editSpotInspectionPlan',   //编辑点检计划
        findSpotInspectionStandardItemByStandardIdAndPlanIdPath:'/api/spotInspectionStandard/findSpotInspectionStandardItemByStandardIdAndPlanId',   //通过巡检标准ID和巡检计划ID进行查询巡检项目相关数据
        deleteSpotInspectionPlanDetailByPlanIdPath:'/api/spotInspectionPlan/deleteSpotInspectionPlanDetailByPlanId',   //单个删除点检计划
        QuerySpotInspectionPlanDetailByPlanIdPath:'/api/spotInspectionPlan/QuerySpotInspectionPlanDetailByPlanId',   //根据点检计划ID查询点检计划详情
        deleteSpotInspectionStandardByIdsIPPath:'/api/spotInspectionPlan/deleteSpotInspectionStandardByIds',   //批量删除点检计划
        querySpotInspectionRecordByPlanIdPath:'/api/spotInspectionRecord/querySpotInspectionRecordByPlanId',   //根据巡检ID查询巡检记录
        getxjsjPath:'service/getxjsj',   //巡检计划状态
        getxjlxPath:'service/getxjlx'   //巡检计划名称


    })
.run(['$rootScope', '$window', '$location', '$log','$injector','locals','$cookies','$state', function ($rootScope, $window, $location, $log, $injector,locals,$cookies,$state) {

        $rootScope.$on('$stateNotFound',
            function(event, unfoundState, fromState, fromParams){
                console.log(unfoundState.to); // "lazy.state"
                console.log(unfoundState.toParams); // {a:1, b:2}
                console.log(unfoundState.options); // {inherit:false} + default options
            });

        $rootScope.$on('$locationChangeStart',
            function(event, next){
                // var routerArr=crumbNav.getList();
                var stateUrl=$location.$$url.split('/')[2];
                // console.log($location.$$url.split('/')[1]);
                var loginUrl=$location.$$url.split('/')[1];
                $rootScope.stateUrlLi=stateUrl;
                var AuthUserMenu=$rootScope.publicMenu?$rootScope.publicMenu:locals.getObject('menu');
                $rootScope.publicMenu=AuthUserMenu;
                // console.log($rootScope.publicMenu);
                var flog=true;var crumbNavArr=[];$rootScope.topMenulists=[];
                // console.log(AuthUserMenu);
                if(AuthUserMenu!=undefined&&loginUrl!='login'){
                    // if(stateUrl=='home'){
                    //     $rootScope.publicTwoMenu=AuthUserMenu[1].twoMenu;
                    //     $rootScope.liMenu=AuthUserMenu[1].name;
                    //     $rootScope.topMenuLi='home';
                    //     AuthUserMenu.forEach(function (n,i) {
                    //         if(n.url==stateUrl){
                    //             flog=false;
                    //             crumbNavArr.push({'name':n.name});
                    //         }
                    //
                    //     });
                    //     if(flog){
                    //         $state.go('main.'+AuthUserMenu[0].url)
                    //     }
                    // }else {
                    //     AuthUserMenu.forEach(function (n,i) {
                    //         n.twoMenu.forEach(function (v,k) {
                    //             if(v.url==stateUrl){
                    //                 flog=false;
                    //                 // console.log(n,v,'---');
                    //                 $rootScope.publicTwoMenu=n.twoMenu;
                    //                 $rootScope.liMenu=n.name;
                    //                 $rootScope.topMenuLi=n.twoMenu[0].url;
                    //                 // console.log($rootScope.topMenuLi,'---');
                    //                 crumbNavArr=[{'name':n.name},{'name':v.name}];
                    //             }
                    //         });
                    //     });
                    //     if(flog){
                    //         $state.go('main.'+AuthUserMenu[0].url)
                    //     }
                    // }
                    AuthUserMenu.forEach(function (n,i) {
                        n.twoMenu.forEach(function (v,k) {
                            if(v.url==stateUrl){
                                flog=false;
                                // console.log(n,v,'---');
                                $rootScope.publicTwoMenu=n.twoMenu;
                                $rootScope.liMenu=n.name;
                                $rootScope.topMenuLi=n.twoMenu[0].url;
                                // console.log($rootScope.topMenuLi,'---');
                                crumbNavArr=[{'name':n.name},{'name':v.name}];
                            }
                        });
                    });
                    if(flog){
                        $state.go('main.'+AuthUserMenu[0].twoMenu[0].url)
                    }
                    $rootScope.urlLists=crumbNavArr;
                }
                // console.log($rootScope.publicTwoMenu)
            });

        $rootScope.$on('$stateChangeSuccess',
            function(event, toState, toParams, fromState, fromParams){
                if($injector == null){
                    return ;
                }

                if(toState.tabShow == false){
                    return;
                }

                var rootScope = $injector.get('$rootScope');
                if(!rootScope.allStates){
                    rootScope.allStates = [];
                }

                var remove = false;
                for(var i = 0; i < rootScope.allStates.length; i++){
                    if(rootScope.allStates[i].state.name == toState.name){
                        rootScope.allStates.splice(i,1);
                        rootScope.allStates.push({'state':toState, 'params':toParams});
                        remove = true;
                        break;
                    }
                }

                if(!remove){
                    rootScope.allStates.push({'state':toState, 'params':toParams});
                }

                //event.preventDefault();
                // transitionTo() promise will be rejected with
                // a 'transition prevented' error

            });

}])
.config(function ($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.when("", "/main/login");
  $urlRouterProvider.otherwise("/main/home");
})
.config(function ($stateProvider, $urlRouterProvider,FF_API) {
        $stateProvider
            .state("login", {
                url: "/login",
                showName:"登录",
                tabShow:false,
                templateUrl: "views/comm/login1.html",
                controller: 'LoginController'
            })
            .state("main", {
//                resolve: {
//                    menuPerssionss: function (AuthorizationService) {
//                        return AuthorizationService.getMenuNewsPerssions();
//                    }
//                },
                showName:"首页",
                tabShow:false,
                url: "/main",
                views: {
                    '': {
                        templateUrl: FF_API.baseTpl+"tpls/main.html",
                        controller: 'HomeController'
                    },
                    'topMenu@main':{
                        templateUrl:FF_API.baseTpl+'tpls/topMenu.html'
                    },
                    'leftMenu@main':{
                        templateUrl:FF_API.baseTpl+'tpls/leftMenu.html'
                    },
                    'crumbNav@main':{
                        templateUrl:FF_API.baseTpl+'tpls/crumbNav.html'
                    },
                    'mainBody@main':{
                        templateUrl:FF_API.baseTpl+'tpls/content.html'

                    }
                }
                // templateUrl: "views/comm/home.html",
//                templateUrl: "views/factory/index.html",
//                 controller: 'HomeController'
            })
            .state("main.home", {
                showName:"首页",
                tabShow:true,
                url: "/home",
                views:{
                    'content@main':{
                        templateUrl:'views/comm/homePage.html',
                        controller:'HomeController'
                    }
                }
                // templateUrl: "views/comm/homePage.html",
//                templateUrl: "views/factory/index.html",
//                 controller: 'HomeController'
            })
            //用户管理
                //用户／用户组
            .state("main.userManagements",{
                showName:"用户管理",
                tabShow:true,
                url:"/userManagements",
                views:{
                    'content@main':{
                        templateUrl:FF_API.baseTpl+'tpls/userManagement.html',
                        controller:'userManagementCtrl'
                    }
                }
            })
            // 菜单管理
            .state("main.menuManagements",{
                showName:"菜单管理",
                tabShow:true,
                url:"/menuManagements",
                views:{
                    'content@main':{
                        templateUrl:FF_API.baseTpl+'tpls/menuManagements.html',
                        controller:'menuManagementsCtrl'
                    }
                }
            })
            // 角色管理
            .state("main.roleManagements",{
                showName:"角色管理",
                tabShow:true,
                url:"/roleManagements",
                views:{
                    'content@main':{
                        templateUrl:FF_API.baseTpl+'tpls/roleManagements.html',
                        controller:'roleManagementsCtrl'
                    }
                }
            })

            // 设备类型设置
            .state("main.deviceType",{
                url:"/deviceType",
                views:{
                    'content@main':{
                        templateUrl:FF_API.baseTpl+'tpls/deviceType.html',
                        controller:'deviceTypeCtrl'
                    }
                }
            })

            // 备件类型设置
            .state("main.deviceSpares",{
                url:"/deviceSpares",
                views:{
                    'content@main':{
                        templateUrl:FF_API.baseTpl+'tpls/deviceSpares.html',
                        controller:'deviceSparesCtrl'
                    }
                }
            })

            // 审核流程设置
            .state("main.deviceProcess",{
                url:"/deviceProcess",
                views:{
                    'content@main':{
                        templateUrl:FF_API.baseTpl+'tpls/deviceProcess.html',
                        controller:'deviceProcessCtrl'
                    }
                }
            })

            //故障类型设置
            .state("main.deviceTroubleType",{
                url:"/deviceTroubleType",
                views:{
                    'content@main':{
                        templateUrl:FF_API.baseTpl+'tpls/deviceTroubleType.html',
                        controller:'deviceTroubleTypeCtrl'
                    }
                }
            })

            //点巡检
            //巡检标准
            .state("main.spotInspectionStandard",{
                url:"/spotInspectionStandard",
                views:{
                    'content@main':{
                        templateUrl:FF_API.baseTpl+'tpls/spotInspectionStandard.html',
                        controller:'spotInspectionStandardCtrl'
                    }
                }
            })

            //巡检计划
            .state("main.inspectionPlan",{
                url:"/inspectionPlan",
                views:{
                    'content@main':{
                        templateUrl:FF_API.baseTpl+'tpls/inspectionPlan.html',
                        controller:'inspectionPlanCtrl'
                    }
                }
            })

            //巡检记录
            .state("main.spotInspectionRecord",{
                url:"/spotInspectionRecord",
                views:{
                    'content@main':{
                        templateUrl:FF_API.baseTpl+'tpls/spotInspectionRecord.html',
                        controller:'spotInspectionRecordCtrl'
                    }
                }
            })

    })
.config(['$resourceProvider', function ($resourceProvider) {
      $resourceProvider.defaults.actions = {
        post: {method: 'POST', responseType: 'json', timeout:10000},
        get:    {method: 'GET', responseType: 'json',  timeout:10000},
        getAll: {method: 'GET', isArray:true, responseType: 'json', timeout:10000},
        update: {method: 'PUT', responseType: 'json',  timeout:10000},
        delete: {method: 'DELETE', responseType: 'json', timeout:10000},
        save: {method: 'POST', responseType: 'json', timeout:10000},
        query: {method: 'GET', isArray: true, responseType: 'json', timeout:10000},
        remove: {method: 'DELETE', responseType: 'json',  timeout:10000}
      };
    }])
// Angular File Upload module does not include this directive
// Only for example


/**
 * The ng-thumb directive
 * @author: nerv
 * @version: 0.1.2, 2014-01-09
 */
.directive('ngThumb', ['$window', function($window) {
    var helper = {
        support: !!($window.FileReader && $window.CanvasRenderingContext2D),
        isFile: function(item) {
            return angular.isObject(item) && item instanceof $window.File;
        },
        isImage: function(file) {
            var type =  '|' + file.type.slice(file.type.lastIndexOf('/') + 1) + '|';
            return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
        }
    };

    return {
        restrict: 'A',
        template: '<canvas/>',
        link: function(scope, element, attributes) {
            if (!helper.support) return;

            var params = scope.$eval(attributes.ngThumb);

            if (!helper.isFile(params.file)) return;
            if (!helper.isImage(params.file)) return;

            var canvas = element.find('canvas');
            var reader = new FileReader();

            reader.onload = onLoadFile;
            reader.readAsDataURL(params.file);

            function onLoadFile(event) {
                var img = new Image();
                img.onload = onLoadImage;
                img.src = event.target.result;
//                console.log("图片路径为>>>>");
//                console.log(img.src);
            }

            function onLoadImage() {
                var width = params.width || this.width / this.height * params.height;
                var height = params.height || this.height / this.width * params.width;
                canvas.attr({ width: width, height: height });
                canvas[0].getContext('2d').drawImage(this, 0, 0, width, height);
            }
        }
    };
}]);
