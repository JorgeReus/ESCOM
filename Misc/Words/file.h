#include <string>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <iostream>
#ifndef FILE_H_
	#define FILE_H_
	class Archivo
	{
	private:
	int fd;
	char *contenido;
	size_t num_bytes;
	public:
	std::string nombreArchivo;
	Archivo(std::string filename);
	Archivo(std::string filename, int banderas, mode_t modo);
	size_t lee(size_t nbytes);
	size_t escribe(void *buffer, size_t nbytes);
	size_t obtieneNum_bytes();
	const char *get_contenido();
	~Archivo();
	};
#endif