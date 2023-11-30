package com.example.cameraapp

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface ApiService {
    @Multipart
    @POST("upload")
    fun uploadFile(
        @Part file: MultipartBody.Part?
    ) : retrofit2.Call<String>
}