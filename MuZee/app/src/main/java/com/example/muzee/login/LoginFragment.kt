package com.example.muzee.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.muzee.R
import com.example.muzee.databinding.LoginFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoginFragment : Fragment() {

    private val viewModel:LoginViewModel by lazy{
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }
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
            viewModel.response.observe(viewLifecycleOwner,{ login_response ->
                if(login_response == null){
                    Toast.makeText(
                        requireContext(),"Unsuccessful network call",Toast.LENGTH_SHORT
                    ).show()
                    return@observe
                }
                val ID = login_response.ID
                when(login_response.ack){
                    "account_not_exist"->{
                        //pop up an alert dialog to notify incorrect username
                        val incorrectUsrAlertDialog = MaterialAlertDialogBuilder(requireContext())
                            .setTitle(getString(R.string.incorrect_username_title))
                            .setMessage(getString(R.string.incorrect_username_message))
                            .setNeutralButton(getString(R.string.btn_ok)) { dialog, which ->
                            }
                        incorrectUsrAlertDialog.show()
                    }
                    "account_not_valid"->{
                        //pop up an alert dialog to notify incorrect password
                        val incorrectPwdAlertDialog = MaterialAlertDialogBuilder(requireContext())
                            .setTitle(getString(R.string.incorrect_password_title))
                            .setMessage(getString(R.string.incorrect_password_message))
                            .setNeutralButton(getString(R.string.btn_ok)) { dialog, which ->
                            }
                        incorrectPwdAlertDialog.show()
                    }
                    "seller_account_valid"->{

                        findNavController().navigate(R.id.action_loginFragment_to_mainScreenSellerFragment)
                    }
                    "normal_user_account_valid"->{
                        findNavController().navigate(R.id.action_loginFragment_to_mainScreenNormalUsersFragment)
                    }
                }

            })
        }
    }

}