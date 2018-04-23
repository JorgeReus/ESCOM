paquet: datagramPaquet.cpp datagramPaquet.h
	g++ datagramPaquet.cpp -c
socket: datagramSocket.cpp datagramSocket.h datagramPaquet.o
	g++ -c datagramSocket.cpp datagramPaquet.o
coor: Coord.cpp Coord.h
	g++ -c Coord.cpp
client: client.cpp datagramPaquet.o datagramSocket.o Coord.o
	g++ client.cpp datagramPaquet.o datagramSocket.o Coord.o -o client -std=c++11
server: server.cpp datagramPaquet.o datagramSocket.o Coord.o
	g++ server.cpp datagramPaquet.o datagramSocket.o Coord.o -o server -std=c++11