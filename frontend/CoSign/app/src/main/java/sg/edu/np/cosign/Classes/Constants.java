package sg.edu.np.cosign.Classes;

import java.util.ArrayList;
import java.util.HashMap;

public final class Constants {
    public static final String serverIP       = "http://35.229.247.145";
    public static final String databasePort   = ":80";
    public static final String predictionPort = ":5001";

    public ArrayList<String> signNames = new ArrayList<String>();
    public HashMap<String, Integer> signMapping  = new HashMap<String, Integer>();

    // This number indicates how much to jump when going to Word Activity
    public Integer jumpNumber;

    public Constants() {
        String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < alphabets.length(); i++) {
            signNames.add(Character.toString(alphabets.charAt(i)));
        }

        signNames.add("Zero");
        signNames.add("One");
        signNames.add("Two");
        signNames.add("Three");
        signNames.add("Four");
        signNames.add("Five");
        signNames.add("Six");
        signNames.add("Seven");
        signNames.add("Eight");
        signNames.add("Nine");
        signNames.add("Ten");
        signNames.add("Eleven");
        signNames.add("Twelve");
        signNames.add("Thirteen");
        signNames.add("Fourteen");
        signNames.add("Fifteen");
        signNames.add("Sixteen");
        signNames.add("Seventeen");
        signNames.add("Eighteen");
        signNames.add("Nineteen");
        signNames.add("Twenty");
        for (int i = 0; i < signNames.size(); i++) {
            signMapping.put(signNames.get(i), i+1);
        }
        jumpNumber = alphabets.length() + 1;
    }
}
