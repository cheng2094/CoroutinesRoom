package com.devtides.coroutinesroom.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.devtides.coroutinesroom.databinding.FragmentMainBinding
import com.devtides.coroutinesroom.model.LoginState
import com.devtides.coroutinesroom.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentMainBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.usernameTV.text = LoginState.user?.username
        binding.signoutBtn.setOnClickListener { onSignout() }
        binding.deleteUserBtn.setOnClickListener { onDelete() }

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.signout.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, "Signed out", Toast.LENGTH_SHORT).show()
            goToSignupScreen()
        })
        viewModel.userDeleted.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, "Signed out", Toast.LENGTH_SHORT).show()
            goToSignupScreen()
        })
    }

    private fun goToSignupScreen(){
        val action = MainFragmentDirections.actionGoToSignup()
        Navigation.findNavController(binding.usernameTV).navigate(action)
    }

    private fun onSignout() {
        viewModel.onSignout()
    }

    private fun onDelete() {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle("Delete user")
                .setMessage("Are you sure you want to delete this user?")
                .setPositiveButton("Yes"){p0, p1 -> viewModel.onDeleteUser()}
                .setNegativeButton("Cancel", null)
                .create()
                .show()
        }
    }

}
