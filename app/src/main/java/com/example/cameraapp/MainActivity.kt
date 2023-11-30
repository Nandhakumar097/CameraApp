package com.example.cameraapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cameraapp.databinding.ActivityMainBinding
import id.zelory.compressor.Compressor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val REQUEST_IMAGE_CAPTURE = 200
    lateinit var imageUploadViewModel: ImageUploadViewModel

    @SuppressLint("IntentReset")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageUploadViewModel = ViewModelProvider(this)[ImageUploadViewModel::class.java]

        binding.customEditTextWithButton.setOnButtonClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*";
            intent.action = Intent.ACTION_GET_CONTENT;
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                REQUEST_IMAGE_CAPTURE
            );

        }

        binding.customEditTextWithButton.setOnPreviewClickListener {
            imageUploadViewModel.imageUri.value?.let { binding.customEditTextWithButton.setImage(it) }
        }

        binding.customEditTextWithButton.setOnSubmitClickListener {
            GlobalScope.launch {
                imageUploadViewModel.imageUri.value?.let { submitImage(it) }
            }
        }

        imageUploadViewModel.uploadStatus.observe(this) {
            Toast.makeText(applicationContext, "Image Uploaded Successfully", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            if (null != selectedImageUri) {
                imageUploadViewModel.imageUri.value = selectedImageUri
                binding.customEditTextWithButton.setImageName(imageUploadViewModel.imageUri.value!!)
            }
        }
    }

    private suspend fun submitImage(imageData: Uri) {
        val file = imageData.path?.let { File(it) }
        try {
            val compressedImageFile: File? =
                file?.let { Compressor.compress(applicationContext, it) }
            if (compressedImageFile != null) {
                imageUploadViewModel.uploadImageCall(compressedImageFile)
            }
        } catch (e: Exception) {
            file?.let { imageUploadViewModel.uploadImageCall(it) }
        }

    }


}
