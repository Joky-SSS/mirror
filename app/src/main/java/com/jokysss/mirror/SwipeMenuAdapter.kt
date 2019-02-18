package com.jokysss.mirror

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.jokysss.mirror.widget.TipsView
import kotlinx.android.synthetic.main.simple_menu_item.view.*

class SwipeMenuAdapter : BaseAdapter() {
    var data = mutableListOf<MutableMap<String, String>>()

    init {
        for (i in 0..50) {
            val item = mutableMapOf<String, String>()
            item["name"] = "ITEM-$i"
            item["sort"] = "$i"
            data.add(item)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var layout: View? = convertView
        val holder: Holder?
        if (layout == null) {
            layout = LayoutInflater.from(parent!!.context).inflate(R.layout.simple_menu_item, parent, false)
            holder = Holder(position)
            holder.name = layout.name
            holder.sort = layout.sort
            holder.num = layout.num
            layout.tag = holder
        } else {
            holder = layout.tag as Holder?
        }
        holder?.name!!.text = data[position]["name"]
        holder.sort!!.text = data[position]["sort"]
        TipsView.create(parent!!.context as Activity?).attach(holder.num)
        return layout!!
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }

    data class Holder(var position: Int) {
        var name: TextView? = null
        var sort: TextView? = null
        var num: TextView? = null
    }
}