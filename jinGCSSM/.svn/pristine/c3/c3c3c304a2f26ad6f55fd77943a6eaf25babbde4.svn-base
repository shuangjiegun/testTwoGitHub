var a1=$("#a1").height();
var a2=$("#a2").height();
var a3=$("#a3").height();
var a4=$("#a4").height();
var a5=$("#a5").height();
var a6=$("#a6").height();
var mycontainer=$(".swiper-container");
mycontainer.css("height",a1+30);
var tabsSwiper = new Swiper('.swiper-container',{
	speed:500,
	onSlideChangeStart: function(){
		$(".tabs .active").removeClass('active');
		$(".tabs a").eq(tabsSwiper.activeIndex).addClass('active');
	if (tabsSwiper.activeIndex==0){
		mycontainer.css("height",a1+30);
		}
		else if (tabsSwiper.activeIndex==1){
		mycontainer.css("height",a2+30);
		}
		else if (tabsSwiper.activeIndex==2){
		mycontainer.css("height",a3+30);
		}
		else if (tabsSwiper.activeIndex==3){
		mycontainer.css("height",a4+30);
		}
		else if (tabsSwiper.activeIndex==4){
		mycontainer.css("height",a5+30);
		}
		else if (tabsSwiper.activeIndex==5){
		mycontainer.css("height",a6+30);
		}
	}
});

$(".tabs a").on('touchstart mousedown',function(e){
	e.preventDefault()
	$(".tabs .active").removeClass('active');
	$(this).addClass('active');
	if ($(this).index()==0){
		mycontainer.css("height",a1+30);
		}
		else if ($(this).index()==1){
		mycontainer.css("height",a2+30);
		}
		else if ($(this).index()==2){
		mycontainer.css("height",a3+30);
		}
		else if ($(this).index()==3){
		mycontainer.css("height",a4+30);
		}
		else if ($(this).index()==4){
		mycontainer.css("height",a5+30);
		}
		else if ($(this).index()==5){
		mycontainer.css("height",a6+30);
		}
	tabsSwiper.swipeTo($(this).index());
});

$(".tabs a").click(function(e){
	e.preventDefault();
});
