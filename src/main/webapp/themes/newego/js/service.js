$(function () {
    function initService() {
        $.ajax({
            contentType: "text/plain;charset=utf-8",
            url: "front/service/162.do",    //请求的url地址   
            dataType: "json",   //返回格式为json    
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性    
            data: {},    //参数值    
            type: "GET",   //请求方式    
            success: function (data) {
                var entity = data;
                var htmlStr = '';
                for (var i = 0; i < entity.length; i++) {
                    if (i == 0) {
                        htmlStr += '<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">\
                                <img style = "width:100%;height:410px;" src='+ "themes/newego/img/" + entity[i]['cid'] + ".png" + '>\
                                </div>';
                    }
                    htmlStr += '<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">\
                    <ul class="service_index_ul cl">\
                        <a href=' + "/cms?type=category&id=" + entity[i]['cid'] + '>\
                            <li class="service_index_li">\
                                <h1>'+ entity[i]['title'] + '</h1>\
                                <p class="text-ellipsis-muti text-ellipsis-3">'+ entity[i]['intro'] + '</p>\
                            </li>\
                        </a>\
                	</ul>\
                  </div >';
                    if (i >= 8) {
                        break;
                    }
                }
                $('.nav-tabs li:nth-child(2)').addClass('active');
                $('#tab-pane').html(htmlStr);
            }
        })
    }
    $('.nav-tabs a').mouseover(function () {
        $(this).parent('li').addClass('active').siblings().removeClass('active');
        var cid = $(this).attr('data-id');
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
                for (var i = 0; i < entity.length; i++) {
                    if(i == 0) {
                        htmlStr += '<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">\
                                <img style = "width:100%;height:410px;" src='+"themes/newego/img/"+entity[i]['cid']+".png"+'>\
                                </div>';
                    }
                    htmlStr += '<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">\
                    <ul class="service_index_ul cl">\
                        <a href=' + "/cms?type=category&id=" + entity[i]['cid'] + '>\
                            <li class="service_index_li">\
                                <h1>'+ entity[i]['title'] + '</h1>\
                                <p class="text-ellipsis-muti text-ellipsis-3">'+ entity[i]['intro'] + '</p>\
                            </li>\
                        </a>\
                	</ul>\
                  </div >';
                    if (i >= 8) {
                        break;
                    }
                }
                $('#tab-pane').html(htmlStr);
            }
        })
    });
    initService();
})