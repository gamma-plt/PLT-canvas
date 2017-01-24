# Simple C Compiler: 
## The simplest functional compiler one can write. 

This is a compiler/interpreter and a virtual machine for a reduced subset of the C programming language. The development is in Java, and its structure resembles the phases of a classic compiler (without optimisation). It's objetive is to illustrate the development of simple lexers, parsers, code-generators, and the structure of the main data structures that deals with compiler design and implementation. The implemented language, consists of the following features:

* SimpleC support `int` and `char` datatypes.
* It doesn't have explicit type annotations. Variables must be declared and instantiated as in Python.
* It supports iteration (`while`), branching (`if`, `else`), console output (`print`, `putc`).
* SimpleC supports arithmetic (`*`, `+`, `-`, `/`, `%`), relational (`<`, `<=`, `>`, `>=`, `!=`, `==`), and boolean (`&&`, `!`, `||`) operators.
