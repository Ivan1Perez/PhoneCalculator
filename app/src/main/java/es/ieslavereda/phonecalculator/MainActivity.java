package es.ieslavereda.phonecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView display;
    private TextView secondaryDisplay;
    private Button buttonAC;
    private Button buttonDelete;
    private Button buttonPercentage;
    private Button buttonDivision;
    private Button buttonMultiply;
    private Button buttonSubtract;
    private Button buttonSum;
    private Button buttonEqual;
    private Operation operation;
    private double result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        secondaryDisplay = findViewById(R.id.secondaryDisplay);
        buttonAC = findViewById(R.id.buttonAC);
        buttonDelete = findViewById((R.id.buttonDelete));
        buttonPercentage = findViewById(R.id.buttonPercentage);
        buttonDivision = findViewById(R.id.buttonDivision);
        buttonSubtract = findViewById(R.id.buttonSubtract);
        buttonSum = findViewById(R.id.buttonPlus);
        buttonEqual = findViewById(R.id.buttonEqual);

        inicializar();

        // Oculta la barra de navegación
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        // Detecta la interacción del usuario y vuelve a mostrar la barra de navegación
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void inicializar() {

        buttonAC.setOnClickListener(view -> {
            display.setText(null);
            secondaryDisplay.setText("");
            result = 0;
            operation = null;
        });
//
//        buttonDelete.setOnClickListener(view -> {
//            CharSequence text = display.getText();
//            result = 0;
//
//            if (text.length() > 1) {
//                display.setText(text.subSequence(0, text.length()-1));
//                secondaryDisplay.setText(text.subSequence(0, text.length()-1));
//            }else{
//                display.setText(null);
//                secondaryDisplay.setText(null);
//            }
//            calculate(false);
//
//
//        });

        buttonSum.setOnClickListener(view -> {

            if (display.getText().length() > 0)
                if (!(String.valueOf(display.getText().charAt(display.getText().length() - 1)).equals(((Button) view).getText().toString())))
                    display.setText(display.getText() + ((Button) view).getText().toString());

            operation = Operation.SUM;
        });

        buttonEqual.setOnClickListener(view -> {
            display.setText(secondaryDisplay.getText().toString());
            secondaryDisplay.setText("");
            operation = null;
        });


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        String displayText = display.getText().toString();

        if (displayText.equals("0")) {
            startsWithZero(view);
        } else if (displayText.contains(".")) {
            containsDot(view);
        }else
            display.setText(String.valueOf(display.getText()) + ((Button) view).getText());

        if (operation != null) {
            calculate();
            secondaryDisplay.setText(String.valueOf(result));
        }

    }

    @SuppressLint("SetTextI18n")
    public void startsWithZero(View view) {
        if (view.getId() != (R.id.buttonDot))
            display.setText(((Button) view).getText());
        else
            display.setText(String.valueOf(display.getText()) + ((Button) view).getText());
    }

    @SuppressLint("SetTextI18n")
    public void containsDot(View view) {
        try {
            if (view.getId() != R.id.buttonDot)
                display.setText(String.valueOf(display.getText()) + ((Button) view).getText());
        } catch (Exception e) {
            System.out.println("Problemaaaaaaaa");
        }

    }

    @SuppressLint("SetTextI18n")
    public void calculate() {
        String expresion = display.getText().toString();
        String operando = "";
        double a = 0, b = 0;
        int operandoCounter = 0;
        List<String> listaOperandos = new ArrayList<>();
        List<Integer> listaPosicionesOperandos = new ArrayList<>();

        for(int i = 0 ; i < expresion.length() ; i++){
            if(expresion.charAt(i)<'0' || expresion.charAt(i)>'9'){
                listaOperandos.add(operation.getSymbol());
                listaPosicionesOperandos.add(i);
                operandoCounter++;
            }
        }

        if(operandoCounter==1){
            operando = listaOperandos.get(0);
            a = Double.parseDouble(expresion.substring(0, expresion.indexOf(operando)));
            b = Double.parseDouble(expresion.substring(expresion.indexOf(operando)));
            performOperation(operando, a , b);
        }else{
            for(int i = 1, j = 0 ; i <= operandoCounter ; i++, j++){
                operando = listaOperandos.get(j);
                if(i==1){
                    a = Double.parseDouble(expresion.substring(0, expresion.indexOf(operando)));
                    b = Double.parseDouble(expresion.substring(listaPosicionesOperandos.get(0), listaPosicionesOperandos.get(1)));
                }else{
                    a = result;

                    try{
                        b = Double.parseDouble(expresion.substring(listaPosicionesOperandos.get(j), listaPosicionesOperandos.get(j+1)));
                    }catch (Exception e1){
                        b = Double.parseDouble(expresion.substring(listaPosicionesOperandos.get(listaPosicionesOperandos.size()-1)));
                    }
                }
                performOperation(operando, a , b);
            }
        }

    }

    private void performOperation(String operando, double a, double b) {
        switch (operando) {
            case "+":
                result = (a + b);
                break;
            case "-":
                result = (a - b);
                break;
            case "/":
                if(b !=0){
                    result = (a / b);
                }
                break;
            case "%":
                result = (a % b);
                break;
            case "*":
                result = (a * b);
                break;
        }

    }

}