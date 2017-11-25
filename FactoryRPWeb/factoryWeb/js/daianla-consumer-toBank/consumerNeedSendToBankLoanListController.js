/**
 * Created by caolongping on 2016/5/18.
 */
consumerToBankApp.controller('needSendToBankLoanOrderListController',function ($scope,$http,$filter,$cookies, UrlService,UpgradeAllDealWithService,$resource, $location, $state, needSendToBankLoanListService, ToBankLoanOrderDetailService) {
    var data =localStorage.getItem('dataNeedSendToBank')
	$scope.dataNeedSendToBank=data
    $scope.WebURL=UrlService.getUrl('xfdbaodan');
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
    /*******反欺诈报告 start****/
    $scope.moreInformation=false;
    $scope.morePack=function () {
        $scope.moreInformation = !$scope.moreInformation;
    }
    /*******反欺诈报告 end****/

//    $scope.WebURL='http://192.168.6.34:8085/consumption-audit-web/web/';
        //配置分页基本参数
    $(".page-total input").blur(function(){
//        alert($(this).val());
        $cookies.put('currentPageNeedSend',$(this).val());
    });
    $(".pagination").click(function(){
//        alert($(".page-total input").val());
        $cookies.put('currentPageNeedSend',$(".page-total input").val());
    });
    $(".page-total select").change(function(){
        var items =$(".page-total select").val();
        var ss = items.substring(7,items.length);
        $cookies.put('itemsPerPageNeedLoanSend',ss);

    })
    var currentPage;
    var itemsPerPage;
    if($cookies.get('currentPageNeedSend')==null||$cookies.get('currentPageNeedSend')==''||$cookies.get('currentPageNeedSend')==undefined){
        currentPage=1;
    }else{
        currentPage=$cookies.get('currentPageNeedSend');
    }
    if($cookies.get('itemsPerPageNeedLoanSend')==null||$cookies.get('itemsPerPageNeedLoanSend')==''||$cookies.get('itemsPerPageNeedLoanSend')==undefined){
        itemsPerPage=15;
    }else{
        itemsPerPage=$cookies.get('itemsPerPageNeedLoanSend');
    }
    $scope.paginationConf = {
        currentPage: currentPage,
        itemsPerPage: itemsPerPage
    };
    $scope.orderList1 = needSendToBankLoanListService.getOrderList();
    console.log($scope.orderList1)
    /*查询*/
    //用户姓名
    $("#cusName").blur(function(){
        $cookies.put('cusNameNeedLoanSend',$(this).val());
    });
    $scope.cusName=$cookies.get('cusNameNeedLoanSend');
    //用户电话
    $("#telephone").blur(function(){
        $cookies.put('telephoneNeedLoanSend',$(this).val());
    });
    $scope.telephone=$cookies.get('telephoneNeedLoanSend');
    //用户身份证
    $("#idCard").blur(function(){
        $cookies.put('idCardNeedLoanSend',$(this).val());
    });
    $scope.idCard=$cookies.get('idCardNeedLoanSend');
    //批量电话
    $("#userPhones").blur(function(){
        $("#userPhones").blur(function(){
            if($(this).val()!=''){
                $cookies.put('userPhonesNeedLoanSend',$(this).val().match(/\d+/g).join(' '));
            }else{
                $cookies.put('userPhonesNeedLoanSend',$(this).val());
            }
        });
    });
    $scope.userPhones=$cookies.get('userPhonesNeedLoanSend');
    //推荐号
//    $("#commendId").blur(function(){
//        $cookies.put('commendIdNeedSend',$(this).val());
//    });
//    $scope.commendId=$cookies.get('commendIdNeedSend');

    //开始时间
    $("#SendStartDate").blur(function(){
        $cookies.put('SendStartDateBankNeedLoanSend',$(this).val());
    });
    $scope.SendStartDate=$cookies.get('SendStartDateBankNeedLoanSend');
    $("#SendStartDate").val($scope.SendStartDate);

    //结束时间
    $("#SendEndDate").blur(function(){
        $cookies.put('SendEndDateBankNeedLoanSend',$(this).val());
    });
    $scope.SendEndDate=$cookies.get('SendEndDateBankNeedLoanSend');
    $("#SendEndDate").val($scope.SendEndDate);
    //产品名称
    $("#productName").blur(function(){
        $cookies.put('productNameNeedLoanSend',$(this).val());
    });
    $scope.productName=$cookies.get('productNameNeedLoanSend');
    //常住地址
    $("#permanentAddress").blur(function(){
        $cookies.put('permanentAddressNeedSend',$(this).val());
    });
    $scope.permanentAddress=$cookies.get('permanentAddressNeedSend');

    $scope.clearSearch=function(){
        currentPage: $scope.paginationConf.currentPage,
        $("#cusName").val('');
        $("#telephone").val('');
        $("#idCard").val('');
        $("#userPhones").val('');
        $("#commendId").val('');
        $("#SendStartDate").val('');
        $("#SendEndDate").val('');
        $("#productName").val('');
        $("#permanentAddress").val('');
        $scope.cusName=null;
        $scope.Date=null;
        $scope.createdDate=null;
        $scope.telephone=null;
        $scope.userPhones=null;
        $scope.Allphones=null;
        $scope.SendStartDate=undefined;
        $scope.SendEndDate=undefined;
//        $scope.commendId=null;
        $scope.productName=null;
        $scope.permanentAddress=null;
        $scope.idCard=null;
        $cookies.remove('cusNameNeedLoanSend');
        $cookies.remove('telephoneNeedLoanSend');
        $cookies.remove('idCardNeedLoanSend');
        $cookies.remove('userPhonesNeedLoanSend');
//        $cookies.remove('commendIdNeedSend');
        $cookies.remove('SendStartDateBankNeedLoanSend');
        $cookies.remove('SendEndDateBankNeedLoanSend');
        $cookies.remove('productNameNeedLoanSend');
        $cookies.remove('permanentAddressNeedLoanSend');
        $cookies.remove('currentPage');
        $cookies.remove('itemsPerPageNeedLoanSend');
        $scope.onQuery();
    };

    $scope.onQuery=function(){
        $scope.SendStartDates=$filter('date')($scope.SendStartDate,'yyyy-MM-dd');
        $scope.SendEndDates=$filter('date')($scope.SendEndDate,'yyyy-MM-dd');
//        console.log($scope.Date);
        needSendToBankLoanListService.queryOrder({
//            isAuditManager:1,//区分是审批
//            type:5,//1:待认证,2:待审核,3:已通过,4:已拒绝,0:全部订单;5:待推送银行
            userName:$scope.cusName,//用户名
            userPhone:$scope.telephone,//用户电话
            idCardNo:$scope.idCard,//用户身份证号码
//            recommendId:$scope.commendId,//推荐号
            startTime:$scope.SendStartDates,//推送开始时间
            endTime:$scope.SendEndDates,//推送结束时间
            type:$scope.productName,//产品类型
//            location:$scope.permanentAddress,//归属地
            currentPage: $scope.paginationConf.currentPage,
            itemsPerPage: $scope.paginationConf.itemsPerPage
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
    /*转交列表 start*/
    $scope.queryTransfers=function(){
        $scope.Transfer = needSendToBankLoanListService.deliverList(true,
            function(data){
                if(data.status==0){
                    if(data.obj!=''&&data.obj!=null&&data.obj!=undefined){
                        //TODO-业务逻辑
                        $scope.transfers = data.obj;
                        console.log(data.obj);
                    }else{
                        $scope.transfers=[];
                    }
                }else{
//                    popupDiv('SaveSuccess');
//                    $(".SaveSuccess .Message").html(data.msg);
                    console.log(data.msg);
                }
            }, function(err){
                console.log(err)
            });
    };
    $scope.queryTransfers();
    /*转交列表 end*/
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
    /*******单击红旗按钮 start*********/
    $scope.onFlagEdit= function(order){
        popupDiv('redFlag');
        $scope.editMarkValue = order.remarkFlag;
        $scope.editMarkContent= order.remark;
        $scope.curHandlerOrder  = order;
    };
    $scope.onFlagSelected=function(markValues){
        $scope.editMarkValue = markValues;
    };
    /*******单击红旗按钮 end*********/
    /*******备注红旗定义 start*********/
    $scope.onBzContent = function () {
        needSendToBankLoanListService.queryFlagBz({
            id:$scope.curHandlerOrder.id,
            userId:$scope.curHandlerOrder.userId,
            markValue :$scope.editMarkValue,
            handleOpinion :$scope.editMarkContent
        }, function(response){
            $scope.curHandlerOrder.remarkFlag = $scope.editMarkValue;
            $scope.curHandlerOrder.remark = $scope.editMarkContent;
            hideDiv('redFlag');
            popupDiv('SaveSuccess');
            $(".Message").html(response.message);
        });
    };
    /*******备注红旗定义 end*********/
    /*根据登录人 查询部门 start*/
//    var dept=[];
//    $scope.depNames= UpgradeAllDealWithService.queryDepName({
//        name:$scope.userNick
//    }, function(data){
//        if(data.status==0){
//            if(data.obj.employeeVos!=null){
//                //TODO-业务逻辑
//                data.obj.employeeVos.forEach(function(item){
//                    console.log(item);
//                    dept.push(item.depName);
//                });
//                console.log(data.obj.employeeVos);
//                if(dept.join().indexOf('风控')!=-1){
//                    $scope.dealStyle='处理';
//                    $scope.searchButton=true;
//                }else if(dept.join().indexOf('销售')!=-1){
//                    $scope.dealStyle='查看';
//                    $scope.searchButton=false;
//                }else{
//                    $scope.dealStyle='查看';
//                    $scope.searchButton=2;
//                }
//            }else{
//                console.log(data.message);
//                $scope.Employes=[];
//            }
//        }else{
////            alert(data.message);
//        }
//    }, function(err){
//        console.log(err)
//    });
//    console.log(dept);

    /*根据登录人 查询部门 end*/
    /***************************************************************
     当页码和页面记录数发生变化时监控后台查询
     如果把currentPage和itemsPerPage分开监控的话则会触发两次后台事件。
     ***************************************************************/
    /*处理之后保留在当前页面start*/
//    $scope.reload=function(){
//        $scope.paginationConf.currentPage = $(".page-total input").val();
//        $scope.paginationConf.itemsPerPage = $(".page-total select").val();
//        $scope.onQuery();
//    };
    /*处理之后保留在当前页面end*/
    $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.onQuery);
    $scope.onhandle= function($event,order){
        // alert('银行对接，待推送银行订单')
        var dataId = $($event.target).parents('tr').data("appid")
		localStorage.setItem('dataNeedSendToBank',dataId)
		var data =localStorage.getItem('dataNeedSendToBank')
		$scope.dataNeedSendToBank=data
//		alert(data)

        $state.go('main.consumerToBankLoanOrderDetail',{'Need':1});
        ToBankLoanOrderDetailService.setCurHandleOrder(order);
    }
});