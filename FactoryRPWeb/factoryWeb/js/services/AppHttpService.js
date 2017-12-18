"use strict";
/**
 * http服务在封装
 */
myApp.factory('AppHttp',['$http', '$timeout',"$rootScope",'$cookies',
    function($http, $timeout,$rootScope,$cookies) {
        return function(opt) {
            var token=$cookies.get('token')?$cookies.get('token'):'1';
            // console.log($cookies.get('token'));
            opt.headers={token:token};

            return $http(opt)
                .success(function (data, status, headers, config) {
                    // if (data.code == '300' || data.code == '500') {
                    //     $rootScope.loading=false;
                    //     $rootScope.indexErrorMsg=data.msg;
                    //     $rootScope.indexErrorMessage($rootScope.indexErrorMsg);
                    //
                    // }else if(data.code == '401')
                    // {
                    //     window.location=CC_API.path+"/user/loginOut.do";
                    // }
                })
                .error(function (data, status, headers, config) {
                    // $rootScope.loading=false;
                });
        };
}]);