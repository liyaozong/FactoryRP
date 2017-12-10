/**
 * Created by jayvenLee on 2017/11/11.
 */
//var app = angular.module('myApp', ['tm.pagination','ngResource']);
consumerToBankApp.controller('commitmentOrderDetailController',function ($scope,$cookies,FileUploader,$stateParams,againstCheatReportService,consumerAuditDataCheckService,$resource, $location, $state, UrlService, commitmentOrderDetailService,$http,$filter) {
    $scope.WebURL=UrlService.getUrl('manageAudit');
    $scope.WebURL1=UrlService.getUrl('commitmentServiceUpload');
    $scope.curHandleOrder = commitmentOrderDetailService.getCurHandleOrder();
//    $scope.orderStatus=$scope.curHandleOrder.orderStatus;
    if($scope.curHandleOrder.userId!=null&&$scope.curHandleOrder.userId!=''&&$scope.curHandleOrder.userId!=undefined){
        $cookies.put('userId',$scope.curHandleOrder.userId);
        $scope.userId=$cookies.get('userId');
    }else{
        $scope.userId=$cookies.get('userId');
    }
    if($scope.curHandleOrder.code!=null&&$scope.curHandleOrder.code!=''&&$scope.curHandleOrder.code!=undefined){
        $cookies.put('code',$scope.curHandleOrder.code);
        $scope.code=$cookies.get('code');
    }else{
        $scope.code=$cookies.get('code');
    }
    if($scope.curHandleOrder.channelProductCode!=null&&$scope.curHandleOrder.channelProductCode!=''&&$scope.curHandleOrder.channelProductCode!=undefined){
        $cookies.put('channelProductCode',$scope.curHandleOrder.channelProductCode);
        $scope.channelProductCode=$cookies.get('channelProductCode');
    }else{
        $scope.channelProductCode=$cookies.get('channelProductCode');
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
    /*图片放大旋转 start*/
//    $scope.commitmentImgs=['http://daianla-oss.oss-cn-hangzhou.aliyuncs.com/data/2017/9/27/18802086470/36406c09-eebf-4b72-9dcd-f5466725a0a2.jpg?Expires=1509680201&OSSAccessKeyId=LTAIF6S6okWXoW6z&Signature=9s%2BHquEmEDcRpYO7f7n51ue48MI%3D','http://daianla-oss.oss-cn-hangzhou.aliyuncs.com/data/2017/9/27/18802086470/38e0252c-dcf1-49fd-a06f-ba9567801fac.jpg?Expires=1509680201&OSSAccessKeyId=LTAIF6S6okWXoW6z&Signature=jyL6x24snmrvZjEarTzDVtiN2rs%3D','http://daianla-oss.oss-cn-hangzhou.aliyuncs.com/data/2017/9/27/18802086470/06a5edfd-e06b-476e-a1a6-99f63d3ceaf8.jpg?Expires=1509680201&OSSAccessKeyId=LTAIF6S6okWXoW6z&Signature=GZQ36yYs/Dl5MVDpYIQmIZQoBf4%3D'];
    $scope.imgShow=function(imgUrl){
        $scope.chooled=imgUrl;
        popupDiv("imgShow");
        $(".imgShow").css('top','40px');
    };
    /*图片放大旋转 end*/
    /*upload start*/
    $scope.uploadFile=function(){
        $("#uploadRealFile").click();
    };
    var uploader = $scope.uploader = new FileUploader({
        url: $scope.WebURL1+'commitmentLetter?JSESSIONID='+$cookies.get('JSESSIONID')
    });
    // FILTERS

    uploader.filters.push({
        name: 'imageFilter',
        fn: function(item /*{File|FileLikeObject}*/, options) {
            var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
            return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
        }
    });

    // CALLBACKS

    uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
        console.info('onWhenAddingFileFailed', item, filter, options);
    };
    uploader.onAfterAddingFile = function(fileItem) {
        console.info('onAfterAddingFile', fileItem);
    };
    uploader.onAfterAddingAll = function(addedFileItems) {
//        console.info('onAfterAddingAll', addedFileItems);
    };
    uploader.onBeforeUploadItem = function(item) {
//        console.info('onBeforeUploadItem', item);
    };
    uploader.onProgressItem = function(fileItem, progress) {
//        console.info('onProgressItem', fileItem, progress);
    };
    uploader.onProgressAll = function(progress) {
//        console.info('onProgressAll', progress);
    };
    uploader.onSuccessItem = function(fileItem, response, status, headers) {
//        console.info('onSuccessItem', fileItem, response, status, headers);
        $scope.uploadSuccess=true;
        if(response.status==0){
            if(response.obj!=null){
                $scope.commitmentImgs.push(response.obj);
            }else{
                console.log(response.message);
            }
        }else{
            console.log(response.message);
        }
//        console.log(response.obj);
    };
    uploader.onErrorItem = function(fileItem, response, status, headers) {
//        console.info('onErrorItem', fileItem, response, status, headers);
    };
    uploader.onCancelItem = function(fileItem, response, status, headers) {
//        console.info('onCancelItem', fileItem, response, status, headers);
    };
    uploader.onCompleteItem = function(fileItem, response, status, headers) {
//        console.info('onCompleteItem', fileItem, response, status, headers);
    };
    uploader.onCompleteAll = function() {
//        console.info('onCompleteAll');
    };

//    console.info('uploader', uploader);
    /*upload end*/
    /* 查询承诺函信息 start*/
    $scope.showCommitmentOrderStatus=function(){
        commitmentOrderDetailService.queryCommitmentInfo($scope.code, function(response){
            if(response.status==0&&response.obj!=null&&response.obj!=''&&response.obj!=undefined){
                $scope.commitmentOrderInfo = response.obj;
                console.log($scope.commitmentOrderInfo);
                $scope.commitmentImgs=$scope.commitmentOrderInfo.picUrls;
                $scope.version=$scope.commitmentOrderInfo.version;
            }else{
                $scope.commitmentOrderInfo = [];
                $scope.commitmentImgs = [];
                console.log(response.message)
            }
        });
    };
    $scope.showCommitmentOrderStatus();
    /* 查询承诺函信息 end*/
    /*提交已上传的 承诺函图片 start*/
    $scope.submitCommitmentInfo=function(){
        commitmentOrderDetailService.commitmentSubmit({
            'code':$scope.code,
            'picUrls':$scope.commitmentImgs
        },function(data) {
            if (data.status == 0) {
                hideDiv('BusinessFillAgain');
                if (data.obj != '' && data.obj != null && data.obj != undefined) {
                    //TODO-业务逻辑
                    $scope.storeInfos = data.obj;
                    console.log($scope.storeInfos);
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.message);
                } else {
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.message);
                }
            } else {
                hideDiv('BusinessFillAgain');
                popupDiv('SaveSuccess');
                $('.SaveSuccess .Message').html(data.message);
                console.log(data.message);
            }
        }, function (err) {
            console.log(err);
        });
    };
    /*提交已上传的 承诺函图片 end*/

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
        $scope.queryStoryList = commitmentOrderDetailService.queryStoryList({
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
        $scope.queryCommonloan= commitmentOrderDetailService.queryLoanConditions($scope.userId,  function(data){
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
            $scope.deleteCommonloan= commitmentOrderDetailService.deleteLoanConditions(res.id, function(data){
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
            commitmentOrderDetailService.addLoanConditions({
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
            commitmentOrderDetailService.addPersonalCommonLoaner({
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
        $scope.queryCommonloan= commitmentOrderDetailService.queryPersonalCommonLoaner({
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
        $scope.queryRealController= commitmentOrderDetailService.queryRealController({
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
        $scope.queryCompanyInfo= commitmentOrderDetailService.queryCompanyInfo({
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
            $scope.deleteCommonloan= commitmentOrderDetailService.deletePersonalCommonLoaner(res.id, function(data){
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
        $scope.queryWithdrawCompany= commitmentOrderDetailService.queryWithdrawCompanyInfo({
            userId:$scope.userId, //申请人用户Id
            code:$scope.code      //订单ID（放款的订单ID）
        }, function(data){
            if(data.status==0){
                $scope.storeBaseDto=data.obj.storeBaseDto;//实际控制人,企业，店铺等信息列表
                $scope.companyCode=data.obj.zzCode;//企业中征码
                $scope.coBorrowerDtos=data.obj.coBorrowerDtos;//共同贷款人相关信息列表
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
            $scope.updateShareHolder = commitmentOrderDetailService.updateShareHolders(pa, function(data){
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
            $scope.deleteCommonloan= commitmentOrderDetailService.deleteCompany({
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
        commitmentOrderDetailService.creditResults($scope.orderId, function(data){
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
        $scope.DeliverList = commitmentOrderDetailService.deliverList({
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
    $scope.handleStatus='PASSED';//NOT_SUBMITTED("未提交"),//审核中()AUDITING("审核中" //审核通过 -- 可以信息提额认证 2PASSED("审核通过")
    $scope.submitAndRemarkNew=function(){
        if($scope.handleStatus==''||$scope.handleStatus==null||$scope.handleStatus==undefined){
            $("#handleStatus").focus();
        }else{
            commitmentOrderDetailService.commitmentSubmitAudit($scope.code,{
                version:$scope.version, //申请人用户Id
                status:$scope.handleStatus//订单ID（放款的订单ID）
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
    }
    /*放款环节提交 end*/
});