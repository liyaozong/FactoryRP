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
            // console.log(req);
            var flog;
            for(var i in req){
                // console.log(i,":",req[i]);
                flog=validate.required(req[i]);
                if(!flog){
                    break;
                }
            }
            if(req.relateDevices.length>0&&req.spotInspectionItems.length>0){
                // flog=true;
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
myApp.controller('inspectionPlanCtrl',['$filter','$rootScope','$location','$scope','$cookies','departmentManageService','inspectionPlan','queryCorporateAllUser','factoryParameterSettingService','validate','$timeout','FileUploader',function($filter,$rootScope,$location,$scope,$cookies,departmentManageService,inspectionPlan,queryCorporateAllUser,factoryParameterSettingService,validate,$timeout,FileUploader){
    // console.log('巡检计划控制器');

    //阻止冒泡
    $scope.stopUp=function (event) {
        // console.log(event)
        event.stopPropagation();
    };

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

    //点检值
    $scope.djzLists=[{
        id:1,
        name:'偏高'
    },{
        id:2,
        name:'正常'
    },{
        id:3,
        name:'偏低'
    }];

    //查询执行人
    queryCorporateAllUser.getData().success(function (data) {
        $scope.allUserLists=data.data.userRespList;
        // console.log($scope.allUserLists)
    });

    //查询巡检计划执行时间类型
    inspectionPlan.queryAllSpotInspectionPlanRecycleType().success(function (data) {
       // console.log(data);
       $scope.SIPRecycleTypeLists=data.data;
    });

    //根据设备id查询巡检标准
    $scope.queryStanardByDeviceId=function (id) {
        inspectionPlan.queryStanardByDeviceId(id).success(function (data) {
            // console.log(data);
            $scope.stanardByDeviceIdLists=data.data;
        })
    };

    //分页查询巡检计划
    $scope.paginationConf={
        currentPage:1,
        itemsPerPage:5
    };
    $scope.spotInspectionPlanLists=[];$scope.changeDetailLists=[];
    $scope.onQuery=function () {
        var req={
            "currentPage": $scope.paginationConf.currentPage, //当前页码
            "itemsPerPage": $scope.paginationConf.itemsPerPage,//每页显示记录数
            departmentId:$scope.departmentManage
        };
        inspectionPlan.spotInspectionPlanFindByPage(req).success(function (data) {
            // console.log(data,'巡检计划');
            if(data.data&&data.data.totalCount>=1){
                // if(true){
                $scope.paginationConf.totalItems = data.data.totalCount;
                $scope.spotInspectionPlanLists=data.data.spotInspectionPlanQueryRespList;
                $timeout(function () {
                    if($scope.spotInspectionPlanLists){
                        $scope.spotInspectionPlanLists.forEach(function (k) {
                            var strsArr=[];
                            for(var i=0;i<k.executors.length;i++){
                                $scope.allUserLists.forEach(function (v) {
                                    if(v.userId==k.executors[i]){
                                        strsArr.push(v.userName);
                                    }
                                });
                            }
                            k.executorsName=strsArr.join(',');
                        });
                    }
                    if($scope.spotInspectionPlanLists.length>0){
                        // spotInspectionStandard.queryInspectionStandardDetail($scope.spotInspectionStandardLists[0].id).success(function (data) {
                        //     $scope.changeDetailLists=data.data.spotInspectionItems;
                        //     // console.log($scope.changeDetailLists,'--==')
                        // });
                        $($('.prossTr')[0]).addClass('ccTr');
                    }
                },400);
            }else{
                // $scope.changeDetailLists=[];
                $scope.paginationConf.totalItems = 0;
                $scope.spotInspectionPlanLists.length = 0;
            }
        });
    };

    //切换历史巡检表格事件
    $scope.changeDetail=function (id,event) {
        // console.log($(event.target));
        $(event.target).parent().parent().find('tr').removeClass('ccTr');
        $(event.target).parent().addClass('ccTr');
        // spotInspectionStandard.queryInspectionStandardDetail(id).success(function (data) {
        //     $scope.changeDetailLists=data.data.spotInspectionItems;
        // });
    };

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
            executors:[] ,//执行人集合
            nameStrs:'', //执行人姓名字符串
            list:[]
        };
        popupDiv('iPlanPopup')

    };

    //确认提交巡检计划
    $scope.addIPlanSure=function () {
      if($scope.iPlanType==0){
          var arr=[];
          $scope.iPlanReq.list.forEach(function (v,i) {
              arr.push({
                  deviceId:v.id,
                  deviceType:v.deviceType,
                  lineOrder:i+1,
                  spotInspectionStandard:v.planStatus
              })
          });
          var req={
              "department": $scope.iPlanReq.department,
              "endTime":$filter('date')($scope.iPlanReq.endTime,'yyyy-MM-dd HH:mm:ss'),
              "executors": $scope.iPlanReq.executors,
              "list": arr,
              "name": $scope.iPlanReq.name,
              "nextExecuteTime":$filter('date')($scope.iPlanReq.nextExecuteTime,'yyyy-MM-dd HH:mm:ss'),
              "planStatus": $scope.iPlanReq.planStatus,
              "range": $scope.iPlanReq.range,
              "recyclePeriod": $scope.iPlanReq.recyclePeriod,
              "recyclePeriodType": $scope.iPlanReq.recyclePeriodType,
              "requestTime": $filter('date')(new Date(),'yyyy-MM-dd HH:mm:ss')
          };
          // console.log(req);
          var flog;
          for(var i in req){
              // console.log(i,":",req[i]);
              flog=validate.required(req[i]);
              if(!flog){
                  break;
              }
          }
          if(req.list.length>0){
              // flog=true;
          }else {
              flog=false;
          }
          // console.log($scope.iPlanReq.nextExecuteTime,new Date());
          if($scope.iPlanReq.nextExecuteTime<new Date()){
              flog=false;
              alert('下次执行时间必须大于当前时间');
          }else if($scope.iPlanReq.endTime<$scope.iPlanReq.nextExecuteTime){
              flog=false;
              alert('截止时间必须大于下次执行时间');
          }
          if(flog){
              inspectionPlan.addSpotInspectionPlan(req).success(function (data) {
                  if(data.errorCode=='000000'){
                      hideDiv('iPlanPopup');
                      popupDiv('SaveSuccess');
                      $('.SaveSuccess .Message').html(data.errorMessage);
                  }else{
                      hideDiv('iPlanPopup');
                      popupDiv('SaveSuccess');
                      $('.SaveSuccess .Message').html(data.errorMessage);
                  }
              })
          }else {
              $scope.errFlog=true;
          }
      }
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
        $scope.iPlanReq.executors=processAuditorList;
        $scope.iPlanReq.nameStrs=nameList.join(',');

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

    //打开添加巡检对象弹出层
    $scope.addDevice=function () {
        hideDiv('iPlanPopup');
        popupDiv('iPlanDevicePopup');
        $scope.changedDeviceObj={};
    };

    //点击确认巡检对象
    $scope.addSISSure_device=function () {
        if($scope.stanardByDeviceIdLists){
            $scope.stanardByDeviceIdLists.forEach(function (v) {
                if($scope.changedDeviceObj.planStatus==v.id){
                    $scope.changedDeviceObj.planStatusName=v.name;
                }
            });
        }
        if($scope.changedDeviceObj.id){
            $scope.iPlanReq.list.push({
                id:$scope.changedDeviceObj.id,
                name:$scope.changedDeviceObj.name,
                code:$scope.changedDeviceObj.code,
                specification:$scope.changedDeviceObj.specification,
                planStatus:$scope.changedDeviceObj.planStatus,
                planStatusName:$scope.changedDeviceObj.planStatusName,
                deviceType:$scope.changedDeviceObj.deviceTypeId
            });
            hideDiv('iPlanDevicePopup');
            popupDiv('iPlanPopup');
        }else {
            alert('请选择设备');
        }
    };

    //删除包含设备列表中的设备
    $scope.deleteSISBalance_device=function (obj,i) {
        $scope.iPlanReq.list.splice(i,1);
    };

    //分页查询设备
    $scope.paginationConf_CC = {
        currentPage: 1,
        itemsPerPage: 5
    };
    $scope.onQuery_cc=function () {
        factoryParameterSettingService.deviceListInfo({
            useDept:$scope.dd_departmentManage,//使用部门
            deviceType:$scope.dd_DeviceType,//设备类型
            name:$scope.dd_DeviceTypeName,//设备名称
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
    };

    //打开设备列表弹出层
    $scope.changedDevice=function () {
        $scope.onQuery_cc();
        hideDiv('iPlanDevicePopup');
        popupDiv('changedDevicePop');
    };

    //确认点击选择设备
    $scope.changedDeviceClick=function (event,obj) {
        $(event.target).parent().parent().find('tr').removeClass('ccTr');
        $(event.target).parent().addClass('ccTr');
        // console.log(obj);
        $scope.dd_obj=obj;
    };
    //点击确认选择设备
    $scope.changeCCSure_changedDevice=function () {
        if($scope.dd_obj){
            $scope.changedDeviceObj=$scope.dd_obj;
            // console.log($scope.dd_obj);
            $scope.queryStanardByDeviceId($scope.dd_obj.id);
            hideDiv('changedDevicePop');
            popupDiv('iPlanDevicePopup');
        }else {
            alert('请选择设备');
        }
    };

    //巡检计划操作（编辑、详情）
    $scope.openIPlan_detail=function (type,obj,$event) {
        alert('调试中');
        $event.stopPropagation();
    };

    //打开执行巡检计划（添加巡检记录）弹出层
    $scope.openAddRecord=function (obj,$event) {
        console.log(obj);
        $scope.spotInspectionRecordAddReq={
            "detailList": [1,2,3],//巡检记录详情集合
            "executeTime": "1",//巡检时间
            "executor": 1,//执行者
            "planId": 1,//巡检计划ID
            "planName": "1",//巡检计划名称
            "planTime": "1",//计划时间
            "recyclePeriod": 0,//循环周期
            "recyclePeriodType": "string",//循环周期类型
            "requestTime": $filter('date')(new Date(),'yyyy-MM-dd HH:mm:ss'),//请求时间
            "standard": 1//巡检标准ID
        };

        popupDiv('addRecordPopup');
        $event.stopPropagation();
    };
    //关闭执行巡检计划（添加巡检记录）弹出层
    $scope.closeAR1=function () {
        hideDiv('addRecordPopup');
    };
    $scope.closeAR=function () {//确定添加巡检
        alert('接口调试中');
        hideDiv('addRecordPopup');
    };

    //打开巡检明细录入弹出层
    $scope.open_ar_lrs=function (obj,$index) {


        hideDiv('addRecordPopup');
        popupDiv('addRecordPopup2');
    };
    //关闭执行巡检计划（添加巡检记录2）弹出层
    $scope.closeAR21=function () {
        hideDiv('addRecordPopup2');
        popupDiv('addRecordPopup');
    };
    $scope.closeAR2=function () {//确定执行
        alert('接口调试中');
        hideDiv('addRecordPopup2');
        popupDiv('addRecordPopup');
    };

    //打开巡检明细录入弹出层3
    $scope.open_ar_lrs2=function (obj,$index) {
        hideDiv('addRecordPopup2');
        popupDiv('addRecordPopup3');
    };
    //关闭执行巡检计划（添加巡检记录3）弹出层
    $scope.closeAR31=function () {
        hideDiv('addRecordPopup3');
        popupDiv('addRecordPopup2');
    };
    $scope.closeAR3=function () {//确定编辑
        alert('接口调试中');
        hideDiv('addRecordPopup3');
        popupDiv('addRecordPopup2');
    };

    // popupDiv('addRecordPopup2');
    //上传文件
    $scope.uploadStatus = $scope.uploadStatus1 = false; //定义两个上传后返回的状态，成功获失败
    var uploader = $scope.uploader = new FileUploader({
        url: '/service/wordsList',
        queueLimit: 1,     //文件个数
        removeAfterUpload: true   //上传后删除文件
    });
    $scope.clearItems = function(){    //重新选择文件时，清空队列，达到覆盖文件的效果
        uploader.clearQueue();
    };
    uploader.onAfterAddingFile = function(fileItem) {
        $scope.fileItem = fileItem._file;    //添加文件之后，把文件信息赋给scope
        //能够在这里判断添加的文件名后缀和文件大小是否满足需求。
        // console.log($scope.fileItem,'添加文件后')
    };
    uploader.onBeforeUploadItem=function(item){
        console.log(item,'文件上传之前')
    };
    uploader.onProgressItem =function (item, progress) {
        // console.log(item, progress,'文件上传中');
    };
    uploader.onSuccessItem = function(fileItem, response, status, headers) {
        $scope.uploadStatus = true;   //上传成功则把状态改为true
        // console.log(fileItem,response,status,headers,'文件上传成功后');
    };
    $scope.UploadFile = function(){
        uploader.uploadAll();
    };


    $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.onQuery);
    $scope.$watch('paginationConf_CC.currentPage + paginationConf_CC.itemsPerPage', $scope.onQuery_cc);
}]);
