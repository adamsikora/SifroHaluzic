#include "Trie.h"

void Trie::countContainments(const char* start, char* curr, const char* end, int& result) const
{
	if (contained) {
		result += curr - start;
	}
	if (curr < end) {
		for (unsigned i = 0; i < leaves.size(); ++i) {
			int temp = leaves[i].first;
			if (temp >= curr[0]) {
				if (temp == curr[0]) {
					leaves[i].second.countContainments(start, curr + 1, end, result);
				}
				else {
					break;
				}
			}
		}
	}
}

void Trie::insert(char* s, char* end)
{
	if (s == end) {
		contained = true;
	}
	else {
		if (leaves.empty() || s[0] != leaves.back().first) {
			leaves.push_back({ s[0], Trie() });
		}
		leaves.back().second.insert(s + 1, end);
	}
}