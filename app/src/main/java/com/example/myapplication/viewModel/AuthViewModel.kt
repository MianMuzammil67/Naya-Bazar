package com.example.myapplication.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.AuthRepository
import com.example.myapplication.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor (private val  repository: AuthRepository): ViewModel() {

    private val _signUpFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signUpFlow : StateFlow<Resource<FirebaseUser>?> = _signUpFlow

    private val _signInFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signInFlow : StateFlow<Resource<FirebaseUser>?> = _signInFlow

    fun signUp(name : String,email :String, password : String) = viewModelScope.launch {
        _signUpFlow.value = Resource.Loading()
        val result = repository.signUP(name,email,password)
        _signUpFlow.value = result
    }
    fun sighIn(email: String, password: String)= viewModelScope.launch {
        _signInFlow.value = Resource.Loading()
        val result = repository.signIn(email,password)
        _signInFlow.value = result
    }
    fun signOut(){
        repository.signOut()
        _signInFlow.value = null
        _signUpFlow.value = null
    }
}