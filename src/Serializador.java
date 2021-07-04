import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serializador implements Serializable{
    //clase para serializar el objeto cliente
    public Cliente ingresarABD(Cliente cliente) throws IOException{
        FileOutputStream file = new FileOutputStream("DataBase/"+cliente.getId());
        ObjectOutputStream output = new ObjectOutputStream(file);

        output.writeObject(cliente);

        output.close();

        return cliente;
    }

}