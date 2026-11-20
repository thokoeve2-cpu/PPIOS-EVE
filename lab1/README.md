# Laboratory Work 1 – OOP Fundamentals

## Variant 1.4.2 – Unordered Multiset

### 1. Project Description

This project implements an unordered multiset using C++. A multiset is a collection of elements in which duplicates are allowed. Unlike an ordinary set, the same element can appear more than once.

The project demonstrates object-oriented programming principles, including encapsulation, constructors, copy operations, operator overloading, and separation of the class implementation from the user interface.

### 2. Project Files

- `CantorSet.h` and `CantorSet.cpp` – declaration and implementation of the Cantor Set class.
- `CantorMultiset.h` and `CantorMultiset.cpp` – declaration and implementation of the Multiset class.
- `Lab1_SetAndMultiset.cpp` – console application and user menu.
- `Lab1_Tests_Mac.cpp` – portable test program for macOS.
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

Open the `lab1` project folder in the terminal.

Compile the application:

```bash
clang++ -std=c++17 -Wall -Wextra Lab1_SetAndMultiset.cpp CantorSet.cpp CantorMultiset.cpp -o Lab1App
```

Run the application:

```bash
./Lab1App
```

### 6. Tests

The portable test program uses a custom test-checking harness. It can be compiled on macOS using:

```bash
clang++ -std=c++17 -Wall -Wextra Lab1_Tests_Mac.cpp CantorMultiset.cpp -o Lab1Tests
```

Run the tests:

```bash
./Lab1Tests
```

Latest locally reported test result:

- Checks passed: 56
- Checks failed: 0
- Total checks: 56

**Note:** These are checks implemented in the project's own test harness; they are not tests run through a third-party unit-testing framework.

### 7. Continuous Integration and Code Coverage

The GitHub Actions workflow builds the application and test program, runs the tests, checks line coverage, and performs static analysis.

Latest verified GitHub Actions line-coverage result:

- Line coverage: **93.94%**
- Required minimum: 90%

The workflow fails the coverage step if line coverage is below 90%.

### 8. Example Input

An example multiset containing repeated and nested elements is:

`{a, a, c, {a, b, b}, {}, {a, {c, c}}}`

### 9. Development Environment

- Operating system: macOS
- Programming language: C++
- C++ standard: C++17
- Compiler: Apple Clang
- IDE: Visual Studio Code
