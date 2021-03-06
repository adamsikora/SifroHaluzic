package cz.civilizacehra.sifrohaluzic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class PrincipReaderActivity extends AppCompatActivity {

    ImageView principy[] = new ImageView[6];

    TextView solutionView;
    Button nextButton;
    Button solutionButton;
    GridLayout wordGridLayout;

    int mHihglightColor = 0xFF24872F;
    int mDefaultColor = 0xFFBBBBBB;

    String mPrincipy[] = {"n", "m", "b", "s", "bin", "t"};
    String mPrincip = "n";
    Vector<String> mWords = new Vector<>();
    String mCurrentWord = "";
    Random mRandom = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_princip_reader);

        principy[0] = (ImageView)findViewById(R.id.nImage);
        principy[1] = (ImageView)findViewById(R.id.mImage);
        principy[2] = (ImageView)findViewById(R.id.bImage);
        principy[3] = (ImageView)findViewById(R.id.sImage);
        principy[4] = (ImageView)findViewById(R.id.binImage);
        principy[5] = (ImageView)findViewById(R.id.tImage);

        for (int i = 0; i < mPrincipy.length; ++i) {
            final int ii = i;
            if (i == 0) {
                principy[i].setBackgroundColor(mHihglightColor);
            } else {
                principy[i].setBackgroundColor(mDefaultColor);
            }
            principy[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mPrincip = mPrincipy[ii];
                    for (int i = 0; i < mPrincipy.length; ++i) {
                        if (i == ii) {
                            principy[i].setBackgroundColor(mHihglightColor);
                        } else {
                            principy[i].setBackgroundColor(mDefaultColor);
                        }
                    }
                    newWord();
                }
            });
        }

        solutionView = (TextView)findViewById(R.id.solutionView);

        nextButton = (Button)findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newWord();
            }
        });

        solutionButton = (Button)findViewById(R.id.solutionButton);
        solutionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                solutionView.setText(mCurrentWord);
            }
        });

        wordGridLayout = (GridLayout)findViewById(R.id.wordGridLayout);

        setImage(principy[0], "principy/n/7.png");
        setImage(principy[1], "principy/m/16.png");
        setImage(principy[2], "principy/b/9.png");
        setImage(principy[3], "principy/s/13.png");
        setImage(principy[4], "principy/bin/21.png");
        setImage(principy[5], "principy/t/15.png");

        try {
            InputStream inputStream = getApplicationContext().getAssets().open("cs_CZ_openoffice.canon");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;

            while((line = in.readLine()) != null) {
                StringPair word = StringPair.fromString(line);
                String first = word.getFirst();

                mWords.add(first);
            }
        } catch (java.io.IOException e) {

        }

        newWord();
    }

    private void setImage(ImageView btn, String path) {
        InputStream is = null;
        try {
            is = this.getResources().getAssets().open(path);
        } catch (IOException e) {
            Log.w("EL", e);
        }

        Bitmap image = BitmapFactory.decodeStream(is);
        btn.setImageBitmap(image);
    }

    private void newWord() {
        solutionView.setText("");
        mCurrentWord = mWords.elementAt(mRandom.nextInt(mWords.size()));
        wordGridLayout.removeAllViews();
        for (int i = 0; i < mCurrentWord.length(); ++i) {
            int index = mCurrentWord.charAt(i) - 'a' + 1;
            ImageView newView = new ImageView(this);
            int dim = 240;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dim, dim);
            newView.setLayoutParams(layoutParams);
            newView.setPadding(30,0,0,0);
            setImage(newView, "principy/" + mPrincip + "/" + index + ".png");
            wordGridLayout.addView((View)newView);
        }

        //for (int i = 0; i < 5; ++i) {
        //    solutions[i].setBackgroundColor(mDefaultColor);
        //    textView.setText("");
        //}
//
        //String mSource;
        //String mOut;
        //mCurrent = mRandom.nextInt(5);
//
        //if (mInvert) {
        //    mSource = mPrincip;
        //    mOut = "a";
        //} else {
        //    mSource = "a";
        //    mOut = mPrincip;
        //}
//
        //ArrayList<Integer> list = new ArrayList<>();
        //for (int i=1; i<=26; i++) {
        //    if (i != mLast) {
        //        list.add(i);
        //    }
        //}
        //Collections.shuffle(list);
        //for (int i=0; i<5; i++) {
        //    setImage(solutions[i], "principy/" + mSource + "/" + list.get(i) + ".png");
        //}
        //mLast = list.get(mCurrent);
//
        //InputStream is = null;
        //try {
        //    is = this.getResources().getAssets().open("principy/" + mOut + "/" + list.get(mCurrent) + ".png");
        //} catch (IOException e) {
        //    Log.w("EL", e);
        //}
//
        //Bitmap image = BitmapFactory.decodeStream(is);
        //imageView.setImageBitmap(image);
        //imageView.setBackgroundColor(mDefaultColor);
    }
}
