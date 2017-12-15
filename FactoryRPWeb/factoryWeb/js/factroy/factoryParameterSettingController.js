/**
 * Created by jayvenLee on 2017/11/11.
 */
factoryParameterSettingApp.controller('factoryParameterSettingController',function ($scope,UrlService,$http,$filter,$cookies,$resource, $location, $state, factoryParameterSettingService, ToBankOrderDetailService,ToBankLoanOrderDetailService) {
    $scope.WebURL=UrlService.getUrl('manageAudit');
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
        currentPage: currentPage,
        itemsPerPage: itemsPerPage
    };
    $scope.orderList = factoryParameterSettingService.getOrderList();

    /* 从总配置中查询优啦产品信息和渠道产品信息 start queryListProduct*/
    $scope.queryListProducts=function(){
        factoryParameterSettingService.queryListProduct({}, function(response){
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
    $scope.showProductType=function(){
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
    $scope.showChannelProductType=function(){
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
        factoryParameterSettingService.queryListOrderStatus({}, function(response){
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

    //创建放款单开始时间
    $("#startDate").blur(function(){
        $cookies.put('startDateAllSend',$(this).val());
    });
    $scope.startDate=$cookies.get('startDateAllSend');
    $("#startDate").val($scope.startDate);

    //创建放款单结束时间
    $("#endDate").blur(function(){
        $cookies.put('endDateAllSend',$(this).val());
    });
    $scope.endDate=$cookies.get('endDateAllSend');
    $("#endDate").val($scope.endDate);

    //产品类型
    $("#productId").blur(function(){
        $cookies.put('productIdAllSend',$(this).val());
    });
    $scope.productId=$cookies.get('productIdAllSend');

    //资金通道
    $("#channelProductCode").blur(function(){
        $cookies.put('channelProductCodeAllSend',$(this).val());
    });
    $scope.channelProductCode=$cookies.get('channelProductCodeAllSend');

    //中征码
    $("#zzCodeExists").blur(function(){
        $cookies.put('zzCodeExistsAllSend',$(this).val());
    });
    $scope.zzCodeExists=$cookies.get('zzCodeExistsAllSend');

    //订单状态
    $("#orderStatus").blur(function(){
        $cookies.put('orderStatusAllSend',$(this).val());
    });
    $scope.orderStatus=$cookies.get('orderStatusAllSend');

    //备注状态
    $scope.putFlagToCook=function(res){
        $cookies.put('remarkFlagAllSend',res);
    };
    $scope.remarkFlag=$cookies.get('remarkFlagAllSend');

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
        $("#orderStatus").val('');
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
        $scope.orderStatus=null;
        $cookies.remove('cusNameAllSend');
        $cookies.remove('telephoneAllSend');
        $cookies.remove('userPhonesAllSend');
        $cookies.remove('startDateAllSend');
        $cookies.remove('endDateAllSend');
        $cookies.remove('productIdAllSend');
        $cookies.remove('channelProductCodeAllSend');
        $cookies.remove('remarkFlagAllSend');
        $cookies.remove('zzCodeExistsAllSend');
        $cookies.remove('orderStatusAllSend');
        $cookies.remove('currentPageAllSend');
        $cookies.remove('itemsPerPageAllSend');
        $scope.onQuery();
    };

    $scope.onQuery=function () {
        $scope.startDates=$filter('date')($scope.startDate,'yyyy-MM-dd');
        $scope.endDates=$filter('date')($scope.endDate,'yyyy-MM-dd');
        factoryParameterSettingService.queryOrder({
            name:$scope.cusName,//姓名
            phone:$scope.telephone,//用户电话
            pushStartTime:$scope.startDates,//查询创建开始时间
            pushEndTime:$scope.endDates,//查询创建结束时间
            loanProductType:$scope.productId,//贷款产品类型
            channelProductCode:$scope.channelProductCode,//放款金融机构贷款产品类型
            zzCodeExists:$scope.zzCodeExists,//中证码有无中证码有无(1：有，0：无)
            flag:$scope.remarkFlag,//备注状态
            status:$scope.orderStatus,//订单状态 WAITING_PUSH:待推送银行 AUDITING:推送银行成功 PUSH_EXCEPTION:推送银行失败
            pageNo: $scope.paginationConf.currentPage,
            pageSize: $scope.paginationConf.itemsPerPage
        }, function(response){
            if(response.obj.totalCount>=1){
                $scope.paginationConf.totalItems = response.obj.totalCount;
                $scope.orderList=response.obj.dataItem
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
    $scope.onhandle= function($event,order){
//  	alert('我是审批待处理')
        //  	只缓存最后一条
        //  	获取点击对应元素的appid
        var dataId = $($event.target).parents('tr').data("appid");
//  	存在localstorage
//		alert(dataId)
        localStorage.setItem('dataWait',dataId);
        var data =localStorage.getItem('dataWait');
        $scope.dataWait=data;
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