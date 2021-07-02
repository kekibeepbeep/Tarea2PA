public class CtaAhorro extends Cuenta {

  public CtaAhorro() {
    super();
  }

  public CtaAhorro(double interes) {
    super(interes);
  }

  public String toString() {
    return ">>id: " + id + ", saldo: " + saldo + ", interes: " + interes + "<<";
  }

  public void cerrar(Cuenta cta) {
    // no existe esta funcion en cta ahorro
  }

  public void calcInteres() {
    double interesAbs = this.saldo*this.interes;
    //System.out.println("interesAbs = " + interesAbs);
    this.saldo += interesAbs;
    //System.out.println("this.saldo = " + this.saldo);

    // pregunta: se puede factorizar este codigo?
  }

  public boolean depositar(int monto) {
    if (monto >= 0) {
      this.saldo += monto;
      this.tieneDeposito = true;
      return true;
    }
    return false;
  }

  public boolean girar(int monto) {
    if ( (monto >= 0) && (monto <= this.saldo) ) {
      this.saldo -= monto;
      return true;
    }
    return false;
  }
}