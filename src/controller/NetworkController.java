package controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import org.json.JSONArray;

public class NetworkController {
    private static final String PLAYERS_URL = "https://froggerfx-api.herokuapp.com/api/users";  //"http://localhost:8000/api/users";
    private static final String LOGIN_URL = "https://froggerfx-api.herokuapp.com/api/auth/login";  //"http://localhost:8000/api/auth/login";
    private static final String REGISTER_URL = "https://froggerfx-api.herokuapp.com/api/auth/register";  //"http://localhost:8000/api/auth/register";
    private static final String TEST_URL = "https://froggerfx-api.herokuapp.com/";  //"http://localhost:8000/";
    private static final String UPDATE_URL = "https://froggerfx-api.herokuapp.com/api/user/update";  //"http://localhost:8000/";
    private static String TOKEN = "";
    public JSONObject errorMessage;

    public JSONObject login(String username, String password) throws IOException {
        URL loginURL = new URL(LOGIN_URL);
        String body = "{\"username\": \""+username+"\",\"password\": \""+password+"\"}";
        try {
            HttpURLConnection con = (HttpURLConnection) loginURL.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type",
                    "application/json");
            con.setRequestProperty("Content-Length", String.valueOf(body.length()));
            con.setRequestProperty("Accept", "*/*");
            con.setRequestProperty("Connection", "keep-alive");

            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            if(con.getResponseCode() < 400) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                JSONObject res = new JSONObject(in.readLine());
                TOKEN = (String) res.get("token");
                //System.out.println(TOKEN);
                in.close();
                return res;
            } else {

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                errorMessage = new JSONObject(in.readLine());
                in.close();
            }

        } catch ( Exception ex ){
            System.out.println(ex);
        }
        return errorMessage;
    }

    public JSONObject register(String username, String password) throws IOException {
        URL loginURL = new URL(REGISTER_URL);

        String body = "{\"username\": \""+username+"\", \"password\": \""+password+"\"}";
        try {
            HttpURLConnection con = (HttpURLConnection) loginURL.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type",
                    "application/json");
            con.setRequestProperty("Content-Length", String.valueOf(body.length()));
            con.setRequestProperty("Accept", "*/*");
            con.setRequestProperty("Connection", "keep-alive");

            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();

            if(con.getResponseCode() < 400) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                JSONObject res = new JSONObject(in.readLine());
                TOKEN = (String) res.get("token");
                in.close();
                //System.out.println(res);
                return res;
            } else {

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                errorMessage = new JSONObject(in.readLine());
                in.close();
            }

        } catch ( Exception ex ){
            System.out.println(ex);
        }
        return errorMessage;
    }

    public JSONArray fetchAllPlayers() throws IOException {
        URL playersURL = new URL(PLAYERS_URL);
        try {
            HttpURLConnection con = (HttpURLConnection) playersURL.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty( "Authorization", "Token " + TOKEN);
            con.setRequestProperty("Accept", "*/*");
            con.setRequestProperty("Connection", "keep-alive");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            JSONArray players = new JSONArray(in.readLine());
            //System.out.println(players.toString());
            in.close();
            return players;

        } catch (IOException ex){
            System.out.println(ex);
        }
        return null;
    }

    public void updateHighscore(int highscore) throws IOException {
        URL loginURL = new URL(UPDATE_URL);

        String body = "{\"highscore\": \""+highscore+"\"}";
        try {
            HttpURLConnection con = (HttpURLConnection) loginURL.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty( "Authorization", "Token " + TOKEN);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type",
                    "application/json");
            con.setRequestProperty("Content-Length", String.valueOf(body.length()));
            con.setRequestProperty("Accept", "*/*");
            con.setRequestProperty("Connection", "keep-alive");

            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();

            if(con.getResponseCode() < 400) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                JSONObject res = new JSONObject(in.readLine());
                in.close();
                //System.out.println(res);
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                errorMessage = new JSONObject(in.readLine());
                in.close();
            }

        } catch ( Exception ex ){
            System.out.println(ex);
        }
    }

    public boolean testConnection() throws IOException {
        URL loginURL = new URL(TEST_URL);
        try {
            HttpURLConnection con = (HttpURLConnection) loginURL.openConnection();
            if(con.getResponseCode() == 200) {
                return true;
            }
        } catch ( Exception ex ){
            System.out.println(ex);
        }
        return false;
    }
}
