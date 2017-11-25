/**
 * Created by caolongping on 2016/5/18.
 */
consumerToBankApp.controller('hadFailSendToBankOrderListController',function ($scope,$http,$filter,$cookies, UrlService,$resource, $location, $state, hadSendFailToBankListService, ToBankOrderDetailService,ToBankLoanOrderDetailService) {
    $scope.WebURL=UrlService.getUrl('manageAudit');
    var data =localStorage.getItem('dataWait');
    $scope.dataWait=data;
    //配置分页基本参数
    $(".page-total input").blur(function(){
//        alert($(this).val());
        $cookies.put('currentPageSendFail',$(this).val());
    });
    $(".pagination").click(function(){
//        alert($(".page-total input").val());
        $cookies.put('currentPageSendFail',$(".page-total input").val());
    });
    $(".page-total select").change(function(){
        var items =$(".page-total select").val();
        var ss = items.substring(7,items.length);
        $cookies.put('itemsPerPageSendFail',ss);
    });
    var currentPage;
    var itemsPerPage;
    if($cookies.get('currentPageSendFail')==null||$cookies.get('currentPageSendFail')==''||$cookies.get('currentPageSendFail')==undefined){
        currentPage=1;
    }else{
        currentPage=$cookies.get('currentPageSendFail');
    }
    if($cookies.get('itemsPerPageSendFail')==null||$cookies.get('itemsPerPageSendFail')==''||$cookies.get('itemsPerPageSendFail')==undefined){
        itemsPerPage=15;
    }else{
        itemsPerPage=$cookies.get('itemsPerPageSendFail');
    }
    $scope.paginationConf = {
        currentPage: currentPage,
        itemsPerPage: itemsPerPage
    };
    $scope.orderList = hadSendFailToBankListService.getOrderList();

    /* 从总配置中查询优啦产品信息和渠道产品信息 start queryListProduct*/
    $scope.queryListProducts=function(){
        hadSendFailToBankListService.queryListProduct({}, function(response){
            if(response.status==0&&response.obj!=null&&response.obj!=''&&response.obj!=undefined){
                $scope.listProduct = response.obj;
                $scope.showChannelProductType();
                $scope.showProductType();
            }else{
                console.log(response.message)
            }
        });
    };
    $scope.queryListProducts();
    $scope.showChannelProduct=function(res){
        if($scope.listProduct!=''&&$scope.listProduct!=null&&$scope.listProduct!=undefined){
            $scope.listProduct.forEach(function(item){
                if(item.productCode==res){
                    $scope.channelProductList=item.channelProductes;
                }
            })
        }
    };
    $scope.showProductType=function(res){
        if($scope.listProduct!=''&&$scope.listProduct!=null&&$scope.listProduct!=undefined){
            $scope.listProduct.forEach(function(item){
                $scope.orderList.forEach(function(items){
                    if(items.loanProductType==item.productCode){
                        items.productName=item.productName;
                    }
                })
            })
        }
    };
    $scope.showChannelProductType=function(res){
        if($scope.listProduct!=''&&$scope.listProduct!=null&&$scope.listProduct!=undefined){
            $scope.channelProductLists=[];
            $scope.listProduct.forEach(function(item){
                if(item.channelProductes!=null&&item.channelProductes!=''&&item.channelProductes!=undefined){
                    item.channelProductes.forEach(function(data){
                        $scope.channelProductLists.push(data)
                    })
                }
            });
            console.log($scope.channelProductLists);
            if($scope.channelProductLists!=''&&$scope.channelProductLists!=undefined&&$scope.channelProductLists!=null){
                $scope.channelProductLists.forEach(function(items){
                    $scope.orderList.forEach(function(datas){
                        if(datas.channelProductCode==items.code){
                            datas.channelProductName=items.name;
                        }
                    })
                })
            }
        }
    };
    /* 从总配置中查询优啦产品信息和渠道产品信息 end queryListProduct*/

    /* 查询订单列表 queryListOrderStatus start*/
    $scope.queryListOrder=function(){
        hadSendFailToBankListService.queryListOrderStatus({}, function(response){
            if(response.status==0&&response.obj!=null&&response.obj!=''&&response.obj!=undefined){
                $scope.ListOrderStatus = response.obj;
            }else{
                console.log(response.message)
            }
        });
    };
    $scope.queryListOrder();
    $scope.showOrderStatus=function(){
        if($scope.ListOrderStatus!=''&&$scope.ListOrderStatus!=null&&$scope.ListOrderStatus!=undefined){
            $scope.ListOrderStatus.forEach(function(item){
                $scope.orderList.forEach(function(items){
                    if(items.status==item.key){
                        items.orderStatusName=item.value;
                    }
                })
            });
        }

    };
    /* 查询订单列表 end*/

    /*查询*/
    //用户姓名
    $("#cusName").blur(function(){
        $cookies.put('cusNameSendFail',$(this).val());
    });
    $scope.cusName=$cookies.get('cusNameSendFail');
    //用户电话
    $("#telephone").blur(function(){
        $cookies.put('telephoneSendFail',$(this).val());
    });
    $scope.telephone=$cookies.get('telephoneSendFail');

    //用户电话批量
    $("#userPhones").blur(function(){
        if($(this).val()!=''){
            $cookies.put('userPhonesSendFail',$(this).val().match(/\d+/g).join(','));
        }else{
            $cookies.put('userPhonesSendFail',$(this).val());
        }
    });
    $scope.userPhones=$cookies.get('userPhonesSendFail');

    //创建放款单开始时间
    $("#startDate").blur(function(){
        $cookies.put('startDateSendFail',$(this).val());
    });
    $scope.startDate=$cookies.get('startDateSendFail');
    $("#startDate").val($scope.startDate);

    //创建放款单结束时间
    $("#endDate").blur(function(){
        $cookies.put('endDateSendFail',$(this).val());
    });
    $scope.endDate=$cookies.get('endDateSendFail');
    $("#endDate").val($scope.endDate);

    //产品类型
    $("#productId").blur(function(){
        $cookies.put('productIdSendFail',$(this).val());
    });
    $scope.productId=$cookies.get('productIdSendFail');

    //资金通道
    $("#channelProductCode").blur(function(){
        $cookies.put('channelProductCodeSendFail',$(this).val());
    });
    $scope.channelProductCode=$cookies.get('channelProductCodeSendFail');

    //中征码
    $("#zzCodeExists").blur(function(){
        $cookies.put('zzCodeExistsSendFail',$(this).val());
    });

    $scope.zzCodeExists=$cookies.get('zzCodeExistsSendFail');
    //备注状态
    $scope.putFlagToCook=function(res){
        $cookies.put('remarkFlagSendFail',res);
    };
    $scope.remarkFlag=$cookies.get('remarkFlagSendFail');

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
        $cookies.remove('cusNameSendFail');
        $cookies.remove('telephoneSendFail');
        $cookies.remove('userPhonesSendFail');
        $cookies.remove('startDateSendFail');
        $cookies.remove('endDateSendFail');
        $cookies.remove('productIdSendFail');
        $cookies.remove('channelProductCodeSendFail');
        $cookies.remove('remarkFlagSendFail');
        $cookies.remove('zzCodeExistsSendFail');
        $cookies.remove('currentPageSendFail');
        $cookies.remove('itemsPerPageSendFail');
        $scope.onQuery();
    };

    $scope.onQuery=function () {
        $scope.startDates=$filter('date')($scope.startDate,'yyyy-MM-dd');
        $scope.endDates=$filter('date')($scope.endDate,'yyyy-MM-dd');
        hadSendFailToBankListService.queryOrder({
            name:$scope.cusName,//姓名
            phone:$scope.telephone,//用户电话
            pushStartTime:$scope.startDates,//推送开始时间(yyyy-MM-dd)
            pushEndTime:$scope.endDates,//推送结束时间(yyyy-MM-dd)
            loanProductType:$scope.productId,//贷款产品类型
            channelProductCode:$scope.channelProductCode,//放款金融机构贷款产品类型
            zzCodeExists:$scope.zzCodeExists,//中证码有无中证码有无(1：有，0：无)
            flag:$scope.remarkFlag,//备注状态
            status:'PUSH_EXCEPTION',//推送银行失败
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
        hadSendFailToBankListService.queryFlagBz({
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
    $scope.onhandle= function($event,order){
//  	alert('我是审批待处理')
        //  	只缓存最后一条
        //  	获取点击对应元素的appid
        var dataId = $($event.target).parents('tr').data("appid")
//  	存在localstorage
//		alert(dataId)
        localStorage.setItem('dataWait',dataId)
        var data =localStorage.getItem('dataWait')
        $scope.dataWait=data
//		alert(data)
        if(order.loanProductType=='CONSUMPTION_LOANS'){//消费贷跳转详情
            $state.go('main.consumerToBankOrderDetail',{'hideData':0});
            ToBankOrderDetailService.setCurHandleOrder(order);
        }else{//经营贷跳转详情
            if(order.conditionStatusMap==undefined){
                // 股东确认
                $state.go('main.consumerToBankLoanOrderDetail',{'hideData':0});
            }else{
                if(order.conditionStatusMap.CCB_ELOAN_DATA_INPUT==undefined||order.conditionStatusMap.CCB_ELOAN_DATA_INPUT.success==false){
                    // 股东确认
                    $state.go('main.consumerToBankLoanOrderDetail',{'hideData':0});
                }else{
                    if(order.conditionStatusMap.CCB_PUSH_INFO_CONFIRM==undefined||order.conditionStatusMap.CCB_PUSH_INFO_CONFIRM==false){
                        // 确认推送信息
                        $state.go('main.consumerToBankLoanOrderDetailDeal',{'hideData':0});
                    }else{
                        // 查看股东信息以及企业信息
                        $state.go('main.consumerToBankLoanOrderDetailSearch',{'hideData':0});
                    }
                }
            }
            ToBankLoanOrderDetailService.setCurHandleOrder(order);
        }

    }
});