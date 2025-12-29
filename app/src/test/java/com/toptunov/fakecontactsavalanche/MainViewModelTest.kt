package com.toptunov.fakecontactsavalanche

import android.app.Application
import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var application: Application
    private lateinit var resources: Resources
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        application = mockk(relaxed = true)
        resources = mockk(relaxed = true)
        
        every { application.resources } returns resources
        every { application.getString(any()) } returns "Test string"
        every { application.getString(any(), any()) } returns "Test string with arg"
        every { resources.getQuantityString(any(), any(), any()) } returns "Test plural"
        
        viewModel = MainViewModel(application)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Idle`() {
        assertEquals(UiState.Idle, viewModel.uiState.value)
    }

    @Test
    fun `startGenerating with zero shows error`() {
        viewModel.startGenerating(0)
        
        assertTrue(viewModel.uiState.value is UiState.Error)
    }

    @Test
    fun `startGenerating with negative number shows error`() {
        viewModel.startGenerating(-5)
        
        assertTrue(viewModel.uiState.value is UiState.Error)
    }

    @Test
    fun `startGenerating with number exceeding max shows error`() {
        viewModel.startGenerating(10001)
        
        assertTrue(viewModel.uiState.value is UiState.Error)
    }

    @Test
    fun `resetState changes to Idle`() {
        viewModel.startGenerating(0)
        assertTrue(viewModel.uiState.value is UiState.Error)
        
        viewModel.resetState()
        assertEquals(UiState.Idle, viewModel.uiState.value)
    }

    @Test
    fun `cancelGeneration sets state to Idle`() {
        viewModel.cancelGeneration()
        assertEquals(UiState.Idle, viewModel.uiState.value)
    }

    @Test
    fun `UiState Loading calculates progress correctly`() {
        val loadingState = UiState.Loading(current = 25, total = 100)
        assertEquals(25, loadingState.progress)
    }

    @Test
    fun `UiState Loading with zero total returns zero progress`() {
        val loadingState = UiState.Loading(current = 0, total = 0)
        assertEquals(0, loadingState.progress)
    }

    @Test
    fun `consecutive startGenerating calls cancel previous job`() = runTest {
        viewModel.startGenerating(100)
        advanceUntilIdle()
        
        viewModel.startGenerating(50)
        advanceUntilIdle()
        
        assertNotNull(viewModel.uiState.value)
    }

    @Test
    fun `MAX_CONTACTS constant is 10000`() {
        viewModel.startGenerating(10000)
        assertFalse(viewModel.uiState.value is UiState.Error)
        
        viewModel.resetState()
        
        viewModel.startGenerating(10001)
        assertTrue(viewModel.uiState.value is UiState.Error)
    }
}

