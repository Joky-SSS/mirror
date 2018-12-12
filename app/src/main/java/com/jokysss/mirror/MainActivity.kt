package com.jokysss.mirror

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import java.util.*

class MainActivity : AppCompatActivity() {
    private val pageList = ArrayList<PageModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        pageList.add(PageModel(R.layout.search_view, R.string.search_view))
        pageList.add(PageModel(R.layout.poly_to_poly, R.string.poly2poly))
        pageList.add(PageModel(R.layout.fancy, R.string.fancy))
        pageList.add(PageModel(R.layout.location, R.string.location))
        pageList.add(PageModel(R.layout.halo, R.string.halo))

        val pager = findViewById<ViewPager>(R.id.vp_content)
        pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return PageFragment.newInstance(pageList[position].layoutId)
            }

            override fun getCount(): Int {
                return pageList.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return getString(pageList[position].titleResId)
            }
        }
        val tab = findViewById<TabLayout>(R.id.tl_tab)
        tab.setupWithViewPager(pager)
    }

    inner class PageModel(@param:LayoutRes @field:LayoutRes var layoutId: Int, @param:StringRes @field:StringRes var titleResId: Int)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
