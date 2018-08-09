clock:  clock.o gfxModified.o
	g++ -std=c++11 clock.o gfxModified.o -o clock  -lX11 -L/usr/X11R6/lib
clock.o: clock.cpp gfxModified.o
	g++ -std=c++11 clock.cpp -c 
gfxModified.o: gfxModified.c gfxModified.h
	gcc gfxModified.c -c