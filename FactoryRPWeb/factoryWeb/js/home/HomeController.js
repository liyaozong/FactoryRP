/**
 * Created by SHYL on 2016/6/13.
 */
myApp.controller("HomeController", function($rootScope,UrlService,$cookies,$state, $scope, $resource, $timeout, $http,$location,$log,AuthorizationService,userManagementService) {
    $("body").css({
        'background-color':'#fff',
        'background-image':'none',
        'background-repeat':'no-repeat',
        'background-position':'center top',
        'overflow':'hidden'
    });
    clearInterval(aa);
    clearInterval(bb);
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

    /*退出登录 start*/
    $scope.exitSystem=function(){
        $state.go('login');
        $("body").attr('id');
        $("body").css({
            'background-color':'#1c77ac',
            'background-image':'../../images/light.png',
            'background-repeat':'no-repeat',
            'background-position':'center top',
            'overflow':'hidden'
        })
    };
    /*退出登录 end*/
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
    if($location.path()=='/login'){
        $rootScope.allStates = [];
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