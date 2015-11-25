/**
 * Created by Administrator on 2015/11/25 0025.
 */
var ws;//webSocket
var contestId=-1;
if (!window.WebSocket && window.MozWebSocket)
    window.WebSocket=window.MozWebSocket;
if (!window.WebSocket)
    alert("你的浏览器不支持动态更新，要使用该功能请使用其他浏览器（比如谷歌浏览器）");
function send(data)//发送消息
{
    console.log("Send:"+data);
    ws.send(data);
}
function startWebSocket(cid)
{
    contestId=cid;
    ws = new WebSocket("ws://" + location.host + "/WebSocket?cid="+cid);
    ws.onopen = function(){
        console.log("success open");
    };
    ws.onmessage = function(event)//接收消息
    {
        notice(event.data);
        console.log("RECEIVE:"+event.data);
    };
    ws.onclose = function(event) {
        console.log("Client notified socket has closed",event);
    };
}