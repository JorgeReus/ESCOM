clock:  clock.o gfxModified.o
	g++ -std=c++11 clock.o gfxModified.o -o clock -lX11
clock.o: clock.cpp gfxModified.o
	g++ -std=c++11 clock.cpp -c
gfxModified.o: gfxModified.c gfxModified.h
	gcc gfxModified.c -c