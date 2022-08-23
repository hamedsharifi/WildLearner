package com.wildlearner.core

import com.wildlearner.core.model.SearchModel
import com.wildlearner.core.repository.SearchRepository
import com.wildlearner.core.usecase.SearchUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import ir.haytech.core.testutils.MainCoroutineRuleCore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import org.junit.After
import org.junit.Test

import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
class SearchUseCaseTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRuleCore()

    @RelaxedMockK
    lateinit var repository: SearchRepository

    @RelaxedMockK
    lateinit var searchModel: SearchModel

    private fun createUseCase() = SearchUseCase(
        repository = repository
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when search elun+mask as search query`() =
        mainCoroutineRule.runBlockingTest {
            val useCase = createUseCase()

            coEvery {
                delay(100)
                repository.googleSearch("elun+mask")
            } coAnswers { searchModel }

            val response = useCase.execute("elun+mask")

            delay(200)

            coVerify(exactly = 1) {
                repository.googleSearch("elun+mask")
            }
            kotlin.test.assertEquals(searchModel, response)
        }

}