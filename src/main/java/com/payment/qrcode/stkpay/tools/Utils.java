package com.payment.qrcode.stkpay.tools;

import java.util.HashMap;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public final class Utils {
    public static String postRequest(String url, String payload, HashMap<String, String> headers)
            throws ClientProtocolException, IOException {

                System.out.println(payload);

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost httppost;
        try {
            httppost = new HttpPost(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "";
        }

        StringEntity entity = new StringEntity(payload, StandardCharsets.UTF_8);

        for (String header : headers.keySet()) {
            httppost.addHeader(header, headers.get(header));
        }

        httppost.setEntity(entity);

        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        CloseableHttpResponse responseBody = httpclient.execute(httppost);

        String body = responseHandler.handleResponse(responseBody);

        httpclient.close();
        return body;

    }

    public static String getRequest(String url, HashMap<String, String> headers)
            throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpGet httpGet;
        try {
            httpGet = new HttpGet(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "";
        }

        for (String header : headers.keySet()) {
            httpGet.addHeader(header, headers.get(header));
        }

        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        CloseableHttpResponse responseBody = httpclient.execute(httpGet);

        String body = responseHandler.handleResponse(responseBody);

        return body;

    }
}
