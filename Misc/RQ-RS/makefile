paquet: datagramPaquet.cpp datagramPaquet.h
	g++ -c datagramPaquet.cpp
socket: datagramSocket.cpp datagramSocket.h
	g++ -c datagramSocket.cpp
client: client.cpp datagramPaquet.o datagramSocket.o
	g++ client.cpp datagramPaquet.o datagramSocket.o -o client -std=c++11
server: server.cpp datagramPaquet.o datagramSocket.o Coord.o gfx.o Spider.o
	g++ server.cpp datagramPaquet.o datagramSocket.o Coord.o gfx.o Spider.o -o server -std=c++11