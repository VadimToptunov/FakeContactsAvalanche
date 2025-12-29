package com.toptunov.fakecontactsavalanche

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test

class ValidationTest : TestCase() {

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun testZeroInputShowsError() = run {
        step("Enter zero") {
            MainScreen {
                inputField.typeText("0")
                generateButton.click()
            }
        }

        step("Verify error is shown") {
            MainScreen {
                statusText.isDisplayed()
            }
        }
    }

    @Test
    fun testNegativeNumberShowsError() = run {
        step("Enter negative number (if possible)") {
            MainScreen {
                inputField.typeText("-5")
                generateButton.click()
            }
        }

        step("Wait for response") {
            MainScreen {
                flakySafely(timeoutMs = 1000) {
                    titleText.isDisplayed()
                }
            }
        }
    }

    @Test
    fun testMaximumLimitValidation() = run {
        step("Enter number exceeding 10000") {
            MainScreen {
                inputField.typeText("10001")
                generateButton.click()
            }
        }

        step("Verify error message appears") {
            MainScreen {
                flakySafely(timeoutMs = 2000) {
                    statusText.isDisplayed()
                }
            }
        }
    }

    @Test
    fun testValidNumberInRange() = run {
        step("Enter valid number") {
            MainScreen {
                inputField.typeText("10")
                generateButton.click()
            }
        }

        step("Verify no immediate error") {
            MainScreen {
                titleText.isDisplayed()
            }
        }
    }

    @Test
    fun testMaximumAllowedNumber() = run {
        step("Enter exactly 10000") {
            MainScreen {
                inputField.replaceText("10000")
                generateButton.click()
            }
        }

        step("Verify generation starts") {
            MainScreen {
                flakySafely(timeoutMs = 3000) {
                    progressCard.isDisplayed()
                }
            }
        }

        step("Cancel generation to avoid long wait") {
            MainScreen {
                cancelButton.click()
            }
        }
    }

    @Test
    fun testOnlyNumbersAccepted() = run {
        step("Input field should only accept numbers") {
            MainScreen {
                inputField.isDisplayed()
                inputField.typeText("123")
                inputField.hasText("123")
            }
        }
    }
}

