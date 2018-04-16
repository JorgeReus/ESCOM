paquet: datagramPaquet.cpp datagramPaquet.h
	g++ datagramPaquet.cpp -c
socket: datagramSocket.cpp datagramSocket.h datagramPaquet.o
	g++ -c datagramSocket.cpp datagramPaquet.o
client: primesClient.cpp datagramPaquet.o datagramSocket.o
	g++ primesClient.cpp datagramPaquet.o datagramSocket.o -o primesClient -std=c++11
server: primesServer.cpp datagramPaquet.o datagramSocket.o
	g++ primesServer.cpp datagramPaquet.o datagramSocket.o -o primesServer -std=c++11