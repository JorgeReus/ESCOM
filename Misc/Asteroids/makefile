main: main.cpp Asteroid.o Coordinate.o
	g++ gfx.o main.cpp Asteroid.o Coordinate.o -o main -lX11
	rm *.o
Asteroid.o: Asteroid.cpp Coordinate.o gfx.o Asteroid.h
	g++ Asteroid.cpp -c
gfx.o: gfx.c gfx.h
	gcc gfx.c -c
Coordinate.o: Coordinate.cpp Coordinate.h
	g++ Coordinate.cpp -c