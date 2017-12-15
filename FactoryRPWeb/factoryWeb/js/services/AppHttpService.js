"use strict";
/**
 * http服务在封装
 */
app.factory('AppHttp',['$http', '$timeout',"$rootScope","CC_API",
    function($http, $timeout,$rootScope,ngDialog,CC_API) {
        return function(opt) {
            // if(opt.data)
            // {
            //     opt.data.visitType='APP006';
            // }else{
            //     opt.data={};
            //     opt.data.visitType='APP006';
            // }
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