package android.example.caltip;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

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
        btnCalculate.setEnabled(false);

        txtTipAmount = (TextView) findViewById(R.id.txtTipAmount);
        txtTotalToPay = (TextView) findViewById(R.id.txtTotalToPay);
        txtTipPerPerson = findViewById(R.id.txtTipPerPerson);

        txtTipOther.setEnabled(false);

        rdoGroupTips.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.radioFifteen || checkedId == R.id.radioTwenty) {
                    txtTipOther.setEnabled(false);
                    btnCalculate.setEnabled(txtAmount.getText().length() > 0 && txtPeople.getText().length() > 0);
                }
                if (checkedId == R.id.radioOther) {
                    txtTipOther.setEnabled(true);
                    txtTipOther.requestFocus();

                    btnCalculate.setEnabled(txtAmount.getText().length() > 0 && txtPeople.getText().length() > 0
                            && txtTipOther.getText().length() > 0);
                }
                radioCheckedId = checkedId;
            }
        });


        txtPeople.setOnKeyListener(mKeyListener);
        txtAmount.setOnKeyListener(mKeyListener);
        txtTipOther.setOnKeyListener(mKeyListener);

        btnCalculate.setOnClickListener(mClickListener);
        btnReset.setOnClickListener(mClickListener);

    }

        OnKeyListener mKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                switch (view.getId()) {
                    case R.id.txtAmount:
                    case R.id.txtPeople:
                        btnCalculate.setEnabled(txtAmount.getText().length() > 0 && txtPeople.getText().length() > 0);
                        break;
                    case R.id.txtTipOther:
                        btnCalculate.setEnabled(txtAmount.getText().length() > 0
                                            && txtPeople.getText().length() > 0
                                            &&  txtTipOther.getText().length() > 0);
                        break;
                }
                return false;
            }
        };

        OnClickListener mClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.btnCalculate){
                    calculate();
                }else{
                    reset();
                }
            }
        };



    private void reset(){
        txtAmount.setText("");
        txtPeople.setText("");
        txtTipOther.setText("");
        txtTipAmount.setText("");
        txtTotalToPay.setText("");
        txtTipPerPerson.setText("");
        rdoGroupTips.clearCheck();
        rdoGroupTips.check(R.id.radioFifteen);
        rdoGroupTips.requestFocus();
    }

    private void calculate() {
        Double billAmount = Double.parseDouble(txtAmount.getText().toString());
        Double totalPeople = Double.parseDouble(txtPeople.getText().toString());
        Double percentage = null;
        boolean isError = false;

        if(billAmount < 1.0){
            showErrorAlert("Enter a valid Total Amount.", txtAmount.getId());
            isError = true;
        }
        if(radioCheckedId == -1){
            radioCheckedId = rdoGroupTips.getCheckedRadioButtonId();
        }
        if(radioCheckedId == R.id.radioFifteen){
            percentage = 15.00;
        }
        else if(radioCheckedId == R.id.radioTwenty){
            percentage = 20.00;
        }
        else if(radioCheckedId == R.id.radioOther){
            percentage = Double.parseDouble(txtTipOther.getText().toString());
            if(percentage < 1.0){
                showErrorAlert("Enter a valid Tip Percentage.", txtTipOther.getId());
                isError = true;
            }
        }

        if(!isError){
            Double tipAmount = (billAmount * percentage)/100;
            Double totalToPay = billAmount + tipAmount;
            Double perPersonPays = totalToPay / totalPeople;

            txtTipAmount.setText(tipAmount.toString());
            txtTotalToPay.setText(totalToPay.toString());
            txtTipPerPerson.setText(perPersonPays.toString());
        }
    }

    private void showErrorAlert(String errorMessage, final int fieldId){
        new AlertDialog.Builder(this).setTitle("Error").setMessage(errorMessage).setNeutralButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                findViewById(fieldId).requestFocus();
            }
        }).show();
    }
}