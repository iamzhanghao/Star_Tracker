package com.tinymos.demo.star_tracker.phone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tinymos.demo.star_tracker.R;

public class P4SearchStar extends Activity {


    Button   mButton;
    EditText mSearchBar;
    TextView mTitle,mOption;
    String starName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_input);

        mTitle = (TextView)findViewById(R.id.title);
        mButton = (Button)findViewById(R.id.search_button);
        mSearchBar = (EditText)findViewById(R.id.search_bar);
        mOption = (TextView)findViewById(R.id.option);

        mTitle.setText("Tracker is Ready!");
        mSearchBar.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
        mSearchBar.setHint("Type you star");
        mOption.setText("Star Name");
        mButton.setText("Search");




        mButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        starName = mSearchBar.getText().toString();
                        if (starName.equals("")){
                            Toast.makeText(getApplication(),"Star can't be empty!", Toast.LENGTH_LONG).show();
                        }else {
                            startTracking(view);
                        }
                    }
                });

    }

    @Override
    public void onBackPressed() {
        //disable backbutton
    }

    public void startTracking(View view)
    {
        Intent intent = new Intent(P4SearchStar.this, P5TrackingProgress.class);
        intent.putExtra("name",starName);
        startActivity(intent);
    }


}
