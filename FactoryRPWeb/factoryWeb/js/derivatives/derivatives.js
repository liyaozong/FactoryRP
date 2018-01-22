
//设备类型指令
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

//备件类型指令
myApp.directive("deviceSparesList",["deviceSpares",'$compile','$filter',function(deviceSpares,$compile,$filter){
    return{
        restrict: 'A',
        compile: function(tEle, tAttrs, transcludeFn) {
            return function(scope, ele, attrs){
                //查询备件类型列表
                deviceSpares.list().success(function(data){
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
                                        '<a href="javascript:void (0)" ng-click="subShowDS('+n.id+',$event)"> + </a> ' +
                                        '</li>';
                                    str+='<li class="deviceName">'+n.name+'</li>';
                                    str+='<li class="ccc">';
                                    str+='<a href="javascript:void (0)" class="a_btn" ng-click="addSomeDS('+n.id+','+n.parentId+',\''+n.name+'\',1)"> 添加同级 </a>';
                                    str+='<a href="javascript:void (0)" class="a_btn" ng-click="addSomeDS('+n.id+','+n.parentId+',\''+n.name+'\',2)"> 添加下级</a>';
                                    str+='<a href="javascript:void (0)" class="a_btn" ng-click="addSomeDS('+n.id+','+n.parentId+',\''+n.name+'\',3)">编辑</a>';
                                    str+='<a href="javascript:void (0)" class="a_btn" ng-click="deleteDS('+n.id+',\''+n.name+'\')"> 删除 </a>';
                                    str+='</li>';
                                    str+='</ul>';
                                }

                            });
                            var html=$compile(str)(scope);
                            ele.html(html);
                        }else {
                            str+='<ul><li style="width: 100%;text-align: center;">暂无数据。。。请' +
                                '<a href="javascript:void (0)" class="a_btn" ng-click="addSomeDS(\'\',-1,\'\',1)"> 添加备件类型 </a>' +
                                '</li></ul>';
                            var html1=$compile(str)(scope);
                            ele.html(html1);
                        }

                    }
                });
                scope.subShowDS=function (id,$event) {
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
                                '<a href="javascript:void (0)" ng-click="subShowDS('+n.id+',$event)"> + </a> ' +
                                '</li>';
                            str+='<li class="deviceName">'+n.name+'</li>';
                            str+='<li class="ccc">';
                            str+='<a href="javascript:void (0)" class="a_btn" ng-click="addSomeDS('+n.id+','+n.parentId+',\''+n.name+'\',1)"> 添加同级 </a>';
                            str+='<a href="javascript:void (0)" class="a_btn" ng-click="addSomeDS('+n.id+','+n.parentId+',\''+n.name+'\',2)"> 添加下级</a>';
                            str+='<a href="javascript:void (0)" class="a_btn" ng-click="addSomeDS('+n.id+','+n.parentId+',\''+n.name+'\',3)">编辑</a>';
                            str+='<a href="javascript:void (0)" class="a_btn" ng-click="deleteDS('+n.id+',\''+n.name+'\')"> 删除 </a>';
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



//菜单列表指令

myApp.directive("menuManageMentList",["userManageMent",'$compile','$filter',function(userManageMent,$compile,$filter){
    return{
        restrict: 'A',
        compile: function(tEle, tAttrs, transcludeFn) {
            return function(scope, ele, attrs){
                //查询所有菜单
                scope.onQuery=function () {
                    // console.log('查询所有菜单');
                    userManageMent.queryCorporateMenu().success(function(data){
                        if(data.errorCode='000000'){
                            // console.log('data',data.data);
                            scope.deviceTypeList=data.data;
                            scope.orderList=data.data;
                            scope.hierarchy=0;
                            var str='';
                            // console.log(scope.deviceTypeList,'---');
                            if(scope.deviceTypeList.length>0){
                                scope.deviceTypeList.forEach(function (n,i) {
                                    // console.log(n,i);
                                    if(n.parentId==undefined||n.parentId==-1){
                                        str+='<ul hierarchy="'+scope.hierarchy+'" isFlog="true">';
                                        str+='<li class="emptyTd">' +
                                            '<a href="javascript:void (0)" ng-click="subShowDP('+n.id+',$event)"> + </a> ' +
                                            '</li>';
                                        str+='<li class="menuCF">'+(i+1)+'</li>';
                                        str+='<li class="menuCM">'+n.name+'</li>';
                                        str+='<li class="menuCE">'+n.url+'</li>';
                                        str+='<li class="ccc">';
                                        // str+='<a href="javascript:void (0)" class="a_btn" ng-click="editfunctionBalance('+n.id+',\''+n.name+'\','+n.orderNumber+','+n.parentId+',\''+n.url+'\',\''+n.remark+'\')">编辑</a>';
                                        str+='<a href="javascript:void (0)" class="a_btn" ng-click="deletefunctionBalance('+n.id+',\''+n.name+'\')"> 删除 </a>';
                                        str+='</li>';
                                        str+='</ul>';
                                    }

                                });
                                var html=$compile(str)(scope);
                                ele.html(html);
                            }else {
                                str+='<ul><li style="width: 100%;text-align: center;">暂无数据。。。请' +
                                    '<a href="javascript:void (0)" class="a_btn" ng-click="addfunctionBalance(\'\',-1,\'\',1)"> 添加菜单 </a>' +
                                    '</li></ul>';
                                var html1=$compile(str)(scope);
                                ele.html(html1);
                            }

                        }
                    });
                };
                scope.onQuery();
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
                            str+='<li class="menuCF" style="width: '+(200-hp*32)+'px">'+(i+1)+'</li>';
                            str+='<li class="menuCM">'+n.name+'</li>';
                            str+='<li class="menuCE">'+n.url+'</li>';
                            str+='<li class="ccc">';
                            // str+='<a href="javascript:void (0)" class="a_btn" ng-click="editfunctionBalance('+n.id+',\''+n.name+'\','+n.orderNumber+','+n.parentId+',\''+n.url+'\',\''+n.remark+'\')">编辑</a>';
                            str+='<a href="javascript:void (0)" class="a_btn" ng-click="deletefunctionBalance('+n.id+',\''+n.name+'\')"> 删除 </a>';
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


//故障类型指令
myApp.directive("deviceTroubleTypeList",["deviceTroubleType",'$compile','$filter',function(deviceTroubleType,$compile,$filter){
    return{
        restrict: 'A',
        compile: function(tEle, tAttrs, transcludeFn) {
            return function(scope, ele, attrs){
                //查询设备类型列表
                deviceTroubleType.list().success(function(data){
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

//审核流程canvas指令
myApp.directive("canvasDiv",['$compile','$filter',function($compile,$filter){
    return{
        restrict: 'A',
        compile: function(tEle, tAttrs, transcludeFn) {
            return function(scope, ele, attrs){
                var width=ele.parent().css('width');
                // console.log(width);
                ele.attr('width',width);
                //初始坐标
                var moveX=0,moveY=0,w=30,rectW=100;
                var h=15;
                var up_x=8;
                var up_y=6;
                //创建开始图标
                var beginFnc=function (ctx,x,y,w,h,color,t) {
                    var str=t==0?'开始':'结束';
                    ctx.beginPath();
                    ctx.strokeStyle=color;
                    ctx.rect(x,y,w,h);
                    ctx.stroke();
                    ctx.beginPath();
                    ctx.font="14px Georgia";
                    ctx.fillStyle='#131313';
                    ctx.fillText(str,x+10,y+20,w);
                    moveX=moveX+w;
                };
                //会签矩形
                var hqFnc=function (ctx,x,y,w,h,color,type,t) {
                    var hc='';
                    var tc='';
                    ctx.beginPath();
                    ctx.strokeStyle=color;
                    ctx.rect(x,y,w,h);
                    ctx.stroke();
                    ctx.beginPath();
                    ctx.font="14px Georgia";
                    ctx.fillStyle='#131313';
                    if(type==1){
                        hc='单人会签';
                    }else if(type==2){
                        hc='多人会签';
                    }
                    if(t==0){
                        tc='开始';
                    }else if(t==1){
                        tc='结束';
                    }
                    ctx.fillText(hc+tc,x+10,y+20,w);
                    moveX=moveX+w;
                };

                //直线箭头
                var jtFnc=function (ctx,x1,y1) {
                    ctx.beginPath();
                    ctx.strokeStyle='#131313';
                    ctx.moveTo(x1,y1+h);
                    ctx.lineTo(x1+w,y1+h);
                    ctx.lineTo(x1+w-up_x,y1+h+up_y);
                    ctx.moveTo(x1+w,y1+h);
                    ctx.lineTo(x1+w-up_x,y1+h-up_y);
                    ctx.stroke();
                    moveX=moveX+w;
                };

                //直线
                var zxFnc=function (ctx,x1,y1) {
                    ctx.beginPath();
                    ctx.strokeStyle='#131313';
                    ctx.moveTo(x1,y1+h);
                    ctx.lineTo(x1+w,y1+h);
                    ctx.stroke();
                    moveX=moveX+w;
                };
                //每一步
                var bzjtFnc=function (ctx,x1,y1,l,num,type,name) {
                    var max_h=l*40;
                    var hh,y2,ww;
                    ctx.beginPath();
                    ctx.strokeStyle='#000000';
                    ctx.font="14px Georgia";
                    if(type==0){
                        hh=(l-num)*(max_h/l);
                        y2=y1+h-hh;
                        //分散箭头
                        ctx.moveTo(x1,y1+h);
                        ctx.lineTo(x1,y2);
                        ctx.lineTo(x1+w,y2);
                        ctx.lineTo(x1+w-up_x,y2+up_y);
                        ctx.moveTo(x1+w,y2);
                        ctx.lineTo(x1+w-up_x,y2-up_y);
                        //审核人员矩形
                        ctx.fillStyle='#ff6f09';
                        ctx.fillRect(x1+w,y2-15,rectW,30);
                        ctx.fillStyle='#ffffff';
                        ctx.fillText(name,x1+w+10,y2-15+20,rectW-20);
                        //收拢箭头
                        ww=x1+w+rectW;
                        ctx.moveTo(ww,y2);
                        ctx.lineTo(ww+w,y2);
                        ctx.lineTo(ww+w,y1+h);


                    }else if(type==1){
                        hh=(l+l+1-num)*(max_h/l);
                        y2=y1+h+hh;
                        //分散箭头
                        ctx.moveTo(x1,y1+h);
                        ctx.lineTo(x1,y2);
                        ctx.lineTo(x1+w,y2);
                        ctx.lineTo(x1+w-up_x,y2+up_y);
                        ctx.moveTo(x1+w,y2);
                        ctx.lineTo(x1+w-up_x,y2-up_y);
                        //审核人员矩形
                        ctx.fillStyle='#ff6f09';
                        ctx.fillRect(x1+w,y2-15,rectW,30);
                        ctx.fillStyle='#ffffff';
                        ctx.fillText(name,x1+w+10,y2-15+20,rectW-20);
                        //收拢箭头
                        ww=x1+w+rectW;
                        ctx.moveTo(ww,y2);
                        ctx.lineTo(ww+w,y2);
                        ctx.lineTo(ww+w,y1+h);

                    }else  if(type==2){
                        //分散箭头
                        ctx.moveTo(x1,y1+h);
                        ctx.lineTo(x1+w,y1+h);
                        ctx.lineTo(x1+w-up_x,y1+h+up_y);
                        ctx.moveTo(x1+w,y1+h);
                        ctx.lineTo(x1+w-up_x,y1+h-up_y);
                        //审核人员矩形
                        ctx.fillStyle='#ff6f09';
                        ctx.fillRect(x1+w,y1,rectW,30);
                        ctx.fillStyle='#ffffff';
                        ctx.fillText(name,x1+w+10,y1+20,rectW-20);
                        //收拢箭头
                        ww=x1+w+rectW;
                        ctx.moveTo(ww,y1+h);
                        ctx.lineTo(ww+w,y1+h);

                    }

                    ctx.stroke();
                    // moveX=moveX+x1+w+rectW+rectW;
                };

                scope.$watch(tAttrs.canvasDiv, function(newValue, oldValue, scope) {
                    // console.log(ele[0],'=',document.getElementById("myCanvas"));
                    if (angular.isDefined(newValue)) {
                        // console.log(newValue,'canvasData');
                        var list=newValue;
                        var c=ele[0];
                        var ctx=c.getContext("2d");
                        ctx.beginPath();
                        ctx.clearRect(0,0,parseInt(width),400);
                        ctx.closePath();
                        moveX=30;moveY=185;
                        //开始
                        beginFnc(ctx,moveX,moveY,50,30,'green',0);

                        // console.log(list,list.length);
                        for(var i=0;i<list.length;i++){
                            var l=list[i].processAuditorList.length;
                            var mu=l%2;

                            //会签状态绘制开始
                            if(list[i].processAuditorList.length>1){
                                jtFnc(ctx,moveX,moveY);
                                hqFnc(ctx,moveX,moveY,100,30,'#d43cee',list[i].handleDemandType,0);
                            }

                            if(l>0){

                                //绘制每一步审核人员
                                zxFnc(ctx,moveX,moveY);
                                for(var j=0;j<list[i].processAuditorList.length;j++){
                                    // console.log(mu,parseInt(l/2),j,'==---');
                                    if(mu==1){
                                        var kk=parseInt(l/2)+1;
                                        // console.log(kk,j,'22222');
                                        if(j+1<kk){
                                            // console.log(kk,'33333');
                                            bzjtFnc(ctx,moveX,moveY,kk-1,j,0,list[i].processAuditorList[j]);
                                        }else  if(j+1==kk){
                                            bzjtFnc(ctx,moveX,moveY,kk-1,j,2,list[i].processAuditorList[j]);
                                        }else  if(j+1>kk){
                                            // console.log(kk,'44444');
                                            bzjtFnc(ctx,moveX,moveY,kk-1,j,1,list[i].processAuditorList[j]);
                                        }
                                        // console.log(j,'jjjjjj')
                                    }else {
                                        var kkk=parseInt(l/2);
                                        // console.log(kkk,j,'kkkkkkkk');
                                        if(j<kkk){
                                            bzjtFnc(ctx,moveX,moveY,kkk,j,0,list[i].processAuditorList[j]);
                                        }else  if(j>=kkk){
                                            bzjtFnc(ctx,moveX,moveY,kkk,j+1,1,list[i].processAuditorList[j]);
                                        }
                                    }
                                    ctx.beginPath();
                                }

                                moveX=moveX+w+rectW+w;
                            }


                            //会签状态绘制结束
                            if(list[i].processAuditorList.length>1){
                                jtFnc(ctx,moveX,moveY);
                                hqFnc(ctx,moveX,moveY,100,30,'#0506ee',list[i].handleDemandType,1);
                            }
                        }
                        //结束
                        jtFnc(ctx,moveX,moveY);
                        beginFnc(ctx,moveX,moveY,50,30,'red',1);
                    }
                });
            }
        }
    }
}]);