
myApp.directive("departMentManage",["departmentManageService",'$compile',function(departmentManageService,$compile){
    return{
        restrict: 'A',
        compile: function(tEle, tAttrs, transcludeFn) {
            return function(scope, ele, attrs){

            }
        }
    }
}]);