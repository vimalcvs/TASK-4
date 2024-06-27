package com.vimal.healthy.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.vimal.healthy.R
import com.vimal.healthy.profilemodel.User
import com.vimal.healthy.util.InternalStoragePhoto
import com.vimal.healthy.viewmodel.DataStoreViewModel
import com.vimal.healthy.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_profile.clearProfile
import kotlinx.android.synthetic.main.activity_profile.profile_back_button
import kotlinx.android.synthetic.main.activity_profile.rgUnits
import kotlinx.android.synthetic.main.activity_profile.saveProfile
import kotlinx.android.synthetic.main.activity_profile.tvProfileUserName
import kotlinx.android.synthetic.main.activity_profile.userProfileImage
import kotlinx.android.synthetic.main.layout_fitness.layoutFitness
import kotlinx.android.synthetic.main.layout_general.et_email
import kotlinx.android.synthetic.main.layout_general.et_phone
import kotlinx.android.synthetic.main.layout_general.et_userName
import kotlinx.android.synthetic.main.layout_general.layout_general
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException


@AndroidEntryPoint
class ProfileActivity : AppCompatActivity(), View.OnClickListener {
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private var filepath: Uri? = null
    private lateinit var bitmap: Bitmap

    private val takePhoto =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            filepath = result!!
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source =
                    ImageDecoder.createSource(applicationContext.contentResolver!!, filepath!!)
                bitmap = ImageDecoder.decodeBitmap(source)
            }
            saveImageToInternalStorage("profile", bitmap)
            Glide.with(applicationContext).load(filepath).circleCrop().into(userProfileImage)
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        profile_back_button.setOnClickListener {
            onBackPressed()
        }


        rgUnits.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.btnGeneral -> {
                    layout_general.visibility = View.VISIBLE
                    layoutFitness.visibility = View.GONE
                }

                R.id.btnFitness -> {
                    layout_general.visibility = View.GONE
                    layoutFitness.visibility = View.VISIBLE
                }
            }
        })

        getUserDetails()
        checkIfUserHasSavedDetails()
        deleteUserRecord()

    }

    override fun onClick(view: View?) {
        when (view!!.id) {
        }
    }


    private fun checkIfUserHasSavedDetails() {
        dataStoreViewModel.savedKey.observe(this) {
            if (it != true) {
                saveProfile.visibility = View.VISIBLE
                clearProfile.visibility = View.GONE
                et_userName.isFocusable = true
                et_email.isFocusable = true
                et_phone.isFocusable = true

                userProfileImage.setOnClickListener {
                    takePhoto.launch("image/*")
                }
                saveUserData()

            } else if (it == true) {
                saveProfile.visibility = View.GONE
                clearProfile.visibility = View.VISIBLE
                et_userName.isFocusable = false
                et_email.isFocusable = false
                et_phone.isFocusable = false

            }
        }
    }

    private fun saveUserData() {
        saveProfile.setOnClickListener {
            when {
                et_userName.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show()
                }

                et_phone.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show()
                }

                et_email.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                }

                filepath == null -> {
                    Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    //get entered details
                    val userName = et_userName.text.toString()
                    val phone = et_phone.text.toString()
                    val email = et_email.text.toString()
                    val profileImageFilePath = filepath.toString()

                    val user = User(
                        id = 1,
                        name = userName,
                        phone = phone,
                        email = email,
                        profileImageFilePath = profileImageFilePath
                    )

                    // save the details to room database
                    userViewModel.insertUserDetails(user)

                    userViewModel.response.observe(this) {
                        Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                        // success, save the key to check if user has saved details or not
                        dataStoreViewModel.setSavedKey(true)

                        et_userName.text!!.clear()
                        et_phone.text!!.clear()
                        et_email.text!!.clear()

                        //show toast message
                        Toast.makeText(this, "Record Saved", Toast.LENGTH_LONG).show()
                        userProfileImage.setOnClickListener(null)

                    }
                }
            }
        }
    }

    private fun getUserDetails() {
        this.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                userViewModel.doGetUserDetails()
                userViewModel.userDetails.collect { users ->
                    val listOfImage = loadImageFromInternalStorage()
                    for (user in users) {
                        et_userName.setText(user.name)
                        et_phone.setText(user.phone)
                        et_email.setText(user.email)
                        tvProfileUserName.text = user.name

                        for (i in listOfImage) {
                            if (i.name.contains("profile")) {
                                Glide.with(applicationContext).load(i.bitmap).circleCrop()
                                    .into(userProfileImage)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun deleteUserRecord() {
        clearProfile.setOnClickListener {
            //clear record from room database
            userViewModel.doDeleteSingleUserRecord()
            dataStoreViewModel.setSavedKey(false)
            et_userName.text = null
            et_email.text = null
            et_phone.text = null
            tvProfileUserName.text = null
            userProfileImage.setImageBitmap(null)
            userProfileImage.setImageDrawable(null)
            startActivity(Intent(this@ProfileActivity, ProfileActivity::class.java))
            finish()
            clearProfileImage()

        }

    }


    private fun saveImageToInternalStorage(fileName: String, bitmap: Bitmap): Boolean {
        return try {
            applicationContext.openFileOutput("$fileName.jpg", MODE_PRIVATE).use { outputStream ->
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 95, outputStream)) {
                    throw IOException("Could not save Bitmap")
                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }

    }

    private suspend fun loadImageFromInternalStorage(): List<InternalStoragePhoto> {
        return withContext(Dispatchers.IO) {
            val files = applicationContext.filesDir.listFiles()
            files!!.filter {
                it.canRead() && it.isFile && it.name.endsWith(".jpg")
            }.map {
                val bytes = it.readBytes()
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalStoragePhoto(it.name, bitmap)
            }
        }
    }

    private fun clearProfileImage() {
        val files: Array<File> = applicationContext.filesDir.listFiles()
        if (files != null) for (file in files) {
            file.delete()
        }
    }

}
