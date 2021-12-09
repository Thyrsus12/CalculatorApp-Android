/**
 * @author Tirso García
 */

package com.example.calculadora_pmdm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public TextView txtResultado;
    String operandoUno = "";
    String operandoDos = "";
    String operador = "";
    boolean igualusado = false;

    public Switch switchTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResultado = findViewById(R.id.textView);
        switchTheme = findViewById(R.id.switchTheme);
    }

    public void btnC(View view) {
        operandoUno = "";
        operandoDos = "";
        operador = "";
        txtResultado.setText("");
    }

    public void btnDel(View view) {
        //solo hay primer operando
        if (operandoUno != "" && operador.equals("") && operandoDos.equals("")) {
            //primer operando con un solo numero
            if (operandoUno.length() == 1) {
                operandoUno = "";
                txtResultado.setText(operandoUno);
                //primer operando con mas de un numero
            } else {
                operandoUno = operandoUno.substring(0, operandoUno.length() - 1);
                txtResultado.setText(operandoUno);
            }
            //hay primer operando y operador
        } else if (operandoUno != "" && operador != "" && operandoDos.equals("")) {
            operador = "";
            txtResultado.setText(operandoUno);
            //hay primer operando, operador y segundo operando
        } else if (operandoUno != "" && operador != "" && operandoDos != "") {
            if (operandoDos.length() == 1) {
                operandoDos = "";
                txtResultado.setText(operandoUno + " " + operador);
            } else {
                operandoDos = operandoDos.substring(0, operandoDos.length() - 1);
                txtResultado.setText(operandoUno + " " + operador + " " + operandoDos);
            }
        }
    }

    public void btnSiete(View view) {
        crearOperando("7");
    }

    public void btnOcho(View view) {
        crearOperando("8");
    }

    public void btnNueve(View view) {
        crearOperando("9");
    }

    public void btnCuatro(View view) {
        crearOperando("4");
    }

    public void btnCinco(View view) {
        crearOperando("5");
    }

    public void btnSeis(View view) {
        crearOperando("6");
    }

    public void btnUno(View view) {
        crearOperando("1");
    }

    public void btnDos(View view) {
        crearOperando("2");
    }

    public void btnTres(View view) {
        crearOperando("3");
    }

    public void btnCero(View view) {
        crearOperando("0");
    }

    public void btnPunto(View view) {
        crearOperando(".");
    }

    //añade al operando en el que nos encontremos el numero que pulsemos
    public void crearOperando(String numero) {
        /*En caso de que el primer operando esté vacio o
            sea el resultado de darle al igual (no se debe poder añadir numeros a un resultado)
            le asigna el numero*/
        if (operandoUno.equals("") || igualusado == true) {
            //siempre que el numero sea distinto de 0 (evitar 0s a la izquierda)
            if (numero != "0") {
                operandoUno = numero;
                txtResultado.setText(operandoUno);
            }
            igualusado = false;
            //en caso de que no esté vacío, concatena con lo que hay
        } else if (operador.equals("") && igualusado == false) {
            operandoUno += numero;
            txtResultado.setText(operandoUno);
        }

        //en caso de que el segundo operando esté vacio y el operador esté introducido, le asigna el numero
        if (operandoDos.equals("") && operador != "") {
            if (numero != "0") {
                operandoDos = numero;
                txtResultado.setText(operandoUno + " " + operador + " " + operandoDos);
            }
            //en caso de que no este vacio, concatena con lo que ya hay
        } else if (operador != "") {
            operandoDos += numero;
            txtResultado.setText(operandoUno + " " + operador + " " + operandoDos);
        }
    }

    public void btnDividir(View view) {
        crearOperador("/");
    }

    public void btnMultiplicar(View view) {
        crearOperador("*");
    }

    public void btnSumar(View view) {
        crearOperador("+");
    }

    public void btnRestar(View view) {
        crearOperador("-");
    }

    //asignacion de operadores
    public void crearOperador(String oper) {
        //en caso de que exista el primer operando (si no existe, introducir un operador no hara nada)
        if (operandoUno != "") {
            //si el operador esta vacio, define operador y muestra opernado uno y operador
            if (operador.equals("")) {
                operador = oper;
                txtResultado.setText(operandoUno + " " + oper);
            /*en caso de que ya este definido un operador y haya segundo operando
                (si hay operador pero no segundo operando, no hace nada)*/
            } else if (operador != "" && operandoDos != "") {
                operar("concatenadas", oper);
            }
        }
    }

    public void btnIgual(View view) {
        if (operandoUno != "" && operador != "" && operandoDos != "") {
            String oper = operador;
            operar("bigual", oper);
        }
    }

    public void operar(String procedencia, String operprov) {
        float result = 0.0F;
        //conversion de las string a float para poder operar
        float num1 = Float.parseFloat(operandoUno);
        float num2 = Float.parseFloat(operandoDos);

        //division
        boolean divisioncero = false;
        if (operador.equals("/")) {
            //opera si no es entre 0
            if (num2 != 0.0) {
                result = num1 / num2;
                //define error si se sintenta divivir entre 0
            } else {
                txtResultado.setText("Math Error");
                divisioncero = true;
            }

            //resto de operaciones
        } else if (operador.equals("*"))
            result = num1 * num2;
        else if (operador.equals("+"))
            result = num1 + num2;
        else if (operador.equals("-"))
            result = num1 - num2;

        //evita que se machaque el error de dividir por 0
        if (divisioncero == false) {
            //si el metodo ha sido llamado por el boton igual
            if (procedencia.equals("bigual")) {
                txtResultado.setText(result + "");
                operandoUno = result + "";
                operador = "";
                operandoDos = "";
                igualusado = true; /*si despues de darle a igual pulsamos un numero,
                                    esto permitira que este numero pulsado machaque el resultado
                                    en vez de concatenerse a el*/

                //si el metodo ha sido llamado por concatenar operaciones
            } else if (procedencia.equals("concatenadas")) {
                operador = operprov;
                txtResultado.setText(result + " " + operador);
                operandoUno = result + "";
                operandoDos = "";
            }
        }
    }

    public void changeTheme(View view) {
        if (switchTheme.isChecked()==true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            recreate();
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            recreate();
        }
    }
}