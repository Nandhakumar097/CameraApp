package com.example.cameraapp

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri

class ImageUploadComponent(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

//    private val selectImageButton: Button
//    private val previewButton: Button
//    private val imageView: ImageView
//    private val fileInfoTextView: TextView
//    private val submitButton: Button

    private var selectedImageUri: String? = null

//    init {
//        orientation = VERTICAL
//        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        inflater.inflate(R.layout.image_upload_view, this, true)
//
//        selectImageButton = findViewById(R.id.selectImageButton)
//        previewButton = findViewById(R.id.previewButton)
//        imageView = findViewById(R.id.imageView)
//        fileInfoTextView = findViewById(R.id.fileInfoTextView)
//        submitButton = findViewById(R.id.submitButton)
//
//        selectImageButton.setOnClickListener {
//            // Launch image picker
////            pickImage.launch("image/*")
//        }
//
//        previewButton.setOnClickListener {
//            // Implement preview logic (show a dialog or enlarge the image)
//            // For simplicity, it's left as a placeholder
//            showPreview()
//        }
//
//        submitButton.setOnClickListener {
//            // Implement submission logic (upload to server or process)
//            submitImage()
//        }
//    }

//    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//        // Handle the picked image URI
//        selectedImageUri = uri.toString()
//
//        // Update UI components
//        updatePreview()
//        updateFileInfo()
//        enableSubmitButton()
//    }

//    private fun updatePreview() {
//        imageView.setImageURI(selectedImageUri?.toUri())
//    }
//
//    private fun updateFileInfo() {
//        val fileInfo = getFileInfo(selectedImageUri)
//        fileInfoTextView.text = fileInfo
//    }
//
//    private fun enableSubmitButton() {
//        submitButton.isEnabled = true
//        submitButton.backgroundTintList = ContextCompat.getColorStateList(context, R.color.black)
//    }

    private fun showPreview() {
        // Placeholder for preview logic
        // Implement preview functionality such as displaying the image in a dialog
        // or navigating to a new activity for a detailed view
    }

    private fun submitImage() {
        // Placeholder for submission logic
        // Implement the logic to submit the selected image (e.g., upload to server)
        // You can use the selectedImageUri for the file path
    }

    private fun getFileInfo(uri: String?): String {
        // Placeholder for file info retrieval logic
        // Implement the logic to get file information such as name and type
        // You can use ContentResolver to get the file type, and parse the file name from the URI
        return "Image File"
    }
}
