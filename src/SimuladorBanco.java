import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

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
  }
 
  public void rescatar() throws IOException{
    Serializador s = new Serializador();
    clientes.addAll(s.cargarDataBase());

   
  }
  public void listarClientes() { //lista clientes del banco hecha para fines de testeo.
    for (int i=0;i<clientes.size();i++) {
      System.out.println(clientes.get(i).toString());
    }
  }
  public boolean depositar(int idOrigen, int idDestino) {
    Scanner in = new Scanner(System.in);
    Cliente cOrigen = obtenerCliente(idOrigen);
    Cliente cDestino = obtenerCliente(idDestino);
    if(cOrigen == null)return false;
    if(cDestino==null)return false;
    for (Cliente cliente : cOrigen.getAgenda()) {
        if(cliente.getId() == idDestino){
          System.out.println("Eliga cuenta del destinatario: \n");
          for (Cuenta cuenta : cliente.cuentas)System.out.println(cuenta.getId());
          System.out.print("nmro cta: ");
          int nmroCtadDestino = in.nextInt();
          System.out.println("Eliga cuenta origen: \n");
          for(Cuenta cuenta : obtenerCliente(idOrigen).cuentas)System.out.println(cuenta);
          System.out.print("nmro cta: ");
          int nmroCtaOrigen = in.nextInt();
          for (Cuenta cuentaD : cliente.cuentas) {
            for(Cuenta cuentaO : obtenerCliente(idOrigen).cuentas){
              if(cuentaD.getId()==nmroCtadDestino && cuentaO.getId()==nmroCtaOrigen){
                System.out.print("Cantidad a depositar: ");
                int dep = in.nextInt();
                if(!cuentaD.depositar(dep))return false;
                if(!cuentaO.girar(dep))return false;
                return true;
              }
            }
          }
        } 
    }
    return false;
  }
  public boolean existeCliente(Cliente cliente){ //verifica si existe un cliente
    for (int i=0;i<clientes.size();i++) {
      if (cliente == clientes.get(i)){
        return true;
      }   
    }
    System.out.println("El cliente solicitado no existe.");
    return false;
  }

  public int getPosCliente(Cliente cliente){ //retorna posicion de un cliente en el arraylist clientes
    for (int i=0;i<clientes.size();i++) {
      if (cliente == clientes.get(i)){
        return i;
      } 
    }
    System.out.println("El cliente solicitado no existe.");
    return -1;  //retorna -1 si no lo encuentra
  }



}

