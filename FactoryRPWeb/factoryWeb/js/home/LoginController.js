/**
 * Created by SHYL on 2016/6/13.
 */
myApp.controller("LoginController", function($rootScope,UrlService,$cookies,$state, $scope, $resource, $timeout, $http,$location,$log,AuthorizationService,userManagementService) {
    $rootScope.isLogin = AuthorizationService.isLogined();
    $scope.alertMsg ='';
    $scope.curFooter = "items";
    /*新版权限系统相关权限 start*/
    $scope.WebURL=UrlService.getUrl('authorizationNew');
    $scope.userPhones=$cookies.get('username');

    $("#userName").focus(function(){
        $(".loginErrMessage").hide();
        $(".loginErrMessage").html('');
        $("#loginButton").removeAttr('disabled');
        $("#loginButton").html('登录');
    });
    $("#passWord").focus(function(){
        $(".loginErrMessage").hide();
        $(".loginErrMessage").html('');
        $("#loginButton").removeAttr('disabled');
        $("#loginButton").html('登录');
    });
    /*登录start*/
    $scope.username=$("#userName").val();
    $scope.imgCode=$("#imgCode").val();
    $scope.phoneCode=$("#phoneCode").val();


    $scope.loginToFactory=function(){
        console.log('123456');
        if($("#username").val()==''||$("#username").val()==null||$("#username").val()==undefined){
            $("#username").focus();
        }else if($("#password").val()==''||$("#password").val()==null||$("#password").val()==undefined){
            $("#password").focus();
        }else if($("#corporateCode").val()==''||$("#corporateCode").val()==null||$("#corporateCode").val()==undefined){
            $("#corporateCode").focus();
        }else{
//            var pa={
//                username:$("#username").val(),
//                password:$("#password").val(),
//                corporateCode:$("#corporateCode").val()
//            };
//            var par={params:pa};
//            console.log(par,'-----');
//            $http.get(UrlService.getUrl('authorizationNew')+'login',par).success(function(data){
//                console.log(data,'123');
//                if(data != null && data.status == '0' && (data.errorCode == '000000' && data.errorMessage=="成功")){
//                    $scope.userNick = data.obj.employee.phone;
//                    $("body").css('background-color','#fff');
//                    $("body").removeAttr('id');
////                    $state.go('main.home',{'userName':$scope.userNick});
//                    console.log('登陆成功');
//                }else{
////                    alert(data.message);
//                    $("#loginButton").removeAttr('disabled');
//                    $(".loginErrMessage").show();
//                    $(".loginErrMessage").html(data.errorMessage);
//                    $("#loginButton").val('登录');
//                }
//            }).error(function(response){
//                alert(response.message);
//            });
            AuthorizationService.doLoginNew({
                username:$("#username").val(),
                password:$("#password").val(),
                corporateCode:$("#corporateCode").val()
            },function(data){
                if(data != null && data.status == '0' && (data.errorCode == '000000' && data.errorMessage=="成功")){
                    $scope.userNick = data.obj.employee.phone;
                    $("body").css('background-color','#fff');
                    $("body").removeAttr('id');

                }else{

                    $("#loginButton").removeAttr('disabled');
                    $(".loginErrMessage").show();
                    $(".loginErrMessage").html(data.errorMessage);
                    $("#loginButton").val('登录');
                }
            }, function(err){

            });
        }
    };

    $("body").keydown(function(e) {
        var theEvent = window.event || e;
        var code = theEvent.keyCode || theEvent.which;
        if (code == "13") {//keyCode=13是回车键
            if($("#userName").val()==''||$("#userName").val()==null||$("#userName").val()==undefined){
                $("#userName").focus();
            }else if($("#imgCode").val()==''||$("#imgCode").val()==null||$("#imgCode").val()==undefined){
                $("#imgCode").focus();
            }else if($("#phoneCode").val()==''||$("#phoneCode").val()==null||$("#phoneCode").val()==undefined){
                $("#phoneCode").focus();
            }else{
                $("#loginButton").attr('disabled','disabled');
                $("#loginButton").html('登录中......');
                /*******登录加密 start*******/
                //  var loginMd=$("#passWord").val();
//            hash = faultylabs.MD5($scope.password);
//
//            b = base64.encode64(hash);
////            console.log(b);
                /*******登录加密 end****/
                AuthorizationService.doLogin($scope.username, $scope.imgCode, $scope.phoneCode,function(data){
                    if(data != null && data.status == '0' && (data.code == 'LOGIN_SUCESS' || data.code=="RE_LOGIN_ERROR")){
//                    $scope.userNick = '1111';
                        $scope.userNick = data.obj.username;
//                    alert(1);
//                    $state.go('main.home',{'userName':$scope.username});
                    }else{
//                    alert(data.message);
                        $("#loginButton").removeAttr('disabled');
                        $(".loginErrMessage").show();
                        $(".loginErrMessage").html(data.message);
                        $("#loginButton").html('登录');
                    }
                }, function(err){
                    // $rootScope.isLogin = false;
                });
            }
        }
    });
    /*登录end*/
    $rootScope.commonTips = '12332';
    $scope.onCloseTips = function(){
        $rootScope.commonTips = '';
    };
    /*登出 start*/
    $scope.onLogout = function(){
        $cookies.remove('JSESSIONID');
        $state.go("login");
//        AuthorizationService.doLogout(function(data){
//            console.log(data)
//            if(data != null && data.status == '0' && (data.code == 'LOGIN_SUCESS' || data.code=="LOGOUT_SUCESS")){
//                $rootScope.allStates = [];
//
//            }else{
//                alert(data.message);
//            }
//        }, function(err){
//            // $rootScope.isLogin = false;
//        });
    };
    /*登出 end*/
    /*修改密码 start*/
    $scope.updatePassword=function(){
        popupDiv('updatePassword');
        $("#oldPassword").val('');
        $("#newPassword").val('');
        $("#newAgainPassword").val('');
    };
    $scope.updatePasswordSubmit=function(){
        if($("#oldPassword").val()==null||$("#oldPassword").val()==''||$("#oldPassword").val()==undefined){
            $("#oldPassword").focus();
        }else if($("#newPassword").val()==null||$("#newPassword").val()==''||$("#newPassword").val()==undefined){
            $("#newPassword").focus();
        }else if($("#newAgainPassword").val()==null||$("#newAgainPassword").val()==''||$("#newAgainPassword").val()==undefined){
            $("#newAgainPassword").focus();
        }else if($("#newAgainPassword").val()!=$("#newPassword").val()){
            $('.updateErrMessage').show();
            $(".updateErrMessage").html('两次输入的密码不一致，请再次确认');
        }else{
            hash1 = faultylabs.MD5($scope.oldPassword);
            a = base64.encode64(hash1);
            hash = faultylabs.MD5($scope.newAgainPassword);
            b = base64.encode64(hash);
            var pa={
                oldPassword:a,
                newPassword:b
            };
//            console.log(pa);
//            console.log(1313131313);
            var par={params:pa};
            $http.get(UrlService.getUrl('authorization')+'employee/updatePassword',par).success(function(response){
                if(response.message=='重置密码成功'){
                    hideDiv('updatePassword');
                    popupDiv('UpdateSuccess');
                    $(".UpdateSuccess .Message").html(response.message);
                }else{
                    hideDiv('updatePassword');
                    popupDiv('UpdateError');
                    $(".UpdateError .Message").html(response.message);
                }
            }).error(function(response){
                alert(response.message);
            })
        }
    };
    /*修改密码 end*/
    $scope.onCloseState = function(state){
        if(!$rootScope.allStates){
            $rootScope.allStates = [];
        }

        for(var i = 0; i < $rootScope.allStates.length; i++) {
            if ($rootScope.allStates[i].state.name == state.name) {
                $rootScope.allStates.splice(i,1);
                if(state.name == $state.current.name){
                    if($rootScope.allStates.length > 0){
                        $state.go($rootScope.allStates[$rootScope.allStates.length - 1].state, $rootScope.allStates[$rootScope.allStates.length - 1].params);
                    } else {
                        $state.go("main.home");
                    }

                }
                break;
            }
        }

        if( $rootScope.allStates.length == 0){
            $state.go("main.home");
        }
    };

    $scope.onChangeState = function(stateItem){
        $state.go(stateItem.state, stateItem.params);

    };

    $scope.reload = function(){
        $state.reload($state.current);
    };

    $scope.$on('home_change', function(event,data) {
        $scope.curFooter = data;
    });

    $scope.$on('alertMsg', function(event,data) {
        $scope.alertMsg = data;

        $timeout(function(){
            $scope.alertMsg ='';
        }, 1000);
    });
});