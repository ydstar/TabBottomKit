package com.example.tabbottomkit.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

open abstract class BaseFragment : Fragment() {
    protected var mLayoutView: View? = null

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mLayoutView = inflater.inflate(getLayoutId(), container, false)
        return mLayoutView
    }

    //检测 宿主 是否还存活
    fun isAlive(): Boolean {
        return if (isRemoving || isDetached || activity == null) {
            false
        } else true
    }

    fun showToast(message: String?) {
        if (TextUtils.isEmpty(message)) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

}