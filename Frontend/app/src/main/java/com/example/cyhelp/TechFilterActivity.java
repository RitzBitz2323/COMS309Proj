package com.example.cyhelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TechFilterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int categoryID;
    int techID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_filter);

        Intent intent = getIntent();
        techID = intent.getIntExtra("techID", 2);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void openMap(View view) {
        Intent intent = new Intent(this, TechActivity.class);
        intent.putExtra("categoryID", categoryID);
        intent.putExtra("id", techID);
        startActivity(intent);
    }

    /**
     * This method is called when an item is selected from the Category drop down menu
     * It records the item selected so that it can be sent to the Tech Activity while will filter the
     * tickets according to category
     * @param parent The AdapterView where the selection happened
     * @param view The View within the AdapterView that was selected
     * @param pos The position of the item selected, starting at 0
     * @param id The row ID of the item that is selected
     */
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        System.out.println("Position: " + pos);
        categoryID = pos + 2;
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
}