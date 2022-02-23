package android.example.caltip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText txtAmount;
    private EditText txtPeople;
    private EditText txtTipOther;
    private RadioGroup rdoGroupTips;
    private Button btnReset;
    private Button btnCalculate;

    private TextView txtTipAmount;
    private TextView txtTotalToPay;
    private TextView txtTipPerPerson;

    private int radioCheckedId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtAmount = (EditText) findViewById(R.id.txtAmount);
        txtAmount.requestFocus();

        txtPeople = (EditText) findViewById(R.id.txtPeople);
        txtTipOther = (EditText) findViewById(R.id.txtTipOther);

        rdoGroupTips = (RadioGroup) findViewById(R.id.rdoGroupTips);

        btnReset = (Button) findViewById(R.id.btnReset);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);

        txtTipAmount = (TextView) findViewById(R.id.txtTipAmount);
        txtTotalToPay = (TextView) findViewById(R.id.txtTotalToPay);
        txtTipPerPerson = findViewById(R.id.txtTipPerPerson);

        txtTipOther.setEnabled(false);
    }
}