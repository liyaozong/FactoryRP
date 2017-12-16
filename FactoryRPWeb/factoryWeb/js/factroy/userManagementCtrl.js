//用户管理
// 用户/用户组控制器
myApp.controller('userManagementCtrl',['$filter','$rootScope','$location','$scope','queryCorporateAllUser','userManageMent',function($filter,$rootScope,$location,$scope,queryCorporateAllUser,userManageMent){
    // console.log('用户管理控制器');
    //查询与企业用户列表
    queryCorporateAllUser.getData().success(function(data){
        console.log('data',data.data.userRespList);
        $scope.userLists=data.data.userRespList;
    });

    //新增用户
    $scope.addUser=function () {
        console.log('新增用户');
        popupDiv('addUserPop');
        $scope.addUsersSure=function(){
            var userAddReq={
                corporateIdentify:$scope.corporateIdentify,
                password:$scope.password,
                userName:$scope.userName,
                requestTime:$filter('date')(new Date(),'yyyy-MM-dd HH:mm:ss')
            };
            console.log(userAddReq);
            userManageMent.addUser(userAddReq).success(function (data) {
                console.log(data,'data');
                if(data.errorCode=='000000'){
                    hideDiv('addUserPop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }else{
                    hideDiv('addUserPop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }
            })
        }
    };

    //删除用户
    $scope.deleteUserBalance=function(id){
        console.log(id);
        popupDiv('deleteUser');
        $scope.deleteUserSure=function(){


            hideDiv('deleteUser');
            // popupDiv('SaveSuccess');
            // $('.SaveSuccess .Message').html(data.errorMessage);
        }
    };
}]);
