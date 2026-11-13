#include "CantorSet.h"

CantorSet::CantorSet() {}

CantorSet::CantorSet(const CantorSet& other) {
	elements = other.elements;
}

CantorSet& CantorSet::operator=(const CantorSet& other) {
	if (this != &other) {
		elements = other.elements;
	}
	return *this;
}

bool CantorSet::operator==(const CantorSet& other) const {
	return elements == other.elements;
}

bool CantorSet::operator!=(const CantorSet& other) const {
	return elements != other.elements;
}

void CantorSet::add(const std::string& item) {
	elements.insert(item);
}

void CantorSet::parseFromString(const std::string& input) {
	elements.clear();
	std::string current = "";
	for (char ch : input) {
		if (ch == '{' || ch == '}' || ch == ',' || ch == ' ') {
			if (!current.empty()) {
				elements.insert(current);
				current = "";
			}
		}
		else {
			current += ch;
		}
	}
}

std::ostream& operator<<(std::ostream& os, const CantorSet& s) {
	os << "{ ";
	for (const auto& item : s.elements) {
		os << item << " ";
	}
	os << "}";
	return os;
}

std::istream& operator>>(std::istream& is, CantorSet& s) {
	std::string input;
	std::getline(is, input);
	s.parseFromString(input);
	return is;
}