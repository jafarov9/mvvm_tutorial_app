package com.ntech.mvvmapp.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ntech.mvvmapp.R
import com.ntech.mvvmapp.databinding.ActivitySignupBinding
import com.ntech.mvvmapp.ui.home.HomeActivity
import com.ntech.mvvmapp.util.ApiException
import com.ntech.mvvmapp.util.NoInternetException
import com.ntech.mvvmapp.util.snackbar
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignupActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)


        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if(user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

        binding.buttonSignUp.setOnClickListener {
            userSignUp()
        }
    }

    private fun userSignUp() {
        val name = binding.editTextName.text.toString().trim()
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        val password1 = binding.editTextPasswordConfirm.text.toString().trim()

        //@todo user validation

        lifecycleScope.launch {
            try {
                val authResponse = viewModel.userSignUp(name, email, password)

                if(authResponse.user != null) {
                    viewModel.saveUser(authResponse.user)
                } else {
                    binding.root.snackbar(authResponse.message!!)
                }

            } catch (e: ApiException) {
                e.printStackTrace()
            } catch (e: NoInternetException) {
                e.printStackTrace()
            }
        }
    }

}