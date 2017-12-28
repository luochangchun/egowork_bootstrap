var mySwiper = new Swiper('.swiper-container', {
    pagination: '.pagination',
    paginationClickable: true,
    autoplay: 500000,
    speed: 1,
    // loop: true,
    //autoplayDisableOnInteraction : false,

    onInit: function (swiper) { //Swiper2.x的初始化是onFirstInit
        swiperAnimateCache(swiper); //隐藏动画元素 
        swiperAnimate(swiper); //初始化完成开始动画
    },
    onSlideChangeEnd: function (swiper) {
        swiperAnimate(swiper); //每个slide切换结束时也运行当前slide动画
    }
})
$('.arrow-left').on('click', function (e) {
    e.preventDefault()
    mySwiper.swipePrev()
})
$('.arrow-right').on('click', function (e) {
    e.preventDefault()
    mySwiper.swipeNext()
})    