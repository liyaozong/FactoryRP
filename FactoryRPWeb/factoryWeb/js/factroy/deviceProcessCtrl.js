//参数设置
// 审核流程设置
myApp.controller('deviceProcessCtrl',['$timeout','$filter','$rootScope','$location','$scope','deviceProcess','deviceType','departmentManageService','validate','queryCorporateAllUser',function($timeout,$filter,$rootScope,$location,$scope,deviceProcess,deviceType,departmentManageService,validate,queryCorporateAllUser){

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
                $timeout(function () {
                    $scope.deviceProcessLists.forEach(function (n,i) {
                        var item=n;
                        if(item.triggerConditionType==1){
                            item.triggerConditionTypeName='设备类型';
                            $scope.deviceTypeList.forEach(function (k,j) {
                                // console.log(item.triggerCondition,k);
                                if(item.triggerCondition==k.id){
                                    // console.log(k,'----');
                                    item.triggerConditionName=k.name;
                                }else if(item.triggerCondition==''||item.triggerCondition==null||item.triggerCondition==undefined){
                                    item.triggerConditionName='所有设备';
                                }
                            })
                        }else if(item.triggerConditionType==2){
                            item.triggerConditionTypeName='金额上限';
                            item.triggerConditionName=item.triggerCondition;
                        }else if(item.triggerConditionType==3){
                            item.triggerConditionTypeName='部门';
                            $scope.departmentList.forEach(function (k,j) {
                                // console.log(item.triggerCondition,k);
                                if(item.triggerCondition==k.id){
                                    // console.log(k,'----');
                                    item.triggerConditionName=k.name;
                                }else if(item.triggerCondition==''||item.triggerCondition==null||item.triggerCondition==undefined){
                                    item.triggerConditionName='所有部门';
                                }
                            })
                        }
                        $scope.deviceProcessTypeLists.forEach(function (m,n) {
                            // console.log(item.processType,m.id,'--');
                            if(item.processType==m.id){
                                item.deviceProcessTypeName=m.deviceProcessType;
                                m.deviceProcessPhaseList.forEach(function (k,j) {
                                    // console.log(k.id,item.processStage,'====')
                                    if(item.processStage==k.id){
                                        // console.log(k);
                                        item.processStageName=k.deviceProcessPhase;
                                    }
                                });
                            }
                        });

                    });
                },500);
            }else{
                $scope.paginationConf.totalItems = 0;
                $scope.deviceProcessLists.length = 0;
            }
        });
    };

    //查询设备类型
    deviceType.list().success(function (data) {
        $scope.deviceTypeList=data.data;
    });

    //查询部门
    deviceProcess.departmentGetList().success(function (data) {
        // console.log(data);
        $scope.departmentList=data.data;
    });

    //查询所有流程类型集合
    deviceProcess.queryAllDecviceProcessType().success(function (data) {
       // console.log(data);
       $scope.deviceProcessTypeLists=data.data;
    });

    //查询审核用户
    queryCorporateAllUser.getData().success(function (data) {
        $scope.allUserLists=data.data.userRespList;
        // console.log($scope.allUserLists)
    });

    $scope.onQuery();

    //流程类型选择事件
    $scope.processTypeChange=function () {
        // console.log($scope.formDeviceProcess.processType);
        var id=$scope.formDeviceProcess.processType;
        $scope.deviceProcessTypeLists.forEach(function (n,i) {
            // console.log(i,n);
            if(id==n.id){
                $scope.deviceProcessPhaseLists=n.deviceProcessPhaseList;
            }
        });
        $scope.formDeviceProcess.processStage='';
        // console.log($scope.formDeviceProcess.processStage)
    };

    //触发条件改变事件
    $scope.triggerConditionTypeChange=function () {
      $scope.formDeviceProcess.triggerCondition='';
    };

    //下一步点击事件
    $scope.addDeviceProcessNext=function () {
        var flog;
        var validateList={
            processName:$scope.formDeviceProcess.processName,
            processType:$scope.formDeviceProcess.processType,
            processStage:$scope.formDeviceProcess.processStage,
            triggerConditionType:$scope.formDeviceProcess.triggerConditionType
        };
        for(var i in validateList){
            // console.log(i,":",validateList[i]);
            flog=validate.required(validateList[i]);
            if(!flog){
                break;
            }
        }
        if(flog){
            $scope.nextPop=true;
        }else {
            $scope.nextFlog=true;
        }
    };
    // $scope.nextPop=true;popupDiv('addDeviceProcessPop');//
    //上一步
    $scope.addDeviceProcessProv=function () {
        $scope.nextPop=false;
    };

    //新增审核人员组
    $scope.addDeviceProcessDetail=function () {
        $scope.deviceProcessDetail={
            auditType:1,
            handleDemandType:1,
            nameStrs:'',
            processAuditor:[]
        };
        hideDiv('addDeviceProcessPop');popupDiv('addDeviceProcessDetailPop');
        $scope.changeFLog=false;
    };

    //打开审核人选择列表
    $scope.openPA=function () {
        // console.log('打开审核人员选择列表');
        $scope.changeFLog=true;
        $scope.processAuditorLists=[];
        $scope.userLists=angular.copy($scope.allUserLists);
    };

    //选择审核人员
    $scope.changePA_left=function (id,i) {
        // console.log(id,i);
        var arr=$scope.userLists[i];
        $scope.processAuditorLists.push(arr);
        $scope.userLists.splice(i,1);
    };
    //删除选择审核人员
    $scope.changePA_right=function (id,i) {
        // console.log(id,i);
        var arr=$scope.processAuditorLists[i];
        $scope.userLists.push(arr);
        $scope.processAuditorLists.splice(i,1);
    };

    //确定选择审核人员列表
    $scope.closePA=function () {
        // console.log('确定审核人员列表');
        var processAuditorList=[];var nameList=[];
        $scope.processAuditorLists.forEach(function (n,i) {
            // console.log(n,i);
            processAuditorList.push(n.userId);
            nameList.push(n.userName);
        });
        $scope.deviceProcessDetail={
            auditType:$scope.deviceProcessDetail.auditType,
            handleDemandType:$scope.deviceProcessDetail.handleDemandType,
            processAuditor:processAuditorList,
            nameStrs:nameList.join(',')
        };
        // $scope.deviceProcessDetail.processAuditor=processAuditorList;
        // $scope.deviceProcessDetail.nameStrs=nameList.join(',');
        // console.log($scope.deviceProcessDetail);
        if($scope.deviceProcessDetail.processAuditor.length<=0){
            alert('审核人员不能为空');
        }
        $scope.changeFLog=false;
    };
    $scope.closePA1=function () {
        $scope.changeFLog=false;
    };

    //确定当前审核流程审核人员
    $scope.changeCCSure=function () {
        var req={
            auditType:$scope.deviceProcessDetail.auditType,
            handleDemandType:$scope.deviceProcessDetail.handleDemandType,
            processAuditor:$scope.deviceProcessDetail.processAuditor,
            processStep:$scope.processStep,
            nameStrs:$scope.deviceProcessDetail.nameStrs
        };
        $scope.reqList.push(req);
        $scope.processStep++;
        console.log($scope.reqList);
        hideDiv('addDeviceProcessDetailPop');popupDiv('addDeviceProcessPop');
    };

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
        $scope.reqList=[];
        $scope.processStep=1;
        $scope.sureFlog=1;
        $scope.errFlog=false;
        $scope.nextFlog=false;
        $scope.nextPop=false;
    };
    //新增审核流程确定
    $scope.addDeviceProcessSure=function () {
        var addReq={
            processName:$scope.formDeviceProcess.processName,
            processType:$scope.formDeviceProcess.processType,
            processStage:$scope.formDeviceProcess.processStage,
            triggerConditionType:$scope.formDeviceProcess.triggerConditionType,
            triggerCondition:$scope.formDeviceProcess.triggerCondition,
            processRemark:$scope.formDeviceProcess.processRemark,
            list:$scope.reqList,
            requestTime:$filter('date')(new Date(),'yyyy-MM-dd HH:mm:ss')
        };
        var flog;
        // for(var i in addReq){
        //     console.log(i,":",addReq[i]);
        //     flog=validate.required(addReq[i]);
        //     if(!flog){
        //         break;
        //     }
        // }
        if(addReq.list.length>0){
            flog=true;
        }else {
            flog=false;
        }
        console.log('addReq',addReq);
        if(flog){
            if($scope.sureFlog==1){
                console.log('新增');
                deviceProcess.addDeviceProcess(addReq).then(function (data) {
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
