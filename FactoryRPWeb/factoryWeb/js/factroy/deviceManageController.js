/**
 * Created by jayvenLee on 2017/11/11.
 */
factoryParameterSettingApp.controller('deviceManageController',function ($scope,UrlService,$http,$filter,$cookies,$resource, $location, $state, factoryParameterSettingService) {
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
    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 5
    };
    /*查询设备信息 start*/
    $scope.onQuery=function () {
        factoryParameterSettingService.deviceListInfo({
            name:$scope.cusName,//姓名
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
    /*新增设备信息 start*/
    $scope.addContactCompany=function(){
        popupDiv('addContactBalance');
        $scope.addContactSure=function(){
            if($scope.name==''||$scope.name==null||$scope.name==undefined){
                $("#name").focus()
            }else{
                var pa={
                    name:$scope.name,
                    code:$scope.code,
                    contactName:$scope.contactName,
                    contactPhone:$scope.contactPhone,
                    fax:$scope.fax,
                    taxNumber:$scope.taxNumber,
                    email:$scope.email,
                    address:$scope.address,
                    zipCode:$scope.zipCode,
                    bankName:$scope.bankName,
                    bankNumber:$scope.bankNumber,
                    webSite:$scope.webSite,
                    remark:$scope.remark
                };
                $scope.saveContactCompany = factoryParameterSettingService.saveContactCompany(pa,function(res){
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
    $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.onQuery);
});