//面包屑导航控制器
myApp.factory('crumbNav',[function(){
    // console.log('面包屑导航控制器');
    var crumbNav={};
    var list=[{
        stateUrl:'home',
        nameArr:[{
            name:'首页'
        }]
    },{
        stateUrl:'userManagements',
        nameArr:[{
            name:'用户管理'
        },{
            name:'用户/用户组'
        }]
    },{
        stateUrl:'menuManagements',
        nameArr:[{
            name:'用户管理'
        },{
            name:'菜单管理'
        }]
    },{
        stateUrl:'roleManagements',
        nameArr:[{
            name:'用户管理'
        },{
            name:'角色管理'
        }]
    },{
        stateUrl:'deviceType',
        nameArr:[{
            name:'参数设置'
        },{
            name:'设备类型设置'
        }]
    },{
        stateUrl:'deviceSpares',
        nameArr:[{
            name:'参数设置'
        },{
            name:'备件类型设置'
        }]
    },{
        stateUrl:'deviceTroubleType',
        nameArr:[{
            name:'参数设置'
        },{
            name:'故障类型设置'
        }]
    },{
        stateUrl:'deviceProcess',
        nameArr:[{
            name:'参数设置'
        },{
            name:'审核流程设置'
        }]
    },{
        stateUrl:'otherOptionsSetting',
        nameArr:[{
            name:'参数设置'
        },{
            name:'其他选项设置'
        }]
    },{
        stateUrl:'departmentManage',
        nameArr:[{
            name:'参数设置'
        },{
            name:'部门设置'
        }]
    },{
        stateUrl:'contactCompany',
        nameArr:[{
            name:'参数设置'
        },{
            name:'往来单位'
        }]
    },{
        stateUrl:'repairGroup',
        nameArr:[{
            name:'参数设置'
        },{
            name:'维修工段/班组'
        }]
    },{
        stateUrl:'deviceManage',
        nameArr:[{
            name:'设备管理'
        },{
            name:'设备台账'
        }]
    },{
        stateUrl:'sparePartsManage',
        nameArr:[{
            name:'备件仓库'
        },{
            name:'配件台账'
        }]
    },{
        stateUrl:'modelToolsManage',
        nameArr:[{
            name:'工装模具'
        },{
            name:'工装模具总账'
        }]
    }];

    crumbNav.getList=function () {
        return list;
    };

    return crumbNav;
    
}]);
