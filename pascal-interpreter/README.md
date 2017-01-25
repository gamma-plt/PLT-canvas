# Pascal Interpreter

This is an implementation of a very simple interpreter implemented in Java following the [@rspivak's tutorial](https://ruslanspivak.com/lsbasi-part1/). The programming language that we're building the interpreter for is the magnificent [Pascal programming language](https://en.wikipedia.org/wiki/Pascal_(programming_language)), designed by Niklaus Wirth. It was one of the first languages to compile to an intermediate representation (bytecode). Here we are building an AST interpreter, not a compiler. 

### Contents
* [Part 1](https://github.com/gamma-plt/PLT-canvas/tree/master/pascal-interpreter/part01) A simple interpreter for one digit and one operator calculations. A lexer, a parser and the REPL of the interpreter.
* [Part 2](https://github.com/gamma-plt/PLT-canvas/tree/master/pascal-interpreter/part02) Extending the code to handle more than one digit integers, supporting other operators (`+`, `-`, `*`, `/`), and handling more than one operator per expression.