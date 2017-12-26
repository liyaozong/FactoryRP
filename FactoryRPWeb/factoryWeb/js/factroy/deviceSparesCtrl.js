//参数设置
// 备件类型设置控制器
myApp.controller('deviceSparesCtrl',['$filter','$rootScope','$location','$scope','deviceSpares',function($filter,$rootScope,$location,$scope,deviceSpares){

    //新增 修改备件类型
    $scope.addSomeDS=function (id,parentId,name,type) {
        // console.log(id,parentId,name,type,'-----');
        $scope.name=type=='3'?name:'';
        if(type=='1'){
            $scope.dtTitle='添加同级备件类型';
        }else if(type=='2'){
            $scope.dtTitle='添加下级备件类型';
        }else if(type=='3'){
            $scope.dtTitle='修改该备件类型';
        }
        popupDiv('deviceSparesBalance');
        $scope.addSomeDSSure=function(){
            var pd={
                id:id,
                parentId:parentId?parentId:-1,
                name:$scope.name,
                requestTime:$filter('date')(new Date(),'yyyy-MM-dd HH:mm:ss')
            };
            // console.log(pd);
            if(type=='1'){
                console.log('添加同级');
                deviceSpares.addSameDeviceType(pd).success(function (data) {
                    // console.log(data,'data');
                    if(data.errorCode=='000000'){
                        hideDiv('deviceSparesBalance');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }else{
                        hideDiv('deviceSparesBalance');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }
                })
            }else if(type=='2'){
                console.log('添加下级');
                deviceSpares.addSubDeviceType(pd).success(function (data) {
                    // console.log(data,'data');
                    if(data.errorCode=='000000'){
                        hideDiv('deviceSparesBalance');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }else{
                        hideDiv('deviceSparesBalance');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }
                })
            }else if(type=='3'){
                console.log('编辑');
                deviceSpares.updateDeviceType(pd).success(function (data) {
                    // console.log(data,'data');
                    if(data.errorCode=='000000'){
                        hideDiv('deviceSparesBalance');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }else{
                        hideDiv('deviceSparesBalance');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }
                })
            }

        }
    };

    //删除用户
    $scope.deleteDS=function(id,name){
        console.log(id);
        $scope.deleteName=name;
        popupDiv('deleteDS');
        $scope.deleteDSSure=function(){
            deviceSpares.deleteDeviceType(id).success(function (data) {
                // console.log(data,'data');
                if(data.errorCode=='000000'){
                    hideDiv('deleteDS');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }else{
                    hideDiv('deleteDS');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }
            })
        }
    };
}]);
