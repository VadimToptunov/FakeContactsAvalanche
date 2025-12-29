package com.toptunov.fakecontactsavalanche

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test

class MainActivityTest : TestCase() {

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun testAppLaunchesSuccessfully() = run {
        step("Verify app launches and displays main screen") {
            MainScreen {
                titleText.isDisplayed()
                titleText.hasText(R.string.app_title)
            }
        }
    }

    @Test
    fun testInputFieldIsVisible() = run {
        step("Verify input field is visible") {
            MainScreen {
                inputField.isDisplayed()
                inputField.hasHint(R.string.contacts_quantity)
            }
        }
    }

    @Test
    fun testGenerateButtonIsVisible() = run {
        step("Verify generate button is visible") {
            MainScreen {
                generateButton.isDisplayed()
                generateButton.hasText(R.string.generate_btn_text)
            }
        }
    }

    @Test
    fun testEmptyInputShowsError() = run {
        step("Click generate button without input") {
            MainScreen {
                generateButton.click()
            }
        }

        step("Verify error message appears") {
            MainScreen {
                statusText.isDisplayed()
            }
        }
    }

    @Test
    fun testInputAcceptsNumbers() = run {
        step("Type number in input field") {
            MainScreen {
                inputField.typeText("50")
                inputField.hasText("50")
            }
        }
    }

    @Test
    fun testLargeNumberShowsError() = run {
        step("Enter number exceeding maximum") {
            MainScreen {
                inputField.typeText("99999")
                generateButton.click()
            }
        }

        step("Verify error message appears") {
            MainScreen {
                statusText.isDisplayed()
            }
        }
    }

    @Test
    fun testHelperTextIsVisible() = run {
        step("Verify helper text is displayed") {
            MainScreen {
                inputLayout.isDisplayed()
            }
        }
    }

    @Test
    fun testProgressCardInitiallyHidden() = run {
        step("Verify progress card is not visible initially") {
            MainScreen {
                progressCard.isNotDisplayed()
            }
        }
    }

    @Test
    fun testCancelButtonInitiallyHidden() = run {
        step("Verify cancel button is not visible initially") {
            MainScreen {
                cancelButton.isNotDisplayed()
            }
        }
    }

    @Test
    fun testInputFieldClearable() = run {
        step("Type in input field") {
            MainScreen {
                inputField.typeText("123")
            }
        }

        step("Clear input field") {
            MainScreen {
                inputField.clearText()
                inputField.hasEmptyText()
            }
        }
    }

    @Test
    fun testTitleTextContent() = run {
        step("Verify title displays correct text") {
            MainScreen {
                titleText.isDisplayed()
                titleText.hasAnyText()
            }
        }
    }
}

