package cz.civilizacehra.sifrohaluzic;

import android.content.res.AssetManager;
import android.widget.TextView;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Adam on 11. 4. 2016.
 */
public class Dictionary {
    public Dictionary(AssetManager manager, String filename, TextView results) {
        m_manager = manager;
        m_filename = filename;
        m_results = results;
    }
    public void findResults(String input, boolean subset, boolean exact, boolean superset, boolean regexp, int minLength, int maxLength) {

        prepare();

        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(input);

        int[] charCount = new int[26];
        for (int i = 0; i < input.length(); ++i) {
            int position = input.charAt(i) - 'a';
            if (position >= 0 && position < 26) {
                ++charCount[position];
            } else {
                assert(false);
            }
        }


        try {
            InputStream inputStream = m_manager.open(m_filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;

            while((line = in.readLine()) != null) {
                StringPair word = StringPair.fromString(line);
                String first = word.getFirst();

                if (subset && first.length() > input.length()) {
                    continue;
                }
                if (superset && first.length() < input.length()) {
                    continue;
                }
                if (exact && first.length() != input.length()) {
                    continue;
                } else if (first.length() < minLength || first.length() > maxLength) {
                    continue;
                }
                if (regexp) {
                    matcher = pattern.matcher(first);
                    if (matcher.matches()) {
                        matched(word.getSecond());
                    }
                } else {
                    int[] chars = new int[26];
                    for (int i = 0; i < first.length(); ++i) {
                        int position = first.charAt(i) - 'a';
                        if (position >= 0 && position < 26) {
                            ++chars[position];
                        } else {
                            assert (false);
                        }
                    }
                    for (int i = 0; i < 26; ++i) {
                        if (subset && charCount[i] < chars[i]) {
                            break;
                        }
                        if (exact && charCount[i] != chars[i]) {
                            break;
                        }
                        if (superset && charCount[i] > chars[i]) {
                            break;
                        }
                        if (i == 26 - 1) {
                            matched(word.getSecond());
                        }
                    }
                }
            }
            conclude();
        } catch (java.io.IOException e) {

        }
    }
    protected void prepare() {};
    protected void matched(String match) {
        m_results.setText(m_results.getText() + match + "\n");
    }
    protected void conclude() {};

    protected AssetManager m_manager;
    protected String m_filename;
    protected TextView m_results;
}
