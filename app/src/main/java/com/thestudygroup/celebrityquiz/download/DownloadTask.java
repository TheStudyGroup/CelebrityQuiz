package com.thestudygroup.celebrityquiz.download;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTask
{
    public static <T extends AppCompatActivity & DownloadListener> void start(@NonNull final T context, @NonNull final String url) {
        final OkHttpClient client  = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                context.runOnUiThread(context::onDownloadFailure);
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    final String data = Objects.requireNonNull(response.body()).string();
                    context.runOnUiThread(() -> context.onDownloadResponse(data));
                } catch (Exception e) {
                    e.printStackTrace();
                    context.runOnUiThread(context::onDownloadFailure);
                }
            }
        });
    }
}
