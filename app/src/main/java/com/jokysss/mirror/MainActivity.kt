package com.jokysss.mirror

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
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

        val pager = findViewById<androidx.viewpager.widget.ViewPager>(R.id.vp_content)
        pager.adapter = object : androidx.fragment.app.FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): androidx.fragment.app.Fragment {
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
