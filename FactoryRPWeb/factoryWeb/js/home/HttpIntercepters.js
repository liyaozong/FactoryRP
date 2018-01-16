myApp.factory('HttpInterceptor', function($rootScope,$q,$window,$cookies,$injector) {
        var interceptor = {
            'request': function(config) {
                //config.headers.Cookie='token=
                if(config != null && config.params == null){
                    config.params = {};
                }

                if(config != null) {
                    config.timeout = Promise;
                }

                var token =  $cookies.get('token');
                if(token) {

                    config.params.token =token;
                }

                if(!$rootScope.loading) {
                    $rootScope.loading = 1;
                } else {
                    $rootScope.loading++;
                }

            	config.headers = config.headers || {};
                if (token) {
                  config.headers.token = token;
//                  config.headers.token = '1';
                }
                return config; 
            },
            response: function (response) {
                if(response && response.data && response.data.errorCode == '000000' && ($cookies.get('token')==''||$cookies.get('token')==null||$cookies.get('token')==undefined)){
                    $cookies.put('token', response.data.data.token);
                    $cookies.put('username', response.data.data.userName);//用户名
                    $cookies.put('corporateIdentify', response.data.data.corporateIdentify);//企业唯一标识
                    $cookies.put('requestSeqNo', response.data.requestSeqNo);//新版登录用户名
                    var rootScope = $injector.get('$rootScope');
                    var stateService = $injector.get('$state');
                    if(rootScope.stateBeforeLogin && rootScope.stateBeforeLogin.current &&  rootScope.stateBeforeLogin.current.name != 'login'){
                        stateService.go(rootScope.stateBeforeLogin.current, rootScope.stateBeforeLogin.params);
                    } else {
                        var AuthUserMenu=response.data.data.authUserMenuList;

                        // console.log($rootScope.AuthUserMenu,'+++++++++');
                        $rootScope.publicMenu=[];
                        AuthUserMenu.forEach(function (n,i) {
                            var menu={};
                            if(n.parentId==null||n.parentId==''||n.parentId==undefined){
                                menu={
                                    name:n.name,
                                    id:n.id,
                                    url:n.url,
                                    remark:n.remark,
                                    twoMenu:[]
                                };
                                AuthUserMenu.forEach(function (m,k) {
                                    var q={};
                                    // console.log(n.id,m.parentId,'=======');
                                    if(n.id==m.parentId){
                                        q={
                                            id:m.id,
                                            name:m.name,
                                            url:m.url,
                                            remark:m.remark,
                                            parentId:m.parentId
                                        };
                                        menu.twoMenu.push(q);
                                    }

                                });
                                $rootScope.publicMenu.push(menu);
                            }
                        });
                        var str11=angular.toJson($rootScope.publicMenu);
                        $cookies.put('menu',str11);
                        // console.log('$rootScope.publicMenu',$rootScope.publicMenu)
                        // console.log('response',$rootScope.AuthUserMenu);
                        stateService.go("main.home");
                    }
                    rootScope.stateBeforeLogin = null;
                } else if(response && response.data && response.data.errorCode == '000667' && response.data.errorMessage == '请登录'){
                    $cookies.remove('token');
                    $cookies.remove('username');
                    $cookies.remove('menu');

                    var rootScope = $injector.get('$rootScope');
                    if($injector.get('$rootScope').$state && $injector.get('$rootScope').$state.current && $injector.get('$rootScope').$state.current.name != 'login'){
                        rootScope.stateBeforeLogin = {current:$injector.get('$rootScope').$state.current, params:$injector.get('$rootScope').$state.params};
                    }
                    var stateService = $injector.get('$state');
                    stateService.go("login");
                }else if(response && response.data && response.data.status != '0' && response.data.message){
                    var rootScope = $injector.get('$rootScope');
                    rootScope.alertMsg = response.data.message;
                }else{
                   // console.log(1)
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