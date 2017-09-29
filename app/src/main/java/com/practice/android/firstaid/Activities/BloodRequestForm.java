package com.practice.android.firstaid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.practice.android.firstaid.R;

public class BloodRequestForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_request_form);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_close:
                Intent intent = new Intent(BloodRequestForm.this, MainActivity.class);
                intent.putExtra("SelectedId", "Blood Network");
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
