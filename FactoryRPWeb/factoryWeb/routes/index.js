var express = require('express');
var router = express.Router();

//查询巡检状态
router.get('/service/getxjsj',function(req,res,next){
    var data=[{
      id:0,
      name:'今日待检'
    },{
      id:1,
      name:'明日待检'
    },{
      id:2,
      name:'本周待检'
    },{
      id:3,
      name:'下周待检'
    },{
      id:4,
      name:'本月待检'
    },{
      id:5,
      name:'下月待检'
    }];
    res.send({data:data,
        errorCode:"000000",
        errorMessage:"成功",
        requestSeqNo:"wt_2.140_1517416385397",
        responseTime:new Date().getTime()});
});
//查询巡检名称
router.get('/service/getxjlx',function(req,res,next){
    var data=[{
      id:0,
      name:'小时检'
    },{
      id:1,
      name:'日检'
    },{
      id:2,
      name:'周检'
    },{
      id:3,
      name:'月检'
    },{
      id:4,
      name:'年检'
    }];
    res.send({data:data,
        errorCode:"000000",
        errorMessage:"成功",
        requestSeqNo:"wt_2.140_1517416385397",
        responseTime:new Date().getTime()});
});


//获取热点人物
router.post('/getHotPeopleList',function(req,res,next){
    var data=[];
    for(var i=0;i<11;i++)
    {
        data.push({
            id:i,
            name:'张三' + i,
            img_url:'',
            source:'麻辣社区'
        });
    }
    res.send(data);
});

//上传文件测试
router.post('/service/wordsList', function(req, res, next) {
    console.log(req.body);
    res.send('200');
});

// router.post('/service/wordsList', function(req, res, next) {
//     var ret = [];
//     for(var i=1; i<21; i++) {
//         ret.push({name:"石化重大" + i,level:20});
//     }
//     res.send(JSON.stringify(ret));
// });

module.exports = router;
