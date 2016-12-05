package ca.timandrew.timsquizap;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.*;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PlayQuiz extends AppCompatActivity {

    /*Global Variables */
    //variable used to hold quiz hashmap
    HashMap quizHashMap;
    //variable usesd to hold current correct answer and 3 dummy answers:
    String currentAnswer;
    String wrongAnswer1, wrongAnswer2, wrongAnswer3;

    //variable to hold username:
    String username;

    //Variable used to hold the question and answer list:
    List questionList;
    List answerList;
    //Variable used to hold the correct answer (it will be 1-4)
    int answer;
    //variable used to hold the current question (it's 1 because the first question displays on start):
    int questionNumber = 1;
    //variable used to hold the initial score:
    int score = 0;
    //variable used to hold the number of questions (determined by the # of lines in quiz file)
    int questions;

    //Set up variables for the activity elements:
    TextView tvPickAnswer;
    TextView tvQuizBox;
    TextView user;
    TextView tvScore;
    Button btnQuit;
    Button btnQuiz1;
    Button btnQuiz2;
    Button btnQuiz3;
    Button btnQuiz4;



    //method to build the question/answer hashmap
    private HashMap<String,String> BuildHashmap(String file)
    {
        /*Set up an asset Manager reader to read the data from the quiz.txt assets file*/
        AssetManager am = getApplicationContext().getAssets();
        InputStream is = null;
        try { // to open the file
            is = am.open(file);
        }
        catch (IOException e)//if error
        {
            Log.v("OpenFile","There was an error reading from the file");
        }

        /*Create new hashmap:*/
        HashMap<String,String> quizHashMap = new HashMap<String,String>();

        /*Then read the new input stream into the hashmap*/
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s;
        try
        {
            //While the line is not null:
            while((s = br.readLine())!=null)
            {
                //Find the delimiter of line and then add before it and after it to the hashmap:
                int delimiter = s.indexOf(";");
                String question = s.substring(0, delimiter);
                String answer = s.substring(delimiter+1, s.length() );
                quizHashMap.put(question, answer);
            }
        }
        catch(IOException e)
        {
            Log.v("ReadInputString","Error reading line from input stream.");
        }

        /*/HASHMAP CONSOLE PRINTOUT:
        int count = 0;
        for (Map.Entry<String,String> entry : quizHashMap.entrySet())
        {
            count++;
            System.out.println ("Question(K)" + count + ": \t" + entry.getKey());
            System.out.println ("\tAnswer(V)" + count + ": \t" + entry.getValue());
        } // End TEST */

        return quizHashMap;
    }

    //method to build the question ArrayList
    private List BuildQuestionList(HashMap quizHashMap)
    {
        //Shuffle the questions from the hashmap into a new array - modded from http://stackoverflow.com/questions/6017338/how-do-you-shuffle-elements-in-a-map /
        List questionList = new ArrayList(quizHashMap.keySet());
        Collections.shuffle(questionList);
        for (Object o : questionList) {
            quizHashMap.get(o);
        }

        /*/ARRAYLIST CONSOLE PRINTOUT TEST:
        int count = 0;
        for (int b = 0 ; b < questions.size(); b++)
        {
                count++;
                System.out.println("question " + count + ": \t" + questions.get(b));
                //System.out.println("answer " + count + ": \t" + answers.get(b));
        }// End TEST */

        return questionList;
    }

    //build the answers list:
    private List BuildAnswerList(HashMap quizHashMap)
    {
        //Shuffle the questions from the hashmap into a new array - modded from http://stackoverflow.com/questions/6017338/how-do-you-shuffle-elements-in-a-map /
        List answerList = new ArrayList(quizHashMap.values());
        Collections.shuffle(answerList);
        for (Object o : answerList) {
            quizHashMap.get(o);
        }

        /*/ARRAYLIST CONSOLE PRINTOUT TEST:
        int count = 0;
        for (int b = 0 ; b < answerList.size(); b++)
        {
                count++;
                System.out.println("question " + count + ": \t" + answerList.get(b));
                //System.out.println("answer " + count + ": \t" + answers.get(b));
        }// End TEST */

        return answerList;
    }
    //Method to display next question:
    private List NewScreenQuestion()
    {
        //grab first item from questionList and put it in the activity's QuizBox
        tvQuizBox.setText(questionList.get(0).toString());
        if (questionList.size() == 1)
            return questionList;
        else
        {
            //create a new list
            List ShortenedQuestionList = new ArrayList();
            //Iterate through the questionList starting at index 1 and put everything in a new list:
            for (int i = 1; i < questionList.size(); i++) {
                ShortenedQuestionList.add(questionList.get(i));
            }
            return ShortenedQuestionList;
        }
    }

    //Update the score:
    private void UpdateScore()
    {
        tvScore.setText(getResources().getString(R.string.PlayQuiz_quiz_score) + " " + score + "/" + questions);
    }

    //update the pick answer textview:
    private void UpdatePickAnswer()
    {
        tvPickAnswer.setText(getResources().getString(R.string.PlayQuiz_pick_answer) + " " + questionNumber + "/" + questions + ":");
    }

    //Method to get current answer:
    private void getCurrentAnswer()
    {
        //this hits the hashmap 1 time to get the current answer:
        currentAnswer = quizHashMap.get(questionList.get(0).toString()).toString();

        //test this to see if the answer is right:
        //System.out.println(questionList.get(0).toString());
        //System.out.println(currentAnswer);
    }

    //method to pick 3 more answers:
    private void PickThreeWrongAnswers()
    {
        //reinitialize wrong answers to nothing:
        wrongAnswer1 = null;
        wrongAnswer2 = null;
        wrongAnswer3 = null;

        //pick wrongAnswer1:
        do
        {
            int rnd = new Random().nextInt(answerList.size());
            wrongAnswer1 = answerList.get(rnd).toString();
            //System.out.println(wrongAnswer1);
        } while (currentAnswer.equals(wrongAnswer1));

        //pick wrongAnswer2:
        do
        {
            int rnd = new Random().nextInt(answerList.size());
            wrongAnswer2 = answerList.get(rnd).toString();
            //System.out.println(wrongAnswer2);
        } while (currentAnswer.equals(wrongAnswer2) || wrongAnswer1.equals(wrongAnswer2));

        //pick wrongAnswer3:
        do
        {
            int rnd = new Random().nextInt(answerList.size());
            wrongAnswer3 = answerList.get(rnd).toString();
            //System.out.println(wrongAnswer3);
        } while (currentAnswer.equals(wrongAnswer3) || wrongAnswer1.equals(wrongAnswer3) || wrongAnswer2.equals(wrongAnswer3));

    }

    //method to randomize and build the buttons:
    private void BuildButtons()
    {
        //initialize private variables for wrong answers:
        int wrongans1 = 0;
        int wrongans2 = 0;
        int wrongans3 = 0;

        //randomize the right answer from 1-4
        answer = new Random().nextInt(5 - 1)+1;

        //randomize wrongans1
        if (answer == 1)
        {
            wrongans1 = 2;
            wrongans2 = 3;
            wrongans3 = 4;
        }
        else if (answer == 2)
        {
            wrongans1 = 1;
            wrongans2 = 3;
            wrongans3 = 4;
        }
        else if (answer == 3)
        {
            wrongans1 = 1;
            wrongans2 = 2;
            wrongans3 = 4;
        }
        else
        {
            wrongans1 = 1;
            wrongans2 = 2;
            wrongans3 = 3;
        }



        //send to a function to brand the answers to the button:
        BrandButton(answer, currentAnswer);
        BrandButton(wrongans1, wrongAnswer1);
        BrandButton(wrongans2, wrongAnswer2);
        BrandButton(wrongans3, wrongAnswer3);
    }

    //a function used by BuildButtons() to brand the buttons:
    private void BrandButton(int number, String answer)
    {
        switch (number) {
            case 1:
                btnQuiz1.setText(answer);
                break;
            case 2:
                btnQuiz2.setText(answer);
                break;
            case 3:
                btnQuiz3.setText(answer);
                break;
            case 4:
                btnQuiz4.setText(answer);
                break;
        }
    }

    //method to find out if the button pressed is the answer (or not):
    private void CheckAnswer(int button)
    {

        if (answer == button) // if they got it right:
        {
            //increment their score by 1:
            score++;
            Toast.makeText(getApplicationContext(), "CORRECT!!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "INCORRECT", Toast.LENGTH_SHORT).show();
        }

        //check if questions = # of questions and exit:
        if (questionNumber == questions)
        {
            //go to the end game:
            Intent i = new Intent(PlayQuiz.this, EndGame.class);
            //Setup the intent to take the username gathered here:
            i.putExtra("SCORE", score);
            i.putExtra("QUESTIONS", questions);
            i.putExtra("NAME", username);
            startActivity(i);
        }
        else { //otherwise:
            //increment questionNumber:
            questionNumber++;
            //Recall the functions that were run on the first run:
            getCurrentAnswer();
            PickThreeWrongAnswers();
            questionList = NewScreenQuestion();
            UpdateScore();
            UpdatePickAnswer();
            BuildButtons();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_quiz);

        //First set up variables for this activity's elements:
        tvPickAnswer = (TextView)findViewById(R.id.tvPickAnswer);
        tvQuizBox = (TextView)findViewById(R.id.tvQuizBox);
        user = (TextView)findViewById(R.id.tvUser);
        tvScore = (TextView)findViewById(R.id.tvScore);
        btnQuit = (Button)findViewById(R.id.btnQuit);
        btnQuiz1 = (Button)findViewById(R.id.btnQuiz1);
        btnQuiz2 = (Button)findViewById(R.id.btnQuiz2);
        btnQuiz3 = (Button)findViewById(R.id.btnQuiz3);
        btnQuiz4 = (Button)findViewById(R.id.btnQuiz4);

        //On the creation of the activity, get the username sent from MainActivity:
        Bundle data = getIntent().getExtras();
        username = data.getString("USERNAME");



        //Then, the username in textbox for this activity:
        user.setText(getResources().getString(R.string.PlayQuiz_quiz_user) + " " + username);

        //Then, define the quiz file (stored in assets):
        String quizFile = "Quiz.txt";

        //Then, use a method to build a HashMap for the quiz using that file:
        quizHashMap = BuildHashmap(quizFile);

        //Next, use a method to take the HashMap and build an ArrayList of the available questions:
        questionList = BuildQuestionList(quizHashMap);

        //Next, use a method to take the HashMap and build an ArrayList of available answers:
        answerList = BuildAnswerList(quizHashMap);

        //Then, set up variables for the user's score and the initial number of questions that will be asked:
        questions = questionList.size();



        /*between here and the buttons (below), is initial startup for the first question*/

        //First, set the current answer:
        getCurrentAnswer();

        //Then, pick 3 wrong answers that aren't that answer:
        PickThreeWrongAnswers();

        //Then, use a function to set the initial question and remove that question from the questionList:
        questionList = NewScreenQuestion();

        //Then use a function to set the initial score for the text view in the acctivity:
        UpdateScore();

        //Then use a fuunction to set the initial tvPickAnswer (it displays the current question the user is on):
        UpdatePickAnswer();

        //Finally use a function to randomize the answers and plug them into the buttons:
        BuildButtons();


        /* The following is for the button functions */

        //quit button listener
        btnQuit.setOnClickListener(new OnClickListener()
        {
            //set up a new intent to go back to mainActivity:
            Intent a = new Intent(getApplicationContext(), MainActivity.class);
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(), R.string.PlayQuiz_quit_msg, Toast.LENGTH_SHORT).show();
                startActivity(a);
            }

        });// end quit button listener

        //First question button listener:
        btnQuiz1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int buttonPressed = 1;
                CheckAnswer(buttonPressed);
            }
        });//end first button listener

        //Second question button listener:
        btnQuiz2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int buttonPressed = 2;
                CheckAnswer(buttonPressed);
            }
        });//end second button listener

        //Third question button listener:
        btnQuiz3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int buttonPressed = 3;
                CheckAnswer(buttonPressed);
            }
        });//end third button listener

        //Fourth question button listener:
        btnQuiz4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int buttonPressed = 4;
                CheckAnswer(buttonPressed);
            }
        });//end fourth button listner

    }
}