package cz.civilizacehra.sifrohaluzic;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DebinarizatorActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    NumberPicker numberPicker;
    LinearLayout rowsLayout;
    ArrayList<View> rows = new ArrayList<View>();
    LayoutInflater mLayoutInflater;

    final int bits[] = { R.id.bit1, R.id.bit2, R.id.bit3, R.id.bit4, R.id.bit5 };
    final int results[] = { R.id.result11, R.id.result12, R.id.result21, R.id.result22 };
    final String alphabet[] = {"?", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debinarizator);

        mLayoutInflater = getLayoutInflater();

        rowsLayout = (LinearLayout) findViewById(R.id.rowsLayout);

        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(30);
        numberPicker.setMinValue(0);
        loadNRows();
        numberPicker.setWrapSelectorWheel(false);

        for (int i = 0; i < numberPicker.getValue(); ++i) {
            addRow();
        }

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                while (rows.size() < newVal) {
                    addRow();
                }
                while (rows.size() > newVal) {
                    removeRow();
                }
                saveNRows();
            }
        });
    }

    protected String getLetter(int i) {
        if (i > 26) {
            i = 0;
        }
        return alphabet[i];
    }

    protected void addRow() {
        final RelativeLayout layout = (RelativeLayout) mLayoutInflater.inflate(R.layout.binaryrow, null, false);
        rows.add((View)layout);
        rowsLayout.addView((View)layout);

        for (int j = 0; j < 5; ++j) {
            ImageView view = (ImageView) layout.findViewById(bits[j]);
            view.setTag(0);
            view.setImageResource(getResources().getIdentifier("@android:drawable/alert_light_frame", null, null));
        }
        for (int j = 0; j < 5; ++j) {
            final ImageView view = (ImageView) layout.findViewById(bits[j]);
            view.setOnClickListener(new View.OnClickListener()
            {
                private int counter = (int) view.getTag();

                public void onClick(View v)
                {
                    ++counter;
                    counter %= 2;
                    view.setTag(counter);
                    if (counter == 0) {
                        view.setImageResource(getResources().getIdentifier("@android:drawable/alert_light_frame", null, null));
                    } else if (counter == 1) {
                        view.setImageResource(getResources().getIdentifier("@android:drawable/alert_dark_frame", null, null));
                    }/* else {
                        view.setImageResource(getResources().getIdentifier("@android:drawable/ic_menu_help", null, null));
                    }*/

                    boolean compute = true;
                    int values[] = new int[5];
                    for (int k = 0; k < 5; ++k) {
                        ImageView view = (ImageView) layout.findViewById(bits[k]);
                        int value = (int) view.getTag();
                        values[k] = value;
                        if (value == 2) {
                            compute = false;
                        }
                    }
                    if (compute) {
                        int down = 0, up = 0;
                        for (int k = 0; k < 5; ++k) {
                            down *= 2;
                            down += values[k];
                        }
                        for (int k = 4; k >= 0; --k) {
                            up *= 2;
                            up += values[k];
                        }
                        if (up >= 0 && up <= 31) {
                            TextView text = (TextView) layout.findViewById(results[0]);
                            text.setText(getLetter(up));
                            text = (TextView) layout.findViewById(results[1]);
                            text.setText(getLetter(31 - up));
                        }
                        if (down >= 0 && down <= 31) {
                            TextView text = (TextView) layout.findViewById(results[2]);
                            text.setText(getLetter(down));
                            text = (TextView) layout.findViewById(results[3]);
                            text.setText(getLetter(31 - down));
                        }
                    } else {
                        for (int k = 0; k < 4; ++k) {
                            TextView text = (TextView) layout.findViewById(results[k]);
                            text.setText("");
                        }
                    }
                }
            });
        }
    }

    protected void removeRow() {
        int size = rows.size();
        rowsLayout.removeView(rows.get(size-1));
        rows.remove(size-1);
    }

    private void saveNRows(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("nRows", numberPicker.getValue());
        editor.apply();
    }

    public void loadNRows(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        numberPicker.setValue(sharedPreferences.getInt("nRows", 15));
    }
}
