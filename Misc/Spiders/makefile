paquet: datagramPaquet.cpp datagramPaquet.h
	g++ -c datagramPaquet.cpp
socket: datagramSocket.cpp datagramSocket.h
	g++ -c datagramSocket.cpp
coor: Coord.cpp Coord.h
	g++ -c Coord.cpp
client: client.cpp datagramPaquet.o datagramSocket.o Coord.o
	g++ client.cpp datagramPaquet.o datagramSocket.o Coord.o -o client -std=c++11
server: server.cpp datagramPaquet.o datagramSocket.o Coord.o
	g++ server.cpp datagramPaquet.o datagramSocket.o Coord.o -o server -std=c++11 -lpthread