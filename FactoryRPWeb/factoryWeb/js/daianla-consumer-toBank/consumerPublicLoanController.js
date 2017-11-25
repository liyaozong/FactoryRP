
consumerToBankApp.controller('consumerPublicLoanController',function ($scope,UrlService,$http,$filter,$cookies, UpgradeAllDealWithService,$resource, $location, $state, consumerPublicLoanService,ToBankOrderDetailService) {
    $scope.WebURL=UrlService.getUrl('xfdbaodan');
    var data =localStorage.getItem('dataPublic');
    $scope.dataPublic=data;
    //    $scope.WebURL='http://192.168.6.34:8085/consumption-audit-web/web/';
    //配置分页基本参数
    $(".page-total input").blur(function(){
//        alert($(this).val());
        $cookies.put('currentPageZWOrder',$(this).val());
    });
    $(".pagination").click(function(){
//        alert($(".page-total input").val());
        $cookies.put('currentPageZWOrder',$(".page-total input").val());
    });
    $(".page-total select").change(function(){
        var items =$(".page-total select").val();
        var ss = items.substring(7,items.length);
        $cookies.put('itemsPerPageZWOrder',ss);

    })
    var currentPage;
    var itemsPerPage;
    if($cookies.get('currentPageZWOrder')==null||$cookies.get('currentPageZWOrder')==''||$cookies.get('currentPageZWOrder')==undefined){
        currentPage=1;
    }else{
        currentPage=$cookies.get('currentPageZWOrder');
    }
    if($cookies.get('itemsPerPageZWOrder')==null||$cookies.get('itemsPerPageZWOrder')==''||$cookies.get('itemsPerPageZWOrder')==undefined){
        itemsPerPage=15;
    }else{
        itemsPerPage=$cookies.get('itemsPerPageZWOrder');
    }
    $scope.paginationConf = {
        currentPage: currentPage,
        itemsPerPage: itemsPerPage
    };
    $scope.orderList = consumerPublicLoanService.getOrderList();
    /*查询*/

    //用户姓名
    $("#cusName").blur(function(){
        $cookies.put('cusNameZWOrder',$(this).val());
    });
    $scope.cusName=$cookies.get('cusNameZWOrder');

    //用户电话
    $("#telephone").blur(function(){
        $cookies.put('telephoneZWOrder',$(this).val());
    });
    $scope.telephone=$cookies.get('telephoneZWOrder');

    //推送时间
    $("#pushDate").blur(function(){
        $cookies.put('SendPushDateZWOrder',$(this).val());
    });
    $scope.pushDate=$cookies.get('SendPushDateZWOrder');
    $("#pushDate").val($scope.pushDate);

    //推送银行结束时间
    $("#zongwangDate").blur(function(){
        $cookies.put('zongwangDateZWOrder',$(this).val());
    });
    $scope.zongwangDate=$cookies.get('zongwangDateZWOrder');
    $("#zongwangDate").val($scope.zongwangDate);

    //订单放款状态
    $("#loanOrderStatus").blur(function(){
        $cookies.put('loanOrderStatusZWOrder',$(this).val());
    });
    $scope.loanOrderStatus=$cookies.get('loanOrderStatusZWOrder');

    //推荐号
    $("#recommendation").blur(function(){
        $cookies.put('commendNumZWOrder',$(this).val());
    });
    $scope.recommendation=$cookies.get('commendNumZWOrder');
//清空按钮
    $scope.clearSearch=function(){
        $scope.paginationConf.currentPage=1;
        $("#cusName").val('');
        $("#telephone").val('');
        $("#pushDate").val('');
        $("#zongwangDate").val('');
        $("#recommendation").val('');
        $("#loanOrderStatus").val('');
        $scope.cusName=undefined;
        $scope.telephone=undefined;
        $scope.userPhones=undefined;
        $scope.pushDate=undefined;
        $scope.zongwangDate=undefined;
        $scope.loanOrderStatus=undefined;
        $scope.recommendation=undefined;
        $cookies.remove('cusNameZWOrder');
        $cookies.remove('telephoneZWOrder');
        $cookies.remove('SendPushDateZWOrder');
        $cookies.remove('zongwangDateZWOrder');
        $cookies.remove('loanOrderStatusZWOrder');
        $cookies.remove('commendNumZWOrder');
        $cookies.remove('currentPageBankHadSendOrder');
        $cookies.remove('itemsPerPageBankHadSendOrder');
        $scope.onQuery();
    };
    /*查询 start*/
    $scope.onQuery=function () {
        $scope.pushDates=$filter('date')($scope.pushDate,'yyyy-MM-dd');
        $scope.zongwangDates=$filter('date')($scope.zongwangDate,'yyyy-MM-dd');
//        console.log($scope.Date);
        consumerPublicLoanService.queryOrder({
            name:$scope.cusName,
            phone:$scope.telephone,//用户电话
            pushTime:$scope.pushDates,//推送时间
            loanTime:$scope.zongwangDates,//众网放款时间
            orderLoanStatusValue:$scope.loanOrderStatus,//订单放款状态
            recommendedNo:$scope.recommendation,//推荐号
            currentPage: $scope.paginationConf.currentPage,
            itemsPerPage: $scope.paginationConf.itemsPerPage
        }, function(response){
            if(response.totalCount>=1){
                $scope.paginationConf.totalItems = response.totalCount;
            }else{
                $scope.paginationConf.totalItems = 0;
                $scope.orderList.length = 0;
            }
        });
    };
    /*查询 end*/
    /*处理之后保留在当前页面start*/
//    $scope.reload=function(){
//        $scope.paginationConf.currentPage = $(".page-total input").val();
//        $scope.paginationConf.itemsPerPage = $(".page-total select").val();
//        $scope.onQuery();
//    };
    /*处理之后保留在当前页面end*/
    $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.onQuery);
    $scope.onhandle= function($event,order){
        // alert('银行对接：已推送银行成功订单')
        var dataId = $($event.target).parents('tr').data("appid");
        localStorage.setItem('dataPublic',dataId);
        var data =localStorage.getItem('dataPublic');
        $scope.dataPublic=data;
        window.localStorage.setItem("statusName", order.statusName)//订单放款状态
//		alert(data)
//        if(order.loan==3){
//            $state.go('main.consumerToBankOrderDetailSearch',{'hideData':0});
//        }else{
//            $state.go('main.consumerToBankOrderDetailGiveUp',{'hideData':0,'giveUp':1});
//        }
        $state.go('main.consumerToBankOrderDetailSearch',{'hideData':0,'isZwbank':0});
        ToBankOrderDetailService.setCurHandleOrder(order);
    }
    
    
    $scope.detailSearch=function(){
           $state.go('main.consumerToBankOrderDetailSearch',{'hideData':0,"isZwbank":0});  
    }
});