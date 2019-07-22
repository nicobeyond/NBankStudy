package com.nbank.study.image;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nbank.study.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.yitong.universalimageloader.core.DisplayImageOptions;
import com.yitong.universalimageloader.core.ImageLoader;
import com.yitong.universalimageloader.core.assist.ImageScaleType;
import com.yitong.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ico.ico.adapter.BaseAdapter;
import ico.ico.ico.BaseFragActivity;
import ico.ico.util.log;

public class ImageTestActivity extends BaseFragActivity {

    /**
     * @param context
     * @param type    1imageload 2glide 3picasso
     * @return
     */
    public static Intent getIntent(Context context, int type) {
        Intent intent = new Intent(context, ImageTestActivity.class);
        intent.putExtra("type", type);
        return intent;
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    int type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_test);
        ButterKnife.bind(this);

        type = getIntent().getIntExtra("type", 1);

//        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1556800033&di=eda9bb7d0649ed1f061dee09025a7190&imgtype=jpg&er=1&src=http%3A%2F%2Fthumb102.hellorf.com%2Fpreview%2F411353650.jpg";
//        String url = "http://www.bkill.com/u/upload/2017/12/06/061940553080.jpg";//909x514
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1556190970347&di=37fef59b0bdce080305ea953d503988b&imgtype=0&src=http%3A%2F%2Fi5.download.fd.pchome.net%2Ft_1920x1080%2Fg1%2FM00%2F08%2F1E%2FooYBAFOpFQGIcEn2AB0OtDloIckAABomAMqnkcAHQ7M173.jpg";

        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            list.add(url + "#" + i);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        });

        recyclerView.setAdapter(new BaseAdapter<String, BaseAdapter.BaseViewHolder<String>>(mActivity, R.layout.item_image_test, list) {
            @Override
            protected void onWidgetInit(BaseViewHolder<String> holder, int position) {
                super.onWidgetInit(holder, position);
                TextView txt = (TextView) holder.getView(R.id.txt);
                txt.setText("" + position);
                ImageView iv = (ImageView) holder.getView(R.id.img);
                switch (type) {
                    case 1://231 217.8 604.2(放大)  235.4
                        //0.9 399.2 394.8
//                        DisplayImageOptions dio = new DisplayImageOptions.Builder().imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build();
//                        DisplayImageOptions dio = new DisplayImageOptions.Builder().imageScaleType(ImageScaleType.EXACTLY).build();
//                        DisplayImageOptions dio = new DisplayImageOptions.Builder().imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
                        DisplayImageOptions dio = new DisplayImageOptions.Builder().imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
                        ImageLoader.getInstance().displayImage(holder.itemData, iv, dio, new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                log.w("===imageloader" + position + "|" + loadedImage.getWidth() + "|" + loadedImage.getHeight());
                            }
                        });
                        break;
                    case 2:
                        Glide.with(mActivity).load(holder.itemData).addListener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                log.w("===glide onLoadFailed" + position);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                log.w("===glide onResourceReady" + position + "|" + ((BitmapDrawable) resource).getBitmap().getWidth() + "|" + ((BitmapDrawable) resource).getBitmap().getHeight());
                                return false;
                            }
                        }).into(iv);
                        break;
                    case 3:
                        Picasso.get().load(holder.itemData)
                                .transform(getTransformation(iv))
                                .into(iv, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        log.w("===picasso onSuccess" + position);
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        log.w("===picasso onError" + position);
                                    }
                                });
                        break;
                }

            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                log.w("===onScrollStateChanged", "scroll");
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                log.w("===onScrolled", "scroll");
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    static Transformation getTransformation(final ImageView view) {
        return new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                int targetWidth = view.getWidth();

                //返回原图
                if (source.getWidth() == 0 || source.getWidth() < targetWidth) {
                    return source;
                }

                //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                if (targetHeight == 0 || targetWidth == 0) {
                    return source;
                }
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                log.w("===" + targetWidth + "|" + targetHeight);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }

            @Override
            public String key() {
                return "transformation" + " desiredWidth";
            }
        };
    }
}
