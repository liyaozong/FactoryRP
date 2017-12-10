/**
 * Created by jayvenLee on 2017/11/11.
 */
consumerToBankApp.controller('consumerYoungToBankListController',function ($scope,$http,$filter,$cookies, UrlService,$resource, $location, $state, consumerYoungToBankListService, ToBankOrderDetailService) {
    var data =localStorage.getItem('dataYoungLoan')
	$scope.dataYoungLoan=data
    
    $scope.WebURL=UrlService.getUrl('xfdbaodan');
    $scope.orderList = consumerYoungToBankListService.getOrderList();
    /*********更多 start******/
    $scope.moreInformation=false;
    $scope.morePack=function () {
        $scope.moreInformation = !$scope.moreInformation;
        if($scope.moreInformation){
            $scope.isActive = true;
            $(".moreChoose").html("收起<i></i>");

        }else{
            $scope.isActive = false;
            $(".moreChoose").html("更多选项<i></i>");

        }
    }

    /*********更多 end******/



        //配置分页基本参数
    $(".page-total input").blur(function(){
//        alert($(this).val());
        $cookies.put('currentPageYoungTobankOrder',$(this).val());
    });
    $(".pagination").click(function(){
//        alert($(".page-total input").val());
        $cookies.put('currentPageYoungTobankOrder',$(".page-total input").val());
    });
    $(".page-total select").change(function(){
        var items =$(".page-total select").val();
        var ss = items.substring(7,items.length);
        $cookies.put('itemsPerPageYoungTobankOrder',ss);

    })
    var currentPage;
    var itemsPerPage;
    if($cookies.get('currentPageYoungTobankOrder')==null||$cookies.get('currentPageYoungTobankOrder')==''||$cookies.get('currentPageYoungTobankOrder')==undefined){
        currentPage=1;
    }else{
        currentPage=$cookies.get('currentPageYoungTobankOrder');
    }
    if($cookies.get('itemsPerPageYoungTobankOrder')==null||$cookies.get('itemsPerPageYoungTobankOrder')==''||$cookies.get('itemsPerPageYoungTobankOrder')==undefined){
        itemsPerPage=15;
    }else{
        itemsPerPage=$cookies.get('itemsPerPageYoungTobankOrder');
    }
    $scope.paginationConf = {
        currentPage: currentPage,
        itemsPerPage: itemsPerPage
    };

    /*查询*/
    
    //用户姓名
    $("#cusName").blur(function(){
        $cookies.put('cusNameYoungTobankOrder',$(this).val());
    });
    $scope.cusName=$cookies.get('cusNameYoungTobankOrder');

    //用户电话
    $("#telephone").blur(function(){
        $cookies.put('telephoneYoungTobankOrder',$(this).val());
    });
    $scope.telephone=$cookies.get('telephoneYoungTobankOrder');
    
    //用户电话
    $("#userPhones").blur(function(){
        if($(this).val()!=''){
            $cookies.put('userPhonesYoungTobankOrder',$(this).val().match(/\d+/g).join(' '));
        }else{
            $cookies.put('userPhonesYoungTobankOrder',$(this).val());
        }
    });
    $scope.userPhones=$cookies.get('userPhonesYoungTobankOrder');

    //推送银行开始时间
    $("#SendStartDate").blur(function(){
        $cookies.put('SendStartDateYoungTobankOrder',$(this).val());
    });
    $scope.SendStartDate=$cookies.get('SendStartDateYoungTobankOrder');
    $("#SendStartDate").val($scope.SendStartDate);

    //推送银行结束时间
    $("#SendEndDate").blur(function(){
        $cookies.put('SendEndDateYoungTobankOrder',$(this).val());
    });
    $scope.SendEndDate=$cookies.get('SendEndDateYoungTobankOrder');
    $("#SendEndDate").val($scope.SendEndDate);



    //银行放款状态
    $("#bankPayStatus").blur(function(){
        $cookies.put('bankPayStatusHadSendOrder',$(this).val());
    });
    $scope.bankPayStatus=$cookies.get('bankPayStatusHadSendOrder');

    //设备类型
    $("#deviceType").blur(function(){
        $cookies.put('deviceTypeYoungOrder',$(this).val());
    });
    $scope.deviceType=$cookies.get('deviceTypeYoungOrder');

    $scope.clearSearch=function(){
        $scope.paginationConf.currentPage=1;
        $("#cusName").val('');
        $("#telephone").val('');
        $("#SendStartDate").val('');
        $("#SendEndDate").val('');
//        $("#sendBankStatus").val('');
        $("#bankPayStatus").val('');
        $("#helpPayStatus").val('');
        $("#deviceType").val('');
        $scope.cusName=null;
        $scope.telephone=null;
        $scope.userPhones=null;
        $scope.SendStartDate=null;
        $scope.SendEndDate=null;
        $scope.sendBankStatus=null;
        $scope.bankPayStatus=null;
        $scope.deviceType=null;
        $cookies.remove('cusNameYoungTobankOrder');
        $cookies.remove('telephoneYoungTobankOrder');
        $cookies.remove('userPhonesYoungTobankOrder');
        $cookies.remove('SendStartDateYoungTobankOrder');
        $cookies.remove('SendEndDateYoungTobankOrder');
//        $cookies.remove('sendBankStatusHadSendOrder');
        $cookies.remove('bankPayStatusHadSendOrder');

        $cookies.remove('currentPageYoungTobankOrder');
        $cookies.remove('itemsPerPageYoungTobankOrder');
        $cookies.remove('deviceTypeYoungOrder');
        $scope.onQuery();
    };

    $scope.onQuery=function () {
        $scope.SendStartDates=$filter('date')($scope.SendStartDate,'yyyy-MM-dd');
        $scope.SendEndDates=$filter('date')($scope.SendEndDate,'yyyy-MM-dd');
//        console.log($scope.Date);
        $scope.userPhones=$cookies.get('userPhonesYoungTobankOrder');
        $scope.telephone=$cookies.get('telephoneYoungTobankOrder');
        if($scope.telephone==''||$scope.telephone==null||$scope.telephone==undefined){
            $scope.telephone='';
            $scope.Allphones=$scope.telephone+' '+$scope.userPhones;
        }else if($scope.userPhones==''||$scope.userPhones==null||$scope.userPhones==undefined){
            $scope.userPhones='';
            $scope.Allphones=$scope.telephone+' '+$scope.userPhones;
        }else{
            $scope.Allphones=$scope.telephone+' '+$scope.userPhones;
        }
        console.log($scope.telephone);
        console.log($scope.userPhones);
        console.log($scope.Allphones);
        if($scope.Allphones==' '||$scope.Allphones==' undefined'||$scope.Allphones==undefined||$scope.Allphones=='undefined '){
            $scope.Allphones='';
        }
        consumerYoungToBankListService.queryOrder({
            userName:$scope.cusName,
            userPhone:$scope.Allphones,//用户电话
            startTime:$scope.SendStartDates,//推送开始时间
            endTime:$scope.SendEndDates,//推送结束时间
            status:$scope.bankPayStatus,//0：已提交；-1：提交失败；-2：跳过
            deviceType:$scope.deviceType,
            currentPage: $scope.paginationConf.currentPage,
            itemsPerPage: $scope.paginationConf.itemsPerPage
//            orderStatus:4//订单状态（2表示待处理、4表示已通过、5表示已拒绝）
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



    /***************************************************************
     当页码和页面记录数发生变化时监控后台查询
     如果把currentPage和itemsPerPage分开监控的话则会触发两次后台事件。
     ***************************************************************/


    /*处理之后保留在当前页面start*/
    $scope.reload=function(){
        $scope.paginationConf.currentPage = $(".page-total input").val();
        $scope.paginationConf.itemsPerPage = $(".page-total select").val();
        $scope.onQuery();
    };
    /*处理之后保留在当前页面end*/
    $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.onQuery);
    $scope.onhandle= function($event,order){
        // alert('银行对接：已推送青春贷订单')
        var dataId = $($event.target).parents('tr').data("appid")
		localStorage.setItem('dataYoungLoan',dataId)
		var data =localStorage.getItem('dataYoungLoan')
		$scope.dataYoungLoan=data
//		alert(data)
        $state.go('main.consumerToBankYoungOrderDetail');
        ToBankOrderDetailService.setCurHandleOrder(order);
    }
});