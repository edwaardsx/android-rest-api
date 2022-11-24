package com.magdaraog.engagia.ojtapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProductsViewModel : ViewModel() {

    private var products: MutableLiveData<List<Products>>? = null
    private var productsUOM: MutableLiveData<List<ProductsUOM>>? = null
    private var uomCategories: MutableLiveData<List<String>>? = null
    private var uomProductIDs: MutableLiveData<List<String>>? = null

    var tempProductID = MutableLiveData<String>()
    var tempProdCode = MutableLiveData<String>()
    var tempProdName = MutableLiveData<String>()

    private var productsRepository: ProductsRepository? = null

    fun init() {
        if (products != null) {
            return
        }
        productsRepository = ProductsRepository.getInstance()
        products = productsRepository!!.getProducts()
    }

    fun initUOMcategs() {
        if (uomCategories != null) {
            return
        }
        productsRepository = ProductsRepository.getInstance()
        uomCategories = productsRepository!!.getUOMcategs()
    }

    fun initUOM(prodCode: String?) {
        if (productsUOM != null) {
            return
        }
        productsRepository = ProductsRepository.getInstance()
        productsUOM = productsRepository!!.getUOM(prodCode)
    }

    fun initUOMIds(prodCode: String?) {
        if (uomProductIDs != null) {
            return
        }
        productsRepository = ProductsRepository.getInstance()
        uomProductIDs = productsRepository!!.getUOMIds(prodCode)
    }

    fun getProducts(): LiveData<List<Products>>? {
        return products
    }

    fun getUOMcategories(): LiveData<List<String>>? {
        return uomCategories
    }

    fun getUOM(): LiveData<List<ProductsUOM>>? {
        return productsUOM
    }

    fun getUOMIds(): LiveData<List<String>>? {
        return uomProductIDs
    }

    fun editProductValue(prodID: String?, prodCode:String?, prodName:String?) {
        productsRepository = ProductsRepository.getInstance()
        val job = CoroutineScope(IO).launch {
            productsRepository!!.updateProducts(prodID, prodCode,prodName)
        }
        runBlocking {
            job.join()
            init()
        }
    }

    fun editUOMValue(UOMId: String?, prodCode:String?, UOM:String?, price: String?) {
        productsRepository = ProductsRepository.getInstance()
        val job = CoroutineScope(IO).launch {
            productsRepository!!.updateUOM(UOMId, prodCode, UOM, price)
        }
        runBlocking {
            job.join()
            init()
        }
    }

    fun createNewValue(prodCode:String?, prodName:String?) {
        productsRepository = ProductsRepository.getInstance()
        val job = CoroutineScope(IO).launch {
            productsRepository!!.createProducts(prodCode,prodName)
            productsRepository = ProductsRepository.getInstance()
            val allDataProducts = productsRepository!!.getProducts()
            products?.postValue(listOf(Products(allDataProducts.value?.get(allDataProducts.value!!.size)?.prodID?.plus(1), prodCode, prodName)))
        }
        runBlocking {
            job.join()
            init()
        }
    }

    fun createNewUOMcateg(category: String?) {
        productsRepository = ProductsRepository.getInstance()
        val job = CoroutineScope(IO).launch {
            productsRepository!!.createUOMCategory(category)
        }
        runBlocking {
            job.join()
            init()
        }
    }

    fun createNewUOM(prodCode: String?, uom: String?, price: String?) {
        productsRepository = ProductsRepository.getInstance()
        val job = CoroutineScope(IO).launch {
            productsRepository!!.createUOM(prodCode, uom, price)
        }
        runBlocking {
            job.join()
            init()
        }
    }

    fun deleteProductValue(prodID: String?) {
        productsRepository = ProductsRepository.getInstance()
        val job = CoroutineScope(IO).launch {
            productsRepository!!.deleteProducts(prodID)
        }
        runBlocking {
            job.join()
            init()
        }
    }

    fun deleteCategory(categ: String?) {
        productsRepository = ProductsRepository.getInstance()
        CoroutineScope(IO).launch {
            productsRepository!!.deleteCateg(categ)
        }
    }

    fun deleteUOM(UOMId: String?) {
        productsRepository = ProductsRepository.getInstance()
        CoroutineScope(IO).launch {
            productsRepository!!.deleteUOM(UOMId)
        }
    }
}