package com.bitflippin.andsoforth.analysis

fun project(sourceCode: String) = Project(listOf(Resource(sourceCode)))

fun environment(sourceCode: String) = project(sourceCode).build()