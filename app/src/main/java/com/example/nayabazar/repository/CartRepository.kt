package com.example.nayabazar.repository

import android.util.Log
import com.example.nayabazar.model.Product
import com.example.nayabazar.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CartRepository @Inject constructor(
    auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) {
    private val currentUser = auth.currentUser
    private val userId = currentUser?.uid ?: ""
    private val _cartItemFlow = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    val cartItemFlow: StateFlow<Resource<List<Product>>> = _cartItemFlow
    suspend fun addToCart(product: Product) {
        try {
            fireStore.collection("User")
                .document(userId)
                .collection("Cart").document(product.productName)
                .set(product).await()
        } catch (e: Exception) {
            Log.d("cartRepo", e.toString())
        }
    }
    suspend fun updateCart(product: Product, quantity: Int) {
        try {
            val cartItemRef = fireStore.collection("User")
                .document(userId)
                .collection("Cart")
                .document(product.productName)

            val documentSnapshot = cartItemRef.get().await()
            if (documentSnapshot.exists()) {
                cartItemRef.update("productQuantity", quantity.toString()).await()
            } else {
                Log.d(
                    "cartRepo",
                    "Document does not exist for product ${product.productId}"
                )
            }
        } catch (e: Exception) {
            Log.d("cartRepo", e.toString())
        }
    }
    suspend fun getFromCart() {
        try {
            fireStore.collection("User")
                .document(userId)
                .collection("Cart")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        _cartItemFlow.value = (Resource.Error(error.message))
                        return@addSnapshotListener
                    }
                    if (snapshot != null) {
                        val cartItems = snapshot.documents.map { document ->
                            document.toObject(Product::class.java)
                        }
                        _cartItemFlow.value = (Resource.Success(cartItems.filterNotNull()))
                    }
                }

        } catch (e: Exception) {
            _cartItemFlow.emit(Resource.Error(e.message))
        }
    }
    suspend fun deleteFromCart(product: Product) {
        try {
            fireStore.collection("User").document(userId).collection("Cart")
                .document(product.productName).delete().await()
        } catch (e: Exception) {
            Log.d("cartrepo", e.toString())
        }
    }
   suspend fun emptyCart() {
        try {
            val cartCollection = fireStore.collection("User").document(userId)
                .collection("Cart")
                .get().await()
            for (document in cartCollection ){
                fireStore.collection("User")
                    .document(userId)
                    .collection("Cart")
                    .document(document.id)
                    .delete()
                    .await()
            }
        } catch (e: Exception) {
            Log.d("cartrepo", e.toString())
        }    }
}
