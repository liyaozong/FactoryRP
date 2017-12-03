/**
 * Created by caolongping on 2016/5/18.
 */
consumerToBankApp.controller('ToBankHadSubmitListController',function ($scope,UrlService,$http,$filter,$cookies, UpgradeAllDealWithService,$resource, $location, $state, ToBankHadSubmitListService, ToBankOrderDetailService) {
    $scope.WebURL=UrlService.getUrl('consumerNormal');
//    $scope.WebURL='http://192.168.6.34:8085/consumption-audit-web/web/';
    //配置分页基本参数
    $(".page-total input").blur(function(){
//        alert($(this).val());
        $cookies.put('currentPageAllOrder',$(this).val());
    });
    $(".pagination").click(function(){
//        alert($(".page-total input").val());
        $cookies.put('currentPageAllOrder',$(".page-total input").val());
    });
    $(".page-total select").change(function(){
        var items =$(".page-total select").val();
        var ss = items.substring(7,items.length);
        $cookies.put('itemsPerPageBankHadSubmit',ss);

    })
    var currentPage;
    var itemsPerPage;
    if($cookies.get('currentPageAllOrder')==null||$cookies.get('currentPageAllOrder')==''||$cookies.get('currentPageAllOrder')==undefined){
        currentPage=1;
    }else{
        currentPage=$cookies.get('currentPageAllOrder');
    }
    if($cookies.get('itemsPerPageBankHadSubmit')==null||$cookies.get('itemsPerPageBankHadSubmit')==''||$cookies.get('itemsPerPageBankHadSubmit')==undefined){
        itemsPerPage=15;
    }else{
        itemsPerPage=$cookies.get('itemsPerPageBankHadSubmit');
    }
    $scope.paginationConf = {
        currentPage: currentPage,
        itemsPerPage: itemsPerPage
    };
    $scope.orderList = ToBankHadSubmitListService.getOrderList();

    /*查询*/
    $("#cusName").blur(function(){
        $cookies.put('cusNameAllOrder',$(this).val());
    });
    $scope.cusName=$cookies.get('cusNameAllOrder');

    $("#stMoney").blur(function(){
        $cookies.put('stMoneyAllOrder',$(this).val());
    });
    $scope.stMoney=$cookies.get('stMoneyAllOrder');

    $("#endMoney").blur(function(){
        $cookies.put('endMoneyAllOrder',$(this).val());
    });
    $scope.endMoney=$cookies.get('endMoneyAllOrder');

    $("#createdDate").blur(function(){
        $cookies.put('createdDateAllOrder',$(this).val());
    });
    $scope.createdDate=$cookies.get('createdDateAllOrder');
    $("#createdDate").val($scope.createdDate);

    $("#place").blur(function(){
        $cookies.put('placeAllOrder',$(this).val());
    });
    $scope.place=$cookies.get('placeAllOrder');

    $("#busSmoney").blur(function(){
        $cookies.put('busSmoneyAllOrder',$(this).val());
    });
    $scope.busSmoney=$cookies.get('busSmoneyAllOrder');

    $("#bubSmoney").blur(function(){
        $cookies.put('bubSmoneyAllOrder',$(this).val());
    });
    $scope.bubSmoney=$cookies.get('bubSmoneyAllOrder');

    $("#telephone").blur(function(){
        $cookies.put('telephoneAllOrder',$(this).val());
    });
    $scope.telephone=$cookies.get('telephoneAllOrder');

    $("#orderStatus").blur(function(){
        $cookies.put('orderStatus',$(this).val());
    });
    $scope.orderStatus=$cookies.get('orderStatus');

    $("#operatorCode").blur(function(){
        $cookies.put('operatorCode',$(this).val().split(':')[1]);
    });
    $scope.operatorCode=$cookies.get('operatorCode');

    $("#commendId").blur(function(){
        $cookies.put('commendId',$(this).val());
    });
    $scope.commendId=$cookies.get('commendId');

    $scope.clearSearch=function(){
        $scope.paginationConf.currentPage=1;
        $("#cusName").val('');
        $("#stMoney").val('');
        $("#endMoney").val('');
        $("#createdDate").val('');
        $("#place").val('');
        $("#busSmoney").val('');
        $("#bubSmoney").val('');
        $("#telephone").val('');
        $("#orderStatus").val('');
        $("#operatorCode").val('');
        $("#commendId").val('');
        $scope.cusName=null;
        $scope.Date=null;
        $scope.createdDate=null;
        $scope.stMoney=null;
        $scope.endMoney=null;
        $scope.telephone=null;
        $scope.place=null;
        $scope.busSmoney=null;
        $scope.bubSmoney=null;
        $scope.orderStatus=null;
        $scope.operatorCode=null;
        $scope.commendId=null;
        $cookies.remove('cusNameAllOrder');
        $cookies.remove('stMoneyAllOrder');
        $cookies.remove('endMoneyAllOrder');
        $cookies.remove('createdDateAllOrder');
        $cookies.remove('placeAllOrder');
        $cookies.remove('busSmoneyAllOrder');
        $cookies.remove('bubSmoneyAllOrder');
        $cookies.remove('telephoneAllOrder');
        $cookies.remove('orderStatus');
        $cookies.remove('operatorCode');
        $cookies.remove('currentPageAllOrder');
        $cookies.remove('commendId');
        $cookies.remove('itemsPerPageBankHadSubmit');
        ToBankHadSubmitListService.queryOrder({
            currentPage: $scope.paginationConf.currentPage,
            itemsPerPage: $scope.paginationConf.itemsPerPage
//            orderStatus:4//订单状态（2表示待处理、4表示已通过、5表示已拒绝）
        }, function(response){
            if(response.totalCount>=1){
                $scope.paginationConf.totalItems = response.totalCount;
            }else{
                $scope.paginationConf.totalItems = 0;
                $scope.orderList.length = 0;
            }
        });
    };
    $scope.onQuery=function () {
        $scope.Date=$filter('date')($scope.createdDate,'yyyy-MM-dd');
//        console.log($scope.Date);
        if($scope.commendId==''||$scope.commendId==undefined){
            $scope.commendId=null;
        }
        ToBankHadSubmitListService.queryOrder({
            userName:$scope.cusName,
            applyTime:$scope.Date,
            applyMoneyLow:$scope.stMoney,//申请金额下限（可选）
            applyMoneyHigh:$scope.endMoney,//申请金额上限
            userPhone:$scope.telephone,//用户电话
            addr:$scope.place,//归属地
            personIncomeLow:$scope.busSmoney,//个人工资收入下限
            personIncomeHigh:$scope.bubSmoney,//个人工资上限
            orderStatus:$scope.orderStatus,//订单状态
            operatorCode:$scope.operatorCode,//归属经理
            commendId:$scope.commendId,//推荐号
            currentPage: $scope.paginationConf.currentPage,
            itemsPerPage: $scope.paginationConf.itemsPerPage
//            orderStatus:4//订单状态（2表示待处理、4表示已通过、5表示已拒绝）
        }, function(response){
            if(response.totalCount>=1){
                $scope.paginationConf.totalItems = response.totalCount;
            }else{
                $scope.paginationConf.totalItems = 0;
                $scope.orderList.length = 0;
            }
        });
    };
    /*根据登录人 查询角色 start*/
    $scope.Employees= ToBankHadSubmitListService.queryEmployees('',  function(data){
        if(data.status==0){
            if(data.obj!=null){
                //TODO-业务逻辑
                $scope.Employes = data.obj;
//                console.log($scope.Employes);
            }else{
                console.log(data.message);
                $scope.Employes=[];
            }
        }else{
//            alert(data.message);
        }
    }, function(err){
        console.log(err)
    });
    /*根据登录人 查询角色 end*/
    /*****统一分配订单 start*****/;
    $scope.outPostAll=function(){
        $scope.operatorCodes=$("#disSaler1 input[type='radio']:checked").val();
        $scope.operatorNames=$("#disSaler1 input[type='radio']:checked").parent().next().html();
        var orders = [];
        $('input[name="che"]:checked').each(function(){
            var sfruit=$(this).val();
            orders.push(sfruit);
        });
        console.log(orders);
        var pa={
            operatorCode:$scope.operatorCodes,
            operatorName:$scope.operatorNames,
            orderId:orders
        };
        var par={params:pa};
        console.log(par);
        if($("#apply_infos_table input[type='checkbox']:checked").size()>=1){
//            $http.get($scope.WebURL+'loan/allocationOrder/'+orders,par).success(function(response){
//                hideDiv('statisticWhole');
//                popupDiv('SaveSuccess');
//                $(".Message").html(response.message);
//            }).error(function(response){
//                alert(response.message);
//            })
            popupDiv('SaveSuccess');
            $(".Message").html('功能尚未开发');
        }else if($("#apply_infos_table input[type='checkbox']:checked").size()<1){
            alert("请选择需要批量导出的名单!");
            hideDiv('statisticWhole');
        }
    };
    /******统一分配订单 end****/
    /*******单击红旗按钮 start*********/
    $scope.onFlagEdit= function(order){
        popupDiv('redFlag');
        $scope.editMarkValue = order.markValue;
        $scope.editMarkContent= order.mark;
        $scope.curHandlerOrder  = order;
    };
    $scope.onFlagSelected=function(markValue){
        $scope.editMarkValue = markValue;
    };
    /*******单击红旗按钮 end*********/
    /*******备注红旗定义 start*********/
    $scope.onBzContent = function () {
        ToBankHadSubmitListService.queryFlagBz({
            id:$scope.curHandlerOrder.id,
            userId:$scope.curHandlerOrder.userId,
            markValue :$scope.editMarkValue,
            handleOpinion :$scope.editMarkContent
        }, function(response){
            $scope.curHandlerOrder.markValue = $scope.editMarkValue;
            $scope.curHandlerOrder.handleOpinion = $scope.editMarkContent;
            hideDiv('redFlag');
            popupDiv('SaveSuccess');
            $(".Message").html(response.msg);
        });
    };
    /*******备注红旗定义 end*********/
    /*根据登录人 查询部门 start*/
    var dept=[];
    $scope.depNames= UpgradeAllDealWithService.queryDepName({
        name:$scope.userNick
    }, function(data){
        if(data.status==0){
            if(data.obj.employeeVos!=null){
                //TODO-业务逻辑
                data.obj.employeeVos.forEach(function(item){
                    console.log(item);
                    dept.push(item.depName);
                });
                console.log(data.obj.employeeVos);
                if(dept.join().indexOf('风控')!=-1){
                    $scope.dealStyle='处理';
                    $scope.searchButton=true;
                }else if(dept.join().indexOf('销售')!=-1){
                    $scope.dealStyle='查看';
                    $scope.searchButton=false;
                }else{
                    $scope.dealStyle='查看';
                    $scope.searchButton=2;
                }
            }else{
                console.log(data.message);
                $scope.Employes=[];
            }
        }else{
//            alert(data.message);
        }
    }, function(err){
        console.log(err)
    });
    console.log(dept);

    /*根据登录人 查询部门 end*/
    /***************************************************************
     当页码和页面记录数发生变化时监控后台查询
     如果把currentPage和itemsPerPage分开监控的话则会触发两次后台事件。
     ***************************************************************/
    /*处理之后保留在当前页面start*/
//    $scope.reload=function(){
//        $scope.paginationConf.currentPage = $(".page-total input").val();
//        $scope.onQuery();
//    };
    /*处理之后保留在当前页面end*/
    /*处理之后保留在当前页面start*/
    $scope.reload=function(){
        $scope.paginationConf.currentPage = $(".page-total input").val();
        $scope.paginationConf.itemsPerPage = $(".page-total select").val();
        $scope.onQuery();
    };
    /*处理之后保留在当前页面end*/
    $scope.outPost=function(order){
      popupDiv('SaveSuccess');
      $(".Message").html('功能尚未开发');
    };
    $scope.onhandle= function(order){
        if($scope.searchButton==true){
            $scope.dealStyle='处理';
            $state.go('main.consumerUpgradeOrderDetail',{'hideData':0});
        }else if($scope.searchButton==2){
            $scope.dealStyle='查看';
            $state.go('main.consumerUpgradeOrderDetailSearch',{'hideData':1});
        }else{
            $scope.dealStyle='查看';
            $state.go('main.consumerUpgradeOrderDetailSearchSales',{'hideData':0});
        }
        ToBankOrderDetailService.setCurHandleOrder(order);
    }
});