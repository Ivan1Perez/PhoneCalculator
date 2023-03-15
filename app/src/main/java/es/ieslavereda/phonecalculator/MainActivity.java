package es.ieslavereda.phonecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static TextView display;
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
    private static String displayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
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

    private void inicializar(){
        int buttonID;

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

//            buttonSum.setOnClickListener(view -> {
//                CharSequence text = display.getText();
//                if(text==null || text)
//            });


//        buttonEqual.setOnClickListener(view -> {
//            switch (view.getId()){
//                case buttonAC.getId():
//
//            }
//        });

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

    }

    @SuppressLint("SetTextI18n")
    public void startsWithZero(View view){
        if (!(view.getId()==(R.id.buttonDot)))
            display.setText(((Button) view).getText());
        else
            display.setText(String.valueOf(display.getText()) + ((Button) view).getText());
    }

    @SuppressLint("SetTextI18n")
    public void containsDot(View view){
        if(view.getId()==(R.id.buttonDot))
            display.getText();
        else
            display.setText(String.valueOf(display.getText()) + ((Button) view).getText());
    }

    public static String getDisplayText() {
        return displayText = (String) display.getText();
    }

    public static void setDisplayText(String displayText) {
        display.setText(displayText);
    }
}