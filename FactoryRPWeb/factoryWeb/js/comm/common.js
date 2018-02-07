/**
 * Created by hujia on 2015/6/3.
 */

/*公用URL*/
var WebURL='http://192.168.6.204:8080/';

document.onkeydown=function(e){
//    console.log(event.keyCode)
    var theEvent = window.event || e;
    var code = theEvent.keyCode || theEvent.which;
    if(code==116){
        return  false;
    }
};
if(location.href.split('#')[1]=='/login'){
    $("body").css({
        'background-color':'#1c77ac',
        'background-image':'../../images/light.png',
        'background-repeat':'no-repeat',
        'background-position':'center top',
        'overflow':'hidden'
    })
}else{
    $("body").removeAttr('id');
    $("body").css('background','#fff')
}
$(function(){
    //完整菜单效果1
    $(".menu_list").hide();
    $(".menu_list_first").show();
    $(".a_list").click(function(){
//        alert(1);
        var len = $('.a_list').length;
        var index = $(".a_list").index(this);
        for(var i=0;i<len;i++){
            if(i == index){
                $('.menu_list').eq(i).slideToggle(300);
            }else{
                $('.menu_list').eq(i).slideUp(300);
            }
        }
    });
    //完整菜单效果2
    //顶部导航切换
    $(".nav li a").click(function(){
        $(".nav li a.selected").removeClass("selected");
        $(this).addClass("selected");
    });
    //导航切换
    $(".menuson li").click(function(){
        $(".menuson li.active").removeClass("active");
        $(this).addClass("active");
    });

    $('.title').click(function(){
        var $ul = $(this).next('ul');
        $('dd').find('ul').slideUp();
        if($ul.is(':visible')){
            $(this).next('ul').slideUp();
        }else{
            $(this).next('ul').slideDown();
        }
    });
})
/****************************************************弹出警告提示框******************************************************************************************************************/
function popuWarning(msg){
    $("body").append(
        "<div id='bg1'></div><div class='popuWarning'>"+
        "<div class='warning'>"+
        "<p class='warningImg'></p>"+
        "<p  class='tip'>温馨提示</p>"+
        "<p class='tipValue' id='tipValue'></p>"+
        "<p class='warningBtn' onclick='hidePublicPopDIV()'>好的</p>"+
        "</div></div>"
    );
    var windowWidth = document.body.clientWidth;
    var scrollTop = document.body.scrollTop+document.documentElement.scrollTop;
    var minheight=document.documentElement.clientHeight;
    $("#bg1").show();
    $("#tipValue").html(msg);
    var div_obj = $(".popuWarning");
    var popupHeight = div_obj.height();
    var popupWidth = div_obj.width();
    div_obj.show();
    div_obj.css("top",scrollTop+(minheight-popupHeight)/2);
    div_obj.css("left",(windowWidth-popupWidth)/2);
}
//待自定义回调的警告弹出框
function popuWarningCall(msg,callback){
    $("body").append(
        "<div id='bg1'></div><div class='popuWarning'>"+
        "<div class='warning'>"+
        "<p class='warningImg'></p>"+
        "<p  class='tip'>温馨提示</p>"+
        "<p class='tipValue' id='tipValue'></p>"+
        "<p class='warningBtn' onclick='"+callback+"()'>好的</p>"+
        "</div></div>"
    );
    var windowWidth = document.body.clientWidth;
    var scrollTop = document.body.scrollTop+document.documentElement.scrollTop;
    var minheight=document.documentElement.clientHeight;
    $("#bg1").show();
    $("#tipValue").html(msg);
    var div_obj = $(".popuWarning");
    var popupHeight = div_obj.height();
    var popupWidth = div_obj.width();
    div_obj.show();
    div_obj.css("top",scrollTop+(minheight-popupHeight)/2);
    div_obj.css("left",(windowWidth-popupWidth)/2);
}
function popuError(msg){
    $("body").append(
            "<div id='bg1'></div><div class='popuWarning'>"+
            "<div class='warning'>"+
            "<p class='errorImg'></p>"+
            "<p  class='tip'>温馨提示</p>"+
            "<p class='tipValue' id='tipValue'>123</p>"+
            "<p class='warningBtn' onclick='hidePublicPopDIV()'>好的</p>"+
            "</div></div>"
    );
    $("#bg1").show();
    $("#tipValue").html(msg);
    var div_obj = $(".popuWarning");
    var windowWidth = document.body.clientWidth;
    var scrollTop = document.body.scrollTop+document.documentElement.scrollTop;
    var minheight=document.documentElement.clientHeight;;
    var popupHeight = div_obj.height();
    var popupWidth = div_obj.width();
    div_obj.show();
    div_obj.css("top",scrollTop+(minheight-popupHeight)/2);
    div_obj.css("left",(windowWidth-popupWidth)/2);
}
function success(msg){
    $("body").append(
            "<div id='bg1'></div><div class='popuSucess'>"+
            "<div class='warning'>"+
            "<p class='sucess'></p>"+
            "<p  class='tip'>温馨提示</p>"+
            "<p class='tipValue' id='tipValue'>123</p>"+
            "<p class='warningBtn' onclick='hidePublicPopDIV()'>好的</p>"+
            "</div></div>"
    );
    $("#bg1").show();
    $("#tipValue").html(msg);
    var div_obj = $(".popuWarning");
    var windowWidth = document.body.clientWidth;
    var windowHeight =  Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight)  ;
    var popupHeight = div_obj.height();
    var popupWidth = div_obj.width();
    div_obj.show();
    div_obj.css("top",windowHeight-popupWidth*2);
    div_obj.css("left",(windowWidth-popupWidth)/2);
}

function hidePublicPopDIV() {
    $("body").css("overflow","auto")
    $("#bg1").hide();
    $(".popuWarning").css("display","none");
    $(".popuWarning").remove();

}
/***************弹出层*****************/
function popupDiv(div_class) {
    $("#bg1").show();

    var div_obj = $("."+div_class);

	cc=div_obj.width()+14;
	dd=div_obj.height()+14;
    var windowWidth = document.body.clientWidth;
    var windowHeight = document.body.clientHeight;

    div_obj.css("maxWidth",windowWidth*0.7);
    div_obj.css("maxHeight",windowHeight*0.6);
    var popupHeight = div_obj.height();
    var popupWidth = div_obj.width();
	$("."+div_class).show();
    $("."+div_class).css('position','fixed');


     div_obj.css("top",(windowHeight-popupHeight)/2);
     div_obj.css("left",(windowWidth-popupWidth)/2);
     div_obj.css("overflow",'auto');

}
function hideDiv(div_class) {
    $("#bg1").hide();
    $('.clip_wrapper').hide();
    $("."+div_class).hide();
}




//页面初始化时对所有input做初始化
//PlaceHolder.init();
//或者单独设置某个元素
//PlaceHolder.create(document.getElementById('t1'));
function popuWarning1(msg,ids){
	
    	$("body").append(
                "<div id='optionBg1'></div><div class='popuWarning'>"+
                "<div class='warning'>"+
                "<p class='warningImg'></p>"+
                "<p  class='tip'>温馨提示</p>"+
                "<p class='tipValue' id="+ids+"></p>"+
                "<p class='warningBtn' onclick='hidePublicPopDIV1()'>好的</p>"+
                "</div></div>"
            );
            var windowWidth = document.body.clientWidth;
            var scrollTop = document.body.scrollTop+document.documentElement.scrollTop;
            var minheight=document.documentElement.clientHeight;
            $("#optionBg1").show();
//            $("#tipValue").html(msg);
            $("#"+ids).html(msg);
            var div_obj = $(".popuWarning");
            var popupHeight = div_obj.height();
            var popupWidth = div_obj.width();
            div_obj.show();
            div_obj.css("top",scrollTop+(minheight-popupHeight)/2);
            div_obj.css("left",(windowWidth-popupWidth)/2);
    
}
function hidePublicPopDIV1() {
    $("body").css("overflow","auto")
    $("#optionBg1").hide();
    $(".popuWarning").css("display","none");
    $(".popuWarning").empty();

}


//弹出提示层，func回调方法，msg提示信息
function pupuAlert(func,msg){
	$("body").append(
	        "<div id='bg1'></div><div class='popuWarning'>"+
	        "<div class='warning'>"+
	        "<p class='warningImg'></p>"+
	        "<p  class='tip'>温馨提示</p>"+
	        "<p class='tipValue' id='tipValue'></p>"+
	        "<p class='warningBtn' onclick='"+func+"();'>好的</p>"+
	        "</div></div>"
	    );
	    var windowWidth = document.body.clientWidth;
	    var scrollTop = document.body.scrollTop+document.documentElement.scrollTop;
	    var minheight=document.documentElement.clientHeight;
	    $("#bg1").show();
	    $("#tipValue").html(msg);
	    var div_obj = $(".popuWarning");
	    var popupHeight = div_obj.height();
		
		
	    var popupWidth = div_obj.width();
	    div_obj.show();
	    div_obj.css("top",scrollTop+(minheight-popupHeight)/2);
	    div_obj.css("left",(windowWidth-popupWidth)/2);
}


/**********静态修改页头登陆状态 1-已登录 0-未登录***************/
function changeHead(status){
	  var html = "";
	  if(status==1){
		 html += "<a class='goHelpCenter' href='"+basePath+"/html/help/subaccount_help.html'><img style='vertical-align: middle;' src='"+basePath+"/images/common/helpCenterGo.png' alt='帮助'/>帮助</a>";
		 html += "<a class='GouserCenter' href='"+basePath+"/personalController/showUserCenter'>用户中心</a>";
		 html += "<a class='exit' href='"+basePath+"/registerController/outLogin'>退出</a>";
		 $(".helpUserCenter").html(html);
		  
	  }else if(status == 0){
		  html += "<a class='goHelpCenter' href='"+basePath+"/html/help/subaccount_help.html'><img style='vertical-align: middle;' src='"+basePath+"/images/common/helpCenterGo.png' alt='帮助'/>帮助</a>";
		  html += "<a style='margin: 0 10px 0 10px;width: 70px;line-height:25px;' class='exit' href='"+basePath+"/registerController/loginPage'>登录</a>";
		  html += "<a style='width: 70px;margin: 0;' class='GouserCenter' href='"+basePath+"/registerController/registerPage'>注册</a>";
		  $(".helpUserCenter").html(html);
	  }
}


//add by cns 20160201
function setNewToken() {
	 $.ajax({
	 		type: "post",
	 		async:true,
	 		contentType: "application/json;charset=UTF-8",
	 		url: basePath + "/personalController/createNewToken",
	 		success: function(response) {
	 			if(response.success){
	 				$("#token").val(response.obj);
	 			}
	 		}
	 	});
}

function viewInfoByUrl(viewUrl) {
	window.location.href = basePath + viewUrl;
}

function hiddenParamJumpe(url,param) {
	var div = document.createElement("div");
	document.body.appendChild(div);
	div.style.display = "none";
	 // 创建Form  
    var form = $("<form></form>");  
    // 设置属性  
    form.attr("action", basePath + url);  
    form.attr("method", "post");  
    form.attr("target", "_self");  
    // 创建Input  
    var my_input = $("<input type='text' type='shopId' name='shopId' />");  
    my_input.attr("value", param);  
    // 附加到Form  
    form.append(my_input);  
    // 提交表单  
    form.appendTo(div).submit();
}
/*图片展示效果JSstart*/

//复选框全选的方法*********************开始
  function selAll(obj) {
      var o = document.getElementsByName("che");
      for (var i = 0; i < o.length; i++) {
          if (obj.checked == true) {
              o[i].checked = true;
          }
          else {
              o[i].checked = false;
          }

      }
  }
function selAll1(obj) {
    var o = document.getElementsByName("che1");
    for (var i = 0; i < o.length; i++) {
        if (obj.checked == true) {
            o[i].checked = true;
        }
        else {
            o[i].checked = false;
        }

    }
}
//复选框全选的方法*********************结束
//每一个子集复选框全选的方法*********************开始
function selAl(obj)
{
    var h=$(obj).siblings().find('.subCheckBox');

    for(var i=0;i<h.length;i++){
        if(obj.checked==true){
            h[i].checked=true;
        }else{
            h[i].checked=false;
        }
    }
}
function selMain(obj){
    if($(obj).parent().siblings().find(".subCheckBox").is(':checked')||$(obj).is(':checked')){
            $(obj).parent().parent().parent().parent().find('.functionList').prop('checked',true);
        }else{
            $(obj).parent().parent().parent().parent().find('.functionList').removeAttr('checked');
        }
}
//每一个子集复选框全选的方法*********************结束

  //判断是否全部被选中，如果选中全选复选框被选中，反之未被选中***************开始
   function selFirst()
   {
      var o=document.getElementsByName("che");
      var count=0,num=0;
      for(var i=0;i<o.length-1;i++)
      {
        if(o[i+1].checked==true)
        {
          count++;
        }
        if(o[i+1].checked==false)
        {
          num++;
        }
      }
      if(count==o.length-1)
      {
        o[0].checked=true;
      }
      if(num>0)
      {
        if(o[0].checked==true)
        {
          o[0].checked=false;
        }
      }
   }
  //判断是否全部被选中，如果选中全选复选框被选中，反之未被选中************结束
var wait =60;
//倒计时
function downtime() {
    if (wait == 0){
        $(".freeYzm").removeAttr("disabled");
        $(".freeYzm").removeClass('disFree');
        $(".freeYzm").html("获取验证码");
        wait = 60;
    }else {
        $(".freeYzm").attr("disabled","disabled");
        $(".freeYzm").addClass('disFree');
        $(".freeYzm").html("重新发送(" + wait + ")");
        wait--;
        setTimeout(function(){
            downtime();
        },1000);
    }
}
//复选框电话标签关键词全选的方法*********************开始
function phoneSelAll(obj) {
    var o = document.getElementsByName("phoneLabelKey");
    for (var i = 0; i < o.length; i++) {
        if (obj.checked == true) {
            o[i].checked = true;
        }
        else {
            o[i].checked = false;
        }

    }
}
function parciyphoneSelAll(obj) {
    var o = document.getElementsByName("praicyphoneLabelKey");
    for (var i = 0; i < o.length; i++) {
        if (obj.checked == true) {
            o[i].checked = true;
        }
        else {
            o[i].checked = false;
        }

    }
}
function operatorPhoneSelAll(obj) {
    var o = document.getElementsByName("operatorphoneLabelKey");
    for (var i = 0; i < o.length; i++) {
        if (obj.checked == true) {
            o[i].checked = true;
        }
        else {
            o[i].checked = false;
        }

    }
}
function operatorMsgphoneSelAll(obj) {
    var o = document.getElementsByName("operatorMsgphoneLabelKey");
    for (var i = 0; i < o.length; i++) {
        if (obj.checked == true) {
            o[i].checked = true;
        }
        else {
            o[i].checked = false;
        }

    }
}
//复选框电话标签关键词全选的方法*********************结束
//格式化时间*********************开始
Date.prototype.Format = function (fmt) {  
    var o = {  
        "M+": this.getMonth() + 1, //月份  
        "d+": this.getDate(), //日  
        "h+": this.getHours(), //小时  
        "m+": this.getMinutes(), //分  
        "s+": this.getSeconds(), //秒  
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度  
        "S": this.getMilliseconds() //毫秒  
    };  
    if (/(y+)/.test(fmt))  
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
    for (var k in o)  
        if (new RegExp("(" + k + ")").test(fmt))  
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));  
    return fmt;  
}  
Date.prototype.addDays = function (d) {  
    return this.setDate(this.getDate() + d);  
};  
//格式化时间*********************结束

//del_repeat()数组去重
function del_repeat(arr) {
    var hash = {};
    var newArr = [];
    for (var i = 0; i < arr.length; i++) {

        // hash["key"] 关联数组
        if (!hash[arr[i]] && arr[i] != undefined) {
            newArr.push(arr[i]);
            hash[arr[i]] = true;
        }
    }
    return newArr;
}