#include <sstream>

#include <set>
#include <map>

#include <algorithm>

#include "MrizkoDrtic.h"

int getScore(const std::string& s)
{
	int result = 0;
	char start[256];
	memcpy(start, s.c_str(), s.size());
	for (unsigned i = 0; i < s.size(); ++i) {
		//int plus = 0;
		root.countContainments(start + i, start + i, start + s.size(), result);
		//result += plus;
		//for (unsigned j = 1; j <= s.size() - i; ++j) {
		//	if (root.contains(start + i, start + j)) {
		//		result += j;
		//	}
		//	//std::cout << s.substr(i, j) << " " << result << std::endl;
		//}
	}
	return result;
}

bool isPerfectSquare(int n)
{
	if (n < 0)
		return false;
	int root(round(sqrt(n)));
	return n == root * root;
}

void initialize()
{
	std::string w = "ab";
	char s[256];
	memcpy(s, w.c_str(), w.size());
	root.insert(s, s + w.size());

	initialized = true;
}

std::string drtMrizku(std::string input)
{
	if (!initialized) {
		initialize();
	}
	int size = input.size();
	if (!isPerfectSquare(size)) {
		return "wrong input size\n";
	}
	int sideLength = round(sqrt(size));
	int halfSideLength = sideLength / 2;
	int halfSideLengthExt = halfSideLength + sideLength % 2;
	int fourthSize = (sideLength*sideLength - size % 2) / 4;

	std::multimap<int, std::string> results;
	std::vector<Distributed> hints(fourthSize, Distributed());

	for (int i = 0; i < halfSideLength; ++i) {
		for (int j = 0; j < halfSideLengthExt; ++j) {
			//std::cout << i << "," << j << "," << i*halfSideLengthExt + j << "\n";
			int pos = i*halfSideLengthExt + j;
			int index = i*sideLength + j;
			hints[pos].ch[0] = { input[index], index };
			index = sideLength - 1 - i + j*sideLength;
			hints[pos].ch[1] = { input[index], index };
			index = size - 1 - (i*sideLength + j);
			hints[pos].ch[2] = { input[index], index };
			index = size - 1 - (sideLength - 1 - i + j*sideLength);
			hints[pos].ch[3] = { input[index], index };
		}
	}

	Crawler crawler(fourthSize, 4);
	do {
		std::vector<std::pair<int, char>> vec;
		vec.reserve(size);

		for (int i = 0; i < fourthSize; ++i) {
			for (int j = 0; j < 4; ++j) {
				auto temp = hints[i].ch[j];
				int index = 128*((j - crawler.indices[i] + 4) % 4) + hints[i].ch[j].second;
				//if (map.count(index) != 0) {
				//	std::cout << "wrong map input\n";
				//}
				vec.push_back({ index, temp.first });
			}
		}

		if (vec.size() + size % 2 != size) {
			//std::cout << "wrong mapping\n";
		}
		std::sort(vec.begin(), vec.end());
		std::string toCheck;
		for (auto c : vec) {
			toCheck += c.second;
		}
		int score = getScore(toCheck);
		if (results.size() < 100) {
			results.insert({ score, toCheck });
		} else {
			if (score > results.begin()->first) {
				results.insert({ score, toCheck });
				results.erase(results.begin());
			}
		}
	} while (crawler.next());

	std::stringstream ss;
	for (auto it = results.rbegin(); it != results.rend(); ++it) {
		ss << it->second << " (" << it->first << ")\n";
	}
	return ss.str();
}