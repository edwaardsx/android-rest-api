package com.magdaraog.engagia.ojtapp

class Products (val prodID: Int? = null, val prodCode: String? = null, val prodName: String? = null) {
    override fun toString(): String {
        return "Products(prodID=$prodID, prodCode=$prodCode, prodName=$prodName)"
    }
}