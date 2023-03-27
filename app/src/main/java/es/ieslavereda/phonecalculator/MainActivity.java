package es.ieslavereda.phonecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
    private double operando1;
    private double operando2;
    private double result;
    private boolean equalPressed;
    private boolean operationDone;
    private double previousResult;
    private boolean firstCalculation;
    private List<Float> results;
    private boolean operationButtonPressed;
    private int totalOperadores;

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
        equalPressed = false;
        results = new ArrayList<>();
        operationButtonPressed = false;
        totalOperadores = 0;

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

//        buttonAC.setOnClickListener(view -> {
//            display.setText(null);
//            secondaryDisplay.setText("");
//            result = 0;
//            previousResult = 0;
//        });
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
            operationButtonPressed = true;
        });

        buttonEqual.setOnClickListener(view -> {
            display.setText("");
            equalPressed = true;
            operation = null;
        });


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {

        if (display.getText().equals("0")) {
            startsWithZero(view);
        } else if (display.getText().toString().contains(".")) {
            containsDot(view);
        } else
            display.setText(String.valueOf(display.getText()) + ((Button) view).getText());

        if (operation != null) {
            calculate();
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
    public void getOperando1() {
        String displayText = display.getText().toString();

        if (!displayText.equals("."))
            operando1 = Float.parseFloat(displayText.substring(0, displayText.indexOf(operation.getSymbol())));
    }

    @SuppressLint("SetTextI18n")
    public void getOperando2() {
        String displayText = display.getText().toString();

        try {
            operando2 = Float.parseFloat(displayText.substring(displayText.lastIndexOf(operation.getSymbol())));
        } catch (Exception e) {
            operando2 = 0;
        }
    }

    @SuppressLint("SetTextI18n")
    public void calculate() {
        String expresion = display.getText().toString();
        char character = ' ';
        String operando = "";
        String numStringA = "", numStringB = "";
        boolean operatorACompleted = false;
        double a = 0, b = 0;
        int operandoCounter = 0;
//
//        if(secondaryDisplay.getText()==null || secondaryDisplay.getText().equals("")){
//            expresion = display.getText().toString();
//        }else {
//            expresion = secondaryDisplay.getText().toString();
//        }

        for (int i = 0; i < expresion.length(); i++) {
            if (expresion.charAt(i) >= '0' && expresion.charAt(i) <= '9') {
                if(operando.equals("")){
                    numStringA += expresion.charAt(i);
                }else{
                    numStringB += expresion.charAt(i);
                }
            }else{
                operando = operation.getSymbol();
            }
        }

        if(!operando.equals("")){
            a = Double.parseDouble(numStringA);
            b = Double.parseDouble(numStringB);

            switch (operando) {
                case "+":
                    result = (a + b);
                    break;
                case "-":
                    result = (a - b);
                    break;
                case "/":
                    if(b!=0){
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
            secondaryDisplay.setText(String.valueOf(result));
        }

    }

}