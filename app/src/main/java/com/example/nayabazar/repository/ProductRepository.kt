package com.example.nayabazar.repository

import com.example.nayabazar.model.Category
import com.example.nayabazar.model.Product
import com.example.nayabazar.model.SaleProduct
import com.example.nayabazar.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepository @Inject constructor(private val fireStore: FirebaseFirestore) {

    private val _productFlow = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    val productFlow: StateFlow<Resource<List<Product>>> = _productFlow

    private val _saleProductFlow = MutableStateFlow<Resource<List<SaleProduct>>>(Resource.Loading())
    val saleProductFlow: StateFlow<Resource<List<SaleProduct>>> = _saleProductFlow

    private val _categoryFlow = MutableStateFlow<Resource<List<Category>>>(Resource.Loading())
    val categoryFlow: StateFlow<Resource<List<Category>>> = _categoryFlow

    private val _catProductFlow = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    val catProductFlow: StateFlow<Resource<List<Product>>> = _catProductFlow
    suspend fun getAllProducts() {
        try {
//            val snapshot = fireStore.collection("Top Products").get().await()
            val snapshot = fireStore.collection("Popular Products").get().await()
            val products =
                snapshot.documents.map { document -> document.toObject(Product::class.java) }
            _productFlow.emit(Resource.Success(products.filterNotNull()))
        } catch (e: Exception) {
            _productFlow.emit(Resource.Error(e.message))
        }
    }

    suspend fun getSaleProducts() {
        try {
            val snapshot = fireStore.collection("Hot sales").get().await()
            val saleProduct = snapshot.documents.mapNotNull { document ->
                document.toObject(SaleProduct::class.java)
            }
            _saleProductFlow.emit(Resource.Success(saleProduct))

        } catch (e: Exception) {
            _saleProductFlow.emit(Resource.Error(e.message))
        }
    }

    suspend fun getCategories() {
//        var cat = Category()
        try {
            val snapshot = fireStore.collection("Category").get().await()
            val categories = snapshot.documents
                .map { documentSnapshot ->
//                    cat.id = documentSnapshot.id
//                    documentSnapshot.toObject(Category::class.java)
//                }
                    val model = documentSnapshot.toObject(Category::class.java)
                    model?.apply { id = documentSnapshot.id }
                }

            _categoryFlow.emit(Resource.Success(categories.filterNotNull()))
        } catch (e: Exception) {
            _categoryFlow.emit(Resource.Error(e.message))
        }
    }
    suspend fun getCategoryProduct(categoryName: String) {
        try {
            val snapshot = fireStore.collection("Category")
                .document(categoryName)
                .collection("Products").get().await()
            val product = snapshot.documents.mapNotNull { document ->
                document.toObject(Product::class.java)
            }
            _catProductFlow.emit(Resource.Success(product))
        } catch (e: Exception) {
            _categoryFlow.emit(Resource.Error(e.message))
        }
    }


}