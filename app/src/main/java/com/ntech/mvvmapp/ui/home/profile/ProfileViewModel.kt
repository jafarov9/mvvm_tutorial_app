package com.ntech.mvvmapp.ui.home.profile

import androidx.lifecycle.ViewModel
import com.ntech.mvvmapp.data.repositories.UserRepository

class ProfileViewModel(
    repository: UserRepository
) : ViewModel() {

    val user = repository.getUser()


}