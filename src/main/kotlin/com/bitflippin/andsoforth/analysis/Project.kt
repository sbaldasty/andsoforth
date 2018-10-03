package com.bitflippin.andsoforth.analysis

import com.bitflippin.andsoforth.error.AndSoForthError

class Project(private val sources: List<Resource>) {

    val errors = ArrayList<AndSoForthError>()

    fun build(): Environment {
        val result = Environment()
        sources.forEach { errors += it.parse() }
        sources.forEach { it.createObjects(result) }
        sources.forEach { it.populateObjects(result) }
        return result
    }
}