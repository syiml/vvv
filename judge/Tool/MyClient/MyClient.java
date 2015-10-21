package Tool.MyClient;

import Main.Main;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 对HttpClient的封装
 * 使用同一个MyClient类进行操作可以保存其cookie和session信息
 * 可以实现先登录，然后获取登录后页面的操作
 * Created by Administrator on 2015/6/7.
 */
public class MyClient {
    HttpClient hc;
    public MyClient(){
        hc = new DefaultHttpClient();
        HttpClientParams.setCookiePolicy(hc.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
    }

    /**
     * 对url提交一个post请求
     * @param URL 提交地址
     * @param from 表单的key value
     * @return 1成功 0失败
     */
    public synchronized int Post(String URL,List<NameValuePair> from){
        HttpEntity entity;
        try {
            entity = new UrlEncodedFormEntity(from, "UTF-8");
            HttpPost httppost = new HttpPost(URL);
            httppost.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
            httppost.setEntity(entity);
            HttpResponse hr;
            hr = hc.execute(httppost);
            entity = hr.getEntity();
            if (entity != null) {
                //System.out.println("Response content lenght:"  + entity.getContentLength());
                String content;
                try {
                    content = EntityUtils.toString(entity);
                    Main.debug("Response content:" + content);
                } catch (IOException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
            return 0;
        } catch (ClientProtocolException e) {
            //e.printStackTrace();
            return 0;
        } catch (IOException e) {
            //e.printStackTrace();
            return 0;
        }
        return 1;
    }

    /**
     * 获取指定url的内容，指定了utf-8编码
     * 要获取登录后才能显示的页面，要先用Post提交登录表单，然后get指定页面
     * 返回的页面没有执行页面的js脚本代码
     * @param URL 地址
     * @return 返回地址的Document类
     */
    public Document get(String URL){
        HttpEntity entity = null;
        try {
            HttpGet httpget = new HttpGet(URL);
            httpget.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
            HttpResponse hr=hc.execute(httpget);
            entity = hr.getEntity();
            return Jsoup.parse(entity.getContent(), "utf-8", "");
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
            return null;
        } catch (ClientProtocolException e) {
            //e.printStackTrace();
            return null;
        } catch (IOException e) {
            //e.printStackTrace();
            return null;
        }
    }
}
