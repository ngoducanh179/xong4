package com.example.xong4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty
    @Length(min = 3, max = 10)
    @Pattern(regex = "/^[a-z ,.'-]+$/i")
    private EditText editRestaurantName;

    @NotEmpty
    private EditText editRestaurantType;

//    @NotEmpty
//    private TextClock editVisitDate;

    @NotEmpty
    @Pattern(regex =  "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$")
    private EditText editAverage;

    @NotEmpty
    private EditText editNotes;

    @NotEmpty
    private EditText editReporter;

    @Checked
    private CheckBox checkBoxAgree;

    private Button buttonSave;

    private Validator validator;

    String[] languages = { "Need to improve","OKAY","Good ","Excellent"};
    Spinner editCleanRatingSet;
    Spinner editFoodRatingSet;
    Spinner editServiceRatingSet;
    DatePicker picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        validator = new Validator(this);
        validator.setValidationListener(this);
        editCleanRatingSet	=	(Spinner) findViewById(R.id.editCleanRating);
        editFoodRatingSet	=	(Spinner) findViewById(R.id.editFoodRating);
        editServiceRatingSet	=	(Spinner) findViewById(R.id.editServiceRating);


        ArrayAdapter	adapter	=	new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        editCleanRatingSet.setAdapter(adapter);
        editFoodRatingSet.setAdapter(adapter);
        editServiceRatingSet.setAdapter(adapter);

    }

    private void initView() {
        editRestaurantName = findViewById(R.id.editRestaurantName);
        editRestaurantType = findViewById(R.id.editRestaurantType);
//        editVisitDate = findViewById(R.id.editVisitDate);
        editAverage = findViewById(R.id.editAverage);
        editNotes = findViewById(R.id.editNotes);
        editReporter = findViewById(R.id.editReporter);
        checkBoxAgree = findViewById(R.id.checkBoxAgree);
        buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSave_onClick(view);
            }
        });
    }

    private void buttonSave_onClick(View view) {
        validator.validate();
        String restaurantName = editRestaurantName.getText().toString();
        if (restaurantName.equalsIgnoreCase("pmk")) {
            editRestaurantName.setError(getText(R.string.Restaurant_already_exists));
        }
    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "We got it right!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            //display error message
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}