package com.bitflippin.andsoforth.analysis

import org.antlr.v4.runtime.ParserRuleContext

class Environment : Scope() {
    val contextScopes = HashMap<ParserRuleContext, Scope>()
    val semanticBindings = HashMap<ParserRuleContext, Any>()
}