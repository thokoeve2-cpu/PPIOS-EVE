#pragma once

#include <iostream>
#include <set>
#include <string>
#include <vector>
/**
 * @class 
 * @brief 
 */
class CantorMultiset {
private:
    std::multiset<std::string> elements;

    static std::string trim(const std::string& text);

public:
    // Constructors
    CantorMultiset();
    CantorMultiset(const CantorMultiset& other);
    CantorMultiset& operator=(const CantorMultiset& other);

    // Basic operations
    bool isEmpty() const;
    size_t cardinality() const;
    void add(const std::string& item);
    bool remove(const std::string& item);
    bool contains(const std::string& item) const;
	bool operator[](const std::string& item) const;

    // Set operations
    CantorMultiset operator+(const CantorMultiset& other) const;
    CantorMultiset operator*(const CantorMultiset& other) const;
    CantorMultiset operator-(const CantorMultiset& other) const;

    CantorMultiset& operator+=(const CantorMultiset& other);
    CantorMultiset& operator*=(const CantorMultiset& other);
    CantorMultiset& operator-=(const CantorMultiset& other);

    // Equality
    bool operator==(const CantorMultiset& other) const;
    bool operator!=(const CantorMultiset& other) const;

    // Power set
    std::vector<CantorMultiset> powerSet() const;

    // String operations
    void parseFromString(const std::string& input);

    // Stream operations
    friend std::ostream& operator<<(
        std::ostream& os,
        const CantorMultiset& s
    );

    friend std::istream& operator>>(
        std::istream& is,
        CantorMultiset& s
    );
};