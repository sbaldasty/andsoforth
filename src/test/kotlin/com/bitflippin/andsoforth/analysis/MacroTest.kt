package com.bitflippin.andsoforth.analysis

import org.junit.Assert
import org.junit.Test
import java.io.File

class MacroTest {

    /**
     * A source file containing just an empty macro can be loaded, and the
     * macro can be resolved.
     */
    @Test
    fun loadsEmptyMacro() {
        val environment = load("EmptyMacro")
        Assert.assertNotNull(environment.resolveLocally("EmptyMacro"))
    }

    private fun load(vararg names: String) = Project(names.map {
        val path = "src/test/resources/andsoforth/$it.andsoforth"
        Resource(File(path))
    }).build()
}