package com.example.cameramaps.client.repository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.os.Handler;

import com.example.cameramaps.client.RunnableHttpRequest;

public abstract class Repository<T> {
    private final String url;
    private final Class<T> contentClass;
    private ExecutorService es = Executors.newSingleThreadExecutor();

    public Repository(String url, Class<T> contentClass) {
        this.url = url;
        this.contentClass = contentClass;
    }

    public void executeRequest(Handler handler) {
        try {
            RunnableHttpRequest request = new RunnableHttpRequest(url, contentClass, handler);
            es.execute(request);
        } catch (Exception e) {
            // ToDo: Handle exception
        }
    }
}
