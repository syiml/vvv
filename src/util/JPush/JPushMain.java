package util.JPush;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import util.Tool;

/**
 * Created by QAQ on 2016/10/10.
 */
public class JPushMain {
    private static JPushClient jpushClient = new JPushClient("78848b9f145a20e8ebbea9f0", "1275009c2148a71627f35e98");

    public static boolean pushToAll(String text){
        return pushMessage(payloadAll(text));
    }
    public static boolean pushToUser(String username,String text){
        return pushMessage(payloadUser(username,text));
    }

    private static boolean pushMessage(PushPayload payload){
        try {
            PushResult result = jpushClient.sendPush(payload);
            Tool.debug("Got result - " + result);
            return true;
        } catch (APIConnectionException e) {
            // Connection error, should retry later
            Tool.debug("Connection error, should retry later");
            return false;
        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            Tool.debug("Should review the error, and fix the request");
            Tool.debug("HTTP Status: " + e.getStatus());
            Tool.debug("Error Code: " + e.getErrorCode());
            Tool.debug("Error Message: " + e.getErrorMessage());
            return false;
        }
    }
    private static PushPayload payloadUser(String username, String text){
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(username))
                .setNotification(Notification.alert(text))
                .build();
    }
    private static PushPayload payloadAll(String text) {
        return PushPayload.alertAll(text);
    }
}
