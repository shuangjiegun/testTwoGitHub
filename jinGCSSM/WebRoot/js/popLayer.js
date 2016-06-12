$(function(){	
	$("#btnMsg").on('touchstart mousedown',function(){
			$('#maskLayer').css('height', $(document).height());
			$("#maskLayer").show();
			$('#popLayer-msg').css('left', ($(document).width() - 300) / 2);
			$("#popLayer-msg").fadeIn("fast");
	});
			
	$("#btnClose").on('touchstart mousedown',function(){
		$("#maskLayer").hide();
		$("#popLayer-msg").fadeOut();	
	});	
	
})
