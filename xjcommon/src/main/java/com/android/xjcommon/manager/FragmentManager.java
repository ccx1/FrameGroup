package com.android.xjcommon.manager;

import android.support.v4.app.Fragment;

import com.android.xjcommon.base.SupportFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * @author chicunxiang
 */
public class FragmentManager {
    private static List<SupportFragment> task = new ArrayList<>();
    private static FragmentManager       sFragmentManager;

    private FragmentManager() {
    }

    public static FragmentManager getInstance() {
        if (sFragmentManager == null) {
            sFragmentManager = new FragmentManager();
        }
        return sFragmentManager;
    }

    public void pushOneFragment(SupportFragment f) {
        if (f != null && !task.contains(f)) {
            task.add(0, f);
        }
    }

    public void PopOneFragment(SupportFragment f) {
        if (task.contains(f)) {
            task.remove(f);
            f = null;
        }
    }

    public int getFragmentTaskSize() {
        return task.size();
    }

    public SupportFragment getTopFragment() {
        SupportFragment fragment = null;
        if (task.size() != 0) {
            fragment = task.get(0);
        }
        return fragment;
    }

    public List<SupportFragment> getWillPopFragments(android.support.v4.app.FragmentManager supportFragmentManager, String tag, boolean includeTargetFragment) {
        Fragment targetFragment = supportFragmentManager.findFragmentByTag(tag);

        List<SupportFragment> list = new ArrayList<>();
        if (targetFragment == null) {
            return list;
        }
        int startIndex = -1;
        for (int i = 0; i < task.size(); i++) {
            if (task.get(i) == targetFragment) {
                // 如果等于true。则为包含此
                // 如果为false，则不包含此
                if (includeTargetFragment) {
                    startIndex = i;
                } else {
                    startIndex = i - 1;
                }
                break;
            }
        }
        if (startIndex == -1) {
            return list;
        }
        // startIndex 是目标位置。
        for (int i = 0; i <= startIndex; i++) {
            Fragment fragment = (Fragment) task.get(i);
            //  如果为null，则不需要这样的fragment
            if (fragment != null && fragment.getView() != null) {
                list.add(task.get(i));
            }
        }
        return list;
    }

}
