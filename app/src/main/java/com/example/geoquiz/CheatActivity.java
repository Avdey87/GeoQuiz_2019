package com.example.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static String EXTRA_ANSWER_IS_TRUE = "com.exmpale.geoquiz.answer_is_true";
    private static String EXTRA_ANSWER_SHOW = "com.exmpale.geoquiz.answer_show";
    private static String CURRENT_ANSWER_STATE = " current answer state";
    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private TextView mSdkLevel;
    private boolean mAnswerState;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if (savedInstanceState != null) {
            mAnswerState = savedInstanceState.getBoolean(CURRENT_ANSWER_STATE, mAnswerState);
            setAnswerShowResult(mAnswerState);
        }

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = findViewById(R.id.answer_text_view);

        mShowAnswerButton = findViewById(R.id.show_answer_button);

        mSdkLevel = findViewById(R.id.sdk_level);
        mSdkLevel.setText(getString(R.string.apiLevel, Build.VERSION.RELEASE));

        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                mAnswerState = true;
                setAnswerShowResult(mAnswerState);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswerButton.getWidth() / 2;
                    int cy = mShowAnswerButton.getHeight() / 2;
                    float radius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils
                            .createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswerButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                } else {
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void setAnswerShowResult(boolean isAnswerShow) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOW, isAnswerShow);
        setResult(RESULT_OK, data);
    }

    public static boolean wasAnswerShow(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOW, false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(CURRENT_ANSWER_STATE, mAnswerState);

    }
}
