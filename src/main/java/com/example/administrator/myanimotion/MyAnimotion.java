package com.example.administrator.myanimotion;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * /**
 * <p/>
 * ----------Dragon be here!----------/
 * 　　　 ┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 */

public class MyAnimotion extends Animation {
    private View view;
    private int changeX;
    private int changeY;
    private float yuanshigaodu;
    private float yuanshikuandu;

    public MyAnimotion(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyAnimotion() {
    }

    public MyAnimotion(View view, int changeX,int changeY) {
        this.view = view;
        this.changeX = changeX;
        this.changeY = changeY;
        this.yuanshigaodu = view.getLayoutParams().height;
        this.yuanshikuandu = view.getLayoutParams().width;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        view.getLayoutParams().height = (int) (yuanshigaodu-(changeX*interpolatedTime));
        view.getLayoutParams().width = (int) (yuanshikuandu-(changeY*interpolatedTime));
        view.requestLayout();
    }
}
