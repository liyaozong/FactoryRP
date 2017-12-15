app.factory('HttpInterceptor', function($rootScope,$q,$window) {
        var interceptor = {
            'request': function(config) {
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

            // �������˴�������ܴӴ����лָ������Է���һ���µ������promise
            // ���µ�promise
            // ���ߣ�����ͨ������һ��rejection����ֹ��һ��
            // return $q.reject(rejection);
            'requestError': function(rejection) {
                if($rootScope.loading > 1) {
                    $rootScope.loading --;
                } else {
                    $rootScope.loading = 0;
                }
                return response;
            },

            // �������˴�������ܴӴ����лָ������Է���һ���µ���Ӧ��promise
            // ���ߣ�����ͨ������һ��rejection����ֹ��һ��
            // return $q.reject(rejection);
            // ���µ�promise
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