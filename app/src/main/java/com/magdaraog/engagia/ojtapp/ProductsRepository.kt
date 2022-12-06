package com.magdaraog.engagia.ojtapp

import android.net.TrafficStats
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@Suppress("KotlinConstantConditions")
class ProductsRepository {

    private var ipAddress: String = "192.168.100.7:8765"
    private var tableListURL: String = "http://$ipAddress/productsTable/tablelist.json"

    private var addURL = "http://$ipAddress/productsTable/add.json"
    private var updateProductURL = "http://$ipAddress/productsTable/edit/"
    private var deleteProductURL = "http://$ipAddress/productsTable/deleterecord/"
    private var deleteUOMurlCategs = "http://$ipAddress/productsTable/deletecategs/"
    private var categListURL = "http://$ipAddress/productsTable/listcategs.json"
    private var addCategsURL = "http://$ipAddress/productsTable/addcategs.json"
    private var uomUrl = "http://$ipAddress/productsTable/listuom/"
    private var addUOMurl = "http://$ipAddress/productsTable/adduom.json"
    private var updateUOMurl = "http://$ipAddress/productsTable/edituom/"
    private var deleteUOMurl = "http://$ipAddress/productsTable/deleteuomrecord/"
    private var listAllUOMurl: String = "http://$ipAddress/productsTable/listalluom.json"

    private val dataSet = java.util.ArrayList<Products>()
    private val uomDataSet = java.util.ArrayList<ProductsUOM>()
    private val uomCategSet = java.util.ArrayList<String>()
    private val uomProductIDSet = java.util.ArrayList<String>()

    companion object {
        private var instance: ProductsRepository? = null
        fun getInstance() = instance
            ?: ProductsRepository().also {
                instance = it
            }
    }

    fun getProducts(): MutableLiveData<List<Products>> {
        fetchProducts()
        val data: MutableLiveData<List<Products>> = MutableLiveData()
        data.postValue(dataSet)
        return data
    }

    fun getAllUOM(): MutableLiveData<List<ProductsUOM>> {
        fetchAllUOM()
        val data: MutableLiveData<List<ProductsUOM>> = MutableLiveData()
        data.postValue(uomDataSet)
        return data
    }

    fun getUOM(prodcode: String?): MutableLiveData<List<ProductsUOM>> {
        fetchUOM(prodcode)
        val data: MutableLiveData<List<ProductsUOM>> = MutableLiveData()
        data.value = uomDataSet
        return data
    }

    fun getUOMcategs(): MutableLiveData<List<String>> {
        fetchUOMcategs()
        val data: MutableLiveData<List<String>> = MutableLiveData()
        data.value = uomCategSet
        return data
    }

    fun getUOMIds(prodcode: String?): MutableLiveData<List<String>> {
        fetchUOMIds(prodcode)
        val data: MutableLiveData<List<String>> = MutableLiveData()
        data.value = uomProductIDSet
        return data
    }

    fun createProducts(prodCode: String?, prodName: String?) {
        val job = CoroutineScope(IO).launch {
            httpPost(addURL, prodCode, prodName)
        }
        runBlocking {
            job.join()
            getProducts()
        }
    }

    fun createUOMCategory(category: String?) {
        val job = CoroutineScope(IO).launch {
            httpPostForUOMcategs(addCategsURL, category)
        }
        runBlocking {
            job.join()
        }
    }

    fun createUOM(prodCode: String?, uom: String?, price: String?) {
        val job = CoroutineScope(IO).launch {
            httpPostForUOM(addUOMurl, prodCode, uom, price)
        }
        runBlocking {
            job.join()
        }
    }

    fun updateProducts(prodID: String?, prodCode: String?, prodName: String?) {
        val job = CoroutineScope(IO).launch {
            httpPost("$updateProductURL$prodID.json", prodCode, prodName)
        }
        runBlocking {
            job.join()
            getProducts()
        }
    }

    fun updateUOM(UOMId: String?, prodCode: String?, UOM: String?, price: String?) {
        val job = CoroutineScope(IO).launch {
            httpPostForUOM("$updateUOMurl$UOMId.json", prodCode, UOM, price)
        }
        runBlocking {
            job.join()
            getProducts()
        }
    }

    fun deleteProducts(prodID: String?) {
        val job = CoroutineScope(IO).launch {
            httpDelete(deleteProductURL, prodID)
        }
        runBlocking {
            job.join()
            getProducts()
        }
    }

    fun deleteCateg(categ: String?) {
        CoroutineScope(IO).launch {
            httpDelete(deleteUOMurlCategs, categ)
        }
    }

    fun deleteUOM(UOMId: String?) {
        CoroutineScope(IO).launch {
            httpDelete(deleteUOMurl, UOMId)
        }
    }

    private fun fetchProducts() {
        val job = CoroutineScope(IO).launch {
            val output = httpGet(tableListURL)
            val obj = JSONObject(output)
            val userArray: JSONArray = obj.getJSONArray("products")
            dataSet.clear()
            for (i in 0 until userArray.length()) {
                val productsDetail: JSONObject = userArray.getJSONObject(i)
                dataSet.add(Products(productsDetail.getString("id").toInt(), productsDetail.getString("product_code"),
                    productsDetail.getString("product_name")))
            }
        }
        runBlocking {
            job.join()
        }
    }

    private fun fetchAllUOM() {
        val job = CoroutineScope(IO).launch {
            val output = httpGet(listAllUOMurl)
            val obj = JSONObject(output)
            val userArray: JSONArray = obj.getJSONArray("products")
            uomDataSet.clear()
            for (i in 0 until userArray.length()) {
                val productsDetail: JSONObject = userArray.getJSONObject(i)
                uomDataSet.add(
                    ProductsUOM(productsDetail.getString("id").toInt(), productsDetail.getString("product_code"),
                        productsDetail.getString("unit_of_measure"), productsDetail.getString("price"))
                )
            }
        }
        runBlocking {
            job.join()
        }
    }

    private fun fetchUOMcategs() {
        val job = CoroutineScope(IO).launch {
            val output = httpGet(categListURL)
            val obj = JSONObject(output)
            val userArray: JSONArray = obj.getJSONArray("uom")
            uomCategSet.clear()
            for (i in 0 until userArray.length()) {
                val productsDetail: JSONObject = userArray.getJSONObject(i)
                uomCategSet.add(productsDetail.getString("categories"))
            }
        }
        runBlocking {
            job.join()
        }
    }

    private fun fetchUOM(prodCode: String?) {
        val job = CoroutineScope(IO).launch {
            val output = httpGet("$uomUrl$prodCode.json")
            val obj = JSONObject(output)
            val userArray: JSONArray = obj.getJSONArray("productUnitOfMeasures")
            uomDataSet.clear()
            for (i in 0 until userArray.length()) {
                val productsDetail: JSONObject = userArray.getJSONObject(i)
                uomDataSet.add(ProductsUOM(productsDetail.getString("id").toInt(), productsDetail.getString("product_code"),
                    productsDetail.getString("unit_of_measure"), productsDetail.getString("price")))
            }
        }
        runBlocking {
            job.join()
        }
    }

    private fun fetchUOMIds(prodCode: String?){
        val job = CoroutineScope(IO).launch {
            val output = httpGet("$uomUrl$prodCode.json")
            val obj = JSONObject(output)
            val userArray: JSONArray = obj.getJSONArray("productUnitOfMeasures")
          uomProductIDSet.clear()
            for (i in 0 until userArray.length()) {
                val productsDetail: JSONObject = userArray.getJSONObject(i)
                uomProductIDSet.add(productsDetail.getString("id"))
            }
        }
        runBlocking {
            job.join()
        }
    }

    private fun httpGet(myURL: String?): String {
        val inputStream: InputStream
        val url = URL(myURL)
        val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
        conn.connect()
        inputStream = conn.inputStream
        return if (inputStream != null) {
            convertInputStreamToString(inputStream)
        } else {
            "Did not work!"
        }
    }

    private fun httpPost(myURL: String?, prodCode: String?, prodName: String?): String {
        val result:String
        TrafficStats.setThreadStatsTag(Thread.currentThread().id.toInt())
        val url = URL(myURL)
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        urlConnection.readTimeout = 30000
        urlConnection.connectTimeout = 30000
        val builder: Uri.Builder = Uri.Builder()
            .appendQueryParameter("product_code", prodCode)
            .appendQueryParameter("product_name", prodName)
        val query: String? = builder.build().encodedQuery
        urlConnection.requestMethod = "POST"
        urlConnection.doInput = true
        urlConnection.doOutput = true
        urlConnection.connect()
        val wr = DataOutputStream(urlConnection.outputStream)
        wr.writeBytes(query)
        wr.flush()
        wr.close()
        val isNew: InputStream = urlConnection.inputStream
        BufferedReader(InputStreamReader(isNew))
        result = convertInputStreamToString(isNew)
        return result
    }

    private fun httpPostForUOMcategs(myURL: String?, category: String?): String {
        val result:String
        TrafficStats.setThreadStatsTag(Thread.currentThread().id.toInt())
        val url = URL(myURL)
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        urlConnection.readTimeout = 30000
        urlConnection.connectTimeout = 30000
        val builder: Uri.Builder = Uri.Builder()
            .appendQueryParameter("categories", category)
        val query: String? = builder.build().encodedQuery
        urlConnection.requestMethod = "POST"
        urlConnection.doInput = true
        urlConnection.doOutput = true
        urlConnection.connect()
        val wr = DataOutputStream(urlConnection.outputStream)
        wr.writeBytes(query)
        wr.flush()
        wr.close()
        val isNew: InputStream = urlConnection.inputStream
        BufferedReader(InputStreamReader(isNew))
        result = convertInputStreamToString(isNew)
        return result
    }

    private fun httpPostForUOM(myURL: String?, prodCode: String?, uom: String?, price: String?): String {
        val result:String
        TrafficStats.setThreadStatsTag(Thread.currentThread().id.toInt())
        val url = URL(myURL)
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        urlConnection.readTimeout = 30000
        urlConnection.connectTimeout = 30000
        val builder: Uri.Builder = Uri.Builder()
            .appendQueryParameter("product_code", prodCode)
            .appendQueryParameter("unit_of_measure", uom)
            .appendQueryParameter("price", price)
        val query: String? = builder.build().encodedQuery
        urlConnection.requestMethod = "POST"
        urlConnection.doInput = true
        urlConnection.doOutput = true
        urlConnection.connect()
        val wr = DataOutputStream(urlConnection.outputStream)
        wr.writeBytes(query)
        wr.flush()
        wr.close()
        val isNew: InputStream = urlConnection.inputStream
        BufferedReader(InputStreamReader(isNew))
        result = convertInputStreamToString(isNew)
        return result
    }

    private fun httpDelete(myURL: String?, prodID: String?): String {
        val result: String
        TrafficStats.setThreadStatsTag(Thread.currentThread().id.toInt())
        val url = URL("$myURL$prodID.json")
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        urlConnection.readTimeout = 30000
        urlConnection.connectTimeout = 30000
        urlConnection.requestMethod = "DELETE"
        urlConnection.doInput = true
        urlConnection.doOutput = true
        urlConnection.connect()
        val isNew: InputStream = urlConnection.inputStream
        result = convertInputStreamToString(isNew)
        return result
    }

    private fun convertInputStreamToString(inputStream: InputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        var line:String? = bufferedReader.readLine()
        var result = ""
        while (line != null) {
            result += line
            line = bufferedReader.readLine()
        }
        inputStream.close()
        return result
    }
}