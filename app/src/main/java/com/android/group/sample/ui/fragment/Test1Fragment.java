package com.android.group.sample.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.common.base.SupportFragmentImp;
import com.android.common.bus.EventBus;
import com.android.common.bus.EventBusSubscriptions;
import com.android.data.helper.SharedPreferencesHelper;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author chicunxiang
 */
public class Test1Fragment extends SupportFragmentImp {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(mActivity);
        textView.setText("1号界面");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(new Test2Fragment());
            }
        });

        EventBusSubscriptions.bindAll(this,
                EventBus.get().subscribe(String.class).map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) throws Exception {
                        return o;
                    }
                }).subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        System.out.println("我在1号界面收到了 " + o);
                    }
                }),
                EventBus.get().subscribe(Integer.class).map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) throws Exception {
                        return o;
                    }
                }).subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        System.out.println("我在1号界面收到了 " + o);
                    }
                }));


        SharedPreferencesHelper.getInstance().put("abc", "123");
        return textView;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusSubscriptions.unbind(this);
    }
}
