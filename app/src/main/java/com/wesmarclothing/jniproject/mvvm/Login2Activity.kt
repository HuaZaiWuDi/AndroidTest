package com.wesmarclothing.jniproject.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.vondear.rxtools.utils.bitmap.RxCameraUtils
import com.wesmarclothing.jniproject.R
import com.wesmarclothing.jniproject.RxPermissionsUtils
import com.wesmarclothing.jniproject.databinding.ActivityLoginBinding
import com.wesmarclothing.jniproject.onRequestPermissionsListener

/**
 * @Package com.wesmarclothing.jniproject.mvvm
 * @FileName Login2Activity
 * @Date 2019/7/19 14:26
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */
class Login2Activity : AppCompatActivity() {


    private val viewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityLoginBinding>(
            this,
            R.layout.activity_login
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.testJson.observe(this) {
            binding.testJson = it
        }

        observe(viewModel.testJson) {

        }


    }

}