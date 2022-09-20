package com.android.assessment.sciflare.technologies.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.assessment.sciflare.technologies.adapter.UserListAdapter
import com.android.assessment.sciflare.technologies.api.ApiHelperImpl
import com.android.assessment.sciflare.technologies.api.RetrofitBuilder
import com.android.assessment.sciflare.technologies.databinding.LayoutListItemEditBinding
import com.android.assessment.sciflare.technologies.databinding.LayoutTask1Binding
import com.android.assessment.sciflare.technologies.mvvm.RoomDBViewModel
import com.android.assessment.sciflare.technologies.mvvm.ViewModelFactory
import com.android.assessment.sciflare.technologies.room.DatabaseBuilder
import com.android.assessment.sciflare.technologies.room.DatabaseHelperImpl
import com.android.assessment.sciflare.technologies.room.entity.User
import com.android.assessment.sciflare.technologies.support.AppUtils
import com.android.assessment.sciflare.technologies.support.OnClick
import com.google.android.material.bottomsheet.BottomSheetDialog

class ActivityTask1 : AppCompatActivity(), OnClick {

    lateinit var binding: LayoutTask1Binding
    private lateinit var viewModel: RoomDBViewModel
    lateinit var adapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutTask1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        setupRecyclerview()
        setupViewModel()
        setupObserver()
    }

    private fun setupRecyclerview() {
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.addItemDecoration(
            DividerItemDecoration(
                this@ActivityTask1,
                (binding.recyclerview.layoutManager as LinearLayoutManager).orientation
            )
        )
        adapter = UserListAdapter(this@ActivityTask1, this)
        binding.recyclerview.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
            )
        )[RoomDBViewModel::class.java]
    }

    private fun setupObserver() {
        viewModel.listUsers.observe(this@ActivityTask1, Observer {
            if (it.isNotEmpty()) {
                adapter.addData(it)
                binding.layoutMenu.visibility = View.VISIBLE
                binding.layoutWarning.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
            } else {
                binding.layoutMenu.visibility = View.GONE
                binding.layoutWarning.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        })

        viewModel.result.observe(this@ActivityTask1, Observer {
            if (it == "No data") {
                binding.layoutMenu.visibility = View.GONE
                binding.layoutWarning.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.textWarning.text = it
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun userEdit(user: User) {
        dialogUpdateUser(user)
    }

    override fun userDelete(user: User) {

        AlertDialog.Builder(this@ActivityTask1)
            .setCancelable(false)
            .setTitle("Alert")
            .setMessage("Do you want to delete this user?")
            .setPositiveButton(
                "Yes"
            ) { paramDialogInterface, paramInt ->

                val data = User(
                    user._id,
                    user.name,
                    user.mobile,
                    user.email,
                    user.gender
                )
                viewModel.deleteUser(user)
            }
            .setNegativeButton(
                "No"
            ) { paramDialogInterface, paramInt ->
                paramDialogInterface.dismiss()
            }
            .show()
    }


    private fun dialogUpdateUser(user: User) {
        val dialog = BottomSheetDialog(
            this@ActivityTask1
        )
        val binding = LayoutListItemEditBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.editTextName.setText(user.name)
        binding.editTextMobile.setText(user.mobile)
        binding.editTextEmail.setText(user.email)
        binding.editTextGender.setText(user.gender)

        binding.textNext.setOnClickListener {
            if (binding.editTextName.text.toString().trim().isEmpty()) {
                AppUtils.toastNormal(this@ActivityTask1, "Enter Name")
                return@setOnClickListener
            }
            if (binding.editTextMobile.text.toString().trim().isEmpty()) {
                AppUtils.toastNormal(this@ActivityTask1, "Enter Mobile")
                return@setOnClickListener
            }
            if (binding.editTextEmail.text.toString().trim().isEmpty()) {
                AppUtils.toastNormal(this@ActivityTask1, "Enter Email")
                return@setOnClickListener
            }
            if (binding.editTextGender.text.toString().trim().isEmpty()) {
                AppUtils.toastNormal(this@ActivityTask1, "Enter Gender")
                return@setOnClickListener
            }
            dialog.dismiss()

            val data = User(
                user._id,
                binding.editTextName.text.toString(),
                binding.editTextMobile.text.toString(),
                binding.editTextEmail.text.toString(),
                binding.editTextGender.text.toString()
            )
            viewModel.updateUser(data)
        }

        dialog.show()
    }

}