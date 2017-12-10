/**
 * Created by jayvenLee on 2017/11/11.
 */
consumerToBankApp.controller('needSendToCCBankOrderListController',function ($scope,$http,$filter,$cookies, UrlService,$resource, $location, $state, needSendToCCBankListService, ToBankOrderDetailService,ToBankLoanOrderDetailService) {
    $scope.WebURL=UrlService.getUrl('manageAudit');
    var data =localStorage.getItem('dataWait');
    $scope.dataWait=data;
    //配置分页基本参数
    $(".page-total input").blur(function(){
//        alert($(this).val());
        $cookies.put('currentPageNeedSendCCB',$(this).val());
    });
    $(".pagination").click(function(){
//        alert($(".page-total input").val());
        $cookies.put('currentPageNeedSendCCB',$(".page-total input").val());
    });
    $(".page-total select").change(function(){
        var items =$(".page-total select").val();
        var ss = items.substring(7,items.length);
        $cookies.put('itemsPerPageNeedSendCCB',ss);
    });
    var currentPage;
    var itemsPerPage;
    if($cookies.get('currentPageNeedSendCCB')==null||$cookies.get('currentPageNeedSendCCB')==''||$cookies.get('currentPageNeedSendCCB')==undefined){
        currentPage=1;
    }else{
        currentPage=$cookies.get('currentPageNeedSendCCB');
    }
    if($cookies.get('itemsPerPageNeedSendCCB')==null||$cookies.get('itemsPerPageNeedSendCCB')==''||$cookies.get('itemsPerPageNeedSendCCB')==undefined){
        itemsPerPage=15;
    }else{
        itemsPerPage=$cookies.get('itemsPerPageNeedSendCCB');
    }
    $scope.paginationConf = {
        currentPage: currentPage,
        itemsPerPage: itemsPerPage
    };
    $scope.orderList = needSendToCCBankListService.getOrderList();

    /*查询*/
    //用户姓名
    $("#cusName").blur(function(){
        $cookies.put('cusNameNeedSendCCB',$(this).val());
    });
    $scope.cusName=$cookies.get('cusNameNeedSendCCB');
    //用户电话
    $("#telephone").blur(function(){
        $cookies.put('telephoneNeedSendCCB',$(this).val());
    });
    $scope.telephone=$cookies.get('telephoneNeedSendCCB');

    //用户电话批量
    $("#userPhones").blur(function(){
        if($(this).val()!=''){
            $cookies.put('userPhonesNeedSendCCB',$(this).val().match(/\d+/g).join(','));
        }else{
            $cookies.put('userPhonesNeedSendCCB',$(this).val());
        }
    });
    $scope.userPhones=$cookies.get('userPhonesNeedSendCCB');

    //创建放款单开始时间
    $("#startDate").blur(function(){
        $cookies.put('startDateNeedSendCCB',$(this).val());
    });
    $scope.startDate=$cookies.get('startDateNeedSendCCB');
    $("#startDate").val($scope.startDate);

    //创建放款单结束时间
    $("#endDate").blur(function(){
        $cookies.put('endDateNeedSendCCB',$(this).val());
    });
    $scope.endDate=$cookies.get('endDateNeedSendCCB');
    $("#endDate").val($scope.endDate);

//    //产品类型
//    $("#productId").blur(function(){
//        $cookies.put('productIdNeedSendCCB',$(this).val());
//    });
//    $scope.productId=$cookies.get('productIdNeedSendCCB');

    //资金通道
    $("#zzCodeExists").blur(function(){
        $cookies.put('zzCodeExistsNeedSendCCB',$(this).val());
    });
    $scope.zzCodeExists=$cookies.get('zzCodeExistsNeedSendCCB');

    //备注状态
    $scope.putFlagToCook=function(res){
        $cookies.put('remarkFlagNeedSendCCB',res);
    };
    $scope.remarkFlag=$cookies.get('remarkFlagNeedSendCCB');

    $scope.clearSearch=function(){
        $scope.paginationConf.currentPage=1;
//        $scope.paginationConf.itemsPerPage=15;
        $("#cusName").val('');
        $("#telephone").val('');
        $("#userPhones").val('');
        $("#startDate").val('');
        $("#endDate").val('');
        $("#productId").val('');
        $("#channelProductCode").val('');
        $("#zzCodeExists").val('');
        $("input[type='radio']").removeAttr('checked');
        $scope.cusName=null;
        $scope.telephone=null;
        $scope.userPhones=null;
        $scope.startDate=null;
        $scope.endDate=null;
        $scope.productId=null;
        $scope.channelProductCode=null;
        $scope.remarkFlag=null;
        $scope.zzCodeExists=null;
        $cookies.remove('cusNameNeedSendCCB');
        $cookies.remove('telephoneNeedSendCCB');
        $cookies.remove('userPhonesNeedSendCCB');
        $cookies.remove('startDateNeedSendCCB');
        $cookies.remove('endDateNeedSendCCB');
        $cookies.remove('productIdNeedSendCCB');
        $cookies.remove('channelProductCodeNeedSendCCB');
        $cookies.remove('remarkFlagNeedSendCCB');
        $cookies.remove('zzCodeExistsNeedSendCCB');
        $cookies.remove('currentPageNeedSendCCB');
        $cookies.remove('itemsPerPageNeedSendCCB');
        $scope.onQuery();
    };

    $scope.onQuery=function () {
        $scope.startDates=$filter('date')($scope.startDate,'yyyy-MM-dd');
        $scope.endDates=$filter('date')($scope.endDate,'yyyy-MM-dd');
        needSendToCCBankListService.queryOrder({
            name:$scope.cusName,//姓名
            phone:$scope.telephone,//用户电话
            createStartTime:$scope.startDates,//查询创建开始时间
            createEndTime:$scope.endDates,//查询创建结束时间
            loanProductType:$scope.productId,//贷款产品类型
            channelProductCode:$scope.channelProductCode,//放款金融机构贷款产品类型
            zzCodeExists:$scope.zzCodeExists,//中证码有无中证码有无(1：有，0：无)
            remarkStatus:$scope.remarkFlag,//备注状态
            pageNo: $scope.paginationConf.currentPage,
            pageSize: $scope.paginationConf.itemsPerPage
        }, function(response){
            if(response.obj.totalCount>=1){
                $scope.paginationConf.totalItems = response.obj.totalCount;
            }else{
                $scope.paginationConf.totalItems = 0;
                $scope.orderList.length = 0;
            }
        });
    };
    /*查询*/
    /*****统一分配订单 start*****/
    $scope.statisticWholePop=function(){
        if($("#apply_infos_table input[type='checkbox']:checked").size()>=1){
            popupDiv('statisticWhole');
            $scope.statisticDistrubuteOrder=function(){
                $scope.operatorCodes=$("#disSaler1 input[type='radio']:checked").val();
                $scope.operatorNames=$("#disSaler1 input[type='radio']:checked").parent().next().html();
                var orders = '';
                $('input[name="che"]:checked').each(function(){
                    var sfruit=$(this).val();
                    orders +=','+sfruit;
                });
                orders=orders.substr(1,orders.length);
                console.log(orders);
                var pa={
                    userId:$scope.operatorCodes,
                    type:1,//0:报单环节,1:审批环节
                    ids:orders
                };
//        var par={params:pa};
                console.log(pa);
                $http.post($scope.WebURL+'order/assignOrder',pa).success(function(response){
                    if(response.status==0){
                        hideDiv('statisticWhole');
                        popupDiv('SaveSuccess');
                        $(".SaveSuccess .Message").html(response.message);
                    }else{
                        hideDiv('statisticWhole');
                        popupDiv('SaveSuccess');
                        $(".SaveSuccess .Message").html(response.message);
                    }
                }).error(function(response){
                    alert(response.message);
                })
            };
        }else{
            hideDiv('statisticWhole');
            popupDiv('SaveSuccess');
            $(".SaveSuccess .Message").html('请选择要分配的客户');
        }
    };
    /******统一分配订单 end****/


    $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.onQuery);
    $scope.onhandle= function($event,order){
//  	alert('我是审批待处理')
        //  	只缓存最后一条
        //  	获取点击对应元素的appid
        var dataId = $($event.target).parents('tr').data("appid")
//  	存在localstorage
//		alert(dataId)
        localStorage.setItem('dataWait',dataId);
        var data =localStorage.getItem('dataWait');
        $scope.dataWait=data;

        $state.go('main.consumerToCCBankLoanOrderDetail',{'hideData':0});
        ToBankLoanOrderDetailService.setCurHandleOrder(order);

    }
});