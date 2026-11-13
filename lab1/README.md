# Laboratory Work 1 – OOP Fundamentals

## Variant 1.4.2 – Unordered Multiset

### 1. Project Description

This project implements an unordered multiset using C++. A multiset is a collection of elements in which duplicates are allowed. Unlike an ordinary set, the same element can appear more than once.

The project demonstrates object-oriented programming principles, including encapsulation, constructors, copy operations, operator overloading, and separation of the class implementation from the user interface.

### 2. Project Files

- `CantorSet.h` and `CantorSet.cpp` – declaration and implementation of the Cantor Set class.
- `CantorMultiset.h` and `CantorMultiset.cpp` – declaration and implementation of the Multiset class.
- `Lab1_SetAndMultiset.cpp` – console application and user menu.
- `Lab1_Tests_Mac.cpp` – portable unit test program for macOS.
- `README.md` – project description and instructions.

### 3. Implemented Multiset Operations

The Multiset class supports:

- Creating an empty multiset.
- Adding elements, including duplicates.
- Removing one occurrence of an element.
- Checking whether an element exists.
- Checking whether the multiset is empty.
- Calculating cardinality.
- Union using `+` and `+=`.
- Intersection using `*` and `*=`.
- Difference using `-` and `-=`.
- Membership checking using `contains()` and `[]`.
- Comparing two multisets using `==` and `!=`.
- Creating a copy using the copy constructor and assignment operator.
- Generating the power set.
- Reading and writing multisets through stream operators.
- Parsing a multiset from a string, including nested brace expressions.

### 4. Multiset Rules

Duplicates are preserved.

For example:

`{a, a, b}`

has cardinality 3.

The implemented operations use the following multiplicity rules:

- Union adds the number of occurrences.
- Intersection keeps the minimum number of occurrences.
- Difference subtracts occurrences, without allowing negative multiplicities.

### 5. Compilation and Execution

Open the project folder in the terminal.

Compile the application:

```bash
clang++ -std=c++17 -Wall -Wextra Lab1_SetAndMultiset.cpp CantorSet.cpp CantorMultiset.cpp -o Lab1App
```

Run the application:

```bash
./Lab1App
```

### 6. Unit Testing

The portable test program can be compiled on macOS using:

```bash
clang++ -std=c++17 -Wall -Wextra Lab1_Tests_Mac.cpp CantorMultiset.cpp -o Lab1Tests
```

Run the tests:

```bash
./Lab1Tests
```

Latest test result:

- Tests passed: 56
- Tests failed: 0
- Total tests: 56

### 7. Code Coverage

Code coverage was measured using the LLVM coverage tools included with Xcode Command Line Tools.

Compile with coverage instrumentation:

```bash
clang++ -std=c++17 -O0 -fprofile-instr-generate -fcoverage-mapping Lab1_Tests_Mac.cpp CantorMultiset.cpp -o Lab1Tests
```

Run the tests and generate profiling data:

```bash
LLVM_PROFILE_FILE="Lab1Tests.profraw" ./Lab1Tests
```

Merge the profiling data:

```bash
xcrun llvm-profdata merge -sparse Lab1Tests.profraw -o Lab1Tests.profdata
```

Display the coverage report:

```bash
xcrun llvm-cov report ./Lab1Tests -instr-profile=Lab1Tests.profdata
```

Latest coverage results for `CantorMultiset.cpp`:

- Line coverage: 94.92%
- Region coverage: 95.51%
- Function coverage: 100%
- Branch coverage: 79.69%

The implementation exceeds the required 90% line coverage.

### 8. Example Input

An example multiset containing repeated and nested elements is:

`{a, a, c, {a, b, b}, {}, {a, {c, c}}}`

### 9. Development Environment

- Operating system: macOS Ventura 13.7.8
- Programming language: C++
- C++ standard: C++17
- Compiler: Apple Clang
- IDE: Visual Studio Code
