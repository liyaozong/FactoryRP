/**
 * Created by jayvenLee on 2017/11/11.
 */
authorizationApp.controller('teamManageController',function ($scope,$http,UrlService,$filter, $resource, $location, $state,teamManageService) {
//配置分页基本参数
//    alert(1)
    $scope.WebURL=UrlService.getUrl('authorization');
    $scope.businessTypeChange=function(typeCode){
       if (typeCode=='OPERATION_CREDIT_ORDER') {
           $scope.isJYD = true;
       }else{
           $scope.isJYD = false;
       }
    }
    /*查询所有用户 start*/
    $scope.onQuery=function () {
        teamManageService.getAllGroup({
            keywords:$scope.QueryName
        }, function(data){
            if (data.status==0){
                console.log(data)
                $scope.orderList = data.obj;
            }else{
                popupDiv('SaveSuccess')
                 $(".Message").html(data.message);
            }
        });
    };
    /*查询所有用户 end*/
   $scope.onQuery()
    
    /*新增 功能点 start addfunctionBalance*/
    $scope.selected1 = [] ;//组长code       
    $scope.selectedName1 = [] ;//组长name      
    $scope.isChecked1 = function(id){  
        return $scope.selected1.indexOf(id) >= 0 ;  
    } ;     
    $scope.updateSelection1 = function($event,id,name){  
        if($event.target.checked){  
            $scope.selected1.push(id) ;  
            $scope.selectedName1.push(name) ;  
        }else{  
            $scope.selected1.splice($scope.selected1.indexOf(id),1);  
            $scope.selectedName1.splice($scope.selectedName1.indexOf(id),1);  
        }  
    } ;  
    $scope.selected2 = [] ;//组员code       
    $scope.selectedName2 = [] ;//组员name      
    $scope.isChecked2 = function(id){  
        return $scope.selected2.indexOf(id) >= 0 ;  
    } ;     
    $scope.updateSelection2 = function($event,id,name){  
        if($event.target.checked){  
            $scope.selected2.push(id) ;  
            $scope.selectedName2.push(name) ;  
        }else{  
            $scope.selected2.splice($scope.selected2.indexOf(id),1);  
            $scope.selectedName2.splice($scope.selectedName2.indexOf(id),1);  
        }  
    } ;  
     $scope.selected3 = [] ;//组员code       
    $scope.selectedName3 = [] ;//组员name      
    $scope.isChecked3 = function(id){  
        return $scope.selected3.indexOf(id) >= 0 ;  
    } ;     
    $scope.updateSelection3 = function($event,id,name){  
        if($event.target.checked){  
            $scope.selected3.push(id) ;  
            $scope.selectedName3.push(name) ;  
        }else{  
            $scope.selected3.splice($scope.selected3.indexOf(id),1);  
            $scope.selectedName3.splice($scope.selectedName3.indexOf(id),1);  
        }  
    } ;  
    $scope.addType='group';//默认类型为组
    $scope.addfunctionBalance=function(){
        if($scope.businessType==null||$scope.businessType==''||$scope.businessType==undefined){
            $("#businessType").focus();
        }else if($scope.groupCode==null||$scope.groupCode==''||$scope.groupCode==undefined){
            $("#groupName").focus();
        }/*else if($scope.addType==null||$scope.addType==''||$scope.addType==undefined){
            $("#addType").focus();
        }else if($scope.addPercode==null||$scope.addPercode==''||$scope.addPercode==undefined){
            $("#addPercode").focus();
        }*/else{
            if($scope.code==null||$scope.code==''||$scope.code==undefined){
                var pa={
                productCodes:[$scope.businessType],
                productNames:[$("#businessType").find("option:selected").text()],
                name:$("#groupName").find("option:selected").text(),//组名称
                groupCode:$scope.groupCode,//组编码
                 managerNames:$scope.selectedName1,//部门内员工姓名
                 managerCodes:$scope.selected1,//部门内员工编码
                 employeeCodes:$scope.selected2,//管理员列表编码
                 employeeNames:$scope.selectedName2,//组管理员列表姓名编码
                provinceCodes:$scope.selected3,//省列表编号
                provinceNames:$scope.selectedName3//省列表名称
              };
            }else{
                var pa={
                code:$scope.code,
                productCodes:[$scope.businessType],
                productNames:[$("#businessType").find("option:selected").text()],
                name:$("#groupName").find("option:selected").text(),//组名称
                groupCode:$scope.groupCode,//组编码
                 managerNames:$scope.selectedName1,//部门内员工姓名
                 managerCodes:$scope.selected1,//部门内员工编码
                 employeeCodes:$scope.selected2,//管理员列表编码
                 employeeNames:$scope.selectedName2,//组管理员列表姓名编码
                provinceCodes:$scope.selected3,//省列表编号
                provinceNames:$scope.selectedName3//省列表名称
            };
            }
            
            console.log(pa)
           teamManageService.addGroup({
        },pa, function(data){
            if(data.status==0){
                 $scope.reload()
                hideDiv('functionBalance')
            }else{
                hideDiv('functionBalance')
                popupDiv("SaveSuccess")
                $(".Message").html(data.message);
            }
        });
        }
    };
    /*新增 功能点 end addfunctionBalance*/
    /*删除 功能点 start deleteFunction*/
    $scope.deletefunctionBalance=function(code){
        popupDiv('deleteFunction');
        $scope.dellCode=code;
    };
    $scope.removeGroup=function(){
         teamManageService.removeGroup({
        },{},$scope.dellCode, function(data){
            if(data.status==0){
                $scope.reload()
                hideDiv('deleteFunction')
            }else{
                popupDiv("SaveSuccess")
                hideDiv('deleteFunction')
                $(".Message").html(data.message);
            }
        });
    }
    /*删除 功能点 end deleteFunction*/
    /*获取功能点详情 start */
    $scope.getGroupInfo=function(code){
        $scope.headerText = "编辑小组";
        $scope.code = code;
         popupDiv('functionBalance');
         teamManageService.getGroupInfo({
           
        },code, function(data){
            if(data.status==0){
                console.log(data)
                $scope.businessType=data.obj.productCodes[0];
                //$scope.groupName =  $scope.businessType;
                $scope.groupCode=data.obj.groupCode;
                $scope.groupName=data.obj.name;
                $scope.selected2=data.obj.employeeCodes;
                $scope.selectedName2=data.obj.employeeNames;
                $scope.selected1=data.obj.managerCodes;
                $scope.selectedName1=data.obj.managerNames;
                $scope.selected3=data.obj.provinceCodes;   
                $scope.selectedName3=data.obj.provinceNames; 
                 if ( $scope.businessType=='OPERATION_CREDIT_ORDER') {
                   $scope.isJYD = true;
               }else{
                   $scope.isJYD = false;
               }  
            }else{
                popupDiv("SaveSuccess")
                hideDiv('deleteFunction')
                $(".Message").html(data.message);
            }
        });
    }
    /*获取功能点详情 end */

    /*处理之后保留在当前页面start*/
    $scope.reload=function(){
        $scope.onQuery();
    };
    /*处理之后保留在当前页面end*/
     /*获取业务类型start*/
          teamManageService.getListProductType({
        }, function(data){
            if(data.status==0){
                 $scope.allListProductType = data.obj;
            }else{
                popupDiv("SaveSuccess")
                $(".Message").html(data.message);
            }
        });
    
    /*获取业务类型end*/
    /*部門查詢start*/
          teamManageService.getDepartment({
        }, function(data){
            if(data.status==0){
               $scope.allDepartment = data.obj;
            }else{
                popupDiv("SaveSuccess")
                $(".Message").html(data.message);
            }
        });
    
    /*部門查詢end*/
     /*員工查詢start*/
          teamManageService.getEmployee({
        }, function(data){
            if(data.status==0){
                $scope.allEmployee = data.obj;
            }else{
                popupDiv("SaveSuccess")
                $(".Message").html(data.message);
            }
        });
    
    /*員工查詢end*/
     /*区域查询start*/
     teamManageService.getListProvince({
        }, function(data){
            if(data.status==0){
                $scope.provinceLists = data.obj;
            }else{
                popupDiv("SaveSuccess")
                $(".Message").html(data.message);
            }
        });
    /*区域查询end*/
    /*新增小组start*/
     $scope.addTeam=function(){
        $scope.headerText = "新增小组";
        $scope.businessType="";
        $scope.groupCode="";
        $scope.code="";
        $scope.selected1=[];
        $scope.selectedName1=[];
        $scope.selected2=[];
        $scope.selectedName2=[];
        $scope.selected3=[];    
        $scope.selectedName3=[];    

     }
    /*新增小组end*/

    /***************************************************************
     当页码和页面记录数发生变化时监控后台查询
     如果把currentPage和itemsPerPage分开监控的话则会触发两次后台事件。
     ***************************************************************/

});