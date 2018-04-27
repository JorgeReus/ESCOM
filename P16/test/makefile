main: main.cpp gfx.o paquet socket
	g++ gfx.o main.cpp Coordinate.o -o main -lX11
	rm *.o
gfx.o: gfx.c gfx.h
	gcc gfx.c -c
paquet: datagramPaquet.cpp datagramPaquet.h
		g++ datagramPaquet.cpp -c
socket: datagramSocket.cpp datagramSocket.h datagramPaquet.o
		g++ -c datagramSocket.cpp datagramPaquet.o
