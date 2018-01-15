//参数设置
// 故障类型设置控制器
myApp.controller('deviceTroubleTypeCtrl',['$filter','$rootScope','$location','$scope','deviceTroubleType',function($filter,$rootScope,$location,$scope,deviceTroubleType){

    //新增 修改故障类型
    $scope.addSomeDP=function (id,parentId,name,type) {
        $scope.name=type=='3'?name:'';
        if(type=='1'){
            $scope.dtTitle='添加同级故障类型';
        }else if(type=='2'){
            $scope.dtTitle='添加下级故障类型';
        }else if(type=='3'){
            $scope.dtTitle='修改该故障类型';
        }
        popupDiv('deviceTroubleTypeBalance');
        $scope.addSomeDPSure=function(){
            var pd={
                id:id,
                parentId:parentId?parentId:-1,
                name:$scope.name,
                requestTime:$filter('date')(new Date(),'yyyy-MM-dd HH:mm:ss')
            };
            // console.log(pd);
            if(type=='1'){
                console.log('添加同级');
                deviceTroubleType.addSameDeviceTroubleType(pd).success(function (data) {
                    // console.log(data,'data');
                    if(data.errorCode=='000000'){
                        hideDiv('deviceTroubleTypeBalance');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }else{
                        hideDiv('deviceTroubleTypeBalance');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }
                })
            }else if(type=='2'){
                console.log('添加下级');
                deviceTroubleType.addSubDeviceTroubleType(pd).success(function (data) {
                    // console.log(data,'data');
                    if(data.errorCode=='000000'){
                        hideDiv('deviceTroubleTypeBalance');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }else{
                        hideDiv('deviceTroubleTypeBalance');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }
                })
            }else if(type=='3'){
                console.log('编辑');
                deviceTroubleType.updateDeviceTroubleType(pd).success(function (data) {
                    // console.log(data,'data');
                    if(data.errorCode=='000000'){
                        hideDiv('deviceTroubleTypeBalance');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }else{
                        hideDiv('deviceTroubleTypeBalance');
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
            deviceTroubleType.deleteDeviceTroubleType(id).success(function (data) {
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
