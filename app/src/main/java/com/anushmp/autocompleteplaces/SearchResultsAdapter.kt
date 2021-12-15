package com.anushmp.autocompleteplaces

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anushmp.autocompleteplaces.datamodels.AddressList

class SearchResultsAdapter(val context:Context,val listOfAddresses:ArrayList<AddressList>): RecyclerView.Adapter<SearchResultsAdapter.vh>() {

    inner class vh(view:View): RecyclerView.ViewHolder(view) {

        var addressView = view.findViewById<TextView>(R.id.addressFromApi)

        fun setData(address:String){
            addressView.text = address
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vh {
        val view:View = LayoutInflater.from(context).inflate(R.layout.result_item_view,parent,false)
        return vh(view)
    }

    override fun onBindViewHolder(holder: vh, position: Int) {
        holder.setData(listOfAddresses[position].addressString)
    }

    override fun getItemCount(): Int {
       return listOfAddresses.size
    }

}