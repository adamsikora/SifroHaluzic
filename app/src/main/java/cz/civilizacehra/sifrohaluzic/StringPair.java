package cz.civilizacehra.sifrohaluzic;

/**
 * Created by Adam on 12. 4. 2016.
 */
public class StringPair {
    private String first;
    private String second;

    public StringPair(String first, String second) {
        super();
        this.first = first;
        this.second = second;
    }

    public boolean equals(Object other) {
        if (other instanceof StringPair) {
            StringPair otherPair = (StringPair) other;
            return
                    this.first != null && otherPair.first != null && this.first.equals(otherPair.first) &&
                            this.second != null && otherPair.second != null && this.second.equals(otherPair.second);
        }

        return false;
    }

    public String toString()
    {
        return "(" + first + ", " + second + ")";
    }

    static public StringPair fromString(String input)
    {
        String[] split = input.split(":");
        if (split.length == 2) {
            return new StringPair(split[0], split[1]);
        } else {
            return new StringPair(input, input);
        }
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }
}
