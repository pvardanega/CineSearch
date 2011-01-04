package com.varda.android.cinesearch.resultsform.adapters;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import com.varda.android.cinesearch.httpsearch.HttpRetriever;

import java.lang.ref.WeakReference;

public class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {

    private String url;
    private final WeakReference<ImageView> imageViewWeakReference;

    public BitmapDownloaderTask(ImageView imageView) {
        this.imageViewWeakReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        this.url = params[0];
        HttpRetriever httpRetriever = new HttpRetriever();
        return httpRetriever.retrieveBitmap(this.url);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (this.imageViewWeakReference != null) {
            ImageView imageView = this.imageViewWeakReference.get();
            imageView.setImageBitmap(bitmap);
        }
    }

}
