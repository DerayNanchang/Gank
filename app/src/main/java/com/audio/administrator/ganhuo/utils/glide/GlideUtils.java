package com.audio.administrator.ganhuo.utils.glide;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import com.audio.administrator.audio.util.ThreadUtil;
import com.audio.administrator.ganhuo.R;
import com.audio.administrator.ganhuo.app.GanHuoApplication;
import com.audio.administrator.ganhuo.config.Config;
import com.audio.administrator.ganhuo.utils.JThreadUtil;
import com.audio.administrator.ganhuo.utils.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Chris on 2018/3/29.
 */

public class GlideUtils {


    public static void local(ImageView imageView, int url, int defaultImg) {
        RequestOptions requestOptions = new RequestOptions();
        Glide.with(imageView)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade(500))
                .apply(requestOptions.placeholder(defaultImg))
                .apply(requestOptions.error(defaultImg))
                .into(imageView);
    }

    public static void local(ImageView imageView, String url, int defaultImg) {
        RequestOptions requestOptions = new RequestOptions();
        Glide.with(imageView).load(url).apply(requestOptions.error(defaultImg)).into(imageView);
    }


    public static void noDefault(ImageView imageView, String url) {
        Glide.with(imageView)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade(500))
                .into(imageView);
    }


    //保存图片（指定文件夹以及名称）
    public static void downLoadImageNew(String url) {

        final Context context = GanHuoApplication.JVM.getGet();

        RequestOptions options = new RequestOptions()
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL); //指定图片大小(原图)

        Glide.with(context).asBitmap().load(url).apply(options)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {

                        Bitmap tempBitmip = resource;
                        //将时间戳转成固定格式
                        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                        Date date = new Date(System.currentTimeMillis());
                        //图片名称
                        String mFileName = format.format(date) + ".jpg";
                        //图片路径
                        //File tempPath = Environment.getExternalStorageDirectory();
                        File tempPath = Config.get().initPhotoDir();
                        //保存图片
                        File imgFile = new File(tempPath, mFileName);
                        if (imgFile.exists()) {
                            Toast.show("该图片已经保存啦");
                        } else {
                            File newFile = saveBitmap(tempPath, mFileName, tempBitmip);
                            String backgroundPath = newFile.getPath();
                            if (backgroundPath != null) {

                                // 其次把文件插入到系统图库
                                try {
                                    MediaStore.Images.Media.insertImage(context.getContentResolver(),
                                            newFile.getAbsolutePath(), backgroundPath, null);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                                // 最后通知图库更新
                                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                        Uri.fromFile(new File(newFile.getPath()))));

                                Toast.show("图片成功保存至:" + tempPath.getAbsolutePath());

                            } else {
                                Toast.show("保存失败");
                            }
                        }
                    }
                });

    }

    /**
     * 保存图片方法
     */

    private static File saveBitmap(File path, String imgName, Bitmap bitmap) {

        File f = new File(path, imgName);
        //创建文件夹
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            return f;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }


    public static void baseMusicState(Context context, ImageView imageView, String url, int def) {
        imageView.setTag("");
        RequestOptions requestOptions = new RequestOptions();
        Glide.with(context)
                .load(url)
                .apply(requestOptions.error(def))
                .apply(requestOptions.skipMemoryCache(false))
                .apply(requestOptions.transform(new GlideRoundTransform(6)))
                .apply(requestOptions.dontAnimate())
                .into(imageView);
    }

    public static void baseMusicState(ImageView imageView, String url, int def) {
        imageView.setTag("");
        RequestOptions requestOptions = new RequestOptions();
        Glide.with(imageView.getContext())
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade(500))
                .apply(requestOptions.error(def))
                .apply(requestOptions.transform(new GlideRoundTransform(6)))
                .into(imageView);
    }

    public static void simple(ImageView imageView, String url, int defaultImg) {

        RequestOptions requestOptions = new RequestOptions();
        Glide.with(imageView)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade(500))
                .apply(requestOptions.placeholder(defaultImg))
                .apply(requestOptions.error(defaultImg))
                .into(imageView);
    }

    public static void simple(ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions();
        Glide.with(imageView)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade(500))
                .apply(requestOptions.error(R.mipmap.ic_launcher))
                .apply(requestOptions.transform(new GlideRoundTransform(6)))
                .into(imageView);
    }


    public static void setImage(ImageView imageView, int resource) {
        Glide.with(imageView)
                .load(resource)
                .transition(new DrawableTransitionOptions().crossFade(500))
                .into(imageView);
    }

    public static void simple(ImageView imageView, File url, int defaultImg) {

        RequestOptions requestOptions = new RequestOptions();
        Glide.with(imageView)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade(500))
                .apply(requestOptions.placeholder(defaultImg))
                .apply(requestOptions.error(defaultImg))
                .into(imageView);
    }

    public static void imIcon(ImageView imageView, String url, int defaultImg) {
        RequestOptions requestOptions = new RequestOptions();
        Glide.with(imageView)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade(500))
                .apply(requestOptions.placeholder(defaultImg))
                .apply(requestOptions.error(defaultImg))
                .apply(requestOptions.override(96, 96))
                .into(imageView);

    }

    // 圆图
    public static void circular(ImageView imageView, String url, int defaultImg) {
        RequestOptions requestOptions = new RequestOptions();
        Glide.with(imageView)
                .load(url)
                .apply(requestOptions.error(defaultImg))
                .apply(requestOptions.placeholder(defaultImg))
                .apply(requestOptions.transform(new GlideCircleTransform()))
                .into(imageView);
    }

    // 圆图
    public static void customCircular(ImageView imageView, String url, int defaultImg) {
        RequestOptions requestOptions = new RequestOptions();
        Glide.with(imageView)
                .load(url)
                .apply(requestOptions.dontAnimate().centerCrop())
                .apply(requestOptions.error(defaultImg))
                .apply(requestOptions.skipMemoryCache(false))
                .apply(requestOptions.transform(new GlideCircleTransform()))
                .into(imageView);
    }

    // 圆图
    public static void circular(ImageView imageView, File url, int defaultImg) {
        RequestOptions requestOptions = new RequestOptions();
        Glide.with(imageView)
                .load(url)
                .apply(requestOptions.dontAnimate().centerCrop())
                .apply(requestOptions.error(defaultImg))
                .apply(requestOptions.skipMemoryCache(false))
                .apply(requestOptions.transform(new GlideCircleTransform()))
                .into(imageView);
    }


    // 圆图
    public static void circular(ImageView imageView, int url, int defaultImg) {
        RequestOptions requestOptions = new RequestOptions();
        Glide.with(imageView)
                .load(url)
                .apply(requestOptions.dontAnimate().centerCrop())
                .apply(requestOptions.error(defaultImg))
                .apply(requestOptions.skipMemoryCache(false))
                .apply(requestOptions.transform(new GlideCircleTransform()))
                .into(imageView);
    }


    public static void clear(final Context context) {
        Glide.get(context).clearMemory();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();

    }
}
