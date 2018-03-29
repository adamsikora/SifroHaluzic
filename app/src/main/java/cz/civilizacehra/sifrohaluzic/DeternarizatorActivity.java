package cz.civilizacehra.sifrohaluzic;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

public class DeternarizatorActivity extends DebaseatorActivity {
    Switch direction;
    CheckBox ch;
    int mapping[][] = {
            { 0, 1, 2 },
            { 0, 2, 1 },
            { 1, 0, 2 },
            { 1, 2, 0 },
            { 2, 0, 1 },
            { 2, 1, 0 }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deternarizator);
        bits = new int[]{ R.id.tern1, R.id.tern2, R.id.tern3 };
        results = new int[]{ R.id.result1, R.id.result2, R.id.result3, R.id.result4, R.id.result5, R.id.result6 };
        commonPostCreate(3, 3, R.layout.ternaryrow);
        direction = (Switch) findViewById(R.id.directionSwitch);
        direction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                for (View row : rows) {
                    row.callOnClick();
                }
            }
        });
        ch = (CheckBox) findViewById(R.id.chCheckBox);
        ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                for (View row : rows) {
                    row.callOnClick();
                }
            }
        });
    }

    @Override
    protected void onRowClick(RelativeLayout layout) {
        int values[] = new int[mBaseLength];
        for (int k = 0; k < mBaseLength; ++k) {
            ImageView view = (ImageView) layout.findViewById(bits[k]);
            int value = (int) view.getTag();
            values[k] = value;
        }
        int iterate[];
        if (direction.isChecked()) {
            iterate = new int[]{ 2, 1, 0 };
        } else {
            iterate = new int[]{ 0, 1, 2 };
        }
        int offset = mode.getCheckedRadioButtonId() == R.id.rbtn0 ? 1 : 0;

        for (int i = 0; i < 6; ++i) {
            int value = 0;
            for (int j : iterate) {
                value *= mBase;
                value += mapping[i][values[j]];
            }
            if (value >= 0 && value <= mBaseMax) {
                TextView text = (TextView) layout.findViewById(results[i]);
                String letter = ch.isChecked() ? getChLetter(value + offset) : getLetter(value + offset);
                text.setText(letter);
            }
        }

        /*int down = 0, up = 0;
        for (int k = 0; k < mBaseLength; ++k) {
            down *= mBase;
            down += values[k];
        }
        for (int k = mBaseLength - 1; k >= 0; --k) {
            up *= mBase;
            up += values[k];
        }
        int offset = mode.getCheckedRadioButtonId() == R.id.rbtn0 ? 1 : 0;

        if (up >= 0 && up <= mBaseMax) {
            TextView text = (TextView) layout.findViewById(results[0]);
            text.setText(getLetter(up + offset));
            text = (TextView) layout.findViewById(results[1]);
            text.setText(getLetter(mBaseMax - up + offset));
        }
        if (down >= 0 && down <= mBaseMax) {
            TextView text = (TextView) layout.findViewById(results[2]);
            text.setText(getLetter(down + offset));
            text = (TextView) layout.findViewById(results[3]);
            text.setText(getLetter(mBaseMax - down + offset));
        }*/
    }
}