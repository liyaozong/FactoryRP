
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
    //新增备件
    spareParts.editSpareParts=function(data){
        return AppHttp({
            method: 'post',
            url: FF_API.base + FF_API.editSparePartsPath,
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

    //查询设备参数
    spareParts.findByCode=function (code) {
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.findByCodePath+'?code='+code
        });
    };

    //单个删除备件
    spareParts.deleteSparePartsById=function (id) {
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.deleteSparePartsByIdPath+'?id='+id
        });
    };

    //批量删除备件
    spareParts.deleteSparePartsByIds=function (ids) {
        return AppHttp({
            method: 'get',
            url: FF_API.base + FF_API.deleteSparePartsByIdsPath+'?ids='+ids
        });
    };


    return spareParts;
}]);