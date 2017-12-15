//用户管理
// 用户/用户组控制器
myApp.controller('userManagementCtrl',['$rootScope','$location','$scope','queryCorporateAllUser',function($rootScope,$location,$scope,queryCorporateAllUser){
    // console.log('用户管理控制器');
    queryCorporateAllUser.getData().success(function(data){
        console.log('data',data.data.userRespList);
        $scope.userLists=data.data.userRespList;
    });

}]);
