file.o: file.cpp file.h
	g++ file.cpp -c
main: main.cpp file.o
	g++ main.cpp file.o -o main -std=c++11 -lpthread