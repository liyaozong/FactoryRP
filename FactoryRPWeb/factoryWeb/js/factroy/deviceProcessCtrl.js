//参数设置
// 审核流程设置
myApp.controller('deviceProcessCtrl',['$filter','$rootScope','$location','$scope','deviceProcess','deviceType','departmentManageService',function($filter,$rootScope,$location,$scope,deviceProcess,deviceType,departmentManageService){

    //分页查询审核流程
    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 5
    };
    $scope.onQuery=function () {
        var req={
            "currentPage": $scope.paginationConf.currentPage, //当前页码
            "itemsPerPage": $scope.paginationConf.itemsPerPage//每页显示记录数
        };
        deviceProcess.list(req).then(function (data) {
            // console.log(data,'data');
            if(data.data.totalCount>=1){
                $scope.paginationConf.totalItems = data.data.totalCount;
                $scope.deviceProcessLists=data.data.list;
            }else{
                $scope.paginationConf.totalItems = 0;
                $scope.deviceProcessLists.length = 0;
            }
        });
    };
    $scope.onQuery();

    //查询设备类型
    deviceType.list().success(function (data) {
        $scope.deviceTypeList=data.data;
    });

    //查询部门
    $scope.departmentList = departmentManageService.getOrderList();

    //新增审核流程
    $scope.addDeviceProcess=function () {
        popupDiv('addDeviceProcessPop');
        $scope.formDeviceProcess={
            processName:'',
            processType:'',
            processStage:'',
            triggerConditionType:1,
            triggerCondition:'',
            processRemark:''
        };
        $scope.sureFlog=1;
        $scope.errFlog=false;
    };
    $scope.addDeviceProcessSure=function () {
        var addReq={

        };
        var flog;
        for(var i in addReq){
            console.log(i,":",addReq[i]);
            flog=validate.required(addReq[i]);
            if(!flog){
                break;
            }
        }
        if(flog){
            if($scope.sureFlog==1){
                console.log('新增');
                spareParts.addSpareParts(addReq).success(function (data) {
                    if(data.errorCode=='000000'){
                        hideDiv('addDeviceProcessPop');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }else{
                        hideDiv('addDeviceProcessPop');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }
                })
            }else if($scope.sureFlog==2){
                addReq.id=$scope.editSpareParts.id;
                console.log('编辑',addReq);
                spareParts.editSpareParts(addReq).success(function (data) {
                    if(data.errorCode=='000000'){
                        hideDiv('addDeviceProcessPop');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }else{
                        hideDiv('addDeviceProcessPop');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }
                })
            }
        }else {
            $scope.errFlog=true;
        }
    };



    $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.onQuery);
}]);
