
//备件相关服务
myApp.factory("spareParts",["AppHttp","FF_API",function(AppHttp,FF_API){
    var spareParts={};
    //查询备件
    spareParts.findByPage=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.findByPagePath,
            data:data
        });
    };
    //新增备件
    spareParts.addSpareParts=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.addSparePartsPath,
            data:data
        });
    };

    //查询往来单位（生产厂商和供应商）
    spareParts.contactCompanyList=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.contactCompanyListPath,
            data:data
        });
    };
    spareParts.findByCode=function (code) {
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.findByCodePath+'?code='+code
        });
    };
    //查询设备参数


    return spareParts;
}]);