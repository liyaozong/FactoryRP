/**
 * Created by SHYL on 2016/6/13.
 */
myApp.controller("LoginController", function($rootScope,UrlService,$cookies,$state, $scope, $resource, $timeout, $http,$location,$log,AuthorizationService,userManagementService) {
// Cloud Float...
    $("body").attr('id');
    $("body").css({
        'background-color':'#1c77ac',
        'background-image':'../../images/light.png',
        'background-repeat':'no-repeat',
        'background-position':'center top',
        'overflow':'hidden'
    });
    $rootScope.isLogin = AuthorizationService.isLogined();
    $scope.alertMsg ='';
    $scope.curFooter = "items";

    /*老版权限系统相关权限 start*/
//    $scope.WebURL=UrlService.getUrl('authorization');
//    $scope.menuPerssions = AuthorizationService.getMenuPerssions();
//    $scope.groupPerssions = AuthorizationService.getGroupPerssions();
//    $scope.buttonPerssions = AuthorizationService.getButtonPerssions();
    /*老版权限系统相关权限 end*/

    /*新版权限系统相关权限 start*/
    $scope.WebURL=UrlService.getUrl('authorizationNew');
    $scope.userPhones=$cookies.get('username');
    $scope.menuPerssions=[];
//    $scope.menuPerssions = AuthorizationService.getMenuNewPerssions();
    $scope.userPersonInfo= userManagementService.queryUserDetail($scope.userPhones,  function(data){
        if(data.status==0){
            if(data.obj!=null){
                //TODO-业务逻辑
                /*获取当前员工 拥有的菜单权限 start*/
//                $scope.menuPerssions = data.obj.permissions;
                if(data.obj.permissions!=null&&data.obj.permissions!=''&&data.obj.permissions!=undefined){
                    data.obj.permissions.forEach(function(item){
                        if(item!=null&&item!=''&&item!=undefined){
                            $scope.menuPerssions.push(item);
                        }
                    })
                }
                /*获取当前员工 拥有的菜单权限 end*/
            }else{
                console.log(data.message);
            }
        }else{
            console.log(data.message);
        }
    }, function(err){
        console.log(err)
    });
//    $scope.groupPerssions = AuthorizationService.getGroupNewPerssions();
//    $scope.buttonPerssions = AuthorizationService.getButtonNewPerssions();
    /*新版权限系统相关权限 end*/

    $scope.userNick = AuthorizationService.getUsernick();

    /*菜单显示隐藏 start*/
    $scope.showMenu=function(res,groupPerssion){
        if(groupPerssion.MenuShow){
            groupPerssion.MenuShow = false;
        } else {
            res.forEach(function(item){
                item.MenuShow = false;
            });
            groupPerssion.MenuShow = true;
        }
    };
    /*菜单显示隐藏 end*/
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
    /*获取图片验证码Old start*/
    $scope.changeImgCode=function(){
        if($("#userName").val()==''||$("#userName").val()==null||$("#userName").val()==undefined){
            $("#userName").focus();
        }else{
//            var updateAddressData;
            $("#imgCodes").attr('src',$scope.WebURL+'employee/getImageCode?username='+$("#userName").val()+'&dt_'+new Date());
        }
    };
    $scope.changeImgCode();
    $("#userName").blur(function(){
        if($(this).val()==''){
            $(this).focus();
        }else{
            $scope.changeImgCode();
        }
    });
    /*获取图片验证码Old end*/

    /*获取图片验证码New start*/
    $scope.changeImgCodeNew=function(){
        if($("#userPhone").val()==''||$("#userPhone").val()==null||$("#userPhone").val()==undefined){
            $("#userPhone").focus();
        }else{
            $("#imgCodes").attr('src',$scope.WebURL+'employee/patchca?phone='+$("#userPhone").val()+'&dt_'+new Date());
        }
    };
    $scope.changeImgCodeNew();
    $("#userPhone").blur(function(){
        if($(this).val()==''){
            $(this).focus();
        }else{
            $scope.changeImgCodeNew();
        }
    });
    /*获取图片验证码New end*/

    /*获取短信验证码Old start*/
    $scope.getPhoneCode=function(){
        if($("#userName").val()==''||$("#userName").val()==null||$("#userName").val()==undefined){
            $("#userName").focus();
        }else{
            $(".freeYzm").attr("disabled","disabled");
            $http.get($scope.WebURL+'employee/getPhoneCode?username='+$("#userName").val()).success(function(response){
//            var updateAddressData;
                if(response.status==1){
                    $(".loginErrMessage").show();
                    $(".loginErrMessage").html(response.message);
                    $(".freeYzm").removeAttr("disabled");
                }else{
                    $(".loginErrMessage").hide();
                    downtime();
                    console.log(response.obj);
                }
            }).error(function(response){
                console.log(response.message);
            })
        }
    };
    /*获取短信验证码Old end*/

    /*获取短信验证码New start*/
    $scope.getPhoneCodeNew=function(){
        if($("#userPhone").val()==''||$("#userPhone").val()==null||$("#userPhone").val()==undefined){
            $("#userPhone").focus();
        }else if($("#imgCode").val()==''||$("#imgCode").val()==null||$("#imgCode").val()==undefined){
            $("#imgCode").focus();
        }else{
            $(".freeYzm").attr("disabled","disabled");
            $http.post($scope.WebURL+'employee/smsCode',{
                phone:$("#userPhone").val(),
                imgCode:$("#imgCode").val()
            }).success(function(response){
//            var updateAddressData;
                if(response.status==1){
                    $(".loginErrMessage").show();
                    $(".loginErrMessage").html(response.message);
                    $(".freeYzm").removeAttr("disabled");
                }else{
                    $(".loginErrMessage").hide();
                    downtime();
                    console.log(response.obj);
                }
            }).error(function(response){
                console.log(response.message);
            })
        }
    };
    /*获取短信验证码New end*/

    /*登录Old start*/
    $scope.onLogin = function(){
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
//            b = base64.encode64(hash);
//            console.log(b);
            /*******登录加密 end****/
            AuthorizationService.doLogin($scope.username, $scope.imgCode, $scope.phoneCode,function(data){
                if(data != null && data.status == '0' && (data.code == 'LOGIN_SUCESS' || data.code=="RE_LOGIN_ERROR")){
                    $scope.userNick = data.obj.username;
//                    $state.go('main.home',{'userName':$scope.username});
                }else{
                    $("#loginButton").removeAttr('disabled');
                    $(".loginErrMessage").show();
                    $(".loginErrMessage").html(data.message);
                    $("#loginButton").html('登录');
                }
            }, function(err){
                // $rootScope.isLogin = false;
            });
        }
    };
    /*登录Old end*/

    /*登录New start*/
    $scope.onLoginNew = function(){
        if($("#userPhone").val()==''||$("#userPhone").val()==null||$("#userPhone").val()==undefined){
            $("#userPhone").focus();
        }else if($("#imgCode").val()==''||$("#imgCode").val()==null||$("#imgCode").val()==undefined){
            $("#imgCode").focus();
        }else if($("#passWord").val()==''||$("#passWord").val()==null||$("#passWord").val()==undefined){
            $("#passWord").focus();
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
            AuthorizationService.doLoginNew($("#userPhone").val(),$("#passWord").val(),function(data){
                if(data != null && data.status == '0' && (data.code == 'LOGIN_SUCESS' || data.code=="RE_LOGIN_ERROR")){
                    $scope.userNick = data.obj.employee.phone;
//                    $state.go('main.home',{'userName':$scope.userNick});
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
    };
    /*登录New end*/
    console.log($location.search());



    $scope.loginToFactory=function(){
        if($("#username").val()==''||$("#username").val()==null||$("#username").val()==undefined){
            $("#username").focus();
        }else if($("#password").val()==''||$("#password").val()==null||$("#password").val()==undefined){
            $("#password").focus();
        }else if($("#requestSeqNo").val()==''||$("#requestSeqNo").val()==null||$("#requestSeqNo").val()==undefined){
            $("#requestSeqNo").focus();
        }else{
            $("#loginButton").attr('disabled','disabled');
            $("#loginButton").val('登录中......');
            /*******登录加密 start*******/
            //  var loginMd=$("#passWord").val();
//            hash = faultylabs.MD5($scope.password);
//
//            b = base64.encode64(hash);
////            console.log(b);
            /*******登录加密 end****/
            var pa={
                username:$("#username").val(),
                password:$("#password").val(),
                requestSeqNo:$("#requestSeqNo").val()
            };
            var par={params:pa};
            $http.get(UrlService.getUrl('authorizationNew')+'login',par).success(function(data){
                if(data != null && data.status == '0' && (data.errorCode == '000000' && data.errorMessage=="成功")){
                    $scope.userNick = data.obj.employee.phone;
                    $("body").css('background-color','#fff');
                    $("body").removeAttr('id');
//                    $state.go('main.home',{'userName':$scope.userNick});
                }else{
//                    alert(data.message);
                    $("#loginButton").removeAttr('disabled');
                    $(".loginErrMessage").show();
                    $(".loginErrMessage").html(data.errorMessage);
                    $("#loginButton").val('登录');
                }
            }).error(function(response){
                alert(response.message);
            });
           /* AuthorizationService.doLoginNew({
                username:$("#username").val(),
                password:$("#password").val(),
                requestSeqNo:$("#requestSeqNo").val()
            },function(data){
                if(data != null && data.status == '0' && (data.errorCode == '000000' && data.errorMessage=="成功")){
                    $scope.userNick = data.obj.employee.phone;
                    $("body").css('background-color','#fff');
                    $("body").removeAttr('id');
//                    $state.go('main.home',{'userName':$scope.userNick});
                }else{
//                    alert(data.message);
                    $("#loginButton").removeAttr('disabled');
                    $(".loginErrMessage").show();
                    $(".loginErrMessage").html(data.errorMessage);
                    $("#loginButton").val('登录');
                }
            }, function(err){
                // $rootScope.isLogin = false;
            });*/
        }
//        $state.go('main.home');
//        console.log($location.search());
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