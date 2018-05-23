/**
 * Created by Administrator on 2016/5/26.
 */
var myApp = angular.module('myApp.Common', [
    'ui.router',
    'ngResource'
]).factory('UrlService', function($resource, $log) {
    var allUrlInfo=[];
    /*开发环境 start*/
    allUrlInfo.push({name: 'authorizationNew',  url: httpUrl + '/api/authorization/'});//系统权限相关接口
    allUrlInfo.push({name: 'factoryServe',  url: httpUrl + '/api/'});//系统权限相关接口
    allUrlInfo.push({name: 'mainServe',  url: httpUrl + '/main/'});//main控制器
    allUrlInfo.push({name: 'troubleRecordServe',  url: httpUrl + '/'});//main控制器
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







