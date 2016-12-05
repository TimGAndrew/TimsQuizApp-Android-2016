package ca.timandrew.timsquizap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.R.color.holo_green_dark;
import static android.R.color.holo_green_light;
import static android.R.color.holo_orange_dark;
import static android.R.color.holo_orange_light;
import static android.R.color.holo_red_dark;
import static android.R.color.holo_red_light;

public class EndGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        //On the creation of the activity, get the name, score, and # of questions from PlayQuiz
        Bundle data = getIntent().getExtras();
        int score = data.getInt("SCORE");
        int questions = data.getInt("QUESTIONS");
        String name = data.getString("NAME");
        double percent;
        String closing;

        //set up variables to handle some Activity elements:
        Button finish = (Button)findViewById(R.id.btnFinish);
        TextView results = (TextView)findViewById(R.id.tvFinalScore);
        TextView pct = (TextView)findViewById(R.id.tvFinalScorePct);

        //do some calculations to get the percent:
        percent = ((double)score/(double)questions)* 100;
        int percentint = (int)percent;
        //then do some iffy stuff to determine the colour of the text:
        if (percent <= 50)
        {
            pct.setTextColor(getResources().getColor(holo_red_dark));
            closing = ".";
        }
        else if (percent <= 75)
        {
            pct.setTextColor(getResources().getColor(holo_orange_dark));
            closing = "!";
        }
        else
        {
            pct.setTextColor(getResources().getColor(holo_green_dark));
            closing = "!!!!";
        }

        //set the results to the results of the quiz:
        results.setText(name + getResources().getString(R.string.EndGame_youscored) + " "+ score + "/" + questions + closing);
        pct.setText(percentint + "%");

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EndGame.this, MainActivity.class);

                startActivity(i);
            }
        });
    }
}
