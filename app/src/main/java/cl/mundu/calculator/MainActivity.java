package cl.mundu.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_Calculator = "Calculator";

    private String buffer = "";
    private String ope = "";

    private Boolean opeActive = Boolean.FALSE;
    private Boolean resultActive = Boolean.FALSE;

    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.txtResult = findViewById(R.id.txtResult);
    }

    public void calcEvent(View view) {

        Log.i(TAG_Calculator, "calcEvent [START]");

        Button btn = (Button) view;
        Log.i(TAG_Calculator, "Click Button [" + btn.getText().toString() + "]");

        switch (btn.getId()) {
            case R.id.btnCambioSigno:

                if (this.txtResult.getText().toString().length() > 0 && this.txtResult.getText().toString().substring(0,1).equals("-")) {
                    this.txtResult.setText(txtResult.getText().toString().substring(1,txtResult.getText().toString().length()));
                } else {
                    this.txtResult.setText("-".concat(txtResult.getText().toString()));
                }

                break;
            case R.id.btnAc:
                cleanAttributes();
                break;
            case R.id.btnResult:

                if (this.txtResult.getText().toString().length() > 0 && this.buffer.length() > 0 && this.ope.length() > 0) {

                    Double result = Double.valueOf(0);

                    if (!resultActive) {
                        result = calc(convertNumber(this.buffer), convertNumber(this.txtResult.getText().toString()), this.ope);
                        this.buffer = this.txtResult.getText().toString();
                    } else {
                        result = calc(convertNumber(this.txtResult.getText().toString()), convertNumber(this.buffer), this.ope);
                    }

                    this.txtResult.setText(convertNumber(result));
                    this.resultActive = Boolean.TRUE;
                }

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

        Log.i(TAG_Calculator, "calcEvent [END]");
    }

    public void numberClickEvent(View view) {

        Log.i(TAG_Calculator, "numberClickEvent [START]");

        Button btn = (Button) view;

        Log.i(TAG_Calculator, "Click Button [" + btn.getText().toString() + "]");

        if (this.opeActive) {

            this.buffer = this.txtResult.getText().toString();
            this.txtResult.setText(btn.getText().toString());

        } else if (!btn.getText().toString().equals(",") || (btn.getText().toString().equals(",") && !this.txtResult.getText().toString().contains(","))) {
            this.txtResult.setText(this.txtResult.getText().toString().concat(btn.getText().toString()));
        }

        this.resultActive = Boolean.FALSE;
        this.opeActive = Boolean.FALSE;

        Log.i(TAG_Calculator, "numberClickEvent [END]");
    }

    public void opeClickEvent(View view) {
        Log.i(TAG_Calculator, "opeClickEvent [START]");

        Button btn = (Button) view;

        Log.i(TAG_Calculator, "Click Button [" + btn.getText().toString() + "]");

        this.ope = btn.getText().toString();
        this.opeActive = Boolean.TRUE;
        this.resultActive = Boolean.FALSE;

        Log.i(TAG_Calculator, "numberClickEvent [END]");
    }

    private Double calc(Double valueA, Double valueB, String ope) {

        Log.i(TAG_Calculator, "calc [START]");

        Log.d(TAG_Calculator, "valueA = [" + valueA + "], value B = [" + valueB + "], ope = [" + ope + "]");

        double result = Double.valueOf(0);
        switch (ope) {
            case "+":
                result = valueA + valueB;
                break;
            case "-":
                result = valueA - valueB;
                break;
            case "X":
                result = valueA * valueB;
                break;
            case "/":
                result = valueA / valueB;
                break;
            case "%":
                result = valueA % valueB;
                break;
            default:
                throw new IllegalStateException("Unexpected operation: " + ope);
        }

        Log.d(TAG_Calculator, "result = [" + result + "]");

        Log.i(TAG_Calculator, "calc [END]");

        return result;
    }

    private void cleanAttributes() {

        Log.i(TAG_Calculator, "cleanAttributes [START]");

        this.buffer = "";
        this.txtResult.setText("");
        this.ope = "";
        this.opeActive = Boolean.FALSE;

        Log.i(TAG_Calculator, "cleanAttributes [END]");
    }

    private Double convertNumber(String value) {
        return Double.parseDouble(value.replace(",","."));
    }

    private String convertNumber(Double value) {
        return value.toString().replace(".", ",");
    }
}
