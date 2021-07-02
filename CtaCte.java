 // como Cuenta implementa Operaciones, no es necesario poner implements Operaciones aca
 public class CtaCte extends Cuenta {

  // Ojo que es necesario sobreescribir explicitamente los constructores, pues la clase base tiene varios atributos por definir y ademas tiene dos constructores. Por lo tanto, el compilador no tiene informacion suficiente para saber que constructor usar. Notar que con super se puede reusar completamente el constructor de la clase base. Asi que a la larga no es tanto trabajo extra.
  public CtaCte() {
    super();
  }

  public CtaCte(double interes) {
    super(0.0);
  }

  // implementacion de metodos abstractos de clase base Cuenta
  public String toString() {
    return ">>id: " + id + ", saldo: " + saldo + "<<";
  }

  public void cerrar(Cuenta cta) {
    // no aplica esta funcion en cta cte
  }

  public void calcInteres() {
    // la cta cte no acumula intereses
  }
  
  // implementacion de metodos definidos en la Interfaz Operaciones
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

  // clase para fines de testeo
  public static void main (String[] args) {
    System.out.println("probando clase CtaCte");
    // la siguiente linea si se puede hacer, porque CtaCte implementa a tanto a los metodos abstractos de Cuenta como los metodos de la interfaz Operaciones

    // creamos una cuenta
    Cuenta cta = new CtaCte();
    System.out.println(cta);

    // creamos otra mas para seguir probando cosas
    Cuenta cta2 = new CtaCte();
    System.out.println(cta2);
    System.out.println("" + cta2.depositar(10) + cta2);
    System.out.println("" + cta2.girar(10) + cta2);
    System.out.println("" + cta2.girar(10) + cta2);
    System.out.println("" + cta2.depositar(20) + cta2);
  }
}