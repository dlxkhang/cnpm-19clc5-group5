package com.example.muzee.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.muzee.R
import com.example.muzee.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private var binding:LoginFragmentBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        var fragmentbinding = LoginFragmentBinding.inflate(inflater,container,false)
        binding = fragmentbinding
        return fragmentbinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
        binding?.labelSignUp?.setOnClickListener{
            gotoSignUpFragment()
        }
        binding?.btnSignIn?.setOnClickListener{
            handleSignInBtn()
        }
    }
    private fun gotoSignUpFragment(){
        findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
    }
    private fun handleSignInBtn(){
        val username_input_layout = binding?.labelUsername
        val password_input_layout = binding?.labelPassword
        var notNull = true
        if(username_input_layout!!.editText!!.text!!.isEmpty()){
            notNull = false
            username_input_layout.error = getString(R.string.error_enter_username)
        }
        if(password_input_layout!!.editText!!.text!!.isEmpty()){
            notNull = false
            password_input_layout.error = getString(R.string.error_enter_password)
        }
        if(notNull){
            val username = username_input_layout.editText?.text.toString()
            val password = password_input_layout.editText?.text.toString()
            viewModel.checkLogin(username,password)
        }
    }

}