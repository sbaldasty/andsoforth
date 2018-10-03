package com.bitflippin.andsoforth.analysis

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class MacroTest {

    /**
     * An appropriate model is constructed for a macro that has no commands
     * in it.
     */
    @Test
    fun empty() {
        val code = "/macro EmptyMacro /begin /end"
        val environment = environment(code)
        // The model exists
        val macro = environment.macros.getValue("EmptyMacro")
        // And has no commands in it
        assertEquals(emptyList<Any>(), macro.commands)
    }

    /**
     * Appropriate models are constructed for two macros that reference each
     * other.
     */
    @Test
    fun circular() {
        val code = "/macro Me /begin You /end /macro You /begin Me /end"
        val environment = environment(code)
        // The models exist
        val meMacro = environment.macros.getValue("Me")
        val youMacro = environment.macros.getValue("You")
        // And contain each other as commands
        assertEquals(listOf(meMacro), youMacro.commands)
        assertEquals(listOf(youMacro), meMacro.commands)
    }
}