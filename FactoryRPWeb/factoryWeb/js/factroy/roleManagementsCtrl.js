//角色管理
myApp.controller('roleManagementsCtrl',['$filter','$rootScope','$location','$scope','deviceType',function($filter,$rootScope,$location,$scope,deviceType){

    //新增 修改设备类型
    $scope.addSomeDP=function (id,parentId,name,type) {
        $scope.name=type=='3'?name:'';
        if(type=='1'){
            $scope.dtTitle='添加同级设备类型';
        }else if(type=='2'){
            $scope.dtTitle='添加下级设备类型';
        }else if(type=='3'){
            $scope.dtTitle='修改该设备类型';
        }
        popupDiv('deviceTypeBalance');
        $scope.addSomeDPSure=function(){
            var pd={
                id:id,
                parentId:type=='1'?parentId:0,
                name:$scope.name,
                requestTime:$filter('date')(new Date(),'yyyy-MM-dd HH:mm:ss')
            };
            // console.log(pd);
            if(type=='1'){
                console.log('添加同级');
                deviceType.addSameDeviceType(pd).success(function (data) {
                    // console.log(data,'data');
                    if(data.errorCode=='000000'){
                        hideDiv('deviceTypeBalance');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }else{
                        hideDiv('deviceTypeBalance');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }
                })
            }else if(type=='2'){
                console.log('添加下级');
                deviceType.addSubDeviceType(pd).success(function (data) {
                    // console.log(data,'data');
                    if(data.errorCode=='000000'){
                        hideDiv('deviceTypeBalance');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }else{
                        hideDiv('deviceTypeBalance');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }
                })
            }else if(type=='3'){
                console.log('编辑');
                deviceType.updateDeviceType(pd).success(function (data) {
                    // console.log(data,'data');
                    if(data.errorCode=='000000'){
                        hideDiv('deviceTypeBalance');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }else{
                        hideDiv('deviceTypeBalance');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }
                })
            }

        }
    };

    //删除用户
    $scope.deleteDP=function(id,name){
        console.log(id);
        $scope.deleteName=name;
        popupDiv('deleteDP');
        $scope.deleteDPSure=function(){
            deviceType.deleteDeviceType(id).success(function (data) {
                // console.log(data,'data');
                if(data.errorCode=='000000'){
                    hideDiv('deleteDP');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }else{
                    hideDiv('deleteDP');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }
            })
        }
    };
}]);
