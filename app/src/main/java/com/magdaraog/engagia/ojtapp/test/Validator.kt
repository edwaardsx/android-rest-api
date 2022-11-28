package com.magdaraog.engagia.ojtapp.test

object Validator {
    fun validateInput(productCode: String, productName: String): Boolean {
    return!(productCode.isEmpty() || productName.isEmpty())
    }
}