package com.lzk.stateviewtest;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author LiaoZhongKai
 * @date 2019/8/16.
 */
public abstract class BaseActivity extends AppCompatActivity implements IStateView{

    private static final int NORMAL_STATE = 0;
    private static final int LOADING_STATE = 1;
    private static final int ERROR_STATE = 2;
    private static final int EMPTY_STATE = 3;

    private int currentState = NORMAL_STATE;

    private ViewGroup mNormalView;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private TextView mLoadingTip;
    private TextView mErrorTip;
    private TextView mEmptyTip;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("shiZi","Base onCreate");
        setContentView(getLayoutId());
        Log.d("shiZi","Base after setContentView");
        initStateView();
    }

    /**
     * 获取当前Activity的UI布局
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    private void initStateView(){
        mNormalView = findViewById(R.id.normal_view_ll);
        if (mNormalView == null){
            throw new IllegalStateException(
                    "The subclass of RootActivity must contain a View named 'mNormalView'.");
        }

        if (!(mNormalView.getParent() instanceof ViewGroup)) {
            throw new IllegalStateException(
                    "mNormalView's ParentView should be a ViewGroup.");
        }

        ViewGroup mParent = (ViewGroup) mNormalView.getParent();
        View.inflate(this, R.layout.layout_loading_view, mParent);
        View.inflate(this, R.layout.layout_error_view, mParent);
        View.inflate(this,R.layout.layout_empty,mParent);

        mLoadingView = mParent.findViewById(R.id.loading_view_fl);
        mErrorView = mParent.findViewById(R.id.error_view_rl);
        mEmptyView = mParent.findViewById(R.id.empty_view_rl);

        mLoadingTip = mParent.findViewById(R.id.loading_tip_tv);
        mErrorTip = mParent.findViewById(R.id.error_view_tip_tv);
        mEmptyTip = mParent.findViewById(R.id.empty_tip_tv);


        Button mErrorRetryBtn = findViewById(R.id.error_view_retry_btn);
        mErrorRetryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });

        Button mEmptyRetryBtn = findViewById(R.id.empty_retry_btn);
        mEmptyRetryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });

        mLoadingView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mNormalView.setVisibility(View.VISIBLE);
    }

    private void hideCurrentView() {
        switch (currentState) {
            case NORMAL_STATE:
                if (mNormalView == null) {
                    return;
                }
                mNormalView.setVisibility(View.INVISIBLE);
                break;
            case LOADING_STATE:
                mLoadingView.setVisibility(View.GONE);
                break;
            case ERROR_STATE:
                mErrorView.setVisibility(View.GONE);
                break;
            case EMPTY_STATE:
                mEmptyView.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoading(String tip) {
        if (currentState == LOADING_STATE || mLoadingView == null) {
            return;
        }
        hideCurrentView();
        currentState = LOADING_STATE;
        mLoadingView.setVisibility(View.VISIBLE);
        if (tip != null && !tip.equals("")){
            mLoadingTip.setText(tip);
        }else {
            mLoadingTip.setText("加载中...");
        }
    }

    @Override
    public void showError(String tip) {
        if (currentState == ERROR_STATE){
            return;
        }
        hideCurrentView();
        currentState = ERROR_STATE;
        mErrorView.setVisibility(View.VISIBLE);
        if (tip != null && !tip.equals("")){
            mErrorTip.setText(tip);
        }else {
            mErrorTip.setText("发生错误了");
        }
    }

    @Override
    public void showEmpty(String tip) {
        if (currentState == EMPTY_STATE){
            return;
        }
        hideCurrentView();
        currentState = EMPTY_STATE;
        mEmptyView.setVisibility(View.VISIBLE);
        if (tip != null && !tip.equals("")){
            mEmptyTip.setText(tip);
        }else {
            mErrorTip.setText("这里什么都没有");
        }
    }

    @Override
    public void showNormal() {
        if (currentState == NORMAL_STATE){
            return;
        }
        hideCurrentView();
        currentState = NORMAL_STATE;
        mNormalView.setVisibility(View.VISIBLE);
    }

    @Override
    public void reload() {

    }
}
