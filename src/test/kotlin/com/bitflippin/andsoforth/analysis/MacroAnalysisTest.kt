package com.bitflippin.andsoforth.analysis

import org.junit.Assert.assertEquals
import org.junit.Test

class MacroAnalysisTest {

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

    /**
     * An appropriate model is constructed for a macro that contains multiple
     * commands.
     */
    @Test
    fun multipleCommands() {
        val code = "/macro MultiMacro /begin X X /end /macro X /begin /end"
        val environment = environment(code)
        // The model exists
        val multiMacro = environment.macros.getValue("MultiMacro")
        val otherMacro = environment.macros.getValue("X")
        // And has the two commands in it
        assertEquals(listOf(otherMacro, otherMacro), multiMacro.commands)
    }

    /**
     * An appropriate model is constructed for a macro that references itself.
     */
    @Test
    fun recursive() {
        val code = "/macro Macro /begin Macro /end"
        val environment = environment(code)
        // The model exists
        val macro = environment.macros.getValue("Macro")
        // And is its own command
        assertEquals(listOf(macro), macro.commands)
    }
}