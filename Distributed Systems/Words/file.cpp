#include "file.h"
#include <string>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <iostream>
#include <cstdlib>
#include <unistd.h>
#include <stdio.h>

Archivo::Archivo(std::string filename)
{
    nombreArchivo = filename;
    fd = open(filename.c_str(), O_RDONLY);
    if (fd < 0)
    {
        std::cout << "Error al intentar abrir el Archivo" << std::endl;
        perror(nombreArchivo.c_str());
        exit(-1);
    }
    contenido = NULL;
    struct stat st;
    fstat(fd, &st);
    num_bytes = st.st_size;
    contenido = (char*)malloc(num_bytes);
}

Archivo::Archivo(std::string filename, int banderas, mode_t modo)
{
    nombreArchivo = filename;
    fd = open(filename.c_str(), banderas, modo);
    if (fd < 0)
    {
        std::cout << "Error al intentar abrir el Archivo" << std::endl;
        perror(nombreArchivo.c_str());
        exit(-1);
    }
    contenido = NULL;
    num_bytes = 0;
}
size_t Archivo::lee()
{
    int n = read(fd, contenido, num_bytes);
    if(n < 0) {
        std::cout << "Error En la lectura del archivo " << std::endl;
        exit(1); 
    }
    return n;
}
size_t Archivo::escribe(void *buffer ,size_t nbytes)
{
    int bytes = write(fd, buffer, nbytes);
    if (bytes != nbytes){
        std::cout << "Error al escribir en el archivo" << std::endl;
        perror(nombreArchivo.c_str());
        exit(1);
    }
    return bytes;
}
size_t Archivo::obtieneNum_bytes()
{
    return num_bytes;
}
const char *Archivo::get_contenido()
{
    return contenido;
}

Archivo::~Archivo()
{
    free(contenido);
    close(fd);
}
