interface Operaciones {
  // Como es una interfaz, los artibutos son estaticos y constantes
  public double interesNulo = 0.0; // estatico y constante por omision, y double para que el compilador no alegue perdidad de conversion
  
  // prototipos de metodos, especifica valor de retorno, nombre de la función y argumentos. Sin cuerpo.
  public boolean depositar(int monto);
  public boolean girar(int monto);
}