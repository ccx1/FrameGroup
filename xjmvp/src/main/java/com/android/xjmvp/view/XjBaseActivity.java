package com.android.xjmvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.xjcommon.action.Action1;
import com.android.xjcommon.base.XjSupportActivityImp;
import com.android.xjcommon.helper.XjPermissionsHelper;
import com.android.xjmvp.R;
import com.android.xjmvp.presenter.XjBasePresenter;
import com.android.xjmvp.widget.StatusLayout;

/**
 * @author ccx
 * @date 2018/11/22
 */
public abstract class XjBaseActivity<P extends XjBasePresenter> extends XjSupportActivityImp implements XjBaseView {

    private              P                   mPresenter;
    private              XjPermissionsHelper mPermissionsHelper;
    private              StatusLayout        mStatusLayout;
    private static final int                 defaultBaseContentId = 100092;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
        mPresenter.attachView(this, this);
        setContentView(R.layout.activity_base);
        initCenterView();
        initView();
    }

    protected abstract void initView();

    private void initCenterView() {
        mStatusLayout = findViewById(R.id.base_status_layout);
        int layoutId = contentLayoutId();
        if (layoutId != 0) {
            mStatusLayout.setContentView(layoutId);
        } else {
            mStatusLayout.setContentView(contentLayout());
        }
        mStatusLayout.setRetryOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.retry();
            }
        });
    }

    protected abstract View contentLayout();

    protected abstract int contentLayoutId();

    @Override
    public void setContentView(int layoutResID) {
        mStatusLayout.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        if (view.getId() == -1) {
            view.setId(defaultBaseContentId);
        }
        mStatusLayout.setContentView(view);
    }

    public abstract P initPresenter();

    @Override
    public void showContent() {
        mStatusLayout.showContent();
    }

    @Override
    public void showLoading() {
        mStatusLayout.showLoading();
    }

    @Override
    public void showError() {
        mStatusLayout.showError();
    }

    @Override
    public void showEmpty() {
        mStatusLayout.showEmpty();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void requestPermission(String[] permissions, Action1<Boolean> action1) {
        // 避免造成不必要的浪费
        if (mPermissionsHelper == null) {
            mPermissionsHelper = new XjPermissionsHelper(this);
        }
        mPermissionsHelper
                .request(permissions)
                .subscribe(action1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 防止没有请求权限直接setResult，导致程序崩溃
        if (mPermissionsHelper != null) {
            mPermissionsHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }
}
