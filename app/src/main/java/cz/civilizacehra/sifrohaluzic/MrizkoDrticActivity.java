package cz.civilizacehra.sifrohaluzic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.PatternSyntaxException;

public class MrizkoDrticActivity extends AppCompatActivity {

    Button goBtn;
    EditText inputBox;
    TextView results;

    ColorStateList textColors;

    boolean isTrieInitialized;

    static {
        System.loadLibrary("MrizkoDrtic");
    }

    private native String grindGrid(String str);
    private native void initializeTrie(Object mgr);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrizko_drtic);

        goBtn = (Button) findViewById(R.id.GoBtn);
        inputBox = (EditText) findViewById(R.id.inputEditText);
        results = (TextView) findViewById(R.id.resultTextView);

        textColors = results.getTextColors();

        try {
            InputStream inputStream = getApplicationContext().getAssets().open("cs_CZ_openoffice.canon");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;

            while((line = in.readLine()) != null) {
                StringPair word = StringPair.fromString(line);
                //mTrieMap.put(word.getFirst(), "");
            }
        } catch (java.io.IOException e) {

        }

        if (goBtn != null) {
            goBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    results.setTextColor(textColors);
                    results.setText("Result:\n");

                    String input = inputBox.getText().toString().replaceAll("[^A-Za-z0-9_]", "").toLowerCase();

                    try {
                        if (!isTrieInitialized) {
                            initializeTrie((Object)getApplicationContext().getAssets());
                            isTrieInitialized = true;
                        }
                        String solutions = grindGrid(inputBox.getText().toString());
                        results.setText(results.getText().toString() + solutions);
                    } catch (Throwable e) {
                        String solutions = "";
                    }
                }
            });
        }
    }
}
