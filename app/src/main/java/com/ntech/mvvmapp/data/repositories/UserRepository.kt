package com.ntech.mvvmapp.data.repositories

import com.ntech.mvvmapp.data.db.AppDatabase
import com.ntech.mvvmapp.data.db.entities.User
import com.ntech.mvvmapp.data.network.MyApi
import com.ntech.mvvmapp.data.network.SafeApiRequest
import com.ntech.mvvmapp.data.network.responses.AuthResponse


class UserRepository(
    private val api: MyApi,
    private val db: AppDatabase
): SafeApiRequest() {

    suspend fun userLogin(email: String, password: String) : AuthResponse {
        return apiRequest { api.userLogin(email, password) }
    }

    suspend fun userSignUp(name: String, email: String, password: String): AuthResponse {
        return apiRequest { api.userSignUp(name, email, password) }
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getUser()
}