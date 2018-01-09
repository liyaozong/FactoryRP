/**
 * Created by jayvenLee on 2017/11/11.
 */
factoryParameterSettingApp.controller('sparePartsManageController',function ($scope,UrlService,$http,$filter,$cookies,$resource, $location, $state, spareParts,deviceSpares,deviceType,validate,$timeout) {
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

    $scope.onQuery_cc=function (type) {
        var ccReq={
            "code": $scope.ccQuery.code,//单位编码
            "contactName": $scope.ccQuery.contactName,//联系人名称
            "currentPage": $scope.paginationConf_CC.currentPage,//当前页码
            "itemsPerPage": $scope.paginationConf_CC.itemsPerPage,//每页显示记录数
            "name": $scope.ccQuery.name,//单位名称
            "requestTime": $filter('date')(new Date(),'yyyy-MM-dd HH:mm:ss')//请求时间
        };

        spareParts.contactCompanyList(ccReq).success(function (data) {
            // console.log('往来单位',data);
            if(data.data.totalCount>=1){
                $scope.paginationConf_CC.totalItems = data.data.totalCount;
                // $scope.deviceListInfos=data.data.list
            }else{
                $scope.paginationConf_CC.totalItems = 0;
                // $scope.deviceListInfos.length = 0;
            }
            $scope.orderList=data.data.list;
        });
    };
    $scope.onQuery_cc();

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
            if(data.data.totalCount>=1){
                $scope.paginationConf.totalItems = data.data.totalCount;
                $scope.sparePartsLists=data.data.list;
                $timeout(function () {
                    for(var i=0;i<$scope.sparePartsLists.length;i++){
                        for(var j=0;j<$scope.sparePartTypeLists.length;j++){
                            if($scope.sparePartsLists[i].sparePartType==$scope.sparePartTypeLists[j].id){
                                $scope.sparePartsLists[i].sparePartTypeName=$scope.sparePartTypeLists[j].name;
                            }
                        }
                        for(var k=0;k<$scope.measuringUnitLists.length;k++){
                            if($scope.sparePartsLists[i].measuringUnit==$scope.measuringUnitLists[k].id){
                                $scope.sparePartsLists[i].measuringUnitName=$scope.measuringUnitLists[k].name;
                            }
                        }
                        for(var m=0;m<$scope.orderList.length;m++){
                            if($scope.sparePartsLists[i].manufacturer==$scope.orderList[m].id){
                                $scope.sparePartsLists[i].manufacturerNameTd=$scope.orderList[m].name;
                            }
                        }
                    }
                },500);

            }else{
                $scope.paginationConf.totalItems = 0;
                $scope.sparePartsLists.length = 0;
            }
        });
    };
    /*查询设备信息 end*/
    $scope.onQuery();


    //打开厂商或者供应商选择层
    $scope.openCC=function (type) {
        $scope.ccQuery={
            code:'',
            contactName:'',
            name:''
        };
        $scope.onQuery_cc(type);
        $scope.ccType=type;
        if($scope.ccType=='1'){
            $scope.contactCompanyName='生产厂商';
        }else  if($scope.ccType=='2'){
            $scope.contactCompanyName='供应商';
        }
        hideDiv('addSparePartsPop');
        popupDiv('contactCompanyListPop');
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
    $scope.errFlog=false;
    /*新增设备信息 start*/
    $scope.addSpareParts=function () {
        popupDiv('addSparePartsPop');
        $scope.editSpareParts={
            sparePartType:'',//备件类型
            deviceInfoId:'',//关联设备
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
            remark:'',//备注
            id:null
        };
        $('#extendDateFieldOne').val('');
        $('#extendDateFieldTwo').val('');
        $scope.sureFlog=1;
        $scope.errFlog=false;
    };
    $scope.addSparePartsSure=function(){
        var addReq={
            sparePartType:$scope.editSpareParts.sparePartType,//备件类型
            deviceInfoId:$scope.editSpareParts.deviceInfoId,//关联设备
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
            extendDateFieldOne:$filter('date')($scope.editSpareParts.extendDateFieldOne,'yyyy-MM-dd')?$filter('date')($scope.editSpareParts.extendDateFieldOne,'yyyy-MM-dd'):$('#extendDateFieldOne').val(),//自定义日期1
            extendDateFieldTwo:$filter('date')($scope.editSpareParts.extendDateFieldTwo,'yyyy-MM-dd')?$filter('date')($scope.editSpareParts.extendDateFieldTwo,'yyyy-MM-dd'):$('#extendDateFieldTwo').val(),//自定义日期2
            remark:$scope.editSpareParts.remark//备注
        };
        var flog;
        // console.log(addReq.length);
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
                        hideDiv('addSparePartsPop');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }else{
                        hideDiv('addSparePartsPop');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }
                })
            }else if($scope.sureFlog==2){
                addReq.id=$scope.editSpareParts.id;
                console.log('编辑',addReq);
                spareParts.editSpareParts(addReq).success(function (data) {
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
            }
        }else {
            $scope.errFlog=true;
        }
    };
    /*新增设备信息 end*/
    /*编辑设备信息 start*/
    $scope.editSparePartsFuc=function(res){
        $scope.errFlog=false;
        console.log(res);
        popupDiv('addSparePartsPop');
        $scope.editSpareParts={
            sparePartType:res.sparePartType,//备件类型
            deviceInfoId:res.deviceInfoId,//关联设备
            name:res.name,//备件名称
            specificationsAndodels:res.specificationsAndodels,//规格型号
            code:res.code,//备件编号
            barCode:res.barCode,//条形码
            measuringUnit:res.measuringUnit,//计量单位
            replacementCycle:res.replacementCycle,//更换周期
            manufacturer:res.manufacturer,//生产厂商
            suppliers:res.suppliers,//供应商
            inventoryFloor:res.inventoryFloor,//库存下限
            inventoryUpperLimit:res.inventoryUpperLimit,//库存上限
            referencePrice:res.referencePrice,//参考价
            unitConversion:res.unitConversion,//换算单位
            conversionRatio:res.conversionRatio,//换算比例
            materialProperties:res.materialProperties,//物料属性
            extendFieldOne:res.extendFieldOne,//文本型1
            extendFieldTwo:res.extendFieldTwo,//文本型2
            extendFieldThree:res.extendFieldThree,//文本型3
            extendFieldFour:res.extendFieldFour,//文本型4
            extendFieldFive:res.extendFieldFive,//文本型5
            extendFieldSix:res.extendFieldSix,//文本型6
            extendFieldSeven:res.extendFieldSeven,//文本型7
            // extendDateFieldOne:$filter('date')(res.extendDateFieldOne,'yyyy-MM-dd'),//自定义日期1
            // extendDateFieldTwo:$filter('date')(res.extendDateFieldTwo,'yyyy-MM-dd'),//自定义日期2
            remark:res.remark,//备注
            id:res.id
        };
        $('#extendDateFieldOne').val($filter('date')(res.extendDateFieldOne,'yyyy-MM-dd'));
        $('#extendDateFieldTwo').val($filter('date')(res.extendDateFieldTwo,'yyyy-MM-dd'));
        $("#sparePartType1 option").each(function(){
            if($(this).val()==$scope.editSpareParts.sparePartType){
                $(this).prop("selected",true);
            }
        });
        $("#deviceType option").each(function(){
            if($(this).val()==$scope.editSpareParts.deviceInfoId){
                $(this).prop("selected",true);
            }
        });
        $scope.orderList.forEach(function (n,i) {
            // console.log(n.id,res.suppliers);
            if(n.id==res.suppliers){
                $scope.editSpareParts.suppliersName=n.name;
            }
            if(n.id==res.manufacturer){
                $scope.editSpareParts.manufacturerName=n.name;
            }
        });
        $scope.sureFlog=2;
    };
    /*编辑设备信息 end*/
    /*删除设备信息 start*/
    $scope.deleteSparePartsBalance=function(res){
        var $inputCheck=$(".tableListDiv tr td input[name='che']:checked");
        if($inputCheck.length==1){
            popupDiv('deleteSparePartsPop');
            var id=$inputCheck.val();
            // console.log(id);
            $scope.deleteSparePartsSure=function () {
                spareParts.deleteSparePartsById(id).success(function (data) {
                    hideDiv('deleteSparePartsPop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                });
            }

        }else if($inputCheck.length>1){
            popupDiv('deleteSparePartsPop');
            var idList=[];
            $inputCheck.each(function(){
                idList.push($(this).val());
            });
            var ids=idList.join(',');
            // console.log(ids);
            $scope.deleteSparePartsSure=function () {
                spareParts.deleteSparePartsByIds(ids).success(function (data) {
                    hideDiv('deleteSparePartsPop');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                });
            }

        }else  if($inputCheck.length<1){
            popupDiv('SaveSuccess');
            $('.SaveSuccess .Message').html('请至少选中一个需要删除的班组');
        }

    };
    /*删除设备信息 end*/
    $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.onQuery);
    $scope.$watch('paginationConf_CC.currentPage + paginationConf_CC.itemsPerPage', $scope.onQuery_cc);
});