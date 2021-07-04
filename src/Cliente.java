import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/*
  Ya no se asumen los tipos numericos de cuenta.
  */

public class Cliente implements Serializable{
  protected String nombre;
  protected int numCedula;
  protected ArrayList<Cuenta> cuentas;
  //protected ArrayList<Cliente> agendaDestinatarios; //ojo al serializar

  public Cliente(String nombre, int cedula) {
    this.nombre = nombre;
    this.numCedula = cedula;
    this.cuentas = new ArrayList<Cuenta>(3);
    // los clientes suelen tener 1 cta corriente, 1 de ahorro y 1CDT.
    // El tamaño por omisión de un ArrayList es 10
  }
  
  public int getId() {
    return numCedula;
  }

  public int saldoTotal() {
    int total = 0; // variable auxiliar para contabilizar saldos

    // recorrer el ArrayList con un iterador
    Iterator<Cuenta> it =  cuentas.iterator();
    while (it.hasNext()) {
      total += it.next().consultaSaldo();
    }

    return total;
  }

  public String toString() {
    return ">>nombre: " + this.nombre +
      ", numCedula " + numCedula +
      ", #cuentas: " + cuentas.size() +
      ", saldoTotal " + saldoTotal() +
      "<<";
  }

  public void simulaMes() {
    // alternativa 1: recorrer el ArrayList como arreglo
    //for (int i = 0; i < cuentas.size(); i++) {
    //  cuentas.get(i).calcInteres();
    //}

    // alternativa 2: recorrer el ArrayList con iterador
    Iterator<Cuenta> it = cuentas.iterator();
    while (it.hasNext()) {
      it.next().calcInteres();
    }
  }

  public boolean agregarCtaCte() {
    return cuentas.add(new CtaCte());
  }

  public boolean agregarCtaAhorro(double interes) {
    return cuentas.add(new CtaAhorro(interes));
  }

  public boolean agregarCDT(int monto, double interes) {
    CDT cdt = new CDT(interes);
    cdt.depositar(monto);
    return cuentas.add(cdt);
  }

  public String listarCuentas(Class clase) {

    Iterator<Cuenta> it = cuentas.iterator();
    String resultado = "";
    Cuenta cta;
    while (it.hasNext()) {
      cta = it.next();
      if (clase.isInstance(cta)) {
        resultado += " " + cta.getId();
      }
    }
    return resultado;
  }

  public void listarCuentas() {
    System.out.print("Listando " + cuentas.size() + " cuentas: ");
    Iterator<Cuenta> it = cuentas.iterator();
    while (it.hasNext()) {
      System.out.print(" " + it.next().getId());
    }
    System.out.println("");
  }

  protected int getIdxCta(int id) {
    // recorrer el ArrayList como arreglo
    for (int i = 0; i < cuentas.size(); i++) {
      Cuenta cta = cuentas.get(i);
      if (cta.getId() == id) { return i; }
    }
    return -1; // no esta la cuenta
  }

  public boolean borrarCuenta(int id, Class clase) {
    int idx = getIdxCta(id);
    if (idx == -1) {
      System.out.println("Cliente " + this.numCedula + " " + this.nombre + " no tiene cuenta  " + id + ", operacion cancelada");
      return false; // no encontro la cuenta
    }
    Cuenta cta = cuentas.get(idx); // tenemos la cta

    // chequeamos que sea un tipo de cuenta que se puede borrar
    if ( ((cta instanceof CtaCte) || (cta instanceof CtaAhorro)) == false ) {
      System.out.println("la cuenta " + id + " no pertenece a las cuentas que se pueden borrar, operacion cancelada");
      return false;
    }

    // chequeamos que el tipo de la cuenta coincide con lo especificado en clase
    if (clase.isInstance(cta)) {
      // pregunta: ¿como podemos considerar el hecho de que la este vacia antes de cerrarla?
      return (cuentas.remove(idx) != null)?true:false;
    }
    else {
      System.out.println("la cuenta " + id + " no corresponde con el tipo " + clase.getSimpleName() + ", operacion cancelada");
      return false;
    }    
  }

  public boolean cerrarCDT(int id, int idCtaCte) {
    int idx = getIdxCta(idCtaCte); // buscamos cta cte de destino
    if (idx == -1) {
      System.out.println("Cliente " + this.numCedula + " " + this.nombre + " no tiene cuenta corriente " + idCtaCte + ", operacion cancelada");
      return false;
    }
    Cuenta ctaDest = cuentas.get(idx); // tenemos la cta de destino

    idx = getIdxCta(id); // buscamos CDT a cerrar
    if (idx == -1) {
      System.out.println("Cliente " + this.numCedula + " " + this.nombre + " no tiene CDT " + id + ", operacion cancelada");
      return false;
    }
    Cuenta cdt = cuentas.get(idx); // tenemos el certif a cerrar

    ctaDest.depositar(cdt.consultaSaldo());
    return (cuentas.remove(idx) != null)?true:false;
  }

  public boolean deposito(int monto, int id, Class clase) {
    String nombreClase = clase.getSimpleName();
    if ( ( nombreClase.equals(CtaCte.class.getSimpleName()) ||
           nombreClase.equals(CtaAhorro.class.getSimpleName())) == false ) {
      System.out.println("Tipo " + nombreClase + " no admite depositos");
      return false;
    }

    int idx = getIdxCta(id); // buscamos cta para depositar
    if (idx == -1) {
      System.out.println("Cliente " + this.numCedula + " " + this.nombre + " no tiene cuenta  " + id + ", operacion cancelada");
      return false; // no encontro la cuenta
    }

    Cuenta cta = cuentas.get(idx); // tenemos la cta
    if ( clase.isInstance(cta) ) {
      // fijense que salio facil chequear que la clase coincide
      boolean respuesta = cta.depositar(monto);
      //System.out.println("deposito en cuenta " + nombreClase + " id " + id + " dio " + respuesta);
      return respuesta;
    }

    System.out.println("la cuenta " + id + " no corresponde con el tipo " + clase.getSimpleName() + ", operacion cancelada");
    return false;
  }

  public boolean giro(int monto, int id, Class clase) {
    String nombreClase = clase.getSimpleName();
    if ( ( nombreClase.equals(CtaCte.class.getSimpleName()) ||
           nombreClase.equals(CtaAhorro.class.getSimpleName())) == false ) {
      System.out.println("Tipo " + nombreClase + " no admite giros");
      return false;
    }

    int idx = getIdxCta(id); // buscamos cta para girar
    if (idx == -1) {
      System.out.println("Cliente " + this.numCedula + " " + this.nombre + " no tiene cuenta  " + id + ", operacion cancelada");
      return false; // no encontro la cuenta
    }

    Cuenta cta = cuentas.get(idx); // tenemos la cta
    if ( clase.isInstance(cta) ) {
      // fijense que salio facil chequear que la clase coincide
      boolean respuesta = cta.girar(monto);
      //System.out.println("giro en cuenta" + cta.getClass() + " listo");
      return true;
    }

    System.out.println("la cuenta " + id + " no corresponde con el tipo " + clase.getSimpleName() + ", operacion cancelada");
    return false;
  }
  
  // clase para fines de testeo
  public static void main (String[] args) {
    System.out.println("cliente:");
    Cliente cli1 = new Cliente("rapa", 123);
    System.out.println(cli1);

    System.out.println("\ncta cte:");
    cli1.agregarCtaCte();
    System.out.println("Cuentas corrientes:" + cli1.listarCuentas(CtaCte.class));
    cli1.borrarCuenta(1000, CDT.class);
    cli1.borrarCuenta(1000, CtaAhorro.class);
    cli1.borrarCuenta(1000, CtaCte.class);
    cli1.agregarCtaCte();
    cli1.deposito(500, 1001, CtaCte.class);
    System.out.println(cli1);

    double interesAhorro = 0.006;
    System.out.println("\ncta ahorros:");
    cli1.agregarCtaAhorro(interesAhorro);
    System.out.println("Cuentas de ahorro:" + cli1.listarCuentas(CtaAhorro.class));
    cli1.borrarCuenta(1002, CtaAhorro.class);
    cli1.agregarCtaAhorro(interesAhorro);
    cli1.deposito(250, 1003, CtaAhorro.class); System.out.println(cli1);
    cli1.deposito(350, 1003, CtaAhorro.class); System.out.println(cli1);

    System.out.println("\ncertificados:");
    cli1.agregarCDT(400, 3*interesAhorro);
    System.out.println("Certificados:" + cli1.listarCuentas(CDT.class));
    System.out.println(cli1);
    cli1.cerrarCDT(1004,1002);
    cli1.cerrarCDT(1004,1006);
    cli1.cerrarCDT(1004,1001);
    System.out.println(cli1 + "\n");

    System.out.println("\ngiros:");
    cli1.giro(100, 1001, CtaCte.class);
    cli1.giro(100, 1003, CtaAhorro.class);
    System.out.println(cli1 + "\n");

    cli1.simulaMes(); System.out.println(cli1 + "\n");
    cli1.simulaMes(); System.out.println(cli1 + "\n");
    cli1.simulaMes(); System.out.println(cli1 + "\n");
    cli1.listarCuentas();
  }

}