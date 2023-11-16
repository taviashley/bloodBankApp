package com.example.bloodbank.home


data class HomeModel(
     val donorName: String,
     val bloodType: String,
     val address: String,
     val contact: String,
     val availability:String
):java.io.Serializable