package es.ieslavereda.phonecalculator;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class ActionButtons implements View.OnClickListener{

    private Map<Integer, Runnable> actionButtons;
    private TextView display;
    private int viewId;
    String displayText;

    public ActionButtons(TextView display){
        actionButtons = new HashMap<>();
        this.display = display;

        actionButtons.put(R.id.buttonAC, this::clearDisplay);
        actionButtons.put(R.id.buttonDelete, this::deleteChar);
        actionButtons.put(R.id.buttonPercentage, this::calculatePercentage);
        actionButtons.put(R.id.buttonDivision, this::performDivision);
        actionButtons.put(R.id.buttonMultiply, this::performMultiplication);
        actionButtons.put(R.id.buttonSubtract, this::performSubtraction);
        actionButtons.put(R.id.buttonPlus, this::performAddition);
        actionButtons.put(R.id.buttonEqual, this::performCalculation);
    }

    public String getDisplayText() {
        return displayText;
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public Map<Integer, Runnable> getActionButtons() {
        return actionButtons;
    }

    public void clearDisplay() {
        displayText = "";
        display.setText(displayText);
    }

    public void deleteChar() {
        // Borra el último carácter
    }

    public void calculatePercentage() {
        // Calcula el porcentaje
    }

    public void performDivision() {
        // Realiza la división
    }

    public void performMultiplication() {
        // Realiza la multiplicación
    }

    public void performSubtraction() {
        // Realiza la resta
    }

    public void performAddition() {
        // Realiza la suma
    }

    public void performCalculation() {
        // Realiza el cálculo y muestra el resultado
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {

    }

    @Override
    public int hashCode() {
        return viewId;
    }
}
