//巡检记录控制器
myApp.controller('spotInspectionRecordCtrl',['$filter','$rootScope','$location','$scope','$cookies','departmentManageService','inspectionPlan','queryCorporateAllUser','factoryParameterSettingService','validate','$timeout',function($filter,$rootScope,$location,$scope,$cookies,departmentManageService,inspectionPlan,queryCorporateAllUser,factoryParameterSettingService,validate,$timeout){

    // console.log('巡检记录控制器');
    //查询部门
    departmentManageService.queryOrder({
        name:$scope.depName,
        corporateIdentify:$scope.corporateIdentify
    }, function(response){
        if(response.data!=''&&response.data!=null&&response.data!=undefined&&response.errorCode=='000000'){
            $scope.departmentManageLists=response.data;
        }else{
            console.log(response.errorMessage);
        }
    });

    $scope.xjsjLists=[{
        id:1,
        name:'有异常或漏检'
    },{
        id:2,
        name:'有异常'
    },{
        id:3,
        name:'有漏检'
    }];
    $scope.xjlxLists=[{
        id:1,
        name:'本周记录'
    },{
        id:2,
        name:'上周记录'
    },{
        id:3,
        name:'本月记录'
    },{
        id:4,
        name:'上月记录'
    },{
        id:5,
        name:'本年记录'
    },{
        id:6,
        name:'上年记录'
    },{
        id:7,
        name:'更早记录'
    }];


    //分页查询巡检记录
    $scope.paginationConf={
        currentPage:1,
        itemsPerPage:5
    };
    $scope.spotInspectionRecordLists=[];$scope.changeDetailLists=[];
    $scope.onQuery=function () {
        var req={
            "currentPage": $scope.paginationConf.currentPage, //当前页码
            "itemsPerPage": $scope.paginationConf.itemsPerPage,//每页显示记录数
            departmentId:$scope.departmentManage
        };
        // inspectionPlan.spotInspectionPlanFindByPage(req).success(function (data) {
        //     // console.log(data,'巡检计划');
        //     if(data.data&&data.data.totalCount>=1){
        //         // if(true){
        //         $scope.paginationConf.totalItems = data.data.totalCount;
        //         $scope.spotInspectionRecordLists=data.data.spotInspectionPlanQueryRespList;
        //         $timeout(function () {
        //             if($scope.spotInspectionRecordLists){
        //                 $scope.spotInspectionRecordLists.forEach(function (k) {
        //                     var strsArr=[];
        //                     for(var i=0;i<k.executors.length;i++){
        //                         $scope.allUserLists.forEach(function (v) {
        //                             if(v.userId==k.executors[i]){
        //                                 strsArr.push(v.userName);
        //                             }
        //                         });
        //                     }
        //                     k.executorsName=strsArr.join(',');
        //                 });
        //             }
        //             if($scope.spotInspectionRecordLists.length>0){
        //                 // spotInspectionStandard.queryInspectionStandardDetail($scope.spotInspectionStandardLists[0].id).success(function (data) {
        //                 //     $scope.changeDetailLists=data.data.spotInspectionItems;
        //                 //     // console.log($scope.changeDetailLists,'--==')
        //                 // });
        //                 $($('.prossTr')[0]).addClass('ccTr');
        //             }
        //         },400);
        //     }else{
        //         // $scope.changeDetailLists=[];
        //         $scope.paginationConf.totalItems = 0;
        //         $scope.spotInspectionRecordLists.length = 0;
        //     }
        // });
    };

    //切换巡检明细表格事件
    $scope.changeDetail=function (id,event) {
        // console.log($(event.target));
        $(event.target).parent().parent().find('tr').removeClass('ccTr');
        $(event.target).parent().addClass('ccTr');
        // spotInspectionStandard.queryInspectionStandardDetail(id).success(function (data) {
        //     $scope.changeDetailLists=data.data.spotInspectionItems;
        // });
    };


    $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.onQuery);
}]);
