paquet: datagramPaquet.cpp datagramPaquet.h
	g++ -c datagramPaquet.cpp
socket: datagramSocket.cpp datagramSocket.h
	g++ -c datagramSocket.cpp
coor: Coord.cpp Coord.h
	g++ -c Coord.cpp
client: client.cpp datagramPaquet.o datagramSocket.o Coord.o
	g++ client.cpp datagramPaquet.o datagramSocket.o Coord.o -o client -std=c++11
server: server.cpp datagramPaquet.o datagramSocket.o Coord.o gfx.o Spider.o
	g++ server.cpp datagramPaquet.o datagramSocket.o Coord.o gfx.o Spider.o -o server -std=c++11 -lX11
gfx: gfx.c gfx.h
	gcc -c gfx.c
spider: Spider.cpp gfx.o Coord.o Spider.h
	g++ Spider.cpp -c -std=c++11 -lX11 
draw: draw.cpp gfx.o Coord.o Spider.o
	g++ draw.cpp gfx.o Coord.o Spider.o -o draw -std=c++11 -lX11 