package com.anushmp.autocompleteplaces

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anushmp.autocompleteplaces.datamodels.AddressList
import com.anushmp.autocompleteplaces.datamodels.Json4Kotlin_Base
import com.anushmp.autocompleteplaces.datamodels.remote.AddressApi
import com.anushmp.autocompleteplaces.datamodels.remote.Networker
import org.jetbrains.anko.find
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class AutoCompleteAirtelActivity : AppCompatActivity() {

    lateinit var addressSearch:EditText
    lateinit var addressDisplay:TextView
    lateinit var searchResultsRV:RecyclerView
    lateinit var api:AddressApi
    lateinit var retrofit:Retrofit

    var listofResults = ArrayList<AddressList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_complete_airtel)

        addressSearch = findViewById(R.id.addressSearch)
        addressDisplay = findViewById(R.id.addressDisplay)
        searchResultsRV = findViewById(R.id.searchResultsRV)

        retrofit = Networker.getRetrofit()
        api = retrofit.create(AddressApi::class.java)

//        api.getResults("a").enqueue(object :Callback<Json4Kotlin_Base>{
//            override fun onResponse(
//                call: Call<Json4Kotlin_Base>,
//                response: Response<Json4Kotlin_Base>
//            ) {
//                response.body()?.data?.addressList?.get(0)?.let { Log.d("AnushLogs", it.addressString) }
//            }
//
//            override fun onFailure(call: Call<Json4Kotlin_Base>, t: Throwable) {
//                Log.d("AnushLogs",t.message.toString())
//
//            }
//
//        })

        //recyclerView default visibility
        val adapter = SearchResultsAdapter(this,listofResults)
        val llm = LinearLayoutManager(this)
        searchResultsRV.adapter = adapter
        searchResultsRV.layoutManager = llm
        searchResultsRV.visibility = View.GONE

        addressDisplay.visibility = View.GONE

        addressSearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               //Log.d("AnushLogsBefore",p0.toString())
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Log.d("AnushLogsTextChanged",p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
               Log.d("AnushLogsAfterTextChanged",p0.toString())

                if(searchResultsRV.visibility == View.GONE){
                    searchResultsRV.visibility = View.VISIBLE
                }

                if(p0.toString() == " " || p0.toString() == ""){
                    searchResultsRV.visibility = View.GONE
                }

                api.getResults(p0.toString()).enqueue(object:Callback<Json4Kotlin_Base>{
                    override fun onResponse(
                        call: Call<Json4Kotlin_Base>,
                        response: Response<Json4Kotlin_Base>
                    ) {
                        listofResults.clear()
                        response.body()?.data?.addressList?.let { listofResults.addAll(it) }
                        searchResultsRV.adapter?.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<Json4Kotlin_Base>, t: Throwable) {
                        Log.d("AnushLogs",call.request().url.toString())

                    }

                })

                addressDisplay.append(p0.toString())
//                Toast.makeText(this@AutoCompleteAirtelActivity,
//                    "yes",
//                    Toast.LENGTH_LONG).show()

            }

        })

    }
}