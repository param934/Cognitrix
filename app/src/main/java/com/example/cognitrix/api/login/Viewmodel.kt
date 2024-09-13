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
    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError

    private val _registerError = MutableStateFlow<String?>(null)
    val registerError: StateFlow<String?> = _registerError
    private val _isRegistrationSuccessful = MutableStateFlow<Boolean>(false)
    val isRegistrationSuccessful: StateFlow<Boolean> = _isRegistrationSuccessful

    fun login(username: String, password: String) {
        viewModelScope.launch {

            val result = repository.login(username, password)
//            print("output "+result.toString())
            if (result.isSuccess) {
                _user.value = result.getOrNull()

            } else {
                _loginError.value = result.exceptionOrNull()?.message

            }
        }
    }
    fun fetchUser() {
        viewModelScope.launch {
            val fetchedUser = repository.getUser()
            _user.emit(fetchedUser)
            println("dataaaaaaaaaa "+_user.value)
        }
    }
    // Registration function
    fun registerUser(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            val result = repository.register(registerRequest)
            if (result.isSuccess) {
                _user.value=result.getOrNull()
                println("sucess")
                _isRegistrationSuccessful.value = true
            } else {
                _registerError.value = result.exceptionOrNull()?.message
                println(result.exceptionOrNull()?.message)
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