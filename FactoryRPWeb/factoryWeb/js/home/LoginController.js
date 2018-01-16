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
    $("body").css({
        'background-color':'#1c77ac',
        'background-image':'../../images/light.png',
        'background-repeat':'no-repeat',
        'background-position':'center top',
        'overflow':'hidden'
    });
    /*登录start*/
    $scope.loginToFactory=function(){
        if($("#username").val()==''||$("#username").val()==null||$("#username").val()==undefined){
            $("#username").focus();
        }else if($("#password").val()==''||$("#password").val()==null||$("#password").val()==undefined){
            $("#password").focus();
        }else if($("#corporateCode").val()==''||$("#corporateCode").val()==null||$("#corporateCode").val()==undefined){
            $("#corporateCode").focus();
        }else{
            AuthorizationService.doLoginNew({
                username:$("#username").val(),
                password:$("#password").val(),
                corporateCode:$("#corporateCode").val()
            },function(data){
                // console.log('loginData',data);
                // $rootScope.AuthUserMenu=data.AuthUserMenu;
                if(data != null && (data.errorCode == '000000' && data.errorMessage=="成功")){
                    $scope.userNick = data.data.userName;
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
            if($("#username").val()==''||$("#username").val()==null||$("#username").val()==undefined){
                $("#username").focus();
            }else if($("#password").val()==''||$("#password").val()==null||$("#password").val()==undefined){
                $("#password").focus();
            }else if($("#corporateCode").val()==''||$("#corporateCode").val()==null||$("#corporateCode").val()==undefined){
                $("#corporateCode").focus();
            }else{
                AuthorizationService.doLoginNew({
                    username:$("#username").val(),
                    password:$("#password").val(),
                    corporateCode:$("#corporateCode").val()
                },function(data){
                    if(data != null && (data.errorCode == '000000' && data.errorMessage=="成功")){
                        $scope.userNick = data.data.userName;
                        $state.go('main.home',{'userName':$scope.userNick});
                    }else{
                        $("#loginButton").removeAttr('disabled');
                        $(".loginErrMessage").show();
                        $(".loginErrMessage").html(data.errorMessage);
                        $("#loginButton").val('登录');
                    }
                }, function(err){

                });
            }
        }
    });
    /*登录end*/
    $scope.onCloseTips = function(){
        $rootScope.commonTips = '';
    };
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