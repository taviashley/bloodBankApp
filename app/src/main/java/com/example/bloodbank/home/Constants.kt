package com.example.bloodbank.home

object Constants {
    // Arraylist and return the Arraylist
    fun getHomeData():ArrayList<HomeModel>{
        // create an arraylist of type employee class
        val homeList=ArrayList<HomeModel>()
        val donor1=HomeModel("Tavisha Saulick", "A+", "Novelle France", "5881961", "Immediately")
        homeList.add(donor1)
        val donor2=HomeModel("TavishaSaulick", "0+", "Nonce", "5861", "11")
        homeList.add(donor2)
        val donor3=HomeModel("Tavishaaulick", "1+", "Nouvance", "583861", "455")
        homeList.add(donor3)
        val donor4=HomeModel("Tavishalick", "4+", "Noue France", "583861", "huh")
        homeList.add(donor4)
        val donor5=HomeModel("Tavishack", "47+", "Nouvelle France", "583961", "jjj")
        homeList.add(donor5)
        val donor6=HomeModel("Tavishk", "A7", "Nouve France", "581", "Immediiiiately")
        homeList.add(donor6)



        return  homeList
    }
}