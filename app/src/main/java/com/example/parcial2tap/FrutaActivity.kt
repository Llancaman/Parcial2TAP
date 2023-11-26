package com.example.parcial2tap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FrutaActivity : AppCompatActivity() {
    private lateinit var tv_name: TextView
    private lateinit var tv_calories: TextView
    private lateinit var tv_fat: TextView
    private lateinit var tv_sugar: TextView
    private lateinit var tv_carbohydrates: TextView
    private lateinit var tv_protein: TextView
    private lateinit var img_detail: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruta)

        tv_name = findViewById(R.id.textView_detalleName)
        tv_calories = findViewById(R.id.text_calorias)
        tv_fat = findViewById(R.id.text_fat)
        tv_sugar = findViewById(R.id.text_sugar)
        tv_carbohydrates = findViewById(R.id.text_carbo)
        tv_protein = findViewById(R.id.text_protein)
        img_detail= findViewById(R.id.imgview_detalle)

        val bundle = intent.extras
        val fruta = bundle?.getString("fruta")

        getFruitsDetalle(fruta)
    }

    private fun getFruitsDetalle(fruta: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService:: class.java)
                .getFruitsDetalle("fruit/$fruta")
            val response = call.body()
            runOnUiThread{
                if (call.isSuccessful){
                    response.let {fruta ->
                        tv_name.text = fruta?.name.toString()
                        tv_calories.text = "Calorias: " + fruta?.nutritions?.calories.toString()
                        tv_fat.text = "Grasa: " + fruta?.nutritions?.fat.toString()
                        tv_sugar.text = "Azucar: " + fruta?.nutritions?.sugar.toString()
                        tv_carbohydrates.text = "Carbohidratos: " + fruta?.nutritions?.carbohydrates.toString()
                        tv_protein.text = "Proteinas: " + fruta?.nutritions?.protein.toString()
                    }
                    Picasso.get()
                        .load("https://media.metrolatam.com/2018/02/20/lasfrutasmassanas660x495-1200x800.jpg")
                        .into(img_detail)
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object{
        const val BASE_URL = "https://fruityvice.com/api/"
    }
}