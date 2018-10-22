package com.jokysss.mirror;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<PageModel> pageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pageList.add(new PageModel(R.layout.search_view,R.string.search_view));
        pageList.add(new PageModel(R.layout.poly_to_poly, R.string.poly2poly));
        pageList.add(new PageModel(R.layout.fancy, R.string.fancy));
        pageList.add(new PageModel(R.layout.location, R.string.location));
        pageList.add(new PageModel(R.layout.halo, R.string.halo));

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
