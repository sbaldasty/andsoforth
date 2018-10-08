package com.bitflippin.andsoforth.analysis

import com.bitflippin.andsoforth.domain.DataCommand
import org.junit.Assert.assertEquals
import org.junit.Test

class MacroAnalysisTest {

    /**
     * Macros may contain no commands.
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
     * Two macros may contain references to each other.
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
     * A macro may contain multiple commands.
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
     * A macro can contain a command that references itself.
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

    /**
     * A macro may push commands onto the data stack.
     */
    @Test
    fun dataCommand() {
        val code = "/macro Macro /begin (Macro) /end"
        val environment = environment(code)
        // The model exists, and has a push command
        val macro = environment.macros.getValue("Macro")
        val push = macro.commands[0] as DataCommand
        // And the data being pushed is the macro
        assertEquals(listOf(macro), push.data)
    }

    /**
     * A macro may push commands onto the data stack that push commands onto
     * the data stack.
     */
    @Test
    fun dataCommandData() {
        val code = "/macro Macro /begin ((Macro)) /end"
        val environment = environment(code)
        // The model exists, and has a push command
        val macro = environment.macros.getValue("Macro")
        val outerPush = macro.commands[0] as DataCommand
        // And the data being pushed is another push command
        val innerPush = outerPush.data[0] as DataCommand
        // That pushes the macro
        assertEquals(listOf(macro), innerPush.data)
    }

    /**
     * A macro may push multiple commands onto the stack.
     */
    @Test
    fun multipleData() {
        val code = "/macro M /begin (X Y) /macro X /begin /end /macro Y /begin /end"
        val environment = environment(code)
        // The model exists, and has a push command
        val macro = environment.macros.getValue("M")
        val push = macro.commands[0] as DataCommand
        // And the data being pushed is another push command
        val x = environment.macros.getValue("X")
        val y = environment.macros.getValue("Y")
        assertEquals(listOf(x, y), push.data)
    }
}