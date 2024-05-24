package com.example.diplom

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun test_incorrect_password_alert() {
        val signInButton = composeTestRule.onNode(
            hasTestTag("signInButton"),
            useUnmergedTree = true
        )
        val loginForm = composeTestRule.onNode(
            hasTestTag("loginForm"),
            useUnmergedTree = true
        )
        val passwordForm = composeTestRule.onNode(
            hasTestTag("passwordForm"),
            useUnmergedTree = true
        )

        loginForm.performTextInput("incorrect username")
        passwordForm.performTextInput("incorrect password")

        signInButton.performClick()

        val errorMessage = composeTestRule.onNodeWithTag("errorMessage")
        errorMessage.assertIsDisplayed()
    }

    @Test
    fun test_products_opening() {

        val signInButton = composeTestRule.onNode(
            hasTestTag("signInButton"),
            useUnmergedTree = true
        )
        val loginForm = composeTestRule.onNode(
            hasTestTag("loginForm"),
            useUnmergedTree = true
        )
        val passwordForm = composeTestRule.onNode(
            hasTestTag("passwordForm"),
            useUnmergedTree = true
        )
        loginForm.performTextInput("admin")
        passwordForm.performTextInput("admin")
        signInButton.performClick()

        val instructButton = composeTestRule.onNodeWithTag("instrButton")
        instructButton.performClick()

        val table = composeTestRule.onNode(
            hasTestTag("table"),
            useUnmergedTree = true
        )
        table.assertIsDisplayed()
    }

    @Test
    fun test_menu_opening() {
        val signInButton = composeTestRule.onNode(
            hasTestTag("signInButton"),
            useUnmergedTree = true
        )
        val loginForm = composeTestRule.onNode(
            hasTestTag("loginForm"),
            useUnmergedTree = true
        )
        val passwordForm = composeTestRule.onNode(
            hasTestTag("passwordForm"),
            useUnmergedTree = true
        )
        loginForm.performTextInput("admin")
        passwordForm.performTextInput("admin")
        signInButton.performClick()

        val instructButton = composeTestRule.onNodeWithTag("instrButton")
        instructButton.assertIsDisplayed()
    }

    @Test
    fun test_logs_opening() {
        val signInButton = composeTestRule.onNode(
            hasTestTag("signInButton"),
            useUnmergedTree = true
        )
        val loginForm = composeTestRule.onNode(
            hasTestTag("loginForm"),
            useUnmergedTree = true
        )
        val passwordForm = composeTestRule.onNode(
            hasTestTag("passwordForm"),
            useUnmergedTree = true
        )
        loginForm.performTextInput("admin")
        passwordForm.performTextInput("admin")
        signInButton.performClick()

        val logsButton = composeTestRule.onNodeWithTag("logsButton")
        logsButton.assertIsDisplayed()
    }
}