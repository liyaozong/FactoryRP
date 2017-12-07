'use strict';

// Declare app level module which depends on views, and components
var myApp = angular.module('myApp', [
    'ui.router',
    'ngResource',
    'ngCookies',
    'myApp.Common',
    'angularFileUpload',
    'myApp.authorizationManagement',//权限管理
    'myApp.consumerToBank',//消费贷银行对接
    'myApp.factoryParameterSetting'//参数设置
])
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
.config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("login", {
                url: "/login",
                showName:"登录",
                tabShow:false,
                templateUrl: "views/comm/login1.html",
                controller: 'LoginController'
            })
            .state("main", {
                resolve: {
                    menuPerssionss: function (AuthorizationService) {
                        return AuthorizationService.getMenuNewsPerssions();
                    }
                },
                showName:"首页",
                tabShow:false,
                url: "/main",
                templateUrl: "views/comm/home.html",
                controller: 'HomeController'
            })
            .state("main.home", {
                showName:"首页",
                tabShow:true,
                url: "/home",
                templateUrl: "views/comm/homePage.html",
                controller: 'HomeController'
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