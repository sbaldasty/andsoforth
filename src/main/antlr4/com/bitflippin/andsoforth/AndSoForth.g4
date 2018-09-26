grammar AndSoForth;

command
  : identifierCommand
  ;

commandBlock
  : BeginKeyword command* EndKeyword
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

EndKeyword
  : '/end'
  ;

MacroKeyword
  : '/macro'
  ;

Whitespace
  : (' ' | '\r' | '\n' | '\t')+ -> channel(HIDDEN)
  ;
