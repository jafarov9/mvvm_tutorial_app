package com.ntech.mvvmapp.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ntech.mvvmapp.data.db.entities.User
import com.ntech.mvvmapp.data.repositories.UserRepository
import com.ntech.mvvmapp.util.ApiException
import com.ntech.mvvmapp.util.Coroutines
import com.ntech.mvvmapp.util.NoInternetException

open class AuthViewModel(
    private val repository: UserRepository
): ViewModel() {


    fun getLoggedInUser() = repository.getUser()

    suspend fun userLogin(
        email: String,
        password: String
    ) = repository.userLogin(email, password)

    suspend fun userSignUp(
        name: String,
        email: String,
        password: String
    ) = repository.userSignUp(name, email, password)

    suspend fun saveUser(user: User) = repository.saveUser(user)

}