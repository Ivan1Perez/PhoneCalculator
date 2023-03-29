package es.ieslavereda.phonecalculator;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

 enum Operation {

     PERCENTAGE("%"),
     DIVISION("÷"),
     MULTIPLY("×"),
     SUBTRACT("-"),
     SUM("+");

     private String symbol;

     Operation(String symbol){
         this.symbol = symbol;
     }

     public String getSymbol() {
         return symbol;
     }
 }
