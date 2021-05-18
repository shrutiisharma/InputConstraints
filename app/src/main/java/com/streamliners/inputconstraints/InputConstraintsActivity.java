package com.streamliners.inputconstraints;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.streamliners.inputconstraints.databinding.ActivityInputConstraintsBinding;

import java.util.Objects;

public class InputConstraintsActivity extends AppCompatActivity {

    //for call
    private static final int REQUEST_CODE = 0;

    //create object for view binding
    ActivityInputConstraintsBinding b;

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
        b = ActivityInputConstraintsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        setTitle("Input Constraints");
    }


    /**
     * Called when a view (Take Input button here) has been clicked.
     * @param view : actual view (button in this case) that was clicked
     */
    public void takeInput(View view) {

        //create a bundle
        Bundle bundle = new Bundle();

        //put data inside the bundle
        if (b.ucAlphabetCB.isChecked())
            bundle.putString(Constants.UC_ALPHABETS, "true");

        if (b.lcAlphabetCB.isChecked())
            bundle.putString(Constants.LC_ALPHABETS, "true");

        if (b.digitsCB.isChecked())
            bundle.putString(Constants.DIGITS, "true");

        if (b.mathOpsCB.isChecked())
            bundle.putString(Constants.MATH_OPS, "true");

        if (b.otherCB.isChecked())
            bundle.putString(Constants.OTHER_SYMBOLS, "true");

        //make sure at least one checkBox should be selected
        if (bundle.isEmpty()){
            Toast.makeText(this, "Please select at least one checkbox!", Toast.LENGTH_SHORT).show();
            return;
        }

        //create intent and send bundle
        Intent intent = new Intent(this, InputActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * @param requestCode :
     * @param resultCode  :
     * @param data        :
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){

            //get data

            String input = Objects.requireNonNull(data).getStringExtra(Constants.INPUT);

            //show data
            b.result.setText("Your input: " + input);
            b.result.setVisibility(View.VISIBLE);
        }
    }
}