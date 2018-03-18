//用户管理
// 用户/用户组控制器
myApp.controller('userManagementCtrl',['$filter','$rootScope','$location','$scope','$cookies','queryCorporateAllUser','userManageMent','$timeout',function($filter,$rootScope,$location,$scope,$cookies,queryCorporateAllUser,userManageMent,$timeout){
    // console.log('用户管理控制器');
    //查询与企业用户列表
    queryCorporateAllUser.getData().success(function(data){
        // console.log('data',data.data.userRespList);
        $scope.userLists=data.data.userRespList;
    });

    //新增用户
    $scope.addUser=function () {
        // console.log('新增用户');
        popupDiv('addUserPop');
        $scope.addUsersSure=function(){
            var userAddReq={
                // corporateIdentify:$scope.corporateIdentify,
                password:$scope.password,
                userName:$scope.userName,
                requestTime:$filter('date')(new Date(),'yyyy-MM-dd HH:mm:ss')
            };
            var regData=[{
                val:userAddReq.corporateIdentify,
                required:true
            }];
            // console.log(userAddReq);
            userManageMent.addUser(userAddReq).success(function (data) {
                // console.log(data,'data');
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
    $scope.deleteUserBalance=function(id,name){
        $scope.companyName=name;
        popupDiv('deleteUser');
        $scope.deleteUserSure=function(){
            userManageMent.deleteUser(id).success(function (data) {
                if(data.errorCode=='000000'){
                    hideDiv('deleteUser');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }else{
                    hideDiv('deleteUser');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }
            });
        }
    };

    //为用户添加角色
    $scope.addUserRole=function (id,name) {
        $scope.userRoleLists=[];
        $scope.addUserRoleName=name;
        //每次进入获取角色列表
        var requestSeqNo=$cookies.get('corporateIdentify');
        userManageMent.queryRoles(requestSeqNo).success((function (data) {
            $scope.userRoleLists=data.data;
            $timeout(function () {
                popupDiv('addUserRolePop'); },200)
        }));
        var arr=[];
        $scope.addUserRoleSure=function () {
            $('.userRoleCheckBox:checked').each(function (i,n) {
                arr.push($(n).attr('id'));

            });

            var po={
                requestTime:$filter('date')(new Date(),'yyyy-MM-dd HH:mm:ss'),
                roleIdList:arr,
                userId:id
            };
            userManageMent.addUserRole(po).success((function (data) {
                if(data.errorCode=='000000'){
                    hideDiv('addUserRolePop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }else{
                    hideDiv('addUserRolePop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }
            }));
        }
    };

    //为用户删除角色
    $scope.delUserRole=function (id,name) {
        $scope.delUserRoleLists=[];
        $scope.delUserRoleName=name;
        //每次进入获取角色列表
        userManageMent.queryRoleByUserId(id).success((function (data) {
            $scope.delUserRoleLists=data.data;
            $timeout(function () {
                popupDiv('delUserRolePop');
            },200)
        }));
        var arr=[];
        $scope.delUserRoleSure=function () {
            $('.userRoleCheckBoxDel:checked').each(function (i,n) {
                arr.push($(n).attr('id'));

            });

            var po={
                requestTime:$filter('date')(new Date(),'yyyy-MM-dd HH:mm:ss'),
                roleList:arr,
                userId:id
            };
            userManageMent.deleteUserRoleByUserId(po).success((function (data) {
                if(data.errorCode=='000000'){
                    hideDiv('delUserRolePop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }else{
                    hideDiv('delUserRolePop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }
            }));
        }
    };


}]);
