package com.example.moguhaian.easyshop.biz;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.moguhaian.easyshop.Base.BaseBiz;
import com.example.moguhaian.easyshop.Base.Constants;
import com.example.moguhaian.easyshop.Utils.LogUtils;
import com.example.moguhaian.easyshop.Utils.PicUtils;
import com.example.moguhaian.easyshop.Utils.SharedPreferencesUtils;
import com.example.moguhaian.easyshop.Utils.ToastUtils;
import com.example.moguhaian.easyshop.listener.GlideLoadListener;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Ali1688Biz extends BaseBiz {
    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private int detailPosition = -1;
    private int picSpacePosition = -1;
    private String resultUrl = "";
    private String url;
    private ArrayList<String> detailsList;
    private ArrayList<String> picSpacelsList;

    public ArrayList<String> getCompareResultList() {
        return compareResultList;
    }

    private ArrayList<String> compareResultList = new ArrayList<>();

    public interface DiffProgressListener {
        void diffFinish();
    }

    public void resetPosition() {
        detailPosition = -1;
        picSpacePosition = -1;
    }


    public void diffResult(final List<String> list1, final List<String> list2, final DiffProgressListener listener) {
        if (detailPosition == -1 && picSpacePosition == -1) {
            detailPosition = 0;
            picSpacePosition = 0;
            detailsList = new ArrayList<>();
            picSpacelsList = new ArrayList<>();
            compareResultList.clear();
            url = list1.get(detailPosition).split("\n")[0];
        } else if (detailPosition == list1.size() - 1) {
            url = list2.get(picSpacePosition).split("\n")[0];
        }
        LogUtils.e("总数:" +( list1.size() + list2.size()) + ",进度:" + (detailPosition + picSpacePosition));
        if (!url.equals("~~")) {

            glidePic(url, new GlideLoadListener() {
                @Override
                public void loadFinish(Bitmap bitmap) {
                    bitmap = ThumbnailUtils.extractThumbnail(bitmap, 8, 8);
                    String picHexString = PicUtils.getPicHexString(bitmap);
                    if (detailPosition < list1.size() - 1) {
                        detailsList.add(list1.get(detailPosition) + "\n" + picHexString);
                        detailPosition++;
                        url = list1.get(detailPosition).split("\n")[0];
                        diffResult(list1, list2, listener);
                    } else if (picSpacePosition < list2.size() - 1) {
                        picSpacelsList.add(list2.get(picSpacePosition) + "\n" + picHexString);
                        picSpacePosition++;
                        url = list2.get(picSpacePosition).split("\n")[0];
                        diffResult(list1, list2, listener);
                    } else {
                        String positonStrs = "";
                        String picSpaceName = "";
                        for (int i = 0; i < detailsList.size(); i++) {
                            TreeMap<Integer, String> treeMap = new TreeMap<>();
                            TreeMap<Integer, String> treePicNameMap = new TreeMap<>();
                            String[] detailArray = detailsList.get(i).split("\n");

                            if (detailsList.get(i).contains("~~")) {
                                compareResultList.add(detailsList.get(i).split("\n")[1] + "\n" + "~~" + "\n" + detailsList.get(i).split("\n")[2] + "\n" + detailsList.get(i).split("\n")[3]);
                                picSpaceName = TextUtils.isEmpty(picSpaceName) ? "~~" : picSpaceName + "###" + "~~";

                            } else {
                                if (Integer.parseInt(detailArray[3]) > 50) {
                                    for (int j = 0; j < picSpacelsList.size(); j++) {
                                        int diff = PicUtils.diff(detailsList.get(i).split("\n")[4], picSpacelsList.get(j).split("\n")[2]);
                                        treeMap.put(diff, detailsList.get(i).split("\n")[1] + "\n" + picSpacelsList.get(j).split("\n")[1].replace(".jpg", "") + "\n" + detailsList.get(i).split("\n")[2] + "\n" + detailsList.get(i).split("\n")[3]);
                                        treePicNameMap.put(diff, picSpacelsList.get(j).split("\n")[1].replace(".jpg", ""));
                                        //名字、图片名字、价格、数量
//                                        LogUtils.e(">50:\n" + i + ",相似:" + diff + "详情:\n" + detailsList.get(i) + "图片空间:\n" + picSpacelsList.get(j));
                                        positonStrs = positonStrs + "," + i;
//                                        picSpaceName = TextUtils.isEmpty(picSpaceName) ? picSpacelsList.get(j).split("\n")[1].replace(".jpg", "") : picSpaceName + "###" + picSpacelsList.get(j).split("\n")[1].replace(".jpg", "");

                                    }
                                    LogUtils.e("treeMap key:" + treeMap.firstEntry().getKey());
                                    LogUtils.e("treeMap value:" + treeMap.firstEntry().getValue());
                                    LogUtils.e("treePicNameMap value:" + treePicNameMap.firstEntry().getValue());

                                    compareResultList.add(treeMap.firstEntry().getValue());
                                    String picName = treePicNameMap.firstEntry().getValue();
                                    picSpaceName = TextUtils.isEmpty(picSpaceName) ? picName : picSpaceName + "###" + picName;

                                }
                            }
                        }
                        if (!TextUtils.isEmpty(picSpaceName)) {
                            SharedPreferencesUtils.putValue(Constants.GET_UPLOAD_PIC_NAMES, picSpaceName);
                        }
                        LogUtils.e("对比结束");
                        ToastUtils.showToast("对比结束");
                        LogUtils.e("positonStrs:" + positonStrs);
                        listener.diffFinish();
                        detailPosition = -1;
                        picSpacePosition = -1;
                    }
                }
            });
        } else {
            if (detailPosition < list1.size()-1) {
                detailsList.add(list1.get(detailPosition) + "\n" + "~~");
                detailPosition++;
                url = list1.get(detailPosition).split("\n")[0];
                diffResult(list1, list2,listener);
            }
            if (list1.size() == 1) {
                String picSpaceName = "";
                detailsList.add(list1.get(detailPosition) + "\n" + "~~");
                compareResultList.add(detailsList.get(0).split("\n")[1] + "\n" + "~~" + "\n" + detailsList.get(0).split("\n")[2] + "\n" + detailsList.get(0).split("\n")[3]);
                picSpaceName = TextUtils.isEmpty(picSpaceName) ? "~~" : picSpaceName + "###" + "~~";

                if (!TextUtils.isEmpty(picSpaceName)) {
                    SharedPreferencesUtils.putValue(Constants.GET_UPLOAD_PIC_NAMES, picSpaceName);
                }

                LogUtils.e("对比结束");
                ToastUtils.showToast("对比结束");
                listener.diffFinish();
            }
        }

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
