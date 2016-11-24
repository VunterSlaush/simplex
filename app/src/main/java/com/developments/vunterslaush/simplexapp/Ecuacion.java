package com.developments.vunterslaush.simplexapp;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
  Clase Ecuacion: Clase Encargada de la Validacion, Transformacion y tratamiento
                  de las Entradas de Texto del Usuario, Es decir esta es la clase
                  que se encarga de convertir el texto plano que es ingresado a
                  la aplicacion en Inecuaciones (Restricciones) y en Funciones
                  Objetivos, en un formato adaptado para su manejo y aplicacion
                  en el programa, esto mediante el mecanismo de Expresiones
                  Regulares, para seleccionar asi las cadenas de caracteres validas
                  para el programa, en Resumen esta clase es la encargada
                  de convertir en texto mediante Expresiones regulares en una
                  estructura u objeto que puede ser manejado de manera mas simple por
                  el programa para su operacion y manejo de los valores.


  Atributos:

    REGEX : Significa Expresion Regular, abrevitura del ingles Regular Expresion(REGEX)

    NOTA: Cada una de las Constantes Definidas reprensentan el valor
          que tienen como nombre, por ejemplo REGEX_MONOMIOS, Representa
          la expresion regular para monomios de una ecuacion, y asi con cada
          una de las constantes definidas en esta clase.

    Signo signoEcuacion : Representa el Signo de la ecuacion de la ecuacion en cuestion,
                          pj, MayorIgual para inecuaciones (Restricciones) con signo
                          mayor o igual, igual para ecuaciones con signo igual.

    Boolean esFuncionObjetivo: Booleano que indica si la Ecuacion es o no funcion Objetivo.

    ArrayList<Double> cocientes: Lista de los Valores que acompañan a las variables en los
                                 monomios de cada ecuacion, pj la ecuacio 5x1+5x2, la lista
                                 seria {5.0,5.0}.

    ArrayList<String> variables: Lista de los nombres de las Variables presentes en la
                                 ecuacion en cuestion, pj ejemplo 5w+8h+9k <= 150, la
                                 lista de variables seria {w,h,k}.

    String  variableFobj: Nombre de la variable o de la funcion que representa a la
                          funcion Objetivo por ejemplo Max Y = x1 + x2, el valor
                          de este atributo seria "Y", solo en el caso de que la
                          ecuacion sea una funcion Objetivo, en otro caso esto tendria
                          como valor " ".

    Double  ladoDerecho: Valor numerico de lo que se encuentra al lado derecho
                         de la ecuacion pj X1+x2 >= 150, este atributo tendria
                         como valor 150.0, en el caso de las funciones objetivos
                         este atributo tiene como valor 1.0 .

    String  ecuacion: Estre atributo representa la ecuacion en cuestion como una
                      cadena de caracteres.

    Ecuacion formaEstandar: Atributo de una Ecuacion que Almacena otro objeto
                            de tipo Ecuacion que representa la ecuacion en cuestion
                            pero estandarizada, esto quiere decir que el mismo objeto
                            se Estandariza y guarda la ecuacion generada por los pasos
                            de estandarizacion de un Problema de programacion Lineal.


  Metodos:

    public Ecuacion(String ecuacionEnTexto)
    throws EcuacionNoValida                 : Constructor Principal, se encarga
                                              de procesar el texto ingresado por
                                              el usuario y llamar a los metodos
                                              pertinentes para realizar el formateo
                                              y la conversion del Texto plano
                                              a una ecuacion representable en el sistema,
                                              ademas se encarga de Lanzar o Evaluar las
                                              posibles Excepciones que sucedan en el
                                              proceso de formateo y conversion.

    private String configurarTextoEcuacion(String ecuacionTexto): Metodo encargado de Con-
                                                                  figurar la ecuacion para
                                                                  proceder al proceso de
                                                                  conversion esto mediante
                                                                  la eliminacion de los es
                                                                  pacios de mas en la ecuacion
                                                                  los signos sobrantes, etc etc.

    private String reemplazarFracciones(String ec)
    throws EcuacionNoValida                        : Metodo que se encarga de Reemplazar todas
                                                     y cada una de las fracciones existentes
                                                     en la ecuacion, a modo de mejorar la
                                                     conversion y formateo del texto a la ecuacion
                                                     reemplazando cada ecuacion por su valor
                                                     numerico Real, ademas evalua si existen
                                                     divisiones entre cero, y lanza la correcta
                                                     excepcion de este tipo de Errores.


    private void validarTextoEcuacion(String ecuacionTexto)
    throws EcuacionNoValida                                 : Valida que el texto plano en la
                                                              ecuacion ingresada por el usuario
                                                              no posea caracteres invalidos, no
                                                              tenga caracteres extras, solo tenga
                                                              un signo y no posea el caracter "S"
                                                              o "s", ya que este es usado para las
                                                              variables de Holgura.


    private void convertirTextoEnEcuacion()
    throws EcuacionNoValida                : Metodo principal que se encarga de llamar a los demas
                                             demas metodos auxiliares para convertir el texto
                                             plano recibido por el usuario en una ecuacion repre
                                             sentable, ademas se encarga tambien de evaluar
                                             los posibles errores en la conversion del texto.

    private boolean esFuncionObjetivo(String ec) : Metodo que Retorna True si la ecuacion
                                                   representa una funcion objetiva y false
                                                   si no es el caso

    private boolean hayFracciones(String ec): Retorna True si encuentra alguna Fraccion
                                              en la ecuacion en cuestion.

    private ArrayList<String> extraerSegunRegex(String ec,String regex)

                                            :Metodo encargado de retornar una lista
                                             de Strings de todas y cada una de las ocurrencias
                                             encontradas segun una Expresion Regular.
                                             Recibe Cadena de La Ecuacion
                                                    Cadena de la Expresion Regular.
                                             Retorna Lista de cadenas de Ocurrencias.


    private ArrayList<Double> convertirEnNumerosconComa(ArrayList<String> fraccionesTexto)
    throws EcuacionNoValida
                                    :Metodo encargado de Recibir una lista de cadenas
                                    que representan fracciones y retornar una lista
                                    de la resolucion o el valor decimal flotante que
                                    representa cada una de esas fracciones, pj
                                    la lista de String es {"6/2","8/4","3/2"}
                                    el metodo retornaria {3.0,2.0,1.5}.


    private Double retornarDivision(String fraccion)
    throws EcuacionNoValida                         : Metodo que se encarga de recibir un
                                                      solo String que Representa una fraccion
                                                      y retorna su valor numerico flotante
                                                      por ejemplo recibe "80/4"
                                                      Retornaria 20.0

    private String sustituirFracciones(String ec, ArrayList<Double> sustitutos)

                                    :Metodo Encargado De sustituir todas y cada una de las
                                     fracciones de una cadena, por cada una de sus valores
                                     esto, quitando cada Fraccion del String y sustituyendola
                                     por un "?" y cada "?" sustituirlo por el valor numerico
                                     flotante.

    private boolean sinEses(String str) : Metodo que Retorna true si la ecuacion no Tiene
                                          caracteres 'S' o 's', en caso de que la ecuacion
                                          no sea una forma Estandar.

    private Boolean charsValidos(String stringEvaluado) : Retorna true si el texto que representa
                                                          la ecuacion solo tiene Caracteres validos
                                                          para el Programa.

    private Boolean noExtraChars(String ecuacionTexto): Retorna True si al Remover fracciones
                                                        monomios, variables, el signo, el lado
                                                        derecho, la funcion objetivo y etc, no se
                                                        encuentra ningun otro caracter, de otro
                                                        modo retorna false.

    private Boolean soloUnSigno(String ecuacionTexto): Retorna true si la Ecuacion posee solo 1
                                                       signo, ya sea igual, mayor igual o menor
                                                       o igual.


    private void mapearMonomios(ArrayList<String> monomios)
    throws EcuacionNoValida                                : Metodo encargado de convertir los
                                                             monomios extraidos del texto de la
                                                             ecuacion, pares Clave Valor, donde
                                                             la Clave es la Variable y el valor
                                                             el Coeficiente de cada variable.
                                                             por ejemplo la lista de monomios
                                                             recibida es {"70h","90x","1/2p"}
                                                             se "Mappean" de la siguiente manera
                                                             {{70.0,"h"},{90.0, "x" },{0.5,"p"}}.

    private boolean conseguirSigno(String monomio) : Retorna True si el Signo de un monomio
                                                     es posivitivo y false en caso de que
                                                     sea negativo.

    private Double conseguirCociente(String monomio) : Retorna el Lado izquierdo del monomio
                                                       es decir el valor numerico que acompaña
                                                       a la variable.

    private String conseguirVariable(String monomio): Retorna el String referente a la Variable
                                                      del monomio en cuestion.


    private void extraerSigno(): extrae el signo de la ecuacion y convierte este en un valor
                                 del Enumerado Para Representar los Signos.

    private void extraerLadoDerecho(): Extrae el Valor numerico del Lado derecho de la ecuacion.

    public String toString(): Retorna un String que Representa a la ecuacion en Cuestion.

    public ArrayList<String> getVariables(): Retorna La Lista de variables Existentes en la
                                             Ecuacion.

    public Boolean buscarVariable(String variableABuscar): Retorna True si el valor pasado
                                                           como parametro es una variable
                                                           existente en las variables
                                                           de la ecuacion.


    public String getVFO(): Retorna el String referente a la Variable o nombre de la
                            funcion Objetivo Pj la Ecuacion es Max Y = 132x2 + 12x3
                            retornaria "Y".

    public Double buscarValor(String variable): Retorna el Coeficiente Existente
                                                de que acompaña a la variable pasada como
                                                parametro, es decir Retorna el valor pasando
                                                la clave que en este caso es la variable.


    public Signo getSigno(): Retorna un Enumerado con el Signo de la ecuacion en cuestion.

    public void estandarizar(int indiceRConRespectoPlanteamiento): Metodo que procede a
                                                                   estandarizar la ecuacion en
                                                                   custion y ademas guarda la
                                                                   ecuacion estandarizada en el
                                                                   campo de FormaEstandar.

    public String getEstandarString(): Retorna la Cadena de caracteres que representa la
                                       Ecuacion de la forma Estandar.

    public Ecuacion getEcuacionEstandar(): Retorna el Valor numerico del Lado derecho de la
                                           Ecuacion.

    private boolean esFormaEstandar(String str): Retorna True si el String pasado por parametro
                                                 Es una Ecuacion en Forma Estandar.

    public boolean tipoFuncionObjetivo(): Retorna true si es Maximizacion y False si es
                                          Minimizacion.

    private String retornarSignoString(): Retorna el String referente al Signo de la ecuacion.

    private String concatenacionMonomios(): Retorna la concatenacion en cadena de caracteres
                                            de todos los Pares Clave Valor de la ecuacion
                                            es decir Coeficiente-Variable.

    private int ocurrenciasRegex(String stringEvaluado, String regex): Retorna la cantidad de
                                                                       ocurrencias de una exp-
                                                                       resion regular en una
                                                                       cadena de caracteres.

  fecha: Febrero 2016
  @author Jesus Mota
 */
enum Signo { Igual, MayorIgual, MenorIgual, SinSigno}

public class Ecuacion
{
    public static final String LADO_DERECHO = "LD";
    final int INICIO_NUMEROS_ASCII = 48;
    final int FIN_NUMEROS_ASCII = 57;

    final String REGEX_CARACTERES_VALIDOS = "([a-z,0-9,+,<,>,=,\\-,.])+";
    final String REGEX_MONOMIOS =
            "([+,\\-]){1}([0-9]){0,}([.]){0,1}([0-9]){0,}([a-z]){1}([0-9]){0,}+";

    final String REGEX_LADO_DERECHO = "([<,>,=]){1,2}([0-9]){1,}([.]){0,1}([0-9]){0,}+";
    final String REGEX_FUNCION_OBJETIVO = "([a,x,m,i,n]){3}([a-z]){1}([=])+";
    final String REGEX_ESPACIOS = "\\s";
    final String REGEX_NUMEROS = "([0-9]){0,}([.]){0,1}([0-9]){1,}+";
    final String REGEX_SIGNOS = "([+,\\-,<,>,=]{1,2})+";
    final String REGEX_VARIABLES = "([a-z])([0-9]){0,}+";
    final String REGEX_FRACCIONES = "([0-9]){1,}([/]){1}([0-9]){1,}+";
    final String REGEX_SIN_NUMERADOR = "([/]){1}([0-9]){1,}+";
    final String REGEX_MAX_O_MIN = "([m,a,x,i,n]){3}+";


    Signo             signoEcuacion;
    Boolean           esFuncionObjetivo;
    ArrayList<Double> cocientes;
    ArrayList<String> variables;
    String            variableFobj;
    Double            ladoDerecho;
    String            ecuacion;
    Ecuacion          formaEstandar;

    public Ecuacion(String ecuacionEnTexto) throws EcuacionNoValida
    {
        cocientes = new ArrayList<Double>();
        variables = new ArrayList<String>();
        formaEstandar = null;
        ecuacionEnTexto = configurarTextoEcuacion(ecuacionEnTexto);

        ecuacionEnTexto = reemplazarFracciones(ecuacionEnTexto);

        validarTextoEcuacion(ecuacionEnTexto);

        convertirTextoEnEcuacion();
    }

    private String configurarTextoEcuacion(String ecuacionTexto)
    {
        String ecuacionConfigurada =
                ecuacionTexto.toLowerCase().replaceAll(REGEX_ESPACIOS,"");

        if(esFuncionObjetivo(ecuacionConfigurada))
        {
            if(!ecuacionConfigurada.replaceAll(REGEX_FUNCION_OBJETIVO,"").startsWith("-"))
                ecuacionConfigurada = "+"+ecuacionConfigurada;
        }
        else
        {
            if(!ecuacionConfigurada.startsWith("-"))
                ecuacionConfigurada = "+"+ecuacionConfigurada;
        }
        return ecuacionConfigurada;
    }

    private String reemplazarFracciones(String ec) throws EcuacionNoValida
    {

        String sinFracciones = ec;
        if(hayFracciones(ec))
        {
            ArrayList<String> fracciones;
            fracciones = extraerSegunRegex(ec,REGEX_FRACCIONES);
            ArrayList<Double> conComaFlotante = convertirEnNumerosconComa(fracciones);
            sinFracciones = sustituirFracciones(ec,conComaFlotante);
        }
        return sinFracciones;
    }

    private void validarTextoEcuacion(String ecuacionTexto) throws EcuacionNoValida
    {

        if(charsValidos(ecuacionTexto) && noExtraChars(ecuacionTexto)
                && soloUnSigno(ecuacionTexto) && sinEses(ecuacionTexto))
        {
            ecuacion = ecuacionTexto;
        }
        else
            throw new EcuacionNoValida("Posee Caracteres Invalidos!");
    }


    private void convertirTextoEnEcuacion() throws EcuacionNoValida
    {
        ArrayList<String> monomios;
        if(esFuncionObjetivo(ecuacion))
        {
            esFuncionObjetivo = true;
            monomios = extraerSegunRegex(ecuacion.replaceAll(REGEX_FUNCION_OBJETIVO,"")
                    ,REGEX_MONOMIOS);
            mapearMonomios(monomios);
            extraerSigno();

            if(signoEcuacion != Signo.Igual)
                throw new EcuacionNoValida("Signo no Permitido!");

            variableFobj = ecuacion.replaceAll(REGEX_MAX_O_MIN,"").
                    replaceAll(REGEX_SIGNOS,"").substring(0,1);

            if(variables.contains(variableFobj))
                throw new
                        EcuacionNoValida("Variable de la funcion al otro lado de la igualdad!");

            ladoDerecho = 0.0;
        }
        else
        {
            monomios = extraerSegunRegex(ecuacion,REGEX_MONOMIOS);

            mapearMonomios(monomios);
            extraerSigno();

            if(signoEcuacion == Signo.SinSigno)
                throw new EcuacionNoValida("Signo no Permitido!");

            extraerLadoDerecho();
        }
    }

    private boolean esFuncionObjetivo(String ec)
    {
        return ec.contains("max") || ec.contains("min");
    }

    public boolean esFuncionObjetivo()
    {
        return esFuncionObjetivo(ecuacion);
    }

    private boolean hayFracciones(String ec)
    {
        return ocurrenciasRegex(ec,REGEX_FRACCIONES) > 0;
    }

    private ArrayList<String> extraerSegunRegex(String ec,String regex)
    {
        ArrayList<String> ocurrencias = new ArrayList<String>();
        Matcher match = Pattern.compile(regex).matcher(ec);
        while (match.find())
        {
            ocurrencias.add(match.group());
        }
        return ocurrencias;
    }

    private ArrayList<Double> convertirEnNumerosconComa(ArrayList<String> fraccionesTexto)
            throws EcuacionNoValida
    {
        ArrayList<Double> conComa = new ArrayList<Double>();
        for(int i = 0; i<fraccionesTexto.size(); i++)
        {
            conComa.add(retornarDivision(fraccionesTexto.get(i)));
        }
        return conComa;
    }

    private Double retornarDivision(String fraccion)
            throws EcuacionNoValida
    {
        Double denominador = Double.
                parseDouble
                        (
                                fraccion.replaceFirst(REGEX_NUMEROS,"").
                                        replaceFirst("/","")
                        );

        Double numerador = Double.
                parseDouble
                        (
                                fraccion.replaceAll(REGEX_SIN_NUMERADOR,"")
                        );

        if(denominador == 0)
            throw new EcuacionNoValida("NO ES POSIBLE PROCESAR DIVISIONES ENTRE CERO");
        Double retorno = numerador/denominador;
        return (double)Math.round(retorno * 100) / 100;
    }

    private String sustituirFracciones(String ec, ArrayList<Double> sustitutos)
    {
        ec = ec.replaceAll(REGEX_FRACCIONES,"?");
        for (Double sustituto : sustitutos) {
            ec = ec.replaceFirst("([?])+", sustituto.toString());
        }
        return ec;
    }


    private boolean sinEses(String str)
    {
        if(!esFormaEstandar(str) || esFuncionObjetivo(str))
            return !(str.contains("s") || str.contains("S"));
        else
            return true;
    }

    private Boolean charsValidos(String stringEvaluado)
    {
        if(esFuncionObjetivo(stringEvaluado))
            return stringEvaluado.matches(REGEX_CARACTERES_VALIDOS)
                    && ocurrenciasRegex(stringEvaluado,REGEX_FUNCION_OBJETIVO) == 1;
        else
            return stringEvaluado.matches(REGEX_CARACTERES_VALIDOS);
    }

    private Boolean noExtraChars(String ecuacionTexto)
    {
        if(esFuncionObjetivo(ecuacionTexto))
            ecuacionTexto = ecuacionTexto.replaceAll(REGEX_FUNCION_OBJETIVO,"");
        else
            ecuacionTexto = ecuacionTexto.replaceAll(REGEX_LADO_DERECHO,"");

        ecuacionTexto = ecuacionTexto.replaceAll(REGEX_MONOMIOS,"");

        return ecuacionTexto.length() == 0;
    }

    private Boolean soloUnSigno(String ecuacionTexto)
    {
        if(esFuncionObjetivo(ecuacionTexto))
            return ocurrenciasRegex(ecuacionTexto,REGEX_FUNCION_OBJETIVO) == 1;
        else
            return ocurrenciasRegex(ecuacionTexto,REGEX_LADO_DERECHO) == 1;
    }

    private void mapearMonomios(ArrayList<String> monomios)
            throws EcuacionNoValida
    {
        double cociente = 0;
        String variable;
        Boolean signo; // True Positivo, False Negativo
        String monomio;
        for (String monomio1 : monomios)
        {

            monomio = monomio1;
            cociente = conseguirCociente(monomio);
            variable = conseguirVariable(monomio);
            signo = conseguirSigno(monomio);
            if(signo)
                cocientes.add(cociente);
            else
                cocientes.add(-cociente);
            if(!buscarVariable(variable))
                variables.add(variable);
            else
                throw new EcuacionNoValida("Tiene Variables Repetidas");
        }
    }

    private boolean conseguirSigno(String monomio)
    {
        monomio = monomio.replaceAll(REGEX_NUMEROS,"").replaceAll(REGEX_VARIABLES,"");
        return  monomio.compareTo("+") == 0;
    }

    private Double conseguirCociente(String monomio)
    {
        monomio = monomio.replaceFirst(REGEX_VARIABLES,"");
        monomio = monomio.replaceAll(REGEX_SIGNOS,"");
        if(monomio.isEmpty())
            return 1.0;
        else
            return Double.parseDouble(monomio);
    }

    private String conseguirVariable(String monomio)
    {
        monomio = monomio.replaceAll(REGEX_SIGNOS,"");
        if(monomio.replaceAll(REGEX_VARIABLES,"").isEmpty())
        {
            return monomio;
        }
        else
        {
            return monomio.replaceFirst(REGEX_NUMEROS,"");
        }
    }

    private void extraerSigno()
    {
        if(ecuacion.contains("=<"))
            signoEcuacion = Signo.SinSigno;
        else if(ecuacion.contains("=>"))
            signoEcuacion = Signo.SinSigno;
        else if(ecuacion.contains("<="))
            signoEcuacion = Signo.MenorIgual;
        else if(ecuacion.contains(">="))
            signoEcuacion = Signo.MayorIgual;
        else if(ecuacion.contains("<"))
            signoEcuacion = Signo.SinSigno;
        else if(ecuacion.contains(">"))
            signoEcuacion = Signo.SinSigno;
        else if(ecuacion.contains("="))
            signoEcuacion = Signo.Igual;
        else
            signoEcuacion = Signo.SinSigno;
    }

    private void extraerLadoDerecho()
    {
        ladoDerecho = Double.parseDouble(ecuacion.replaceAll(REGEX_MONOMIOS,"")
                .replaceAll(REGEX_SIGNOS,""));
    }

    public String toString()
    {
        String toStringReturn = "";
        if(esFuncionObjetivo(ecuacion))
        {
            if(tipoFuncionObjetivo())
                toStringReturn += "Max ";
            else
                toStringReturn += "Min ";

            toStringReturn += variableFobj;

            toStringReturn+=retornarSignoString();
            toStringReturn+=concatenacionMonomios();
        }
        else
        {
            toStringReturn+=concatenacionMonomios();
            toStringReturn+=retornarSignoString();
            toStringReturn+=ladoDerecho;
        }
        return toStringReturn;

    }

    public ArrayList<String> getVariables()
    {
        return variables;
    }

    public Boolean buscarVariable(String variableABuscar)
    {
        if(esFuncionObjetivo(ecuacion) && variableABuscar.equals(variableFobj)
                || variableABuscar.equals(LADO_DERECHO))
        {
            return true;
        }

        for (String variable : variables)
        {
            if (variableABuscar.equals(variable))
            {
                return true;
            }
        }
        return false;
    }

    public String getVFO()
    {
        return variableFobj;
    }

    public Double buscarValor(String variable)
    {
        if(esFuncionObjetivo(ecuacion) && variable.equals(variableFobj))
        {
            return 1.0;
        }

        if(variable.equals(LADO_DERECHO))
            return ladoDerecho;

        for (int i=0;i<variables.size();i++)
        {
            if(variable.equals(variables.get(i)))
                return cocientes.get(i);
        }
        return null;
    }

    public Signo getSigno()
    {
        return signoEcuacion;
    }

    public void estandarizar(int indiceRConRespectoPlanteamiento)
            throws EcuacionNoValida
    {
        String ecuacionEstandarizada = ecuacion.
                replaceAll(REGEX_LADO_DERECHO,"");

        if(ecuacionEstandarizada.startsWith("+"))
            ecuacionEstandarizada = ecuacionEstandarizada.
                    replaceFirst(REGEX_SIGNOS,"");

        if(esFuncionObjetivo(ecuacion))
        {
            formaEstandar = this;
        }
        else
        {
            if(signoEcuacion == Signo.Igual)
                formaEstandar = this;

            else if(signoEcuacion == Signo.MayorIgual)
            {

                ecuacionEstandarizada += "-"+"s"+indiceRConRespectoPlanteamiento;

                ecuacionEstandarizada += "="+ladoDerecho;

                formaEstandar = new Ecuacion(ecuacionEstandarizada);
            }
            else
            {

                ecuacionEstandarizada += "+"+"s"+indiceRConRespectoPlanteamiento;

                ecuacionEstandarizada += "="+ladoDerecho;

                formaEstandar = new Ecuacion(ecuacionEstandarizada);
            }
        }
    }

    public String getEstandarString()
    {
        if(formaEstandar != null)
            return formaEstandar.toString();
        else
            return "";
    }

    public Ecuacion getEcuacionEstandar()
    {
        return formaEstandar;
    }

    public Double retornarLadoDerecho()
    {
        return ladoDerecho;
    }

    private boolean esFormaEstandar(String str)
    {
        return !esFuncionObjetivo(str) && str.contains("=");
    }

    public boolean tipoFuncionObjetivo()
    {
        return ecuacion.contains("max");
    }

    private String retornarSignoString()
    {
        switch(signoEcuacion)
        {
            case MayorIgual:
                return " >= ";
            case Igual:
                return " = ";

            case MenorIgual:
                return " <= ";

            default:
                return "";

        }

    }

    private String concatenacionMonomios()
    {
        String retorno = "";
        Double valorAbsoluto;
        for (int i = 0;i<variables.size(); i++)
        {
            if(cocientes.get(i)< 0)
                valorAbsoluto = cocientes.get(i) * -1.0;
            else
                valorAbsoluto = cocientes.get(i);

            if(cocientes.get(i)>0 && cocientes.get(i) == 1.0)
                retorno+=" + "+variables.get(i);
            if(cocientes.get(i)<0 && cocientes.get(i) == -1.0)
                retorno+=" - "+variables.get(i);
            if(cocientes.get(i)>0 && cocientes.get(i) != 1.0)
                retorno+=" + "+cocientes.get(i)+variables.get(i);
            if(cocientes.get(i)<0 && cocientes.get(i) != -1.0)
                retorno+=" - "+valorAbsoluto+variables.get(i);
        }
        return retorno;
    }

    private int ocurrenciasRegex(String stringEvaluado, String regex)
    {
        int c = 0;
        Matcher  matcher = Pattern.compile(regex).matcher(stringEvaluado);
        while (matcher.find())
            c++;
        return c;
    }
}


//Excepcion para cuando la ecuacion no es Valida!
class EcuacionNoValida extends Exception
{
    String errorMessage;

    public EcuacionNoValida(String message)
    {
        super(message);
        errorMessage = message;
    }

    public String toString()
    {
        return errorMessage;
    }
}