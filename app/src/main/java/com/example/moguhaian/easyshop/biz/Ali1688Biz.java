package com.example.moguhaian.easyshop.biz;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.moguhaian.easyshop.Base.BaseBiz;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.PicUtils;
import com.example.moguhaian.easyshop.listener.GlideLoadListener;

import java.util.List;

public class Ali1688Biz extends BaseBiz {
    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private int detailPosition = -1;
    private int picSpacePosition = -1;
    private String url1;
    private String url2;

    public void diffResult(final List<String> list1, final List<String> list2) {

        if (detailPosition == -1) {
            detailPosition = 0;
            picSpacePosition = 0;
        }
        url1 = list1.get(detailPosition);
        url2 = list2.get(picSpacePosition);
        Glide.with(activity)
                .load(url1)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {


                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        //加载完成后的处理
                        bitmap1 = ThumbnailUtils.extractThumbnail(resource, 8, 8);
                        glidePic(url2, new GlideLoadListener() {
                            @Override
                            public void loadFinish(Bitmap bitmap) {
                                bitmap2 = ThumbnailUtils.extractThumbnail(bitmap, 8, 8);
//                                Toast.makeText(MainActivity.this, urlPosition + "", Toast.LENGTH_SHORT).show();
                                String picHexString1 = PicUtils.getPicHexString(bitmap1);
                                String picHexString2 = PicUtils.getPicHexString(bitmap2);

                                int diff = PicUtils.diff(picHexString1, picHexString2);
                                if (diff < 3) {
                                    LogUtils.e("相似:" + "\n" + url1 + "\n" + url2);
                                    detailPosition++;
                                    picSpacePosition = 0;
                                } else {
                                    picSpacePosition++;
                                }

                                if (picSpacePosition == list2.size()) {
                                    picSpacePosition = 0;
                                }
                                if (detailPosition < list1.size()) {
                                    diffResult(list1, list2);
                                }


                            }
                        });

                    }
                });


    }


    private void glidePic(String loadurl, final GlideLoadListener glideLoadListener) {
        Glide.with(activity)
                .load(loadurl)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        //加载完成后的处理
                        glideLoadListener.loadFinish(resource);
                    }
                });
    }


}
