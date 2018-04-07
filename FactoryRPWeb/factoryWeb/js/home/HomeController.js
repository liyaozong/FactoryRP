/**
 * Created by SHYL on 2016/6/13.
 */
myApp.controller("HomeController", function($rootScope,UrlService,$cookies,$state, $scope, $resource, $timeout, $http,$location,$log,AuthorizationService,departmentManageService,factoryParameterSettingService,userManagementService,$window,$timeout) {
    //首页显示用户名
    $rootScope.username=$cookies.get('username');
    $("body").removeAttr('id');
    $("body").css('background','#fff');
    $("body").css('overflow','auto');
    //登出
    $rootScope.onLogoutNew=function () {
        AuthorizationService.doLogout({},function(data){
            if(data != null && (data.errorCode == '000000' && data.errorMessage=="成功")){
                $cookies.remove('username');
                $cookies.remove('token');
                $cookies.remove('corporateIdentify');
                $state.go('login');
            }else{
                $cookies.remove('username');
                $cookies.remove('token');
                $cookies.remove('corporateIdentify');
                $state.go('login');
            }
        }, function(err){

        });
    };
    //修改密码 updateUserPassword
    $rootScope.updatePasswordClick=function () {
        popupDiv('updatePassword');
        $rootScope.updatePasswordSure=function () {
            if($("#oldPassword").val()==''||$("#oldPassword").val()==null||$("#oldPassword").val()==undefined){
                $("#oldPassword").focus();
            }else if($("#newPassword").val()==''||$("#newPassword").val()==null||$("#newPassword").val()==undefined){
                $("#newPassword").focus();
            }else if($("#newPasswordSure").val()==''||$("#newPasswordSure").val()==null||$("#newPasswordSure").val()==undefined){
                $("#newPasswordSure").focus();
            }else if($("#newPassword").val()!=$("#newPasswordSure").val()){
                $("#newPasswordSure").focus();
                alert('两次输入的密码不一致，请确认！')
            }else {
                var pa={
                    newPassword:$("#newPasswordSure").val(),
                    oldPassword:$("#oldPassword").val()
                };
                var par={params:pa};
                AuthorizationService.updateUserPassword(pa,function(data){
                    if(data != null && (data.errorCode == '000000' && data.errorMessage=="成功")){
                        hideDiv('updatePassword');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }else{
                        hideDiv('updatePassword');
                        popupDiv('SaveSuccess');
                        $('.SaveSuccess .Message').html(data.errorMessage);
                    }
                }, function(err){

                });
            }
        }
    };
    $scope.$on('home_change', function(event,data) {
        $scope.curFooter = data;
    });

    $scope.$on('alertMsg', function(event,data) {
        $scope.alertMsg = data;

        $timeout(function(){
            $scope.alertMsg ='';
        }, 1000);
    });
    $scope.list1=[];
    $scope.list2=[];
    $scope.list3=[];
    /*首页故障设备统计 start*/
    $scope.indexTroubleCount1=function () {
        factoryParameterSettingService.indexTroubleCount({}, function(response){
            if(response.data!=''&&response.data!=null&&response.data!=undefined&&response.errorCode=='000000'){
                $scope.list1=response.data.troubleRecords;//滚动列表
                console.log($scope.list1);
                $scope.countDevice=response.data.countDevice;//设备总数
                $scope.countRepairing=response.data.countRepairing;//正在维修的台数
                $scope.countTrouble=response.data.countTrouble;//故障台数
                //超过5条滚动
                if($scope.list1.length>5){
                    test1();
                    $("#troubleTable").css('height','170px');
                }else{
                    $("#troubleTable").css('height','auto');
                }
            }else{
                console.log(response.errorMessage);
            }
        });
    };
    $scope.indexTroubleCount1();
    /*首页故障设备统计 end*/
    /*首页巡查计划 start*/
    //超过5条滚动
        if($scope.list2.length>5){
            test2();
            $("#patrolTable").css('height','170px');
        }else{
            $("#patrolTable").css('height','auto');
        }
    /*首页巡查计划 end*/
    /*首页润滑计划统计 start*/
    $scope.indexTroubleCount3=function () {
        factoryParameterSettingService.indexMaintainPlan({}, function(response){
            if(response.data!=''&&response.data!=null&&response.data!=undefined&&response.errorCode=='000000'){
                $scope.list3=response.data.plans;//滚动列表
                console.log($scope.list3);
                $scope.todayPlanNum=response.data.todayPlanNum;// 今日计划数量
                $scope.executedPlanNum=response.data.executedPlanNum;//已执行计划数量
                $scope.notExecutedPlanNum=response.data.notExecutedPlanNum;//未执行计划数量
                //超过5条滚动
                if($scope.list3.length>5){
                    test3();
                    $("#maintainTable").css('height','170px');
                }else{
                    $("#maintainTable").css('height','auto');
                }
            }else{
                console.log(response.errorMessage);
            }
        });
    };
    $scope.indexTroubleCount3();
    /*首页润滑计划统计 end*/
    /*滚动公用方法 start*/
    function test1() {
        var timer1=$timeout(function () {
            $('.home-table div p').css('margin-top','0');
            $('.table-animation').animate({marginTop:"-34px"},function () {
                var arr1=$scope.list1.shift();
                // console.log(arr1);
                $scope.list1.push(arr1);
                // console.log($scope.list1);
                $timeout(function () {
                    test1();
                },1000)
            });
        },1000)
    }
    function test2() {
        var timer2=$timeout(function () {
            $('.table-animation2').animate({marginTop:"-34px"},function () {
                var arr2=$scope.list2.shift();
                // console.log(arr);
                $scope.list2.push(arr2);
                $timeout(function () {
                    test2();

                },1000)
            });
        },1000)
    }
    function test3() {
        var timer3=$timeout(function () {
            $('.table-animation3').animate({marginTop:"-34px"},function () {
                var arr2=$scope.list3.shift();
                // console.log(arr);
                $scope.list3.push(arr2);
                $timeout(function () {
                    test3();

                },1000)
            });
        },1000)
    }
    /*滚动公用方法 end*/
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

    //故障类型
    $scope.troubleTypeData=[{
        name: '其他',
        y: 25
    }, {
        name: '电气故障',
        y: 25
//                sliced: true,
//                selected: true
    }, {
        name: '机械故障',
        y: 25
    }, {
        name: '其他故障',
        y: 25
    }];
    troubleType($scope.troubleTypeData);

    //故障部位
    $scope.troublePartData=[{
        name: '燃烧头',
        y: 25
    }, {
        name: '点火电极',
        y: 25
//                sliced: true,
//                selected: true
    }, {
        name: 'UV电眼',
        y: 25
    }, {
        name: '燃气管路连接法兰',
        y: 25
    }];
    troublePart($scope.troublePartData);

    //故障等级
    $scope.troubleLevelData=[{
        name: '大故障',
        y: 25
    }, {
        name: '中故障',
        y: 35
//                sliced: true,
//                selected: true
    }, {
        name: '小故障',
        y: 30
    }, {
        name: '其他',
        y: 10
    }];
    troubleLevel($scope.troubleLevelData);
});

//故障类型
function troubleType(DATA) {
    new Highcharts.Chart({
        credits: {
            enabled:false,
            text: 'Example.com',
            position:{
                align:'center'
            },
            href: 'http://www.example.com'
        },
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: "pie",
            height:175,
            // width:405,
            renderTo:"container"
        },
        title: {
            text: '故障类型',
            floating:true,
            y:160,
            style:{
                fontSize:'14px'
            }
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f}%',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        legend: {
            align: 'left',
            verticalAlign: 'top',
            layout: 'vertical',
            x: 0,
            y: 0,
            floating: true
        },
        series: [{
            name: '占比',
            colorByPoint: true,
            data: DATA
        }]
    });
}
//故障部位
function troublePart(DATA) {
    new Highcharts.Chart({
        credits: {
            enabled:false,
            text: 'Example.com',
            position:{
                align:'center2'
            },
            href: 'http://www.example.com'
        },
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: "pie",
            height:175,
            // width:405,
            renderTo:"container1"
        },
        title: {
            text: '故障部位',
            floating:true,
            y:160,
            style:{
                fontSize:'14px'
            }
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f}%',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                },
                // x:100,
                showInLegend: false
            }
        },
        legend: {
            align: 'left',
            verticalAlign: 'top',
            layout: 'vertical',
            x: 0,
            y: 0,
            floating: true
        },
        series: [{
            name: '占比',
            colorByPoint: true,
            data: DATA
        }]
    });
}
//故障等级
function troubleLevel(DATA) {
    new Highcharts.Chart({
        credits: {
            enabled:false,
            text: 'Example.com',
            position:{
                align:'center2'
            },
            href: 'http://www.example.com'
        },
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: "pie",
            height:175,
            // width:405,
            renderTo:"container2"
        },
        title: {
            text: '故障等级',
            floating:true,
            y:160,
            style:{
                fontSize:'14px'
            }
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        legend: {
            align: 'left',
            verticalAlign: 'top',
            layout: 'vertical',
            x: 0,
            y: 0,
            floating: true
        },
        series: [{
            name: '占比',
            colorByPoint: true,
            data: DATA
        }]
    });
}
