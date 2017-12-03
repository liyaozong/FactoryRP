var app = angular.module('myApp', ['ngResource']);
//'use strict';
//
//var app = angular.module("myApp", [ 'ngRoute','ngTouch','ngResource']);
//app.config(['$logProvider', function($logProvider) {
//    $logProvider.debugEnabled(true);
//}]);
//
//app.config(['$httpProvider', function($httpProvider) {
//        $httpProvider.interceptors.push('HttpInterceptor');
//    }]);
//
//app.run(['$rootScope', '$window', '$location', '$log', function ($rootScope, $window, $location, $log) {
//    var locationChangeStartOff = $rootScope.$on('$locationChangeStart', locationChangeStart);
//    var locationChangeSuccessOff = $rootScope.$on('$locationChangeSuccess', locationChangeSuccess);
//
//    var routeChangeStartOff = $rootScope.$on('$routeChangeStart', routeChangeStart);
//    var routeChangeSuccessOff = $rootScope.$on('$routeChangeSuccess', routeChangeSuccess);
//
//    function locationChangeStart(event) {
//        if(!$rootScope.loading) {
//            $rootScope.loading = 1;
//        } else {
//            $rootScope.loading++;
//        }
//    }
//
//    function locationChangeSuccess(event) {
//        if($rootScope.loading > 1) {
//            $rootScope.loading --;
//        } else {
//            $rootScope.loading = 0;
//        }
//    }
//
//    function routeChangeStart(event) {
//        if(!$rootScope.loading) {
//            $rootScope.loading = 1;
//        } else {
//            $rootScope.loading++;
//        }
//    }
//
//    function routeChangeSuccess(event) {
//        if($rootScope.loading > 1) {
//            $rootScope.loading --;
//        } else {
//            $rootScope.loading = 0;
//        }
//    }
//}]);