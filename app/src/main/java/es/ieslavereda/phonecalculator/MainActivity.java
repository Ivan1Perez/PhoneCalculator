package es.ieslavereda.phonecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Pagina web útil para diseño → https://m3.material.io/
    private TextView display;
    private Button AC;
    private Button delete;
    private Button percentage;
    private Button division;
    private Button multiply;
    private Button subtract;
    private Button sum;
    private Button equal;
    private Operation operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        AC = findViewById(R.id.buttonAC);

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

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {

        if (display.getText().equals("0") || display.getText().equals("."))
            display.setText(((Button) view).getText());
        else{
            if(!dotClicked(view))
                display.setText(String.valueOf(display.getText()) + ((Button) view).getText());
        }
    }

    @SuppressLint("SetTextI18n")
    public boolean dotClicked(View view){

        if (view.getId() == R.id.buttonDot) {
            if (!display.getText().toString().contains("."))
                display.append(((Button) view).getText());
            return true;
        }

        return false;
    }

}