package com.bitflippin.andsoforth.analysis

import com.bitflippin.andsoforth.domain.Macro
import org.antlr.v4.runtime.ParserRuleContext

class Environment {
    val macros = HashMap<String, Macro>()
    val globalScope = Scope()
    val contextScopes = HashMap<ParserRuleContext, Scope>()
    val semanticBindings = HashMap<ParserRuleContext, Any>()
}