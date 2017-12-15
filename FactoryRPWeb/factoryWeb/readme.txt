1. js/comm/common.js   公用的方法，日期格式化、弹出框效果，全选，数组去重，都有备注的
2. js/comm/common.js   后台的接口路径地址
3. js/daianla-authorizationM/authorizationApp.js  权限的相关路由配置
4. js/factroy/factoryParameterSettingApp.js   工厂的相关配置

5. views/daianla-authorizationManagement  下面都是权限管理相关的view层，
5. views/daianla-authorizationManagement/functionManage.html  参数设置>用户管理>菜单管理，
5. views/daianla-authorizationManagement/departmentManage.html  参数设置>通用设置>部门设置，

6. views/comm  首页，登录页的view层，
6. views/comm/home.html  首页，公用菜单，
6. views/comm/homePage.html  首页数据统计，
6. views/comm/login1.html  登录页面，
6. views/comm/uploaddemo.html  angular 上传demo，

7. views/factory/ContactCompany.html  参数设置>通用设置>往来单位
7. views/factory/repairGroup.html  参数设置>通用设置>维修工段/班组
7. views/factory/deviceManage.html  设备管理>设备台账 (该功能接口只调试了列表查询 ，以及添加的弹出框内容定义，添加接口在factoryParameterSettingService.js定义了，controller里面没调用)
7. views/factory/right.html  用来看样式的页面
7. views/other  其他的目前没用到，但是可能用得到的view,404啊，富文本页面啊，等等


