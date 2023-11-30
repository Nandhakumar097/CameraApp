package com.example.cameraapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.provider.OpenableColumns
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CustomEditTextWithButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val actionButton: Button
    private var imageView: ImageView
    private lateinit var preview: Button
    private lateinit var fileName : TextView
    private var onButtonClickListener: (() -> Unit)? = null
    private var onPreviewClickListener: (() -> Unit)? = null
    private var onSubmitClickListener: (() -> Unit)? = null
    private var imagePreview = false
    private var dialog: AlertDialog? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_edit_text_with_button, this, true)
        actionButton = findViewById(R.id.actionButton)
        imageView = findViewById(R.id.imageview)
        preview = findViewById(R.id.preview)
        fileName = findViewById(R.id.filename)
        actionButton.setOnClickListener {
            onButtonClickListener?.invoke()
        }
        preview.setOnClickListener {
            if (imagePreview) {
                imagePreview = false
                imageView.setImageDrawable(resources.getDrawable(R.drawable.no_image_24))
                preview.isEnabled = false
                fileName.text = ""
                preview.text = "Preview"
                showDialog(context,"Image Uploading...")
                GlobalScope.launch() {
                    delay(2000)
                    dismissDialog()
                    onSubmitClickListener?.invoke()
                }
            }else{
                onPreviewClickListener?.invoke()
                preview.text = "Submit"
                imagePreview = true
            }
        }
    }

    fun dismissDialog() {
        if (dialog != null) {
            dialog!!.dismiss()
            dialog = null
        }
    }

    fun setImage(image: Uri) {
        imageView.setImageURI(image)
    }

    fun setImageName(name : Uri){
        fileName.text = "File Selected : ${getFileName(name)}"
        preview.isEnabled = true
    }

    fun setOnButtonClickListener(listener: () -> Unit) {
        onButtonClickListener = listener
    }

    fun setOnPreviewClickListener(listener: () -> Unit) {
        onPreviewClickListener = listener
    }

    fun setOnSubmitClickListener(listener: () -> Unit){
        onSubmitClickListener = listener
    }

    @SuppressLint("Range")
    fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor = context.getContentResolver().query(uri, null, null, null, null) as Cursor
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }

    fun showDialog(context: Context?, message: String?) {
        if (dialog == null) {
            val llPadding = 30
            val ll = LinearLayout(context)
            ll.orientation = HORIZONTAL
            ll.setPadding(llPadding, llPadding, llPadding, llPadding)
            ll.gravity = Gravity.CENTER
            var llParam = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
            llParam.gravity = Gravity.CENTER
            ll.layoutParams = llParam
            val progressBar = ProgressBar(context)
            progressBar.isIndeterminate = true
            progressBar.setPadding(0, 0, llPadding, 0)
            progressBar.layoutParams = llParam
            llParam = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            llParam.gravity = Gravity.CENTER
            val tvText = TextView(context)
            tvText.text = message
            tvText.setTextColor(Color.parseColor("#000000"))
            tvText.textSize = 20f
            tvText.layoutParams = llParam
            ll.addView(progressBar)
            ll.addView(tvText)
            val builder = AlertDialog.Builder(context)
            builder.setCancelable(true)
            builder.setView(ll)
            dialog = builder.create()
            dialog?.show()
            val window = dialog?.getWindow()
            if (window != null) {
                val layoutParams = WindowManager.LayoutParams()
                layoutParams.copyFrom(dialog?.getWindow()!!.attributes)
                layoutParams.width = LayoutParams.WRAP_CONTENT
                layoutParams.height = LayoutParams.WRAP_CONTENT
                dialog?.getWindow()!!.attributes = layoutParams
            }
        }
    }

}