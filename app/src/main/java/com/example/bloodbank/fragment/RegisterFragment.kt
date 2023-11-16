package com.example.bloodbank.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.bloodbank.MainActivity
import com.example.bloodbank.R
import com.example.bloodbank.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var mainActivity: MainActivity

    private lateinit var binding: FragmentRegisterBinding

    private var isEmailAddressValid = false
    private var isPassWordValid = false
    private var isConfirmPasswordValid = false
    private var termsAndConditionAccepted = false
    private var passwordMatch = false
    private var password: String = ""
    private var confirmPassword: String = ""
    private var isShowPassword = false
    private var passwordPattern =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TextWatcher
        addTextWatcherEmail()
        addTextWatcherPassword()
        addTextWatcherConfirmPassword()
        passwordRequirements()

        maskPassword()
        maskConfirmPassword()

        acceptTermsAndCondition()
        performSignUp()
        gotoLoginScreen()
        onBackPressed()
    }

    private fun addTextWatcherEmail() {
        binding.editTextEmailId.addTextChangedListener(object : TextWatcher {
            private fun setErrorMsg() {
                binding.editTextEmailId.error = "Invalid email address"
                disableButtonClick()
                isEmailAddressValid = false
            }

            override fun beforeTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
                /**
                 * NA
                 */
            }

            override fun onTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (value.isValidEmail()) {
                    isEmailAddressValid = true

                    if (isPassWordValid && isConfirmPasswordValid && termsAndConditionAccepted && passwordMatch) {
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
        binding.editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
                /**
                 * NA
                 */
            }

            override fun onTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
                password = value.toString()

                if (value?.matches(passwordPattern.toRegex()) == false) {
                    disableButtonClick()
                    isPassWordValid = false
                    return
                } else {
                    isPassWordValid = true

                    if (isEmailAddressValid && isConfirmPasswordValid && termsAndConditionAccepted && passwordMatch)
                        enableButtonClick()
                }

                if (confirmPassword.isNotEmpty() && value.toString() != confirmPassword) {
                    binding.editTextPassword.error = "Password does not match"
                    disableButtonClick()
                    passwordMatch = false
                } else {
                    passwordMatch = true

                    if (isEmailAddressValid && isPassWordValid && termsAndConditionAccepted && isConfirmPasswordValid)
                        enableButtonClick()
                }

            }

            override fun afterTextChanged(value: Editable?) {
                /**
                 * NA
                 */
            }
        })
    }

    private fun addTextWatcherConfirmPassword() {
        binding.editTextPasswordConfirm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
                /**
                 * NA
                 */
            }

            override fun onTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
                confirmPassword = value.toString()

                if (value?.matches(passwordPattern.toRegex()) == false) {
                    disableButtonClick()
                    isConfirmPasswordValid = false
                    return
                } else {
                    isConfirmPasswordValid = true
                    binding.editTextPasswordConfirm.error = null

                    if (isEmailAddressValid && isPassWordValid && termsAndConditionAccepted && passwordMatch)
                        enableButtonClick()
                }

                if (password.isNotEmpty() && value.toString() != password) {
                    disableButtonClick()
                    passwordMatch = false
                } else {
                    passwordMatch = true
                    if (isEmailAddressValid && isPassWordValid && termsAndConditionAccepted && isConfirmPasswordValid)
                        enableButtonClick()
                }
            }

            override fun afterTextChanged(value: Editable?) {
                /**
                 * NA
                 */
            }
        })
    }

    private fun passwordRequirements() {
        val passwordReq = "1. Password should be a minimum of 6 characters" +
                "\n2. Password should contain 1 alpha character" +
                "\n3. Password should contain 1 numeric character" +
                "\n4. Password should contain 1 special character" +
                "\n5. Password should contain 1 lower case character" +
                "\n6. Password should contain 1 upper case character"

        binding.imageViewPasswordRequirement.setOnClickListener {
            val alertDialog = AlertDialog.Builder(mainActivity).create()
            alertDialog.setTitle(R.string.password_requirement)
            alertDialog.setMessage(passwordReq)
            alertDialog.setButton(
                AlertDialog.BUTTON_NEGATIVE, "OK"
            ) { dialog, _ -> dialog.dismiss() }
            alertDialog.show()
        }
    }

    private fun showPassword(isShow: Boolean) {
        if (isShow) {
            binding.editTextPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.showPassword.setImageResource(R.drawable.ic_baseline_visibility_24)
        } else {
            binding.editTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.showPassword.setImageResource(R.drawable.ic_baseline_visibility_off_24)
        }
        binding.editTextPassword.setSelection(binding.editTextPassword.text.toString().length)
    }

    private fun maskPassword() {
        binding.showPassword.setOnClickListener {
            isShowPassword = !isShowPassword
            showPassword(isShowPassword)
        }
    }

    private fun showConfirmPassword(isShow: Boolean) {
        if (isShow) {
            binding.editTextPasswordConfirm.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
            binding.showConfirmPassword.setImageResource(R.drawable.ic_baseline_visibility_24)
        } else {
            binding.editTextPasswordConfirm.transformationMethod =
                PasswordTransformationMethod.getInstance()
            binding.showConfirmPassword.setImageResource(R.drawable.ic_baseline_visibility_off_24)
        }
        binding.editTextPasswordConfirm.setSelection(binding.editTextPasswordConfirm.text.toString().length)
    }

    private fun maskConfirmPassword() {
        binding.showConfirmPassword.setOnClickListener {
            isShowPassword = !isShowPassword
            showConfirmPassword(isShowPassword)
        }
    }

    private fun acceptTermsAndCondition() {
        binding.checkBoxTermsAndConditions.setOnCheckedChangeListener { _, isChecked ->
            hideShowKeyboard()

            if (isChecked) {
                termsAndConditionAccepted = true
                if (isPassWordValid && isEmailAddressValid && isConfirmPasswordValid && passwordMatch)
                    enableButtonClick()
            } else {
                disableButtonClick()
                termsAndConditionAccepted = false
            }
        }
    }

    private fun performSignUp() {
        binding.btnRegister.setOnClickListener {
            hideShowKeyboard()

            if (binding.editTextPassword.text?.isEmpty() == true || binding.editTextEmailId.text?.isEmpty() == true) {
                Toast.makeText(
                    mainActivity,R.string.enter_email_password,
                    Toast.LENGTH_SHORT
                )
                    .show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(mainActivity,R.string.unmatch_password, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



        }
    }

    private fun gotoLoginScreen() {
        binding.textViewLogin.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_RegisterFragment_to_LoginFragment)
        }
    }

    fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun disableButtonClick() {
        binding.btnRegister.isEnabled = false
        binding.btnRegister.background =
            ContextCompat.getDrawable(mainActivity, R.drawable.round_disable_button)
    }

    private fun enableButtonClick() {
        binding.btnRegister.isEnabled = true
        binding.btnRegister.background =
            ContextCompat.getDrawable(mainActivity, R.drawable.button_background)
    }

    private fun onBackPressed() {
        binding.toolBar.setOnClickListener() {
            view?.findNavController()?.navigate(R.id.action_RegisterFragment_to_LoginFragment)
        }
    }

    private fun hideShowKeyboard() {
        val inputManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputManager?.hideSoftInputFromWindow(view?.windowToken, InputMethodManager.SHOW_FORCED)
    }
}