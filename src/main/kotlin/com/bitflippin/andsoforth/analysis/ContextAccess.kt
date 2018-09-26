package com.bitflippin.andsoforth.analysis

import com.bitflippin.andsoforth.AndSoForthParser.MacroContext
import com.bitflippin.andsoforth.AndSoForthParser.RootContext

val MacroContext.name: String
    get() = Identifier().text

val RootContext.macroContexts: List<MacroContext>
    get() = macro()