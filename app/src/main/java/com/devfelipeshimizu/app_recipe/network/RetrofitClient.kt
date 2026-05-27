package com.devfelipeshimizu.app_recipe.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Para testar no emulador use: "http://10.0.2.2:3000/"
    // Para produção use a URL do Render
    private const val BASE_URL = "https://api-recipe-fulh.onrender.com/"

    val api: RecipeApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipeApiService::class.java)
    }
}