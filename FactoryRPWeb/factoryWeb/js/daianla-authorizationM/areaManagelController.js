/**
 * Created by jayvenLee on 2017/11/11.
 */
authorizationApp.controller('areaManagelController',function ($scope,$filter, $resource, $location,UrlService, $state,areaManagelService,$http) {
    $scope.WebURL=UrlService.getUrl('authorization');
    //配置分页基本参数 start
    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 15
    };
    //配置分页基本参数 end
    $scope.orderList = areaManagelService.getOrderList();
    /*查询区域列表 start*/
    $scope.onQuery=function () {
        areaManagelService.queryOrder({
            currentPage: $scope.paginationConf.currentPage,
            itemsPerPage: $scope.paginationConf.itemsPerPage,
            depName:$scope.depName
        }, function(response){
            if(response.obj.totalCount>=1){
                $scope.paginationConf.totalItems = response.obj.totalCount;
            }else{
                $scope.paginationConf.totalItems = 0;
                $scope.orderList.length = 0;
            }
        });
    };
    /*查询区域列表 end*/

    /*查询所有省 start*/
    $scope.queryAllProvince=function(){
        $scope.queryAllProvince= areaManagelService.queryProvince(function(data){
            if(data.status==0){
                if(data!=null){
                    //TODO-业务逻辑
                    $scope.queryAllPro = data.obj;
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
    $scope.queryAllProvince();
    /*查询所有省 end*/

    /*查询归属部门 start*/
    $scope.queryManager=function(){
        $scope.queryCommonManager= areaManagelService.manager(function(data){
            if(data.status==0){
                if(data!=null){
                    //TODO-业务逻辑
                    $scope.queryManager = data.obj;
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
    $scope.queryManager();
    /*查询归属部门 end*/

 /*新增区域信息 start*/
    $scope.addNewArea=function(){
        /*选中的省 信息 start*/
        $scope.NewArea=[];
        $(".addAreaP input[type=checkbox]").each(function(){
            var data={};
            if(this.checked){
//                console.log($(this).val());
                data.opeProvince=$(this).val();
//                console.log(data);
                $scope.NewArea.push(data);
//                console.log($scope.NewArea);
            }
        });
        /*选中的省 信息 end*/
    if($scope.productSelected==''||$scope.productSelected==null||$scope.productSelected==undefined){
        $("#departName").focus();
    }else{
        var pa={

                "opeType":1,
                "opeId":$scope.productSelected,
                "voList":$scope.NewArea
        };
//        console.log(pa);
        var par={params:pa};
        $http.post($scope.WebURL+'/employee/addDepArea',pa).success(function(response){
            hideDiv('areaBalance');
            popupDiv('SaveSuccess');
            $(".Message").html(response.message);
        }).error(function(response){
            console.log(response.message);
        })
    }
};
/*新增区域信息 end*/

/*根据区域id号查询区域信息 editArea start*/
    $scope.editAreaBalance=function(res){
//        $(".editAreafunction input[type=checkbox]").removeAttr('checked');
        $scope.editarea=[];
        popupDiv('editAreaBalance');
        $scope.editproductSelected = res.opeName;
        var str="";
         str=res.opeProvince;
        $scope.editarea=str.split(",");//这个就是你要的数组
//console.log( $scope.editarea);

     /*   获取当前部门拥有的区域 复选框选中 start*/
        var inputVal=$(".editAreafunction input[type=checkbox]");
        for(var i=0;i<inputVal.length;i++){
//                        console.log(inputVal.eq(i).val());
//                        console.log($scope.roles);
            if($scope.editarea.indexOf(inputVal.eq(i).val())!=-1){
//                            console.log($scope.roles);
                inputVal.eq(i).attr('checked','checked');
            }
        }
     /*   获取当前部门拥有的区域 复选框选中 end*/
        /*修改区域信息 start  */
        $scope.upateAreaInfo=function(){
            /*添加区域 信息 start*/
            $scope.updateArea=[];
            $(".editAreafunction  input[type=checkbox]").each(function(){
                var data={};
                if(this.checked){
                    data.opeProvince=$(this).val();
                    $scope.updateArea.push(data);
//                    console.log($scope.updateArea);
                }
            });
//            alert($scope.NewRoles);
            /*添加区域 信息 end*/
            if($scope.editproductSelected==''||$scope.editproductSelected==null||$scope.editproductSelected==undefined){
                $("#editdepartName").focus();
            }else{
                var pa={
                    "opeType":1,
                    "opeId":res.opeId,
                    "voList":$scope.updateArea
                };
//                console.log(pa);
//                console.log(pa1);
                var par={params:pa};
                $http.post($scope.WebURL+'employee/updateDepArea',pa).success(function(response){
                    hideDiv('editAreaBalance');
                    popupDiv('SaveSuccess');
                    $(".Message").html(response.message);
                }).error(function(response){
                    console.log(response.message);
                })
            }

        };
        /*修改区域信息 end  */
    };
    /*根据区域id号查询区域信息 editArea end*/

    /*删除区域管理 deleteAreaManagement start*/
    $scope.deleteAreaManagement=function(res){
        popupDiv("deleteArea");
        $scope.saleName=res.opeName;
        var pa={
            "opeType":1,
            "opeId":res.opeId
        };
        $scope.deleteAreaManage=function(){
            var par={params:pa};
            $http.get($scope.WebURL+'employee/delDepArea',par).success(function(response){
                hideDiv('deleteArea');
                popupDiv('SaveSuccess');
                $(".Message").html(response.message);
            }).error(function(response){
//                alert(response.message);
            })
        }
    };
    /*删除区域管理 deleteAreaManagement end*/
//    /*刷新页面 start*/
    /*处理之后保留在当前页面start*/
//    $scope.reload=function(){
//        $scope.paginationConf.currentPage = $(".page-total input").val();
//        $scope.onQuery();
//    };
    /*处理之后保留在当前页面end*/
//    /*刷新页面 end*/
    /***************************************************************
     当页码和页面记录数发生变化时监控后台查询
     如果把currentPage和itemsPerPage分开监控的话则会触发两次后台事件。
     ***************************************************************/
    $scope.$watch('paginationConf.currentPage + paginationConf.itemsPerPage', $scope.onQuery);
});