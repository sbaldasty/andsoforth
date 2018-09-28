package com.bitflippin.andsoforth.analysis

import java.io.File

fun project(vararg names: String) = Project(names.map {
    val path = "src/test/resources/andsoforth/$it.andsoforth"
    Resource(File(path))
})