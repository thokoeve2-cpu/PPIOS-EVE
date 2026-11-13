#include <iostream>
#include <sstream>
#include <string>
#include <vector>

#include "CantorMultiset.h"

using namespace std;

int testsPassed = 0;
int testsFailed = 0;

#define CHECK(condition) do { \
    if (condition) { \
        testsPassed++; \
    } else { \
        testsFailed++; \
        cout << "FAILED: " << #condition \
             << " (line " << __LINE__ << ")\n"; \
    } \
} while (false)

void testConstructorsAndBasicOperations() {
    CantorMultiset m;

    CHECK(m.isEmpty());
    CHECK(m.cardinality() == 0);

    m.add("a");
    m.add("a");
    m.add("b");

    CHECK(!m.isEmpty());
    CHECK(m.cardinality() == 3);
    CHECK(m.contains("a"));
    CHECK(m.contains("b"));
    CHECK(!m.contains("c"));

    CHECK(m["a"]);
CHECK(m["b"]);
CHECK(!m["c"]);

    CHECK(m.remove("a"));
    CHECK(m.cardinality() == 2);
    CHECK(m.contains("a"));

    CHECK(m.remove("a"));
    CHECK(!m.contains("a"));
    CHECK(!m.remove("z"));
}

void testCopyAndAssignment() {
    CantorMultiset original;
    original.add("a");
    original.add("b");
    original.add("b");

    CantorMultiset copy(original);

    CHECK(copy == original);
    CHECK(copy.cardinality() == 3);

    CantorMultiset assigned;
    assigned = original;

    CHECK(assigned == original);

    assigned.add("c");

    CHECK(assigned != original);



    CHECK(original.cardinality() == 3);
}

void testEquality() {
    CantorMultiset first;
    CantorMultiset second;

    first.add("a");
    first.add("b");
    first.add("a");

    second.add("b");
    second.add("a");
    second.add("a");

    CHECK(first == second);
    CHECK(!(first != second));

    second.add("c");

    CHECK(first != second);
}

void testUnion() {
    CantorMultiset first;
    CantorMultiset second;

    first.add("a");
    first.add("a");
    first.add("b");

    second.add("a");
    second.add("c");

    CantorMultiset result = first + second;

    CHECK(result.cardinality() == 5);
    CHECK(result.contains("a"));
    CHECK(result.contains("b"));
    CHECK(result.contains("c"));

    first += second;

    CHECK(first == result);
}

void testIntersection() {
    CantorMultiset first;
    CantorMultiset second;

    first.add("a");
    first.add("a");
    first.add("b");

    second.add("a");
    second.add("b");
    second.add("b");

    CantorMultiset result = first * second;

    CHECK(result.cardinality() == 2);
    CHECK(result.contains("a"));
    CHECK(result.contains("b"));

    first *= second;

    CHECK(first == result);
}

void testDifference() {
    CantorMultiset first;
    CantorMultiset second;

    first.add("a");
    first.add("a");
    first.add("a");
    first.add("b");

    second.add("a");
    second.add("b");
    second.add("c");

    CantorMultiset result = first - second;

    CHECK(result.cardinality() == 2);
    CHECK(result.contains("a"));
    CHECK(!result.contains("b"));

    first -= second;

    CHECK(first == result);
}

void testPowerSet() {
    CantorMultiset empty;
    vector<CantorMultiset> emptyPower = empty.powerSet();

    CHECK(emptyPower.size() == 1);
    CHECK(emptyPower[0].isEmpty());

    CantorMultiset m;
    m.add("a");
    m.add("a");
    m.add("b");

    vector<CantorMultiset> subsets = m.powerSet();

    CHECK(subsets.size() == 6);

    bool foundEmpty = false;
    bool foundFull = false;

    for (const auto& subset : subsets) {
        if (subset.isEmpty()) {
            foundEmpty = true;
        }

        if (subset == m) {
            foundFull = true;
        }
    }

    CHECK(foundEmpty);
    CHECK(foundFull);
}

void testStringParsing() {
    CantorMultiset m;

    m.parseFromString("{a, a, b}");

    CHECK(m.cardinality() == 3);
    CHECK(m.contains("a"));
    CHECK(m.contains("b"));

    m.parseFromString("{a, a, c, {a, b, b}, {}, {a, {c, c}}}");

    CHECK(m.cardinality() == 6);
    CHECK(m.contains("{a, b, b}"));
    CHECK(m.contains("{}"));
    CHECK(m.contains("{a, {c, c}}"));
}

void testStreamOperators() {
    CantorMultiset original;
    original.add("a");
    original.add("a");
    original.add("b");

    stringstream output;
    output << original;

    CHECK(!output.str().empty());

    CantorMultiset loaded;
    stringstream input("{x, x, y}");

    input >> loaded;

    CHECK(loaded.cardinality() == 3);
    CHECK(loaded.contains("x"));
    CHECK(loaded.contains("y"));
}

void testAssignmentOperators() {
    CantorMultiset first;
    CantorMultiset second;

    first.add("a");
    first.add("b");

    second.add("c");

    CantorMultiset expectedUnion = first + second;
    CantorMultiset expectedIntersection = first * second;
    CantorMultiset expectedDifference = first - second;

    first += second;
    CHECK(first == expectedUnion);

    first = expectedUnion;
    first *= second;
  CHECK(first == second);

    first = expectedUnion;
    first -= second;
    CHECK(first == expectedDifference);
}

int main() {
    cout << "=================================\n";
    cout << "     MULTISET UNIT TESTS\n";
    cout << "=================================\n";

    testConstructorsAndBasicOperations();
    testCopyAndAssignment();
    testEquality();
    testUnion();
    testIntersection();
    testDifference();
    testPowerSet();
    testStringParsing();
    testStreamOperators();
    testAssignmentOperators();

    cout << "\n=================================\n";
    cout << "Tests passed: " << testsPassed << endl;
    cout << "Tests failed: " << testsFailed << endl;
    cout << "Total tests:  " << testsPassed + testsFailed << endl;
    cout << "=================================\n";

    return testsFailed == 0 ? 0 : 1;
}