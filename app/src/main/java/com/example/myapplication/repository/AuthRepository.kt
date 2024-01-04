package com.example.myapplication.repository

import com.example.myapplication.model.UserData
import com.example.myapplication.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor (private val auth : FirebaseAuth,
                                          private val realtimeDb : FirebaseDatabase) {

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    suspend fun signUP(name :String, eMail :String, password : String): Resource<FirebaseUser> {
        return try {
           val result= auth.createUserWithEmailAndPassword(eMail, password).await()
            result.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
            auth.currentUser?.let {
                realtimeDb.reference.child("User").child(it.uid)
                    .setValue(UserData(name,eMail,password)) }
            Resource.Success(result.user!!)

        }catch(e : Exception){
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }
    suspend fun signIn  (email : String, password: String) : Resource<FirebaseUser>{
        return try {
          val result =  auth.signInWithEmailAndPassword(email, password).await()

            Resource.Success(result.user!!)
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }
    fun signOut(){
        auth.signOut()

    }

}