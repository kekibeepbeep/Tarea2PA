public class CDT extends Cuenta {

  public CDT() {
    super();
  }

  public CDT(double interes) {
    super(interes);
  }

  public String toString() {
    return ">>id: " + id + ", saldo: " + saldo + ", interes: " + interes + "<<";
  }

  public void cerrar(Cuenta cta) {
    if (cta instanceof CtaCte) {
      //System.out.println("clase permitida");
      cta.depositar(this.saldo);
      this.saldo = 0;
    }
    else {
      System.out.println("Error. La cuenta " + cta + " es de tipo " + cta.getClass() + ". Solo se permite traspasar fondos a una Cuenta Corriente");
    }
  }

  public void calcInteres() {
    double interesAbs = this.saldo*this.interes;
    //System.out.println("interesAbs = " + interesAbs);
    this.saldo += interesAbs;
    //System.out.println("this.saldo = " + this.saldo);

    // pregunta: se puede factorizar este codigo?
  }

  public boolean depositar(int monto) {
    if (!this.tieneDeposito) {
      if (monto >= 0) {
        this.saldo += monto;
        this.tieneDeposito = true;
        return true;
      }
      return false;
    }
    System.out.println("Ya se hizo el primer deposito");
    return false;
  }

  public boolean girar(int monto) {
    // no existe esta funcion en CDT
    return false;
  }

  
}