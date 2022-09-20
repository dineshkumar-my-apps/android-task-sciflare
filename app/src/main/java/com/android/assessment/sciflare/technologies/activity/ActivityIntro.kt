package com.android.assessment.sciflare.technologies.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.assessment.sciflare.technologies.databinding.LayoutIntroBinding

class ActivityIntro : AppCompatActivity() {

    lateinit var binding: LayoutIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textStart.setOnClickListener {
            val intent = Intent(this@ActivityIntro, ActivityHome::class.java)
            startActivity(intent)
            finish()
        }
    }
}