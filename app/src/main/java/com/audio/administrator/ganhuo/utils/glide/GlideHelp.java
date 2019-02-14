package com.audio.administrator.ganhuo.utils.glide;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * Created by Administrator on 2018/9/20.
 */

public class GlideHelp {


    public interface OnAsBitmap {
        void asBitmap(Bitmap bitmap);
    }

    public void asBitMap(final ImageView imageView, final String url, final OnAsBitmap onAsBitmap) {
        Glide.with(imageView.getContext())
                .asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        onAsBitmap.asBitmap(resource);
                    }
                });
    }
}
