package org.lion.utils;

import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;

import java.io.IOException;

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
       System.out.println(String.format("--> Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));

        Buffer requestBuffer = new Buffer();
        request.body().writeTo(requestBuffer);
       System.out.println(requestBuffer.readUtf8());

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
       System.out.println(String.format("<-- Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        MediaType contentType = response.body().contentType();
        BufferedSource buffer = Okio.buffer(new GzipSource(response.body().source()));
        String content = buffer.readUtf8();
       System.out.println(content);

        ResponseBody wrappedBody = ResponseBody.create(contentType, content);
        return response.newBuilder().removeHeader("Content-Encoding").body(wrappedBody).build();
    }
}