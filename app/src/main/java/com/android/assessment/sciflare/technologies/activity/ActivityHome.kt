package com.android.assessment.sciflare.technologies.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.assessment.sciflare.technologies.databinding.LayoutHomeBinding
import com.android.assessment.sciflare.technologies.support.AppString
import com.android.assessment.sciflare.technologies.support.AppUtils

class ActivityHome: AppCompatActivity() {

    lateinit var binding: LayoutHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textStart1.setOnClickListener {
            if (!AppUtils.checkNetworkAvailable(this@ActivityHome)) {
                AppUtils.toastNormal(this@ActivityHome, AppString.textInternetCheck)
                return@setOnClickListener
            }
            val intent = Intent(this@ActivityHome, ActivityTask1::class.java)
            startActivity(intent)
        }

        binding.textStart2.setOnClickListener {
            if (!AppUtils.checkNetworkAvailable(this@ActivityHome)) {
                AppUtils.toastNormal(this@ActivityHome, AppString.textInternetCheck)
                return@setOnClickListener
            }
            val intent = Intent(this@ActivityHome, ActivityTask2::class.java)
            startActivity(intent)
        }
    }
}