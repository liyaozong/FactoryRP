/**
 * Created by jayvenLee on 2017/11/11.
 */
factoryParameterSettingApp.controller('sparePartsManageController',function ($scope,UrlService,$http,$filter,$cookies,$resource, $location, $state, spareParts,deviceSpares,deviceType,validate) {
    $scope.WebURL=UrlService.getUrl('factoryServe');
    if($location.path()=='/main/sparePartsManage'){
        $("#menuLeft .leftmenu .sparePartsManage .menuson").css('display','block');
        $("#menuLeft .leftmenu .sparePartsManage").removeClass('hide');
        $("#menuLeft .leftmenu .sparePartsManage").siblings().addClass('hide');
    }else{
        $("#menuLeft .leftmenu .deviceManage").addClass('hide');
        $("#menuLeft .leftmenu .deviceManage").siblings().removeClass('hide');
        $("#menuLeft .leftmenu .sparePartsManage").addClass('hide');
        $("#menuLeft .leftmenu .sparePartsManage").siblings().removeClass('hide');
        $("#menuLeft .leftmenu .modelToolsManage").addClass('hide');
        $("#menuLeft .leftmenu .modelToolsManage").siblings().removeClass('hide');
    }
    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 5
    };
    $scope.sparePartType='';//备件类型参数
    $scope.inventoryUpperLimit="";//库存上限
    $scope.inventoryFloor="";//库存下限
    $scope.name="";//备件名称
    $scope.specificationsAndodels="";//规格型号
    $scope.code="";//备件编码
    $scope.barCode="";//条形码
    $scope.manufacturer="";//生产厂商
    //查询备件类型
    deviceSpares.list().success(function (data) {
        $scope.sparePartTypeLists=data.data;
    });
    //查询计量单位 device_measuring_unit
    spareParts.findByCode('device_measuring_unit').success(function (data) {
       $scope.measuringUnitLists=data.data;
    });
    //查询设备类型
    deviceType.list().success(function (data) {
        $scope.deviceTypeList=data.data;
    });

    /*查询设备信息 start*/
    $scope.onQuery=function () {
        var req={
            "barCode": $scope.barCode, //条形码
            "code": $scope.code,//备件编号
            // "conversionRatio": 0,//换算比例
            // "corporateIdentify": 32132132132213,//企业唯一标示
            "currentPage": $scope.paginationConf.currentPage, //当前页码
            "inventoryFloor": $scope.inventoryFloor,//库存下限
            "inventoryUpperLimit": $scope.inventoryUpperLimit,//库存上限
            "itemsPerPage": $scope.paginationConf.itemsPerPage,//每页显示记录数
            // "manufacturer": $scope.manufacturer,//生产厂商
            // "materialProperties": "string",//无聊属性
            // "measuringUnit": 0,//计量单位
            "name": $scope.name,//备件名称
            // "referencePrice": 0,//参考价
            // "replacementCycle": 0,//更换周期
            "requestTime": $filter('date')(new Date(),'yyyy-MM-dd HH:mm:ss'),//请求时间
            "sparePartType": $scope.sparePartType,//备件类型
            "specificationsAndodels": $scope.specificationsAndodels//规格型号
            // "suppliers": 0,//供应商
            // "unitConversion": 0//换算单位
        };
        spareParts.findByPage(req).success(function (data) {
            console.log(data);
            if(data.data.totalCount>=1){
                $scope.paginationConf.totalItems = data.data.totalCount;
                // $scope.deviceListInfos=data.data.list
            }else{
                $scope.paginationConf.totalItems = 0;
                // $scope.deviceListInfos.length = 0;
            }
        });
        // spareParts.deviceListInfo({
        //     name:$scope.cusName,//姓名
        //     currentPage: $scope.paginationConf.currentPage,
        //     itemsPerPage: $scope.paginationConf.itemsPerPage
        // }, function(response){
        //     if(response.data.totalCount>=1){
        //         $scope.paginationConf.totalItems = response.data.totalCount;
        //         $scope.deviceListInfos=response.data.list
        //     }else{
        //         $scope.paginationConf.totalItems = 0;
        //         $scope.deviceListInfos.length = 0;
        //     }
        // });
    };
    /*查询设备信息 end*/
    $scope.onQuery();

    /* 查询往来单位 */
    $scope.paginationConf_CC = {
        currentPage: 1,
        itemsPerPage: 10
    };

    $scope.ccQuery={
        code:'',
        contactName:'',
        name:''
    };
    $scope.onQuery_cc=function () {
        var ccReq={
            "code": $scope.ccQuery.code,//单位编码
            "contactName": $scope.ccQuery.contactName,//联系人名称
            "currentPage": $scope.paginationConf_CC.currentPage,//当前页码
            "itemsPerPage": $scope.paginationConf_CC.itemsPerPage,//每页显示记录数
            "name": $scope.ccQuery.name,//单位名称
            "requestTime": $filter('date')(new Date(),'yyyy-MM-dd HH:mm:ss')//请求时间
        };

        spareParts.contactCompanyList(ccReq).success(function (data) {
            console.log('往来单位',data);
            if(data.data.totalCount>=1){
                $scope.paginationConf_CC.totalItems = data.data.totalCount;
                // $scope.deviceListInfos=data.data.list
            }else{
                $scope.paginationConf_CC.totalItems = 0;
                // $scope.deviceListInfos.length = 0;
            }
            $scope.orderList=data.data.list;
            hideDiv('addSparePartsPop');
            popupDiv('contactCompanyListPop');
        });
    };
    //打开厂商或者供应商选择层
    $scope.openCC=function (type) {
        $scope.ccQuery={
            code:'',
            contactName:'',
            name:''
        };
        $scope.onQuery_cc();
        $scope.ccType=type;
    };
    //选中厂商或供应商
    $scope.changeCC=function (id,name,$event) {
        // console.log(id,name,$($event.target).parent().parent().html());
        $($event.target).parent().parent().find('tr').removeClass('ccTr');
        $($event.target).parent().addClass('ccTr');
        $scope.ccId=id;
        $scope.ccName=name;
    };
    $scope.changeCCSure=function () {
        if($scope.ccType=='1'){
            $scope.editSpareParts.manufacturerName=$scope.ccName;
            $scope.editSpareParts.manufacturer=$scope.ccId;
        }else  if($scope.ccType=='2'){
            $scope.editSpareParts.suppliersName=$scope.ccName;
            $scope.editSpareParts.suppliers=$scope.ccId;
        }
        hideDiv('contactCompanyListPop');
        popupDiv('addSparePartsPop');
    };

    /*新增设备信息 start*/
    $scope.editSpareParts={
        sparePartType:'',//备件类型
        device_id:'',//关联设备
        name:'',//备件名称
        specificationsAndodels:'',//规格型号
        code:'',//备件编号
        barCode:"",//条形码
        measuringUnit:'',//计量单位
        replacementCycle:'',//更换周期
        manufacturer:'',//生产厂商
        suppliers:'',//供应商
        inventoryFloor:'',//库存下限
        inventoryUpperLimit:'',//库存上限
        referencePrice:"",//参考价
        unitConversion:'',//换算单位
        conversionRatio:'',//换算比例
        materialProperties:'',//物料属性
        extendFieldOne:'1',//文本型1
        extendFieldTwo:'',//文本型2
        extendFieldThree:'',//文本型3
        extendFieldFour:'',//文本型4
        extendFieldFive:'',//文本型5
        extendFieldSix:'',//文本型6
        extendFieldSeven:'',//文本型7
        extendDateFieldOne:'',//自定义日期1
        extendDateFieldTwo:'',//自定义日期2
        remark:''//备注
    };
    $scope.addSparePartsSure=function(){
        var addReq={
            sparePartType:$scope.editSpareParts.sparePartType,//备件类型
            device_id:$scope.editSpareParts.device_id,//关联设备
            name:$scope.editSpareParts.name,//备件名称
            specificationsAndodels:$scope.editSpareParts.specificationsAndodels,//规格型号
            code:$scope.editSpareParts.code,//备件编号
            barCode:$scope.editSpareParts.barCode,//条形码
            measuringUnit:$scope.editSpareParts.measuringUnit,//计量单位
            replacementCycle:$scope.editSpareParts.replacementCycle,//更换周期
            manufacturer:$scope.editSpareParts.manufacturer,//生产厂商
            suppliers:$scope.editSpareParts.suppliers,//供应商
            inventoryFloor:$scope.editSpareParts.inventoryFloor,//库存下限
            inventoryUpperLimit:$scope.editSpareParts.inventoryUpperLimit,//库存上限
            referencePrice:$scope.editSpareParts.referencePrice,//参考价
            unitConversion:$scope.editSpareParts.unitConversion,//换算单位
            conversionRatio:$scope.editSpareParts.conversionRatio,//换算比例
            materialProperties:$scope.editSpareParts.materialProperties,//物料属性
            extendFieldOne:$scope.editSpareParts.extendFieldOne,//文本型1
            extendFieldTwo:$scope.editSpareParts.extendFieldTwo,//文本型2
            extendFieldThree:$scope.editSpareParts.extendFieldThree,//文本型3
            extendFieldFour:$scope.editSpareParts.extendFieldFour,//文本型4
            extendFieldFive:$scope.editSpareParts.extendFieldFive,//文本型5
            extendFieldSix:$scope.editSpareParts.extendFieldSix,//文本型6
            extendFieldSeven:$scope.editSpareParts.extendFieldSeven,//文本型7
            extendDateFieldOne:$filter('date')($scope.editSpareParts.extendDateFieldOne,'yyyy-MM-dd'),//自定义日期1
            extendDateFieldTwo:$filter('date')($scope.editSpareParts.extendDateFieldTwo,'yyyy-MM-dd'),//自定义日期2
            remark:$scope.editSpareParts.remark//备注
        };
        var flog;
        console.log(addReq.length);
        for(var i in addReq){
            console.log(i,":",addReq[i]);
            flog=validate.required(addReq[i]);
            if(!flog){
                break;
            }
        }
        if(flog){
            spareParts.addSpareParts(addReq).success(function (data) {
                if(data.errorCode=='000000'){
                    hideDiv('addSparePartsPop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }else{
                    hideDiv('addSparePartsPop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }
            })
        }else {
            $('.SaveSuccess .Message').html('表单有空值');
        }
    };
    /*新增设备信息 end*/
    /*编辑设备信息 start*/
    $scope.editContactCompanyBalance=function(res){
        $scope.deviceNameEdit=res.name;
        $scope.deviceCodeEdit=res.code;
        $scope.specificationEdit=res.specification;
        $scope.manufacturerEdit=res.manufacturer;
        $scope.supplierEdit=res.supplier;
        $scope.deviceFlag=res.deviceType;
        $scope.buyDateEdit=res.buyDate;
        $scope.sourcePriceEdit=res.sourcePrice;
        $scope.netWorthEdit=res.netWorth;
        $scope.headEdit=res.head;
        $scope.usefulLifeEdit=res.usefulLife;
        $scope.netResidueRateEdit=res.netResidueRate;
        $scope.useStatusEdit=res.useStatus;
        $scope.useDeptEdit=res.useDept;
        $scope.operatorEdit=res.operator;
        $scope.repairNameEdit=res.code;
        $scope.installationAddressEdit=res.installationAddress;
        $scope.checkCircleEdit=res.code;
        $scope.lastCheckDateEdit=res.code;
        $scope.nextCheckDateEdit=res.code;
        $scope.extendFieldOneEdit=res.code;
        $scope.extendFieldTwoEdit=res.code;
        $scope.extendFieldThreeEdit=res.code;
        $scope.extendFieldFourEdit=res.code;
        $scope.extendFieldFiveEdit=res.code;
        $scope.extendFieldSixEdit=res.code;
        $scope.extendFieldSevenEdit=res.code;
        $scope.extendFieldEightEdit=res.code;
        $scope.extendFieldNineEdit=res.code;
        $scope.extendFieldTenEdit=res.code;
        $scope.NumberEdit=res.code;
        $scope.startDateEdit=res.code;
        $scope.remarkEdit=res.remark;
        popupDiv('editContactBalance');
        $scope.editContactSure=function(){
            if($scope.editName==''||$scope.editName==null||$scope.editName==undefined){
                $("#editName").focus()
            }else{
                var pa={
                    id:res.id,
                    name:$scope.editName,
                    code:$scope.editCode,
                    contactName:$scope.editContactName,
                    contactPhone:$scope.editContactPhone,
                    fax:$scope.editFax,
                    taxNumber:$scope.editTaxNumber,
                    email:$scope.editEmail,
                    address:$scope.editAddress,
                    zipCode:$scope.editZipCode,
                    bankName:$scope.editBankName,
                    bankNumber:$scope.editBankNumber,
                    webSite:$scope.editWebSite,
                    remark:$scope.editRemark
                };
                $scope.saveContactCompany = factoryParameterSettingService.saveContactCompany(pa,function(res){
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
    };
    /*编辑设备信息 end*/
    /*删除设备信息 start*/
    $scope.deleteContactCompanyBalance=function(res){
        popupDiv('deleteContactCompany');
        $scope.companyName=res.name;
        $scope.deleteContactCompanySure=function(){
            $scope.deleteContactCompany = factoryParameterSettingService.deleteContactCompany({
                id:res.id
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
    // $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.onQuery);
});