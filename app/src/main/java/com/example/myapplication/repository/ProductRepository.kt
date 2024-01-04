package com.example.myapplication.repository

import com.example.myapplication.model.Product
import com.example.myapplication.model.SaleProduct
import com.example.myapplication.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class  ProductRepository @Inject constructor(private val fireStore: FirebaseFirestore) {

        private val _productFlow = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
        val productFlow : StateFlow<Resource<List<Product>>> = _productFlow

    private  val _saleProductFlow = MutableStateFlow<Resource<List<SaleProduct>>>(Resource.Loading())
    val saleProduct : StateFlow<Resource<List<SaleProduct>>>
        get() {return _saleProductFlow}

       suspend fun getAllProducts()  {
             try {
                      val snapshot = fireStore.collection("Products").get().await()
                      val products =
                      snapshot.documents.map { document -> document.toObject(Product::class.java) }
                 _productFlow.emit(Resource.Success(products.filterNotNull()))
              }catch (e:Exception){
                 _productFlow.emit(Resource.Loading())
              }
      }
    suspend fun getSaleProducts()  {
             try {
                      val snapshot = fireStore.collection("Hot sales").get().await()
                      val products =
                      snapshot.documents.map { document -> document.toObject(SaleProduct::class.java) }
                      _saleProductFlow.emit(Resource.Success(products.filterNotNull()))
              }catch (e:Exception){
                      _saleProductFlow.emit(Resource.Loading())
              }
      }


}