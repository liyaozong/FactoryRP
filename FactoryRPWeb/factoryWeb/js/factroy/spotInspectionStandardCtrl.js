//点巡检


// 巡检标准控制器
myApp.controller('spotInspectionStandardCtrl',['$filter','$rootScope','$location','$scope','$cookies','spotInspectionStandard','deviceType','departmentManageService','factoryParameterSettingService',function($filter,$rootScope,$location,$scope,$cookies,spotInspectionStandard,deviceType,departmentManageService,factoryParameterSettingService){
    // console.log('巡检标准控制器');

    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 5
    };

    //查询设备类型
    deviceType.list().success(function (data) {
        console.log(data,'设备类型');
        $scope.deviceTypeLists=data.data;
    });

    //查询部门
    $scope.departmentManageLists = departmentManageService.getOrderList();

    //查询设备
    $scope.paginationConf_CC = {
        currentPage: 1,
        itemsPerPage: 5
    };
    $scope.onQuery_cc=function () {
        factoryParameterSettingService.deviceListInfo({
            useDept:$scope.addDepartmentManage,//使用部门
            deviceType:$scope.addDeviceType,//设备类型
            name:$scope.addDeviceType,//设备名称
            currentPage: $scope.paginationConf_CC.currentPage,
            itemsPerPage: $scope.paginationConf_CC.itemsPerPage
        }, function(response){
            if(response.data.totalCount>=1){
                $scope.paginationConf_CC.totalItems = response.data.totalCount;
                $scope.deviceListInfos=response.data.list
            }else{
                $scope.paginationConf_CC.totalItems = 0;
                $scope.deviceListInfos.length = 0;
            }
        });
    };

    //分页查询巡检标准
    $scope.spotInspectionStandardLists=[];
    $scope.onQuery=function () {
        var req={
            "currentPage": $scope.paginationConf.currentPage, //当前页码
            "itemsPerPage": $scope.paginationConf.itemsPerPage,//每页显示记录数
            relateDeviceType:$scope.relateDeviceType
        };
        spotInspectionStandard.list(req).success(function (data) {
            console.log(data,'巡检标准');
            if(data.data.totalCount>=1){
                $scope.paginationConf.totalItems = data.data.totalCount;
                $scope.spotInspectionStandardLists=data.data;
                console.log($scope.spotInspectionStandardLists);
                $timeout(function () {



                },500);
            }else{
                $scope.paginationConf.totalItems = 0;
                $scope.spotInspectionStandardLists.length = 0;
            }
        });
    };

    //条件分页查询巡检标准
    $scope.queryList=function () {
        $scope.onQuery()
    };

    //添加设备(打开弹出层)
    $scope.addDevice=function () {
        $scope.onQuery_cc();
        hideDiv('SISpopup');popupDiv('addDevicePop');
    };

    //保存添加设备弹出层数据
    $scope.changeCCSure=function () {
        var $inputCheck=$(".addDeviceCheck:checked");
        $inputCheck.each(function (i,n) {
            console.log(n,i);
            $scope.deviceListInfos.forEach(function (v) {
                console.log(v);
                if($(n).val()==v.id){

                }
            })
        })
    };

    //新增巡检标准
    $scope.openSIS=function (type) {
        $scope.SISType=type;
        $scope.SISTip='新增巡检标准';
        $scope.SISReq={
            name:'',
            sysb:1
        };
        popupDiv('SISpopup');
    };

    // popupDiv('SISpopup');///////////
    $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.onQuery);
    $scope.$watch('paginationConf_CC.currentPage + paginationConf_CC.itemsPerPage', $scope.onQuery_cc);

}]);


// 巡检计划控制器
myApp.controller('inspectionPlanCtrl',['$filter','$rootScope','$location','$scope','$cookies','queryCorporateAllUser','userManageMent',function($filter,$rootScope,$location,$scope,$cookies,queryCorporateAllUser,userManageMent){
    // console.log('巡检计划控制器');



}]);
