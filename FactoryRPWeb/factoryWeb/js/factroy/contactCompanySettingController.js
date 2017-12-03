/**
 * Created by caolongping on 2016/5/18.
 */
factoryParameterSettingApp.controller('contactCompanySettingController',function ($scope,UrlService,$http,$filter,$cookies,$resource, $location, $state, factoryParameterSettingService) {
    $scope.WebURL=UrlService.getUrl('factoryServe');
    var data =localStorage.getItem('dataWait');
    $scope.dataWait=data;
    //配置分页基本参数
    $(".page-total input").blur(function(){
//        alert($(this).val());
        $cookies.put('currentPageAllSend',$(this).val());
    });
    $(".pagination").click(function(){
//        alert($(".page-total input").val());
        $cookies.put('currentPageAllSend',$(".page-total input").val());
    });
    $(".page-total select").change(function(){
        var items =$(".page-total select").val();
        var ss = items.substring(7,items.length);
        $cookies.put('itemsPerPageAllSend',ss);
    });
    var currentPage;
    var itemsPerPage;
    if($cookies.get('currentPageAllSend')==null||$cookies.get('currentPageAllSend')==''||$cookies.get('currentPageAllSend')==undefined){
        currentPage=1;
    }else{
        currentPage=$cookies.get('currentPageAllSend');
    }
    if($cookies.get('itemsPerPageAllSend')==null||$cookies.get('itemsPerPageAllSend')==''||$cookies.get('itemsPerPageAllSend')==undefined){
        itemsPerPage=15;
    }else{
        itemsPerPage=$cookies.get('itemsPerPageAllSend');
    }
    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 10
    };
    $scope.orderList = factoryParameterSettingService.getOrderList();

    /*查询*/
    //用户姓名
    $("#cusName").blur(function(){
        $cookies.put('cusNameAllSend',$(this).val());
    });
    $scope.cusName=$cookies.get('cusNameAllSend');
    //用户电话
    $("#telephone").blur(function(){
        $cookies.put('telephoneAllSend',$(this).val());
    });
    $scope.telephone=$cookies.get('telephoneAllSend');

    //用户电话批量
    $("#userPhones").blur(function(){
        if($(this).val()!=''){
            $cookies.put('userPhonesAllSend',$(this).val().match(/\d+/g).join(','));
        }else{
            $cookies.put('userPhonesAllSend',$(this).val());
        }
    });
    $scope.userPhones=$cookies.get('userPhonesAllSend');

    $scope.onQuery=function () {
        $scope.startDates=$filter('date')($scope.startDate,'yyyy-MM-dd');
        $scope.endDates=$filter('date')($scope.endDate,'yyyy-MM-dd');
        factoryParameterSettingService.queryOrder({
            name:$scope.cusName,//姓名
            currentPage: $scope.paginationConf.currentPage,
            itemsPerPage: $scope.paginationConf.itemsPerPage
        }, function(response){
            if(response.data.totalCount>=1){
                $scope.paginationConf.totalItems = response.data.totalCount;
                $scope.orderList=response.data.list
            }else{
                $scope.paginationConf.totalItems = 0;
                $scope.orderList.length = 0;
            }
        });
    };
    /*查询*/
    /*新增往来单位 start*/
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
    /*新增往来单位 end*/
    /*编辑往来单位 start*/
    $scope.editContactCompanyBalance=function(res){
        $scope.editName=res.name;
        $scope.editCode=res.code;
        $scope.editContactName=res.contactName;
        $scope.editContactPhone=res.contactPhone;
        $scope.editFax=res.fax;
        $scope.editTaxNumber=res.taxNumber;
        $scope.editEmail=res.email;
        $scope.editAddress=res.address;
        $scope.editZipCode=res.zipCode;
        $scope.editBankName=res.bankName;
        $scope.editBankNumber=res.bankNumber;
        $scope.editWebSite=res.webSite;
        $scope.editRemark=res.remark;
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
    /*编辑往来单位 end*/
    /*删除往来单位 start*/
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
    /*删除往来单位 end*/

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
    /*编辑维修工段 start*/
    $scope.editRepairGroup=function(res){
        popupDiv('editRepairGroup');
        $scope.editName=res.name;
        $scope.editCode=res.code;
        $scope.editRepairGroupSure=function(){
            if($scope.editName==''||$scope.editName==null||$scope.editName==undefined){
                $("#editName").focus()
            }else if($scope.editCode==''||$scope.editCode==null||$scope.editCode==undefined){
                $("#editCode").focus()
            }else{
                $scope.addRepairGroups = factoryParameterSettingService.addRepairGroup({
                    id:res.id,
                    name:$scope.editName,
                    code:$scope.editCode
                }, function (res) {
                    if (res.errorCode == '000000' && res.data!=''&& res.data!=null&& res.data!=undefined) {
                        hideDiv('editRepairGroup');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(res.errorMessage);
                    } else {
                        hideDiv('editRepairGroup');
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
    /*编辑维修工段 end*/
    /*新增维修工段 start*/
    $scope.addRepairGroupClick=function(res){
        popupDiv('addRepairGroup');
        $scope.addRepairGroupSure=function(){
            if($scope.name==''||$scope.name==null||$scope.name==undefined){
                $("#name").focus()
            }else if($scope.code==''||$scope.code==null||$scope.code==undefined){
                $("#code").focus()
            }else{
                $scope.addRepairGroups = factoryParameterSettingService.addRepairGroup({
                    name:$scope.name,
                    code:$scope.code
                }, function (res) {
                    if (res.errorCode == '000000' && res.data!=''&& res.data!=null&& res.data!=undefined) {
                        hideDiv('addRepairGroup');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(res.errorMessage);
                    } else {
                        hideDiv('addRepairGroup');
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
    /*新增维修工段 end*/
    /*单个删除 start*/
    $scope.deleteRepairGroup=function(res){
        $scope.repairName=res.name;
        popupDiv('deleteRepairGroup');
        $scope.deleteRepairGroupSure=function(){
            $scope.deleteRepairGroups = factoryParameterSettingService.deleteRepairGroup({
                ids:res.id
            }, function (res) {
                if (res.errorCode == '000000' && res.data!=''&& res.data!=null&& res.data!=undefined) {
                    hideDiv('deleteRepairGroup');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(res.errorMessage);
                } else {
                    hideDiv('deleteRepairGroup');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(res.errorMessage);
                    console.log(res.errorMessage);
                }
            }, function (err) {
                console.log(err);
            });
        }
    };
    /*单个删除 end*/
    /*批量删除维修工段 start*/
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
    /*批量删除维修工段 end*/
    /*******单击红旗按钮 start*********/
    $scope.onFlagEdit= function(order){
        popupDiv('redFlag');
        $scope.editMarkValue = order.flag;
        $scope.editMarkContent= order.flagValue;
        $scope.curHandlerOrder  = order;
    };
    $scope.onFlagSelected=function(markValues){
        $scope.editMarkValue = markValues;
    };
    /*******单击红旗按钮 end*********/
    /*******备注红旗定义 start*********/
    $scope.onBzContent = function () {
        factoryParameterSettingService.queryFlagBz({
            code:$scope.curHandlerOrder.code,
            flag :$scope.editMarkValue,//备注标识
            flagValue :$scope.editMarkContent //备注详情
        }, function(response){
            $scope.curHandlerOrder.flag = $scope.editMarkValue;
            $scope.curHandlerOrder.flagValue = $scope.editMarkContent;
            hideDiv('redFlag');
            popupDiv('SaveSuccess');
            $(".Message").html(response.message);
        });
    };
    /*******备注红旗定义 end*********/
    $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.onQuery);
});