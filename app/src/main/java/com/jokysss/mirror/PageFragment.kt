package com.jokysss.mirror


import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub


/**
 * A simple [Fragment] subclass.
 * Use the [PageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PageFragment : androidx.fragment.app.Fragment() {
    private var mViewResId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mViewResId = arguments!!.getInt(ARG_VIEWRESID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_page, container, false)
        val stub = view.findViewById<ViewStub>(R.id.vs_stub)
        stub.layoutResource = mViewResId
        stub.inflate()
        return view
    }

    companion object {
        private val ARG_VIEWRESID = "ViewResId"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param viewResId Parameter 1.
         * @return A new instance of fragment PageFragment.
         */
        fun newInstance(@LayoutRes viewResId: Int): PageFragment {
            val fragment = PageFragment()
            val args = Bundle()
            args.putInt(ARG_VIEWRESID, viewResId)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
