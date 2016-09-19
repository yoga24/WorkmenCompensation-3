package com.cyorg.uiic.workmencompensationrainbow3.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cyorg.uiic.workmencompensationrainbow3.R;
import com.cyorg.uiic.workmencompensationrainbow3.model.SingleEntryModel;
import com.cyorg.uiic.workmencompensationrainbow3.utils.CommonUtils;

public class SingleEntryActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout layout;

    private Toolbar toolBar;

    private Button addEntryButton;
    private Button resetButton;

    private EditText salary;
    private EditText annSalary;
    private EditText premium;
    private EditText sumInsured;
    private EditText noPersons;

    private double valSalary;
    private double valAnnSalary;
    private double valPremium;
    private double valSumInsured;
    private int valNoPersons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_entry);

        //Initialize
        layout = (LinearLayout) findViewById(R.id.se_layout);

        toolBar = (Toolbar) findViewById(R.id.se_toolbar);

        addEntryButton = (Button) findViewById(R.id.se_btn_add);
        resetButton = (Button) findViewById(R.id.se_btn_reset);

        salary = (EditText) findViewById(R.id.se_edit_salary);
        noPersons = (EditText) findViewById(R.id.se_edit_no_person);
        premium = (EditText) findViewById(R.id.se_edit_premium);
        annSalary = (EditText) findViewById(R.id.se_edit_ann_salary);
        sumInsured = (EditText) findViewById(R.id.se_edit_sum_insured);


        setSupportActionBar(toolBar);
        toolBar.setTitle("Workmen Compensation");

        //setOnClickListener
        addEntryButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);

        //Set Type Listener
        salary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //No Ops Needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //No Ops Needed
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateAnnualSalary();
            }
        });

        noPersons.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //No Ops Needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //No Ops Needed
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateSumInsured();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.se_btn_reset:
                CommonUtils.resetForm((ViewGroup) findViewById(R.id.se_vg));
                break;
            case R.id.se_btn_add:
                addEntry();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar_se, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(SingleEntryActivity.this, "Action Not Supported", Toast.LENGTH_SHORT).show();
    }

    private boolean validateFields() {
        return !(CommonUtils.emptyStringCheck(salary.getText()) || CommonUtils.emptyStringCheck(noPersons.getText()));
    }

    private void readValues() {
        valSalary = Double.parseDouble(salary.getText().toString());
        valAnnSalary = Double.parseDouble(annSalary.getText().toString());
        valPremium = Double.parseDouble(premium.getText().toString());
        valSumInsured = Double.parseDouble(sumInsured.getText().toString());
        valNoPersons = Integer.parseInt(noPersons.getText().toString());
    }

    private void addEntry() {
        if (validateFields()) {
            readValues();
            CommonUtils.addData(prepareEntryModel());
            Intent intent = new Intent(this, SummaryActivity.class);
            startActivity(intent);
            //Toast.makeText(this,"Entry Added",Toast.LENGTH_SHORT).show();
        } else {
            Snackbar snackbar = Snackbar.make(layout, "One or More fields are EMPTY", Snackbar.LENGTH_SHORT);
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }
    }

    private SingleEntryModel prepareEntryModel() {
        SingleEntryModel entry = new SingleEntryModel();

        entry.setSalary(valSalary);
        entry.setNoPersons(valNoPersons);
        entry.setAnnSalary(valAnnSalary);
        entry.setSumInsured(valSumInsured);
        entry.setPremium(valPremium);

        return entry;
    }

    public void calculateAnnualSalary() {
        if (!CommonUtils.emptyStringCheck(salary.getText())) {
            valSalary = Double.parseDouble(salary.getText().toString());
            annSalary.setText(String.valueOf(valSalary * 12));
            calculatePremium();
        }
    }

    public void calculateSumInsured() {
        if (!CommonUtils.emptyStringCheck(salary.getText()) && !CommonUtils.emptyStringCheck(noPersons.getText())) {
            valSalary = Double.parseDouble(salary.getText().toString());
            valNoPersons = Integer.parseInt(noPersons.getText().toString());
            sumInsured.setText(String.valueOf(valSalary * 12 * valNoPersons));
            calculatePremium();
        }
    }

    public void calculatePremium() {
        if (!CommonUtils.emptyStringCheck(salary.getText()) && !CommonUtils.emptyStringCheck(noPersons.getText())) {
            valSalary = Double.parseDouble(salary.getText().toString());
            valNoPersons = Integer.parseInt(noPersons.getText().toString());

            if (valSalary < 8000) {
                valPremium = (valSalary * valNoPersons * 12) * CommonUtils.rate / 1000;
            } else {
                valPremium = ((8000 * valNoPersons * 12) * CommonUtils.rate / 1000) + ((valSalary - 8000) * valNoPersons * 12 * 0.002);
            }

            premium.setText(String.valueOf(valPremium));
        }
    }


}
