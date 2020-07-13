package com.android.group.sample;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.view.View;

import com.android.common.common.Common;
import com.android.install.view.UpdateApkDialog;
import com.android.mvp.view.BaseActivity;
import com.android.group.sample.ui.fragment.Test1Fragment;

import java.io.File;

/**
 * @author chicunxiang
 */
public class MainActivity extends BaseActivity<MainPresenter> {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView() {
        Common.init(this);
        loadRootFragment(R.id.fl, new Test1Fragment());
        UpdateApkDialog updateApkDialog = new UpdateApkDialog(this);
        updateApkDialog.popDialog();
        updateApkDialog.setDownloadApkPathUrl("res/test.apk");
        updateApkDialog.setDownloadApkSavePath(Environment.getExternalStorageDirectory() + File.separator + "test.apk");
    }


    @Override
    protected View contentLayout() {
        return null;
    }

    @Override
    protected int contentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainPresenter initPresenter() {
        return new MainPresenter();
    }


}
