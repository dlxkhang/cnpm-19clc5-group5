package com.example.muzee.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.muzee.R
import com.example.muzee.databinding.FragmentSignUpNormalUsersBinding
import com.example.muzee.signup.model.SignUpViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SignUp_NormalUsersFragment : Fragment() {
    private lateinit var viewModel: SignUpViewModel
    private var binding:FragmentSignUpNormalUsersBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val fragmentbinding =  FragmentSignUpNormalUsersBinding.inflate(inflater, container, false)
        binding = fragmentbinding
        viewModel = SignUpViewModel()
        handle_on_text_change()
        binding?.btnSignUp?.setOnClickListener{
            handle_sign_up_btn()
        }
        return fragmentbinding.root
    }
    private fun handle_on_text_change(){
        binding?.labelFullName?.editText?.doOnTextChanged { _, _, _, _ ->
            binding?.labelFullName?.error = null
        }
        binding?.labelPhoneNumber?.editText?.doOnTextChanged { _, _, _, _ ->
            binding?.labelPhoneNumber?.error = null
        }
        binding?.labelUsername?.editText?.doOnTextChanged { _, _, _, _ ->
            binding?.labelUsername?.error = null
        }
        binding?.labelEnterPassword?.editText?.doOnTextChanged { _, _, _, _ ->
            binding?.labelEnterPassword?.error = null
        }
        binding?.labelReEnterPassword?.editText?.doOnTextChanged { _, _, _, _ ->
            binding?.labelReEnterPassword?.error = null
        }
    }
    fun handle_sign_up_btn(){
        val fullname_input = binding?.labelFullName
        val phonenumber_input = binding?.labelPhoneNumber
        val username_input = binding?.labelUsername
        val password_input = binding?.labelEnterPassword
        val password_re_input = binding?.labelReEnterPassword
        var notNull = true
        if(fullname_input!!.editText!!.text!!.isEmpty()){
            notNull = false
            fullname_input.error = getString(R.string.error_text_fullname)
        }
        else {
            fullname_input.error = null
        }
        if(phonenumber_input!!.editText!!.text!!.isEmpty()){
            notNull = false
            phonenumber_input.error = getString(R.string.error_text_phonenumber)
        }
        else{
            phonenumber_input.error = null
        }
        if(username_input!!.editText!!.text!!.isEmpty()){
            notNull = false
            username_input.error = getString(R.string.error_enter_username)
        }
        else{
            username_input.error = null
        }
        if(password_input!!.editText!!.text!!.isEmpty()){
            notNull = false
            password_input.error = getString(R.string.error_enter_password)
        }
        else{
            password_input.error = null
        }
        if(password_re_input!!.editText!!.text!!.isEmpty()){
            notNull = false
            password_re_input.error = getString(R.string.error_text_re_password)
        }
        else{
            password_re_input.error = null
        }
        if(notNull){
            if(!verify()){
               password_re_input.error = getString(R.string.error_text_not_same_pwd)
            }
            else{
                password_re_input.error = null

                val fullName = fullname_input.editText?.text.toString()
                val phoneNumber = phonenumber_input.editText?.text.toString()
                val username = username_input.editText?.text.toString()
                val password = password_input.editText?.text.toString()
                viewModel.addNormalUserAccount(fullName,username,phoneNumber,password)
                viewModel.reponse.observe(viewLifecycleOwner,{signup_response->
                    if(signup_response==null){
                        Toast.makeText(
                            requireContext(),"Unsuccessful network call", Toast.LENGTH_SHORT
                        ).show()
                        return@observe
                    }
                    when(signup_response.ack){
                        "account_exist"->{
                            val dialogBuilder = MaterialAlertDialogBuilder(requireContext())
                            dialogBuilder.setMessage("This account already registered in the system. Do you want to sign up again?")
                                .setTitle("Account Exists")
                                .setPositiveButton(getString(R.string.btn_ok)){dialog,which->
                                    dialog.cancel()
                                }
                            dialogBuilder.show()
                        }
                        "sign_up_success"->{
                            val dialogBuilder = MaterialAlertDialogBuilder(requireContext())
                            dialogBuilder
                                .setTitle("Sign up Successful")
                                .setPositiveButton(getString(R.string.btn_ok)){dialog,which->
                                    dialog.cancel()
                                    findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                                }
                            dialogBuilder.show()

                        }
                    }
                })
            }
        }
    }
    private fun verify():Boolean{
        return binding!!.labelEnterPassword.editText!!.text!!.toString().equals(binding!!.labelReEnterPassword.editText!!.text.toString())
    }
    companion object {
        fun newInstance() =
            SignUp_NormalUsersFragment()
    }
}