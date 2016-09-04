package com.example.administrator.myanimotion;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

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

public class MyListView extends ListView implements AbsListView.OnScrollListener{
    boolean isCanDrag;
    boolean isDraging;
    boolean isRefreshing;
    ViewGroup.LayoutParams layoutParams;
    int desHeight;
    int height;
    int width;

    public MyListView(Context context) {
        super(context);
        init();
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    ImageView iv;


    public void init(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.head, null);
        iv = (ImageView) view.findViewById(R.id.iv);
        layoutParams = iv.getLayoutParams();
        height = layoutParams.height;
        desHeight = layoutParams.height-height;
        //先手动测量宽高
        iv.measure(MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED),MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED));
        WindowManager manager = (WindowManager) iv.getContext().getSystemService(Context.WINDOW_SERVICE);
        width = manager.getDefaultDisplay().getWidth();
        Log.e("自定义标签", "类名==MyListView" + "方法名==init=====:" + width);
        addHeaderView(view);
        setOnScrollListener(this);
    }

    float downX;
    float downY;
    
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(isCanDrag)
                {
                        if(ev.getY()-downY>0)
                        {
                            layoutParams.height = (int) (height+(ev.getY()-downY)/3);
                            layoutParams.width = (int) (width+((ev.getY()-downY)/2));
                            isDraging = true;
                        }
                        else if(isDraging&&ev.getY()-downY<0)
                        {
                            if(layoutParams.height>height)
                            {
                                layoutParams.height = (int) (height-(ev.getY()-downY)/3);
                                layoutParams.width = (int) (width-((ev.getY()-downY)/2));
                                isDraging = true;
                            }
                        }
                        else {
                            layoutParams.height = height;
                            layoutParams.width = width;
                        }
                    iv.requestLayout();
                }
                break;
            case MotionEvent.ACTION_UP:
                if(isDraging)
                {
                    desHeight = layoutParams.height-height;
                    if(layoutParams.height - height>0)
                    {
                        MyAnimotion myAnimotion = new MyAnimotion(iv, layoutParams.height - height,layoutParams.width-width);
                        myAnimotion.setDuration(400);
                        iv.startAnimation(myAnimotion);
                    }
                    if(!isRefreshing&&desHeight>200&&onRefreshListenr!=null)
                    {
                        onRefreshListenr.onRefresh();
                        Toast.makeText(iv.getContext(),"开始刷新",Toast.LENGTH_SHORT).show();
                        isRefreshing = true;
                    }
                }
                isDraging = false;
                break;
        }
        if(isDraging)
            return true;
        else
            return super.onTouchEvent(ev);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem==0)
        {
            View childAt = view.getChildAt(0);
            if(childAt!=null&&childAt.getTop()==0)
            {
                isCanDrag = true;
            }
            else
                isCanDrag = false;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(layoutParams.width>width)
        {
            int cha = (layoutParams.width-width)/2;
            iv.layout(-cha,0,width+cha,layoutParams.height);
        }
    }

    OnRefreshListener onRefreshListenr;
    public void setOnRefreshListenr(OnRefreshListener onRefreshListenr){
        this.onRefreshListenr = onRefreshListenr;
    }

    public void RefreshComplete(){
        desHeight = 0;
        isRefreshing = false;
        Toast.makeText(iv.getContext(),"刷新完成",Toast.LENGTH_SHORT).show();
    }

    interface OnRefreshListener{
        void onRefresh();
    }
}
