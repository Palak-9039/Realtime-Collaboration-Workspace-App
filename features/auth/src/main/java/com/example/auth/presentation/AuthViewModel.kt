package com.example.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.Resource
import com.example.model.User
import com.example.network.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel  @Inject constructor(
    private val authRepository : AuthRepository
): ViewModel() {

    private val _authState = MutableStateFlow<Resource<User?>>(Resource.Success(null))

    val authState: StateFlow<Resource<User?>> = _authState.asStateFlow()

    fun signIn(email : String, password: String){
        viewModelScope.launch {
            authRepository.signInWithEmail(email,password).collect { result->
                _authState.value = result

            }
        }
    }

    fun signUp(name: String, email: String, password: String, department: String) {
        viewModelScope.launch {
            authRepository.signUpWithEmail(name, email, password, department).collect { result ->
                _authState.value = result
            }
        }
    }

    fun clearState() {
        _authState.value = Resource.Success(null)
    }
}