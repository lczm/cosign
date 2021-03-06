package sg.edu.np.cosign.Classes;

import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class Constants {
    public static final String serverIP       = "http://35.229.247.145";
    public static final String databasePort   = ":80";
    public static final String predictionPort = ":5001";

    public ArrayList<String> signNames = new ArrayList<String>();

    // signMapping
    // "Two" : 3 -> Don't know if this is accurate but this is how it is represented
    // reverseSignMapping
    // 3: "Two" -> Same representation as signMapping but reversed
    public HashMap<String, Integer> signMapping  = new HashMap<String, Integer>();
    public HashMap<Integer, String> reverseSignMapping = new HashMap<Integer, String>();

    // This number indicates how much to jump when going to Word Activity
    public Integer jumpNumber;

    public Constants() {
        String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < alphabets.length(); i++) {
            signNames.add(Character.toString(alphabets.charAt(i)));
        }

        // signNames.add("Zero");
        signNames.add("One");
        signNames.add("Two");
        signNames.add("Three");
        signNames.add("Four");
        signNames.add("Five");
        signNames.add("Six");
        signNames.add("Seven");
        signNames.add("Eight");
        signNames.add("Nine");
        // signNames.add("Ten");
        // signNames.add("Eleven");
        // signNames.add("Twelve");
        // signNames.add("Thirteen");
        // signNames.add("Fourteen");
        // signNames.add("Fifteen");
        // signNames.add("Sixteen");
        // signNames.add("Seventeen");
        // signNames.add("Eighteen");
        // signNames.add("Nineteen");
        // signNames.add("Twenty");
        for (int i = 0; i < signNames.size(); i++) {
            signMapping.put(signNames.get(i), i+1);
            reverseSignMapping.put(i+1, signNames.get(i));
        }
        jumpNumber = alphabets.length() + 1;
    }

    public ArrayList<Integer> getFavourite(String email, String password) {
        ArrayList<Integer> returnFavouriteList = new ArrayList<Integer>();
        try {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("email", email);
                jsonObject.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Constants.serverIP + Constants.databasePort + "/profile")
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            int responseCode = response.code();
            if (responseCode == 200) {
                Log.d("DEBUG", "Successful");
                String responseBody = response.body().string();
                try {
                    JSONObject responseJson = new JSONObject(responseBody);
                    JSONObject bookmarkJson = (JSONObject)responseJson.get("bookmarks");
                    Iterator<String> keys = bookmarkJson.keys();
                    while(keys.hasNext()) {
                        Integer tempInteger = Integer.parseInt(keys.next());
                        returnFavouriteList.add(tempInteger);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return returnFavouriteList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("DEBUG", "Un-Successful");
        return null;
    }

    public boolean postFavouriteToggle(Integer sign_id, String email, String password) {
        try {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("sign_id", sign_id);
                jsonObject.put("email", email);
                jsonObject.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Constants.serverIP + Constants.databasePort + "/bookmark")
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            int responseCode = response.code();
            if (responseCode == 200) {
                Log.d("DEBUG", "Successful");
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Integer> getLearntIds(String email, String password) {
        ArrayList<Integer> returnFavouriteList = new ArrayList<Integer>();
        try {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("email", email);
                jsonObject.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Constants.serverIP + Constants.databasePort + "/profile")
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            int responseCode = response.code();
            if (responseCode == 200) {
                Log.d("DEBUG", "Successful");
                String responseBody = response.body().string();
                try {
                    JSONObject responseJson = new JSONObject(responseBody);
                    JSONObject learns = (JSONObject)responseJson.get("learns");
                    Iterator<String> keys = learns.keys();
                    while(keys.hasNext()) {
                        Integer tempInteger = Integer.parseInt(keys.next());
                        returnFavouriteList.add(tempInteger);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return returnFavouriteList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("DEBUG", "Un-Successful");
        return null;
    }

    public Integer getGoals(String email, String password) {
        ArrayList<Integer> returnGoal = new ArrayList<Integer>();
        try {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("email", email);
                jsonObject.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Constants.serverIP + Constants.databasePort + "/profile")
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            int responseCode = response.code();
            if (responseCode == 200) {
                Log.d("DEBUG", "Successful");
                String responseBody = response.body().string();
                try {
                    JSONObject responseJson = new JSONObject(responseBody);
                    JSONObject goals = (JSONObject)responseJson.get("goals");
                    Iterator<String> keys = goals.keys();
                    while(keys.hasNext()) {
                        JSONObject amountKey = (JSONObject)goals.get(keys.next());
                        returnGoal.add(Integer.parseInt(amountKey.get("amount").toString()));
                    }
                    Log.d("DEBUG", returnGoal.get(returnGoal.size() - 1).toString());
                    return returnGoal.get(returnGoal.size() - 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("DEBUG", "Un-Successful");
        return null;
    }

    public boolean setGoals(String email, String password, Integer amount, String date) {
        ArrayList<Integer> returnGoal = new ArrayList<Integer>();
        try {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("email", email);
                jsonObject.put("password", password);
                jsonObject.put("goal_id", -1);
                jsonObject.put("amount", amount);
                jsonObject.put("date", date);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Constants.serverIP + Constants.databasePort + "/goal")
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            int responseCode = response.code();
            if (responseCode == 200) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("DEBUG", "Un-Successful");
        return false;
    }
}
