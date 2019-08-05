package com.example.geoquiz;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextVew;
    private int coast = 0;


    private static String TAG = "QuizActivity";
    private static String KEY_INDEX = "index";
    private static String STATE_BUTTON = "state button";
    private static boolean PRESSED = false;
    private static String COAST = "coast";

    private ArrayList<Integer> alreadyAnswer = new ArrayList<>();


    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundel) called");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            PRESSED = savedInstanceState.getBoolean(STATE_BUTTON, false);
            coast = savedInstanceState.getInt(COAST, 0);

        }

        mQuestionTextVew = findViewById(R.id.question_text_view);

        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mNextButton = findViewById(R.id.next_button);
        mPrevButton = findViewById(R.id.prev_button);


        if (PRESSED == true) {
            pressedButton();
        }

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                alreadyAnswer.add(mCurrentIndex);
                Log.d(TAG, alreadyAnswer.toString());
                pressedButton();

            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                alreadyAnswer.add(mCurrentIndex);
                Log.d(TAG, alreadyAnswer.toString());
                pressedButton();
            }
        });

        mQuestionTextVew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == mQuestionBank.length - 1) {

                    double procent = coast * 100 / mQuestionBank.length;
                    Toast.makeText(QuizActivity.this, "It's last question! " + "Your coast = " + procent + "%", Toast.LENGTH_LONG).show();
                } else {
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                    updateQuestion();


                }
                PRESSED = false;
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCurrentIndex != 0) {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                    updateQuestion();
                } else {
                    Toast.makeText(QuizActivity.this, "It's first question", Toast.LENGTH_LONG).show();
                }
            }
        });
        updateQuestion();

    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismAnswerTrue();
        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            coast++;

        } else {
            messageResId = R.string.incorrect_toast;

        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getmTextResId();
        mQuestionTextVew.setText(question);
        for (int i = 0; i < mQuestionBank.length; i++) {
            if (i == question) {
                Log.d(TAG, " i = " + i + " mCurrentIndex = " + question);
                pressedButton();
            } else {
                mTrueButton.setEnabled(true);
                mFalseButton.setEnabled(true);
            }
        }
    }

    private void pressedButton() {
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
        PRESSED = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstance");
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putBoolean(STATE_BUTTON, PRESSED);
        outState.putInt(COAST, coast);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}


