//获取i,j
var X,Y;

var wzq_show = function(){
	var flag = 0;
	var $wzq = $('.wzq');
	function init(row,col,click_function){
		for(var i=0;i<row;i++){
			var $row = $wzq.append("<div class='row'></div>").find('.row').last();
			for(var j=0;j<col;j++){
				buildBlock($row,i,j,click_function);
			}
		}
		var $table = $wzq.append("<table></table>").find("table");
		for(var i=0;i<row-1;i++){
			var $row = $table.append("<tr></tr>").find('tr').last();
			for(var j=0;j<col-1;j++){
				$row.append("<td><div></div></td>");
			}
		}
	}
	function buildBlock($row,i,j,click_function){
		var $block = $row.append("<div class='block' id='wzq_"+i+"_"+j+"'></div>").find(".block");
		$block.click(click_function);
	}
	function setBlack(i,j){
		$("#wzq_"+i+"_"+j).removeClass("block").removeClass("black").removeClass("white").addClass("black");
	}
	function setWhite(i,j){
		$("#wzq_"+i+"_"+j).removeClass("block").removeClass("black").removeClass("white").addClass("white");
	}
	function setNone(i,j){
		$("#wzq_"+i+"_"+j).removeClass("block").removeClass("black").removeClass("white").addClass("block");
	}
	function get(i,j){
		var $block = $("#"+i+"_"+j);
		if($block.hasClass("block")) return 0;
		if($block.hasClass("black")) return 1;
		if($block.hasClass("white")) return 2;
	}
	
	return {
		init:init,
		setBlack:setBlack,
		setWhite:setWhite,
		setNone:setNone,
		get:get
	};
}();
//测试
//wzq_show.init(15,15,2);