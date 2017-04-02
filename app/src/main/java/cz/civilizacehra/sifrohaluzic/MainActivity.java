package cz.civilizacehra.sifrohaluzic;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.PatternSyntaxException;

public class MainActivity extends AppCompatActivity {

    TextView presmyslovnik, debinarizator, mrizkodrtic, principtrainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presmyslovnik = (TextView)findViewById(R.id.Presmyslovnik);
        debinarizator = (TextView)findViewById(R.id.Debinarizator);
        mrizkodrtic = (TextView)findViewById(R.id.MrizkoDrtic);
        principtrainer = (TextView)findViewById(R.id.PrincipTrainer);

        presmyslovnik.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, PresmyslovnikActivity.class);
                //myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });

        debinarizator.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, DebinarizatorActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        mrizkodrtic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, MrizkoDrticActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        principtrainer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, PrincipTrainerActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }
}
