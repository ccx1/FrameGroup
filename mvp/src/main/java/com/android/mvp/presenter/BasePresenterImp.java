package com.android.mvp.presenter;

import android.content.Context;

import com.android.common.bus.EventBus;
import com.android.common.bus.EventBusSubscriptions;
import com.android.common.utils.ToastUtil;
import com.android.mvp.view.BaseView;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * @author ccx
 * @date 2018/11/21
 */
public abstract class BasePresenterImp<V extends BaseView> implements BasePresenter<V> {

    protected Context mContext;
    protected V       mView;

    @Override
    public void attachView(V view, Context context) {
        mView = view;
        mContext = context;
        initPresenterData();
    }

    @Override
    public void setData() {
    }

    @Override
    public void retry() {
    }

    @Override
    public void setData(Object o) {
    }

    @Override
    public <T> void createBusInstance(final Class<T> clazz, Consumer<? super T> action) {
        Disposable subscribe = EventBus.get()
                .subscribe(clazz)
                .filter(new Predicate<T>() {
                    @Override
                    public boolean test(T t) throws Exception {
                        return t.getClass() == clazz;
                    }
                })
                .cast(clazz)
                .subscribe(action, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // 统一上报处理
                        showToast(throwable.toString());
                    }
                });
        EventBusSubscriptions.bind(mView, subscribe);
    }

    @Override
    public void showToast(int stringId) {
        ToastUtil.showToast(stringId);
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showToast(msg);
    }

    @Override
    public String getString(int strId) {
        return mContext.getString(strId);
    }

    @Override
    public void onDestroy() {
        EventBusSubscriptions.unbind(mView);
    }
}
