package com.magdaraog.engagia.ojtapp

class ProductsUOM (val UOMId: Int? = null, val UOMCode: String? = null, val UOM: String? = null, val UOMPrice: String? = null) {
    override fun toString(): String {
        return "ProductsUOM(UOMId=$UOMId, UOMCode=$UOMCode, UOM=$UOM, UOMPrice=$UOMPrice)"
    }
}