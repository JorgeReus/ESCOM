paquet: PaqueteDatagrama.cpp PaqueteDatagrama.h
	g++ PaqueteDatagrama.cpp -c

socketMulticast: SocketMulticast.cpp SocketMulticast.h PaqueteDatagrama.o
	g++ SocketMulticast.cpp PaqueteDatagrama.o -c

client: client.cpp PaqueteDatagrama.o SocketMulticast.o
	g++ client.cpp PaqueteDatagrama.o SocketMulticast.o -o client
