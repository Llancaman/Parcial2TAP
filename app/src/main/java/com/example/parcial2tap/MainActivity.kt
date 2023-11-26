package com.example.parcial2tap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: com.example.parcial2tap.Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv_fruits)
        adapter = Adapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        adapter.onItemClickListener = {fruta ->
            val intent = Intent (this, FrutaActivity :: class.java )
            intent.putExtra("fruta", fruta)
            startActivity(intent)
        }

        getFruits()
    }

    private fun getFruits() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getFruits()
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    val fruits = response
                    fruits?.let {

                        val imageUrls = listOf(
                            "https://th.bing.com/th/id/R.a3b43cff65d195c6e28e6753ff8dcc48?rik=phkGnbDVvB65lQ&pid=ImgRaw&r=0",
                            "https://th.bing.com/th/id/R.2781f04dc590b362b384d89fc5255734?rik=c5MvDyJ9dyusgw&pid=ImgRaw&r=0",
                            "https://th.bing.com/th/id/R.70a392b7b0086163e2560be567803576?rik=RXDKsfpzX6D9zw&pid=ImgRaw&r=0",
                            "https://th.bing.com/th/id/OIP.kNiJM15wvcqnBUY6vPYRqQHaE7?rs=1&pid=ImgDetMain",
                            "https://th.bing.com/th/id/OIP.PctURXjsyimKBOAG7mOOtAHaFj?rs=1&pid=ImgDetMain",
                            "https://th.bing.com/th/id/R.14bd2cd4f61329e6d8ba71d3eb3a89cd?rik=nZOTe1Uom0cQdw&pid=ImgRaw&r=0",
                            "https://th.bing.com/th/id/OIP.D9NN--Adl3Pxu21DvmS3aAHaFn?rs=1&pid=ImgDetMain",


                            )
                        for ((index, fruit) in it.withIndex()) {
                            if (index < imageUrls.size) {
                                fruit?.imageUrl = imageUrls[index]
                            } else {
                                fruit?.imageUrl = "https://media.metrolatam.com/2018/02/20/lasfrutasmassanas660x495-1200x800.jpg"
                            }
                        }
                        adapter.setFruits(it)
                    }
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