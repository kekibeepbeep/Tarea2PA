import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Serializador implements Serializable{
    //clase para serializar el objeto cliente
    public Cliente ingresarABD(Cliente cliente) throws IOException{
        FileOutputStream file = new FileOutputStream("DataBase/"+cliente.getId());
        ObjectOutputStream output = new ObjectOutputStream(file);
        if(output != null){
            output.writeObject(cliente);

            output.close();

        }  
        file.close();
        return cliente;
    }
    //clase para recuperar objetos serializados
    public ArrayList<Cliente> cargarDataBase() throws IOException{
        FileInputStream file;
        ObjectInputStream input;
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        Cliente cliente = null;

        //try catch para recorrer carpeta DataBase y crear una lista con los objetos
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("DataBase/"))){

            //utilizamos este foreach para recorrer la carpeta
            for (Path path : stream) {
                /*
                 * el nombre del archivo se puede obtener del objeto Path de java.nio
                 * y al obtener el nombre de este se usa para crear uno nuevo pero 
                 * de tipo FileInputStream de java.io 
                 * 
                 */
                file = new FileInputStream("DataBase/"+path.getFileName().toString());
                input = new ObjectInputStream(file);

                cliente = (Cliente)input.readObject();
                clientes.add(cliente);
                input.close();
                file.close();
            }
            stream.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return clientes;
    }

    //este metodo simplemente borra 1 cliente segun id
    public void borrar(String id) {
        File cliente = new File("DataBase/"+id);
        if(!cliente.exists() || !cliente.delete()){
            System.err.println("Error al borrar de DataBase");
        }
    }
}