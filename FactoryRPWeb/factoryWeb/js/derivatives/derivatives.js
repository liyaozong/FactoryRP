
myApp.directive("deviceTypeList",["deviceType",'$compile','$filter',function(deviceType,$compile,$filter){
    return{
        restrict: 'A',
        compile: function(tEle, tAttrs, transcludeFn) {
            return function(scope, ele, attrs){
                //查询设备类型列表
                deviceType.list().success(function(data){
                    if(data.errorCode='000000'){
                        // console.log('data',data.data);
                        scope.deviceTypeList=data.data;
                        scope.hierarchy=0;
                        var str='';
                        if(scope.deviceTypeList.length>0){
                            scope.deviceTypeList.forEach(function (n,i) {
                                // console.log(n,i);
                                if(n.parentId=='-1'){
                                    str+='<ul hierarchy="'+scope.hierarchy+'" isFlog="true">';
                                    str+='<li class="emptyTd">' +
                                        '<a href="javascript:void (0)" ng-click="subShowDP('+n.id+',$event)"> + </a> ' +
                                        '</li>';
                                    str+='<li class="deviceName">'+n.name+'</li>';
                                    str+='<li class="ccc">';
                                    str+='<a href="javascript:void (0)" class="a_btn" ng-click="addSomeDP('+n.id+','+n.parentId+',\''+n.name+'\',1)"> 添加同级 </a>';
                                    str+='<a href="javascript:void (0)" class="a_btn" ng-click="addSomeDP('+n.id+','+n.parentId+',\''+n.name+'\',2)"> 添加下级</a>';
                                    str+='<a href="javascript:void (0)" class="a_btn" ng-click="addSomeDP('+n.id+','+n.parentId+',\''+n.name+'\',3)">编辑</a>';
                                    str+='<a href="javascript:void (0)" class="a_btn" ng-click="deleteDP('+n.id+',\''+n.name+'\')"> 删除 </a>';
                                    str+='</li>';
                                    str+='</ul>';
                                }

                            });
                            var html=$compile(str)(scope);
                            ele.html(html);
                        }else {
                            str+='<ul><li style="width: 100%;text-align: center;">暂无数据。。。请' +
                                '<a href="javascript:void (0)" class="a_btn" ng-click="addSomeDP(\'\',-1,\'\',1)"> 添加设备类型 </a>' +
                                '</li></ul>';
                            var html1=$compile(str)(scope);
                            ele.html(html1);
                        }

                    }
                });
                scope.subShowDP=function (id,$event) {
                    scope.subList=[];
                    var str='';
                    str+='<div class="subDiv">';
                    var $tr=$($event.target).parent().parent();
                    var hp=parseInt($tr.attr('hierarchy'))+1;
                    scope.deviceTypeList.forEach(function (n,i) {
                        if(n.parentId==id){
                            scope.subList.push(n);
                            str+='<ul hierarchy="'+hp+'" style="padding-left: '+hp*32+'px" isFlog="true">';
                            str+='<li class="emptyTd" style="left: '+hp*32+'px">' +
                                '<a href="javascript:void (0)" ng-click="subShowDP('+n.id+',$event)"> + </a> ' +
                                '</li>';
                            str+='<li class="deviceName">'+n.name+'</li>';
                            str+='<li class="ccc">';
                            str+='<a href="javascript:void (0)" class="a_btn" ng-click="addSomeDP('+n.id+','+n.parentId+',\''+n.name+'\',1)"> 添加同级 </a>';
                            str+='<a href="javascript:void (0)" class="a_btn" ng-click="addSomeDP('+n.id+','+n.parentId+',\''+n.name+'\',2)"> 添加下级</a>';
                            str+='<a href="javascript:void (0)" class="a_btn" ng-click="addSomeDP('+n.id+','+n.parentId+',\''+n.name+'\',3)">编辑</a>';
                            str+='<a href="javascript:void (0)" class="a_btn" ng-click="deleteDP('+n.id+',\''+n.name+'\')"> 删除 </a>';
                            str+='</li>';
                            str+='</ul>';
                        }
                    });
                    str+='</div>';
                    if(scope.subList.length>0){
                        if($tr.attr('isFlog')=='true'||$tr.attr('isFlog')==undefined){
                            var html=$compile(str)(scope);
                            $tr.after(html);
                            $tr.attr('isFlog','false');
                            $($event.target).html('-');
                        }else {
                            $tr.attr('isFlog','true');
                            $($event.target).html('+');
                            $tr.next().remove();

                        }
                    }
                }
            }
        }
    }
}]);