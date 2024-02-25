package com.example.nayabazar.repository

import android.util.Log
import com.example.nayabazar.model.Order
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrderRepository @Inject constructor(private val fireStore: FirebaseFirestore) {


    suspend fun placeOrder(order: Order){
        try {
            fireStore.collection("Orders").add(order).await()
        }catch (e:Exception){
            Log.d("OrderRepo", e.toString())

        }
    }
}
