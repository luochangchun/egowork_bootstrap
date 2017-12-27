function initCourse() {
    $.ajax({
        contentType: "text/plain;charset=utf-8",
        url: "http://192.168.11.222/egocourse/webapp/cou/list?currentPage=1&pageSize=6",    //请求的url地址   
        dataType: "json",   //返回格式为json    
        async: true, //请求是否异步，默认为异步，这也是ajax重要特性    
        data: {},    //参数值    
        type: "GET",   //请求方式    
        success: function (data) {
            if (data['success'] == true) {
                var entity = data['entity']['courseList'];
                var htmlStr = "";
                var turl ="http://vedio.whwomen.org.cn/free/play/";
                for (var i = 0; i < entity.length; i++) {
                    if (i == 2) {
                        htmlStr += '<a href=' + turl + entity[i]['courseId'] + ' class="col-lg-4 col-md-4 col-sm-6 col-sm-12 item rel">\
                        <h2 class="text-ellipsis">'+ entity[i]['courseName'] + '</h2>\
                        <p class="text-ellipsis-muti text-ellipsis-2">'+ entity[i]['context'] + '</p>\
                        <p>\
                            <i class="fa fa-graduation-cap"></i>\<span>'+ entity[i]['pageBuycount'] + '</span>&nbsp;&nbsp;|&nbsp;\
                            <i class="fa fa-eye" aria-hidden="true"></i>\
                            <span>'+ entity[i]['pageViewcount'] + '</span>\
                        </p>\
                        <img src="img/bg_CMS_1.jpg" alt="">\
                            <div class="line line_b abs"></div>\
                        </a>';
                        continue;
                    }
                    if (i == 3 || i == 4) {
                        htmlStr += '<a href=' + turl + entity[i]['courseId'] + ' class="col-lg-4 col-md-4 col-sm-6 col-sm-12 item rel">\
                        <h2 class="text-ellipsis">'+ entity[i]['courseName'] + '</h2>\
                        <p class="text-ellipsis-muti text-ellipsis-2">'+ entity[i]['context'] + '</p>\
                        <p>\
                            <i class="fa fa-graduation-cap"></i>\<span>'+ entity[i]['pageBuycount'] + '</span>&nbsp;&nbsp;|&nbsp;\
                            <i class="fa fa-eye" aria-hidden="true"></i>\
                            <span>'+ entity[i]['pageViewcount'] + '</span>\
                        </p>\
                        <img src="img/bg_CMS_1.jpg" alt="">\
                        <div class="line line_r abs"></div>\
                        </a>';
                        continue;
                    }
                    if (i == 5) {
                        htmlStr += '<a href=' + turl + entity[i]['courseId'] + ' class="col-lg-4 col-md-4 col-sm-6 col-sm-12 item rel">\
                        <h2 class="text-ellipsis">'+ entity[i]['courseName'] + '</h2>\
                        <p class="text-ellipsis-muti text-ellipsis-2">'+ entity[i]['context'] + '</p>\
                        <p>\
                            <i class="fa fa-graduation-cap"></i>\<span>'+ entity[i]['pageBuycount'] + '</span>&nbsp;&nbsp;|&nbsp;\
                            <i class="fa fa-eye" aria-hidden="true"></i>\
                            <span>'+ entity[i]['pageViewcount'] + '</span>\
                        </p>\
                        <img src="img/bg_CMS_1.jpg" alt="">\
                        </a>';
                        continue;
                    }
                    htmlStr += '<a href=' + turl + entity[i]['courseId'] + ' class="col-lg-4 col-md-4 col-sm-6 col-sm-12 item rel">\
                    <h2 class="text-ellipsis">'+ entity[i]['courseName'] + '</h2>\
                    <p class="text-ellipsis-muti text-ellipsis-2">'+ entity[i]['context'] +'</p>\
                    <p>\
                        <i class="fa fa-graduation-cap"></i>\<span>'+ entity[i]['pageBuycount']+'</span>&nbsp;&nbsp;|&nbsp;\
                        <i class="fa fa-eye" aria-hidden="true"></i>\
                        <span>'+ entity[i]['pageViewcount'] +'</span>\
                    </p>\
                    <img src="img/bg_CMS_1.jpg" alt="">\
                        <div class="line line_r abs"></div>\
                        <div class="line line_b abs"></div>\
                    </a>';
                    
                   
                }
                $('#courseList').html(htmlStr);
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

function initTutor() {
    $.ajax({
        contentType: "text/plain;charset=utf-8",
        url: "http://192.168.11.222/egocourse/webapp/teacher/list?currentPage=1&pageSize=6",    //请求的url地址   
        dataType: "json",   //返回格式为json    
        async: true, //请求是否异步，默认为异步，这也是ajax重要特性    
        data: {},    //参数值    
        type: "GET",   //请求方式    
        success: function (data) {
            if (data['success'] == true) {
                console.log(data);
                var entity = data['entity']['teacherList'];
                var htmlStr = "";
                var turl = "http://vedio.whwomen.org.cn/front/teacher/";
                for (var i = 0; i < entity.length; i++) {
                    htmlStr += '<a href='+turl+ entity[i]['id'] + ' class="item col-lg-3 col-md-3 col-sm-6 col-sm-12">\
                            <img src='+"http://192.168.11.132:8080"+entity[i]["picPath"]+'>\
                            <h1 class="tc">'+ entity[i]['name'] + '</h1>\
                            <p class="tc f14 text-ellipsis">'+ entity[i]['career'] + '</p>\
                            <p class="tc f14 text-ellipsis-muti text-ellipsis-2">'+ entity[i]['education'] + '</p>\
                            </a >';
                }
                $('#tutorList').html(htmlStr);
            }


        },
        complete: function () {
            //请求完成的处理    
        },
        error: function () {
            //请求出错处理    
        }
    });
}

$(function () {
    initCourse();
    initTutor();
})