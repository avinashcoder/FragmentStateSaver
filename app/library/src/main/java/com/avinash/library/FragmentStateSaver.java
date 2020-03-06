package com.avinash.library;

/**
 * Created by Avinash on 03/03/2020.
 */

import android.os.Build;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

public abstract class FragmentStateSaver {
    private static final String TAG = "FragmentStateManager";
    private static final boolean DEBUG = false;

    private final FragmentManager mFragmentManager;
    ViewGroup container;

    public FragmentStateSaver(ViewGroup container, FragmentManager fm) {
        mFragmentManager = fm;
        this.container = container;
    }

    /**
     * Return the Fragment associated with a specified position.
     */
    public abstract Fragment getItem(int position);

    /**
     *
     * @param position
     * @return
     */
    public Fragment changeFragment(int position) {
        String tag = makeFragmentName(container.getId(), getItemId(position));
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        /*
          If fragment manager doesn't have an instance of the fragment, get an instance
          and add it to the transaction. Else, attach the instance to transaction.
         */
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = getItem(position);
            fragmentTransaction.add(container.getId(), fragment, tag);
        } else {
            fragmentTransaction.show(fragment);
        }

        // Detach existing primary fragment
        Fragment curFrag = mFragmentManager.getPrimaryNavigationFragment();
        if (curFrag != null && curFrag!=fragment) {
            fragmentTransaction.hide(curFrag);
        }

        // Set fragment as primary navigator for child manager back stack to be handled by system
        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNowAllowingStateLoss();

        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void removeFragment(int position) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.remove(Objects.requireNonNull(mFragmentManager.findFragmentByTag(makeFragmentName(container.getId(), getItemId(position)))));
        fragmentTransaction.commitNowAllowingStateLoss();
    }

    /**
     * Return a unique identifier for the item at the given position.
     * <p>
     * <p>The default implementation returns the given position.
     * Subclasses should override this method if the positions of items can change.</p>
     *
     * @param position Position within this adapter
     * @return Unique identifier for the item at position
     */
    public long getItemId(int position) {
        return position;
    }

    public static String makeFragmentName(int viewId, long id) {
        return "android:fragment:" + viewId + ":" + id;
    }
}
