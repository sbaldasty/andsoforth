package com.bitflippin.andsoforth.analysis

import com.bitflippin.andsoforth.AndSoForthLexer
import com.bitflippin.andsoforth.AndSoForthParser
import com.bitflippin.andsoforth.AndSoForthParser.RootContext
import com.bitflippin.andsoforth.error.AndSoForthError
import com.bitflippin.andsoforth.error.SyntaxError
import org.antlr.v4.runtime.BaseErrorListener
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.RecognitionException
import org.antlr.v4.runtime.Recognizer
import java.io.File

class Resource(private val file: File) {

    lateinit var root: RootContext

    fun performSyntacticAnalysis(): List<AndSoForthError> {
        val result = ArrayList<AndSoForthError>()
        val charStream = CharStreams.fromFileName(file.path)
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

    fun performSemanticAnalysis(environment: Environment) {
        root.macroContexts.forEach { environment.define(it.name, it) }
    }
}