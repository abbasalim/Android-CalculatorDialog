package ir.esfandune.calculatordialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.text.NumberFormat;
import java.text.ParseException;

import ir.esfandune.calculatorlibe.CalculatorDialog;

public class MainActivity extends AppCompatActivity {
    EditText et_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_price=findViewById(R.id.et_price);
    }

    public void showCalculator(View view) {
        int value = 0;
        try {
            value = Integer.parseInt(et_price.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        new CalculatorDialog(this) {
            @Override
            public void onResult(String result) {

                NumberFormat nf = NumberFormat.getInstance();
                double number = 0;
                try {
                    number = Math.abs(nf.parse(result).doubleValue());//abs : manfi ro hazf mokone
                    et_price.setText(Math.round(number) + "");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }.setValue(value).showDIalog();
    }

}
