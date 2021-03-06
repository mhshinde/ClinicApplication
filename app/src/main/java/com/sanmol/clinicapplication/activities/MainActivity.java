package com.sanmol.clinicapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sanmol.clinicapplication.DatabaseAdapter;
import com.sanmol.clinicapplication.R;

import java.text.DateFormat;
import java.util.Date;

import static com.sanmol.clinicapplication.R.id.new_patient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button newPatientBtn, existingPatientBtn;
    String title = "Do you want to close me?";
    DatabaseAdapter databaseAdapter;
    TextView date;
    public static int flag = 1;
    public static boolean errored = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseAdapter = new DatabaseAdapter(this);
        databaseAdapter = databaseAdapter.open();
        newPatientBtn = (Button) findViewById(new_patient);
        existingPatientBtn = (Button) findViewById(R.id.existing_patient);
        date = (TextView) findViewById(R.id.date);
        newPatientBtn.setOnClickListener(this);
        existingPatientBtn.setOnClickListener(this);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

// textView is the TextView view that should display it
        date.setText(currentDateTimeString);

      /*  if (getIntent().getExtras() != null) {
            flag = getIntent().getExtras().getInt("flag");
        }*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case new_patient:
               // flag = 1;
                Intent new_patient = new Intent(MainActivity.this, NewPatientActivity.class);
                startActivity(new_patient);
                finish();
                break;
            case R.id.existing_patient:
                //flag = 1;
                Intent existing_patient = new Intent(MainActivity.this, ExistingPatientActivity.class);
                startActivity(existing_patient);
                finish();
                break;
            default:
                break;
        }

    }


    public void backPopup(final String titleStr, final Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View dialogSignature = layoutInflater.inflate(R.layout.back_popup, null);

        final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                context);


        alertDialogBuilder.setView(dialogSignature);
        TextView title = (TextView) dialogSignature.findViewById(R.id.title);
        final EditText etcontact = (EditText) dialogSignature.findViewById(R.id.contact);

        title.setText(titleStr);

        Button dialog_btn_negative = (Button) dialogSignature.findViewById(R.id.dialog_btn_negative);
        dialog_btn_negative.setText("" + getResources().getString(R.string.cancel));

        Button dialog_btn_positive = (Button) dialogSignature.findViewById(R.id.dialog_btn_positive);
        dialog_btn_positive.setText("" + getResources().getString(R.string.submit));
        final android.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        dialog_btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact = etcontact.getText().toString();
                Boolean result = databaseAdapter
                        .authenticateEntry(contact);
                if (result == true) {
                    finish();
                } else {
                    Toast.makeText(MainActivity.this,
                            "You cannot exit from the application.", Toast.LENGTH_LONG)
                            .show();
//                    Intent main = new Intent(MainActivity.this, MainActivity.class);
//                    startActivity(main);
                }
            }
        });

        dialog_btn_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                //mHomeWatcher.stopWatch();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Log.e("KEYCODE_BACK", "");
            backPopup(title, this);
            return true;
        }
        if ((keyCode == KeyEvent.KEYCODE_MENU)) {
            Log.e("KEYCODE_MENU", "");
            backPopup(title, this);
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backPopup(title, this);
    }

    @Override
    public void onPause() {

        /*finish();
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);*/
        /*Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setComponent(new ComponentName("com.sanmol.clinicapplication","com.sanmol.clinicapplication.MainActivity"));
        startActivity(intent);*/
        super.onPause();
    }

    @Override
    protected void onStop() {
        //finish();
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(MainActivity.this,
                        "Home button  pressed", Toast.LENGTH_LONG).show();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

   /* @Override
    public void onUserLeaveHint() {
        if (flag == 0) {
            finish();
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            super.onUserLeaveHint();
        }
    }*/
}


