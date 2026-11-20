#pragma once

#include <iostream>
#include <memory>
#include <set>
#include <string>
#include <vector>

class CantorMultiset {
private:
    struct Element {
        std::string atom;
        std::shared_ptr<CantorMultiset> nested;

        Element(const std::string& value);
        Element(const CantorMultiset& value);
    };

    struct ElementLess {
        bool operator()(const Element& a, const Element& b) const;
    };

    std::multiset<Element, ElementLess> elements;

    static std::string trim(const std::string& text);
    static Element makeElement(const std::string& item);
    static std::string elementKey(const Element& item);

public:
    CantorMultiset();
    CantorMultiset(const CantorMultiset& other);
    CantorMultiset& operator=(const CantorMultiset& other);

    bool isEmpty() const;
    size_t cardinality() const;

    void add(const std::string& item);
    bool remove(const std::string& item);
    bool contains(const std::string& item) const;
    bool operator[](const std::string& item) const;

    CantorMultiset operator+(const CantorMultiset& other) const;
    CantorMultiset operator*(const CantorMultiset& other) const;
    CantorMultiset operator-(const CantorMultiset& other) const;

    CantorMultiset& operator+=(const CantorMultiset& other);
    CantorMultiset& operator*=(const CantorMultiset& other);
    CantorMultiset& operator-=(const CantorMultiset& other);

    bool operator==(const CantorMultiset& other) const;
    bool operator!=(const CantorMultiset& other) const;

    std::vector<CantorMultiset> powerSet() const;

    void parseFromString(const std::string& input);

    friend std::ostream& operator<<(
        std::ostream& os,
        const CantorMultiset& s
    );

    friend std::istream& operator>>(
        std::istream& is,
        CantorMultiset& s
    );
};