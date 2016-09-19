package com.cyorg.uiic.workmencompensationrainbow3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cyorg.uiic.workmencompensationrainbow3.R;
import com.cyorg.uiic.workmencompensationrainbow3.utils.CommonUtils;
import com.cyorg.uiic.workmencompensationrainbow3.utils.WcConstants;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Toolbar toolBar;
    private EditText insurer;
    private EditText rate;
    private EditText discount;
    private Button proceed;
    private TextInputLayout insurerLayout;
    private TextInputLayout rateLayout;
    private TextInputLayout discLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAppDirectory();

        //Assign elements
        toolBar = (Toolbar) findViewById(R.id.main_toolbar);
        insurer = (EditText) findViewById(R.id.main_edit_insurer);
        rate = (EditText) findViewById(R.id.main_edit_rate);
        discount = (EditText) findViewById(R.id.main_edit_discount);
        insurerLayout = (TextInputLayout) findViewById(R.id.main_edit_insurerlayout);
        rateLayout = (TextInputLayout) findViewById(R.id.main_edit_ratelayout);
        discLayout = (TextInputLayout) findViewById(R.id.main_edit_disclayout);
        proceed = (Button) findViewById(R.id.btn_proceed);

        setSupportActionBar(toolBar);
        toolBar.setTitle("Workmen Compensation");

        proceed.setOnClickListener(this);

        if (WcConstants.isRedefineValues()) {
            insurer.setText(CommonUtils.insurerName);
            rate.setText(CommonUtils.rate);
            discount.setText(CommonUtils.discount);
            WcConstants.setRedefineValues(false);
        }   else    {
            CommonUtils.setFileNameAndPath();
        }

    }

    private void createAppDirectory() {
        File directory = new File(WcConstants.getFileDirectory());
        if (!directory.exists()) {
            Log.i(TAG, "Directory NOT EXISTS ** Creation Status :: " + directory.mkdirs());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolBar.inflateMenu(R.menu.menu_toolbar_main);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_tb_reset) {
            CommonUtils.resetForm((ViewGroup) findViewById(R.id.main_vg));
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (validInsurer() && validRate() && validDiscount()) {
            Intent intent = new Intent(MainActivity.this, SingleEntryActivity.class);

            CommonUtils.rate = Integer.parseInt(rate.getText().toString());
            CommonUtils.insurerName = insurer.getText().toString();
            CommonUtils.discount = Integer.parseInt(discount.getText().toString());

            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(MainActivity.this, "Action Not Supported", Toast.LENGTH_SHORT).show();
    }

    private boolean validDiscount() {
        if (discount.getText().length() == 0) {
            discLayout.setError("Invalid Discount Entered");
            return false;
        }
        discLayout.setErrorEnabled(false);
        return true;
    }

    private boolean validRate() {
        if (rate.getText().length() == 0) {
            rateLayout.setError("Invalid Rate Entered");
            return false;
        }
        rateLayout.setErrorEnabled(false);
        return true;
    }

    private boolean validInsurer() {
        if (insurer.getText().length() == 0) {
            insurerLayout.setError("Invalid Insurer Name");
            return false;
        }
        insurerLayout.setErrorEnabled(false);
        return true;
    }

}
