package com.bitflippin.andsoforth.analysis

import org.antlr.v4.runtime.ParserRuleContext

class Scope(private val parent: Scope? = null) {

    private val symbolTable = HashMap<String, ParserRuleContext>()

    fun define(definiendum: String, definiens: ParserRuleContext) {
        if (symbolTable.containsKey(definiendum)) throw IllegalArgumentException("Duplicate definition")
        else symbolTable[definiendum] = definiens
    }

    fun resolveGlobally(definiendum: String): ParserRuleContext? =
        resolveLocally(definiendum) ?:
        parent?.resolveGlobally(definiendum)

    fun resolveLocally(definiendum: String): ParserRuleContext? =
        symbolTable[definiendum]
}