package com.example.danesh.gradienttransition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class MyActivity extends Activity {

    private ListView mListView;
    private AnimateGradients mGradient;
    private ArrayList<String> list1 = new ArrayList<String>(Arrays.asList(new String[]{"reintegrated",
            "bloomed",
            "chargeless",
            "amid",
            "stretchy",
            "attorn",
            "lindisfarne",
            "argasid",
            "corporate"}));
    private int mNextStart = Color.parseColor("#e91e63"), mNextEnd = Color.parseColor("#ff5722");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        mListView = (ListView) findViewById(R.id.list);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list1);
        mListView.setAdapter(adapter);
        mGradient = (AnimateGradients) findViewById(R.id.gradient);
        mGradient.setColors(Color.parseColor("#ff9800"), Color.parseColor("#ffeb3b"));
    }

    public void slide(View view) {
        mListView.buildDrawingCache();
        final Bitmap b = mListView.getDrawingCache();
        final ImageView imageView = (ImageView) findViewById(R.id.fakeView);
        imageView.setImageBitmap(b);
        imageView.setVisibility(View.VISIBLE);
        mListView.setTranslationX(mListView.getWidth());

        Collections.shuffle(list1);

        imageView.animate().translationXBy(-1 * mListView.getWidth()).setDuration(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //imageView.setBackground(null);
                imageView.setVisibility(View.GONE);
                imageView.setTranslationX(0);
            }
        });

        // WITH LAYER IMPORTANT
        mListView.animate().withLayer().translationX(0).setDuration(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                ((ArrayAdapter) mListView.getAdapter()).notifyDataSetChanged();
            }
        });

        final int nextStart = mNextStart;
        final int nextEnd = mNextEnd;
        int[] startColors = new int[]{mGradient.getStartColor(), nextStart};
        int[] endColors = new int[]{mGradient.getEndColor(), nextEnd};

        mNextStart = mGradient.getStartColor();
        mNextEnd = mGradient.getEndColor();
        mGradient.startTransition(startColors, endColors, 500);
    }

    public int randomColor() {
        Random rnd = new Random(System.currentTimeMillis());
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public static String randomString() {
        Random generator = new Random(System.currentTimeMillis());
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(15);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
