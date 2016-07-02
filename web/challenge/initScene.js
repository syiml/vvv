/**
 * Created by QAQ on 2016/6/25 0025.
 */
var MyLayer = cc.LayerColor.extend({
    init:function () {
        // 1. super init first
        // 必须调用父类init()方法，很多bug都是由于没有调用父类init()方法造成的
        this._super();
        // 设置Layer的背景
        this.setColor(cc.c4(126,126,126,126));
        this.setPosition(0,0);
    }
});
var MyScene = cc.Scene.extend({
    onEnter:function () {
        this._super();
        var layer = new MyLayer();
        this.addChild(layer);
        layer.init();
    }
});