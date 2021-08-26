package com.example.muzee.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doBeforeTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.muzee.R
import com.example.muzee.databinding.LoginFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.labelSignUp?.setOnClickListener{
            gotoSignUpFragment()
        }
        binding?.btnSignIn?.setOnClickListener{
            handleSignInBtn()
        }
        viewModel.status.observe(viewLifecycleOwner,{ it ->
            when(it){
                LoginViewModel.ApiStatus.SUCCESS->{
                    handleSuccessCase()
                }
                LoginViewModel.ApiStatus.ERROR->{
                    showSnackBar()
                }
                LoginViewModel.ApiStatus.WRONGUSERNAME->{
                    showDiaLog(getString(R.string.incorrect_username_title),getString(R.string.incorrect_username_message))
                }
                LoginViewModel.ApiStatus.WRONGPASSWORD->{
                    showDiaLog(getString(R.string.incorrect_password_title),getString(R.string.incorrect_password_message))
                }
            }
        })
    }
    private fun gotoSignUpFragment(){
        findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
    }
    private fun handleSuccessCase(){
        viewModel.sellerAccount.observe(viewLifecycleOwner,{
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainScreenSellerFragment(it,viewModel.accountID.value!!))
        })
        viewModel.userAccount.observe(viewLifecycleOwner,{
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainScreenNormalUsersFragment(it))
        })
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
        }
    }
    private fun showDiaLog(title:String,message: String){
        val dialog = MaterialAlertDialogBuilder(requireContext())
        dialog.setTitle(title)
            .setMessage(message)
            .setPositiveButton(getString(R.string.btn_ok)) { dialog, which ->
                dialog.cancel()
            }
        dialog.show()
    }
    private fun showSnackBar(){
        Snackbar.make(binding!!.root,R.string.connection_error_title,Snackbar.LENGTH_SHORT).show()
    }


}