main: main.cpp Asteroide.o Coordinate.o
	g++ gfx.o main.cpp Asteroide.o Coordinate.o -o main -lX11
	rm *.o
Asteroide.o: Asteroide.cpp Coordinate.o gfx.o Asteroide.h
	g++ Asteroide.cpp -c
gfx.o: gfx.c gfx.h
	gcc gfx.c -c
Coordinate.o: Coordinate.cpp Coordinate.h
	g++ Coordinate.cpp -c