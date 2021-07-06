Desarrolladores: Antonia Fuenzalida, Valentina Yañes, Diego Fernández

Lenguaje: java
JDK: javaSE-10 (eclipse java version)
Editor de Desarrollo: Visual Studio Code
IDE de Desarrollo: Eclipse

Descripcion:

    Si se ejecuta el codigo con vscode en la carpeta .vscode hay perfiles
    de ejecucion que se pueden utilizar, el de launch external terminal, 
    lo ejecuta en una terminal a parte y launch app lo ejecuta en la 
    terminal de visual, existe una opcion alternativa a visual estudio 
    se llama 'vscodium', de todas maneras debe ser posible ejecutarlo por
    simbolos del sistema mediante las lineas de comandos del lenguaje, en 
    la carpeta 'src' se contiene el path de estructuras y la carpeta 'bin' 
    contiene los archivos compilados. Las entradas del programa son
    por teclado, al momento de ejecutar el programa se cargara la 'Base de Datos'
    de la carpeta 'DataBase', este programa utiliza serialicacion a los clientes
    del arraylist de clientes de simuladorbanco, en la carpeta 'DataBase' se encuentran
    los clientes ordenados por id.

Ejemplo de uso 1:

    entrada: 
        - 14
        - 345

    salida:
        se elimino el cliente de celula 345

Ejemplo de uso 2:

    entrada: 
        - 14
        - 412

    salida:
        El saldo del cliente no es 0 por lo que no se puede eliminar

Ejemplo de uso 3:

    entrada: 
        - 15 
        - 666
        - 412

    salida:
        destinatario agregado correctamente

Ejemplo de uso 4:

    entrada: 
        - 15 
        - 616
        - 1

    salida:
        El cliente solicitado no existe

Ejemplo de uso 5:

    entrada: 
        - 17
        - 412
        - 666
        - 1003
        - 1000
        - 900000

    salida:
         Deposito exitoso

Ejemplo de uso 6:

    entrada: 
        - 18 

    salida:
        Serializado completo

Ejemplo de uso 7:

    entrada: 
        - 19

    salida:
        Se han cargado los datos con exito
