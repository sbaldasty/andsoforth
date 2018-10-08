package com.bitflippin.andsoforth.analysis

import com.bitflippin.andsoforth.AndSoForthParser.CommandContext
import com.bitflippin.andsoforth.AndSoForthParser.DataCommandContext
import com.bitflippin.andsoforth.AndSoForthParser.IdentifierCommandContext
import com.bitflippin.andsoforth.AndSoForthParser.MacroContext
import com.bitflippin.andsoforth.AndSoForthParser.RootContext
import com.bitflippin.andsoforth.domain.Command
import com.bitflippin.andsoforth.domain.DataCommand

val MacroContext.commandContexts: List<CommandContext>
    get() = commandBlock().command()

val MacroContext.name: String
    get() = Identifier().text

val RootContext.macroContexts: List<MacroContext>
    get() = macro()

fun CommandContext.toCommand(environment: Environment): Command =
    identifierCommand()?.toCommand(environment) ?:
    dataCommand()?.toCommand(environment) ?:
    throw IllegalStateException()

fun DataCommandContext.toCommand(environment: Environment): Command =
    DataCommand(command().toCommands(environment))

fun IdentifierCommandContext.toCommand(environment: Environment): Command =
    environment.macros.getValue(this.text)

fun List<CommandContext>.toCommands(environment: Environment): List<Command> =
    map { it.toCommand(environment) }