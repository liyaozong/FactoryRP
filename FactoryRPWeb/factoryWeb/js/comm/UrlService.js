/**
 * Created by Administrator on 2016/5/26.
 */
var myApp = angular.module('myApp.Common', [
    'ui.router',
    'ngResource'
]).factory('UrlService', function($resource, $log) {
    var allUrlInfo=[];
    /*开发环境 start*/
    allUrlInfo.push({name: 'authorizationNew',  url:'http://47.96.28.88:9550/api/authorization/'});//系统权限相关接口
    allUrlInfo.push({name: 'factoryServe',  url:'http://47.96.28.88:9550/api/'});//系统权限相关接口
    /*开发环境 end*/
    return {
        getUrl: function(module) {
            for (var i=0; i<allUrlInfo.length; i++){
                item = allUrlInfo[i];
                if(item.name.toLowerCase() == module.toLowerCase()){
                    return item.url;
                }
            }
        }
    };
});







