package com.example.cameraapp

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


class ImageUploadViewModel : ViewModel() {

    val imageUri = MutableLiveData<Uri?>()
    val uploadStatus = MutableLiveData<Boolean>()

    fun uploadImageCall(file : File) {
        val reqFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("upload", file.name, reqFile)
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.dummyapi.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val api: ApiService = retrofit.create(ApiService::class.java)

        val call: Call<String> = api.uploadFile(body)
        call.enqueue(object : Callback<String?>{
            override fun onResponse(call: Call<String?>?, response: Response<String?>) {
                uploadStatus.postValue(true)
            }

            override fun onFailure(call: Call<String?>?, t: Throwable) {
                uploadStatus.postValue(false)
            }
        })
    }
}