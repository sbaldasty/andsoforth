package com.bitflippin.andsoforth.analysis

import com.bitflippin.andsoforth.AndSoForthLexer
import com.bitflippin.andsoforth.AndSoForthParser
import com.bitflippin.andsoforth.AndSoForthParser.RootContext
import org.antlr.v4.runtime.ANTLRErrorListener
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.io.File

class Resource(private val file: File) {

    lateinit var root: RootContext

    fun performSyntacticAnalysis() {
        val charStream = CharStreams.fromFileName(file.path)
        val lexer = AndSoForthLexer(charStream)
        val tokenStream = CommonTokenStream(lexer)
        val parser = AndSoForthParser(tokenStream)
        root = parser.root()
    }

    fun performSemanticAnalysis(environment: Environment) {
        root.macroContexts.forEach { environment.define(it.name, it) }
    }
}