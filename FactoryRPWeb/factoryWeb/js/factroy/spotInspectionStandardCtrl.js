//点巡检


// 巡检标准控制器
myApp.controller('spotInspectionStandardCtrl',['$filter','$rootScope','$timeout','$scope','$cookies','spotInspectionStandard','deviceType','departmentManageService','factoryParameterSettingService','validate',function($filter,$rootScope,$timeout,$scope,$cookies,spotInspectionStandard,deviceType,departmentManageService,factoryParameterSettingService,validate){
    // console.log('巡检标准控制器');

    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 5
    };

    //查询设备类型
    deviceType.list().success(function (data) {
        // console.log(data,'设备类型');
        $scope.deviceTypeLists=data.data;
    });

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
    // $scope.departmentManageLists = departmentManageService.getOrderList();

    //查询点巡检记录方式
    spotInspectionStandard.getRecordType().success(function (data) {
        $scope.recordTypeLists=data.data;
        // console.log($scope.recordTypeLists,'----')
    });

    //分页查询设备
    $scope.paginationConf_CC = {
        currentPage: 1,
        itemsPerPage: 5
    };
    $scope.SISReq={
        addDeviceType:''
    };
    $scope.onQuery_cc=function () {
        // console.log($scope.SISReq.addDeviceType!='',$scope.SISReq.addDeviceType)
        if($scope.SISReq.addDeviceType!=''){
            // console.log('--------------')
            factoryParameterSettingService.deviceListInfo({
                useDept:$scope.addDepartmentManage,//使用部门
                deviceType:$scope.SISReq.addDeviceType,//设备类型
                name:$scope.addDeviceTypeName,//设备名称
                currentPage: $scope.paginationConf_CC.currentPage,
                itemsPerPage: $scope.paginationConf_CC.itemsPerPage
            }, function(response){
                if(response.data.totalCount>=1){
                    $scope.paginationConf_CC.totalItems = response.data.totalCount;
                    $scope.deviceListInfos=response.data.list
                }else{
                    $scope.paginationConf_CC.totalItems = 0;
                    $scope.deviceListInfos=[];
                }
            });
        }
    };

    //分页查询巡检标准
    $scope.spotInspectionStandardLists=[];
    $scope.changeDetailLists=[];
    $scope.onQuery=function () {
        var req={
            "currentPage": $scope.paginationConf.currentPage, //当前页码
            "itemsPerPage": $scope.paginationConf.itemsPerPage,//每页显示记录数
            relateDeviceType:$scope.relateDeviceType
        };
        spotInspectionStandard.list(req).success(function (data) {
            // console.log(data,'巡检标准');
            if(data.data&&data.data.totalCount>=1){
            // if(true){
                $scope.paginationConf.totalItems = data.data.totalCount;
                $scope.spotInspectionStandardLists=data.data.list;
                // console.log($scope.spotInspectionStandardLists);
                $timeout(function () {
                    if($scope.spotInspectionStandardLists.length>0){
                        spotInspectionStandard.queryInspectionStandardDetail($scope.spotInspectionStandardLists[0].id).success(function (data) {
                            $scope.changeDetailLists=data.data.spotInspectionItems;
                            // console.log($scope.changeDetailLists,'--==')
                        });
                        $($('.prossTr')[0]).addClass('ccTr');
                    }
                },300);
            }else{
                $scope.changeDetailLists=[];
                $scope.paginationConf.totalItems = 0;
                $scope.spotInspectionStandardLists.length = 0;
            }
        });
    };

    //阻止冒泡
    $scope.stopUp=function (event) {
        // console.log(event)
        event.stopPropagation();
    };


    //切换详情表格事件
    $scope.changeDetail=function (id,event) {
        // console.log($(event.target));
        $(event.target).parent().parent().find('tr').removeClass('ccTr');
        $(event.target).parent().addClass('ccTr');
        spotInspectionStandard.queryInspectionStandardDetail(id).success(function (data) {
            $scope.changeDetailLists=data.data.spotInspectionItems;
        });
    };

    //添加设备(打开弹出层)
    $scope.addDevice=function () {
        // console.log($scope.addDeviceType);
        if($scope.SISReq.addDeviceType){
            $scope.onQuery_cc();
            hideDiv('SISpopup');popupDiv('addDevicePop');
        }else {
            alert('请选择设备类型');
        }
    };

    //保存添加设备弹出层数据
    $scope.changeCCSure=function () {
        var $inputCheck=$(".addDeviceCheck:checked");
        $inputCheck.each(function (i,n) {
            // console.log(n,i);
            $scope.deviceListInfos.forEach(function (v) {
                // console.log(v);
                if($(n).val()==v.id){
                    var flog=true;
                    $scope.SISReq.deviceItems.forEach(function (m) {
                        if(v.id==m.id){
                            flog=false;
                        }
                    });
                    if(flog){
                        $scope.SISReq.deviceItems.push(v);
                    }
                }
            })
        });
        hideDiv('addDevicePop');popupDiv('SISpopup');
    };

    //删除已添加设备
    $scope.deleteSISBalance=function (item,i) {
        $scope.SISReq.deviceItems.splice(i,1);
    };

    //添加项目事件
    $scope.addProject=function () {
        $scope.addProjectLists.push({'recordType':'recordType'});
    };

    //删除已添加巡检项目
    $scope.deleteProjectBalance=function (item,i) {
        $scope.addProjectLists.splice(i,1);
    };

    //记录方式切换事件
    $scope.changeRecordType=function (i) {
      // console.log($('#recordType_div'+i).val().split(':')[1],'11');
      var $this=$('#recordType_div'+i);
        if($this.val().split(':')[1]=='verbal_description'){
            // console.log('1');
            $this.parent().parent().find('.recordType_td_input').val('').addClass('displayNone');
        }else {
            // console.log('2');
            $this.parent().parent().find('.recordType_td_input').val('').removeClass('displayNone');
        }
    };

    //确定保存事件（新增、编辑、详情）
    $scope.addSISSure=function () {
        if($scope.SISType==0){
            // alert('接口调试中');
            var arr=[];var arr1=[];
            $scope.SISReq.deviceItems.forEach(function (v) {
                arr.push(v.id);
            });
            $('.add_P_tr').each(function (i,n) {
                var obj={
                    name:$(this).find('.projectName').val(),
                    recordType:$(this).find('.projectRecordType').val().split(':')[1],
                    inputLimitValue:$(this).find('.inputLimitValue').val().split('/'),
                    lowerLimit:$(this).find('.lowerLimit').val(),
                    upperLimit:$(this).find('.upperLimit').val()
                };
                arr1.push(obj);
                // console.log($(this).find('.projectRecordType').val())
            });

            var req={
                name:$scope.SISReq.name,
                deviceType:$scope.SISReq.addDeviceType,
                remark:$scope.SISReq.remark,
                relateDevices:arr,
                requirement:$scope.SISReq.requirement,
                requestTime:$filter('date')(new Date(),'yyyy-MM-dd HH:mm:ss'),
                spotInspectionItems:arr1
            };
            console.log(req);
            var flog;
            for(var i in req){
                // console.log(i,":",req[i]);
                flog=validate.required(req[i]);
                if(!flog){
                    break;
                }
            }
            if(req.relateDevices.length>0&&req.spotInspectionItems.length>0){
                flog=true;
            }else {
                flog=false;
            }
            if(flog){
                spotInspectionStandard.addSpotInspectionStandard(req).success(function (data) {
                    if(data.errorCode=='000000'){
                        hideDiv('SISpopup');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }else{
                        hideDiv('SISpopup');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }
                })
            }else {
                $scope.errFlog=true;
            }

        }else if($scope.SISType==1){
            alert('接口调试中');
            hideDiv('SISpopup');
        }else {
            hideDiv('SISpopup');
        }
    };

    //新增巡检标准
    $scope.openSIS=function (type) {
        $scope.SISType=type;
        $scope.errFlog=false;
        $scope.SISTip='巡检标准新增';
        $scope.addProjectLists=[];
        $scope.SISReq={
            name:'',
            deviceItems:[],
            addDeviceType:'',
            requirement:'',
            remark:''
        };
        popupDiv('SISpopup');
    };

    //巡检标准详情or编辑
    $scope.openSIS_detail=function (type,item,event) {
        spotInspectionStandard.queryInspectionStandardDetail(item.id).success(function (data) {
            $scope.errFlog=false;
            $scope.SISType=type;
            $scope.SISTip=type==2?'巡检标准详情':'巡检标准编辑';
            var datas=data.data;
            $scope.SISReq={
                name:datas.name,
                deviceItems:datas.deviceInfoList,
                requirement:datas.requirement,
                addDeviceType:datas.deviceType,
                remark:datas.remark
            };
            $scope.addProjectLists=[];

            $timeout(function () {
                $("#deviceType2 option").each(function(i,n){
                    // console.log($(n).html(),datas.deviceTypeName,'====');
                    // $(n).attr("selected",false);
                    if($(n).html()==datas.deviceTypeName){
                        // console.log('---111');
                        $(n).attr("selected",true);
                    }
                });
            },300);
            if(datas.spotInspectionItems.length>0){
                datas.spotInspectionItems.forEach(function (n,i) {
                    // console.log(n,i);
                    $scope.addProjectLists.push({'recordType':'recordType'});
                });
                $timeout(function () {
                    // console.log($('.add_P_tr').html());
                    $('.add_P_tr').each(function (i,n) {
                        // console.log(this,i);
                        $(this).find('.projectName').val(datas.spotInspectionItems[i].name);
                        // $(this).find('.projectRecordType').val(datas.spotInspectionItems[i].recordTypeName);
                        $(this).find('.projectRecordType option').each(function (j,k) {
                            // console.log(k);
                            if($(k).html()==datas.spotInspectionItems[i].recordTypeName){
                                $(k).attr("selected",true);
                            }
                        });
                        if(datas.spotInspectionItems[i].recordTypeName!='文字描述'){
                            $(this).find('.inputLimitValue').val(datas.spotInspectionItems[i].inputLimitValue.join('/'));
                            $(this).find('.lowerLimit').val(datas.spotInspectionItems[i].lowerLimit);
                            $(this).find('.upperLimit').val(datas.spotInspectionItems[i].upperLimit);
                        }else {
                            $(this).find('.recordType_td_input').val('').addClass('displayNone');
                        }
                    })
                },300)
            }

            // console.log(data);
            popupDiv('SISpopup');
        });
        event.stopPropagation();
    };

    //删除
    $scope.deleteSISBalance_si=function () {
        popupDiv('deleteSISPop');
    };

    //确认删除
    $scope.deleteSISPopSure=function () {
        var $inputCheck=$(".SISListsInput:checked");
        var arr=[];
        // console.log($inputCheck)
        $inputCheck.each(function (i,n) {
            arr.push($(n).attr('value'));
        });
        // console.log(arr);
        if(arr.length>1){
            spotInspectionStandard.deleteSpotInspectionStandardByIds(arr.join(',')).success(function (data) {
                if(data.errorCode=='000000'){
                    hideDiv('deleteSISPop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }else{
                    hideDiv('deleteSISPop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }
            })
        }else if(arr.length==1){
            spotInspectionStandard.deleteInspectionStandard(arr[0]).success(function (data) {
                if(data.errorCode=='000000'){
                    hideDiv('deleteSISPop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }else{
                    hideDiv('deleteSISPop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }
            });
        }
    };

    // popupDiv('SISpopup');///////////
    $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.onQuery);
    $scope.$watch('paginationConf_CC.currentPage + paginationConf_CC.itemsPerPage', $scope.onQuery_cc);

}]);


// 巡检计划控制器
myApp.controller('inspectionPlanCtrl',['$filter','$rootScope','$location','$scope','$cookies','departmentManageService','inspectionPlan','queryCorporateAllUser',function($filter,$rootScope,$location,$scope,$cookies,departmentManageService,inspectionPlan,queryCorporateAllUser){
    // console.log('巡检计划控制器');
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

    //查询巡检计划状态
    inspectionPlan.getxjsj().success(function (data) {
        // console.log(data);
        $scope.xjsjLists=data.data;
    });

    //查询巡检计划名称
    inspectionPlan.getxjlx().success(function (data) {
        $scope.xjlxLists=data.data;
    });

    //计划状态
    $scope.planStatusLists=[{
        id:1,
        name:'启用'
    },{
        id:2,
        name:'停用'
    },{
        id:3,
        name:'编辑中'
    }];

    //查询执行人
    queryCorporateAllUser.getData().success(function (data) {
        $scope.allUserLists=data.data.userRespList;
        // console.log($scope.allUserLists)
    });

    //分页查询巡检计划

    //新增巡检计划
    $scope.openIPlan=function (type) {
        $scope.iPlanType=type;
        $scope.iPlanTip='巡检计划新增';
        $scope.iPlanReq={
            name:'',//计划名称
            range:'',//位置范围
            department:'',//所在部门
            planStatus:'',//计划状态
            nextExecuteTime:'',//下次执行时间
            recyclePeriod:'',//循环周期
            recyclePeriodType:'',//循环周期类型
            endTime:'',//截止时间
            executors:'' ,//执行人集合
            nameStrs:'' //执行人姓名字符串
        };
        popupDiv('iPlanPopup')

    };

    //打开执行人选择列表
    $scope.openPA=function () {
        hideDiv('iPlanPopup');
        popupDiv('executorsPopup');
        $scope.processAuditorLists=[];
        $scope.userLists=angular.copy($scope.allUserLists);
    };

    //选择执行者
    $scope.changePA_left=function (id,i) {
        // console.log(id,i);
        var arr=$scope.userLists[i];
        $scope.processAuditorLists.push(arr);
        $scope.userLists.splice(i,1);
    };

    //删除已选择执行者
    $scope.changePA_right=function (id,i) {
        // console.log(id,i);
        var arr=$scope.processAuditorLists[i];
        $scope.userLists.push(arr);
        $scope.processAuditorLists.splice(i,1);
    };

    //确定选择执行者列表
    $scope.closePA=function () {
        var processAuditorList=[];var nameList=[];
        $scope.processAuditorLists.forEach(function (n,i) {
            processAuditorList.push(n.userId);
            nameList.push(n.userName);
        });
        $scope.iPlanReq={
            executors:processAuditorList,
            nameStrs:nameList.join(',')
        };
        // console.log($scope.iPlanReq);
        if($scope.iPlanReq.executors.length<=0){
            alert('执行者不能为空');
        }else {
            $scope.closePA1();
        }

    };
    $scope.closePA1=function () {
        hideDiv('executorsPopup');
        popupDiv('iPlanPopup');
        // popupDiv('iPlanPopup');
    };

    //选择包含设备
    $scope.addDevice=function () {

    };


}]);
