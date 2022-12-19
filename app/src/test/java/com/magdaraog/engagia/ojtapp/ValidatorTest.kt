package com.magdaraog.engagia.ojtapp

import com.google.common.truth.Truth.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidatorTest {

    enum class FunctionTest {
        TEST,
        SAMPLE,
        TEST_SAMPLE,
        TEST_TEST,
        SAMPLE_SAMPLE
    }

    private fun functionChecker(sectionLink: Int): FunctionTest? {
        when(sectionLink) {
            1 -> {
                return FunctionTest.TEST
            }
            12-> {
                return FunctionTest.SAMPLE
            }
            33-> {
                return FunctionTest.TEST_SAMPLE
            }
        }
        return null
    }

    @Test
    fun functionTester() {

        val missingFunctions =  ArrayList<String>()
        var errorMsg = "Cannot find function "

        enumValues<FunctionTest>().forEach { FUNCTION_TYPE ->
            var i = 0

            while (i <= 1000) {
                val x = functionChecker(i)
                if (i != 1000) {
                    if (x == FUNCTION_TYPE) {
                        println("Found a function: $FUNCTION_TYPE, Found at position $i")
                        break
                    }
                } else if(i == 1000) {
                    missingFunctions.add(FUNCTION_TYPE.toString())
                }
                i++
            }
        }

        if (missingFunctions.size != 0){
            for (missingFunction in missingFunctions) {
                errorMsg += missingFunction + ", "
            }
            fail(errorMsg)
        }
    }
}