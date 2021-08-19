package com.example.muzee.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doBeforeTextChanged
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
    ): View {
        val fragmentbinding = LoginFragmentBinding.inflate(inflater,container,false)
        binding = fragmentbinding
        handle_before_text_change()
        val activity = activity as AppCompatActivity?
        activity!!.supportActionBar!!.hide()
        return fragmentbinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val activity = activity as AppCompatActivity?
        activity!!.supportActionBar!!.show()
    }

private fun handle_before_text_change(){

    binding?.labelUsername?.editText?.doBeforeTextChanged { _, _, _, _ ->
        binding?.labelUsername?.error = null
    }
    binding?.labelPassword?.editText?.doBeforeTextChanged { _, _, _, _ ->
        binding?.labelPassword?.error = null
    }

}
    override fun onDestroy() {
        super.onDestroy()
        binding = null
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
        else{
            username_input_layout.error =null
        }
        if(password_input_layout!!.editText!!.text!!.isEmpty()){
            notNull = false
            password_input_layout.error = getString(R.string.error_enter_password)
        }
        else{
            password_input_layout.error = null
        }
        if(notNull){
            val username = username_input_layout.editText?.text.toString()
            val password = password_input_layout.editText?.text.toString()
            viewModel.checkLogin(username,password)
            viewModel.response.observe(viewLifecycleOwner,{ login_response ->
                if(login_response == null){
                    showDiaLog(
                        getString(R.string.connection_error_title),
                        getString(R.string.connection_error_message),
                    )
                    return@observe
                }
                val ID = login_response.ID
                when(login_response.ack){
                    "account_not_exist"->{
                        showDiaLog(getString(R.string.incorrect_username_title),
                            getString(R.string.incorrect_username_message))
                        //pop up an alert dialog to notify incorrect username
                    }
                    "account_not_valid"->{
                        //pop up an alert dialog to notify incorrect password
                        showDiaLog(getString(R.string.incorrect_password_title),
                            getString(R.string.incorrect_password_message))

                    }
                    "seller_account_valid"->{
                        observeSeller(ID!!)
                    }
                    "normal_user_account_valid"->{
                        observeNUser(ID!!)
                    }
                }

            })
        }
    }
    private fun observeNUser(id:String){
        viewModel.getNormalUserInfo(id)
        viewModel.userAccount.observe(viewLifecycleOwner,{
            response ->
            if(response == null){
                showDiaLog(
                    getString(R.string.connection_error_title),
                    getString(R.string.connection_error_message),
                )
                return@observe
            }
            else {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainScreenNormalUsersFragment(
                    response))
            }
        })
    }
    private fun observeSeller(id:String){
        viewModel.getSellerInfo(id)
        viewModel.sellerAccount.observe(viewLifecycleOwner,{
            response ->
            if(response == null){
                showDiaLog(
                    getString(R.string.connection_error_title),
                    getString(R.string.connection_error_message),
                )
                return@observe
            }
            else{
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainScreenSellerFragment(
                response))
            }
        })
    }
    private fun showDiaLog(title:String,message: String){
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(getString(R.string.btn_ok)) { dialog, which ->
                dialog.cancel()
            }
        dialog.show()
    }


}