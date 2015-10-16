package com.qf.ysr.jiqi;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2015/10/9.
 */
public class HttpData extends AsyncTask<String,Void,String>  {
    private HttpClient mHttpClient;
    private HttpGet mHttpGet;
    private HttpResponse mHttpResponse;
    private String mUrl;
    private HttpEntity mHttpEntity;
    private InputStream is;
    private HttpGetDataListener listener;
    public HttpData(String mUrl,HttpGetDataListener listener) {
        this.mUrl = mUrl;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            mHttpClient = new DefaultHttpClient();
            mHttpGet = new HttpGet(mUrl);
            mHttpResponse = mHttpClient.execute(mHttpGet);
            mHttpEntity = mHttpResponse.getEntity();
            is = mHttpEntity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line=br.readLine())!=null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        listener.getDataUrl(s);
        super.onPostExecute(s);

    }
}
