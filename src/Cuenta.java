import java.io.Serializable;

abstract public class Cuenta implements Operaciones, Serializable{
  // atributos de la clase
  static protected int correlativo = 1000;
  // para controlar los id de las cuentas cte, ahorro o CDT

  protected final int id;
  protected int saldo;
  protected boolean tieneDeposito;
  protected double interes;

  // como se ve, al haber atributos si definicion por omision, hay que escribir los constructores
  public Cuenta(double interes) {
    this.id = correlativo++;
    this.saldo = 0;
    this.interes = interes;
    this.tieneDeposito = false;
    // ojo con que este constructor efectivamente hace todo el trabajo. Tiene que haber algun constructor que no reuse otros constructores, en este caso es este
  }

  public Cuenta() {
    // vamos a considerar que si se crea un objeto por omision, el interes es 0.0
    this(0.0); // pero como el resto del constructor funciona igual, reusamos el codigo del otro constructor
  }

  // una clase abstracta no necesariamente tiene todos sus metodos abstractos, aca se muestran los metodos que estan completamente definidos
  public int getId() {
    return id;
  }

  public int consultaSaldo() {
    return this.saldo;
  }

  // y aca se muestran los metodos que quedan abstractos
  abstract public String toString();
  abstract public void calcInteres();
  abstract public void cerrar(Cuenta cta);

  // note que no es necesario decir nada sobre los metodos que vienen de la interfaz Operaciones, porque ya se dijo implements Operaciones

  // clase para fines de testeo
  public static void main (String[] args) {
    System.out.println("probando clase Cuenta");
    // la siguiente linea es imposible, porque esta es una clase abstracta
    //Cuenta cta = new Cuenta();
  }
}