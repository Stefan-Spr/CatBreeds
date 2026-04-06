package com.example.catbreeds

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.catbreeds.presentation.CatBreedsActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CatBreedsActivityTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<CatBreedsActivity>()

    private lateinit var breedListTag: String
    private lateinit var breedCardPrefix: String
    private lateinit var breedDetailTag: String
    private lateinit var searchScreenTag: String
    private lateinit var searchBarTag: String
    private lateinit var searchButtonTag: String

    @Before
    fun setup() {
        hiltRule.inject()
        val context = composeTestRule.activity
        breedListTag = context.getString(R.string.test_tag_breed_list)
        breedCardPrefix = context.getString(R.string.test_tag_breed_card_prefix)
        breedDetailTag = context.getString(R.string.test_tag_breed_detail_screen)
        searchScreenTag = context.getString(R.string.test_tag_search_screen)
        searchBarTag = context.getString(R.string.test_tag_search_bar)
        searchButtonTag = context.getString(R.string.test_tag_search_button)
    }

    @Test
    fun testNavigationToDetailAndBack() {
        // 1. Check if we are on the Breed List Screen
        composeTestRule.onNodeWithTag(breedListTag).assertExists()

        // 2. Click on the first breed item (Assuming at least one is loaded)
        composeTestRule.onAllNodes(hasTestTagPrefix(breedCardPrefix))
            .onFirst()
            .performClick()

        // 3. Verify Navigation to Detail Screen
        composeTestRule.onNodeWithTag(breedDetailTag).assertExists()

        // 4. Test Back Navigation (System Back)
        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        // 5. Verify we are back on the List Screen
        composeTestRule.onNodeWithTag(breedListTag).assertExists()
    }

    @Test
    fun testNavigationToSearchAndPerformQuery() {
        // 1. Check if we are on the Breed List Screen
        composeTestRule.onNodeWithTag(breedListTag).assertExists()

        // 2. Click on the search button in the header
        composeTestRule.onNodeWithTag(searchButtonTag).performClick()

        // 3. Verify Navigation to Search Screen
        composeTestRule.onNodeWithTag(searchScreenTag).assertExists()

        // 4. Enter a query in the Search Bar
        val query = "Abys"
        composeTestRule.onNodeWithTag(searchBarTag).performTextInput(query)

        // 5. Verify suggestions are shown (Assuming "Abyssinian" exists in data)
        composeTestRule.onNodeWithText("Abyssinian", ignoreCase = true).assertExists()

        // 6. Click on a suggestion
        composeTestRule.onNodeWithText("Abyssinian", ignoreCase = true).performClick()

        composeTestRule.waitForIdle()

        // 7. Verify back navigation
        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        composeTestRule.onNodeWithTag(breedListTag).assertExists()
    }

    private fun hasTestTagPrefix(prefix: String) = SemanticsMatcher("testTag starts with $prefix") {
        it.config.getOrNull(SemanticsProperties.TestTag)?.startsWith(prefix) == true
    }
}
