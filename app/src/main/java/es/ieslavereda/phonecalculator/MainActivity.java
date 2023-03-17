package es.ieslavereda.phonecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private Float operando1;
    private Float operando2;
    private static String displayText;
    private Button currentOperationButton;
    private Float result;

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

        // Oculta la barra de navegación y la barra de estado
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);

        // Detecta la interacción del usuario y vuelve a mostrar la barra de navegación y la barra de estado
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void inicializar(){

        buttonAC.setOnClickListener(view -> {
            display.setText(null);
        });

        buttonDelete.setOnClickListener(view -> {
            CharSequence text = display.getText();
            if (text.length() > 1) {
                display.setText(text.subSequence(0, text.length()-1));
            }else
                display.setText(null);

        });

        buttonSum.setOnClickListener(view -> {
            currentOperationButton = findViewById(view.getId());

            //Check if it is necessary to add the operation button or if it has already been added.
            checkOperationButton(buttonSum);
            operando1 = Float.parseFloat(display.getText().subSequence(0, display.getText().length()-1).toString());
            operation = Operation.SUM;
        });

        buttonEqual.setOnClickListener(view -> {
            calculate(true);
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

        calculate(false);
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
        if(view.getId()!=(R.id.buttonDot))
            display.setText(String.valueOf(display.getText()) + ((Button) view).getText());
    }

    @SuppressLint("SetTextI18n")
    public void calculate(boolean equalPressed){

        if(operation!= null) {
            switch (operation) {
                case SUM:
                    setOperando2(currentOperationButton);
                    if(operando2!=null){
                        result = operando1 + operando2;
                        secondaryDisplay.setText(String.valueOf(result));
                        if(equalPressed){
                            display.setText(String.valueOf(result));
                            secondaryDisplay.setText("");
                        }
                    }
                    break;

            }
            operando2 = null;
        }

    }

    public void setOperando2(Button operationButton){
        String displayText = display.getText().toString();
        int position;
        try{
            position = displayText.indexOf(operationButton.getText().toString());
            operando2 = Float.parseFloat(String.valueOf(display.getText().subSequence(position, displayText.length())));
        }catch (Exception e){
            operando2 = null;
        }
    }

    @SuppressLint("SetTextI18n")
    public void checkOperationButton(Button operationButton){
        boolean containsOperationButton = display.getText().toString().contains(operationButton.getText());
//        int lastCharPosition = display.getText().length()-1;

        if (containsOperationButton){
//            try {
//                lastCharPosition = display.getText().subSequence(lastCharPosition-1, lastCharPosition)
//            }catch ()
        }else{
            display.setText(String.valueOf(display.getText()) + operationButton.getText());
        }


    }

//    public boolean containsOperation(View view){
//        Button operationButton = findViewById(view.getId());
//
//        if(operationButton.isActivated())
//            return true;
//
//        return false;
//    }

}