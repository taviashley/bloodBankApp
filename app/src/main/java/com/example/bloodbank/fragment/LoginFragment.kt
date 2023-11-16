package com.example.bloodbank.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.bloodbank.MainActivity
import com.example.bloodbank.R
import com.example.bloodbank.databinding.FragmentLoginBinding

class LoginFragment : androidx.fragment.app.Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private lateinit var mainActivity: MainActivity
    private var isPassWordValid = false
    private var isEmailAddressValid = false
    private var isShowPassword = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainActivity = activity as MainActivity
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TextWatcher
        addTextWatcherEmail()
        addTextWatcherPassword()
        maskPassword()

        gotoRegisterScreen()
        performLogin()
    }

    private fun addTextWatcherEmail() {
        binding.emailIdEditText.addTextChangedListener(object : TextWatcher {
            private fun setErrorMsg() {
                binding.emailIdEditText.error = "Invalid email address"
                disableButtonClick()
                isEmailAddressValid = false
            }

            override fun beforeTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Not require
            }

            override fun onTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (value.isValidEmail()){
                    isEmailAddressValid = true

                    if (isEmailAddressValid && isPassWordValid) {
                        enableButtonClick()
                    }
                } else {
                    setErrorMsg()
                }
            }

            override fun afterTextChanged(value: Editable?) {
                /**
                 * NA
                 */
            }
        })
    }

    private fun addTextWatcherPassword() {
        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
                /**
                 * NA
                 */
            }

            override fun onTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (value.isNullOrEmpty()) return

                if (value.length < 6) {
                    binding.passwordEditText.error = "Minimum password length is 6"
                    disableButtonClick()
                    isPassWordValid = false
                } else {
                    isPassWordValid = true

                    if (isEmailAddressValid && isPassWordValid)
                        enableButtonClick()
                }
            }

            override fun afterTextChanged(value: Editable?) {
            }
        })
    }

    fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun gotoRegisterScreen() {
        binding.registerTextView.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_LoginFragment_to_RegisterFragment)
        }
    }

    private fun disableButtonClick() {
        binding.btnLogin.isEnabled = false
        binding.btnLogin.background = ContextCompat.getDrawable(mainActivity, R.drawable.round_disable_button)
    }

    private fun enableButtonClick() {
        binding.btnLogin.isEnabled = true
        binding.btnLogin.background = ContextCompat.getDrawable(mainActivity, R.drawable.button_background)
    }

    private fun showPassword(isShow: Boolean) {
        if (isShow) {
            binding.passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.showPassword.setImageResource(R.drawable.ic_baseline_visibility_24)

        } else {
            binding.passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.showPassword.setImageResource(R.drawable.ic_baseline_visibility_off_24)
        }
        binding.passwordEditText.setSelection(binding.passwordEditText.text.toString().length)
    }


    private fun maskPassword() {
        binding.showPassword.setOnClickListener {
            isShowPassword = !isShowPassword
            showPassword(isShowPassword)
        }
    }

    private fun performLogin() {
        binding.btnLogin.setOnClickListener {

           if (binding.passwordEditText.text!!.isEmpty() || binding.emailIdEditText.text?.isEmpty() == true) {
               Toast.makeText(mainActivity, R.string.enter_email_password, Toast.LENGTH_SHORT)
                   .show()
               return@setOnClickListener
           }
           else {
               view?.findNavController()?.navigate(R.id.action_LoginFragment_to_dashboardActivity)
           }
       }
    }
}