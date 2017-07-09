package hr.fer.ruazosa.evidencijaopremeulaboratoriju;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class SearchActivity extends AppCompatActivity {
    private String filter;
    private String searchWord;
    private String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itemsIntent = new Intent(SearchActivity.this, ItemsActivity.class);
                fetchQueryConditions();
                itemsIntent.putExtra("filter", filter);
                itemsIntent.putExtra("search", searchWord);
                itemsIntent.putExtra("status", status);
                startActivity(itemsIntent);
            }
        });

        Button usersButton = (Button) findViewById(R.id.usersButton);
        usersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent usersActivityIntent = new Intent(SearchActivity.this, UsersActivity.class);
                startActivity(usersActivityIntent);
            }
        });

        Button addItemButton = (Button) findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addItemIntent = new Intent(SearchActivity.this, AddItemActivity.class);
                startActivity(addItemIntent);
            }
        });
    }

    private void fetchQueryConditions() {
        Spinner filterSpinner = (Spinner) findViewById(R.id.spinner);
        filter = (String) filterSpinner.getSelectedItem();

        EditText searchEditText = (EditText) findViewById(R.id.searchEditText);
        searchWord = searchEditText.getText().toString();

        RadioButton available = (RadioButton) findViewById(R.id.availableRadioButton);
        RadioButton unavailable = (RadioButton) findViewById(R.id.unavailableRadioButton);
        RadioButton reserved = (RadioButton) findViewById(R.id.reservedRadioButton);

        RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup);
        group.getCheckedRadioButtonId();

        if(reserved.getId() == group.getCheckedRadioButtonId()) status = "reserved";
        else if(unavailable.getId() == group.getCheckedRadioButtonId()) status = "unavailable";
        else if(available.getId() == group.getCheckedRadioButtonId()) status = "available";
        else status = "all";

    }
}
