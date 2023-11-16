package com.example.bloodbank.newDonor

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.bloodbank.databinding.FragmentNewDonorBinding


class NewDonorFragment: Fragment() {
    private lateinit var binding: FragmentNewDonorBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentNewDonorBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
    }
    private fun initialize() {

        val nameEditText = binding.edittextFullName as EditText
        val fullName = nameEditText.text.toString()

        val homeAddressET = binding.homeAddress as EditText
        val homeAddress = homeAddressET.text.toString()

        val contactNumber1ET = binding.contactNumber1 as EditText
        val contactNumber1 = contactNumber1ET.text.toString()

        val contactNumber2ET = binding.contactNumber2 as EditText
        val contactNumber2 = contactNumber2ET.text.toString()

        val bloodTypeET = binding.edittextBloodType as EditText
        val bloodType = bloodTypeET.text.toString()

        //initiate a check box
        val conditionsCheckBox = binding.conditions as CheckBox
//check current state of the check box
        val checkBoxState = conditionsCheckBox.isChecked
    }
}