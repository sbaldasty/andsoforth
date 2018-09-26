package com.bitflippin.andsoforth.analysis

class Project(private val sources: List<Resource>) {

    fun build(): Environment {
        val result = Environment()
        sources.forEach { it.performSyntacticAnalysis() }
        sources.forEach { it.performSemanticAnalysis(result) }
        return result
    }
}