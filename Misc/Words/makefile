words: words.cpp file.o
	g++ words.cpp file.o -o words -std=c++11 -lpthread
file.o: file.cpp file.h
	g++ file.cpp -c
wordsAll: wordsAll.cpp file.o
	g++ wordsAll.cpp file.o -o wordsAll -std=c++11 -lpthread
testFiles: testFiles.cpp file.o
	g++ testFiles.cpp file.o -o testFiles -std=c++11