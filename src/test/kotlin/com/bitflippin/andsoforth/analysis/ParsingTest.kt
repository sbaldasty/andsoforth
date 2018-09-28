package com.bitflippin.andsoforth.analysis

import com.bitflippin.andsoforth.error.AndSoForthError
import com.bitflippin.andsoforth.error.SyntaxError
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ParsingTest {

    /**
     * A project of one error-free source file builds without errors.
     */
    @Test
    fun validSourceCode() {
        val project = project("EmptyMacro")
        project.build()
        assertEquals(ArrayList<AndSoForthError>(), project.errors)
    }

    /**
     * When a source file has an invalid token, an appropriate error is added
     * to the project.
     */
    @Test
    fun invalidToken() {
        val project = project("InvalidTokenError")
        project.build()
        assertEquals(1, project.errors.size)
        val error = project.errors.first() as SyntaxError
        assertEquals(0, error.characterPosition)
        assertEquals(1, error.line)
        assertTrue(error.message.contains("/i"))
    }
}