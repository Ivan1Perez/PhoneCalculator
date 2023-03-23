package es.ieslavereda.phonecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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
    private float operando1;
    private float operando2;
    private float result;
    private boolean equalPressed;
    private boolean operationDone;
    private float previousResult;
    private boolean firstCalculation;
    private List<Float> results;
    private boolean operationButtonPressed;
    private int resultToShow;

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
        resultToShow = 1;

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
    private void inicializar(){

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

            if (!(String.valueOf(display.getText().charAt(display.getText().length()-1)).equals(((Button) view).getText().toString())))
                display.setText(display.getText() + ((Button) view).getText().toString());

            operation = Operation.SUM;
            operationButtonPressed = true;
        });

        buttonEqual.setOnClickListener(view -> {
            display.setText(String.valueOf(result));
            secondaryDisplay.setText("");
            operation = null;
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {

        if(display.getText().equals("0")) {
            startsWithZero(view);
        }
        else if (display.getText().toString().contains(".")){
            containsDot(view);
        }
        else
            display.setText(String.valueOf(display.getText()) + ((Button) view).getText());

        if(operation!=null){
            calculate();
        }

    }

    @SuppressLint("SetTextI18n")
    public void startsWithZero(View view){
        if (view.getId()!=(R.id.buttonDot))
            display.setText(((Button) view).getText());
        else
            display.setText(String.valueOf(display.getText()) + ((Button) view).getText());
    }

    @SuppressLint("SetTextI18n")
    public void containsDot(View view){
        try{
            if(view.getId()!=R.id.buttonDot)
                display.setText(String.valueOf(display.getText()) + ((Button) view).getText());
        }catch (Exception e){
            System.out.println("Problemaaaaaaaa");
        }

    }

    @SuppressLint("SetTextI18n")
    public void getOperando1(){
        String displayText = display.getText().toString();

        if(!displayText.equals("."))
            operando1 = Float.parseFloat(displayText.substring(0, displayText.indexOf(operation.getSymbol())));
    }

    @SuppressLint("SetTextI18n")
    public void getOperando2(){
        String displayText = display.getText().toString();

        try{
            operando2 = Float.parseFloat(displayText.substring(displayText.lastIndexOf(operation.getSymbol())));
        }catch (Exception e){
            operando2 = 0;
        }
    }

    @SuppressLint("SetTextI18n")
    public void calculate(){
        getOperando2();

        switch (operation){
            case SUM:
                if(results.size()==0){
                    result += operando2;
                    results.add(result);
                }else{
                    if(!operationButtonPressed){
                        result = results.get(results.size()-1) + operando2;
                        results.add(result);
                    }

                }

                break;
        }

        secondaryDisplay.setText(String.valueOf(results.get(resultToShow)));
        operationButtonPressed = false;

    }


}