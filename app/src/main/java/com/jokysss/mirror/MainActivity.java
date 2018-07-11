package com.jokysss.mirror;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<PageModel> pageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pageList.add(new PageModel(R.layout.search_view,R.string.search_view));
        ViewPager pager = findViewById(R.id.vp_content);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return PageFragment.newInstance(pageList.get(position).layoutId);
            }

            @Override
            public int getCount() {
                return pageList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return getString(pageList.get(position).titleResId);
            }
        });
        TabLayout tab = findViewById(R.id.tl_tab);
        tab.setupWithViewPager(pager);
    }
    public class PageModel{
        @LayoutRes public int layoutId;
        @StringRes public int titleResId;

        public PageModel(@LayoutRes int layoutId,@StringRes int titleResId) {
            this.layoutId = layoutId;
            this.titleResId = titleResId;
        }
    }
}
