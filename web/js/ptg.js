/**
 * Created by Syiml on 2015/10/4 0004.
 */
var ptg=function(){
    var $tb=$('#ptg-tb');
    var $main=$('#ptg-main');
    var $top=$('#ptg-top');
    var $pre=$('#ptg-pre');
    var $now=$('#ptg-now');
    var $next=$('#ptg-next');
    var $f=$('#ptg-f');
    var $sel=$('#ptg-sel');
    function init(){
        $.getJSON("module/problemtag/tag.jsp",function(data) {
            var s = "";
            s += '<div class="right-block form-inline"><div class="form-group"><div class="input-group input-group-sm"><span class="input-group-addon">标签</span><select class="form-control" id="ptg-sel" name="type">';
            for (var i = 0; i < data.length; i++) {
                s += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
            }
            s += '</select></div></div><button onclick="ptg.go()" class="btn btn-default btn-sm">筛选</button>';
            $sel=$f.append(s).find('#ptg-sel');
            if(location.hash){
                $sel.val(parseInt(location.hash.split('-')[0].substr(1)))
            }
        });
        ch();
    }
    function ch(){
        if(location.hash){
            var s=location.hash;
            s=s.split('-');
            ftag(parseInt(s[0].substr(1)),parseInt(s[1]));
        }
    }
    function ftag(tagid,pa){
        $main.find('table').remove();
        $tb.after("<div id='ptg-load'>"+HTML.loader_h('400px')+"</div>");
        //location.hash=tagid+"-"+pa;
        $.getJSON("module/problembytag.jsp?tag="+tagid+"&pa="+pa,function(data){
            $main.find('#ptg-load').remove();
            $tb.after(data.text);
            $top.attr("href","#"+tagid+"-0");
            if(pa!=0)$pre.removeAttr("disabled").attr("href","#"+tagid+"-"+(pa-1));
            else $pre.attr("disabled","disabled");
            $now.text(pa+1);
            if(!data.islast) $next.removeAttr("disabled").attr("href","#"+tagid+"-"+(pa+1));
            else $next.attr("disabled","disabled");
        })
    }
    function go(){
        location.hash=$sel.val()+"-0";
    }
    return {
        $tb:$tb,
        $main:$main,
        $top:$top,
        $pre:$pre,
        $now:$now,
        $next:$next,
        $f:$f,
        $sel:$sel,
        init:init,
        ftag:ftag,
        go:go,
        ch:ch
    }
}();
ptg.init();
$(window).on('hashchange', function() {
    ptg.ch();
});