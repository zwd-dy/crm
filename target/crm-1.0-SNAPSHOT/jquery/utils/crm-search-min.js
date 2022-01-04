
SEARCH = function (){

    // 查询条件转参数
    this.searchToParam = function ($search){
        var param = "";
        $search.each(function(){
            var key = $(this)[0].id.replace("search-","");
            var value = $(this).val();
            param += key + "=" + value + "&";

        });
        param = param.substr(0, param.length - 1);
        return param;
    }

    // 将隐藏域保存的信息存放回查询条件
    this.searchToHidden = function ($search){
        $search.each(function(){
            var key = $(this)[0].id.replace("search-","");

            $(this).val($.trim($("#hidden-"+key).val()));
        });
    }

    // 将查询条件保存到隐藏域中
    this.hiddenToSearch = function ($search){
        $search.each(function(){
            var key = $(this)[0].id.replace("search-","");
            $("#hidden-"+key).val($.trim($(this).val()));
        });
    }
}

var SE = new SEARCH();
