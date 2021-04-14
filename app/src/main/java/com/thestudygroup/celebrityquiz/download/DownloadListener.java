package com.thestudygroup.celebrityquiz.download;

public interface DownloadListener
{
    void onDownloadResponse(final String data);
    void onDownloadFailure();
}
