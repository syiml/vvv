package util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.List;

/**
 * 对HttpClient的封装
 * 使用同一个MyClient类进行操作可以保存其cookie和session信息
 * 可以实现先登录，然后获取登录后页面的操作
 * Created by Administrator on 2015/6/7.
 */
public class MyClient extends DefaultHttpClient{
    public MyClient(){
        super();
        HttpClientParams.setCookiePolicy(getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
    }

    public MyClient(ClientConnectionManager ccm, HttpParams params) {
        super(ccm,params);
    }

    /**
     * 对url提交一个post请求
     * @param URL 提交地址
     * @param form 表单的key value
     * @return 1成功 0失败
     */
    public synchronized int Post(String URL,List<NameValuePair> form){
        HttpEntity entity;
        try {
            entity = new UrlEncodedFormEntity(form, "UTF-8");
            HttpPost httppost = new HttpPost(URL);
            httppost.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
            httppost.setEntity(entity);
            HttpResponse hr;
            hr = execute(httppost);
            entity = hr.getEntity();
            if (entity != null) {
                System.out.println("Response content lenght:"  + entity.getContentLength());
                String content;
                try {
                    content = EntityUtils.toString(entity);
                    Tool.log("Response content:" + content);
                } catch (IOException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return 0;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
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
            HttpResponse hr = execute(httpget);
            entity = hr.getEntity();
            return Jsoup.parse(entity.getContent(), "utf-8", "");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static MyClient getMyClient() {
        HttpClient base = new DefaultHttpClient() ;
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager(){
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {

                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {

                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = base.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", ssf, 443));
            return new MyClient(ccm, base.getParams());
        } catch (Exception ex) {
            //ex.printStackTrace();
            Tool.log(ex);
            return null;
        }
    }
}
