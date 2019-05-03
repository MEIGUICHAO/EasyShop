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

import java.util.ArrayList;
import java.util.List;

public class Ali1688Biz extends BaseBiz {
    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private int detailPosition = -1;
    private int picSpacePosition = -1;
    private String resultUrl = "";
    private String url;
    private ArrayList<String> detailsList;
    private ArrayList<String> picSpacelsList;

    public void diffResult(final List<String> list1, final List<String> list2) {
//        LogUtils.e("list1:" + list1.size() + ",list2:" + list2.size());

        if (detailPosition == -1 && picSpacePosition == -1) {
            detailPosition = 0;
            picSpacePosition = 0;
            detailsList = new ArrayList<>();
            picSpacelsList = new ArrayList<>();
            url = list1.get(detailPosition).split("\n")[0];
        } else if (detailPosition == list1.size() - 1) {
            url = list2.get(picSpacePosition).split("\n")[0];
        }
        glidePic(url, new GlideLoadListener() {
            @Override
            public void loadFinish(Bitmap bitmap) {
                bitmap = ThumbnailUtils.extractThumbnail(bitmap, 8, 8);
                String picHexString = PicUtils.getPicHexString(bitmap);
                if (detailPosition < list1.size()-1) {
                    detailsList.add(list1.get(detailPosition) + "\n" + picHexString);
                    detailPosition++;
                    url = list1.get(detailPosition).split("\n")[0];
                    diffResult(list1, list2);
                } else if (picSpacePosition < list2.size() - 1) {
                    picSpacelsList.add(list2.get(picSpacePosition) + "\n" + picHexString);
                    picSpacePosition++;
                    url = list2.get(picSpacePosition).split("\n")[0];
                    diffResult(list1, list2);
                } else {
                    String positonStrs = "";
                    for (int i = 0; i < detailsList.size(); i++) {
                        for (int j = 0; j < picSpacelsList.size(); j++) {
                            int diff = PicUtils.diff(detailsList.get(i).split("\n")[2], picSpacelsList.get(j).split("\n")[2]);
                            if (diff <= 3) {
                                LogUtils.e(i+",相似:" + diff + "详情:\n" + detailsList.get(i) + "图片空间:\n" + picSpacelsList.get(j));
                                positonStrs = positonStrs + "," + i;
                            }
                        }
                    }
                    LogUtils.e(positonStrs);
                    detailPosition = -1;
                    picSpacePosition = -1;
                }
            }
        });

//        if (resultUrl.contains(url2)) {
//            picSpacePosition++;
//            if (picSpacePosition == list2.size()) {
//                picSpacePosition = 0;
//                detailPosition++;
//            }
//            if (detailPosition < list1.size()) {
//                diffResult(list1, list2);
//            } else {
//                detailPosition = -1;
//                picSpacePosition = -1;
//                resultUrl = "";
//            }
//            return;
//        }
//        Glide.with(activity)
//                .load(url)
//                .asBitmap()
//                .into(new SimpleTarget<Bitmap>() {
//
//
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        //加载完成后的处理
//                        bitmap1 = ThumbnailUtils.extractThumbnail(resource, 8, 8);
//                        glidePic(url2, new GlideLoadListener() {
//                            @Override
//                            public void loadFinish(Bitmap bitmap) {
//                                bitmap2 = ThumbnailUtils.extractThumbnail(bitmap, 8, 8);
////                                Toast.makeText(MainActivity.this, urlPosition + "", Toast.LENGTH_SHORT).show();
//                                String picHexString1 =PicUtils.getPicHexString(bitmap1);
//                                String picHexString2 = PicUtils.getPicHexString(bitmap2);
//
//                                int diff = PicUtils.diff(picHexString1, picHexString2);
//                                if (diff < 3) {
//                                    LogUtils.e(detailPosition + "---" + picSpacePosition + ",相似:" + diff + "\n" + url + "\n" + url2);
//                                    detailPosition++;
//                                    picSpacePosition = 0;
//                                    resultUrl = TextUtils.isEmpty(resultUrl) ? url2 : resultUrl + "###" + url2;
//                                } else {
//                                    picSpacePosition++;
//                                }
//
//                                if (picSpacePosition == list2.size()) {
//                                    picSpacePosition = 0;
//                                    detailPosition++;
//                                }
//                                if (detailPosition < list1.size()) {
//                                    diffResult(list1, list2);
//                                } else {
//                                    detailPosition = -1;
//                                    picSpacePosition = -1;
//                                    resultUrl = "";
//                                }
//
//
//                            }
//                        });
//
//                    }
//                });


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
