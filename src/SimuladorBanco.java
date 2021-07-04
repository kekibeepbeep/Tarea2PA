import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class SimuladorBanco {
  ArrayList<Cliente> clientes;
  int mesActual;

  public SimuladorBanco() {
    clientes = new ArrayList<Cliente>();
    mesActual = 0;
  }

  public String toString() {
    String answer = "mesActual " + mesActual + ".";
    answer += " Clientes: " + clientes.size() + "\n";
    
    Iterator<Cliente> it = clientes.iterator();
    while (it.hasNext()){
      Cliente clie = it.next();
      answer += clie + "\n";
    }

    return answer;
  }

  public void simulaMes() {
    // para cada cliente, calcula y agrega intereses en ctas de ahorro y cdts
    Iterator<Cliente> it = clientes.iterator();
    while (it.hasNext()) {
      //Cliente clie = it.next();
      //clie.simulaMes();
      
      it.next().simulaMes(); // como solo se necesita una acción con el cliente, la hago directamente
    }
    mesActual++;
  }

  public boolean agregarCliente(String nombre, int cedula) {
    // pregunta: que hacer para no repetir dos clientes con la misma cedula
    return clientes.add(new Cliente(nombre, cedula));
  }

  public Cliente obtenerCliente(int cedula) {
    // revisamos si alguno de los clientes tiene la cedula
    Iterator<Cliente> it = clientes.iterator();
    while (it.hasNext()) {
      Cliente clie = it.next();
      if (clie.getId() == cedula) {
        return clie;
      }
    }
    return null;
  }

  public boolean esCliente(int cedula) { // aprovechando el metodo obtenerCliente
    return obtenerCliente(cedula) != null;
  }

  public boolean agregarCtaCte(int cedula) {
    Cliente clie = obtenerCliente(cedula);
    if (clie != null) {
      return clie.agregarCtaCte();
    }
    else {
      System.out.println("Cliente " + cedula + " no existe. Imposible agregar Cta Cte");
      return false;
    }
  }

  public boolean agregarCtaAhorro(int cedula, double interes) {
    Cliente clie = obtenerCliente(cedula);
    if (clie != null) {
      return clie.agregarCtaAhorro(interes);
    }
    else {
      System.out.println("Cliente " + cedula + " no existe. Imposible agregar Cta Ahorro");
      return false;
    }
  }

  public boolean agregarCDT(int cedula, int monto, double interes) {
    if (monto < 0) {
      System.out.println("monto " + monto + " negativo. Imposible de crear CDT");
      return false;
    }
    Cliente clie = obtenerCliente(cedula);
    if (clie != null) {
      return clie.agregarCDT(monto, interes);
    }
    else {
      System.out.println("Cliente " + cedula + " no existe. Imposible de crear CDT");
      return false;
    }
  }

  public boolean borrarCuenta(int cedula, int id, Class clase) {
    Cliente clie = obtenerCliente(cedula);
    if (clie != null) { // equiv a chequear (clie != null)
      return clie.borrarCuenta(id, clase);
    }
    else {return false;}
  }

  public boolean cerrarCDT(int cedula, int id, int idCtaCte) {
    Cliente clie = obtenerCliente(cedula);
    if (clie != null) {
      return clie.cerrarCDT(id, idCtaCte);
    }
    else {return false;}
  }

  public boolean deposito(int cedula, int monto, int id, Class clase) {
    Cliente clie = obtenerCliente(cedula);
    if (clie != null) {
      return clie.deposito(monto, id, clase);
    }
    else {return false;}
  }

  public boolean giro(int cedula, int monto, int id, Class clase) {
    Cliente clie = obtenerCliente(cedula);
    if (clie != null) {
      return clie.giro(monto, id, clase);
    }
    else {return false;}
  }
  public void seriar(){
    Serializador s = new Serializador();
    for (Cliente cliente : clientes) {
      try {
        s.ingresarABD(cliente);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    System.out.println("\nSerializado completo\n");
  }
  public void rescatar() throws IOException{
    Serializador s = new Serializador();
    clientes.addAll(s.cargarDataBase());

    System.out.println("\nSe han cargado los datos con exito\n");
  }
}