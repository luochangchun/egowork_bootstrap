//口风琴
$('.service_detail_header_title').click(function(){
    $(this).next('.panel-body').toggle(200);
});

/*侧边导航切换折叠指示图标*/
$(function(){
    $(".service_detail_header_title").click(function(e){
        $(this).find("span").toggleClass("glyphicon-chevron-down");
        $(this).find("span").toggleClass("glyphicon-chevron-up");
    });
});
//点击导航添加样式
$(function() {
    $('.service_detail_header_title').click(function() {
        if(!$(this).hasClass('activ')) {
            $(this).addClass('activ').siblings().removeClass('activ');
//                $(this).find(".right").show().parent().siblings().find(".right").hide();
        }
    })
});

$(function () {
    var curId = window.localStorage.getItem('serviceId');
    // console.log(curId);
    $('#tab' + curId).addClass('s777').removeClass('s666');
    $('#text' + curId).show();

    $(".panel-body ul li a").click(function () {//获取点击id
        var id = $(this).attr("data-id");
        
        var curId = window.localStorage.setItem('serviceId',id);
        $('#text' + id).show().siblings().hide();
        $('#tab' + id).addClass('s777').removeClass('s666');
        $('#tab' + id).parents().siblings().find('a').removeClass('s777').addClass('s666');
        
    })
})

