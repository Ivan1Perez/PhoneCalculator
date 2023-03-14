package es.ieslavereda.phonecalculator;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

 enum Operation {

     CLEAR (Operation::clearDisplay),
     DELETE (Operation::deleteChar),
     PERCENTAGE (Operation::calculatePercentage),
     DIVISION (Operation::performDivision),
     MULTIPLY (Operation::performMultiplication),
     SUBTRACT (Operation::performSubtraction),
     SUM (Operation::performAddition),
     EQUAL (Operation::performCalculation);

     private Runnable runnable;

     Operation(Runnable runnable){
         this.runnable = runnable;
    }

     public static void clearDisplay() {

    }

     public static void deleteChar() {
         // Borra el último carácter
     }

     public static void calculatePercentage() {
         // Calcula el porcentaje
     }

     public static void performDivision() {
         // Realiza la división
     }

     public static void performMultiplication() {
         // Realiza la multiplicación
     }

     public static void performSubtraction() {
         // Realiza la resta
     }

     public static void performAddition() {
         // Realiza la suma
     }

     public static void performCalculation() {
         // Realiza el cálculo y muestra el resultado
     }


 }
