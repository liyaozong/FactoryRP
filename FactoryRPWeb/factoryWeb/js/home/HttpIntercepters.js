myApp.factory('HttpInterceptor', function($rootScope,$q,$window,$cookies,$injector) {
        var interceptor = {
            'request': function(config) {
                //config.headers.Cookie='JSESSIONID=
                if(config != null && config.params == null){
                    config.params = {};
                }

                if(config != null) {
                    config.timeout = Promise;
                }

                var JSESSIONID =  $cookies.get('JSESSIONID');
                if(JSESSIONID) {

                    config.params.JSESSIONID =JSESSIONID;
                }

                if(!$rootScope.loading) {
                    $rootScope.loading = 1;
                } else {
                    $rootScope.loading++;
                }

            	config.headers = config.headers || {};
                if ($window.sessionStorage.token) {
                  config.headers.Authorization = $window.sessionStorage.token;
                }
                return config; 
            },
            response: function (response) {
                if(response && response.data && response.data.status == '0' && response.data.code == 'LOGIN_SUCESS'){
                    $cookies.put('JSESSIONID', response.data.obj.jsessionId);
//                    $cookies.put('username', response.data.obj.username);//老版登录用户名
                    $cookies.put('username', response.data.obj.employee.phone);//新版登录用户名
                    var rootScope = $injector.get('$rootScope');
                    var stateService = $injector.get('$state');
                    if(rootScope.stateBeforeLogin && rootScope.stateBeforeLogin.current &&  rootScope.stateBeforeLogin.current.name != 'login'){
                        stateService.go(rootScope.stateBeforeLogin.current, rootScope.stateBeforeLogin.params);
                    } else {
                        stateService.go("main.home");
                    }
                    rootScope.stateBeforeLogin = null;
                } else if(response && response.data  && response.data.status == '0' && response.data.code == 'LOGOUT_SUCESS'){
                    $cookies.remove('JSESSIONID');
                    $cookies.remove('username');

                    var rootScope = $injector.get('$rootScope');
                    if($injector.get('$rootScope').$state && $injector.get('$rootScope').$state.current && $injector.get('$rootScope').$state.current.name != 'login'){
                        rootScope.stateBeforeLogin = {current:$injector.get('$rootScope').$state.current, params:$injector.get('$rootScope').$state.params};
                    }
                    var stateService = $injector.get('$state');
                    stateService.go("login");
                } else if(response && response.data  && response.data.status == 'ERROR_NEED_LOGIN' && response.data.code == 'ERROR_NEED_LOGIN'){
                    $cookies.remove('JSESSIONID');

                    var rootScope = $injector.get('$rootScope');
                    if($injector.get('$rootScope').$state && $injector.get('$rootScope').$state.current && $injector.get('$rootScope').$state.current.name != 'login'){
                        rootScope.stateBeforeLogin = {current:$injector.get('$rootScope').$state.current, params:$injector.get('$rootScope').$state.params};
                    }
                    var stateService = $injector.get('$state');
                    stateService.go("login");
                    return $q.reject(response);
                } else if(response && response.data && response.data.status != '0' && response.data.message){
                    var rootScope = $injector.get('$rootScope');
                    rootScope.alertMsg = response.data.message;
                }

                if($rootScope.loading > 1) {
                    $rootScope.loading --;
                } else {
                    $rootScope.loading = 0;
                }
                if (response.status === 401) {
                  // handle the case where the user is not authenticated
                }
                return response || $q.when(response);
             },

            'requestError': function(rejection) {
                if($rootScope.loading > 1) {
                    $rootScope.loading --;
                } else {
                    $rootScope.loading = 0;
                }
                return response;
            },

            'responseError': function(rejection) {
                if($rootScope.loading > 1) {
                    $rootScope.loading --;
                } else {
                    $rootScope.loading = 0;
                }
                return rejection;
            }
        };
        return interceptor;
    });
myApp.config(['$httpProvider', function($httpProvider) {
    $httpProvider.interceptors.push('HttpInterceptor');
}]);