package cz.civilizacehra.sifrohaluzic;

import android.content.res.AssetManager;
import android.location.Location;
import android.widget.TextView;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Adam on 14. 4. 2016.
 */
public class MapDictionary extends Dictionary {
    public MapDictionary(AssetManager manager, String filename, TextView results) {
        super(manager, filename, results);
    }

    public void setLocation(Location location) {
        mLocation = location;
    }
    private Location mLocation;
    private Map<Float, String> mSortedResults = new TreeMap<Float, String>();

    @Override
    protected void prepare() {
        mSortedResults.clear();
    }

    @Override
    protected void matched(String match) {

        String[] split = match.split(";");
        Double lat = .0;
        Double lon = .0;
        String name = split[0];
        if (split.length >= 3) {
            lat = Double.parseDouble(split[split.length - 2]);
            lon = Double.parseDouble(split[split.length - 1]);
        }
        float distance = .0f;
        if (mLocation != null) {
            Location targetLocation = new Location("");
            targetLocation.setLatitude(lat);
            targetLocation.setLongitude(lon);
            distance = mLocation.distanceTo(targetLocation);
        }
        mSortedResults.put(distance, name);
    }

    @Override
    protected void conclude() {
        String resultStr = "";
        int counter = 0;
        for (Map.Entry<Float, String> point : mSortedResults.entrySet()) {
            ++counter;
            resultStr += point.getValue() + " (" + point.getKey().intValue() + "m)\n";
            if (counter >= 3000) {
                break;
            }
        }
        mResults.setText("Result: (" + counter + ")\n" + resultStr);
    }
}
