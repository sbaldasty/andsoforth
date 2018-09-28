package com.bitflippin.andsoforth.error

import com.bitflippin.andsoforth.analysis.Resource

class SyntaxError(
        val resource: Resource,
        val line: Int,
        val characterPosition: Int,
        val message: String
) : AndSoForthError()