function setCategory(id) {
    var cid = parseInt(id)
    window.localStorage.setItem("categoryId", cid);
}
function initCourse() {
    $.ajax({
        contentType: "text/plain;charset=utf-8",
        url: "http://vedio.whwomen.org.cn/webapp/cou/list?currentPage=1&pageSize=6",    //请求的url地址   
        dataType: "json",   //返回格式为json    
        async: true, //请求是否异步，默认为异步，这也是ajax重要特性    
        data: {},    //参数值    
        type: "GET",   //请求方式    
        success: function (data) {
            if (data['success'] == true) {
                var entity = data['entity']['courseList'];
                var htmlStr = "";
                var turl = "http://vedio.whwomen.org.cn/free/play/";
                for (var i = 0; i < entity.length; i++) {
                    if(i < 4) {
                        htmlStr += '<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 item db" style="margin-bottom: 20px">\
                                    <div class="onlineWrap rel ovh">\
                                        <img src="img/01.png" class="abs">\
                                        <div class="abs onlineMask">\
                                            <a href=' + turl + entity[i]['courseId'] + ' class="tc white db">开始学习</a>\
                                        </div>\
                                    </div>\
                                    <div class="onlineWord">\
                                        <p class="text-ellipsis f14">'+ entity[i]['courseName'] + '</p>\
                                        <p class="clearfix">\
                                            <i class="fa fa-graduation-cap"></i>\
                                            <span>'+ entity[i]['pageBuycount'] + '</span>&nbsp;&nbsp;|&nbsp;\
                                    <i class="fa fa-eye" aria-hidden="true"></i>\
                                            <span>'+ entity[i]['pageViewcount'] + '</span>\
                                            <strong class="r white tc">免费</strong>\
                                        </p>\
                                    </div>\
                                </div>';
                        continue;
                    }
                   
                }
                $('#onlineList').html(htmlStr);
            }


        },
        complete: function () {
            //请求完成的处理    
        },
        error: function () {
            //请求出错处理    
        }
    });
};
initCourse()