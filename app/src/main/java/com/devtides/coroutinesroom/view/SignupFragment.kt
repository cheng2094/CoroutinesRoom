package com.devtides.coroutinesroom.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.devtides.coroutinesroom.databinding.FragmentSignupBinding
import com.devtides.coroutinesroom.viewmodel.SignupViewModel

class SignupFragment : Fragment() {

    private lateinit var viewModel: SignupViewModel
    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreate(savedInstanceState)
        binding = FragmentSignupBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signupBtn.setOnClickListener { onSignup(it) }
        binding.gotoLoginBtn.setOnClickListener { onGotoLogin(it) }

        viewModel = ViewModelProvider(this)[SignupViewModel::class.java]
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.signupComplete.observe(viewLifecycleOwner, Observer { isComplete ->
            Toast.makeText(activity, "Signup complete", Toast.LENGTH_SHORT).show()
            val action = SignupFragmentDirections.actionGoToMain()
                Navigation.findNavController(binding.signupUsername).navigate(action)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(activity,"Error: $error", Toast.LENGTH_SHORT).show()
        })
    }

    private fun onSignup(v: View){
        val username = binding.signupUsername.text.toString()
        val password = binding.signupPassword.text.toString()
        val info = binding.otherInfo.text.toString()
        if(username.isEmpty() || password.isEmpty() || info.isEmpty()){
            Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }else{
            viewModel.signup(username, password, info)
        }
    }

    private fun onGotoLogin(v: View) {
        val action = SignupFragmentDirections.actionGoToLogin()
        Navigation.findNavController(v).navigate(action)
    }
}
