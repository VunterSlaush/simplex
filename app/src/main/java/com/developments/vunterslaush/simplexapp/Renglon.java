package com.developments.vunterslaush.simplexapp;

import com.developments.vunterslaush.simplexapp.Ecuacion;

import java.util.ArrayList;

/*
  Clase Renglon: Clase que Representa un Renglon o Fila  de la Tabla del PL,
  			     esta clase es la encargada de las operaciones Elementales
  			     de renglon, sobre si misma, es decir esta es la clase que
  			     representa unitariamente cada fila de las tablas, y ademas
  			     las operaciones de cada iteracion se hacen sobre esta misma.
  			     Nota: Los Renglones se Forman o se Crean a partir de un Objeto
  			     Ecuacion, y un formato, si en la ecuacion no estan presentes
  			     algunas variables que si estan en el formato, el renglon
  			     igual tiene esas variables pero con su valor en 0.

  Atributos:

  	ArrayList<String> formato: Representa el formato o la cantidad de variables
  							   y las variables que tendra la tabla, ya que todos
  							   los renglones tienen que tener el mismo formato
  							   (Misma cantidad de Columnas, con Mismos Nombres),
  							   para que puedan operar entre si.

	ArrayList<Double> valoresPorColumna: Representa o tiene Todos y cada uno de los
										 valores que tiene dicho renglon en dicha colu-
										 mna, esto quiere decir que pj el Formato es
										 {z,x1,x2,s1,s2,LD}, el indice del valor 2 es
										 el valor respectivo a la columna X2 en el dicho
										 renglon.

  Metodos:

	public Renglon(Ecuacion ec, ArrayList<String> formato): Constructor Principal
															recibe la ecuacion y
															el formato con las variables
															de todo el problema y
															se encarga de convertir esto
															a un Renglon de la tabla.

	public Renglon(Renglon cpy): Constructor secundario, a partir de un renglon
								 crea una Copia en Memoria del mismo, esto
								 para no operar en la deferencia de un mismo renglon.

	private void organizarValoresColumna(Ecuacion ec): Funcion encargada de Recibir la
													   ecuacion y mediante dicha ecuacion
													   buscar los valores con el formato
													   ya pasado al constructor para
													   darle sentido a los Valores por
													   columna del Renglon, es decir
													   Rellenar los valores de Cada columna
													   con respecto a la ecuacion dada.
													   Recibe: Ecuacion Referente al Renglon.

	public ArrayList<String> retornarFormato(): Retorna el Formato (LISTA DE VARIABLES)
												del renglon en cuestion.

	private void multiplicarValores(Double num): Esta funcion se encarga de Multiplicar
												 Todos los valores de "ValoresPorColumna"
												 por un Double dado a la funcion, esto
												 para modificar los valores del renglon
												 en las Operaciones de cada Iteracion.
												 Recibe: Multiplicador.

	public String toString(): Convierte el Renglon a un String representativo y lo
							  retorna, en este String esta escrito de la siguiente
							  Forma "Variable":"valor".

	public int negativos(): Retorna la Cantidad de valores negativos que hay en el renglon
							sin contar la columna LD.

	public Double getValorAt(String columna): Retorna el Valor Numerico que se Encuentra
											  en la columna o nombre de variable pasada
											  por el parametro.
											  Recibe: Nombre de Variable o Columna
											  Retorna: Valor Numerico.

	public Double getValorAt(int i): Retorna el Valor numerico que se encuentre en el
									 indice pasado por el parametro, por ejemplo
									 el formato es {z,x1,x2,s1,LD}, y i = 4, el metodo
									 retornara el valor que tiene el renglon en la columna
									 LD.
									 Recibe: Indice de Columna.
									 Retorna: Valor Numerico.

	public ArrayList<Double> retornarValores(): Retorna la Lista con todos los valores
												de cada columna del Renglon en cuestion.

	//OPERACIONES A LOS RENGLONES: Todas las operaciones hechas al renglon implican
								   Operar en todos y cada uno de los valores de cada
								   columna o variable del formato.

	public Renglon multiplicarPor(Double num): Metodo que mediante un parametro que re
											   presenta la multiplicacion a hacerle a algun
											   renglon retorna una copia de ese mismo renglon
											   pero multiplicada por el numero que fue pasado
											   por parametro.
											   Recibe: Valor Numerico(Multiplicador)
											   Retorna: Copia del Renglon ya multiplicada.

	public Renglon dividirPor(Double num):  Metodo que mediante un parametro que re
										    presenta la division a hacerle a algun
										    renglon retorna una copia de ese mismo renglon
										    pero dividida por el numero que fue pasado
										    por parametro.
										    Recibe: Valor Numerico(Dividendo)
										    Retorna: Copia del Renglon ya divida.

	private void dividirValores(Double num): Metodo que mediante un Parametro numerico
											 procede a dividir todos y cada uno de los
											 valores del renglon, y siendo el resultado
											 de esta division ahora ese valor en dicha
											 columna que fue dividida.

	public void sumarRenglon(Renglon suma): Metodo que recibe un Renglon y lo suma al
											del renglon al que se llama el metodo, esto
											para realizar la suma de un renglon y otro,
											donde al renglon que se le llama el metodo,
											se le suman o se restan dependiendo de los
											signos de cada valor en cada variable.
											Recibe: Renglon que se sumara.

	public void restarRenglon(Renglon resta): Metodo que realiza la misma accion que
											  el metodo sumarRenglon(), pero antes de
											  sumar el renglon este es multiplicado por
											  -1 para simular la resta de renglones.

	private void sumarValores(ArrayList<Double> valores): Metodo que Recibe una lista
														  de valores y procede a operarles
														  la suma a cada uno de los valores
														  del renglon, uno a uno.
														  Recibe: Lista de Valores.


  fecha: Febrero 2016
  @author Jesus Mota y Johans Cedeño
*/
public class Renglon
{
    ArrayList<String> formato;
    ArrayList<Double> valoresPorColumna;


    public Renglon(Ecuacion ec, ArrayList<String> formato)
    {
        this.formato = formato;
        valoresPorColumna = new ArrayList<Double>();
        organizarValoresColumna(ec);
    }
    public Renglon(Renglon cpy)
    {
        formato = cpy.retornarFormato();
        valoresPorColumna = (ArrayList<Double>) cpy.retornarValores().clone();
    }

    public ArrayList<String> retornarFormato()
    {
        return formato;
    }

    private void organizarValoresColumna(Ecuacion ec)
    {
        for (String str : formato )
        {
            if(ec.buscarValor(str) == null)
                valoresPorColumna.add(0.0);
            else
                valoresPorColumna.add(ec.buscarValor(str));
        }

        if(ec.esFuncionObjetivo() && ec.tipoFuncionObjetivo())
        {
            multiplicarValores(-1.0);
            valoresPorColumna.set(0,1.0); // Z o Z prima igual a 1
        }
    }

    private void multiplicarValores(Double num)
    {
        Double aux;
        for (int i = 0 ; i<valoresPorColumna.size() ; i++)
        {
            aux = valoresPorColumna.get(i);
            if(aux != 0)
                aux *= num;
            valoresPorColumna.set(i,aux);
        }
    }

    public String toString()
    {
        String retorno = "";
        for (int i = 0;i<formato.size();i++)
        {
            retorno+= formato.get(i) +" : "+valoresPorColumna.get(i)+"\n";
        }
        return retorno;
    }
    public int negativos()
    {
        int c = 0;
        for (int i=0; i<valoresPorColumna.size(); i++)
        {
            Double d = valoresPorColumna.get(i);
            if(d<0 && !formato.get(i).equals(Ecuacion.LADO_DERECHO))
                c++;
        }
        return c;
    }

    public Double getValorAt(String columna)
    {
        return valoresPorColumna.get(formato.indexOf(columna));
    }

    public Double getValorAt(int i)
    {
        return  valoresPorColumna.get(i);
    }

    public ArrayList<Double> retornarValores()
    {
        return valoresPorColumna;
    }

    public Renglon multiplicarPor(Double num)
    {
        Renglon multiplicado = new Renglon(this);
        multiplicado.multiplicarValores(num);
        return multiplicado;
    }
    public Renglon dividirPor(Double num)
    {
        Renglon dividido = new Renglon(this);
        dividido.dividirValores(num);
        return dividido;
    }

    private void dividirValores(Double num)
    {
        Double aux;
        for (int i = 0 ; i<valoresPorColumna.size() ; i++)
        {
            aux = valoresPorColumna.get(i);
            if(aux != 0)
                aux /= num;
            valoresPorColumna.set(i,aux);
        }
    }

    public void sumarRenglon(Renglon suma)
    {
        sumarValores(suma.retornarValores());
    }

    public void restarRenglon(Renglon resta)
    {
        sumarRenglon(resta.multiplicarPor(-1.0));
    }

    private void sumarValores(ArrayList<Double> valores)
    {
        Double aux;
        for (int i = 0 ; i<valoresPorColumna.size() ;i++)
        {
            aux = valoresPorColumna.get(i);
            aux += valores.get(i);
            valoresPorColumna.set(i,aux);
        }
    }



}
