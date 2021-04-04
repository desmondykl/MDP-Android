package com.example.mdp_android_grp17.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mdp_android_grp17.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4};
    private final List<Fragment> fragmentsA =  new ArrayList<>();
    private final List<String> fragmentsTitle =  new ArrayList<>();
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
    public void addFragment (Fragment fragment, String Title){
        fragmentsA.add(fragment);
        fragmentsTitle.add(Title);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentsA.get(position);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsTitle.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsA.size();
    }

}