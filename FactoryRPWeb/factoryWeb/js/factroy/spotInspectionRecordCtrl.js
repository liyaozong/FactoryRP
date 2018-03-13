//巡检记录控制器
myApp.controller('spotInspectionRecordCtrl',['$filter','$rootScope','$location','$scope','$cookies','departmentManageService','inspectionPlan','queryCorporateAllUser','factoryParameterSettingService','validate','$timeout','FileUploader','FF_API',function($filter,$rootScope,$location,$scope,$cookies,departmentManageService,inspectionPlan,queryCorporateAllUser,factoryParameterSettingService,validate,$timeout,FileUploader,FF_API){

    // console.log('巡检记录控制器');
    //查询部门
    departmentManageService.queryOrder({
        name:$scope.depName,
        corporateIdentify:$scope.corporateIdentify
    }, function(response){
        if(response.data!=''&&response.data!=null&&response.data!=undefined&&response.errorCode=='000000'){
            $scope.departmentManageLists=response.data;
        }else{
            console.log(response.errorMessage);
        }
    });

    //阻止冒泡
    $scope.stopUp=function (event) {
        // console.log(event)
        event.stopPropagation();
    };

    $scope.xjsjLists=[{
        id:1,
        name:'有异常'
    },{
        id:2,
        name:'有异常或者漏检'
    },{
        id:3,
        name:'有漏检'
    }];
    $scope.xjlxLists=[{
        id:1,
        name:'本周记录'
    },{
        id:2,
        name:'上周记录'
    },{
        id:3,
        name:'本月记录'
    },{
        id:4,
        name:'上月记录'
    },{
        id:5,
        name:'本年记录'
    },{
        id:6,
        name:'上年记录'
    },{
        id:7,
        name:'更早记录'
    }];


    //分页查询巡检记录
    $scope.paginationConf={
        currentPage:1,
        itemsPerPage:5
    };
    $scope.spotInspectionRecordLists=[];$scope.changeDetailLists=[];
    $scope.onQuery=function () {
        var req={
            "currentPage": $scope.paginationConf.currentPage, //当前页码
            "itemsPerPage": $scope.paginationConf.itemsPerPage,//每页显示记录数
            departmentId:$scope.departmentManage,
            abnormalOrMissCondition:$scope.abnormalOrMissCondition,
            executeTimeCondition:$scope.executeTimeCondition

        };
        inspectionPlan.findByPageRecord(req).success(function (data) {
            // console.log(data,'巡检记录');
            if(data.data&&data.data.totalCount>=1){
                // if(true){
                $scope.paginationConf.totalItems = data.data.totalCount;
                $scope.spotInspectionRecordLists=data.data.list;
                //查询执行人
                queryCorporateAllUser.getData().success(function (data) {
                    $scope.allUserLists=data.data.userRespList;
                    // console.log($scope.allUserLists)
                    if($scope.spotInspectionRecordLists){
                        $scope.spotInspectionRecordLists.forEach(function (k) {
                            // var strsArr=[];
                            // console.log(k,'k')
                            $scope.allUserLists.forEach(function (v) {
                                // console.log(v.userId,k.executor);
                                if(v.userId==k.executor){
                                    k.executorsName=v.userName;
                                }
                            });
                        });
                    }
                });
                // console.log($scope.spotInspectionRecordLists)
                $timeout(function () {
                    if($scope.spotInspectionRecordLists.length>0){
                        inspectionPlan.querySpotInspectionRecordDetailByRecordId($scope.spotInspectionRecordLists[0].recordId,$scope.spotInspectionRecordLists[0].planId).success(function (data) {
                            $scope.changeDetailLists=data.data.detailList;
                        });
                        $($('.prossTr')[0]).addClass('ccTr');
                    }
                },400);
            }else{
                // $scope.changeDetailLists=[];
                $scope.paginationConf.totalItems = 0;
                $scope.spotInspectionRecordLists.length = 0;
            }
        });
    };

    //切换巡检明细表格事件
    $scope.changeDetail=function (obj,event) {
        // console.log($(event.target));
        $(event.target).parent().parent().find('tr').removeClass('ccTr');
        $(event.target).parent().addClass('ccTr');
        inspectionPlan.querySpotInspectionRecordDetailByRecordId(obj.recordId,obj.planId).success(function (data) {
            $scope.changeDetailLists=data.data.detailList;
        });
    };


    //删除巡检记录
    $scope.deleteSIPBalance=function () {
        popupDiv('deleteSISPop');
        // console.log('11')
    };

    //确认删除
    $scope.deleteSIPPopSure=function () {
        var $inputCheck=$(".SIPListsInput:checked");
        var arr=[];
        // console.log($inputCheck)
        $inputCheck.each(function (i,n) {
            arr.push($(n).attr('value'));
        });
        // console.log(arr);
        if(arr.length>1){
            // inspectionPlan.deleteSpotInspectionStandardByIdsIP(arr.join(',')).success(function (data) {
            //     if(data.errorCode=='000000'){
            //         hideDiv('deleteSISPop');
            //         popupDiv('SaveSuccess');
            //         $('.SaveSuccess .Message').html(data.errorMessage);
            //     }else{
            //         hideDiv('deleteSISPop');
            //         popupDiv('SaveSuccess');
            //         $('.SaveSuccess .Message').html(data.errorMessage);
            //     }
            // });
        }else if(arr.length==1){
            // inspectionPlan.deleteSpotInspectionPlanDetailByPlanId(arr[0]).success(function (data) {
            //     if(data.errorCode=='000000'){
            //         hideDiv('deleteSISPop');
            //         popupDiv('SaveSuccess');
            //         $('.SaveSuccess .Message').html(data.errorMessage);
            //     }else{
            //         hideDiv('deleteSISPop');
            //         popupDiv('SaveSuccess');
            //         $('.SaveSuccess .Message').html(data.errorMessage);
            //     }
            // });
        }
    };


    //打开执行巡检计划（添加巡检记录）弹出层
    $scope.openAddRecord=function (obj,type,$event) {
        // console.log(obj);
        $scope.recordPopType=type;
        inspectionPlan.querySpotInspectionRecordDetailByRecordId(obj.recordId,obj.planId).success(function (data) {
            if(data.errorCode=='000000'){
                console.log(data.data,'------');
                popupDiv('addRecordPopup');
                $scope.errShow1=false;
                $scope.SpotInspectionPlanExecuteWarpReq={
                    name:data.data.planName,
                    executeTime:$filter('date')(data.data.executeTime,'yyyy-MM-dd HH:mm:ss'),
                    "detailList": data.data.detailList,//巡检记录详情集合
                    "abnormalHandleDesc": data.data.exceptionHandleDesc,//异常处理情况,
                    "executor": data.data.executor,//执行者
                    "executorName": data.data.executor,//执行者姓名字符串
                    "planId": obj.planId,//巡检标准ID
                    // missCount:data.data.missCount,//漏检项
                    // abnormalDeviceCount:data.data.abnormalDeviceCount,//异常项
                    // department:data.data.department,//使用部门ID
                    // departmentName:data.data.departmentName,//使用部门名称
                    inTime:data.data.inTime,//是否在执行时间
                    executeStatus:data.data.executeStatus
                };
                // $('#executeTime').val($filter('date')(data.data.executeTime,'yyyy-MM-dd'));
                var arr=[];
                if(data.data.detailList){
                    data.data.detailList.forEach(function (k) {
                        // console.log(k,'第一层');
                        arr.push({
                            standardId:k.standardId,
                            planDeviceId:k.planDeviceId,
                            deviceId:k.deviceId,
                            remake:k.remake,
                            imageIdList:k.imageIdList,
                            itemList:[],
                            isFlog:false
                        });
                    })
                }
                $scope.reqLists={
                    planId:obj.planId,
                    recordId:obj.recordId,
                    abnormalHandleDesc:data.data.abnormalHandleDesc,
                    list:arr
                };
            }else {
                popupDiv('SaveSuccess');
                $('.SaveSuccess .Message').html(data.errorMessage);
            }
        });


        $event.stopPropagation();
    };
    //关闭执行巡检计划（添加巡检记录）弹出层departmentName
    $scope.closeAR1=function () {
        hideDiv('addRecordPopup');
    };
    $scope.closeAR=function () {//确定添加巡检
        // alert('接口调试中');
        // console.log('第一层确定',$scope.reqLists);
        var arr1=[];
        var reqLists=angular.copy($scope.reqLists);
        reqLists.list.forEach(function (m,i) {
            // console.log(m,i);
            var arr2=[];
            m.itemList.forEach(function (k,l) {
                // console.log(k,l);
                if(k.isFlog){
                    arr2.push({
                        remark:k.remark,
                        recordResult:k.recordResult,
                        itemId:k.itemId,
                        abnormalDesc:k.abnormalDesc
                    });
                }
            });
            m.itemList=arr2;
            if(m.isFlog){
                arr1.push({
                    imageIdList:m.imageIdList,
                    itemList:m.itemList,
                    planDeviceId:m.planDeviceId,
                    deviceId:m.deviceId,
                    remake:m.remake,
                    standardId:m.standardId
                });
            }
        });
        reqLists.list=arr1;
        reqLists.abnormalHandleDesc=$scope.SpotInspectionPlanExecuteWarpReq.abnormalHandleDesc;
        console.log(reqLists.abnormalHandleDesc,$scope.SpotInspectionPlanExecuteWarpReq.abnormalHandleDesc,'异常处理情况');
        if(reqLists.list.length>0){
            inspectionPlan.executeSpotInspectionPlan(reqLists).success(function (data) {
                hideDiv('addRecordPopup');
                if(data.errorCode=='000000'){
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }else {
                    popupDiv('SaveSuccess');
                    $('.SaveSuccess .Message').html(data.errorMessage);
                }
            });
        }else {
            $scope.errShow1=true;
        }
        console.log(reqLists,'end');
    };

    //打开巡检明细录入弹出层
    $scope.open_ar_lrs=function (obj,planId,$index) {
        inspectionPlan.findSpotInspectionStandardItemByStandardIdAndPlanId(obj.standardId,planId).success(function (data) {
            if(data.errorCode=='000000'){
                console.log(data,'第二层');
                $scope.uploadEnd=false;
                hideDiv('addRecordPopup');
                popupDiv('addRecordPopup2');
                $scope.errShow2=false;
                $scope.uploadEndDelete=false;
                $scope.repsponseData=[];
                $scope.xjjlDetailList={
                    deviceCode:obj.deviceCode,
                    deviceId:obj.deviceId,
                    deviceName:obj.deviceName,
                    deviceSpecification:obj.deviceSpecification,
                    // department:$scope.SpotInspectionPlanExecuteWarpReq.department,//使用部门ID
                    departmentName:obj.departmentName,//使用部门名称
                    remake:obj.remake,
                    planDeviceId:obj.planDeviceId,
                    list:data.data,
                    standardId:obj.standardId,
                    imageIdList:obj.imageIdList?obj.imageIdList:[]
                };
                // console.log(obj.imageIdList);
                if($scope.xjjlDetailList.imageIdList.length>0){
                    inspectionPlan.viewItem({fileIdList:obj.imageIdList}).success(function (data) {
                        $scope.repsponseData.push(data.data);
                        // $scope.xjjlDetailList.imageIdList.push(data.data.key);
                    })
                }

                var arr2=[];
                if(data.data){
                    data.data.forEach(function (m) {
                        arr2.push({
                            "abnormalDesc": "",
                            "itemId": '',
                            "recordResult": "",
                            "remark": "",
                            isFlog:false
                        })
                    });
                }
                console.log($scope.reqLists.list[$index],'-------',$index,arr2);
                $scope.reqLists.list[$index].itemList=arr2;
                $scope.index2=$index;
                // console.log($scope.reqLists,'22222');
            }else {
                alert(data.errorMessage)
            }
        });
    };
    //关闭执行巡检计划（添加巡检记录2）弹出层
    $scope.closeAR21=function () {
        hideDiv('addRecordPopup2');
        popupDiv('addRecordPopup');
    };
    $scope.closeAR2=function (obj) {//确定执行
        // alert('接口调试中');

        // console.log('第二层确定',$scope.reqLists);
        if($scope.reqLists.list[$scope.index2].itemList){
            hideDiv('addRecordPopup2');
            popupDiv('addRecordPopup');
            $scope.errShow1=false;
            $scope.reqLists.list[$scope.index2].remake=$scope.xjjlDetailList.remake;
            $scope.reqLists.list[$scope.index2].imageIdList=$scope.xjjlDetailList.imageIdList;
            $scope.reqLists.list[$scope.index2].isFlog=true;
        }else{
            $scope.errShow2=true;
        }
    };

    //打开巡检明细录入弹出层3
    $scope.open_ar_lrs2=function (obj,$index) {
        hideDiv('addRecordPopup2');
        popupDiv('addRecordPopup3');
        console.log(obj);
        $scope.xjmxList={
            itemId:obj.itemId,
            name:obj.name,
            recordResult:obj.recordResult,
            abnormalDesc:obj.abnormalDesc,
            remark:obj.remark,
            executeDetailId:obj.executeDetailId,
            inspectionStatus:obj.inspectionStatus
        };
        // console.log(obj.recordResult)
        $timeout(function () {
            $("#AR_djz option").each(function(){
                // console.log($(this).val().split(':')[1],obj.recordResult,'----')
                if($(this).val().split(':')[1]==obj.recordResult){
                    $(this).attr("selected",true);
                }
            });
        },500);
        $scope.index3=$index;
        $scope.errShow3=false;
        // $scope.reqLists.list.itemList[$scope.index2].itemList=[]
        // console.log('第三层')

    };
    //关闭执行巡检计划（添加巡检记录3）弹出层
    $scope.closeAR31=function () {
        hideDiv('addRecordPopup3');
        popupDiv('addRecordPopup2');
    };
    $scope.closeAR3=function (obj) {//确定编辑
        // $scope.reqList3.push($scope.xjmxList);
        // alert('接口调试中');
        var req={
            itemId:$scope.xjmxList.itemId,
            name:$scope.xjmxList.name,
            recordResult:$scope.xjmxList.recordResult,
            abnormalDesc:$scope.xjmxList.abnormalDesc,
            remark:$scope.xjmxList.remark
        };
        var flog;
        for(var i in req){
            // console.log(i,":",req[i]);
            flog=validate.required(req[i]);
            if(!flog){
                break;
            }
        }
        if(flog){
            $scope.xjjlDetailList.list[$scope.index3]={
                itemId:$scope.xjmxList.itemId,
                name:$scope.xjmxList.name,
                recordResult:$scope.xjmxList.recordResult,
                abnormalDesc:$scope.xjmxList.abnormalDesc,
                remark:$scope.xjmxList.remark,
                executeDetailId:$scope.xjmxList.executeDetailId,
                inspectionStatus:$scope.xjmxList.inspectionStatus
            };
            $scope.reqLists.list[$scope.index2].itemList[$scope.index3]={
                itemId:$scope.xjmxList.itemId,
                name:$scope.xjmxList.name,
                recordResult:$scope.xjmxList.recordResult,
                abnormalDesc:$scope.xjmxList.abnormalDesc,
                remark:$scope.xjmxList.remark,
                executeDetailId:$scope.xjmxList.executeDetailId,
                // inspectionStatus:$scope.xjmxList.inspectionStatus,
                isFlog:true
            };
            hideDiv('addRecordPopup3');
            popupDiv('addRecordPopup2');
            $scope.errShow2=false;
        }else {
            $scope.errShow3=true;
        }


        // console.log('第三层确定',$scope.reqLists);
    };

    //上传文件
    $scope.uploadStatus = $scope.uploadStatus1 = false; //定义两个上传后返回的状态，成功获失败
    $scope.uploadShow=false;
    var token=$cookies.get('token');
    var uploader = $scope.uploader = new FileUploader({
        url: FF_API.base+FF_API.uploadToOSSPath+'?token='+token,
        // url: '/service/wordsList',
        queueLimit: 1,     //文件个数
        //headers:{'Content-Type':'multipart/form-data'},
        formData:{
            type:'inspect'
        },
        removeAfterUpload: true   //上传后删除文件
    });
    $scope.clearItems = function(){    //重新选择文件时，清空队列，达到覆盖文件的效果
        uploader.clearQueue();
    };
    uploader.onAfterAddingFile = function(fileItem) {
        $scope.fileItem = fileItem._file;    //添加文件之后，把文件信息赋给scope
        //能够在这里判断添加的文件名后缀和文件大小是否满足需求。
        console.log($scope.fileItem,'添加文件后');
        $scope.uploadEnd=false;
        $scope.uploadFlog=true;
        if(fileItem._file.size>1048576){
            $scope.uploadEnd=true;
            $scope.uploadText='图片过大';
            $scope.uploadFlog=false;
        }
    };
    uploader.onBeforeUploadItem=function(item){
        console.log(item,'文件上传之前');
        $scope.uploadShow=true;
    };
    uploader.onProgressItem =function (item, progress) {
        console.log(item, progress,'文件上传中');
        $('#jdt').css({
            width:280*(progress/100)
        })
    };
    uploader.onSuccessItem = function(fileItem, response, status, headers) {
        $scope.uploadStatus = true;   //上传成功则把状态改为true
        console.log(fileItem,response,status,headers,'文件上传成功后');
        $scope.fileItem='';
        $scope.uploadShow=false;
        $scope.uploadEnd=true;
        $scope.uploadText='文件上传成功';
        if(response.errorCode=='000000'){
            $scope.repsponseData.push(response.data);
            $scope.xjjlDetailList.imageIdList.push(response.data.key);
        }else {
            $scope.uploadEnd=true;
            $scope.uploadText=response.errorMessage;
        }

        // console.log($scope.repsponseData,'图片数组')
    };
    uploader.onErrorItem = function(fileItem, response, status, headers) {
        // $scope.uploadStatus = true;   //上传失败则把状态改为true
        console.log(fileItem,response,status,headers,'文件上传失败后');
        $scope.fileItem='';
        $scope.uploadShow=false;
        $scope.uploadEnd=true;
        $scope.uploadText='文件上传失败';
    };
    $scope.UploadFile = function(){
        if($scope.uploadFlog){
            uploader.uploadAll();
        }
    };
    //删除已上传图片
    $scope.deleteUploadImg=function (obj,index) {
        var req={
            itemList:[obj.key]
        };
        inspectionPlan.batchDeleteItems(req).success(function (response) {
            if(response.errorCode=='000000'){
                $scope.repsponseData.splice(index,1);
                $scope.xjjlDetailList.imageIdList.splice(index,1);
                $scope.uploadEndDelete=false;
            }else {
                $scope.uploadEndDelete=true;
                $scope.uploadDeleteText=response.errorMessage;
            }
        });
    };






    $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.onQuery);
}]);
