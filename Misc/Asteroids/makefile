main: main.cpp Asteroide.o
	g++ gfx.o main.cpp Asteroide.o -o main -lX11
	rm gfx.o Asteroide.o
Asteroide.o: Asteroide.cpp gfx.o Asteroide.h
	g++ Asteroide.cpp -c
gfx.o: gfx.c gfx.h
	gcc gfx.c -c