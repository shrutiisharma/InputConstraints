package com.streamliners.inputconstraints;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.streamliners.inputconstraints.databinding.ActivityInputBinding;

import java.util.Objects;

public class InputActivity extends AppCompatActivity {

    //create object for view binding
    ActivityInputBinding b;


    /**
     * It initialises the activity.
     * @param savedInstanceState : reference to a Bundle object that is passed into the onCreate method of every Android Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupLayout();
    }

    /**
     * To set the layout of the InputConstraintsActivity
     */
    private void setupLayout() {

        b = ActivityInputBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        setTitle("Input & Send");
    }

    /**
     * Called when a view (Send button here) has been clicked.
     * @param view : actual view (button in this case) that was clicked
     */
    public void sendInput(View view) {

        String input = Objects.requireNonNull(b.inputTIL.getEditText()).getText().toString().trim();

        // if nothing is entered
        if (input.isEmpty()){
            b.inputTIL.setError("Please enter something!");
        }

        // if wrong input is entered
        else if (!input.matches(checkInput())){
            b.inputTIL.setError("Invalid Input!");
        }

        // send back the data
        else{

            //create intent
            Intent intent = new Intent(this, InputConstraintsActivity.class);

            //pass data
            intent.putExtra(Constants.INPUT, input);

            // RESULT_OK to confirm that the work for which this activity was opened was successful
            setResult(RESULT_OK, intent);

            //close this activity
            finish();
        }
    }


    /**
     * @return : regEx string to match with the input
     */
    private String checkInput() {

        //get bundle from InputConstraintsActivity
        Bundle bundle = getIntent().getExtras();

        //get strings from bundle
        for (String str:bundle.keySet()){
            bundle.getString(str);
        }

        //Make a new string using StringBuilder to use as Regular Expression
        StringBuilder regex = new StringBuilder();

        regex.append("^([");

        if(Boolean.parseBoolean(bundle.getString(Constants.UC_ALPHABETS,"false"))){
            regex.append("A-Z");
        }

        if(Boolean.parseBoolean(bundle.getString(Constants.LC_ALPHABETS,"false"))){
            regex.append("a-z");
        }

        if(Boolean.parseBoolean(bundle.getString(Constants.DIGITS,"false"))){
            regex.append("0-9");
        }

        if(Boolean.parseBoolean(bundle.getString(Constants.MATH_OPS,"false"))){
            regex.append("+-/*%");
        }

        if(Boolean.parseBoolean(bundle.getString(Constants.OTHER_SYMBOLS,"false"))){
            regex.append("@#\\\\^{}\\]\"\"^()?`~!;:''.,|\\[");
        }

        regex.append("])+");

        //return final string
        return regex.toString();
    }
}