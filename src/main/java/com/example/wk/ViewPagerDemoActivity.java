package com.example.wk;
//实现滑动栏，轮播，具体功能：点击图片停止，点击其他地方两秒后图片切换，若不点击两秒自动循环后切换
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.wk.R.drawable.banner_dian_focus;

public class ViewPagerDemoActivity extends AppCompatActivity {
private ImageView indicator;
private ImageView[] indicators;
private boolean isContinue=true;
private ViewPager viewPager;
private ViewGroup group;
private AtomicInteger index=new AtomicInteger();
private Handler viewHandler=new Handler(){
    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        viewPager.setCurrentItem(msg.what);
    }
};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_demo);
        initView();
    }
    private void initView(){
        viewPager=findViewById(R.id.vp_adv);
        group=findViewById(R.id.view_indicators);
        List<View>listpics=new ArrayList<>();
        ImageView img1=new ImageView(this);
        img1.setBackgroundResource(R.drawable.adv_default_1);
        listpics.add(img1);
        ImageView img2=new ImageView(this);
        img2.setBackgroundResource(R.drawable.adv_default_2);
        listpics.add(img2);
        ImageView img3=new ImageView(this);
        img3.setBackgroundResource(R.drawable.adv_default_3);
        listpics.add(img3);
        ImageView img4=new ImageView(this);
        img4.setBackgroundResource(R.drawable.adv_default_4);
        listpics.add(img4);

        //动态设置
        indicators=new ImageView[listpics.size()];
        for(int i=0;i<indicators.length;i++){
            indicator= new ImageView(this);
            indicator.setLayoutParams(new LinearLayout.LayoutParams(40,40));
            indicator.setPadding(5,5,5,5);
            indicators[i]=indicator;
            if(i==0){
                indicators[i].setBackgroundResource(R.drawable.shape_point);
            }else{
                indicators[i].setBackgroundResource(R.drawable.shape_point1);
            }
            group.addView(indicators[i]);
        }
        //适配器
        viewPager.setAdapter(new MyPagerAdapter(listpics));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
             index.getAndSet(position);
             for(int i=0;i<indicators.length;i++){
                 if(i==position){
                     indicators[i].setBackgroundResource(R.drawable.shape_point);
                 }else{
                     indicators[i].setBackgroundResource(R.drawable.shape_point1);
                 }
             }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        isContinue=false;
                        break;
                    case MotionEvent.ACTION_UP:
                        isContinue=true;
                        break;
                }
                return false;
            }
        });
//使用多线程自动切换
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if(isContinue){
                        viewHandler.sendEmptyMessage(index.get());
                        whatOption();
                    }
                }
            }
        }).start();
    }
    private void whatOption(){
        index.incrementAndGet();
        if(index.get()>indicators.length-1){
            index.getAndAdd(-4);
        }
        try {
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class MyPagerAdapter extends PagerAdapter{
 private List<View> viewList;
 public MyPagerAdapter(List<View> viewList){
     this.viewList=viewList;
 }
    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
     container.addView(viewList.get(position),0);
        return viewList.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(viewList.get(position));
    }
}