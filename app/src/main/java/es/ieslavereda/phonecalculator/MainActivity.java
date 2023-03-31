package es.ieslavereda.phonecalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private double result;
    private boolean equalButtonPressed;
    private RadioGroup radioGroup;
    private CheckBox checkBox;
    private RadioButton radioButtonSuma;
    private RadioButton radioButtonResta;
    private RadioButton radioButtonMult;
    private RadioButton radioButtonDividir;
    private String displayText;
    private String secondaryDisplayText;


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if(!display.getText().equals("")){
            displayText = display.getText().toString();
            secondaryDisplayText = secondaryDisplay.getText().toString();

        }

        outState.putSerializable("result", result);
        outState.putSerializable("displayText", displayText);
        outState.putSerializable("secondaryDisplayText", secondaryDisplayText);



//        outState.putSerializable("hacerOperacion",hacerOperacion);
//        outState.putSerializable("a",a);
//        outState.putSerializable("b",b);
//        outState.putSerializable("borrar",borrar);
//        outState.putSerializable("hayComa", hayComa);
//        outState.putSerializable("operacion",operacion);
//        outState.putSerializable("texto",  text.getText().toString());

    }

    @SuppressLint("MissingInflatedId")
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
        buttonMultiply = findViewById(R.id.buttonMultiply);
        buttonEqual = findViewById(R.id.buttonEqual);
        equalButtonPressed = false;
        radioGroup = findViewById(R.id.radioGroup);
        checkBox = findViewById(R.id.checkboxOpciones);
        radioButtonSuma = findViewById(R.id.radioButtonSuma);
        radioButtonResta = findViewById(R.id.radioButtonResta);
        radioButtonDividir = findViewById(R.id.radioButtonDividir);
        radioButtonMult = findViewById(R.id.radioButtonMultiplicar);

        if (savedInstanceState==null) {
            result = 0;
            displayText = "";
            secondaryDisplayText = "";
        }else {
            result = (double) savedInstanceState.get("result");
            displayText = (String) savedInstanceState.get("displayText");
            display.setText(displayText);
            secondaryDisplayText = (String) savedInstanceState.get("secondaryDisplayText");
            secondaryDisplay.setText(secondaryDisplayText);
        }

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

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                radioGroup.clearCheck();

                if (b) {
                    radioGroup.setVisibility(View.VISIBLE);
                    checkBox.setText("Ocultar opciones");
                }
                else {
                    radioGroup.setVisibility(View.GONE);
                    checkBox.setText("Mostrar opciones");
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void inicializar() {

        buttonAC.setOnClickListener(view -> {
            display.setText("");
            secondaryDisplay.setText("");
            displayText = "";
            secondaryDisplayText = "";
            result = 0;
            operation = null;
            radioGroup.clearCheck();
        });

        buttonDelete.setOnClickListener(view -> {
            CharSequence text = display.getText();

            if (text.length() > 1) {
                display.setText(text.subSequence(0, text.length()-1));
                secondaryDisplay.setText(text.subSequence(0, text.length()-1));
            }else{
                display.setText(null);
                secondaryDisplay.setText(null);
                displayText = "";
                secondaryDisplayText = "";
                operation = null;
                radioGroup.clearCheck();
            }


        });

        buttonSum.setOnClickListener(view -> {
            equalButtonPressed = false;
            radioButtonSuma.setChecked(true);

            arrangeText("+", Operation.SUM);
        });

        radioButtonSuma.setOnClickListener(view -> {
            equalButtonPressed = false;

            arrangeText("+", Operation.SUM);
        });

        buttonSubtract.setOnClickListener(view -> {
            equalButtonPressed = false;
            radioButtonResta.setChecked(true);

            arrangeText("-", Operation.SUBTRACT);
        });

        radioButtonResta.setOnClickListener(view -> {
            equalButtonPressed = false;

            arrangeText("-", Operation.SUBTRACT);
        });

        buttonDivision.setOnClickListener(view -> {
            equalButtonPressed = false;

            arrangeText("÷", Operation.DIVISION);
        });

        radioButtonDividir.setOnClickListener(view -> {
            equalButtonPressed = false;

            arrangeText("÷", Operation.DIVISION);
        });

        buttonPercentage.setOnClickListener(view -> {
            equalButtonPressed = false;

            arrangeText("%", Operation.PERCENTAGE);
        });

        buttonMultiply.setOnClickListener(view -> {
            equalButtonPressed = false;

            arrangeText("×", Operation.MULTIPLY);
        });

        radioButtonMult.setOnClickListener(view -> {
            equalButtonPressed = false;

            arrangeText("×", Operation.MULTIPLY);
        });

        buttonEqual.setOnClickListener(view -> {
            radioGroup.clearCheck();

            percentageToCalculate();

            if(!secondaryDisplay.getText().toString().equals("")){
                display.setText(secondaryDisplay.getText().toString());
                secondaryDisplay.setText("");
                operation = null;
                equalButtonPressed = true;
            }

        });


    }

    private void percentageToCalculate() {
        double operando;
        if(display.getText().toString().charAt(display.getText().length()-1)=='%'){
            if(onlyNumbers()){
                operando = Double.parseDouble(display.getText().toString().substring(0, display.getText().length()-1));
                display.setText(String.valueOf(operando/100.0));
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void arrangeText(String operador, Operation operationSelected) {

        if (display.getText().length() > 0){
            if(display.getText().toString().charAt(display.getText().length()-1)=='.'){
                display.setText(display.getText() + "0");
            }
            if (!(String.valueOf(display.getText().charAt(display.getText().length() - 1)).equals(operador)))
                display.setText(display.getText() + operador);

            operation = operationSelected;
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        displayText = display.getText().toString();
        String resultToShow = "";

        if(equalButtonPressed){
            display.setText("");
            equalButtonPressed = false;
        }

        if (displayText.equals("0")) {
            startsWithZero(view);
        } else if (displayText.contains(".")) {
            containsDot(view);
        }else
            display.setText(String.valueOf(display.getText()) + ((Button) view).getText());

        if (operation != null && display.getText().toString().charAt(display.getText().length()-1)!='.') {
            calculate();
            resultToShow = String.valueOf(result);
            if(resultToShow.endsWith(".0")){
                resultToShow = resultToShow.replace(".0", "");
            }
            secondaryDisplay.setText(resultToShow);
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
        String operando = "";

        if (view.getId() != R.id.buttonDot)
            display.setText(String.valueOf(display.getText()) + ((Button) view).getText());
        else{
            if(operation!=null){
                operando = display.getText().toString().substring(display.getText().toString().lastIndexOf(operation.getSymbol()));
                if(!operando.contains(".")){
                    display.setText(String.valueOf(display.getText()) + ((Button) view).getText());
                }
            }
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
            if((expresion.charAt(i)<'0' || expresion.charAt(i)>'9') && expresion.charAt(i)!='.'){
                listaOperandos.add(String.valueOf(expresion.charAt(i)));
                listaPosicionesOperandos.add(i);
                operandoCounter++;
            }
        }

        if(operandoCounter==1){
            operando = listaOperandos.get(0);
            a = Double.parseDouble(expresion.substring(0, expresion.indexOf(operando)));
            b = Double.parseDouble(expresion.substring(expresion.indexOf(operando)+1));
            performOperation(operando, a , b);
        }else{
            for(int i = 1, j = 0 ; i <= operandoCounter ; i++, j++){
                operando = listaOperandos.get(j);
                if(i==1){
                    a = Double.parseDouble(expresion.substring(0, expresion.indexOf(operando)));
                    b = Double.parseDouble(expresion.substring(listaPosicionesOperandos.get(0)+1, listaPosicionesOperandos.get(1)));
                }else{
                    a = result;

                    try{
                        b = Double.parseDouble(expresion.substring(listaPosicionesOperandos.get(j)+1, listaPosicionesOperandos.get(j+1)));
                    }catch (Exception e1){
                        b = Double.parseDouble(expresion.substring(listaPosicionesOperandos.get(listaPosicionesOperandos.size()-1)+1));
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
            case "÷":
                if(b !=0){
                    result = (a / b);
                }
                break;
            case "%":
                result = (a % b);
                break;
            case "×":
                result = (a * b);
                break;
        }
    }

    public boolean onlyNumbers(){
        displayText = display.getText().toString();

        for(int i = 0; i < displayText.length()-1; i++){
            if((displayText.charAt(i)<'0' || displayText.charAt(i)>'9') && displayText.charAt(i)!='.'){
                return false;
            }
        }

        return true;
    }

}