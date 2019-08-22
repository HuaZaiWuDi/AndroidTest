package com.wesmarclothing.jniproject.kodeinDi

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wesmarclothing.jniproject.R
import com.wesmarclothing.kotlintools.kotlin.utils.load
import kotlinx.android.synthetic.main.activity_kodein_di.*


class KodeinDiActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kodein_di)


        val colors = listOf(
            R.mipmap.ic_1,
            R.mipmap.ic_2,
            R.mipmap.ic_3,
            R.mipmap.ic_4
        )
        for (i in 0..14) {
            val tv = ImageView(this)
//            tv.setBackgroundColor(colors[i])
//            tv.layoutParams = ViewGroup.LayoutParams(-1, -1)
            tv.load(colors[i % colors.size])
            tv.scaleType=ImageView.ScaleType.FIT_XY
            voronoi.addView(tv)
        }
        voronoi.setOnRegionClickListener { view, position ->
            Toast.makeText(this, "$position", Toast.LENGTH_SHORT).show()
        }

//        iv.load(colors[0])



    }
}


class UserManager private constructor() {
    private object SingletonHolder {
        val holder = UserManager()
    }

    companion object {
        val instance = SingletonHolder.holder
    }

    fun getName(): String {
        return "CASE"
    }
}

class ActivityManager private constructor() {
    private object SingletonHolder {
        val holder = ActivityManager()
    }

    companion object {
        val instance = SingletonHolder.holder
    }

    fun getName(): String {
        return "ActivityManager.class"
    }
}
