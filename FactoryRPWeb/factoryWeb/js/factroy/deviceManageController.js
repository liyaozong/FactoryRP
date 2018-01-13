/**
 * Created by jayvenLee on 2017/11/11.
 */
factoryParameterSettingApp.controller('deviceManageController',function ($scope,UrlService,$http,$filter,$cookies,$resource, $location, $state, factoryParameterSettingService,departmentManageService) {
    $scope.WebURL=UrlService.getUrl('factoryServe');
    if($location.path()=='/main/deviceManage'){
        $("#menuLeft .leftmenu .deviceManage .menuson").css('display','block');
        $("#menuLeft .leftmenu .deviceManage").removeClass('hide');
        $("#menuLeft .leftmenu .deviceManage").siblings().addClass('hide');
    }else{
        $("#menuLeft .leftmenu .deviceManage").addClass('hide');
        $("#menuLeft .leftmenu .deviceManage").siblings().removeClass('hide');
        $("#menuLeft .leftmenu .sparePartsManage").addClass('hide');
        $("#menuLeft .leftmenu .sparePartsManage").siblings().removeClass('hide');
        $("#menuLeft .leftmenu .modelToolsManage").addClass('hide');
        $("#menuLeft .leftmenu .modelToolsManage").siblings().removeClass('hide');
    }
    var data =localStorage.getItem('dataAudit');
    $scope.dataAudit=data;

    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 5
    };
    $scope.paginationConf1 = {
        currentPage: 1,
        itemsPerPage: 5
    };
    /*查询设备故障信息列表 公用方法 start*/
    $scope.queryTroubleRecordList=function(deviceId){
        $scope.queryTroubleRecordListss = factoryParameterSettingService.queryTroubleRecordLists({
            deviceId:deviceId,//设备ID
            currentPage: $scope.paginationConf1.currentPage,
            itemsPerPage: $scope.paginationConf1.itemsPerPage
        }, function (response) {
            if(response.data.totalCount>=1){
                $scope.paginationConf1.totalItems = response.data.totalCount;
                $scope.troubleRecordList=response.data.list;
                console.log($scope.troubleRecordList)
            }else{
                $scope.paginationConf1.totalItems = 0;
                $scope.troubleRecordList=[];
            }
        }, function (err) {
            console.log(err);
        });
    };
    $scope.queryTroubleRecordList($scope.dataAudit);
    /*查询设备故障信息列表 公用方法 end*/
    /*查询设备信息 start*/
    $scope.onQuery=function () {
        factoryParameterSettingService.deviceListInfo({
            useDept:$scope.useDeptQuery,//使用部门
            deviceType:$scope.deviceTypeQuery,//设备类型
            useStatus:$scope.useStatusQuery,//使用状态
            deviceFlag:$scope.deviceFlagQuery,//设备标志
            currentPage: $scope.paginationConf.currentPage,
            itemsPerPage: $scope.paginationConf.itemsPerPage
        }, function(response){
            if(response.data.totalCount>=1){
                $scope.paginationConf.totalItems = response.data.totalCount;
                $scope.deviceListInfos=response.data.list
            }else{
                $scope.paginationConf.totalItems = 0;
                $scope.deviceListInfos.length = 0;
            }
        });
    };
    /*查询设备信息 start*/
    /*查询所有生产厂商 start*/
    $scope.queryAllCompanys=function () {
        factoryParameterSettingService.queryAllCompany({
            itemsPerPage: 100000
        }, function(response){
            if(response.data.totalCount>=1){
                $scope.AllCompany= response.data.list;
            }else{
                $scope.AllCompany=[];
            }
        });
    };
    $scope.queryAllCompanys();
    /*查询所有生产厂商 end*/
    /*查询所有部门 start*/
    $scope.queryDepartmentList=function () {
        departmentManageService.queryOrder({
            corporateIdentify:$scope.corporateIdentify
        }, function(response){
            if(response.data!=''&&response.data!=null&&response.data!=undefined&&response.errorCode=='000000'){
                $scope.departmentList=response.data;
            }else{
                console.log(response.errorMessage);
            }
        });
    };
    $scope.queryDepartmentList();

    /*查询所有部门 end*/
    /*查询使用状态，设备标志，设备状态 start*/
    $scope.queryUseStatus=function () {
        factoryParameterSettingService.queryDeviceDictionary({
            code:'device_use_status'
        }, function(response){
            if(response.data!=''&&response.data!=null&&response.data!=undefined&&response.errorCode=='000000'){
                $scope.useStatusList=response.data;
                console.log($scope.useStatusList);
            }else{
                console.log(response.errorMessage);
            }
        });
    };
    $scope.queryUseStatus();

    $scope.queryDeviceFlag=function () {
        factoryParameterSettingService.queryDeviceDictionary({
            code:'device_device_flag'
        }, function(response){
            if(response.data!=''&&response.data!=null&&response.data!=undefined&&response.errorCode=='000000'){
                $scope.deviceFlag=response.data;
                console.log($scope.deviceFlag);
            }else{
                console.log(response.errorMessage);
            }
        });
    };
    $scope.queryDeviceFlag();

    $scope.queryDeviceStatus=function () {
        factoryParameterSettingService.queryDeviceDictionary({
            code:'device_status'
        }, function(response){
            if(response.data!=''&&response.data!=null&&response.data!=undefined&&response.errorCode=='000000'){
                $scope.deviceStatusList=response.data;
                console.log($scope.deviceStatusList);
            }else{
                console.log(response.errorMessage);
            }
        });
    };
    $scope.deviceStatusList=[{
        type:1,
        name:'停机待修'
    },{
        type:2,
        name:'带病运行'
    },{
        type:3,
        name:'其他'
    }];
    $scope.queryDeviceStatus();
    /*查询使用状态，设备标志，设备状态 end*/
    /*查询设备类型 start*/
    $scope.queryDeviceTypeLists=function () {
        factoryParameterSettingService.queryDeviceTypeList({}, function(response){
            if(response.data!=''&&response.data!=null&&response.data!=undefined&&response.errorCode=='000000'){
                $scope.deviceTypeList=response.data;
                console.log($scope.deviceTypeList);
            }else{
                console.log(response.errorMessage);
            }
        });
    };
    $scope.queryDeviceTypeLists();
    /*查询设备类型 end*/

    /*查询故障等级 start*/
    $scope.deviceTroubleLevels=function () {
        factoryParameterSettingService.queryDeviceDictionary({
            code:'device_trouble_level'//字典类型code
        }, function(response){
            if(response.errorCode=='000000'){
                if(response.data!=null&&response.data!=''&&response.data!=undefined){
                    $scope.deviceTroubleLevel=response.data;
                }else{
                    $scope.deviceTroubleLevel=[];
                }
            }else{
                $scope.deviceTroubleLevel=[];
            }
        });
    };
    $scope.deviceTroubleLevels();
    /*查询故障等级 end*/
    /*查询维修工段/班组列表 start*/
    $scope.queryRepairGroupLists=function() {
        $scope.RepairGroupLists = factoryParameterSettingService.queryRepairGroupList({}, function (res) {
            if (res.errorCode == '000000' && res.data!=''&& res.data!=null&& res.data!=undefined) {
                $scope.repairGroupList=res.data;
                console.log($scope.repairGroupList);
            } else {
//                hideDiv('deleteContactCompany');
//                popupDiv('SaveSuccess');
//                $('.SaveSuccess .Message').html(res.errorMessage);
                console.log(res.errorMessage);
            }
        }, function (err) {
            console.log(err);
        });
    };
    $scope.queryRepairGroupLists();
    /*查询维修工段/班组列表 end*/
    /*查询所有故障类型列表 start*/

    $scope.queryAlldDeviceTroubleTypes=function() {
        $scope.queryAlldDeviceTroubleTypeList = factoryParameterSettingService.queryAlldDeviceTroubleType({}, function (res) {
            if (res.errorCode == '000000' && res.data!=''&& res.data!=null&& res.data!=undefined) {
                $scope.deviceTroubleTypeList=res.data;
                console.log($scope.deviceTroubleTypeList);
            } else {
//                hideDiv('deleteContactCompany');
//                popupDiv('SaveSuccess');
//                $('.SaveSuccess .Message').html(res.errorMessage);
                console.log(res.errorMessage);
            }
        }, function (err) {
            console.log(err);
        });
    };
    $scope.queryAlldDeviceTroubleTypes();
    /*查询所有故障类型列表 end*/
    /*新增设备信息 start*/
    $scope.addContactCompany=function(){
        popupDiv('addContactBalance');
        $scope.addContactSure=function(){
            if($scope.deviceName==''||$scope.deviceName==null||$scope.deviceName==undefined){
                $("#deviceName").focus()
            }else{
                if($('input[name="deviceFlag"]:checked').length<1){
                    alert('请至少选择一个设备标志');
                }else{
                    $scope.deviceFlag = '';
                    $('input[name="deviceFlag"]:checked').each(function(){
                        var sfruit=$(this).val();
                        $scope.deviceFlag +=','+sfruit;
                    });
                    $scope.deviceFlag=$scope.deviceFlag.substr(1,$scope.deviceFlag.length);
                    console.log($scope.deviceFlag);
                    $scope.buyDates=$filter('date')($scope.buyDate,'yyyy-MM-dd');
                    console.log($scope.buyDates);
                    var pa={
                        name:$scope.deviceName,
                        code:$scope.deviceCode,
                        specification:$scope.specification,//规格型号
                        manufacturer:$scope.manufacturer,//生产厂商
                        supplier:$scope.supplier,//供应商
                        deviceType:$scope.deviceType,//设备类型
                        deviceFlag:$scope.deviceFlag,//设备标识，多个标识用逗号分割
                        buyDate:$scope.buyDates,//购买时间
                        sourcePrice:$scope.sourcePrice,//资产原值
                        netWorth:$scope.netWorth,//资产净值
                        head:$scope.head,//资产负责人
                        usefulLife:$scope.usefulLife,//使用寿命
                        netResidueRate:$scope.netResidueRate,//净残率（%）
                        useStatus:$scope.useStatus,//使用状况
                        useDept:$scope.useDept,//使用部门
                        operator:$scope.operator,//操作人员
                        repairName:$scope.repairName,//维修班组
                        installationAddress:$scope.installationAddress,//安装地点
                        checkCircle:$scope.checkCircle,//检验周期
                        lastCheckDate:$scope.lastCheckDate,//上次检验时间
                        nextCheckDate:$scope.nextCheckDate,//下次检验时间
                        extendFieldOne:$scope.extendFieldOne,//固定资产编号
                        extendFieldTwo:$scope.extendFieldTwo,//功率
                        extendFieldThree:$scope.extendFieldThree,//自定义3
                        extendFieldFour:$scope.extendFieldFour,//自定义4
                        extendFieldFive:$scope.extendFieldFive,//自定义5
                        extendFieldSix:$scope.extendFieldSix,//自定义6
                        extendFieldSeven:$scope.extendFieldSeven,//自定义7
                        extendFieldEight:$scope.extendFieldEight,//自定义8
                        extendFieldNine:$scope.extendFieldNine,//自定义9
                        extendFieldTen:$scope.extendFieldTen,//自定义10
                        Number:$scope.Number,//台数
                        startDate:$scope.startDate,//启动日期
                        remark:$scope.remark //备注
                    };
                    $scope.saveDeviceInfos = factoryParameterSettingService.saveDeviceInfo(pa,function(res){
                        if(res.errorCode=='000000'){
                            hideDiv('addContactBalance');
                            popupDiv('SaveSuccess');
                            $('.SaveSuccess .Message').html(res.errorMessage);
                        }else{
                            hideDiv('addContactBalance');
                            popupDiv('SaveSuccess');
                            $('.SaveSuccess .Message').html(res.errorMessage);
                        }
                    },function(err){
                        console.log(err);
                    });
                }
            }
        }
    };
    /*新增设备信息 end*/
    /*编辑设备信息 start*/
    $scope.editContactCompanyBalance=function(res){
        factoryParameterSettingService.queryOneDeviceListInfo({
            id:res.id
        }, function(response){
            if(response.data!=''&&response.data!=null&&response.data!=undefined&&response.errorCode=='000000'){
                $scope.oneDeviceType=response.data;
                console.log($scope.oneDeviceType);
                $scope.deviceNameEdit=$scope.oneDeviceType.name;
                $scope.deviceCodeEdit=$scope.oneDeviceType.code;
                $scope.specificationEdit=$scope.oneDeviceType.specification;
                // 生产厂商
                $("#manufacturerEdit option").each(function(){
                    if($(this).text()==$scope.oneDeviceType.manufacturer){
                        console.log($(this).text(),$scope.oneDeviceType.manufacturer);
                        $(this).prop("selected",true);
                    }
                });

                // 供应商
                $("#supplierEdit option").each(function(){
                    if($(this).text()==$scope.oneDeviceType.supplier){
                        $(this).prop("selected",true);
//                        $scope.supplierEdit=$(this).val();
                    }
                });

                // 设备类型
                $("#deviceTypeEdit option").each(function(){
                    if($(this).text()==$scope.oneDeviceType.deviceType){
                        $(this).prop("selected",true);
//                        $scope.deviceTypeEdit=$(this).val();
                    }
                });

                /*拥有的设备标志选中 start*/
                $scope.deviceFlagEdit=$scope.oneDeviceType.deviceFlag;
                var inputVal=$(".editContactBalance input[name='deviceFlagEdit']");
                if($scope.deviceFlagEdit!=null&&$scope.deviceFlagEdit!=''&&$scope.deviceFlagEdit!=undefined){
                    for(var i=0;i<inputVal.length;i++){
//                        console.log(inputVal.eq(i).val());
//                        console.log($scope.roles);
                        if($scope.deviceFlagEdit.indexOf(inputVal.eq(i).val().toString())!=-1){
//                            console.log($scope.roles);
                            inputVal.eq(i).prop('checked',true);
                        }else{
                            inputVal.eq(i).prop('checked',false);
                        }
                    }
                }
                /*拥有的设备标志选中 end*/
                $("#buyDateEdit").val($filter('date')($scope.oneDeviceType.buyDate, 'yyyy-MM-dd'));
                $scope.sourcePriceEdit=$scope.oneDeviceType.sourcePrice;
                $scope.netWorthEdit=$scope.oneDeviceType.netWorth;
                $scope.headEdit=$scope.oneDeviceType.head;
                $scope.usefulLifeEdit=$scope.oneDeviceType.usefulLife;
                $scope.netResidueRateEdit=$scope.oneDeviceType.netResidueRate;
                // 使用状况
                $("#useStatusEdit option").each(function(i,item){
                    if($(this).text()==$scope.oneDeviceType.useStatus){
                        $(this).prop("selected",true);

//                        $scope.useStatusEdit=$(this).val();
                    }
                });

                // 使用部门
                $("#useDeptEdit option").each(function(){
                    if($(this).text()==$scope.oneDeviceType.useDept){
                        $(this).prop("selected",true);
//                        $scope.useDeptEdit=$(this).val();
                    }
                });


                $scope.operatorEdit=$scope.oneDeviceType.operator;
                $scope.repairNameEdit=$scope.oneDeviceType.repairName;
                $scope.installationAddressEdit=$scope.oneDeviceType.installationAddress;
                $scope.checkCircleEdit=$scope.oneDeviceType.checkCircle;
                $scope.lastCheckDateEdit=$scope.oneDeviceType.lastCheckDate;
                $scope.nextCheckDateEdit=$scope.oneDeviceType.nextCheckDate;
                $scope.extendFieldOneEdit=$scope.oneDeviceType.extendFieldOne;
                $scope.extendFieldTwoEdit=$scope.oneDeviceType.extendFieldTwo;
                $scope.extendFieldThreeEdit=$scope.oneDeviceType.extendFieldThree;
                $scope.extendFieldFourEdit=$scope.oneDeviceType.extendFieldFour;
                $scope.extendFieldFiveEdit=$scope.oneDeviceType.extendFieldFive;
                $scope.extendFieldSixEdit=$scope.oneDeviceType.extendFieldSix;
                $scope.extendFieldSevenEdit=$scope.oneDeviceType.extendFieldSeven;
                $scope.extendFieldEightEdit=$scope.oneDeviceType.extendFieldEight;
                $scope.extendFieldNineEdit=$scope.oneDeviceType.extendFieldNine;
                $scope.extendFieldTenEdit=$scope.oneDeviceType.extendFieldTen;
                $scope.NumberEdit=$scope.oneDeviceType.number;
                $scope.startDateEdit=$scope.oneDeviceType.startDate;
                $scope.remarkEdit=$scope.oneDeviceType.remark;
            }else{
                console.log(response.errorMessage);
            }
        });

        popupDiv('editContactBalance');
        $scope.editDeviceSure=function(){
            if($scope.deviceNameEdit==''||$scope.deviceNameEdit==null||$scope.deviceNameEdit==undefined){
                $("#deviceNameEdit").focus()
            }else{
                if($('.editContactBalance input[name="deviceFlagEdit"]:checked').length<1){
                    alert('请至少选择一个设备标志');
                }else{
                    $scope.deviceFlag = '';
                    $('.editContactBalance input[name="deviceFlagEdit"]:checked').each(function(){
                        var sfruit=$(this).val();
                        $scope.deviceFlag +=','+sfruit;
                    });
                    $scope.deviceFlag=$scope.deviceFlag.substr(1,$scope.deviceFlag.length);
                    console.log($scope.deviceFlag);
                    $scope.buyDates=$filter('date')($scope.buyDateEdit,'yyyy-MM-dd');
                    console.log($scope.buyDates);

                    $scope.deviceTypeEdit=$('#deviceTypeEdit').val().split(':')[1];
                    $scope.useStatusEdit=$('#useStatusEdit').val().split(':')[1];
                    $scope.useDeptEdit=$('#useDeptEdit').val().split(':')[1];
                    $scope.supplierEdit=$('#supplierEdit').val().split(':')[1];
                    $scope.manufacturerEdit=$('#manufacturerEdit').val().split(':')[1];

                    var pa={
                        id:res.id,
                        name:$scope.deviceNameEdit,
                        code:$scope.deviceCodeEdit,
                        specification:$scope.specificationEdit,//规格型号
                        manufacturer:$scope.manufacturerEdit,//生产厂商
                        supplier:$scope.supplierEdit,//供应商
                        deviceType:$scope.deviceTypeEdit,//设备类型
                        deviceFlag:$scope.deviceFlag,//设备标识，多个标识用逗号分割
                        buyDate:$scope.buyDates,//购买时间
                        sourcePrice:$scope.sourcePriceEdit,//资产原值
                        netWorth:$scope.netWorthEdit,//资产净值
                        head:$scope.headEdit,//资产负责人
                        usefulLife:$scope.usefulLifeEdit,//使用寿命
                        netResidueRate:$scope.netResidueRateEdit,//净残率（%）
                        useStatus:$scope.useStatusEdit,//使用状况
                        useDept:$scope.useDeptEdit,//使用部门
                        operator:$scope.operatorEdit,//操作人员
                        repairName:$scope.repairNameEdit,//维修班组
                        installationAddress:$scope.installationAddressEdit,//安装地点
                        checkCircle:$scope.checkCircleEdit,//检验周期
                        lastCheckDate:$scope.lastCheckDateEdit,//上次检验时间
                        nextCheckDate:$scope.nextCheckDateEdit,//下次检验时间
                        extendFieldOne:$scope.extendFieldOneEdit,//固定资产编号
                        extendFieldTwo:$scope.extendFieldTwoEdit,//功率
                        extendFieldThree:$scope.extendFieldThreeEdit,//自定义3
                        extendFieldFour:$scope.extendFieldFourEdit,//自定义4
                        extendFieldFive:$scope.extendFieldFiveEdit,//自定义5
                        extendFieldSix:$scope.extendFieldSixEdit,//自定义6
                        extendFieldSeven:$scope.extendFieldSevenEdit,//自定义7
                        extendFieldEight:$scope.extendFieldEightEdit,//自定义8
                        extendFieldNine:$scope.extendFieldNineEdit,//自定义9
                        extendFieldTen:$scope.extendFieldTenEdit,//自定义10
                        Number:$scope.NumberEdit,//台数
                        startDate:$scope.startDateEdit,//启动日期
                        remark:$scope.remarkEdit //备注
                    };
                    $scope.saveDeviceInfos = factoryParameterSettingService.saveDeviceInfo(pa,function(res){
                        if(res.errorCode=='000000'){
                            hideDiv('editContactBalance');
                            popupDiv('SaveSuccess');
                            $('.SaveSuccess .Message').html(res.errorMessage);
                        }else{
                            hideDiv('editContactBalance');
                            popupDiv('SaveSuccess');
                            $('.SaveSuccess .Message').html(res.errorMessage);
                        }
                    },function(err){
                        console.log(err);
                    });
                }
            }
        }
    };
    /*编辑设备信息 end*/
    /*删除设备信息 start*/
    $scope.deleteContactCompanyBalance=function(res){
        if($("#apply_infos_table input[type='checkbox']:checked").size()>=1){
            popupDiv('deleteContactCompany');
            $scope.deleteDeviceSure=function(){
                var ids = '';
                $('input[name="che"]:checked').each(function(){
                    var sfruit=$(this).val();
                    ids +=','+sfruit;
                });
                ids=ids.substr(1,ids.length);
                console.log(ids);
                $scope.batchDeleteDeviceInfos = factoryParameterSettingService.batchDeleteDeviceInfo({
                    ids:ids
                },function(res){
                    if(res.errorCode=='000000'){
                        hideDiv('deleteContactCompany');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(res.errorMessage);
                    }else{
                        hideDiv('deleteContactCompany');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(res.errorMessage);
                    }
                },function(err){
                    console.log(err);
                });
            }
        }else{
            alert('请选择您要删除的设备')
        }
    };
    /*删除设备信息 end*/
    /*批量删除设备信息 start*/
    $scope.deleteAllRepairGroup=function(){
        if($(".tableListDiv tr td input[name='che']:checked").length<1){
            popupDiv('SaveSuccess');
            $('.SaveSuccess .Message').html('请至少选中一个需要删除的班组');
        }else{
            popupDiv('deleteRepairGroupContainer');
            $scope.ids='';
            $(".tableListDiv tr td input[name='che']:checked").each(function(){
                var sfruit=$(this).val();
                $scope.ids +=','+sfruit;
            });
            $scope.ids=$scope.ids.substr(1,$scope.ids.length);
            console.log($scope.ids);
            $scope.deleteAllRepairGroupSure=function(){
                $scope.deleteRepairGroups = factoryParameterSettingService.deleteRepairGroup({
                    ids:$scope.ids
                }, function (res) {
                    if (res.errorCode == '000000' && res.data!=''&& res.data!=null&& res.data!=undefined) {
                        hideDiv('deleteRepairGroupContainer');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(res.errorMessage);
                    } else {
                        hideDiv('deleteRepairGroupContainer');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(res.errorMessage);
                        console.log(res.errorMessage);
                    }
                }, function (err) {
                    console.log(err);
                });
            }
        }
    };
    /*批量删除设备信息 end*/
    /*查询设备对应的故障列表 start*/
    $scope.deviceTroubleRecord=function ($event,deviceInfo) {
        // $($event.target).parent().css('background','#FBF5E1');
        // $($event.target).parent().siblings().css('background','#fff');
        var dataId = $($event.target).parents('tr').data("appid");
        localStorage.setItem('dataAudit',dataId);
        var data =localStorage.getItem('dataAudit');
        $scope.dataAudit=data;
        $scope.queryTroubleRecordList(deviceInfo.id);
    };
    /*查询设备对应的故障列表 end*/
    /*提出设备对应的故障 start*/
    $scope.addTroubleRecord=function () {
        if($scope.dataAudit==''||$scope.dataAudit==null||$scope.dataAudit==undefined){
            alert('请至少选择一个设备');
        }else{
            $scope.deviceTypes=1;//默认选择
            factoryParameterSettingService.queryOneDeviceListInfo({
                id:$scope.dataAudit
            }, function(response){
                if(response.data!=''&&response.data!=null&&response.data!=undefined&&response.errorCode=='000000'){
                    $scope.oneDeviceType=response.data;
                    console.log($scope.oneDeviceType);
                    $scope.deviceNameTrouble=$scope.oneDeviceType.name;
                    $scope.deviceCodeTrouble=$scope.oneDeviceType.code;
                    $scope.specificationTrouble=$scope.oneDeviceType.specification;

                    // 设备类型
                    $("#deviceTypeTrouble option").each(function(){
                        if($(this).text()==$scope.oneDeviceType.deviceType){
                            $(this).prop("selected",true);
//                        $scope.deviceTypeEdit=$(this).val();
                        }
                    });

                    // 使用部门
                    $("#useDeptTrouble option").each(function(){
                        if($(this).text()==$scope.oneDeviceType.useDept){
                            $(this).prop("selected",true);
//                        $scope.useDeptEdit=$(this).val();
                        }
                    });

                }else{
                    console.log(response.errorMessage);
                }
            });
            popupDiv('addTroubleRecordBalance');
            $scope.addTroubleRecordSure=function () {
                if($scope.happendDate==''||$scope.happendDate==null||$scope.happendDate==undefined){
                    $("#happendDate").focus()
                }else if($scope.deviceUser==''||$scope.deviceUser==null||$scope.deviceUser==undefined){
                    $("#deviceUser").focus()
                }else if($scope.deviceUserPhone==''||$scope.deviceUserPhone==null||$scope.deviceUserPhone==undefined){
                    $("#deviceUserPhone").focus()
                }else if($scope.troubleLevel==''||$scope.troubleLevel==null||$scope.troubleLevel==undefined){
                    $("#troubleLevel").focus()
                }else if($scope.deviceStatus==''||$scope.deviceStatus==null||$scope.deviceStatus==undefined){
                    $("#deviceStatus").focus()
                }else if($scope.troubleType==''||$scope.troubleType==null||$scope.troubleType==undefined){
                    $("#troubleType").focus()
                }else if($scope.repairGroupId==''||$scope.repairGroupId==null||$scope.repairGroupId==undefined){
                    $("#repairGroupId").focus()
                }else if($scope.deviceAddress==''||$scope.deviceAddress==null||$scope.deviceAddress==undefined){
                    $("#deviceAddress").focus()
                }else if($scope.troubleAddRemark==''||$scope.troubleAddRemark==null||$scope.troubleAddRemark==undefined){
                    $("#troubleAddRemark").focus()
                }else{
                    $scope.happenTimes=$filter('date')($scope.happenTime,'yyyy-MM-dd');
                    var pa={
                        deviceId:$scope.dataAudit,
                        deviceStatus:$scope.deviceStatus,
                        deviceUser:$scope.deviceUser,
                        happenTime:$scope.happenTimes,
                        phone:$scope.deviceUserPhone,
                        remark:$scope.troubleAddRemark,
                        repairGroupId:$scope.repairGroupId,
                        // requestTime:$scope.deviceStatus,
                        troubleLevel:$scope.troubleLevel,
                        troubleType:$scope.troubleType
                    };
                    $scope.addTroubleRecord = factoryParameterSettingService.addTroubleRecords(pa, function (res) {
                        if (res.errorCode == '000000' && res.data!=''&& res.data!=null&& res.data!=undefined) {
                            hideDiv('addTroubleRecordBalance');
                            popupDiv('SaveSuccess');
                            $('.SaveSuccess .Message').html(res.errorMessage);
                        } else {
                            hideDiv('addTroubleRecordBalance');
                            popupDiv('SaveSuccess');
                            $('.SaveSuccess .Message').html(res.errorMessage);
                            console.log(res.errorMessage);
                        }
                    }, function (err) {
                        console.log(err);
                    });
                }
            }
        }
    };
    /*提出设备对应的故障 end*/
    /*故障信息详情查询 start*/
    $scope.editTroubleRecord=function (res) {
        if($scope.dataAudit==''||$scope.dataAudit==null||$scope.dataAudit==undefined){
            alert('请至少选择一个设备');
        }else{
            $scope.deviceTypesEdit=1;//默认选择
            factoryParameterSettingService.queryOneDeviceListInfo({
                id:$scope.dataAudit
            }, function(response){
                if(response.data!=''&&response.data!=null&&response.data!=undefined&&response.errorCode=='000000'){
                    $scope.oneDeviceType=response.data;
                    console.log($scope.oneDeviceType);
                    $scope.deviceNameTroubleEdit=$scope.oneDeviceType.name;
                    $scope.deviceCodeTroubleEdit=$scope.oneDeviceType.code;
                    $scope.specificationTroubleEdit=$scope.oneDeviceType.specification;

                    // 设备类型
                    $("#deviceTypeTroubleEdit option").each(function(){
                        if($(this).text()==$scope.oneDeviceType.deviceType){
                            $(this).prop("selected",true);
//                        $scope.deviceTypeEdit=$(this).val();
                        }
                    });
                    // 使用部门
                    $("#useDeptTroubleEdit option").each(function(){
                        if($(this).text()==$scope.oneDeviceType.useDept){
                            $(this).prop("selected",true);
//                        $scope.useDeptEdit=$(this).val();
                        }
                    });

                }else{
                    console.log(response.errorMessage);
                }
            });
            // $scope.happendDateEdit=$filter('date')(res.happenTime,'yyyy-MM-dd');//发生时间
            $("#happendDateEdit").val($filter('date')(res.happenTime,'yyyy-MM-dd'));//发生时间
            $scope.deviceUserEdit=res.deviceUser;//操作者
            $scope.deviceUserPhoneEdit=res.phone;//操作者电话
            $scope.deviceAddressEdit=res.deviceAddress;//设备位置
            $scope.troubleAddRemarkEdits=res.remark;//故障描述
            // $scope.troubleLevelEdit=res.troubleLevel;
            // $scope.deviceStatusEdit=res.deviceStatus;
            // $scope.troubleTypeEdit=res.troubleType;
            // $scope.repairGroupIdEdit=res.repairGroupId;
            // 故障等级
            $("#troubleLevelEdit option").each(function(){
                if($(this).text()==res.troubleLevel){
                    $(this).prop("selected",true);
                    console.log($(this).text())
                }
            });
            // 设备状态
            $("#deviceStatusEdit option").each(function(){
                if($(this).text()==res.deviceStatus){
                    $(this).prop("selected",true);
                    console.log($(this).text())
                }
            });
            // 故障类别
            $("#troubleTypeEdit option").each(function(){
                if($(this).text()==res.troubleType){
                    $(this).prop("selected",true);
                    console.log($(this).text())
                }
            });
            // 故障类别
            $("#repairGroupIdEdit option").each(function(){
                if($(this).text()==res.repairGroupId){
                    $(this).prop("selected",true);
                    console.log($(this).text())
                }
            });
            popupDiv('editTroubleRecordBalance');
        }
    };
    /*故障信息详情查询 end*/
    /*批量删除故障 start*/
    $scope.deleteAllTroubleRecords=function(){
        if($(".tableListDiv tr td input[name='che1']:checked").length<1){
            popupDiv('SaveSuccess');
            $('.SaveSuccess .Message').html('请至少选中一个需要删除的故障');
        }else{
            popupDiv('deleteTroubleRecords');
            $scope.ids='';
            $(".tableListDiv tr td input[name='che1']:checked").each(function(){
                var sfruit=$(this).val();
                $scope.ids +=','+sfruit;
            });
            $scope.ids=$scope.ids.substr(1,$scope.ids.length);
            console.log($scope.ids);
            $scope.deleteTroubleRecordSure=function(){
                $scope.deleteTroubleRecords = factoryParameterSettingService.batchDeleteTroubleRecords({
                    ids:$scope.ids
                }, function (res) {
                    if (res.errorCode == '000000' && res.data!=''&& res.data!=null&& res.data!=undefined) {
                        hideDiv('deleteTroubleRecords');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(res.errorMessage);
                    } else {
                        hideDiv('deleteTroubleRecords');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(res.errorMessage);
                        console.log(res.errorMessage);
                    }
                }, function (err) {
                    console.log(err);
                });
            }
        }
    };
    /*批量删除故障 end*/
    $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.onQuery);
});