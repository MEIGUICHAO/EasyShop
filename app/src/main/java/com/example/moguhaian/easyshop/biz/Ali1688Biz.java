package com.example.moguhaian.easyshop.biz;

import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.moguhaian.easyshop.Base.BaseBiz;
import com.example.moguhaian.easyshop.listener.GlideLoadListener;

public class Ali1688Biz extends BaseBiz {

//    private void diffResult() {
//        glidePic(urlsArray[urlPosition], new GlideLoadListener() {
//            @Override
//            public void loadFinish(Bitmap bitmap) {
//                bitmap2 = ThumbnailUtils.extractThumbnail(bitmap, 8, 8);
////                                Toast.makeText(MainActivity.this, urlPosition + "", Toast.LENGTH_SHORT).show();
//                String picHexString1 = PicUtils.getPicHexString(bitmap1);
//                String picHexString2 = PicUtils.getPicHexString(bitmap2);
//                Log.e("urlPosition!!!!!!!!!:", urlPosition + "");
//                int diff = PicUtils.diff(picHexString1, picHexString2);
//                if (diff <= 3) {
//                    Log.e("urlResult:", urlsArray[urlPosition] + "");
//                }
//                urlPosition++;
//                if (urlPosition < urlsArray.length) {
//                    diffResult();
//                }
//            }
//        });
//    }


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
