all: main.cpp SocketDatagrama.o PaqueteDatagrama.o gfx.o
	g++ main.cpp SocketDatagrama.o PaqueteDatagrama.o gfx.o -o server -lX11
SocketDatagrama: SocketDatagrama.cpp SocketDatagrama.h
	g++ -c SocketDatagrama.cpp
PaqueteDatagrama: PaqueteDatagrama.cpp PaqueteDatagrama.h
	g++ PaqueteDatagrama.cpp -c
gfx.o: gfx.c gfx.h
	gcc gfx.c -c