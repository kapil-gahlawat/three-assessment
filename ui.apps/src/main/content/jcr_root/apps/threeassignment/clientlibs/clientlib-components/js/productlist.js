$(function() {
    var elem = $('div.owl-carousel');
    $('.owl-carousel').owlCarousel({
    loop:$( elem ).data( "carousel-loop" ),
    margin:10,
    nav:$( elem ).data( "carousel-nav" ),
    dots:$( elem ).data( "carousel-dots" ),
    stagePadding:130,
    slideBy: $( elem ).data( "carousel-slideby" ),
    navText: ["<span class='arrow left'></span>","<span class='arrow right'></span>"],
    responsive:{
        0:{
            items:1,
            stagePadding:100
        },
        600:{
            items:2,
            stagePadding:60
        },
        760:{
            items:3,
            stagePadding:80
        },
        1200:{
            items:4
        }
    }
})
});