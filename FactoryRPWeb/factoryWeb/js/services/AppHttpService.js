"use strict";
/**
 * http服务在封装
 */
myApp.factory('AppHttp',['$http', '$timeout',"$rootScope",'$cookies',
    function($http, $timeout,$rootScope,$cookies) {
        return function(opt) {
            // var token=$cookies.get('token')?$cookies.get('token'):null;
            // // console.log(opt);
            // // opt.headers={'token':token};
            // if(opt.data)
            // {
            //     opt.data.token=token;
            // }else{
            //     opt.data={};
            //     opt.data.token=token;
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

//表单验证服务
myApp.factory('validate',["$rootScope",function ($rootScope) {
    var validate={};
    validate.regExp=function(s,reg){
        var re = new RegExp(reg);
        if (re.test(s)) {
            return true;
        }else{
            return false;
        }
    };
    validate.required=function (s) {
        if(s==undefined||s=="undefined"||s==''||s==null){
            return false
        }else {
            return true;
        }
    };

    return validate;
}]);

//localStorage 服务封装
myApp.factory('locals', ['$window', function($window){
    return{
        set: function(key,value){ //存储单个属性
            // 判断是否无痕浏览模式
            if (typeof localStorage === 'object') {
                try {
                    $window.localStorage[key] = escape(value);
                } catch (e) {
                    alert('Your web browser does not support storing settings locally. In Safari, the most common cause of this is using "Private Browsing Mode". Some settings may not save or some features may not work properly for you.');
                }
            }
        },
        get: function(key){  //读取单个属性
            // 判断是否无痕浏览模式
            if (typeof localStorage === 'object') {
                try {
                    return unescape($window.localStorage[key]);
                } catch (e) {
                    alert('Your web browser does not support storing settings locally. In Safari, the most common cause of this is using "Private Browsing Mode". Some settings may not save or some features may not work properly for you.');
                }
            }
        },
        getParam: function(c){ //读取单个属性中的某个属性
            var cookieValue = this.get(c);
            var theRequest = {};
            if(cookieValue){
                if (cookieValue.indexOf("&") != -1) {
                    var str = cookieValue.split("&");
                    for (var i = 0; i < str.length; i++) {
                        theRequest[str[i].split("=")[0]] = str[i].split("=")[1];
                    }
                }else{
                    theRequest[cookieValue.split("=")[0]] = cookieValue.split("=")[1];
                }
                return theRequest;
            }else{
                return false;
            }
        },
        setObject: function(key,value){ //存储对象，以JSON格式存储
            $window.localStorage[key] = JSON.stringify(value);
        },
        getObject: function (key) {  //读取对象
            return JSON.parse($window.localStorage[key] || '{}');
        },
        remove: function(key){ //删除缓存
            $window.localStorage[key] = '';
            delete $window.localStorage[key];
        }
    }
}]);