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
import com.devtides.coroutinesroom.databinding.FragmentLoginBinding
import com.devtides.coroutinesroom.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener { onLogin(it) }
        binding.gotoSignupBtn.setOnClickListener { onGotoSignup(it) }

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loginComplete.observe(viewLifecycleOwner, Observer { isComplete ->
            if(isComplete){
                Toast.makeText(activity, "Login complete", Toast.LENGTH_SHORT).show()
                val action = LoginFragmentDirections.actionGoToMain()
                Navigation.findNavController(binding.loginUsername).navigate(action)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            if(!error.isNullOrEmpty()){
                Toast.makeText(activity,"Error: $error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onLogin(v: View) {
        val username = binding.loginUsername.text.toString()
        val password = binding.loginPassword.text.toString()
        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }else{
            viewModel.login(username, password)
        }
    }

    private fun onGotoSignup(v: View){
        val action = LoginFragmentDirections.actionGoToSignup()
        Navigation.findNavController(v).navigate(action)
    }
}
