#include <stdio.h>
#include <stdlib.h>

// setrlimit to increase number of files to be 2^16

int main(int argc, char *argv[])
{
    FILE **fps = malloc(sizeof(FILE*) * 65535);

    int i;
    for(i = 0; i < 65536; i++)
    {
        char filename[13+i];
        sprintf(filename, "./tmp/fdtest.%d", i);
        fps[i] = fopen(filename, "w+");
        
    }
    printf("Created Files, Waiting...\n");
    while(1);
}


