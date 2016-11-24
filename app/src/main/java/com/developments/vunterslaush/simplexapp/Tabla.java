package com.developments.vunterslaush.simplexapp;

import com.developments.vunterslaush.simplexapp.Ecuacion;
import com.developments.vunterslaush.simplexapp.Planteamiento;
import com.developments.vunterslaush.simplexapp.Renglon;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/*
  Clase Tabla: Clase encargada de la ejecucion de la solucion del problema mediante el algoritmo
  			   simplex, esta clase se encarga de contener los diversos objetos de tipo renglon
  			   que conformaran la tabla o tableau simplex para la resolucion del problema, la misma
  			   se encarga de llamara a la interfaz de operaciones de los renglones y de dar a la
  			   clase resultado un estado de la tabla en cada iteracion de la resolucion del problema

  		Atributos:
			ArrayList<String> formato: Representa el formato de la tabla, es decir todas y cada
									   una de las variables que influyen en el problema en cuestion
									   este formato lo debe poseer todos y cada uno de los renglones
									   para que sea posible operar entre ellos mismos, pj
									   si el problema es
									   Max Z = X1 + X2
									   R1 = x1 - x2 <= 60
									   R2 = 1/2x1 > 140
									   El Formato seria {z,x1,x2,s1,s2,LD}
									   donde se puede observar que van las variables del problema
									   (z,x1,x2) y ademas las variables al estandarizar (s1,s2),
									   como ademas la variable que representa el Lado derecho
									   de cada renglon (LD).


			HashMap<String, Integer> variablesBasicas: una lista de Hashes que representa las
													   variables basicas, esta variable como
													   es un HashMap(lista de Hashes o Map de
													   hashes), almacena varios pares clave valor
													   que representan las variables basicas, donde
													   cada par clave valor tiene el renglon y
													   que variable del formato es, pj podria
													   almacenar algo como esto
													   {[x1,1], [s2,2]} donde la primera variable
													   basica es x1 y en el renglon 1 su celda es
													   1 es decir el renglon 1 es el que tiene
													   su valor.

			Pivote<String, Integer>  pivote: Hash que almacena un par clave valor que representa
											 el pivote de cada iteracion, asi como en las variables
											 basicas este par clave valor guarda la variable y
											 el renglon.

			Renglon renglonCero: Objeto Renglon que almacena el estado del renglon Cero
							     en las iteraciones pertinentes a la resolucion del problema

			ArrayList<Renglon> 	renglones: Lista de todos los renglones de la tabla del
										   problema en cuestion, estos renglones son los que
										   iran sieendo alterados con respecto a cada iteracion
										   del algoritmo Simplex.

			Planteamiento planteamiento: Objeto de tipo planteamiento que almacena el planteamiento
										 del problema en cuestion
										 (Ver Documentacion Clase Plateamiento)

			Resultado   interfazResultado: Objeto Resultado donde se presenta el resultado y todas
										   y cada una de las tablas de las iteraciones realizadas
										   por el metodo de resolucion del problema como tambien
										   la solucion optima al problema de programacion lineal
										   en cuestion. (ver Clase Resultado)


			String 	operaciones: Objeto de tipo String que almacena una cadena de texto
								 que representa las operaciones elementales de renglones
								 hechas en cada una de las iteraciones.
								 Nota: Solo Almacena cada iteracion, luego procede a borrar
								 lo anterior guardado.


			JFrame  padre: Objeto de tipo JFrame que almacena el frame padre o frame
						   main de la aplicacion, esta referencia permite a la clase
						   tabla invocar como hijo a un frame de la clase resultado.


		Metodos:
			public Tabla(Planteamiento planteamiento, JFrame padre)
			throws SinSolucionFactible	: Constructor principal y unico que recibe el planteamiento
										  global del problema(Restricciones y funcion objetivo) y
										  ademas una referencia u objeto que representa al frame
										  padre o frame principal de la aplicacion, este constructor
										  se encarga de inicializar todos los renglones de la tabla,
										  y antes de esto generar el formato adecuado para la misma
										  ademas, invoca al metodo de resolucion del problema
										  con el cual puede o no enviar una excepcion
										  SinSolucionFactible.

			private void generarFormato() : metodo encargado de generar la lista de cadenas de
											caracteres (ArrayList<String>) que representa el formato
											de la tabla.

			private void generarRenglones() : Metodo encargado de proceder con el planteamiento
											  del problema a generar todos los renglones que estaran
											  en la tabla de la resolucion del problema
											  (ver Clase Renglon)

			private void generarVariablesBasicas(): Metodo encargado de generar con el estado actual
													de la tabla por cada iteracion, las variables
													que son basicas en el problema.


			private boolean esVariableBasica(String variable): Metodo que indica si un String
															   que representa a una variable en
															   del formato de la tabla, es basica
															   o no, en el estado actual de la tabla.

			private void configurarRenglones(): Metodo que se encarga de configurar los renglones
												es decir les da una configuracion inicial a los
												renglones.

			private void iniciarInterfaz(): Metodo con el cual se invoca la interfaz del resultado

			private void resolverPlanteamiento()
			throws SinSolucionFactible			 : Metodo principal de la resolucion del problema
												   este metodo es el encargado de llamar a los
												   algoritmos generales de resolucion
												   (Dual Simplex o Simplex), primero evalua si el
												   problema posee caracteristicas para resolverse
												   con DualSimplex si cumple Procede a llamar
												   al algoritmo para la resolucion, sino,
												   evalua si el algoritmo se puede resolver con simplex
												   y procede a llamar al metodo que se encarga de
												   operar sobre los renglones, ademas
												   se encarga de lanzar las excepciones pertinentes
												   que pueden darse en la ejecucion de cualquiera
												   de los 2 metodos de resolucion.


			private int simplexDual()
			throws SinSolucionFactible  : Metodo principal para la resolucion del problema en cuestion
										  este metodo se encarga de aplicar el algoritmo del metodo
										  Dual Simplex, con la tabla en cuestion, itera mientras existan
										  valores negativos en el lado derecho y busca el pivote en
										  cuestion y opera cada uno de los renglones, esto hasta que
										  no existan negativos en el lado derecho.

			private int simplex(int iteraciones)
			throws SinSolucionFactible			: se Encarga de aplicar el algoritmo simplex,
												  itera mientras existan negativos en el renglon
												  cero, busca el pivote en cuestion y opera.


			private void operar(int iteraciones): Se encarga de llamar a las funciones de las
												  operaciones y ademas de generar texto
												  pertinente a la operaciones de renglon
												  realizadas en esa iteracion.

			private boolean conSolucionesMultiples(): funcion que indica si el problema tiene
													   o no multiples soluciones.

			private ModeladoTabla modeloTablaActual(): Funcion que se encarga de generar una
													   copia de la tabla por cada iteracion
													   esto para modelado de la interfaz.

			private void generarOperaciones(): se encarga de generar las operaciones pertinentes
											   con respecto al pivote.

			private void generarPrimeraOperacion(): Se encarga de generar la primera operacion
													en el renglon donde se encuentra el pivote
													para hacer 1 al mismo.

			private int contarCeros(String columna): Metodo que cuenta los ceros existentes
													 en una columna o variable dada de la
													 tabla.

			private Renglon buscarUnidad(String columna): Metodo que retorna el renglon
														  donde el valor es 1 en la columna
														  dada.


			private void buscarPivote()
			throws SinSolucionFactible  : metodo encargado de buscar el pivote para el
										  algoritmo Dual Simplex, buscando el reglon
										  con Lado derecho menor, y con el valor menor
										  dividiendo el valor en el renglon cero.

			private String buscarVariablePivote
			(Renglon pivote, Renglon cero)		: Metodo que se encarga de evaluar un renglon
												  diviendo cada celda de cada columna con su
												  respectivo valor en el renglon cero, buscando
												  el valor menor dado en la division.

			private int buscarLDmenor(): Metodo que se encarga de evaluar que renglon posee
										 el lado derecho menor entre todos.

			private void buscarPivoteS()
			throws SinSolucionFactible  : Metodo que se encarga de buscar el pivote para el
										  algoritmo Simplex, buscando el menor valor en el
										  renglon cero y el dividido menor de esa columna
										  con respecto al lado derecho.

			private String buscarRceroMenor(): Busca el menor valor del renglon cero.

			private int renglonLDdivMin(String variable): Busca la celda de la columna
														  del renglon cero con el menor valor,
														  en la cual la celda tiene el valor
														  min al dividir el lado derecho entre
														  esa celda.

			private int contarColumnaNoValida(String columna): Cuenta todos los valores negativos
																de una columna.

			private int contarLDNegativos(): cuenta la cantidad de celdas que tienen valor negativo
											 en la columna del lado derecho.

			private String solucionFactible(): Metodo que se encarga de retornar un String con la
											   solucion Optima Factible del problema en
											   cuestion.

  fecha: Febrero 2016
  @author Jesus Mota
 */
public class Tabla
{
    ArrayList<String> 	     formato;
    HashMap<String, Integer> variablesBasicas;
    Pivote<String, Integer>  pivote;
    Renglon 				 renglonCero;
    ArrayList<Renglon> 		 renglones;
    Planteamiento            planteamiento;
    String 					 operaciones;


    public Tabla(Planteamiento planteamiento) throws SinSolucionFactible
    {
        this.planteamiento = planteamiento;
        renglones = new ArrayList<Renglon>();
        variablesBasicas = new HashMap<String,Integer>();
        generarFormato();
        generarRenglones();
        generarVariablesBasicas();
        configurarRenglones();
        //iniciarInterfaz();
        resolverPlanteamiento();
    }

    private void generarFormato()
    {
        formato = planteamiento.retornarVariables();
        formato.add(0,planteamiento.getFuncionObjetivo().getVFO());
        formato.add(Ecuacion.LADO_DERECHO);
    }

    private void generarRenglones()
    {
        Renglon nuevo;
        renglonCero = new Renglon(planteamiento.getFuncionObjetivo(),formato);

        for (Ecuacion ec : planteamiento.retornarFormasEstandar())
        {
            nuevo = new Renglon(ec,formato);
            renglones.add(nuevo);
        }
        renglones.add(0,renglonCero);
    }

    private void generarVariablesBasicas()
    {
        variablesBasicas.clear();

        for (String variable : formato )
        {
            if(esVariableBasica(variable))
            {
                variablesBasicas.put(variable, renglones.indexOf(buscarUnidad(variable)));
            }
        }
    }

    private boolean esVariableBasica(String variable)
    {
        boolean demasRenglonesCero = (contarCeros(variable) == renglones.size()-1);

        boolean existeUnidad = (buscarUnidad(variable) != null);

        boolean noEstaeseRenglon =	!(variablesBasicas.
                containsValue(renglones.indexOf(buscarUnidad(variable))));

        return (demasRenglonesCero && existeUnidad && noEstaeseRenglon);
    }

    private void configurarRenglones()
    {
        for (HashMap.Entry<String, Integer> entry : variablesBasicas.entrySet())
        {
            if(renglones.get(entry.getValue()).getValorAt(entry.getKey()) == -1)
                renglones.set(entry.getValue(),
                        renglones.get(entry.getValue()).multiplicarPor(-1.0));
        }
    }

    /*
    private void iniciarInterfaz()
    {
        interfazResultado = new Resultado((formato.size()+2),(renglones.size()+1),padre);
    }*/


    private void resolverPlanteamiento() throws SinSolucionFactible
    {
        //interfazResultado.addTexto(planteamiento.toString());

        int iteraciones = simplexDual();

        if(renglonCero.negativos() > 0)
        {
            iteraciones = simplex(iteraciones);
        }

        if(iteraciones > 0 && renglonCero.negativos() == 0)
        {


            //interfazResultado.addTabla(modeloTablaActual());
            //interfazResultado.asignarSolucion(solucionFactible());
            if(conSolucionesMultiples())
            {

            }
            //interfazResultado.setVisible(true);
        }
        else
        {
            System.out.println(iteraciones);
            throw new SinSolucionFactible("El Planteamiento Ya es Optimo");
        }
    }

    private int simplexDual()  throws SinSolucionFactible
    {
        int iteraciones = 0;
        while(contarLDNegativos() != 0)
        {
            iteraciones++;
           // interfazResultado.addTabla(modeloTablaActual());
            buscarPivote();
            operar(iteraciones);
           // interfazResultado.addTexto(operaciones);
        }
        return iteraciones;
    }

    private int simplex(int iteraciones) throws SinSolucionFactible
    {
        while(renglonCero.negativos() > 0)
        {
            iteraciones++;
            //interfazResultado.addTabla(modeloTablaActual());
            buscarPivoteS();
            operar(iteraciones);
            //interfazResultado.addTexto(operaciones);
        }
        return iteraciones;
    }

    private void operar(int iteraciones)
    {
        operaciones = "Operaciones "+iteraciones+" : \n\n";
        operaciones += "Pivote: "+"[R"+ pivote.getValue()+", Variable: "+pivote.getKey()+"]\n\n";
        generarOperaciones();
        generarVariablesBasicas();
    }

    private boolean conSolucionesMultiples()
    {
        double valor;
        for (String f : formato )
        {
            valor = renglonCero.getValorAt(f);
            if(valor == 0 && variablesBasicas.get(f) == null && !f.contains("s") )
            {

                System.out.println(f+"VS"+variablesBasicas.get(f));
                return true;
            }

        }
        return false;
    }

    /*
    private ModeladoTabla modeloTablaActual()
    {
        ArrayList<String> f =(ArrayList<String>)formato.clone();
        HashMap<String,Integer> vb = (HashMap<String,Integer>)variablesBasicas.clone();
        return new ModeladoTabla(f,renglones,vb);
    }*/

    private void generarOperaciones()
    {
        generarPrimeraOperacion();
        for (int i = 0; i < renglones.size(); i++)
        {
            if(i!= pivote.getValue())
                generarOperacion(i);
        }
    }

    private void generarPrimeraOperacion()
    {
        int i = pivote.getValue();
        String v = pivote.getKey();
        double valorPivote = renglones.get(i).getValorAt(v);
        Renglon renglonR = renglones.get(i);
        if(valorPivote != 1)
        {
            operaciones += "R"+i+" = R"+i+"/"+valorPivote+"\n\n";
            renglonR = renglonR.dividirPor(valorPivote);
            renglones.set(i,renglonR);
        }
    }

    private void generarOperacion(int i)
    {
        String v = pivote.getKey();
        Renglon renglonR = renglones.get(i);
        Renglon rPivote = renglones.get(pivote.getValue());
        Double valor = renglonR.getValorAt(v);
        if(valor != 0)
        {
            if(valor == 1)
            {
                renglonR.restarRenglon(rPivote);
                operaciones+= "R"+i+" = "+ "R"+i+" - "+"R"+pivote.getValue()+"\n\n";
            }
            else
            {
                renglonR.sumarRenglon(rPivote.multiplicarPor(-valor));
                operaciones+= "R"+i+" = "+ "R"+i+" + "+
                        "("+(-valor)+" * "+"R"+pivote.getValue()+")\n\n";
            }
        }
        if(i == 0)
            renglonCero = renglonR;
    }

    private int contarCeros(String columna)
    {
        int c = 0;
        for (Renglon r : renglones)
        {
            if(r.getValorAt(columna) == 0)
                c++;
        }
        return c;
    }

    private Renglon buscarUnidad(String columna)
    {
        for (Renglon r : renglones)
        {
            if(r.getValorAt(columna) == 1 || r.getValorAt(columna) == -1)
                return r;
        }
        return null;
    }

    private void buscarPivote() throws SinSolucionFactible
    {

        int renglonMenor = buscarLDmenor();
        Renglon renglonPivote = renglones.get(renglonMenor);
        if(renglonPivote.negativos() == 0)
            throw new SinSolucionFactible("El Algoritmo No tiene Solucion Factible");

        renglonCero = renglones.get(0);

        String variablePivote = buscarVariablePivote(renglonPivote, renglonCero);

        pivote = new Pivote<String, Integer>(variablePivote, renglonMenor);
    }

    private String buscarVariablePivote(Renglon pivote, Renglon cero)
    {
        double menor = 999999999.000;
        double division = 0;
        String retorno = "";
        for (String variable : formato )
        {
            if(pivote.getValorAt(variable) < 0)
            {
                division = cero.getValorAt(variable)/pivote.getValorAt(variable);
                division = Math.abs(division);
                if(division < menor && !variable.equals(Ecuacion.LADO_DERECHO))
                {
                    menor = division;
                    retorno = variable;
                }
            }
        }
        return retorno;
    }

    private int buscarLDmenor()
    {
        int r = 1;
        double valor = renglones.get(1).getValorAt(Ecuacion.LADO_DERECHO);
        for (int i = 2; i<renglones.size(); i++)
        {
            if(renglones.get(i).getValorAt(Ecuacion.LADO_DERECHO) < valor)
            {
                r = i;
                valor = renglones.get(i).getValorAt(Ecuacion.LADO_DERECHO);
            }
        }
        return r;
    }

    private void buscarPivoteS() throws SinSolucionFactible
    {
        String columnaPivote = buscarRceroMenor();

        if(contarColumnaNoValida(columnaPivote) == renglones.size()-1 )
            throw new SinSolucionFactible("El Algoritmo No tiene Solucion Factible");

        int renglonPivote = renglonLDdivMin(columnaPivote);
        pivote = new Pivote<String, Integer>(columnaPivote, renglonPivote);
    }

    private String buscarRceroMenor()
    {
        Double valor = 0.0;
        Double menor = 0.0;
        String columnaMenor = "";
        for (String f : formato )
        {
            valor = renglonCero.getValorAt(f);
            if(valor < 0 && valor < menor && !f.equals(Ecuacion.LADO_DERECHO))
            {
                menor = valor;
                columnaMenor = f;
            }
        }
        return columnaMenor;
    }
    private int renglonLDdivMin(String variable)
    {
        double valor = 0.0;
        double ld = 0.0;
        int renglon = 1;
        double menor = 99999999.9;
        for (int i = 1; i<renglones.size();i++)
        {
            valor = renglones.get(i).getValorAt(variable);
            ld = renglones.get(i).getValorAt(Ecuacion.LADO_DERECHO);
            if(valor > 0 && menor > (ld/valor) )
            {
                menor = ld/valor;
                renglon = i;
            }
        }
        return renglon;
    }
    private int contarColumnaNoValida(String columna)
    {
        int c = 0;
        for(int i = 1; i<renglones.size(); i++)
        {
            if(renglones.get(i).getValorAt(columna) <= 0)
                c++;
        }
        return c;
    }

    private int contarLDNegativos()
    {
        int c = 0;
        for (int i = 1; i<renglones.size(); i++)
        {
            if(renglones.get(i).getValorAt(Ecuacion.LADO_DERECHO) < 0)
                c++;
        }
        return c;
    }

    private String solucionFactible()
    {
        String solucion = "";
        Double valor = 0.0;
        for (HashMap.Entry<String, Integer> entry : variablesBasicas.entrySet())
        {
            if(!entry.getKey().contains("s"))
            {
                valor = renglones.get(entry.getValue()).getValorAt(Ecuacion.LADO_DERECHO);
                if(valor<0)
                    valor *= -1;

                valor = (double)Math.round(valor * 100) / 100;

                solucion += entry.getKey()+" = "+valor+" \n";
            }
        }
        return solucion;
    }

}

class SinSolucionFactible extends Exception
{
    private String message;
    public SinSolucionFactible(String message)
    {
        super(message);
        this.message = message;
    }
    public String toString()
    {

        return message;
    }
}


final class Pivote<K, V> implements Map.Entry<K, V>
{
    private final K key;
    private V value;

    public Pivote(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }
}