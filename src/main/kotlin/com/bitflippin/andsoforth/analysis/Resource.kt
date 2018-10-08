package com.bitflippin.andsoforth.analysis

import com.bitflippin.andsoforth.AndSoForthLexer
import com.bitflippin.andsoforth.AndSoForthParser
import com.bitflippin.andsoforth.AndSoForthParser.RootContext
import com.bitflippin.andsoforth.domain.Macro
import com.bitflippin.andsoforth.error.AndSoForthError
import com.bitflippin.andsoforth.error.SyntaxError
import org.antlr.v4.runtime.BaseErrorListener
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.RecognitionException
import org.antlr.v4.runtime.Recognizer

class Resource(private val sourceCode: String) {

    lateinit var root: RootContext

    fun parse(): List<AndSoForthError> {
        val result = ArrayList<AndSoForthError>()
        val charStream = CharStreams.fromString(sourceCode)
        val lexer = AndSoForthLexer(charStream)
        lexer.addErrorListener(object : BaseErrorListener() {

            override fun syntaxError(
                    recognizer: Recognizer<*, *>,
                    offendingSymbol: Any?,
                    line: Int,
                    characterPosition: Int,
                    message: String,
                    exception: RecognitionException) {

                result += SyntaxError(this@Resource, line, characterPosition, message)
            }
        })
        val tokenStream = CommonTokenStream(lexer)
        val parser = AndSoForthParser(tokenStream)
        root = parser.root()
        return result
    }

    fun createObjects(targetEnvironment: Environment) {
        root.macroContexts.forEach {
            targetEnvironment.globalScope.define(it.name, it)
            targetEnvironment.macros[it.name] = Macro()
        }
    }

    fun populateObjects(targetEnvironment: Environment) {
        root.macroContexts.forEach { macroContext ->
            val macro = targetEnvironment.macros[macroContext.name] ?: throw IllegalStateException()
            macro.commands += macroContext.commandContexts.toCommands(targetEnvironment)
        }
    }
}