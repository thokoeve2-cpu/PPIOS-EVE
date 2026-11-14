#include "CantorMultiset.h"

#include <algorithm>
#include <cctype>
#include <map>
#include <sstream>
#include <utility>

// Element containing an ordinary value
CantorMultiset::Element::Element(const std::string& value)
    : atom(value), nested(nullptr) {
}

// Element containing another multiset
CantorMultiset::Element::Element(const CantorMultiset& value)
    : atom(""), nested(std::make_shared<CantorMultiset>(value)) {
}

// Compare elements using their consistent representations
bool CantorMultiset::ElementLess::operator()(
    const Element& a,
    const Element& b
) const {
    return CantorMultiset::elementKey(a) <
           CantorMultiset::elementKey(b);
}

// Remove unnecessary spaces
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

// Create an element from text
CantorMultiset::Element CantorMultiset::makeElement(
    const std::string& item
) {
    std::string text = trim(item);

    if (text.size() >= 2 &&
        text.front() == '{' &&
        text.back() == '}') {
        CantorMultiset nested;
        nested.parseFromString(text);
        return Element(nested);
    }

    return Element(text);
}

// Produce a consistent representation of an element
std::string CantorMultiset::elementKey(const Element& item) {
    if (item.nested) {
        std::ostringstream output;
        output << *item.nested;
        return output.str();
    }

    return item.atom;
}

// Default constructor
CantorMultiset::CantorMultiset() = default;

// Copy constructor
CantorMultiset::CantorMultiset(const CantorMultiset& other) {
    for (const auto& item : other.elements) {
        if (item.nested) {
            elements.insert(Element(*item.nested));
        }
        else {
            elements.insert(Element(item.atom));
        }
    }
}

// Copy assignment
CantorMultiset& CantorMultiset::operator=(
    const CantorMultiset& other
) {
    if (this != &other) {
        elements.clear();

        for (const auto& item : other.elements) {
            if (item.nested) {
                elements.insert(Element(*item.nested));
            }
            else {
                elements.insert(Element(item.atom));
            }
        }
    }

    return *this;
}

// Check whether the multiset is empty
bool CantorMultiset::isEmpty() const {
    return elements.empty();
}

// Return total number of elements, including duplicates
size_t CantorMultiset::cardinality() const {
    return elements.size();
}

// Add an element
void CantorMultiset::add(const std::string& item) {
    elements.insert(makeElement(item));
}

// Remove one occurrence
bool CantorMultiset::remove(const std::string& item) {
    auto it = elements.find(makeElement(item));

    if (it == elements.end()) {
        return false;
    }

    elements.erase(it);
    return true;
}

// Check whether an element exists
bool CantorMultiset::contains(const std::string& item) const {
    return elements.find(makeElement(item)) != elements.end();
}

// Membership operator
bool CantorMultiset::operator[](const std::string& item) const {
    return contains(item);
}

// Union: add multiplicities
CantorMultiset CantorMultiset::operator+(
    const CantorMultiset& other
) const {
    CantorMultiset result(*this);

    result.elements.insert(
        other.elements.begin(),
        other.elements.end()
    );

    return result;
}

CantorMultiset CantorMultiset::operator*(
    const CantorMultiset& other
) const {
    CantorMultiset result;

    auto first = elements.begin();
    auto second = other.elements.begin();

    while (first != elements.end() &&
           second != other.elements.end()) {

        std::string firstKey = elementKey(*first);
        std::string secondKey = elementKey(*second);

        if (firstKey < secondKey) {
            ++first;
        }
        else if (secondKey < firstKey) {
            ++second;
        }
        else {
            result.elements.insert(*first);
            ++first;
            ++second;
        }
    }

    return result;
}

// Difference: subtract multiplicities
CantorMultiset CantorMultiset::operator-(
    const CantorMultiset& other
) const {
    CantorMultiset result(*this);

    for (const auto& item : other.elements) {
        auto it = result.elements.find(item);

        if (it != result.elements.end()) {
            result.elements.erase(it);
        }
    }

    return result;
}

// Compound union
CantorMultiset& CantorMultiset::operator+=(
    const CantorMultiset& other
) {
    *this = *this + other;
    return *this;
}

// Compound intersection
CantorMultiset& CantorMultiset::operator*=(
    const CantorMultiset& other
) {
    *this = *this * other;
    return *this;
}

// Compound difference
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
    if (elements.size() != other.elements.size()) {
        return false;
    }

    auto first = elements.begin();
    auto second = other.elements.begin();

    while (first != elements.end()) {
        if (elementKey(*first) != elementKey(*second)) {
            return false;
        }

        ++first;
        ++second;
    }

    return true;
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
    std::vector<std::pair<Element, size_t>> groups;

    for (const auto& item : elements) {
        if (groups.empty() ||
            elementKey(groups.back().first) != elementKey(item)) {
            groups.push_back({item, 1});
        }
        else {
            groups.back().second++;
        }
    }

    std::function<void(size_t, CantorMultiset)> generate =
        [&](size_t index, CantorMultiset current) {
            if (index == groups.size()) {
                results.push_back(current);
                return;
            }

            const Element& item = groups[index].first;
            size_t frequency = groups[index].second;

            for (size_t count = 0; count <= frequency; count++) {
                generate(index + 1, current);

                current.elements.insert(item);
            }
        };

    generate(0, CantorMultiset());

    return results;
}

// Parse a multiset string recursively
void CantorMultiset::parseFromString(const std::string& input) {
    elements.clear();

    std::string text = trim(input);

    if (text.size() < 2 ||
        text.front() != '{' ||
        text.back() != '}') {
        return;
    }

    std::string inner = text.substr(1, text.size() - 2);

    std::string current;
    int depth = 0;

    for (char ch : inner) {
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
                elements.insert(makeElement(item));
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
        elements.insert(makeElement(finalItem));
    }
}

// Output operator
std::ostream& operator<<(
    std::ostream& os,
    const CantorMultiset& s
) {
    os << "{ ";

    for (const auto& item : s.elements) {
        if (item.nested) {
            os << *item.nested;
        }
        else {
            os << item.atom;
        }

        os << " ";
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