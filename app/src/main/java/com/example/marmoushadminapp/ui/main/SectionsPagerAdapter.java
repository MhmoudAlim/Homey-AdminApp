package com.example.marmoushadminapp.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.marmoushadminapp.OrderFregmant;
import com.example.marmoushadminapp.R;
import com.example.marmoushadminapp.uploadFregmant;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
Fragment[] fragments=new Fragment[]{new uploadFregmant(),new OrderFregmant()};
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
       if (position==0)return "upload";
       else return "Orders";
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}