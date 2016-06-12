var numA; var numB; var numC; var numD; var numE; var pro;
function showTradeAll(numA){	
		 $("#moreTradeAll_"+numA).slideToggle("slow");
		 $("#moreTradeAll_"+numA).parent().siblings().children("p.none").slideUp("slow");		 
}
function showTradeSG(numB){	
		 $("#moreTradeSG_"+numB).slideToggle("slow");
		 $("#moreTradeSG_"+numB).parent().siblings().children("p.none").slideUp("slow");
}
function showTradeSH(numC){	
		 $("#moreTradeSH_"+numC).slideToggle("slow");
		 $("#moreTradeSH_"+numC).parent().siblings().children("p.none").slideUp("slow");
}
function showTradeRG(numD){	
		 $("#moreTradeRG_"+numD).slideToggle("slow");
		 $("#moreTradeRG_"+numD).parent().siblings().children("p.none").slideUp("slow");
}
function showTradeCD(numE){	
		 $("#moreTradeCD_"+numE).slideToggle("slow");
		 $("#moreTradeCD_"+numE).parent().siblings().children("p.none").slideUp("slow");
}
function showTradeZH(numF){	
		 $("#moreTradeZH_"+numF).slideToggle("slow");
		 $("#moreTradeZH_"+numF).parent().siblings().children("p.none").slideUp("slow");
}
function showButton(pro){	
		 $("#buttonGroup_"+pro).slideToggle("slow");
}

