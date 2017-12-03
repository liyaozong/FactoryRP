/**
 * Created by caolongping on 2016/5/18.
 */
//var app = angular.module('myApp', ['tm.pagination','ngResource']);
consumerToBankApp.controller('consumerToBankLoanOrderDetailController',function ($scope,$cookies,$stateParams,againstCheatReportService,consumerAuditDataCheckService,$resource, $location, $state, UrlService, ToBankLoanOrderDetailService,$http,$filter) {
    $scope.WebURL=UrlService.getUrl('manageAudit');
    $scope.WebURLTaoBao=UrlService.getUrl('baseConfigTaoBaoInfo');
    $(".tableListDiv ul li svg").click(function(){
        $(this).parent().parent().find('.detail').toggleClass('hide');
        if($(this).parent().parent().find('.detail').hasClass('hide')){
            $(this).parent().parent().css('margin-bottom','0');
            $(this).find('path').attr('d','M512 832a32 32 0 0 0 32-32v-256h256a32 32 0 0 0 0-64h-256V224a32 32 0 0 0-64 0v256H224a32 32 0 0 0 0 64h256v256a32 32 0 0 0 32 32')
        }else{
            $(this).parent().parent().css('margin-bottom','15px');
            $(this).find('path').attr('d','M801.171 547.589H222.83c-17.673 0-32-14.327-32-32s14.327-32 32-32h578.341c17.673 0 32 14.327 32 32s-14.327 32-32 32z')
        }

    });
    $scope.curHandleOrder = ToBankLoanOrderDetailService.getCurHandleOrder();
//    $scope.orderStatus=$scope.curHandleOrder.orderStatus;
    //userID
    if($scope.curHandleOrder.userId!=null&&$scope.curHandleOrder.userId!=''&&$scope.curHandleOrder.userId!=undefined){
        $cookies.put('userId',$scope.curHandleOrder.userId);
        $scope.userId=$cookies.get('userId');
    }else{
        $scope.userId=$cookies.get('userId');
    }
    // 放款单code
    if($scope.curHandleOrder.code!=null&&$scope.curHandleOrder.code!=''&&$scope.curHandleOrder.code!=undefined){
        $cookies.put('code',$scope.curHandleOrder.code);
        $scope.code=$cookies.get('code');
    }else{
        $scope.code=$cookies.get('code');
    }
    // 渠道code
    if($scope.curHandleOrder.channelProductCode!=null&&$scope.curHandleOrder.channelProductCode!=''&&$scope.curHandleOrder.channelProductCode!=undefined){
        $cookies.put('channelProductCode',$scope.curHandleOrder.channelProductCode);
        $scope.channelProductCode=$cookies.get('channelProductCode');
    }else{
        $scope.channelProductCode=$cookies.get('channelProductCode');
    }
    // 放款金额
    if($scope.curHandleOrder.withdrawAmount!=null&&$scope.curHandleOrder.withdrawAmount!=''&&$scope.curHandleOrder.withdrawAmount!=undefined){
        $cookies.put('withdrawAmount',$scope.curHandleOrder.withdrawAmount);
        $scope.withdrawAmount=$cookies.get('withdrawAmount');
    }else{
        $scope.withdrawAmount=$cookies.get('withdrawAmount');
    }
    // 放款期限
    if($scope.curHandleOrder.loanTerm!=null&&$scope.curHandleOrder.loanTerm!=''&&$scope.curHandleOrder.loanTerm!=undefined){
        $cookies.put('loanTerm',$scope.curHandleOrder.loanTerm);
        $scope.loanTerm=$cookies.get('loanTerm');
    }else{
        $scope.loanTerm=$cookies.get('loanTerm');
    }
    // 利率
    if($scope.curHandleOrder.serviceFee!=null&&$scope.curHandleOrder.serviceFee!=''&&$scope.curHandleOrder.serviceFee!=undefined){
        $cookies.put('serviceFee',$scope.curHandleOrder.serviceFee);
        $scope.serviceFee=$cookies.get('serviceFee');
    }else{
        $scope.serviceFee=$cookies.get('serviceFee');
    }
    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 10
    };
    $scope.paginationConf1 = {
        currentPage: 1,
        itemsPerPage: 10
    };
    $scope.paginationConf2 = {
        currentPage: 1,
        itemsPerPage: 10
    };
    $scope.paginationConf3 = {
        currentPage: 1,
        itemsPerPage: 10
    };
    $scope.paginationConf8 = {
        currentPage: 1,
        itemsPerPage: 10
    };
    /*淘宝详情 start*/
    $scope.paginationConf4 = {
        currentPage: 1,
        itemsPerPage: 5
    };
    $scope.queryTaobaoInfos=function(){
        var pa={
//            userId:$scope.userId
            userId:2954
        };
        var pa1={
//            userId:$scope.userId,
            userId:2954,
            currentPage:$scope.paginationConf4.currentPage,
            itemsPerPage:$scope.paginationConf4.itemsPerPage
        };
        console.log(pa);
        var par={params:pa};
        /*收货地址 start*/
        $http.post($scope.WebURLTaoBao+'deliverys',pa).success(function(response){
            if(response.status==0&&response.obj!=''&&response.obj!=null&&response.obj!=undefined){
                $scope.TaoBaoDeliverys=response.obj;
                console.log($scope.TaoBaoDeliverys);
//                    popupDiv('SaveSuccess');
//                    $('.SaveSuccess .Message').html(response.message);
            }else{
                $scope.TaoBaoDeliverys='';
//                popupDiv('SaveSuccess');
//                $('.SaveSuccess .Message').html(response.message);
            }
        }).error(function(response){
            console.log(response.msg);
        });
        /*收货地址 end*/
        /*淘宝基本信息 start*/
        $http.post($scope.WebURLTaoBao+'info',pa).success(function(response){
            if(response.status==0&&response.obj!=''&&response.obj!=null&&response.obj!=undefined){
                $scope.TaoBaoBaseInfo=response.obj[0];
                console.log($scope.TaoBaoBaseInfo);
//                    popupDiv('SaveSuccess');
//                    $('.SaveSuccess .Message').html(response.message);
            }else{
                $scope.TaoBaoBaseInfo='';
//                popupDiv('SaveSuccess');
//                $('.SaveSuccess .Message').html(response.message);
            }
        }).error(function(response){
            console.log(response.msg);
        });
        /*淘宝基本信息 end*/
        /*淘宝订单信息 start*/
        $http.post($scope.WebURLTaoBao+'trades',pa1).success(function(response){
            if(response.status==0&&response.obj.list!=''&&response.obj.list!=null&&response.obj.list!=undefined){
                if(response.obj.totalCount>=1){
                    $scope.TaoBaoTrades=response.obj.list;
                    console.log($scope.TaoBaoTrades);
                    $scope.paginationConf4.totalItems = response.obj.totalCount;
                }else{
                    $scope.paginationConf4.totalItems = 0;
                    $scope.TaoBaoTrades = '';
                }
//                    popupDiv('SaveSuccess');
//                    $('.SaveSuccess .Message').html(response.message);
            }else{
                $scope.TaoBaoTrades='';
//                popupDiv('SaveSuccess');
//                $('.SaveSuccess .Message').html(response.message);
            }
        }).error(function(response){
            console.log(response.msg);
        });
        /*淘宝订单信息 end*/
    };
    $scope.queryTaobaoInfos();
    /*淘宝详情 end*/
    /*店铺列表 start*/
    $scope.onStoreToggle = function(shopItem) {
        shopItem.isShow = !shopItem.isShow;
        if(shopItem.isShow){
            $scope.isActives = true;
        }else{
            $scope.isActives = false;
        }
    };
    $scope.onStoreToggleAdd=function(){
        $scope.onStoreToggle();
    };
    $scope.queryStoryLists = function(){
        $scope.queryStoryList = ToBankLoanOrderDetailService.queryStoryList({
            'userId':$scope.userId
        },function(data) {
            if (data.status == 0) {
                if (data.obj != '' && data.obj != null && data.obj != undefined) {
                    //TODO-业务逻辑
                    $scope.storeInfos = data.obj;
                    console.log($scope.storeInfos);
                } else {
                    //popupDiv('SaveSuccess');
                    //$('.SaveSuccess .Message').html(data.message);
                }
            } else {
                console.log(data.message);
            }
        }, function (err) {
            console.log(err);
        });
    };
    $scope.queryStoryLists();
    /*店铺列表 end*/
    /*添加个人、企业共同贷款人 start*/
    $scope.activePerson=true;
    $scope.activeCompany=false;
    $scope.addPerson=function(){
        $scope.activePerson=true;
        $scope.activeCompany=false;
    };
    $scope.addCompany=function(){
        $scope.activePerson=false;
        $scope.activeCompany=true;
    };
    /*添加个人、企业共同贷款人 end*/
    /*放款条件列表 start*/
    $scope.queryConName=[{"id":1,"code":"1","name":"担保人房产证上传","sort":0,"remark":"请上传清晰的房产证的图片"},{"id":2,"code":"2","name":"担保人结婚证上传","sort":0,"remark":"请上传结婚证"},{"id":3,"code":"3","name":"担保人购车认证上传","sort":0,"remark":"请上传购车认证图片"},{"id":4,"code":"4","name":"其他","sort":0,"remark":"其他条件"}];
    /*放款条件列表 end*/
    /*查询放款条件 start*/
    $scope.queryLoanConditonss=function(){
        $scope.queryCommonloan= ToBankLoanOrderDetailService.queryLoanConditions($scope.userId,  function(data){
            if(data.status==0){
                if(data.obj!=null){
                    //TODO-业务逻辑
                    $scope.queryloanConditionsInfos = data.obj;
//                    console.log(data.obj);
                }else{
//                    alert("未获取到数据");
                    $scope.queryloanConditionsInfos=[];
                }
            }else{
                console.log(data.message);
            }
        }, function(err){
            console.log(err)
        });
    };
    $scope.queryLoanConditonss();
    /*查询放款条件 end*/
    /*查询放款条件详情 start*/
    $scope.searchDetail=function(res){
        popupDiv('fileList');
        $scope.fileLists=res.url;
        $scope.fileName=res.termsType;
    };
    /*查询放款条件详情 end*/
    /*删除放款条件 start*/
    $scope.deletequeryLoanConditonss=function(res){
        popupDiv('deleteCommonLoanContainer');
        $scope.titleName='放款条件';
        $scope.CommonLoanName=res.termsType;
        $scope.deleteCommonLoanSure=function(){
            $scope.deleteCommonloan= ToBankLoanOrderDetailService.deleteLoanConditions(res.id, function(data){
                if(data.status==0){
                    popupDiv('SaveSuccess');
                    $(".Message").html(data.message);
                }else{
                    console.log(data.message);
                }
            }, function(err){
                console.log(err)
            });
        };
    };

    /*删除放款条件 end*/
    /*********添加放款条件 start********/
    $scope.addLoanconditions=function() {
        $scope.conReName = $("#condName1").text();
        if ($scope.selectedCommonCondId == '' ||$scope.selectedCommonCondId == null||$scope.selectedCommonCondId == undefined) {
            $("#selectedCommonCondId").focus();
        } else {
            ToBankLoanOrderDetailService.addLoanConditions({
                    userId:$scope.userId,
                    termsType: $("#selectedCommonCondId option:selected").text(),
                    remark: $scope.remark
                }, function (response) {
                    hideDiv('addConditions');
                    popupDiv('SaveSuccess');
                    $(".Message").html(response.message);
                }, function (err) {
//               alert("失败")
                }
            )
        }
    };
    /*********添加放款条件 end********/
    /*********添加共同贷款人个人 start********/
    $scope.addCommonLoanerP=function() {
        $(".store").css("background-color","#ccc");
        $(".store").attr("disabled","disabled");
        if ($scope.jointLoanUserName == '' || $scope.jointLoanUserName == null || $scope.jointLoanUserName == undefined) {
            $("#firPersonN").focus();
        } else if($scope.jointLoanPhone == '' || $scope.jointLoanPhone == null || $scope.jointLoanPhone == undefined){
            $("#jointLoanPhone").focus();
        }else if($scope.jointLoanIDcard == '' || $scope.jointLoanIDcard == null || $scope.jointLoanIDcard == undefined){
            $("#jointLoanIDcard").focus();
        }else{
            ToBankLoanOrderDetailService.addPersonalCommonLoaner({
                userId: $scope.userId,
                coBorrowerPhone: $scope.jointLoanPhone,//共同借款人手机号
                coBorrowerName: $scope.jointLoanUserName,//共同借款人姓名
                coBorrowerByName: $scope.jointLoanUserName+'('+$scope.jointLoanPhone.substr(7,11)+')',//共同贷款人姓名（别名）--防止姓名冲突（姓名+手机尾号后4位，例：张三(手机尾号3467)）
                coBorrowerCardNo: $scope.jointLoanIDcard//共同借款人身份证
            }, function (response) {
                hideDiv('addCommonLoader');
                popupDiv('SaveSuccess');
                $(".Message").html(response.message);
            }, function (err) {
//                alert("失败")
            });
        }
    };
    /*********添加共同贷款人个人 end********/
    /*查询共同借款人 start*/
    $scope.queryCommonLoanInfoS=function(){
        $scope.queryCommonloan= ToBankLoanOrderDetailService.queryPersonalCommonLoaner({
            userId:$scope.userId
        },function(data){
            if(data.status==0){
                if(data.obj!=null){
                    //TODO-业务逻辑
                    $scope.queryCommonloanInfos = data.obj;
                    console.log(data.obj[0]);
                }else{
                    console.log(data.message);
                }
            }else{
                console.log(data.message);
            }
        }, function(err){
            console.log(err)
        });
    };
    $scope.queryCommonLoanInfoS();
    /*查询共同借款人 end*/

    /*查询实际控制人 queryRealController start*/
    $scope.queryRealControllers=function(){
        $scope.queryRealController= ToBankLoanOrderDetailService.queryRealController({
            userId:$scope.userId
        },function(data){
            if(data.status==0){
                if(data.obj!=null){
                    //TODO-业务逻辑
                    $scope.RealControllers = data.obj;
                    console.log($scope.RealControllers);
                }else{
                    console.log(data.message);
                }
            }else{
                console.log(data.message);
            }
        }, function(err){
            console.log(err)
        });
    };
    $scope.queryRealControllers();
    /*查询实际控制人 end*/

    /*查询企业 queryCompanyInfo start*/
    $scope.queryCompanyInfos=function(){
        $scope.queryCompanyInfo= ToBankLoanOrderDetailService.queryCompanyInfo({
            userId:$scope.userId
        },function(data){
            if(data.status==0){
                if(data.obj!=null){
                    //TODO-业务逻辑
                    $scope.CompanyInfos = data.obj;
                    console.log($scope.CompanyInfos);
                }else{
                    console.log(data.message);
                }
            }else{
                console.log(data.message);
            }
        }, function(err){
            console.log(err)
        });
    };
    $scope.queryCompanyInfos();
    /*查询企业 queryCompanyInfo end*/

    /*删除共同借款人 start*/
    $scope.deleteCommonloanInfos=function(res){
        popupDiv('deleteCommonLoanContainer');
        $scope.titleName='共同借款人';
        $scope.CommonLoanName=res.coBorrowerName;
        $scope.deleteCommonLoanSure=function(){
            $scope.deleteCommonloan= ToBankLoanOrderDetailService.deletePersonalCommonLoaner(res.id, function(data){
                if(data.status==0){
                    hideDiv('deleteCommonLoanContainer');
                    popupDiv('SaveSuccess');
                    $(".Message").html(data.message);
                }else{
                    console.log("删除共同借款人调用接口失败！");
                }
            }, function(err){
                console.log(err)
            });
        };

    };
    /*删除共同借款人 end*/
    /*查看股东详情 searchShareHolders start*/
    $scope.searchShareHolders=function($event,res){
        $($event.target).parent().parent().find('.detail').toggleClass('hide');
        $($event.target).parent().parent().siblings().find('.detail').addClass('hide');
        $scope.shareHolders=res.shareHolders;
    };
    /*查看股东详情 end*/
    /*查询企业以及股东信息 queryWithdrawCompanyInfo start*/
    $scope.queryWithdrawCompanyInfos=function(){
        $scope.queryWithdrawCompany= ToBankLoanOrderDetailService.queryWithdrawCompanyInfo({
            userId:$scope.userId, //申请人用户Id
            code:$scope.code      //订单ID（放款的订单ID）
        }, function(data){
            if(data.status==0){
                $scope.storeBaseDto=data.obj.storeBaseDto;//实际控制人,企业，店铺等信息列表
                $scope.companyCode=data.obj.zzCode;//企业中征码
                $scope.coBorrowerDtos=data.obj.coBorrowerDtos;//共同贷款人相关信息列表

                /*推送银行初始值 start*/
                $scope.orderCode=data.obj.code;
                $scope.sellerNick=$scope.storeBaseDto.sellerNick;
                $scope.companyName=$scope.storeBaseDto.companyName;
                $scope.corporateType=$scope.storeBaseDto.companyType;
                $scope.pbCreditCode=$scope.storeBaseDto.zzCode;
                $scope.businessLicense=$scope.storeBaseDto.licenceNo;
                $scope.registeredCapital=$scope.storeBaseDto.registeredCapital/10000;
                $scope.organizationCode=$scope.storeBaseDto.organizationCode;
                $scope.showCity(parseInt($scope.storeBaseDto.province));
                $scope.provinceSN=parseInt($scope.storeBaseDto.province);
                $scope.cityCode=parseInt($scope.storeBaseDto.city);
                $scope.companyAddress=$scope.storeBaseDto.address;
                $scope.name=$scope.storeBaseDto.realControllerName;
                $scope.idcard=$scope.storeBaseDto.realControllerCardNo;
                $scope.mobile=$scope.storeBaseDto.realControllerPhone;
                $scope.shopUrl=$scope.storeBaseDto.shopUrl;

                $scope.companyIndustry='F5294';
                $scope.companySize='30';
                $scope.loanPayTerm='12';
                $scope.repaymentMethod='1';
                $scope.riskRating='1';

                /*推送银行初始值 end*/

                if(data.obj.shareHolderDtos!=''&&data.obj.shareHolderDtos!=null&&data.obj.shareHolderDtos!=undefined){
                    $scope.channelsTypes=data.obj.shareHolderDtos;//股东相关信息
                }else{
                    $scope.channelsTypes = [];
                    var houseObject = {
                        shareHolderName: "",
                        shareHolderPhone: "",
                        shareHolderCardNo: ""
                    };
                    $scope.channelsTypes.push(houseObject);
                    console.log($scope.channelsTypes.length - 1);
                }
            }else{
                $scope.channelsTypes = [];
                var houseObject = {
                    shareHolderName: "",
                    shareHolderPhone: "",
                    shareHolderCardNo: ""
                };
                $scope.channelsTypes.push(houseObject);
                console.log($scope.channelsTypes.length - 1);
                console.log(data.message);
            }
        }, function(err){
            console.log(err)
        });
    };
    $scope.queryWithdrawCompanyInfos();
    /*查询企业以及股东信息 end*/
    /*添加企业 start*/
    $scope.addHouse = function() {
        var houseObject = {
            shareHolderName: "",
            shareHolderPhone: "",
            shareHolderCardNo: ""
        };
        $scope.channelsTypes.push(houseObject);
        console.log($scope.channelsTypes.length - 1)
    };
    $scope.delHouse = function(permission, $index) {
        $("." + $index).addClass('animated rollOut');
        setTimeout(function() {
            $scope.channelsTypes.splice($index, 1);
            $("." + $index).hide()
        }, 400)

    };

    var inputId = []; //存放所有DOM元素的id
    var inputNewId = []; //存放所有DOM元素的id
    /*添加股东 start*/
    $scope.isOkAdd=false;
    $scope.addShareHolderSure=function(){
        $(".sectionNew select").each(function(index, item) {
            inputNewId.push($(this).attr("name"))
        });
        $(".sectionNew input").each(function(index, item) {
            inputNewId.push($(this).attr("name"))
        });
        var _inputNewId = del_repeat(inputNewId);
        for (var i = 0; i < _inputNewId.length; i++) {
            console.log($('#' + _inputNewId[i]));
            if (_inputNewId[i].substring(0, _inputNewId[i].length - 1) == "shareHolderName" &&($('#' + _inputNewId[i]).val() == "" || $('#' + _inputNewId[i]).val() == null || $('#' + _inputNewId[i]).val() == undefined)) {
                //$(".informationError").show();
                $('#' + _inputNewId[i]).focus();
                return false;
            }
            else if (_inputNewId[i].substring(0, _inputNewId[i].length - 1) == "shareHolderPhone" &&($('#' + _inputNewId[i]).val() == "" || $('#' + _inputNewId[i]).val() == null || $('#' + _inputNewId[i]).val() == undefined)) {
                //$(".informationError").show();
                $('#' + _inputNewId[i]).focus();
                return false;
            }
            else if (_inputNewId[i].substring(0, _inputNewId[i].length - 1) == "shareHolderCardNo" &&($('#' + _inputNewId[i]).val() == "" || $('#' + _inputNewId[i]).val() == null || $('#' + _inputNewId[i]).val() == undefined)) {
                //$(".informationError").show();
                $('#' + _inputNewId[i]).focus();
                return false;
            }else{
                $scope.isOkAdd=true;
            }
        }
        if($scope.isOkAdd){
            var channelsArr = [];
            for (var j = 0; j < $scope.channelsTypes.length; j++) {
                var obj = {
                    shareHolderName: $scope.channelsTypes[j].shareHolderName,
                    shareHolderPhone: $scope.channelsTypes[j].shareHolderPhone,
                    shareHolderCardNo: $scope.channelsTypes[j].shareHolderCardNo
                };
                channelsArr.push(obj);
            }
            console.log(channelsArr);
            if($scope.shPerson==0){
                $scope.shPerson=false;
            }else{
                $scope.shPerson=true
            }
            var pa= {
                userId:$scope.userId, //申请人用户Id
                code:$scope.code,    //订单ID（放款的订单ID）
                shareHolderDtos:channelsArr
            };
            var par={params:pa};
            console.log(pa);
            $scope.updateShareHolder = ToBankLoanOrderDetailService.updateShareHolders(pa, function(data){
                if(data.status==0){
                    popupDiv('SaveSuccess');
                    $(".Message").html(data.message);
                }else{
                    popupDiv('SaveSuccess');
                    $(".Message").html(data.message);
                }
            }, function(err){
                console.log(err)
            });
        }
    };
    /*添加股东 end*/

    $scope.realControllerChange=function(res){
        console.log(res);
        $scope.RealControllers.forEach(function(item){
            if(item.userId==res){
                $scope.userPhone=item.userPhone;
                $scope.userCardNo=item.userCardNo;
                $scope.userName=item.userName;
            }
            console.log($scope.userName);
            console.log($scope.userPhone);
            console.log($scope.userCardNo);
        })
    };

    /*添加企业 start*/
    $scope.addCompanyInfoClick=function(){
        $scope.shPerson=1;//是否是股东(true:是，false：不是)
        $scope.companyType=1;//企业类型(1:个体工商户，2：企业)
        popupDiv('addCompanyInfo');
        $(".sectionNew select").each(function(index, item) {
            inputNewId.push($(this).attr("name"))
        });
        $(".sectionNew input").each(function(index, item) {
            inputNewId.push($(this).attr("name"))
        });
        /*根据ID 保存当前信息 start updateSaveFunction*/
        $scope.isOkAdd=false;
        $scope.addCompanySure=function(){
            if($scope.realControllerId==''||$scope.realControllerId==null||$scope.realControllerId==undefined){
                $("#realControllerId").focus();
            }else if($scope.shopName==''||$scope.shopName==null||$scope.shopName==undefined){
                $("#shopName").focus();
            }else if($scope.companyName==''||$scope.companyName==null||$scope.companyName==undefined){
                $("#companyName").focus();
            }else if($scope.licenceNo==''||$scope.licenceNo==null||$scope.licenceNo==undefined){
                $("#licenceNo").focus();
            }else{
                var _inputNewId = del_repeat(inputNewId);
                for (var i = 0; i < _inputNewId.length; i++) {
                    console.log($('#' + _inputNewId[i]));
                    if (_inputNewId[i].substring(0, _inputNewId[i].length - 1) == "shareHolderName" &&($('#' + _inputNewId[i]).val() == "" || $('#' + _inputNewId[i]).val() == null || $('#' + _inputNewId[i]).val() == undefined)) {
                        //$(".informationError").show();
                        $('#' + _inputNewId[i]).focus();
                        return false;
                    }
                    else if (_inputNewId[i].substring(0, _inputNewId[i].length - 1) == "shareHolderPhone" &&($('#' + _inputNewId[i]).val() == "" || $('#' + _inputNewId[i]).val() == null || $('#' + _inputNewId[i]).val() == undefined)) {
                        //$(".informationError").show();
                        $('#' + _inputNewId[i]).focus();
                        return false;
                    }
                    else if (_inputNewId[i].substring(0, _inputNewId[i].length - 1) == "shareHolderCardNo" &&($('#' + _inputNewId[i]).val() == "" || $('#' + _inputNewId[i]).val() == null || $('#' + _inputNewId[i]).val() == undefined)) {
                        //$(".informationError").show();
                        $('#' + _inputNewId[i]).focus();
                        return false;
                    }else{
                        $scope.isOkAdd=true;
                    }
                }
                if($scope.isOkAdd){
                    var channelsArr = [];
                    for (var j = 0; j < $scope.channelsTypes.length; j++) {
                        var obj = {
                            shareHolderName: $scope.channelsTypes[j].shareHolderName,
                            shareHolderPhone: $scope.channelsTypes[j].shareHolderPhone,
                            shareHolderCardNo: $scope.channelsTypes[j].shareHolderCardNo
                        };
                        channelsArr.push(obj);
                    }
                    console.log(channelsArr);
                    if($scope.shPerson==0){
                        $scope.shPerson=false;
                    }else{
                        $scope.shPerson=true
                    }
                    var pa= {
                        userId:$scope.userId,
                        realControllerId:$scope.realControllerId,//实际控制人ID
                        realControllerName:$("#realControllerId").find("option:selected").text(),//实际控制人姓名
                        shPerson:$scope.shPerson,//是否是股东(true:是，false：不是)
                        shopName:$("#shopName").find("option:selected").text(),//店铺名称
                        sellerNick:$scope.shopName,//掌柜名称
                        companyName:$scope.companyName,//企业名
                        companyType:$scope.companyType,//企业类型(1:个体工商户，2：企业)
                        licenceNo:$scope.licenceNo,//营业执照号
                        realControllerPhone:$scope.userPhone,//实际控制人电话
                        realControllerCardNo:$scope.userCardNo,//实际控制人身份证
                        shareHolders:channelsArr
                    };
                    var par={params:pa};
                    console.log(pa);
                    $http.post($scope.WebURL+'company/saveCompany',pa).success(function(response){
                        hideDiv("addCompanyInfo");
                        popupDiv('SaveSuccess');
                        $(".Message").html(response.message);
                    }).error(function(response){
                        popupDiv('SaveSuccess');
                        $(".Message").html(response.message);
                    });
                }
            }
        };
    };
    /*添加企业 end*/


    /*删除企业 start*/
    $scope.deleteCompanyInfos=function(res){
        popupDiv('deleteCommonLoanContainer');
        $scope.titleName='企业';
        $scope.CommonLoanName=res.companyName;
        $scope.deleteCommonLoanSure=function(){
            $scope.deleteCommonloan= ToBankLoanOrderDetailService.deleteCompany({
                userId:$scope.userId,
                sellerNick:res.sellerNick
            }, function(data){
                if(data.status==0){
                    hideDiv('deleteCommonLoanContainer');
                    popupDiv('SaveSuccess');
                    $(".Message").html(data.message);
                }else{
                    console.log("删除企业接口失败！");
                }
            }, function(err){
                console.log(err)
            });
        };

    };
    /*删除企业 end*/
    // 查询授信结果start
    $scope.creditResults=function(){
        console.log($scope.queryCommonloanInfos)
        ToBankLoanOrderDetailService.creditResults($scope.orderId, function(data){
            if(data.status==0){
                $scope.credit=data.obj;
                $scope.version=data.obj.version;
            }else{
                console.log("查询授信结果调用接口失败！");
            }
        }, function(err){
            console.log(err)
        });
    };
    $scope.creditResults();
    // 查询授信结果end

    // 跳转授信报告start
    $scope.automaticCredit=function(){
        $state.go('main.automaticCredit',{'userId':$scope.userId,'orderId':$scope.orderId});
    };
    // 跳转授信报告end


    // 查询授信结果end
    /*转交列表 start*/
    $scope.queryDeliverList=function(){
        $scope.DeliverList = ToBankLoanOrderDetailService.deliverList({
                type: 1, //人员类型   1:风控人员，2:合规人员
                level: 1 //1:员工，2:管理者
            },
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
    $scope.queryDeliverList();
    /*转交列表 end*/
    /*通过拒绝处理操作 start*/
    $scope.showJS=false;
    $scope.showJSOther=false;
//    $scope.changeBank=function($event,code){
//        var bankCodes=[];
//        if($("#BankList input[type='checkbox']:checked").size()>=1){
//            $scope.showJSOther=true;
//        }else{
//            $scope.showJSOther=false;
//        }
//        $('input[name="BankName"]:checked').each(function(){
//            bankCodes.push($(this).val());
//        });
//        if(bankCodes.indexOf('JS')!='-1'){
//            $scope.showJS=true;
////            $scope.showJSOther=false;
//        }else{
//            $scope.showJS=false;
////            $scope.showJSOther=true;
//        }
//    };

    $scope.saveData=function(){
        $cookies.put('ProcessOpinion',$("#ProcessOpinion").val())
    };
    $scope.ProcessOpinion=$cookies.get('ProcessOpinion');
    $scope.handleStatus = 'pass';//处理方式（通过pass，拒绝refuse，转交 transmit）
    $scope.needOrNot = '1';//需要合规参与(0：合规不参与，1：合规参与)
    $scope.submitAndRemark=function(){
        if($scope.handleStatus=='pass'){
            if($scope.sellerNick==''||$scope.sellerNick==null||$scope.sellerNick==undefined){
                $("#sellerNick").focus();
            }else{
                var pa = {
//              orgCode: '' + orgCodes,//银行机构代码
                    orderId: $scope.orderId,
                    version:$scope.version,//授信版本号（处理方式为：通过pass时设定）
                    accessType: $scope.handleStatus,//处理方式（通过pass，拒绝refuse，转交 transmit）
                    regulation: $scope.needOrNot,//需要合规参与(0：合规不参与，1：合规参与)
                    sellerNick: $scope.sellerNick,//店铺掌柜名(处理方式为：转交pass时设定)
                    remark:$scope.ProcessOpinion//处理意见
                };
                console.log(pa);
                $http.post($scope.WebURL + 'approve/approveSubmit', pa).success(function (response) {
                    $("#moneySure").removeAttr("disabled").css("background-color","#4da7fd");
                    if (response.status == 0) {
                        popupDiv('transferInfo');
                        $(".transferInfo .Message").html(response.message);
                        $scope.goBack = function () {
                            hideDiv('transferInfo');
                            window.history.go(-1);
                        }
                    } else {
                        $("#moneySure").removeAttr("disabled").css("background-color","#4da7fd");
                        popupDiv('transferInfo');
                        $(".transferInfo .Message").html(response.message);
                        $scope.goBack = function () {
                            hideDiv('transferInfo');
                            window.history.go(-1);
                        }
                    }
                }).error(function (response) {
                    $("#moneySure").removeAttr("disabled").css("background-color","#4da7fd");
                    console.log(response.message);
                });
            }
        }else if($scope.handleStatus=='refuse'){
            if($scope.ProcessOpinion==''||$scope.ProcessOpinion==undefined||$scope.ProcessOpinion==null){
                $("#ProcessOpinion").focus();
            }else{
                var pa={
//                    orgCode:$scope.BankNameCode,//银行机构代码
                    orderId: $scope.orderId,
                    version:$scope.version,//授信版本号（处理方式为：通过pass时设定）
                    accessType: $scope.handleStatus,//处理方式（通过pass，拒绝refuse，转交 transmit）
//                    regulation: $scope.needOrNot,//需要合规参与(0：合规不参与，1：合规参与)
                    remark:$scope.ProcessOpinion//处理意见
                };
                console.log(pa);
                var par={params:pa};
                $http.post($scope.WebURL+'approve/approveSubmit',pa).success(function(response){
                    if (response.status == 0) {
                        popupDiv('transferInfo');
                        $(".transferInfo .Message").html(response.message);
                        $scope.goBack = function () {
                            hideDiv('transferInfo');
                            window.history.go(-1);
                        }
                    } else {
                        $("#moneySure").removeAttr("disabled").css("background-color","#4da7fd");
                        popupDiv('transferInfo');
                        $(".transferInfo .Message").html(response.message);
                        $scope.goBack = function () {
                            hideDiv('transferInfo');
                            window.history.go(-1);
                        }
                    }
                }).error(function(response){
                    console.log(response.message);
                })
            }
        }else if($scope.handleStatus=='transmit'){
            if($scope.ProcessOpinion==''||$scope.ProcessOpinion==undefined||$scope.ProcessOpinion==null){
                $("#ProcessOpinion").focus();
            }else{
                popupDiv('transfer');
                $("#memberChoose1 input[type='radio']").eq(0).attr('checked','checked');
                $scope.transferMember=function(){
                    $scope.targetOperator=$("#memberChoose1 input[type='radio']:checked").val();
                    $scope.targetOperatorName=$("#memberChoose1 input[type='radio']:checked").parent().next().next().text();
                    var pa={
                        orderId: $scope.orderId,
                        version:$scope.version,//授信版本号（处理方式为：通过pass时设定）
                        accessType: $scope.handleStatus,//处理方式（通过pass，拒绝refuse，转交 transmit）
                        transmitId: $scope.targetOperator,//转交人员ID(处理方式为：转交transmit时设定)
                        remark:$scope.ProcessOpinion//处理意见
                    };
                    console.log(pa);
                    var par={params:pa};
                    $http.post($scope.WebURL+'approve/approveSubmit',pa).success(function(response){
                        if (response.status == 0) {
                            popupDiv('transferInfo');
                            $(".transferInfo .Message").html(response.message);
                            $scope.goBack = function () {
                                hideDiv('transferInfo');
                                window.history.go(-1);
                            }
                        } else {
                            $("#moneySure").removeAttr("disabled").css("background-color","#4da7fd");
                            popupDiv('transferInfo');
                            $(".transferInfo .Message").html(response.message);
                            $scope.goBack = function () {
                                hideDiv('transferInfo');
                                window.history.go(-1);
                            }
                        }
                    }).error(function(response){
                        alert(response.message);
                    })
                }
            }
        }
    };
    /*通过拒绝处理操作 end*/
    /*放款环节提交 submitAndRemarkNew start*/
    $scope.submitAndRemarkNew=function(){
        if($scope.companyCode==''||$scope.companyCode==null||$scope.companyCode==undefined){
            $("#companyCode").focus();
        }else{
            ToBankLoanOrderDetailService.auditSubmitOrder({
                userId:$scope.userId, //申请人用户Id
                code:$scope.code,      //订单ID（放款的订单ID）
                zzCode:$scope.companyCode //中证码
            }, function(data){
                if(data.status==0){
                    popupDiv('SaveSuccess');
                    $(".Message").html(data.message);
                }else{
                    popupDiv('SaveSuccess');
                    $(".Message").html(data.message);
                }
            }, function(err){
                console.log(err)
            });
        }
    };
    /*放款环节提交 end*/
    /* 查询备注列表 queryRemarkList start*/
    $scope.queryRemarkListSure=function(){
        ToBankLoanOrderDetailService.queryRemarkList({
            code:$scope.code     //订单ID（放款的订单ID）
        }, function(data){
            if(data.status==0){
                $scope.RemarkLists=data.obj;
                console.log($scope.RemarkLists);
//                popupDiv('SaveSuccess');
//                $(".Message").html(data.message);
            }else{
                popupDiv('SaveSuccess');
                $(".Message").html(data.message);
            }
        }, function(err){
            console.log(err)
        });
    };
    $scope.queryRemarkListSure();
    /* 查询备注列表 end*/
    /*添加备注 addRemark start*/
    $scope.addRemark=function(){
        if($scope.operAdvice==''||$scope.operAdvice==null||$scope.operAdvice==undefined){
            $("#operAdvice").focus();
        }else{
            var pa={
                code:$scope.code,//放款单编号
//                step:$scope.step,//备注步骤
                remark:$scope.operAdvice //备注内容
            };
            ToBankLoanOrderDetailService.addRemark(pa, function(data){
                if(data.status==0){
                    hideDiv("NoteDiv");
                    popupDiv('SaveSuccess');
                    $(".Message").html(data.message);
                }else{
                    hideDiv("NoteDiv");
                    popupDiv('SaveSuccess');
                    $(".Message").html(data.message);
                }
            }, function(err){
                console.log(err)
            });
        }
    };
    /*添加备注 end*/

    /*获取省市 start*/
    $http.get("js/comm/data.json").success(function(data){
        $scope.provinceCity = data;
        console.log($scope.provinceCity);
    })
        .error(function(){
            alert("出错")
        });
    $scope.showCity=function(res){
        console.log(res);
        $scope.provinceCity.forEach(function(item){
            if(item.regions!=null&&item.regions!=''&&item.regions!=undefined){
                if(item.regions[0].id.toString().substr(0,3)==res.toString().substr(0,3)){
                    $scope.Citys=item.regions;
                    $scope.cityCode=item.regions;
                }
            }

        })
    };
    // 显示省市区
//    $scope.showProvince=function(res){
//        $scope.provinceCity.forEach(function(item){
//           if($scope.CompanyInfos.province==item.id){
//               $scope.CompanyInfos.provinceName=item.name;
//           }
//        })
//    };
    /*获取省市 end*/
    /*学历JSON start*/
    $scope.educations=[
        {id:'081',name:'初中'},
        {id:'071',name:'高中'},
        {id:'051',name:'中专'},
        {id:'041',name:'专科'},
        {id:'031',name:'大学'},
        {id:'021',name:'硕士'},
        {id:'011',name:'博士研究生'}
    ];
    /*学历JSON end*/
    /*获取店铺指标 start*/
    $scope.getStoryInfo=function(){
        ToBankLoanOrderDetailService.findShopItem({
            sellerNick:$scope.sellerNick,
            shopUrl:$scope.shopUrl
        },function(data){
            if(data.status==0&&data.obj!=''&&data.obj!=null&&data.obj!=undefined){
                $scope.annualSalesVolume=data.obj.annualSalesVolume;
                $scope.clickFarmingRate=data.obj.clickFarmingRate;
                $scope.refundRate=data.obj.refundRate;
                $scope.storeAge=data.obj.storeAge;
                $scope.unitPriceYearOnYear=data.obj.unitPriceYearOnYear;
                $scope.ratioOfPaidTraffic=data.obj.ratioOfPaidTraffic;
                $scope.salesYearOnYear=data.obj.salesYearOnYear;
                $scope.debtRatio=data.obj.debtRatio;
                $scope.dsrDescribe=data.obj.dsrDescribe;
                $scope.dsrDescribeAverage=data.obj.dsrDescribeAverage;
                $scope.drsServiceQuality=data.obj.drsServiceQuality;
                $scope.drsServiceQualityAverage=data.obj.drsServiceQualityAverage;
                $scope.drsLogisticsQuality=data.obj.drsLogisticsQuality;
                $scope.drsLogisticsQualityAverage=data.obj.drsLogisticsQualityAverage;
                $scope.disputeRate=data.obj.disputeRate;
                $scope.disputeRateAverage=data.obj.disputeRateAverage;
                $scope.punishPoints=data.obj.punishPoints;
            }else{
                popupDiv('SaveSuccess');
                $(".Message").html(data.message);
            }
        }, function(err){
            console.log(err)
        });
    };
    /*获取店铺指标 end*/
    /*推送 start*/
    $scope.pushToBank=function(){
        if($scope.organizationCode==''||$scope.organizationCode==null||$scope.organizationCode==undefined){
            $("#organizationCode").focus();
        }else if($scope.pbCreditCode==''||$scope.pbCreditCode==null||$scope.pbCreditCode==undefined){
            $("#pbCreditCode").focus();
        }else if($scope.companyIndustry==''||$scope.companyIndustry==null||$scope.companyIndustry==undefined){
            $("#companyIndustry").focus();
        }else if($scope.companySize==''||$scope.companySize==null||$scope.companySize==undefined){
            $("#companySize").focus();
        }else if($scope.companyLinkman==''||$scope.companyLinkman==null||$scope.companyLinkman==undefined){
            $("#companyLinkman").focus();
        }else if($scope.companyLinkmanTel==''||$scope.companyLinkmanTel==null||$scope.companyLinkmanTel==undefined){
            $("#companyLinkmanTel").focus();
        }else if($scope.provinceSN==''||$scope.provinceSN==null||$scope.provinceSN==undefined){
            $("#provinceSN").focus();
        }else if($scope.cityCode==''||$scope.cityCode==null||$scope.cityCode==undefined){
            $("#cityCode").focus();
        }else if($scope.companyAddress==''||$scope.companyAddress==null||$scope.companyAddress==undefined){
            $("#companyAddress").focus();
        }else if($scope.corporateType==''||$scope.corporateType==null||$scope.corporateType==undefined){
            $("#corporateType").focus();
        }
//        else if($scope.legalName==''||$scope.legalName==null||$scope.legalName==undefined){
//            $("#legalName").focus();
//        }
        else if($scope.registeredCapital==''||$scope.registeredCapital==null||$scope.registeredCapital==undefined){
            $("#registeredCapital").focus();
        }else if($scope.documentsAging==''||$scope.documentsAging==null||$scope.documentsAging==undefined){
            $("#documentsAging").focus();
        }else if($scope.postalCode==''||$scope.postalCode==null||$scope.postalCode==undefined){
            $("#postalCode").focus();
        }else if($scope.education==''||$scope.education==null||$scope.education==undefined){
            $("#education").focus();
        }else if($scope.shopUrl==''||$scope.shopUrl==null||$scope.shopUrl==undefined){
            $("#shopUrl").focus();
        }else if($scope.annualSalesVolume==''||$scope.annualSalesVolume==null||$scope.annualSalesVolume==undefined){
            $("#annualSalesVolume").focus();
        }else if($scope.clickFarmingRate==''||$scope.clickFarmingRate==null||$scope.clickFarmingRate==undefined){
            $("#clickFarmingRate").focus();
        }else if($scope.refundRate==''||$scope.refundRate==null||$scope.refundRate==undefined){
            $("#refundRate").focus();
        }else if($scope.storeAge==''||$scope.storeAge==null||$scope.storeAge==undefined){
            $("#storeAge").focus();
        }else if($scope.dsrDescribe==''||$scope.dsrDescribe==null||$scope.dsrDescribe==undefined){
            $("#dsrDescribe").focus();
        }else if($scope.dsrDescribeAverage==''||$scope.dsrDescribeAverage==null||$scope.dsrDescribeAverage==undefined){
            $("#dsrDescribeAverage").focus();
        }else if($scope.drsServiceQuality==''||$scope.drsServiceQuality==null||$scope.drsServiceQuality==undefined){
            $("#drsServiceQuality").focus();
        }else if($scope.drsServiceQualityAverage==''||$scope.drsServiceQualityAverage==null||$scope.drsServiceQualityAverage==undefined){
            $("#drsServiceQualityAverage").focus();
        }else if($scope.drsLogisticsQuality==''||$scope.drsLogisticsQuality==null||$scope.drsLogisticsQuality==undefined){
            $("#drsLogisticsQuality").focus();
        }else if($scope.drsLogisticsQualityAverage==''||$scope.drsLogisticsQualityAverage==null||$scope.drsLogisticsQualityAverage==undefined){
            $("#drsLogisticsQualityAverage").focus();
        }else if($scope.disputeRate==''||$scope.disputeRate==null||$scope.disputeRate==undefined){
            $("#disputeRate").focus();
        }else if($scope.disputeRateAverage==''||$scope.disputeRateAverage==null||$scope.disputeRateAverage==undefined){
            $("#disputeRateAverage").focus();
        }else if($scope.punishPoints==''||$scope.punishPoints==null||$scope.punishPoints==undefined){
            $("#punishPoints").focus();
        }else if($scope.unitPriceYearOnYear==''||$scope.unitPriceYearOnYear==null||$scope.unitPriceYearOnYear==undefined){
            $("#unitPriceYearOnYear").focus();
        }else if($scope.ratioOfPaidTraffic==''||$scope.ratioOfPaidTraffic==null||$scope.ratioOfPaidTraffic==undefined){
            $("#ratioOfPaidTraffic").focus();
        }else if($scope.salesYearOnYear==''||$scope.salesYearOnYear==null||$scope.salesYearOnYear==undefined){
            $("#salesYearOnYear").focus();
        }else if($scope.debtRatio==''||$scope.debtRatio==null||$scope.debtRatio==undefined){
            $("#debtRatio").focus();
        }else if($scope.riskRating==''||$scope.riskRating==null||$scope.riskRating==undefined){
            $("#riskRating").focus();
        }else if($scope.lineOfCredit==''||$scope.lineOfCredit==null||$scope.lineOfCredit==undefined){
            $("#lineOfCredit").focus();
        }else if($scope.loanPayTerm==''||$scope.loanPayTerm==null||$scope.loanPayTerm==undefined){
            $("#loanPayTerm").focus();
        }else if($scope.repaymentMethod==''||$scope.repaymentMethod==null||$scope.repaymentMethod==undefined){
            $("#repaymentMethod").focus();
        }else{
            var pa={
                applyCode:$scope.orderCode, //贷款编号
                sellerNick:$scope.sellerNick, //卖家昵称
                companyName:$scope.companyName,//企业名称
                businessLicense:$scope.businessLicense,//营业执照编号
                organizationCode:$scope.organizationCode,//企业组织代码证编号
                pbCreditCode:$scope.pbCreditCode,//企业中征码
                companyIndustry:$scope.companyIndustry,//企业所属行业
                companySize:$scope.companySize,//企业规模
                companyLinkman:$scope.companyLinkman,//企业联系人姓名
                companyLinkmanTel:$scope.companyLinkmanTel,//企业联系人手机号码
                provinceSN:$scope.provinceSN,//隶属省份
                cityCode:$scope.cityCode,//隶属城市
                companyAddress:$scope.companyAddress,//企业通讯地址
                corporateType:$scope.corporateType,//对公客户分类
//                legalName:$scope.legalName,//法人姓名
                registeredCapital:$scope.registeredCapital*10000,//注册资金
                name:$scope.name,//实际控制人姓名
                idcard:$scope.idcard,//实际控制人身份证号
                mobile:$scope.mobile,//实际控制人手机号码
                documentsAging:$scope.documentsAging,//证件有效日期
                postalCode:$scope.postalCode,//邮编
                education:$scope.education,//学历
                shopUrl:$scope.shopUrl,//店铺Url
                annualSalesVolume:$scope.annualSalesVolume,//年销售额
                clickFarmingRate:$scope.clickFarmingRate,//刷单率
                refundRate:$scope.refundRate,//退款率
                storeAge:$scope.storeAge,//店铺经营年限
                dsrDescribe:$scope.dsrDescribe,//描述相符
                dsrDescribeAverage:$scope.dsrDescribeAverage,//描述相符行业平均
                drsServiceQuality:$scope.drsServiceQuality,//服务质量
                drsServiceQualityAverage:$scope.drsServiceQualityAverage,//服务质量行业平均
                drsLogisticsQuality:$scope.drsLogisticsQuality,//物流质量
                drsLogisticsQualityAverage:$scope.drsLogisticsQualityAverage,//物流质量行业平均
                disputeRate:$scope.disputeRate,//30天纠纷率
                disputeRateAverage:$scope.disputeRateAverage,//30天纠纷率行业平均
                punishPoints:$scope.punishPoints,//处罚扣分
                unitPriceYearOnYear:$scope.unitPriceYearOnYear,//客单价同比
                ratioOfPaidTraffic:$scope.ratioOfPaidTraffic,//付费流量占比
                salesYearOnYear:$scope.salesYearOnYear,//销售额（扣除退款）同比
                debtRatio:$scope.debtRatio,//负债经营率
                riskRating:$scope.riskRating,//建议风险等级
                lineOfCredit:$scope.lineOfCredit,//建议贷款额度
                loanTerm:$scope.loanPayTerm,//建议贷款期限
                repaymentMethod:$scope.repaymentMethod,//建议还款方式
                yssjStatus:$scope.yssjStatus,//原始数据文件状态
                zjlStatus:$scope.zjlStatus//证据链文件状态
            };
            console.log(pa);
            ToBankLoanOrderDetailService.saveConfirmCcbPush(pa,function(data){
                if(data.status==0&&data.obj!=''&&data.obj!=null&&data.obj!=undefined){
                    popupDiv('SaveSuccess');
                    $(".Message").html(data.message);
                }else{
                    popupDiv('SaveSuccess');
                    $(".Message").html(data.message);
                }
            }, function(err){
                console.log(err)
            });
        }
    };
    /*推送 end*/
    $scope.$watch('paginationConf4.currentPage + paginationConf4.itemsPerPage',$scope.queryTaobaoInfos);
});