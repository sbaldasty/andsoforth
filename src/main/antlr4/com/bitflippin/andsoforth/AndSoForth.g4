grammar AndSoForth;

command
  : dataCommand
  | identifierCommand
  ;

commandBlock
  : BeginKeyword command* EndKeyword
  ;

dataCommand
  : OpenParen command* CloseParen
  ;

identifierCommand
  : Identifier
  ;

macro
  : MacroKeyword Identifier commandBlock
  ;

root
  : macro*
  ;

Identifier
  : ('A'..'Z' | 'a'..'z')+
  ;

BeginKeyword
  : '/begin'
  ;

CloseParen
  : ')'
  ;

EndKeyword
  : '/end'
  ;

MacroKeyword
  : '/macro'
  ;

OpenParen
  : '('
  ;

Whitespace
  : (' ' | '\r' | '\n' | '\t')+ -> channel(HIDDEN)
  ;
