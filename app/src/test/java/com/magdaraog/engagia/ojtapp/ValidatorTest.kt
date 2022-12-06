package com.magdaraog.engagia.ojtapp

import com.google.common.truth.Truth.*
import com.magdaraog.engagia.ojtapp.test.Validator
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

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

    enum class FunctionTest{
        Function1,
        Function2,
        Function3,
        Function4,
        Function5
    }

    private fun functionChecker(sectionLink: Int): FunctionTest?{
        when(sectionLink) {
            1 -> {
                return FunctionTest.Function1
            }
            5-> {
                return FunctionTest.Function2
            }
        }
        return null
    }

    @Test
    fun functionTester() {
        enumValues<FunctionTest>().forEach { FUNCTION_TYPE ->
            var i = 0

            while (i <= 1000){
                val x = functionChecker(i)
                if (i != 1000){
                    if (x == FUNCTION_TYPE){
                        break
                    }
                } else if(i == 1000){
                    break
                }
                i++
            }
        }
    }
}