/**
 * Created by SHYL on 2016/6/13.
 */
myApp.controller("HomeController", function($rootScope,UrlService,$cookies,$state, $scope, $resource, $timeout, $http,$location,$log,AuthorizationService,functionManageService,userManagementService) {
    $("body").removeAttr('id');
    $("body").css('background','#fff');
    if($location.path()=='/main/deviceManage'){
        console.log($("#menuLeft .leftmenu .deviceManage"));
        $("#menuLeft .leftmenu .deviceManage").removeClass('hide');
        $("#menuLeft .leftmenu .deviceManage").siblings().addClass('hide');
    }else{
        $('.leftmenu dd').eq(0).find('.menuson').css('display','block');
        $('.leftmenu dd').eq(0).siblings().find('.menuson').css('display','none');
        $('.leftmenu dd').eq(0).find('.menuson').children().eq(0).addClass('active');
        $("#menuLeft .leftmenu .deviceManage").addClass('hide');
        $("#menuLeft .leftmenu .deviceManage").siblings().removeClass('hide');
        $("#menuLeft .leftmenu .sparePartsManage").addClass('hide');
        $("#menuLeft .leftmenu .sparePartsManage").siblings().removeClass('hide');
        $("#menuLeft .leftmenu .modelToolsManage").addClass('hide');
        $("#menuLeft .leftmenu .modelToolsManage").siblings().removeClass('hide');
    }
    $scope.username=$cookies.get('username');
    /*登出 start*/
    $scope.onLogout = function(){
        $cookies.remove('token');
        $cookies.remove('username');
        $cookies.remove('corporateIdentify');
        $cookies.remove('requestSeqNo');
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
    $rootScope.isLogin = AuthorizationService.isLogined();
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

    // console.log('$rootScope.publicMenu',$rootScope.publicMenu);




});