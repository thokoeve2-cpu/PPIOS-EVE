#include "CantorMultiset.h"

#include <algorithm>
#include <cctype>
#include <functional>
#include <map>
#include <utility>

// Remove unnecessary spaces from an element
std::string CantorMultiset::trim(const std::string& text) {
    size_t start = 0;
    size_t end = text.size();

    while (start < end &&
           std::isspace(static_cast<unsigned char>(text[start]))) {
        start++;
    }

    while (end > start &&
           std::isspace(static_cast<unsigned char>(text[end - 1]))) {
        end--;
    }

    return text.substr(start, end - start);
}

// Default constructor
CantorMultiset::CantorMultiset() = default;

// Copy constructor
CantorMultiset::CantorMultiset(const CantorMultiset& other)
    : elements(other.elements) {
}

// Copy assignment operator
CantorMultiset& CantorMultiset::operator=(const CantorMultiset& other) {
    if (this != &other) {
        elements = other.elements;
    }

    return *this;
}

// Check whether the multiset is empty
bool CantorMultiset::isEmpty() const {
    return elements.empty();
}

// Return the total number of elements, including duplicates
size_t CantorMultiset::cardinality() const {
    return elements.size();
}

// Add an element
void CantorMultiset::add(const std::string& item) {
    elements.insert(trim(item));
}

// Remove one occurrence of an element
bool CantorMultiset::remove(const std::string& item) {
    auto it = elements.find(trim(item));

    if (it == elements.end()) {
        return false;
    }

    elements.erase(it);
    return true;
}
// Check whether an element exists
bool CantorMultiset::contains(const std::string& item) const {
    return elements.find(trim(item)) != elements.end();
}

// Check membership using the [] operator
bool CantorMultiset::operator[](const std::string& item) const {
    return contains(item);
}

// Multiset union: add the multiplicities
CantorMultiset CantorMultiset::operator+(
    const CantorMultiset& other
) const {
    CantorMultiset result = *this;

    result.elements.insert(
        other.elements.begin(),
        other.elements.end()
    );

    return result;
}

// Multiset intersection: keep the minimum multiplicity
CantorMultiset CantorMultiset::operator*(
    const CantorMultiset& other
) const {
    CantorMultiset result;

    for (const auto& item : elements) {
        if (result.elements.count(item) < other.elements.count(item)) {
            result.elements.insert(item);
        }
    }

    return result;
}

// Multiset difference: subtract multiplicities
CantorMultiset CantorMultiset::operator-(
    const CantorMultiset& other
) const {
    CantorMultiset result;

    std::map<std::string, size_t> remaining;

    for (const auto& item : elements) {
        remaining[item]++;
    }

    for (const auto& item : other.elements) {
        auto it = remaining.find(item);

        if (it != remaining.end() && it->second > 0) {
            it->second--;
        }
    }

    for (const auto& entry : remaining) {
        for (size_t i = 0; i < entry.second; i++) {
            result.add(entry.first);
        }
    }

    return result;
}

// Union assignment
CantorMultiset& CantorMultiset::operator+=(
    const CantorMultiset& other
) {
    *this = *this + other;
    return *this;
}

// Intersection assignment
CantorMultiset& CantorMultiset::operator*=(
    const CantorMultiset& other
) {
    *this = *this * other;
    return *this;
}

// Difference assignment
CantorMultiset& CantorMultiset::operator-=(
    const CantorMultiset& other
) {
    *this = *this - other;
    return *this;
}

// Equality
bool CantorMultiset::operator==(
    const CantorMultiset& other
) const {
    return elements == other.elements;
}

// Inequality
bool CantorMultiset::operator!=(
    const CantorMultiset& other
) const {
    return !(*this == other);
}

// Generate all distinct sub-multisets
std::vector<CantorMultiset> CantorMultiset::powerSet() const {
    std::vector<CantorMultiset> results;

    std::map<std::string, size_t> frequencies;

    for (const auto& item : elements) {
        frequencies[item]++;
    }

    std::vector<std::pair<std::string, size_t>> groups(
        frequencies.begin(),
        frequencies.end()
    );

    std::function<void(size_t, CantorMultiset)> generate =
        [&](size_t index, CantorMultiset current) {
            if (index == groups.size()) {
                results.push_back(current);
                return;
            }

            const std::string& item = groups[index].first;
            size_t frequency = groups[index].second;

            for (size_t count = 0; count <= frequency; count++) {
                generate(index + 1, current);

                current.add(item);
            }
        };

    generate(0, CantorMultiset());

    return results;
}

// Parse a multiset string, preserving nested elements
void CantorMultiset::parseFromString(const std::string& input) {
    elements.clear();

    std::string text = trim(input);

    if (text.empty() ||
        text.front() != '{' ||
        text.back() != '}') {
        return;
    }

    text = text.substr(1, text.size() - 2);

    std::string current;
    int depth = 0;

    for (char ch : text) {
        if (ch == '{') {
            depth++;
            current += ch;
        }
        else if (ch == '}') {
            depth--;

            if (depth < 0) {
                elements.clear();
                return;
            }

            current += ch;
        }
        else if (ch == ',' && depth == 0) {
            std::string item = trim(current);

            if (!item.empty()) {
                elements.insert(item);
            }

            current.clear();
        }
        else {
            current += ch;
        }
    }

    if (depth != 0) {
        elements.clear();
        return;
    }

    std::string finalItem = trim(current);

    if (!finalItem.empty()) {
        elements.insert(finalItem);
    }
}

// Output operator
std::ostream& operator<<(
    std::ostream& os,
    const CantorMultiset& s
) {
    os << "{ ";

    for (const auto& item : s.elements) {
        os << item << " ";
    }

    os << "}";

    return os;
}

// Input operator
std::istream& operator>>(
    std::istream& is,
    CantorMultiset& s
) {
    std::string input;

    std::getline(is, input);

    if (is) {
        s.parseFromString(input);
    }

    return is;
}