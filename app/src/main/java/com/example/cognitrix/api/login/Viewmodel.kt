package com.example.cognitrix.api.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cognitrix.db.UserEntity
import com.example.cognitrix.db.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _user = MutableStateFlow<UserEntity?>(null)
    val user: StateFlow<UserEntity?> = _user

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val result = repository.login(username, password)
            print(result.toString())
            if (result.isSuccess) {
                _user.value = result.getOrNull()

            } else {

            }
        }
    }

    fun updateTokens(username: String, accessToken: String, refreshToken: String) {
        viewModelScope.launch {
            repository.updateAccessToken(username, accessToken)
            repository.updateRefreshToken(username, refreshToken)
        }
    }
}
class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}