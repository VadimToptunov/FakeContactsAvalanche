package com.toptunov.fakecontactsavalanche

import android.Manifest
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test

class PermissionsTest : TestCase() {

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS
    )

    @Test
    fun testAppWorksWithPermissionsGranted() = run {
        step("Verify app launches with permissions") {
            MainScreen {
                titleText.isDisplayed()
                inputField.isDisplayed()
                generateButton.isDisplayed()
            }
        }

        step("Verify input can be entered") {
            MainScreen {
                inputField.typeText("5")
                inputField.hasText("5")
            }
        }
    }

    @Test
    fun testSmallNumberGenerationFlow() = run {
        step("Enter small number of contacts") {
            MainScreen {
                inputField.replaceText("3")
            }
        }

        step("Click generate button") {
            MainScreen {
                generateButton.click()
            }
        }

        step("Verify progress card becomes visible") {
            MainScreen {
                flakySafely(timeoutMs = 5000) {
                    progressCard.isDisplayed()
                }
            }
        }
    }
}

