package es.ieslavereda.phonecalculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView display;
    private ActionButtons actionButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        actionButtons = new ActionButtons(display);

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
    @Override
    public void onClick(View view) {

        if(!isActionButton(view)){
            if (display.getText().equals("0") || display.getText().equals("."))
                display.setText(((Button) view).getText());
            else{
                if(!dotClicked(view))
                    display.setText(String.valueOf(display.getText()) + ((Button) view).getText());
            }
        }else
            checkActionButton(view);
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

    public boolean isActionButton(View view){
        actionButtons.setViewId(view.getId());

        if(actionButtons.getActionButtons().containsKey(view.getId()))
            return true;

        return false;
    }

    public void checkActionButton(View view){
        Runnable action = actionButtons.getActionButtons().get(view.getId());

        if (action != null) {
            action.run();
            display.setText(actionButtons.getDisplayText());
        }

    }
}