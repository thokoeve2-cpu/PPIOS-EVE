#pragma once
#include <iostream>
#include <set>
#include <string>

class CantorSet {
private:
	std::set<std::string> elements;

public:
	CantorSet();
	CantorSet(const CantorSet& other);
	CantorSet& operator=(const CantorSet& other);
	bool operator==(const CantorSet& other) const;
	bool operator!=(const CantorSet& other) const;

	void add(const std::string& item);
	void parseFromString(const std::string& input);

	friend std::ostream& operator<<(std::ostream& os, const CantorSet& s);
	friend std::istream& operator>>(std::istream& is, CantorSet& s);
};
