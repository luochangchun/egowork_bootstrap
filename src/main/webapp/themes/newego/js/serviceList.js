function setId(id) {
    window.localStorage.setItem("serviceId",id);
}
var cid = window.localStorage.getItem("categoryId");
$(function () {
    function initService() {
        $('#cate' + cid).parent('li').addClass('active').siblings().removeClass('active');
        // $('#cate' + cid).parent('li').siblings().removeClass('active').addClass('s666');
        // $('.list_tab_1 li:nth-child(3)').css('display', 'none');
        $.ajax({
            contentType: "text/plain;charset=utf-8",
            url: "front/service/" + cid+".do",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            data: {},    //参数值
            type: "GET",   //请求方式
            success: function (data) {
                var entity = data;
                var htmlStr = '';
                if (entity.length > 0) {
                    $('.noData').hide();
                    for (var i = 0; i < entity.length; i++) {
                        htmlStr += '<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">\
                    <ul class="service_index_ul cl">\
                        <a onclick="setId(' + entity[i]['id'] + ')" href=' + "/cms?type=category&id=" + entity[i]['cid'] + '>\
                            <li class="service_index_li" style="height:200px;border:none;margin-bottom:10px;">\
                                <div class="ovh" style="background-color:#18b494;height:100%;">\
                                  <h1 style ="margin:30px auto 0 auto;text-align: center;color:#fff;font-size: 18px;" > '+ entity[i]['title'] + '</h1 >\
                                  <p class="text-ellipsis-muti text-ellipsis-2" style="width:90%;margin:0 auto;color:#fff;">'+ entity[i]['intro'] + '<p>\
                                </div>\
                            </li>\
                        </a>\
                	</ul>\
                  </div >';
                    }
                } else {
                    $('.noData').show();
                }
                $('#tab-pane').html(htmlStr);
            }
        })
    }
    $('.nav-tabs a').click(function () {
        $(this).parent('li').addClass('active').siblings().removeClass('active');
        // $('.list_tab_1 li:nth-child(3)').css('display', 'none');
        var cid = $(this).attr('data-id');
        window.localStorage.setItem("categoryId", cid);
        $.ajax({
            contentType: "text/plain;charset=utf-8",
            url: "front/service/" + cid + ".do",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            data: {},    //参数值
            type: "GET",   //请求方式
            success: function (data) {
                var entity = data;
                var htmlStr = '';
                if (entity.length > 0) {
                    $('.noData').hide();
                    for (var i = 0; i < entity.length; i++) {
                        if (i == 0) {
                            // htmlStr += '<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">\
                            //         <img style = "width:100%;height:410px;" src='+"themes/newego/img/"+entity[i]['cid']+".png"+'>\
                            //         </div>';
                        }
                        htmlStr += '<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">\
                    <ul class="service_index_ul cl">\
                        <a onclick="setId(' + entity[i]['id'] + ')" href=' + "/cms?type=category&id=" + entity[i]['cid'] + '>\
                            <li class="service_index_li" style="height:200px;border:none;margin-bottom:10px;">\
                                <div class="ovh" style="background-color:#18b494;height:100%;">\
                                  <h1 style ="margin:30px auto 0 auto;text-align: center;color:#fff;font-size: 18px;" > '+ entity[i]['title'] + '</h1 >\
                                  <p class="text-ellipsis-muti text-ellipsis-2" style="width:90%;margin:0 auto;color:#fff;">'+ entity[i]['intro'] + '<p>\
                                </div>\
                            </li>\
                        </a>\
                	</ul>\
                  </div >';
                    
                    }
                } else {
                    $('.noData').show();
                }
                $('#tab-pane').html(htmlStr);
                
            }
        })
    });
    initService();
});