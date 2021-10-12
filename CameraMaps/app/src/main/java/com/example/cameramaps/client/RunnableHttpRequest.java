package com.example.cameramaps.client;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RunnableHttpRequest<T> implements Runnable {
    private URL url;
    final Class<T> returnType;
    Handler creator;
    HttpsURLConnection connection;

    public RunnableHttpRequest(final String stringUrl, final Class<T> returnType, Handler handler) throws MalformedURLException {
        url = new URL(stringUrl);
        creator = handler;
        this.returnType = returnType;
    }

    @Override
    public void run() {
        Message message = creator.obtainMessage();
        try {
            connection = (HttpsURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            message.obj = new Gson().fromJson(isr, returnType);
            message.getData().putInt("statusCode", connection.getResponseCode());
        } catch (Exception e) {
            message.obj = null;
        }
        creator.sendMessage(message);
    }
}
