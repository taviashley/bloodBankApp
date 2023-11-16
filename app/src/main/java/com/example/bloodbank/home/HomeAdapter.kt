package com.example.bloodbank.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodbank.R

class HomeAdapter(private val homelist: ArrayList<HomeModel>) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    // This method creates a new ViewHolder object for each item in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Inflate the layout for each item and return a new ViewHolder object
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_donors_list, parent, false)
        return MyViewHolder(itemView)
    }

    // This method returns the total
    // number of items in the data set
    override fun getItemCount(): Int {
        return homelist.size
    }

    // This method binds the data to the ViewHolder object
    // for each item in the RecyclerView
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentHome = homelist[position]
        holder.donorName.text = currentHome.donorName
        holder.bloodType.text = currentHome.bloodType
        holder.address.text = currentHome.address
        holder.contact.text = currentHome.contact
        holder.availability.text = currentHome.availability

    }

    // This class defines the ViewHolder object for each item in the RecyclerView
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val donorName: TextView = itemView.findViewById(R.id.tvName)
        val bloodType: TextView = itemView.findViewById(R.id.tvBloodType)
        val address: TextView = itemView.findViewById(R.id.tvLocation)
        val contact: TextView = itemView.findViewById(R.id.tvContactDetails)
        val availability: TextView = itemView.findViewById(R.id.tvAvailability)
    }
}