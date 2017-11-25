/**
 * Created by caolongping on 2016/5/18.
 */
//var app = angular.module('myApp', ['tm.pagination','ngResource']);
consumerToBankApp.controller('consumerToBankOrderDetailController',function ($scope,$cookies,$stateParams,againstCheatReportService,consumerAuditDataCheckService,$resource, $location, $state, UrlService,ToBankOrderDetailService,$http,$filter,phoneLabelService) {
    $scope.WebURL=UrlService.getUrl('xfdbaodan');
    $scope.WebURLTaoBao=UrlService.getUrl('baseConfigTaoBaoInfo');

    /*图片放大旋转 start*/
//    $scope.bgUrl='http://daianla-oss.oss-cn-hangzhou.aliyuncs.com/data/2017/4/12/18802005744/19a575a3-6d44-4923-bd5c-8ccf67b46cb4.jpg?Expires=1491986653&OSSAccessKeyId=azxgzXIKvfOkP2Az&Signature=ERm/0rY3gJgJb%2BF0Y8Yz9BeewDo%3D';
//    $scope.fgUrl='http://daianla-oss.oss-cn-hangzhou.aliyuncs.com/data/2017/4/12/18802005744/3afa5a74-7123-40a4-bff8-c28f8f48ae37.jpg?Expires=1491986653&OSSAccessKeyId=azxgzXIKvfOkP2Az&Signature=yJEL6CBjCWJHJIkv2rEpO9VEIhs%3D';
    $scope.imgShow=function(imgUrl){
        $scope.chooled=imgUrl;
        popupDiv("imgShow");
        $(".imgShow").css('top','40px');
    };
    /*图片放大旋转 end*/
    /*展开和关闭 start*/
    $scope.isShow = true;
    $scope.showTitle='收起认证列表 -';
    $scope.onPersonToggle = function() {
        $scope.isShow = !$scope.isShow;
        if($scope.isShow==true){
            $scope.showTitle='收起认证列表 -';
        }else{
            $scope.showTitle='展开认证列表 +';
        }
    };
    $scope.isShowBank = true;
    $scope.showTitleBank='收起工资流水明细 -';
    $scope.onBankListToggle = function() {
        $scope.isShowBank = !$scope.isShowBank;
        if($scope.isShowBank==true){
            $scope.showTitleBank='收起工资流水明细 -';
        }else{
            $scope.showTitleBank='展开工资流水明细 +';
        }
    };
    $scope.isShowContact = true;
    $scope.showTitleContact='收起运营商数据 -';
    $scope.onContactListToggle = function() {
        $scope.isShowContact = !$scope.isShowContact;
        if($scope.isShowContact==true){
            $scope.showTitleContact='收起运营商数据 -';
        }else{
            $scope.showTitleContact='展开运营商数据 +';
        }
    };

    $scope.isShowSecret = true;
    $scope.showTitleSecret='收起隐私数据 -';
    $scope.onSecretListToggle = function() {
        $scope.isShowSecret = !$scope.isShowSecret;
        if($scope.isShowSecret==true){
            $scope.showTitleSecret='收起隐私数据 -';
        }else{
            $scope.showTitleSecret='展开隐私数据 +';
        }
    };
    /*展开和关闭 end*/
    $scope.curHandleOrder = ToBankOrderDetailService.getCurHandleOrder();
//    $scope.orderStatus=$scope.curHandleOrder.orderStatus;
    // 用户身份证
    if($scope.curHandleOrder.idcardNum==''||$scope.curHandleOrder.idcardNum==null||$scope.curHandleOrder.idcardNum==undefined){
        $scope.idCard='';
    }else{
        $scope.idCard=$scope.curHandleOrder.idcardNum.substring(0,11)+'****'+$scope.curHandleOrder.idcardNum.substring(14,18);
    }
    // 用户名
    if($scope.curHandleOrder.userName==''||$scope.curHandleOrder.userName==null||$scope.curHandleOrder.userName==undefined){
        $scope.userName='';
    }else{
        $scope.userName=$scope.curHandleOrder.userName;
    }
    // 申请借款日期
    if($scope.curHandleOrder.created==''||$scope.curHandleOrder.created==null||$scope.curHandleOrder.created==undefined){
        $scope.created='';
    }else{
        $scope.created=$scope.curHandleOrder.created;
    }
    // 申请贷款金额
    if($scope.curHandleOrder.applyAmount==''||$scope.curHandleOrder.applyAmount==null||$scope.curHandleOrder.applyAmount==undefined){
        $scope.applyAmount='';
    }else{
        $scope.applyAmount=$scope.curHandleOrder.applyAmount;
    }
    // 申请借款期限
    if($scope.curHandleOrder.applyTerm==''||$scope.curHandleOrder.applyTerm==null||$scope.curHandleOrder.applyTerm==undefined){
        $scope.applyTerm='';
    }else{
        $scope.applyTerm=$scope.curHandleOrder.applyTerm;
    }
    //userID
    if($scope.curHandleOrder.userId!=null&&$scope.curHandleOrder.userId!=''&&$scope.curHandleOrder.userId!=undefined){
        $cookies.put('userId',$scope.curHandleOrder.userId);
        $scope.userId=$cookies.get('userId');
    }else{
        $scope.userId=$cookies.get('userId');
    }
    $scope.hideData=$stateParams.hideData;
    $scope.hideReset=$stateParams.hideReset;
    if($scope.orderStatus==4||$scope.orderStatus==5){
        $scope.hideResetSure=false;
    }else{
        $scope.hideResetSure=true;
    }
//    alert($scope.curHandleOrder);
    console.log($scope.curHandleOrder);

    /*查询用户基本信息 start*/
    $scope.queryUserBases = ToBankOrderDetailService.queryUserBase($scope.userId,function(data){
        if(data.status==0){
            if(data.obj!=''&&data.obj!=null&&data.obj!=undefined){
                //TODO-业务逻辑
                $scope.userBaseInfo=data.obj;
                console.log($scope.userBaseInfo);
            }else{
                //popupDiv('SaveSuccess');
                //$('.SaveSuccess .Message').html(data.message);
            }
        }else{
            console.log(data.msg);
        }
    },function(err){
        console.log(err);
    });
    /*查询用户基本信息 end*/

    /*查询认证项列表 start*/
    $scope.AuditListInfo = ToBankOrderDetailService.queryAuditListInfo($scope.userId,function(data){
        if(data.status==0){
            if(data.obj!=''&&data.obj!=null&&data.obj!=undefined){
                //TODO-业务逻辑
                $scope.AuditListInfos=data.obj;
                $scope.AuditListDetails=[];
                data.obj.forEach(function(item){
                    if(item.detailInfos!=null&&item.detailInfos!=''&&item.detailInfos!=undefined){
                        $scope.AuditListDetails.push(item.detailInfos);
                    }
                });
                console.log($scope.AuditListInfos);
                console.log($scope.AuditListDetails);
            }else{
                //popupDiv('SaveSuccess');
                //$('.SaveSuccess .Message').html(data.message);
            }
        }else{
            console.log(data.msg);
        }
    },function(err){
        console.log(err);
    });

    /*查询认证项列表 end*/
    /*查询认证项详情 start*/

    /*淘宝详情 start*/
    $scope.paginationConf4 = {
        currentPage: 1,
        itemsPerPage: 5
    };
    $scope.queryTaobaoInfos=function(){
        var pa={
            userId:$scope.userId
        };
        var pa1={
            userId:$scope.userId,
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
    /*淘宝详情 end*/
    /*工资流水概要 start*/
    $scope.paginationConf6 = {
        currentPage: 1,
        itemsPerPage: 15
    };
    $scope.querySalaryAb=function(){
        $scope.querySalaryAbs= phoneLabelService.salaryWateringAb(
            {
                userId:$scope.userId,
                currentPage: $scope.paginationConf6.currentPage,
                itemsPerPage: $scope.paginationConf6.itemsPerPage
            },function(data){
                if(data.status==0&&data.obj.list!=''&&data.obj.list!=null&&data.obj.list!=undefined){
                    if(data.totalCount>=1){
                        $scope.salaryAbs=data.obj;
                        $scope.salaryAbstractS=data.obj.list;
                        $scope.cardName=data.obj.list[0].nameOnCard;
                        console.log($scope.salaryAbs);
                        $scope.paginationConf6.totalItems = data.obj.total;
                    }else{
                        $scope.paginationConf6.totalItems = 0;
                        $scope.salaryAbstractS = '';
                    }
//                    popupDiv('SaveSuccess');
//                    $('.SaveSuccess .Message').html(response.message);
                }else{
                    $scope.salaryAbstractS='';
//                popupDiv('SaveSuccess');
//                $('.SaveSuccess .Message').html(response.message);
                }
            }, function(err){
                console.log(err)
            });
    };
    /*工资流水概要 end*/
    /**************************工资流水详情 start*/
    $scope.paginationConf5 = {
        currentPage: 1,
        itemsPerPage: 15
    };
    /*银行卡 start*/
    $scope.queryManyCardList = function(){
        $scope.queryManyCard = phoneLabelService.queryBankcard($scope.userId, function(data) {
            if (data.status == 0) {
                if (data.obj != '' && data.obj != null && data.obj != undefined) {
                    //TODO-业务逻辑
                    $scope.queryBankCardList = data.obj;
                    $scope.bankCardChoose=$scope.queryBankCardList[0];
                    $scope.querySalaryDetailss();
                    console.log($scope.queryBankCardList[0]);

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
    $scope.queryManyCardList();
    /*银行卡 end*/
    $scope.querySalarydt = function () {
        $scope.StarDates = $filter('date')($scope.createdDate, 'yyyy-MM-dd');
        $scope.EndDates = $filter('date')($scope.endDate, 'yyyy-MM-dd');
        $scope.transtionTypes = $("#transtionType").val();
        if ($scope.transtionType == "" || $scope.transtionType == null || $scope.transtionType == undefined) {
            $scope.transtionType = undefined;

        }
        if ($scope.liabilities == "" || $scope.liabilities == null || $scope.liabilities == undefined) {
            $scope.liabilities = undefined;
        }
        if ($scope.categoryS == "" || $scope.categoryS == null || $scope.categoryS == undefined) {
            $scope.categoryS = undefined;
        }
        $scope.querySalarydts = phoneLabelService.salaryWateringRun(
            {
                startTime: $scope.StarDates,
                endTime: $scope.EndDates,
                transactionType: $scope.transtionType,
                flag: $scope.liabilities,
                category: $scope.categoryS,
                cardNo: $scope.bankCardChoose,
                userId: $scope.userId,
                currentPage: $scope.paginationConf5.currentPage,
                itemsPerPage: $scope.paginationConf5.itemsPerPage
            }, function (data) {
                if (data.status == 0) {
                    if (data.obj.totalCount >= 1) {
                        $scope.basicSalary = data.obj;
                        $scope.salaryInfos = data.obj.list;
                        $scope.salaryInfoName = data.obj.list[0].name;
                        $scope.salaryInfoIdCard = data.obj.list[0].idcardNo;
                        console.log($scope.salaryInfos);
                        $scope.paginationConf5.totalItems = data.obj.totalCount;
                    } else {
                        $scope.paginationConf5.totalItems = 0;
                        $scope.salaryInfos = '';
                    }
//                    popupDiv('SaveSuccess');
//                    $('.SaveSuccess .Message').html(response.message);
                } else {
                    $scope.salaryInfos = '';
//                popupDiv('SaveSuccess');
//                $('.SaveSuccess .Message').html(response.message);
                }
            }, function (err) {
                console.log(err)
            });

    };
    $scope.$watch('paginationConf5.currentPage + paginationConf5.itemsPerPage',$scope.querySalarydt);
    /****************************工资流水详情 end*/
    /**************************工资流水详情 初始化 start*/
    $scope.querySalaryDetailss = function(){
        $scope.StarDates = $filter('date')($scope.createdDate, 'yyyy-MM-dd');
        $scope.EndDates = $filter('date')($scope.endDate, 'yyyy-MM-dd');
        $scope.transtionTypes = $("#transtionType").val();
        if ($scope.transtionType == "" || $scope.transtionType == null || $scope.transtionType == undefined) {
            $scope.transtionType = undefined;

        }
        if ($scope.liabilities == "" || $scope.liabilities == null || $scope.liabilities == undefined) {
            $scope.liabilities = undefined;
        }
        if ($scope.categoryS == "" || $scope.categoryS == null || $scope.categoryS == undefined) {
            $scope.categoryS = undefined;
        }
        $scope.querySalarydts = phoneLabelService.salaryWateringRun(
            {
                startTime: $scope.StarDates,
                endTime: $scope.EndDates,
                transactionType: $scope.transtionType,
                flag: $scope.liabilities,
                category: $scope.categoryS,
                cardNo: $scope.bankCardChoose,
                userId: $scope.curHandleOrder.userId,
                currentPage: $scope.paginationConf5.currentPage,
                itemsPerPage: $scope.paginationConf5.itemsPerPage
            }, function (data) {
                if (data.status == 0) {
                    if (data.obj.totalCount >= 1) {
                        $scope.basicSalary = data.obj;
                        $scope.salaryInfos = data.obj.list;
                        $scope.salaryInfoName = data.obj.list[0].name;
                        $scope.salaryInfoIdCard = data.obj.list[0].idcardNo;
                        console.log($scope.salaryInfos);
                        $scope.paginationConf5.totalItems = data.obj.totalCount;
                    } else {
                        $scope.paginationConf5.totalItems = 0;
                        $scope.salaryInfos = '';
                    }
                    //                    popupDiv('SaveSuccess');
                    //                    $('.SaveSuccess .Message').html(response.message);
                } else {
                    $scope.salaryInfos = '';
                    //                popupDiv('SaveSuccess');
                    //                $('.SaveSuccess .Message').html(response.message);
                }
            }, function (err) {
                console.log(err)
            });

    };
    /******************************工资流水详情 初始化 end*/
    $scope.$watch('paginationConf4.currentPage + paginationConf4.itemsPerPage',$scope.queryTaobaoInfos);

    $scope.$watch('paginationConf6.currentPage + paginationConf6.itemsPerPage',$scope.querySalaryAb);

    $scope.$watch('paginationConf5.currentPage + paginationConf5.itemsPerPage',$scope.querySalarydt);

    $scope.$watch('paginationConf5.currentPage + paginationConf5.itemsPerPage',$scope.querySalaryDetailss);
    /*查询认证项详情 end*/
    /*查询认证详情公用方法 start*/
    $scope.queryDetail=function(code){
        var pa={
            userId:$scope.userId,
            code:code
        };
        console.log(pa);
        $http.post($scope.WebURL+'order/queryAuthInfo',pa).success(function(response){
            if(response.status==0&&response.obj.obj!=''&&response.obj.obj!=null&&response.obj.obj!=undefined){
                if(code=='CONTACT'){
                    $scope[code+'s']=response.obj.obj;
                    console.log($scope[code]);
                }else{
                    $scope[code.split('.')[1]]=response.obj.obj;
                    console.log($scope[code.split('.')[1]]);
                }
            }else{
                //popupDiv('SaveSuccess');
                //$('.SaveSuccess .Message').html(response.obj.message+',更多详情请查看反欺诈报告');
            }
        }).error(function(response){
            console.log(response.msg);
        });
    };
    /*查询认证详情公用方法 end*/
    $scope.BASICDATA=true;
    $scope.queryDetail('USER.BASICDATA');
    $scope.onAuditDetail = function($event,res){
        //  	点击对应高亮显示
        $($event.target).parents('ul').siblings().find('li').removeClass('bg-gray')
        $($event.target).parents('li').addClass('bg-gray')
        $($event.target).parents('li').prevAll().addClass('bg-gray')

        if(res.itemCode=='TAOBAO.ACCOUNT'){
            popupDiv('TaoBaoInfoContainer');
            $scope.queryTaobaoInfos();
        }else if(res.itemCode.split('.')[0]=='PRIVACY'){
            document.getElementById('privateData').scrollIntoView();
        }


        $scope.auditDetailAll='';
        if(res.code=='USER.BASICDATA'){
            $scope.BASICDATA=!$scope.BASICDATA;
            if($scope.BASICDATA){
                $scope.queryDetail(res.code);
            }
        }else if(res.code=='USER.FAMILY'){
            $scope.FAMILY=!$scope.FAMILY;
            if($scope.FAMILY){
                $scope.queryDetail(res.code);
            }
        }else if(res.code=='CONTACT'){
            $scope.CONTACT=!$scope.CONTACT;
            if($scope.CONTACT){
                $scope.queryDetail(res.code);
            }
        }else if(res.code=='USER.JOB'){
            $scope.JOB=!$scope.JOB;
            if($scope.JOB){
                $scope.queryDetail(res.code);
            }
        }else if(res.code=='USER.IDENTITY'){
            $scope.IDENTITY=!$scope.IDENTITY;
            if($scope.IDENTITY){
                $scope.queryDetail(res.code);
            }
        }else if(res.itemCode=='TAOBAO.ACCOUNT'){
            $scope.queryTaobaoInfos();

        }else if(res.code=='SIGN.CONTRACT'){
            $scope.CONTRACT=!$scope.CONTRACT;
            if($scope.CONTRACT){
                $scope.queryDetail(res.code);
            }
        }else if(res.code=='SALARY.BILL'){
            $scope.BILL=!$scope.BILL;
            if($scope.BILL){
                $scope.querySalarydt();
                $scope.querySalaryAb();
                //if($scope.salaryInfos==''){
                //    popupDiv('SaveSuccess');
                //    $('.SaveSuccess .Message').html('处理成功无结果');
                //}
            }
        }else if(res.code=='USER.BANKCARD'){
            $scope.BANKCARD=!$scope.BANKCARD;
            if($scope.BANKCARD){
                $scope.queryDetail(res.code);
            }
        }else if(res.code=='USER.HOUSE'){
            $scope.HOUSE=!$scope.HOUSE;
            if($scope.HOUSE){
                $scope.queryDetail(res.code);
            }
        }else if(res.code=='PRIVACY.LOCATION'){
            $scope.LOCATION=!$scope.LOCATION;
            if($scope.LOCATION){
                $scope.queryDetail(res.code);
            }
        }else if(res.code=='PRIVACY.APPLIST'){
            $scope.APPLIST=!$scope.APPLIST;
            if($scope.APPLIST){
                $scope.queryDetail(res.code);
            }
        }else if(res.code=='PRIVACY.DIRLIST'){
            $scope.DIRLIST=!$scope.DIRLIST;
            if($scope.DIRLIST){
                $scope.queryDetail(res.code);
            }
        }else if(res.code=='PRIVACY.DEVICE'){
            $scope.DEVICE=!$scope.DEVICE;
            if($scope.DEVICE){
                $scope.queryDetail(res.code);
            }
        }else if(res.code=='PRIVACY.SMS'){
            $scope.SMS=!$scope.SMS;
            if($scope.SMS){
                $scope.queryDetail(res.code);
            }
        }else if(res.code=='PRIVACY.CONTACT'){
            $scope.CONTACTs=!$scope.CONTACTs;
            if($scope.CONTACTs){
                $scope.queryDetail(res.code);
            }
        }else if(res.code=='PRIVACY.CALLRECORD'){
            $scope.CALLRECORD=!$scope.CALLRECORD;
            if($scope.CALLRECORD){
                $scope.queryDetail(res.code);
            }
        }else if(res.code=='PRIVACY.BROWSERHISTORY'){
            $scope.BROWSERHISTORY=!$scope.BROWSERHISTORY;
            if($scope.BROWSERHISTORY){
                $scope.queryDetail(res.code);
            }
        }else if(res.code=='HOUSE.MORTGAGE'){
            $scope.MORTGAGE=!$scope.MORTGAGE;
            if($scope.MORTGAGE){
                $scope.queryDetail(res.code);
            }
        }else if(res.code=='HOUSE.FULL'){
            $scope.FULL=!$scope.FULL;
            if($scope.FULL){
                $scope.queryDetail(res.code);
            }
        }else if(res.code=='TELECOM.OPERATORS'){
            $scope.OPERATORS=!$scope.OPERATORS;
            if($scope.OPERATORS){
                $scope.queryDetail(res.code);
            }
        }
    };
    /* 查看客户人脸相关信息 viewIdCardInformation start*/
    $scope.viewIdCardInformation=function () {
        ToBankOrderDetailService.viewIdCardInformation({
            userId:$scope.userId
        }, function(response){
            if(response.status==0&&response.obj!=null){
                $scope.IdCardInfos=response.obj[0];
                console.log($scope.IdCardInfos)
            }else{
                console.log(response.message);
//                popupDiv('SaveSuccess');
//                $(".SaveSuccess .Message").html(response.message);
            }
        });
    };
    $scope.viewIdCardInformation();
    $scope.faceStatus=0;
    $scope.submitFaceStatus=function(){
        var pa={
            userId:$scope.userId,
            status:$scope.faceStatus
        };
        console.log(pa);
        $http.post($scope.WebURL+'web/loan/faceRecognition',pa).success(function(response){
            if(response.status==0){
                popupDiv('SaveSuccess');
                $('.SaveSuccess .Message').html(response.message);
            }else{
                popupDiv('SaveSuccess');
                $('.SaveSuccess .Message').html(response.message);
            }
        }).error(function(response){
            console.log(response.message);
        })
    };
    /* 查看客户人脸相关信息 viewIdCardInformation end*/
    /*查询所有敏感词 start*/
    var keyWords='';
    $scope.queryAllwords=function () {
        phoneLabelService.queryPrivateDataKeyWords({}, function(response){
            if(response.status==0&&response.obj!=null){
                $scope.orderList=response.obj;
                response.obj.forEach(function(items){
                    keyWords+=items.msg+'-';
                    console.log(items.msg);
                });
            }else{
//                popupDiv('SaveSuccess');
//                $(".SaveSuccess .Message").html(response.msg);
            }
//            console.log(keyWords.substring(0,keyWords.length-1));
        });
    };
    $scope.queryAllwords();
    /*查询所有敏感词 end*/
    /*查看隐私数据 start*/

    /* 初始化隐私数据 查询设备信息 start*/
    $scope.queryEquiP=function(){
        $scope.aganistcheck = phoneLabelService.queryEquipment({
                userId:$scope.userId
            },
            function(data){
                if(data.status==0){
                    $scope.equipData = data.obj;

                    if(data.obj!=""||data.obj!=null||data.obj!=undefined){
                        $scope.equipCode = data.obj[0].outerId;
                        /*******初始化 start*****************/


                        $scope.inintPriacyData=function(){

                            $scope.equipId = [];
                            $scope.equipId[0]= $scope.equipCode;

                            /*查询联系人记录 start*/
                            $scope.queryContactInfo=function(){

                                $scope.praicyphoneLabelkeyWords = [];

                                var phoneLabelOrders ="";

                                $('input[name="praicyphoneLabelKey"]:checked').each(function(){
                                    var phoneLabelKeyId=$(this).val();
                                    phoneLabelOrders +=','+phoneLabelKeyId;
                                });
                                phoneLabelOrders= phoneLabelOrders.substr(1, phoneLabelOrders.length);
                                $scope.praicyphoneLabelkeyWords= phoneLabelOrders.split(",");
                                if($scope.praicyphoneLabelkeyWords==""||$scope.praicyphoneLabelkeyWords==null||$scope.praicyphoneLabelkeyWords==undefined){
                                    $scope.praicyphoneLabelkeyWords=undefined;
                                }
                                $scope.filterContractRecords= phoneLabelService.queryPrivacyContact(
                                    {
                                        userId:$scope.userId,
                                        outerIds:$scope.equipId,
                                        type:"phoneBook",
                                        phone:$scope.contactPersonNumber,
                                        phoneTitles:$scope.praicyphoneLabelkeyWords,
                                        name:$scope.contactPersonName,
                                        currentPage: $scope.paginationConf.currentPage,
                                        itemsPerPage: $scope.paginationConf.itemsPerPage
                                    },function(data){
                                        if(data.status==0){
                                            if(data.obj!=null){
                                                //TODO-业务逻辑
                                                if(data.obj.totalCount>=1){
                                                    $scope.appcontractPeopleList = data.obj.list;
                                                    $scope.paginationConf.totalItems = data.obj.totalCount;
                                                }else{
                                                    $scope.paginationConf.totalItems = 0;
                                                    $scope.appcontractPeopleList = '';
                                                }
                                                //                    console.log(data.obj[0]);
                                            }else{
                                                console.log("未获取到数据");
                                            }
                                        }else{
                                            console.log("调用接口失败！");
                                        }
                                    }, function(err){
                                        console.log(err)
                                    });
                            };
                            /*查询联系人记录 end*/

                            /*查询通话记录信息 start*/
                            $scope.queryContactRecordInfo=function(){
                                $scope.filterCallRecords= phoneLabelService.queryPrivacyCallRecord(
                                    {
                                        userId:$scope.userId,
                                        outerIds:$scope.equipId,
                                        type:"callRecord",
                                        currentPage: $scope.paginationConf1.currentPage,
                                        itemsPerPage: $scope.paginationConf1.itemsPerPage
                                    },function(data){
                                        if(data.status==0){
                                            if(data.obj!=null){
                                                //TODO-业务逻辑
                                                if(data.obj.totalPage>=1){
                                                    $scope.appuserCallRecordList = data.obj.list;
                                                    $scope.paginationConf1.totalItems = data.obj.totalCount;
                                                }else{
                                                    $scope.paginationConf1.totalItems = 0;
                                                    $scope.appuserCallRecordList = '';
                                                }
                                                //                    console.log(data.obj[0]);
                                            }else{
                                                console.log("未获取到数据");
                                            }
                                        }else{
                                            console.log("调用接口失败！");
                                        }
                                    }, function(err){
                                        console.log(err)
                                    });
                            };
                            /*查询通话记录信息 end*/

                            /*查询短信记录信息 start*/
                            $scope.queryMessageRecordInfo=function(){
                                $scope.keyWords = [];
                                var orders = "";
                                $('input[name="che"]:checked').each(function(){
                                    var sfruit=$(this).val();
                                    orders +=','+sfruit;
                                });
                                orders=orders.substr(1,orders.length);
                                $scope.keyWords= orders.split(",");


                                $scope.phoneLabelkeyWords = [];
                                var priacyphoneLaberOrders ="";
                                $('input[name="phoneLabelKey"]:checked').each(function(){
                                    var  priacyphoneLabelKeyId=$(this).val();
                                    priacyphoneLaberOrders +=','+priacyphoneLabelKeyId;
                                });
                                priacyphoneLaberOrders=priacyphoneLaberOrders.substr(1,priacyphoneLaberOrders.length);
//                                $scope.phoneLabelkeyWords.push(priacyphoneLaberOrders);
                                $scope.phoneLabelkeyWords= priacyphoneLaberOrders.split(",");
                                if($scope.phoneLabelkeyWords==""||$scope.phoneLabelkeyWords==null||$scope.phoneLabelkeyWords==undefined){
                                    $scope.phoneLabelkeyWords=undefined;
                                }
                                if($scope.keyWords==""||$scope.keyWords==null||$scope.keyWords==undefined){
                                    $scope.keyWords=undefined;
                                }
                                $scope.filterMessageRecords= phoneLabelService.queryPrivacySms(
                                    {
                                        userId:$scope.userId,
                                        outerIds:$scope.equipId,
                                        type:"sms",
                                        smsKeys:$scope.keyWords,
                                        phone:$scope.msgPhone,
                                        phoneTitles:$scope.phoneLabelkeyWords,
                                        currentPage: $scope.paginationConf2.currentPage,
                                        itemsPerPage: $scope.paginationConf2.itemsPerPage
                                    },function(data){
                                        if(data.status==0){
                                            if(data.obj!=null){
                                                //TODO-业务逻辑
                                                if(data.obj.totalCount>=1){
                                                    $scope.appuserMessageRecordList = data.obj.list;
                                                    console.log($scope.appuserMessageRecordList)
                                                    console.log(data.obj.list)
//                                                    data.obj.list.forEach(function(res){
////                                        res.smsCreateTime=new Date(parseInt(res.smsCreateTime)).toLocaleString().split(' ')[0];
//                                                        $scope.appuserMessageRecordList.push(res);
//                                                    });
                                                    $scope.paginationConf2.totalItems = data.obj.totalCount;
                                                }else{
                                                    $scope.paginationConf2.totalItems = 0;
                                                    $scope.appuserMessageRecordList = '';
                                                }
                                                //                    console.log(data.obj[0]);
                                            }else{
                                                console.log("未获取到数据");
                                            }
                                        }else{
                                            console.log("调用接口失败！");
                                        }
                                    }, function(err){
                                        console.log(err)
                                    });
                            };
                            /*查询短信记录信息 end*/

                            /*查询地理位置 start*/
                            $scope.queryLocationRecordInfo=function(){
                                $scope.filterLocationRecords= phoneLabelService.queryPrivacyGPS(
                                    {
                                        userId:$scope.userId,
                                        outerIds:$scope.equipId,
                                        type:"address",
                                        resoures:["MOBILE"],
                                        currentPage: $scope.paginationConf3.currentPage,
                                        itemsPerPage: $scope.paginationConf3.itemsPerPage
                                    },function(data){
                                        if(data.status==0){
                                            if(data.obj!=null){
                                                //TODO-业务逻辑
                                                if(data.obj.totalCount>=1){
                                                    $scope.appLocationList = data.obj.list;
                                                    $scope.paginationConf3.totalItems = data.obj.totalCount;
                                                }else{
                                                    $scope.paginationConf3.totalItems = 0;
                                                    $scope.appLocationList = '';
                                                }
                                                //                    console.log(data.obj[0]);
                                            }else{
                                                console.log("未获取到数据");
                                            }
                                        }else{
                                            console.log("调用接口失败！");
                                        }
                                    }, function(err){
                                        console.log(err)
                                    });
                            };
                            /*查询地理位置 end*/
                            /*查询设备信息 start*/
                            $scope.queryDeviceRecordInfo=function(){
                                $scope.filterDeviceRecords= phoneLabelService.queryPrivacyDevice(
                                    {
                                        userId:$scope.userId,
                                        outerIds:$scope.equipId,
                                        type:"device",
                                        currentPage: $scope.paginationConf8.currentPage,
                                        itemsPerPage: $scope.paginationConf8.itemsPerPage
                                    },function(data){
                                        if(data.status==0){
                                            if(data.obj!=null){
                                                //TODO-业务逻辑
                                                if(data.obj.totalCount>=1){
                                                    $scope.appDeviceList = data.obj.list;
                                                    $scope.paginationConf8.totalItems = data.obj.totalCount;
                                                }else{
                                                    $scope.paginationConf8.totalItems = 0;
                                                    $scope.appDeviceList = '';
                                                }
                                                //                    console.log(data.obj[0]);
                                            }else{
                                                console.log("未获取到数据");
                                            }
                                        }else{
                                            console.log("调用接口失败！");
                                        }
                                    }, function(err){
                                        console.log(err)
                                    });
                            };
                            /*查询设备信息 end*/
                            /***************************************************************
                             当页码和页面记录数发生变化时监控后台查询
                             如果把currentPage和itemsPerPage分开监控的话则会触发两次后台事件。
                             ***************************************************************/
                            $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage',$scope.queryContactInfo);
                            $scope.$watch('paginationConf1.currentPage + paginationConf1.itemsPerPage',$scope.queryContactRecordInfo);
                            $scope.$watch('paginationConf2.currentPage + paginationConf2.itemsPerPage',$scope.queryMessageRecordInfo);
                            $scope.$watch('paginationConf3.currentPage + paginationConf3.itemsPerPage',$scope.queryLocationRecordInfo);
                            $scope.$watch('paginationConf8.currentPage + paginationConf8.itemsPerPage',$scope.queryDeviceRecordInfo);

                        };
                        $scope.inintPriacyData();
                        /************初始化 end**************/
                    }




                }else{
//                    popupDiv('SaveSuccess');
//                    $(".SaveSuccess .Message").html(data.msg);
                    console.log(data.message);
                }
            }, function(err){
                console.log(err)
            });

    };
    $scope.queryEquiP();
    /*初始化隐私数据 查询设备信息 end*/




    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 5
    };
    $scope.paginationConf1 = {
        currentPage: 1,
        itemsPerPage: 5
    };
    $scope.paginationConf2 = {
        currentPage: 1,
        itemsPerPage: 5
    };
    $scope.paginationConf3 = {
        currentPage: 1,
        itemsPerPage: 5
    };
    $scope.paginationConf8 = {
        currentPage: 1,
        itemsPerPage: 5
    };

    $scope.dataChecked=function(equipCode){

        $scope.equipId = [];
        $scope.equipId[0]= equipCode;

        /*查询联系人记录 start*/
        $scope.queryContactInfo=function(){

            $scope.praicyphoneLabelkeyWords = [];

            var phoneLabelOrders ="";

            $('input[name="praicyphoneLabelKey"]:checked').each(function(){

                var phoneLabelKeyId=$(this).val();
                phoneLabelOrders +=','+phoneLabelKeyId;
            });
            phoneLabelOrders=phoneLabelOrders.substr(1,phoneLabelOrders.length);
            $scope.praicyphoneLabelkeyWords= phoneLabelOrders.split(",");
//            $scope.praicyphoneLabelkeyWords.push(phoneLabelOrders);
            if($scope.praicyphoneLabelkeyWords==""||$scope.praicyphoneLabelkeyWords==null||$scope.praicyphoneLabelkeyWords==undefined){
                $scope.praicyphoneLabelkeyWords=undefined;
            }
            $scope.filterContractRecords= phoneLabelService.queryPrivacyContact(
                {
                    userId:$scope.userId,
                    outerIds:$scope.equipId,
                    type:"phoneBook",
                    phone:$scope.contactPersonNumber,
                    phoneTitles:$scope.praicyphoneLabelkeyWords,
                    name:$scope.contactPersonName,
                    currentPage: $scope.paginationConf.currentPage,
                    itemsPerPage: $scope.paginationConf.itemsPerPage
                },function(data){
                    if(data.status==0){
                        if(data.obj!=null){
                            //TODO-业务逻辑
                            if(data.obj.totalCount>=1){
                                $scope.appcontractPeopleList = data.obj.list;
                                $scope.paginationConf.totalItems = data.obj.totalCount;
                            }else{
                                $scope.paginationConf.totalItems = 0;
                                $scope.appcontractPeopleList = '';
                            }
                            //                    console.log(data.obj[0]);
                        }else{
                            console.log("未获取到数据");
                        }
                    }else{
                        console.log("调用接口失败！");
                    }
                }, function(err){
                    console.log(err)
                });
        };
        /*查询联系人记录 end*/

        /*查询通话记录信息 start*/
        $scope.queryContactRecordInfo=function(){
            $scope.filterCallRecords= phoneLabelService.queryPrivacyCallRecord(
                {
                    userId:$scope.userId,
                    outerIds:$scope.equipId,
                    type:"callRecord",
                    currentPage: $scope.paginationConf1.currentPage,
                    itemsPerPage: $scope.paginationConf1.itemsPerPage
                },function(data){
                    if(data.status==0){
                        if(data.obj!=null){
                            //TODO-业务逻辑
                            if(data.obj.totalPage>=1){
                                $scope.appuserCallRecordList = data.obj.list;
                                $scope.paginationConf1.totalItems = data.obj.totalCount;
                            }else{
                                $scope.paginationConf1.totalItems = 0;
                                $scope.appuserCallRecordList = '';
                            }
                            //                    console.log(data.obj[0]);
                        }else{
                            console.log("未获取到数据");
                        }
                    }else{
                        console.log("调用接口失败！");
                    }
                }, function(err){
                    console.log(err)
                });
        };
        /*查询通话记录信息 end*/

        /*查询短信记录信息 start*/
        $scope.queryMessageRecordInfo=function(){
            $scope.keyWords = [];
            var orders = "";
            $('input[name="che"]:checked').each(function(){
                var sfruit=$(this).val();
                orders +=','+sfruit;
            });
            orders=orders.substr(1,orders.length);

            $scope.keyWords= orders.split(",");
            $scope.phoneLabelkeyWords = [];
            var priacyphoneLaberOrders ="";
            $('input[name="phoneLabelKey"]:checked').each(function(){
                var  priacyphoneLabelKeyId=$(this).val();
                priacyphoneLaberOrders +=','+priacyphoneLabelKeyId;
            });
            priacyphoneLaberOrders=priacyphoneLaberOrders.substr(1,priacyphoneLaberOrders.length);

            $scope.phoneLabelkeyWords= priacyphoneLaberOrders.split(",");
            if($scope.phoneLabelkeyWords==""||$scope.phoneLabelkeyWords==null||$scope.phoneLabelkeyWords==undefined){
                $scope.phoneLabelkeyWords=undefined;
            }
            if($scope.keyWords==""||$scope.keyWords==null||$scope.keyWords==undefined){
                $scope.keyWords=undefined;
            }
            $scope.filterMessageRecords= phoneLabelService.queryPrivacySms(
                {
                    userId:$scope.userId,
                    outerIds:$scope.equipId,
                    type:"sms",
                    smsKeys:$scope.keyWords,
                    phone:$scope.msgPhone,
                    phoneTitles:$scope.phoneLabelkeyWords,
                    currentPage: $scope.paginationConf2.currentPage,
                    itemsPerPage: $scope.paginationConf2.itemsPerPage
                },function(data){
                    if(data.status==0){
                        if(data.obj!=null){
                            //TODO-业务逻辑
                            if(data.obj.totalCount>=1){
                                $scope.appuserMessageRecordList = data.obj.list;
                                console.log($scope.appuserMessageRecordList)
                                console.log(data.obj.list)
//                                $scope.appuserMessageRecordList = [];
//                                data.obj.list.forEach(function(res){
////                                        res.smsCreateTime=new Date(parseInt(res.smsCreateTime)).toLocaleString().split(' ')[0];
//                                    $scope.appuserMessageRecordList.push(res);
//                                });
                                $scope.paginationConf2.totalItems = data.obj.totalCount;
                            }else{
                                $scope.paginationConf2.totalItems = 0;
                                $scope.appuserMessageRecordList = '';
                            }
                            //                    console.log(data.obj[0]);
                        }else{
                            console.log("未获取到数据");
                        }
                    }else{
                        console.log("调用接口失败！");
                    }
                }, function(err){
                    console.log(err)
                });
        };
        /*查询短信记录信息 end*/

        /*查询地理位置 start*/
        $scope.queryLocationRecordInfo=function(){
            $scope.filterLocationRecords= phoneLabelService.queryPrivacyGPS(
                {
                    userId:$scope.userId,
                    outerIds:$scope.equipId,
                    type:"address",
                    resoures:["MOBILE"],
                    currentPage: $scope.paginationConf3.currentPage,
                    itemsPerPage: $scope.paginationConf3.itemsPerPage
                },function(data){
                    if(data.status==0){
                        if(data.obj!=null){
                            //TODO-业务逻辑
                            if(data.obj.totalCount>=1){
                                $scope.appLocationList = data.obj.list;
                                $scope.paginationConf3.totalItems = data.obj.totalCount;
                            }else{
                                $scope.paginationConf3.totalItems = 0;
                                $scope.appLocationList = '';
                            }
                            //                    console.log(data.obj[0]);
                        }else{
                            console.log("未获取到数据");
                        }
                    }else{
                        console.log("调用接口失败！");
                    }
                }, function(err){
                    console.log(err)
                });
        };
        /*查询地理位置 end*/
        /*查询设备信息 start*/
        $scope.queryDeviceRecordInfo=function(){
            $scope.filterDeviceRecords= phoneLabelService.queryPrivacyDevice(
                {
                    userId:$scope.userId,
                    outerIds:$scope.equipId,
                    type:"device",
                    currentPage: $scope.paginationConf8.currentPage,
                    itemsPerPage: $scope.paginationConf8.itemsPerPage
                },function(data){
                    if(data.status==0){
                        if(data.obj!=null){
                            //TODO-业务逻辑
                            if(data.obj.totalCount>=1){
                                $scope.appDeviceList = data.obj.list;
                                $scope.paginationConf8.totalItems = data.obj.totalCount;
                            }else{
                                $scope.paginationConf8.totalItems = 0;
                                $scope.appDeviceList = '';
                            }
                            //                    console.log(data.obj[0]);
                        }else{
                            console.log("未获取到数据");
                        }
                    }else{
                        console.log("调用接口失败！");
                    }
                }, function(err){
                    console.log(err)
                });
        };
        /*查询设备信息 end*/
        /***************************************************************
         当页码和页面记录数发生变化时监控后台查询
         如果把currentPage和itemsPerPage分开监控的话则会触发两次后台事件。
         ***************************************************************/
        $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage',$scope.queryContactInfo);
        $scope.$watch('paginationConf1.currentPage + paginationConf1.itemsPerPage',$scope.queryContactRecordInfo);
        $scope.$watch('paginationConf2.currentPage + paginationConf2.itemsPerPage',$scope.queryMessageRecordInfo);
        $scope.$watch('paginationConf3.currentPage + paginationConf3.itemsPerPage',$scope.queryLocationRecordInfo);
        $scope.$watch('paginationConf8.currentPage + paginationConf8.itemsPerPage',$scope.queryDeviceRecordInfo);

    };


    /*查看隐私数据 end*/
    /*失效操作 start*/
    $scope.baseInfoReset=function(){
        popupDiv('baseInfoReset');
        $scope.baseInfoResetSure=function(){
            var pa={
                userId:$scope.userId
            };
            console.log(pa);
            var par={params:pa};
            $http.post($scope.WebURL+'loan/basicInfoInvalid',pa).success(function(response){
                if(response.success){
                    hideDiv('baseInfoReset');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(response.msg);
                }else{
                    hideDiv('baseInfoReset');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(response.msg);
                }
            }).error(function(response){
                console.log(response.msg);
            })
        }
    };
    $scope.preventFraudReset=function(){
        popupDiv('taoBaoInfoReset');
        $scope.taoBaoInfoResetSure=function(){
            var pa={
                userId:$scope.userId
            };
            console.log(pa);
            var par={params:pa};
            $http.post($scope.WebURL+'loan/taobaoInfoInvalid',pa).success(function(response){
                if(response.success){
                    hideDiv('taoBaoInfoReset');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(response.msg);
                }else{
                    hideDiv('taoBaoInfoReset');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(response.msg);
                }
            }).error(function(response){
                console.log(response.msg);
            })
        }
    };
    $scope.salaryRecordReset=function(){
        popupDiv('salaryRecordReset');
        $scope.salaryRecordResetSure=function(){
            var pa={
                id:$scope.curHandleOrder.id,//处理意见
                userId:$scope.userId
            };
            console.log(pa);
            var par={params:pa};
            hideDiv('salaryRecordReset');
            popupDiv('SaveSuccess');
            $('.SaveSuccess .Message').html('该功能暂未开放');
        }
    };

    $scope.pbcInfoReset=function(){
        popupDiv('pbcRecordReset');
        $scope.pbcRecordResetSure=function(){
            var pa={
                id:$scope.curHandleOrder.id,//处理意见
                userId:$scope.userId
            };
            console.log(pa);
            var par={params:pa};
            hideDiv('pbcRecordReset');
            popupDiv('SaveSuccess');
            $('.SaveSuccess .Message').html('该功能暂未开放');
        }
    };

    $scope.userSignReset=function(){
        popupDiv('signRecordReset');
        $scope.signRecordResetSure=function(){
            var pa={
                id:$scope.curHandleOrder.id,//处理意见
                userId:$scope.userId
            };
            console.log(pa);
            var par={params:pa};
            $http.post($scope.WebURL+'loan/signedInfoInvalid',pa).success(function(response){
                if(response.success){
                    hideDiv('signRecordReset');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(response.msg);
                }else{
                    hideDiv('signRecordReset');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(response.msg);
                }
            }).error(function(response){
                console.log(response.msg);
            })
        }
    };
    $scope.resetOk=function(){
        $state.go('main.consumerUpgradeWaiting')
    };
    /*失效操作 end*/


    /*通过拒绝处理操作 start*/
    $scope.saveData=function(){
        $cookies.put('ProcessOpinion',$("#ProcessOpinion").val())
    };
    $scope.ProcessOpinion=$cookies.get('ProcessOpinion');
    $scope.handleStatus = '3';//默认通过~ 1代表拒绝  2转交 3代表通过
    $scope.submitAndRemark=function(){
        if($scope.handleStatus==3){
            if($scope.ProcessOpinion==''||$scope.ProcessOpinion==undefined||$scope.ProcessOpinion==null){
                $("#ProcessOpinion").focus();
            }else if($scope.PassMoney==''||$scope.PassMoney==null||$scope.PassMoney==undefined){
                $("#PassMoney").focus();
            }else if($scope.PassMoney>30){
                popupDiv('moneyLimited');
                $(".moneyLimited .Message").html("审批金额不能大于30万元！");
                $("#PassMoney").focus();

            }else if($scope.serviceInterestRate==''||$scope.serviceInterestRate==null||$scope.serviceInterestRate==undefined){
                $("#serviceInterestRate").focus();
            }else if($scope.PassRepayType==''||$scope.PassRepayType==null||$scope.PassRepayType==undefined){
                $("#PassRepayType").focus();
            }else if($scope.PassDateTime==''||$scope.PassDateTime==null||$scope.PassDateTime==undefined){
                $("#PassDateTime").focus();
            }else{
                //var orgCodes = "";
                //$('input[name="BankName"]:checked').each(function(){
                //    var sfruit=$(this).val();
                //    orgCodes +=','+sfruit;
                //});
                //if($("#BankList input[type='checkbox']:checked").size()>=1){
                //    orgCodes=orgCodes.substr(1,orgCodes.length);
                var pa={
                    //orgCode:''+orgCodes,//银行机构代码
                    type:$scope.handleStatus,//处理状态 1代表拒绝  2转交 3代表通过
                    remark:$scope.ProcessOpinion,//处理意见
                    creditAmount: $scope.PassMoney*10000,//授信金额
                    creditTerm: $scope.PassDateTime,//还款期限
                    creditRepaymentWay: $scope.PassRepayType,//还款方式
                    interestRate: $scope.serviceInterestRate,//费率
                    orderId:$scope.curHandleOrder.orderCode,
                    bType:1
                };
                console.log(pa);
                var par={params:pa};
                $http.post($scope.WebURL+'orderLog/dailOrder',pa).success(function(response){
                    $cookies.remove('ProcessOpinion');
//                    $http.post('http://192.168.6.24:8082/web/loan/handleUpgradeOrder',pa).success(function(response){
                    popupDiv('transferInfo');
                    $(".transferInfo .Message").html(response.message);
                    $scope.goBack=function(){
                        hideDiv('transferInfo');
//                            window.history.go(-1);
                        $state.go('main.consumerAuditHadAuditNew',{});
                    }
                }).error(function(response){
                    alert(response.message);
                })
                //}else if($("#apply_infos_table input[type='checkbox']:checked").size()<1){
                //    alert("请选择要推送的银行!");
                //    hideDiv('statisticWhole');
                //}
            }
        }else if($scope.handleStatus==1){
            if($scope.ProcessOpinion==''||$scope.ProcessOpinion==undefined||$scope.ProcessOpinion==null){
                $("#ProcessOpinion").focus();
            }else{
                var pa={
//                    orgCode:$scope.BankNameCode,//银行机构代码
                    type:$scope.handleStatus,//处理状态 1代表拒绝  2转交 3代表通过
                    remark:$scope.ProcessOpinion,//处理意见
                    orderId:$scope.curHandleOrder.orderCode,
                    bType:1
                };
                console.log(pa);
                var par={params:pa};
                $http.post($scope.WebURL+'orderLog/dailOrder',pa).success(function(response){
                    $cookies.remove('ProcessOpinion');
                    popupDiv('transferInfo');
                    $(".transferInfo .Message").html(response.message);
                    $scope.goBack=function(){
                        hideDiv('transferInfo');
//                        window.history.go(-1);
                        $state.go('main.consumerAuditHadRefusedNew',{});
                    }
                }).error(function(response){
                    console.log(response.message);
                })
            }
        }else{
            if($scope.ProcessOpinion==''||$scope.ProcessOpinion==undefined||$scope.ProcessOpinion==null){
                $("#ProcessOpinion").focus();
            }else{
                popupDiv('transfer');
                $("#memberChoose input[type='radio']").eq(0).attr('checked','checked');
                $scope.transferMember=function(){
                    $scope.targetOperator=$("#memberChoose input[type='radio']:checked").val();
                    $scope.targetOperatorName=$("#memberChoose input[type='radio']:checked").parent().next().next().text();
                    var pa={
                        userId:$scope.targetOperator,//转交人姓名
                        type:$scope.handleStatus,//处理状态 1代表拒绝  2转交 3代表通过
                        remark:$scope.ProcessOpinion,//处理意见
                        orderId:$scope.curHandleOrder.orderCode,
                        bType:1
                    };
                    console.log(pa);
                    var par={params:pa};
                    $http.post($scope.WebURL+'orderLog/dailOrder',pa).success(function(response){
                        $cookies.remove('ProcessOpinion');
                        hideDiv('transfer');
                        popupDiv('transferInfo');
                        $(".transferInfo .Message").html(response.message);
                        $scope.goBack=function(){
                            hideDiv('transferInfo');
//                            window.history.go(-1);
                            $state.go('main.consumerUpgradeWaitingNewDel',{});
                        }
                    }).error(function(response){
                        alert(response.message);
                    })
                }
            }
        }
    };
    /*通过拒绝处理操作 end*/

    /*添加备注信息 loan/uploadOrderRemark start*/
    $scope.addRemark=function(){
        if($scope.operAdvice==''||$scope.operAdvice==null||$scope.operAdvice==undefined){
            $("#operAdvice").focus();
        }else{
            var pa={
//                pageType:1,//pageType页面显示类型，1处理页面的总备注，2基本信息页面备注，3淘宝页面备注，4反欺诈页面备注，5人行征信页面备注，6工资流水页面备注
                remark:$scope.operAdvice,
                id:$scope.curHandleOrder.orderCode
            };
            console.log(pa);
            var par={params:pa};
            $http.post($scope.WebURL+'orderLog/addOrderBelongChangeLog',pa).success(function(response){
                hideDiv('NoteDiv');
                popupDiv('SaveSuccess');
                $(".SaveSuccess .Message").html(response.message);
            }).error(function(response){
                console.log(response.message);
            })
        }
    };
    /*添加备注信息 end*/
    /*查询备注信息 start*/
    $scope.queryRemarkLists=function(){
        $scope.RemarkList = ToBankOrderDetailService.queryRemarkList($scope.curHandleOrder.id,
            function(data){
                if(data.status==0){
                    if(data.obj!=''&&data.obj!=null&&data.obj!=undefined){
                        //TODO-业务逻辑
                        $scope.RemarkLists = data.obj;
                        console.log(data.obj);
                    }else{
                        $scope.RemarkLists=[];
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
    $scope.queryRemarkLists();
    /*查询备注信息 end*/

    /*查询银行列表 start*/
    $scope.queryBankLists=function(){
        $scope.BankList = ToBankOrderDetailService.queryBankListS($scope.curHandleOrder.id,
            function(data){
                if(data.status==0){
                    if(data.obj!=''&&data.obj!=null&&data.obj!=undefined){
                        //TODO-业务逻辑
                        $scope.BankLists = data.obj;
                        console.log(data.obj);
                    }else{
                        $scope.BankLists=[];
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
    $scope.queryBankLists();
    /*查询银行列表 end*/
    /*转交列表 start*/
    $scope.queryBankLists=function(){
        $scope.BankList = ToBankOrderDetailService.deliverList(true,
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
    $scope.queryBankLists();
    /*转交列表 end*/
    /*反欺诈报告 start*/
    $scope.againstCheatRe=true;
    $scope.fanReport = function(){
        $scope.againstCheatRe = !$scope.againstCheatRe;
        if($scope.againstCheatRe){

            $(".reportShow").html("收起反欺诈报告 -");

        }else{

            $(".reportShow").html("展开反欺诈报告 +");

        }
        //window.open("index.html#/main/againstCheatReport?userId="+$scope.userId);
        //window.open("index.html#/main/eidtRiskPage?parentId=0&userId="+$scope.userId+"&orderId="+$scope.curHandleOrder.id+"&riskId=0&productOne=null");
    };
    /*反欺诈报告 end*/

    var basicInfo=[];
    /*查询反欺诈报告内容 start*/
    $scope.queryAgainstCon=function(){
        $scope.queryAgainstCons = againstCheatReportService.againstCheatReport( $scope.userId,
            function(data){
                if(data.status==0){
                    if(data.obj!=''&&data.obj!=null&&data.obj!=undefined){
                        //TODO-业务逻辑
                        $scope.queryAgainstCheatData = data.obj;

                        $scope.valuess=newLineBySign($scope.queryAgainstCheatData.checkMsg);


                        $scope.queryAgainstCheatP = data.obj.info;
                        console.log(data.obj);
                        if(data.obj.info.length>=1){
                            data.obj.info.forEach(function(item){
                                basicInfo.push(item.listOfData);
                            });
                        }
                        var basicjson = {};
                        for(var i=0;i<basicInfo.length;i++)
                        {
                            basicjson[i]=basicInfo[i];
                        }
                    }else{
                        $scope.queryAgainstCheatData=[];
                    }
                }else{
//                    popupDiv('SaveSuccess');
//                    $(".SaveSuccess .Message").html(data.msg);
                    console.log(data.message);
                }
            }, function(err){
                console.log(err)
            });
    };
    $scope.queryAgainstCon();

    /*查询反欺诈报告内容 end*/
    //反欺诈报告详情
    var basicjson = {};
    $scope.realNameCheck= function(dataList){
        if(dataList.length!=''&&dataList.length!=null&&dataList.length!=undefined){
            for(var i=0;i<dataList.length;i++)
            {
                basicjson[i]=dataList[i];
            }
            $state.go('main.realNameDt',{'mainDataCode':JSON.stringify(basicjson),'userId':$scope.userId});
            //window.open("index.html#/main/realNameDt?userId="+$scope.userId+"&mainDataCode="+JSON.stringify(basicjson));

            //ToBankOrderDetailService.setCurHandleOrder(order);
        }else{
            console.log(dataList);
        }
    }
    //$state.go('applySubmit',{'totalMoney':$scope.totalMoney,'listOfProductDataTerm':JSON.stringify(basicjson),'listOfHouseCredit':JSON.stringify(housejson)});
    //反欺诈报告详情

    /*********运营商报告 start*******/
    $scope.operatorReport= function(){

        $state.go('main.operatorReport',{'userId':$scope.userId});

    }
    /*********运营商报告 end*******/
    /*******重构失效功能 start ********/
    $scope.resetFuc=function(AuditListInfo){
        popupDiv('resetFuc');
        $(".Message").html("确定将 <span style='color: orange '>"+AuditListInfo.name+"</span> 进行失效操作？");
        $scope.resetFucSure=function(){
            var pa={
                orderId:$scope.curHandleOrder.orderCode,
                authItermCode:AuditListInfo.code
            };
            console.log(pa);
            var par={params:pa};
            $http.post($scope.WebURL+'order/setAuthItermStatusInvalidNot',pa).success(function(response){
                if(response.code=="success"){

                    hideDiv('resetFuc');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(response.message);
                }else{
                    hideDiv('resetFuc');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(response.message);
                }
            }).error(function(response){
                console.log(response.msg);
            })
        }
    };
    /*******重构失效功能 end ********/
    /*刷新反欺诈报告 start*/
    $scope.refreshReAgaist=function(){
        popupDiv("loading");
        var pa={
            orderId:$scope.curHandleOrder.orderCode

        };
        console.log(pa);
        var par={params:pa};
        $http.post($scope.WebURL+'product/createAntiFraudReport',pa).success(function(response){
            document.getElementById('againstReportCur').scrollIntoView();
            if(response.status==0){
                hideDiv('loading');

                /*************查看报告 start****/
                $scope.queryAgainstCo=function(){
                    $scope.queryAgainstCons = againstCheatReportService.againstCheatReport( $scope.userId,
                        function(data){
                            if(data.status==0){
                                if(data.obj!=''&&data.obj!=null&&data.obj!=undefined){
                                    //TODO-业务逻辑
                                    $scope.queryAgainstCheatData = data.obj;

                                    $scope.valuess=newLineBySign($scope.queryAgainstCheatData.checkMsg);


                                    $scope.queryAgainstCheatP = data.obj.info;
                                    console.log(data.obj);
                                    if(data.obj.info.length>=1){
                                        data.obj.info.forEach(function(item){
                                            basicInfo.push(item.listOfData);
                                        });
                                    }
                                    var basicjson = {};
                                    for(var i=0;i<basicInfo.length;i++)
                                    {
                                        basicjson[i]=basicInfo[i];
                                    }
                                }else{
                                    $scope.queryAgainstCheatData=[];
                                }
                            }else{
//                    popupDiv('SaveSuccess');
//                    $(".SaveSuccess .Message").html(data.msg);
                                console.log(data.message);
                            }
                        }, function(err){
                            console.log(err)
                        });
                };
                $scope.queryAgainstCo();
                /*************查看报告 end****/
            }else{
                hideDiv('loading');
                console.log(response.message);
//                popupDiv('SaveSuccess');
//                $('.SaveSuccess .Message').html(response.message);
            }
        }).error(function(response){
            console.log(response.message);
        })

    };
    $scope.refreshReAgaist();
    /*刷新反欺诈报告 end*/

    /*重新检验反欺诈规则 start*/
    $scope.aganistcheckouts=function(){
        popupDiv("loading");
        $scope.aganistcheck = ToBankOrderDetailService.aganistcheckout($scope.userId,
            function(data){
                if(data.status==0){
                    hideDiv('loading');
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.message);
                }else{
//                    popupDiv('SaveSuccess');
//                    $(".SaveSuccess .Message").html(data.msg);
                    console.log(data.msg);
                }
            }, function(err){
                console.log(err)
            });
        document.getElementById('againstReportCur').scrollIntoView();
    };
    /*重新检验反欺诈规则 end*/
    /*下载银行流水明细 start*/
    $scope.bankRunningWaterDt=function(){
        var urls = $scope.WebURL+'/bankFlowController/downBankFlowRecord?JSESSIONID='+$cookies.get('JSESSIONID')+'&userId='+$scope.userId;
        window.open(urls,'_blank');


    };

    /*下载银行流水明细 end*/
    /*下载银行流水月统计 start*/
    $scope.bankRunningWaterAbstract=function(){
        var url = $scope.WebURL+'/bankFlowController/downBankFlowMonthRecord?JSESSIONID='+$cookies.get('JSESSIONID')+'&userId='+$scope.userId;
        window.open(url,'_blank');

    };

    /*下载银行流水月统计 end*/
    /*查询电话标签 联系信信息  start*/
    $scope.queryContactPhoneLabelKey=function(){
        $scope.queryContactPhoneLabelKeys = phoneLabelService.queryPhoneLabel($scope.priacyConpphoneLabelKey,
            function(data){
                if(data.status==0){
                    if(data.obj!=''&&data.obj!=null&&data.obj!=undefined){
                        //TODO-业务逻辑
                        $scope.querypriacyConKeyList = data.obj;
                        console.log(data.obj);
                    }else{
                        $scope.querypriacyConKeyList=[];
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

    /*查询电话标签 联系信信息 end*/
    /*查询电话标签 短信 start*/
    $scope.queryPhoneLabelKey=function(){
        $scope.queryPhoneLabelKeys = phoneLabelService.queryPhoneLabel($scope.phoneLabelKey,
            function(data){
                if(data.status==0){
                    if(data.obj!=''&&data.obj!=null&&data.obj!=undefined){
                        //TODO-业务逻辑
                        $scope.queryPhoneLabelKeyList = data.obj;
                        console.log(data.obj);
                    }else{
                        $scope.queryPhoneLabelKeyList=[];
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

    /*查询电话标签 短信 end*/
    /*运营商查询电话标签 通话记录 start*/
    $scope.opeartorqueryPhoneLabelKey=function(){
        if($scope.opConLabelKey==''||$scope.opConLabelKey==null||$scope.opConLabelKey==undefined){
            $("#opConLabelKey").focus();
        }else{
            $scope.opeartorqueryPhoneLabelKeys = phoneLabelService.queryPhoneLabel($scope.opConLabelKey,function(data){
                if(data.status==0){
                    if(data.obj!=''&&data.obj!=null&&data.obj!=undefined){
                        //TODO-业务逻辑
                        $scope.queryConRecordKeyList = data.obj;
                        console.log(data.obj);
                    }else{
                        $scope.queryConRecordKeyList=[];
                    }
                }else{
//                    popupDiv('SaveSuccess');
//                    $(".SaveSuccess .Message").html(data.msg);
                    console.log(data.msg);
                }
            }, function(err){
                console.log(err)
            });
        }
    };

    /*运营商查询电话标签 通话记录 end*/
    /*运营商查询电话标签 短信 start*/
    $scope.opeartorqueryMsgLabelKey=function(){
        $scope.opeartorqueryMsgLabelKeys = phoneLabelService.queryPhoneLabel($scope.operatMsgKey,
            function(data){
                if(data.status==0){
                    if(data.obj!=''&&data.obj!=null&&data.obj!=undefined){
                        //TODO-业务逻辑
                        $scope.queryOpMsgLabelKeyList = data.obj;
                        console.log(data.obj);
                    }else{
                        $scope.queryOpMsgLabelKeyList=[];
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

    /*运营商查询电话标签 短信 end*/
    /***************************************运营商通话记录start*****/

    $scope.paginationConf25 = {
        currentPage: 1,
        itemsPerPage: 5
    };
    $scope.paginationConf26= {
        currentPage: 1,
        itemsPerPage: 5
    };
    /*查询通话记录信息 start*/
    $scope.queryOperatorContactRecordInfoP=function(){
        $scope.operatorphoneLabelkeyWords = [];
        var phoneLabelKeyId ="";
        $('input[name="operatorphoneLabelKey"]:checked').each(function(){
            var phoneLabelKeyId=$(this).val();
            phoneLabelKeyId +=','+phoneLabelKeyId;
        });
        phoneLabelKeyId=phoneLabelKeyId.substr(1,phoneLabelKeyId.length);
        $scope.operatorphoneLabelkeyWords= phoneLabelKeyId.split(",");

        if($scope.operatorphoneLabelkeyWords==""||$scope.operatorphoneLabelkeyWords==null||$scope.operatorphoneLabelkeyWords==undefined){
            $scope.operatorphoneLabelkeyWords=undefined;
        }
        $scope.filteroperatorCallRecords= phoneLabelService.operatorRecord(
            {
                phone:$scope.contactPersonNumberOperator,
                userId:$scope.userId,
                phoneTitles:$scope.operatorphoneLabelkeyWords,
                currentPage: $scope.paginationConf25.currentPage,
                itemsPerPage: $scope.paginationConf25.itemsPerPage
            },function(data){
                if(data.status==0){

                    //TODO-业务逻辑

                    if(data.obj.totalCount>=1){
                        $scope.appuserCallRecordOperator = data.obj.list;
                        $scope.paginationConf25.totalItems = data.obj.totalCount;
                    }else{
                        $scope.paginationConf25.totalItems = 0;
                        $scope.appuserCallRecordOperator = '';
                    }
                    //                    console.log(data.obj[0]);

                }else{
                    console.log("调用接口失败！");
                }
            }, function(err){
                console.log(err)
            });
    };
    /*查询通话记录信息 end*/

    /*查询短信记录信息 start*/
    $scope.queryMessageRecordInfoOperator=function(){
        $scope.operatorMsgLabelkeyWords = [];
        var phoneLaberOrders ="";
        $('input[name="operatorMsgphoneLabelKey"]:checked').each(function(){
            var phoneLabelKeyId=$(this).val();
            phoneLaberOrders +=','+phoneLabelKeyId;
        });
        phoneLaberOrders=phoneLaberOrders.substr(1,phoneLaberOrders.length);
        $scope.operatorMsgLabelkeyWords= phoneLaberOrders.split(",");

        if($scope.operatorMsgLabelkeyWords==""||$scope.operatorMsgLabelkeyWords==null||$scope.operatorMsgLabelkeyWords==undefined){
            $scope.operatorMsgLabelkeyWords=undefined;
        }

        $scope.filterMessageRecordOperator= phoneLabelService.operatorMsgRecord(
            {
                userId:$scope.userId,
                phone:$scope.MsgNumberOperator,
                phoneTitles:$scope.operatorMsgLabelkeyWords,
                currentPage: $scope.paginationConf26.currentPage,
                itemsPerPage: $scope.paginationConf26.itemsPerPage
            },function(data){
                if(data.status==0){

                    //TODO-业务逻辑
                    if(data.obj.totalCount>=1){
                        $scope.appuserMessageOperatorRecordList = data.obj.list;
                        $scope.paginationConf26.totalItems = data.obj.totalCount;
                    }else if(data.obj.list==null){

                        $scope.appuserMessageOperatorRecordList=[];
                        $scope.paginationConf26.totalItems = 0;
                        $scope.appuserMessageOperatorRecordList = '';
                    }
                    //                    console.log(data.obj[0]);

                }else{
                    console.log(data.message);
                }
            }, function(err){
                console.log(err)
            });
    };
    /*查询短信记录信息 end*/
    $scope.$watch('paginationConf25.currentPage + paginationConf25.itemsPerPage',$scope.queryOperatorContactRecordInfoP);
    $scope.$watch('paginationConf26.currentPage + paginationConf26.itemsPerPage',$scope.queryMessageRecordInfoOperator);

    /*******************************************运营商通话记录  end*****/

    /***************************************************************
     当页码和页面记录数发生变化时监控后台查询
     如果把currentPage和itemsPerPage分开监控的话则会触发两次后台事件。
     ***************************************************************/

    /*sy--客户填写公司名称注册情况列表 start */
    $scope.companyInformation= ToBankOrderDetailService.queryCompanyInformation(
        {
            userId:$scope.userId

        },function(data){
            if(data.status==0){
                $scope.conmpanyInfomations = data.obj;
            }else{
                console.log(data.message);
            }
        }, function(err){
            console.log(err)
        });
    /*sy--客户填写公司名称注册情况列表 end */
    /*sy--客户填写公司名称公司验证 start */
    $scope.queryCheckCompany= ToBankOrderDetailService.checkCompany(
        {
            userId:$scope.userId
        },function(data){
            if(data.status==0){
                $scope.checkCompanys = data.obj;

            }else{
                console.log(data.message);
            }
        }, function(err){
            console.log(err)
        });
    /*sy--客户填写公司名称公司验证 end */

});