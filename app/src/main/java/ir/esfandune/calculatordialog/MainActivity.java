package ir.esfandune.calculatordialog;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;


import ir.esfandune.calculatorlibe.CalculatorDialog;


public class MainActivity extends AppCompatActivity {
    EditText et_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_price = findViewById(R.id.et_price);

    }

    public void showCalculator(View view) {
        double value = 0;
        try {
            value = Double.parseDouble(et_price.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        new CalculatorDialog(this) {
            @Override
            public void onResult(String result) {
                et_price.setText(result);
            }
        }.setValue(value).showDIalog();
    }

    public void easyCalc(View view) {
        CalculatorDialog.Companion.easyCalculate(this, et_price, ",", false, false);
//        or
//        easyCalculate(this,et_price);
    }
}
