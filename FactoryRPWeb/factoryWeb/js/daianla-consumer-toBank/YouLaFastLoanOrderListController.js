/**
 * Created by jayvenLee on 2017/11/11.
 */
consumerToBankApp.controller('YouLaFastLoanOrderListController',function ($scope,$http,$filter,$cookies, UrlService,$resource, $location, $state, YouLaFastLoanService, ToBankOrderDetailService) {
    var data =localStorage.getItem('dataHadSentToBank')
	$scope.dataHadSentToBank=data

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
        $cookies.put('currentPageYouLaFastOrder',$(this).val());
    });
    $(".pagination").click(function(){
//        alert($(".page-total input").val());
        $cookies.put('currentPageYouLaFastOrder',$(".page-total input").val());
    });
    $(".page-total select").change(function(){
        var items =$(".page-total select").val();
        var ss = items.substring(7,items.length);
        $cookies.put('itemsPerPageYouLaFastOrder',ss);

    })
    var currentPage;
    var itemsPerPage;
    if($cookies.get('currentPageYouLaFastOrder')==null||$cookies.get('currentPageYouLaFastOrder')==''||$cookies.get('currentPageYouLaFastOrder')==undefined){
        currentPage=1;
    }else{
        currentPage=$cookies.get('currentPageYouLaFastOrder');
    }
    if($cookies.get('itemsPerPageYouLaFastOrder')==null||$cookies.get('itemsPerPageYouLaFastOrder')==''||$cookies.get('itemsPerPageYouLaFastOrder')==undefined){
        itemsPerPage=15;
    }else{
        itemsPerPage=$cookies.get('itemsPerPageYouLaFastOrder');
    }
    $scope.paginationConf = {
        currentPage: currentPage,
        itemsPerPage: itemsPerPage
    };
    $scope.orderList = YouLaFastLoanService.getOrderList();
    console.log(321);
    console.log($scope.orderList);
    /*查询*/
    
    //用户姓名
    $("#cusName").blur(function(){
        $cookies.put('cusNameYouLaFastOrder',$(this).val());
    });
    $scope.cusName=$cookies.get('cusNameYouLaFastOrder');

    //用户电话
    $("#telephone").blur(function(){
        $cookies.put('telephoneYouLaFastOrder',$(this).val());
    });
    $scope.telephone=$cookies.get('telephoneYouLaFastOrder');

    //推送银行开始时间
    $("#SendStartDate").blur(function(){
        $cookies.put('SendStartDateYouLaFastOrder',$(this).val());
    });
    $scope.SendStartDate=$cookies.get('SendStartDateYouLaFastOrder');
    $("#SendStartDate").val($scope.SendStartDate);

    //推送银行结束时间
    $("#SendEndDate").blur(function(){
        $cookies.put('SendEndDateYouLaFastOrder',$(this).val());
    });
    $scope.SendEndDate=$cookies.get('SendEndDateYouLaFastOrder');
    $("#SendEndDate").val($scope.SendEndDate);


    //放款开始时间
    $("#loanStartDate").blur(function(){
        $cookies.put('loanStartDateYouLaFastOrder',$(this).val());
    });
    $scope.loanStartDate=$cookies.get('loanStartDateYouLaFastOrder');
    $("#loanStartDate").val($scope.loanStartDate);

    //放款结束时间
    $("#loanEndDate").blur(function(){
        $cookies.put('loanEndDateYouLaFastOrder',$(this).val());
    });
    $scope.loanEndDate=$cookies.get('loanEndDateYouLaFastOrder');
    $("#loanEndDate").val($scope.loanEndDate);

    //放款状态
    $("#loanStatus").blur(function(){
        $cookies.put('loanStatusYouLaFastOrder',$(this).val());
    });
    $scope.loanStatus=$cookies.get('loanStatusYouLaFastOrder');

//清空按钮
    $scope.clearSearch=function(){
        $scope.paginationConf.currentPage=1;
        $("#cusName").val('');
        $("#telephone").val('');
        $("#SendStartDate").val('');
        $("#SendEndDate").val('');
        $("#sendBankStatus").val('');
        $("#loanStartDate").val('');
        $("#loanEndDate").val('');
        $("#loanStatus").val('');
        $scope.cusName=null;
        $scope.telephone=null;
        $scope.userPhones=null;
        $scope.SendStartDate=null;
        $scope.SendEndDate=null;
        $scope.loanStartDate=null;
        $scope.loanEndDate=null;
        $scope.loanStatus=null;
        $cookies.remove('cusNameYouLaFastOrder');
        $cookies.remove('telephoneYouLaFastOrder');
        $cookies.remove('SendStartDateYouLaFastOrder');
        $cookies.remove('SendEndDateYouLaFastOrder');
        $cookies.remove('loanStartDateYouLaFastOrder');
        $cookies.remove('loanEndDateYouLaFastOrder');
        $cookies.remove('loanStatusYouLaFastOrder');
        $cookies.remove('currentPageYouLaFastOrder');
        $cookies.remove('itemsPerPageYouLaFastOrder');
        $scope.onQuery();
    };
    /*查询 start*/
    $scope.onQuery=function () {
        $scope.SendStartDates=$filter('date')($scope.SendStartDate,'yyyy-MM-dd');
        $scope.SendEndDates=$filter('date')($scope.SendEndDate,'yyyy-MM-dd');
        $scope.loanEStartDates=$filter('date')($scope.loanStartDate,'yyyy-MM-dd');
        $scope.loanEndDates=$filter('date')($scope.loanEndDate,'yyyy-MM-dd');
        YouLaFastLoanService.queryOrder({
            userName:$scope.cusName,
            userPhone:$scope.telephone,//用户电话
            createTimeStart:$scope.SendStartDates,//推送开始时间
            createTimeEnd:$scope.SendEndDates,//推送结束时间
            loanTimeStart:$scope.loanEStartDates,//贷款开始时间
            loanTimeEnd:$scope.loanEndDates,//贷款结束时间
            loanStatus:$scope.loanStatus,//放款状态
            currentPage: $scope.paginationConf.currentPage,
            itemsPerPage: $scope.paginationConf.itemsPerPage
//            orderStatus:4//订单状态（2表示待处理、4表示已通过、5表示已拒绝）
        }, function(response){
            if(response.status==0&&response.obj.list!=null){
                $scope.orderList=response.obj.list;
                $scope.paginationConf.totalItems = response.obj.totalCount;
            }else{
                $scope.paginationConf.totalItems = 0;
                $scope.orderList.length = 0;
            }
        });
    };
    /*查询 end*/

    /*产品列表 start*/
    $scope.productName=function(){
        $scope.productNames = YouLaFastLoanService.productList(
            function(data){
                if(data.status==0){
                    if(data.obj!=''&&data.obj!=null&&data.obj!=undefined){
                        //TODO-业务逻辑
                        $scope.productNameList = data.obj;
                        console.log(data.obj);
                    }else{
                        $scope.productNameList=[];
                    }
                }else{
//
                    console.log(data.message);
                }
            }, function(err){
                console.log(err)
            });
    };
    $scope.productName();
    /*产品列表 end*/

    /*根据登录人 查询角色 start*/
//    $scope.Employees= YouLaFastLoanService.queryEmployees('',  function(data){
//        if(data.status==0){
//            if(data.obj!=null){
//                //TODO-业务逻辑
//                $scope.Employes = data.obj;
////                console.log($scope.Employes);
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
    /*根据登录人 查询角色 end*/
    /*转交列表 start*/
    $scope.queryTransfers=function(){
        $scope.Transfer = YouLaFastLoanService.deliverList(true,
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
        YouLaFastLoanService.queryFlagBz({
            id:$scope.curHandlerOrder.orderId,
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
    /***************************************************************
     当页码和页面记录数发生变化时监控后台查询
     如果把currentPage和itemsPerPage分开监控的话则会触发两次后台事件。
     ***************************************************************/
    /*******手动代扣功能 start ********/
    $scope.articalWithholdFuc=function(orderListDetail){

        $scope.withHoldMon =orderListDetail.cutfee/100;
        popupDiv('articalwithhold');
        $("#users").html(orderListDetail.userName);

            $scope.WithholdFucSure=function(){
                if($scope.withHoldMon==''||$scope.withHoldMon==null||$scope.withHoldMon==undefined||$scope.withHoldMon==0){
                    $("#withHoldMoney").focus();
                }else{
                var pa={
                    fastOrderId:orderListDetail.id,
                    money:$scope.withHoldMon
                };
                console.log(pa);
                var par={params:pa};
                $http.get($scope.WebURL+'fastLoanController/cutMoneyByEmployee',par).success(function(response){
                    if(response.status==0){
                        hideDiv('articalwithhold');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(response.message);
                    }else{
                        hideDiv('articalwithhold');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(response.message);
                    }
                }).error(function(response){
                    console.log(response.msg);
                })
            }
        }

    };
    /*******手动代扣功能 end ********/
    /************/
    $scope.fkoperators= YouLaFastLoanService.queryEmployees({
        'empType':'2'//1是销售 2是风控
    },function(data){
        if(data.status==0){
            if(data.obj!=null){
                //TODO-业务逻辑
//                $scope.Employes = data.obj;
                $scope.fkoperators = data.obj;
                console.log($scope.fkoperators);
            }else{
                console.log(data.message);
                $scope.fkoperators=[];
            }
        }else{
//            alert(data.message);
        }
    }, function(err){
        console.log(err)
    });
    /************/
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
        popupDiv('YouLaLoanAuditDetail');
        $scope.userName=order.userName;
        $scope.userPhone=order.userPhone;
        $scope.userIdcardNo=order.userIdcardNo;
        $scope.userBankNo=order.userBankNo;
        $scope.viewLoanAccounts= YouLaFastLoanService.viewLoanAccount({
            'fastOrderId':order.id//
        },function(data){
            if(data.status==0){
                if(data.obj!=null){
                    //TODO-业务逻辑
//                $scope.Employes = data.obj;
                    $scope.fastOrderAccount = data.obj.fastOrderAccount;
                    $scope.payDetails = data.obj.payDetails;
                    console.log($scope.fastOrderAccount);
                    console.log($scope.payDetails);
                }else{
                    console.log(data.message);
                    $scope.LoanAccounts=[];
                }
            }else{
//            alert(data.message);
            }
        }, function(err){
            console.log(err)
        });
    }
});