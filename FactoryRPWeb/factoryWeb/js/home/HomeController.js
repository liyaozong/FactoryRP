/**
 * Created by SHYL on 2016/6/13.
 */
myApp.controller("HomeController", function($rootScope,UrlService,$cookies,$state, $scope, $resource, $timeout, $http,$location,$log,AuthorizationService,departmentManageService,userManagementService,$window,$timeout) {


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
    for(var i=1;i<100;i++){
        $scope.list1.push({
            name:'故障名称'+i,
            type:'故障状态'+i
        });
        $scope.list2.push({
            name:'点巡检名称'+i,
            type:'巡检状态'+i
        });
        $scope.list3.push({
            name:'润滑计划名称'+i,
            type:'润滑计划状态'+i
        });
    }

    // $('.table-animation').animate({marginTop:"-34px"})
    function test1() {
        var timer1=$timeout(function () {
            // console.log(k);
            // k++;
            $('.table-animation').animate({marginTop:"-34px"},function () {
                var arr1=$scope.list1.shift();
                // console.log(arr);
                $scope.list1.push(arr1);
                // console.log($scope.list1)
                $timeout(function () {
                    test1();

                },1000)
            });
        },1000)
    }
    test1();
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
    test2();
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
    test3();



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

});