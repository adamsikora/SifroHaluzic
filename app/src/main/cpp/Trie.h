#pragma once

#include <vector>

struct Trie {
	void countContainments(const char* start, char* curr, const char* end, int& result) const;
	void insert(char* s, char* end);

	std::vector<std::pair<char, Trie>> leaves;
	bool contained = false;
};