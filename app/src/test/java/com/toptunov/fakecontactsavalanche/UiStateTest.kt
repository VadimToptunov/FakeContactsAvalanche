package com.toptunov.fakecontactsavalanche

import org.junit.Assert.*
import org.junit.Test

class UiStateTest {

    @Test
    fun `Idle state is singleton`() {
        val state1 = UiState.Idle
        val state2 = UiState.Idle
        assertSame(state1, state2)
    }

    @Test
    fun `Loading state calculates progress correctly`() {
        val state1 = UiState.Loading(current = 25, total = 100)
        assertEquals(25, state1.progress)

        val state2 = UiState.Loading(current = 50, total = 100)
        assertEquals(50, state2.progress)

        val state3 = UiState.Loading(current = 100, total = 100)
        assertEquals(100, state3.progress)
    }

    @Test
    fun `Loading state handles zero total gracefully`() {
        val state = UiState.Loading(current = 0, total = 0)
        assertEquals(0, state.progress)
    }

    @Test
    fun `Loading state with partial progress`() {
        val state = UiState.Loading(current = 33, total = 100)
        assertEquals(33, state.progress)
    }

    @Test
    fun `Success state stores message and count`() {
        val state = UiState.Success(message = "Created 10 contacts", count = 10)
        assertEquals("Created 10 contacts", state.message)
        assertEquals(10, state.count)
    }

    @Test
    fun `Warning state stores all fields`() {
        val state = UiState.Warning(
            message = "Partial success",
            successCount = 80,
            totalCount = 100
        )
        assertEquals("Partial success", state.message)
        assertEquals(80, state.successCount)
        assertEquals(100, state.totalCount)
    }

    @Test
    fun `Error state stores message`() {
        val state = UiState.Error(message = "Permission denied")
        assertEquals("Permission denied", state.message)
    }

    @Test
    fun `different Loading states are not equal`() {
        val state1 = UiState.Loading(current = 10, total = 100)
        val state2 = UiState.Loading(current = 20, total = 100)
        assertNotEquals(state1, state2)
    }

    @Test
    fun `same Loading states are equal`() {
        val state1 = UiState.Loading(current = 50, total = 100)
        val state2 = UiState.Loading(current = 50, total = 100)
        assertEquals(state1, state2)
    }

    @Test
    fun `Loading state progress with large batch shows accurate percentages`() {
        val state1 = UiState.Loading(current = 1, total = 10000)
        assertEquals(0, state1.progress)

        val state10 = UiState.Loading(current = 10, total = 10000)
        assertEquals(0, state10.progress)

        val state50 = UiState.Loading(current = 50, total = 10000)
        assertEquals(0, state50.progress)

        val state100 = UiState.Loading(current = 100, total = 10000)
        assertEquals(1, state100.progress)

        val state500 = UiState.Loading(current = 500, total = 10000)
        assertEquals(5, state500.progress)

        val state1000 = UiState.Loading(current = 1000, total = 10000)
        assertEquals(10, state1000.progress)

        val state5000 = UiState.Loading(current = 5000, total = 10000)
        assertEquals(50, state5000.progress)

        val state9999 = UiState.Loading(current = 9999, total = 10000)
        assertEquals(99, state9999.progress)

        val state10000 = UiState.Loading(current = 10000, total = 10000)
        assertEquals(100, state10000.progress)
    }

    @Test
    fun `Loading state progress with small batch shows accurate percentages`() {
        val state1 = UiState.Loading(current = 1, total = 10)
        assertEquals(10, state1.progress)

        val state5 = UiState.Loading(current = 5, total = 10)
        assertEquals(50, state5.progress)

        val state9 = UiState.Loading(current = 9, total = 10)
        assertEquals(90, state9.progress)
    }

    @Test
    fun `Loading state progress never exceeds 100 percent`() {
        val state = UiState.Loading(current = 100, total = 100)
        assertEquals(100, state.progress)
        assertTrue(state.progress <= 100)
    }

    @Test
    fun `Loading state progress rounds down correctly`() {
        val state1 = UiState.Loading(current = 1, total = 3)
        assertEquals(33, state1.progress)

        val state2 = UiState.Loading(current = 2, total = 3)
        assertEquals(66, state2.progress)

        val state3 = UiState.Loading(current = 3, total = 3)
        assertEquals(100, state3.progress)
    }
}

