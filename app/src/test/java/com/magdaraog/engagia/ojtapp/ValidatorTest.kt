package com.magdaraog.engagia.ojtapp

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.google.common.truth.Truth.*
import com.magdaraog.engagia.ojtapp.test.Validator

@RunWith(JUnit4::class)
class ValidatorTest {

    @Test
    fun whenInputIsValid(){
        val productCode = "Some random code"
        val productName = "Some random name"

        val result = Validator.validateInput(productCode, productName)
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun whenInputIsInvalid(){
        val productCode = ""
        val productName = ""

        val result = Validator.validateInput(productCode, productName)
        assertThat(result).isEqualTo(false)
    }
}


























