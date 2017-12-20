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
        base: 'http://47.96.28.88:9550',      //工程路径
        baseTpl: 'views/'                 ,      //模板路径
        queryCorporateAllUserPath:'/api/authorization/queryCorporateAllUser' ,  //查询所有企业用户
        addUserPath:'/api/authorization/addUser',   //添加企业用户
        addUserRolePath:'/api/authorization/addUserRole',   //为用户添加角色
        queryRoleByUserIdPath:'/api/authorization/queryRoleByUserId',   //为用户添加角色
        queryRolesPath:'/api/authorization/queryRoles',   //根据企业标示查询角色
        queryCorporateMenuPath:'/api/authorization/queryCorporateMenu',   //根据企业标示查询菜单
        addMenuPath:'/api/authorization/addMenu',   //根据企业标示查询菜单
        deleteUserPath:'' ,  //删除某个用户
        deviceTypeListPath:'/api/deviceType/list' ,  //查询设备类型列表
        addSameDeviceTypePath:'/api/deviceType/addSameDeviceType' ,  //添加同级设备类型
        deleteDeviceTypePath:'/api/deviceType/deleteDeviceType' ,  //删除设备类型
        updateDeviceTypePath:'/api/deviceType/updateDeviceType' ,  //修改设备类型
        addSubDeviceTypePath:'/api/deviceType/addSubDeviceType'   //添加下级设备类型


    })
.run(['$rootScope', '$window', '$location', '$log','$injector', function ($rootScope, $window, $location, $log, $injector) {

        $rootScope.$on('$stateNotFound',
            function(event, unfoundState, fromState, fromParams){
                console.log(unfoundState.to); // "lazy.state"
                console.log(unfoundState.toParams); // {a:1, b:2}
                console.log(unfoundState.options); // {inherit:false} + default options
            });

        $rootScope.$on('$stateChangeStart',
            function(event, toState, toParams, fromState, fromParams){
              //  $rootScope.$state.go("login");
             //   event.preventDefault();
                // transitionTo() promise will be rejected with
                // a 'transition prevented' error
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
  $urlRouterProvider.when("", "/main/home");
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

