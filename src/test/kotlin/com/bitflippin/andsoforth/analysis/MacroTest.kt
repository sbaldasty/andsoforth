package com.bitflippin.andsoforth.analysis

import org.junit.Assert.assertNotNull
import org.junit.Test

class MacroTest {

    /**
     * A source file containing just an empty macro can be loaded, and the
     * macro can be resolved.
     */
    @Test
    fun loadsEmptyMacro() {
        val environment = project("EmptyMacro").build()
        assertNotNull(environment.resolveLocally("EmptyMacro"))
    }
}