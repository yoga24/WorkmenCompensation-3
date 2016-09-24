package com.cyorg.uiic.workmencompensationrainbow3.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.cyorg.uiic.workmencompensationrainbow3.R;
import com.cyorg.uiic.workmencompensationrainbow3.utils.CommonUtils;

/**
 * Created by Yoganand on 25-09-2016.
 */
public class UserFirstRegistrationActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private EditText userName, otherDesignation;
    private String designationSelected = "";
    private boolean otherDesignationChosen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        userName = (EditText) findViewById(R.id.ur_edit_username);
        otherDesignation = (EditText) findViewById(R.id.ur_edit_other_desig);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        designationSelected = (String) parent.getItemAtPosition(position);
        if ("other".equalsIgnoreCase(designationSelected)) {
            otherDesignation.setVisibility(View.VISIBLE);
            otherDesignationChosen = true;
        } else {
            otherDesignation.setVisibility(View.GONE);
            otherDesignationChosen = false;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        designationSelected = "Branch Manager";
    }

    @Override
    public void onClick(View v) {
        if ((!otherDesignationChosen && CommonUtils.emptyStringCheck(userName.getText())) || (otherDesignationChosen && CommonUtils.emptyStringCheck(userName.getText(), otherDesignation.getText()))) {
            CommonUtils.createCanvasTemplate(userName.getText().toString(), designationSelected);
            startActivity(new Intent(UserFirstRegistrationActivity.this, MainActivity.class));
        } else {
            Toast.makeText(UserFirstRegistrationActivity.this, "One Or More Fileds are EMPTY", Toast.LENGTH_SHORT).show();
        }
    }
}
