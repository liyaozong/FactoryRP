/**
 * Created by SHYL on 2016/6/13.
 */
myApp.factory('AuthorizationService', function($resource,$state, $log, $cookies, UrlService) {
    var sessionId = '0e10a8b7-ed67-4491-a525-3691e3d2ef44';
    var isLogined = false;
    var menuPerssions = [];
    var menuPerssionss = [];
    var groupPerssions = [];
    var buttonPerssions = [];

    var _getSessionId = function(){
        return sessionId;
    }

    /*登录 start*/
    var _doLogin = function(username, imageCode,phoneCode, successFunc, failFunc){
        var Query = $resource(UrlService.getUrl('authorization') + 'login');

        return Query.get({'username':username, 'imageCode':imageCode,'phoneCode':phoneCode}, function(data){
            if(data != null && data.status == '0' && (data.code == 'LOGIN_SUCESS' || data.code == 'RE_LOGIN_ERROR')){
                userNick = data.obj.username;
            }else{
//                console.log(data);
            }
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*登录 end*/
    /*登录 新 start*/
    var _doLoginNew = function(params,successFunc, failFunc){
        var doLoginNew = $resource(UrlService.getUrl('authorizationNew') + 'login');
        doLoginNew.get(params,  function (data) {
            if (successFunc) {
                successFunc(data);
            }
        }, function (err) {
            if (failFunc) {
                failFunc(err);
            }
        });
    };
    /*登录 新 end*/
    /*登出 start*/
    var _doLogout = function(successFunc, failFunc){
        var Query = $resource(UrlService.getUrl('authorizationNew') + 'logout');

        return Query.get({}, function(data){
            if(data != null && data.status == '0' && data.code == 'LOGOUT_SUCESS'){
                userNick = null;
                $cookies.remove('JSESSIONID');
            }else{
//                console.log(data);
            }
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    }
    /*登出 end*/

    /*新菜单权限 start*/
    var _getMenuNewPerssions = function(id,successFunc, failFunc){
        var Query = $resource(UrlService.getUrl('authorizationNew') + 'permission');

        return Query.get({}, function(data){
            if(data.obj != null && data.status == '0'){
//                console.log(data);
                menuPerssions.length = 0;
                data.obj.forEach(function(item){
                    menuPerssions.push(item);
                });
            }else{
//                    alert(data.message);
//                popupDiv('SaveSuccess');
//                $(".Message").html(data.message);
                $state.go("login");
            }
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    }
    /*新菜单权限 end*/
    /*新菜单权限 start*/
//    var _getMenuNewsPerssions = function(id,successFunc, failFunc){
//        var Query = $resource(UrlService.getUrl('authorizationNew') + 'employee/15982498650');
//
//        return Query.get({}, function(data){
//            if(data.obj.permissions != null && data.status == '0'){
////                console.log(data);
//                menuPerssionss.length = 0;
//                data.obj.permissions.forEach(function(item){
//                    menuPerssionss.push(item);
//                });
//            }else{
////                    alert(data.message);
////                popupDiv('SaveSuccess');
////                $(".Message").html(data.message);
//                $state.go("login");
//            }
//            if(successFunc){
//                successFunc(data);
//            }
//        }, function(err){
//            if(failFunc){
//                failFunc(err);
//            }
//        });
//    }
    /*新菜单权限 end*/
    /*新分组权限 start*/
    var _getGroupNewPerssions = function(successFunc, failFunc){
        var Query = $resource(UrlService.getUrl('authorizationNew') + 'permission');

        return Query.get({}, function(data){
            if(data.obj != null && data.status == '0'){
                groupPerssions.length = 0;
                data.obj.forEach(function(item){
                    groupPerssions.push(item);
                });
            }else{
//                alert(data.message);
//                popupDiv('SaveSuccess');
//                $(".Message").html(data.message);
                $state.go("login");
            }
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    }

    var _checkPerssions = function(code) {
        var result  = false;
        buttonPerssions.forEach(function(item){
            if(item.percode == code) {
                result = true;
                return;
            }
        });
       return result;
    };
    /*分组权限 end*/


    /*新按钮权限 start*/
    var _getButtonNewPerssions = function(successFunc, failFunc){
        var Query = $resource(UrlService.getUrl('authorizationNew') + 'permission');

        return Query.get({}, function(data){
            if(data.obj != null && data.status == '0'){
                buttonPerssions.length = 0;
                data.obj.forEach(function(item){
                    buttonPerssions.push(item);
                });
            }else{
//                alert(data.message);
//                popupDiv('SaveSuccess');
//                $(".Message").html(data.message);
                $state.go("login");
            }
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*新按钮权限 end*/



    /*菜单权限 start*/
    var _getMenuPerssions = function(id,successFunc, failFunc){
        var Query = $resource(UrlService.getUrl('authorizationNew') + 'queryMenuByCorporateIdentify');

        return Query.get({}, function(data){
            if(data.obj != null && data.status == '0'){
//                console.log(data);
                menuPerssions.length = 0;
                data.obj.forEach(function(item){
                    menuPerssions.push(item);
                });
            }else{
//                    alert(data.message);
//                popupDiv('SaveSuccess');
//                $(".Message").html(data.message);
                $state.go("login");
            }
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    }
    /*菜单权限 end*/
    /*分组权限 start*/
    var _getGroupPerssions = function(successFunc, failFunc){
        var Query = $resource(UrlService.getUrl('authorization') + 'user/getGroupPerssions');

        return Query.get({}, function(data){
            if(data.obj != null && data.status == '0'){
                groupPerssions.length = 0;
                data.obj.forEach(function(item){
                    groupPerssions.push(item);
                });
            }else{
//                alert(data.message);
//                popupDiv('SaveSuccess');
//                $(".Message").html(data.message);
                $state.go("login");
            }
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    }
    /*按钮权限 start*/
    var _getButtonPerssions = function(successFunc, failFunc){
        var Query = $resource(UrlService.getUrl('authorization') + 'user/getButtonPerssions');

        return Query.get({}, function(data){
            if(data.obj != null && data.status == '0'){
                buttonPerssions.length = 0;
                data.obj.forEach(function(item){
                    buttonPerssions.push(item);
                });
            }else{
//                alert(data.message);
//                popupDiv('SaveSuccess');
//                $(".Message").html(data.message);
                $state.go("login");
            }
            if(successFunc){
                successFunc(data);
            }
        }, function(err){
            if(failFunc){
                failFunc(err);
            }
        });
    };
    /*按钮权限 end*/

    var _isLogined = function(){
        return isLogined;
    }

    var _getUsernick = function(){
        return $cookies.get("username");
    }
    return {
        getSessionId: _getSessionId,
        doLogin:_doLogin,
        doLoginNew:_doLoginNew,
        isLogined:_isLogined,
        doLogout:_doLogout,
        getButtonPerssions:_getButtonPerssions,
        getMenuPerssions:_getMenuPerssions,
        getGroupPerssions:_getGroupPerssions,
        getButtonNewPerssions:_getButtonNewPerssions,
        getMenuNewPerssions:_getMenuNewPerssions,
//        getMenuNewsPerssions:_getMenuNewsPerssions,
        getGroupNewPerssions:_getGroupNewPerssions,
        getUsernick:_getUsernick,
        checkPerssions:_checkPerssions
    };
});