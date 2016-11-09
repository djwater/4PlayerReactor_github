package com.yedona.a4playerreactor;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;

public class FittingBall extends AppCompatActivity {
    private RelativeLayout gameview;
    private final int UPDATA_BALLVIEW = 1,UPDATA_GATE = 0,START_ANIM = 2,NEXT = 3;
    private View bottomView,topView,preTop,prebot;
    private BallView ballView,preBallView;
    private ObjectAnimator animator,animator1;
    private int ballHeight,ballRadius,topHeight,bottomHeight,gateHeight;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATA_GATE:
                    gameview.removeView(prebot);
                    gameview.removeView(preTop);
                    gameview.addView(bottomView);
                    gameview.addView(topView);
                    prebot = bottomView;
                    preTop = topView;
                    break;
                case UPDATA_BALLVIEW:
                    gameview.removeView(preBallView);
                    gameview.addView(ballView);
                    preBallView = ballView;
                    break;
                case START_ANIM:
                    animator.start();
                    animator1.start();
                    break;
                case NEXT:
                    int x = (int) (Math.random() * 30) + 3;
                    createball();
                    createGate(x);
                    mhandler.sendEmptyMessage(UPDATA_BALLVIEW);
                    mhandler.sendEmptyMessage(UPDATA_GATE);
                    boolean right = isRight();
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitting_ball);
        gameview = ((RelativeLayout) findViewById(R.id.gameview));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void createball() {
        ballRadius = (int) (Math.random() * 130)+20;
        int i=(int) (Math.random() * 280) - 2*ballRadius;
        i = i > 0 ? i : 0;
        ballHeight = i+40;
        ballView = new BallView(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(2* ballRadius, 2* ballRadius);
        layoutParams.setMargins(0, pxChange2dp(ballHeight+20),0,0);
        ballView.setColor(Color.RED);
        ballView.setRadius(ballRadius);
        ballView.setLayoutParams(layoutParams);
        animator = ObjectAnimator.ofFloat(ballView,"translationX",0,pxChange2dp(340));
        animator.setDuration(0);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        animator1 = ObjectAnimator.ofFloat(ballView,"translationX",widthPixels,-ballRadius);
        animator1.setDuration(1000);
        mhandler.sendEmptyMessage(START_ANIM);
    }

    private void createGate(int x) {
        gateHeight = x;
        topView = new View(this);
        topView.setBackgroundColor(getResources().getColor(R.color.white));
        topHeight = 10*((int) (Math.random() * (40 - x)))+30;
        RelativeLayout.LayoutParams topParams = new RelativeLayout.LayoutParams(80, topHeight);
        topParams.setMargins(pxChange2dp(20), pxChange2dp(20), pxChange2dp(20), pxChange2dp(20));
        topView.setLayoutParams(topParams);
        bottomView = new View(this);
        bottomView.setBackgroundColor(getResources().getColor(R.color.white));
        bottomHeight = 520 - topHeight - x * 10;
        RelativeLayout.LayoutParams bottomParams = new RelativeLayout.LayoutParams(80, bottomHeight);
        bottomParams.setMargins(pxChange2dp(20), pxChange2dp(20), pxChange2dp(20),  pxChange2dp(20));
        bottomParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bottomView.setLayoutParams(bottomParams);
    }

    private int pxChange2dp(int i) {
        float v = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, i, this.getResources().getDisplayMetrics());
        return (int) v;
    }

    public boolean isRight() {
        boolean isTrue = false;
        int i = pxChange2dp(ballHeight);
        if (topHeight < i&&(530-bottomHeight>i+2*ballRadius)) {
            isTrue = true;
        }
        return true;
    }

    public void player1(View view) {

    }

    public void player2(View view) {

    }

    public void player3(View view) {
    }

    public void player4(View view) {
        mhandler.sendEmptyMessage(NEXT);
    }


}

