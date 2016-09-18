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
 * Created by QAQ on 2016/9/18.
 */
public class Test {
    public static PushPayload buildPushObject_all_all_alert() {
        return PushPayload.alertAll("just a test");
    }
    public static void test(){
        JPushClient jpushClient = new JPushClient("b118aa33ff68ccc230981791", "32172531c963a486ed02849e", 3);

        // For push, all you need do is to build PushPayload object.
        //PushPayload payload = buildPushObject_all_all_alert();
        PushPayload payload = buildPushObject_user();

        try {
            PushResult result = jpushClient.sendPush(payload);
            Tool.debug("Got result - " + result);

        } catch (APIConnectionException e) {
            // Connection error, should retry later
            Tool.debug("Connection error, should retry later");

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            Tool.debug("Should review the error, and fix the request");
            Tool.debug("HTTP Status: " + e.getStatus());
            Tool.debug("Error Code: " + e.getErrorCode());
            Tool.debug("Error Message: " + e.getErrorMessage());
        }
    }
    public static PushPayload buildPushObject_user(){
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias("3148907242"))
                .setNotification(Notification.alert("别名测试"))
                .build();
    }
}
