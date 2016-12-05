package ca.timandrew.timsquizap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnBegin = (Button)findViewById(R.id.btnBegin);

        //Begin button listener
        btnBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get username from edit box:
                EditText username = (EditText) findViewById(R.id.etName);

                if(username.getText().length() == 0)
                {
                    //If the box is empty show a message to enter a name:
                    Toast.makeText(getApplicationContext(), R.string.MainActivity_no_name, Toast.LENGTH_SHORT).show();
                }
                else if (username.getText().length() >= 35)
                {
                    //If the box has more than 100 chars:
                    Toast.makeText(getApplicationContext(), R.string.MainActivity_name_too_long, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Otherwise build & display good luck message:
                    String displayString =  getResources().getString(R.string.MainActivity_good_luck) + " " + username.getText().toString() + "!";
                    Toast.makeText(getApplicationContext(), displayString, Toast.LENGTH_SHORT).show();

                    //Then create new intent to load the next activity:
                    Intent i = new Intent(MainActivity.this, PlayQuiz.class);
                    //Setup the intent to take the username gathered here:
                    i.putExtra("USERNAME", username.getText().toString());
                    //Finally, start the intent/quiz:
                    startActivity(i);
                }
            }
        });//end Begin Button listener
    }
}


