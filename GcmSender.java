/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gcm.play.android.samples.com.gcmsender;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GcmSender {

    // NotfyMe AIzaSyBY_9JtKpmy20hIzX5UNW_kTJV1yLUeAhU
    //GCM-Start AIzaSyDnT2B-J5ih3qkdZwjKlNaJSq2D3JmaMGc
    public static final String API_KEY = "AIzaSyBY_9JtKpmy20hIzX5UNW_kTJV1yLUeAhU";

    /*public static final String TOKEN = "dWrm-GpqlgA:APA91bEYCTAhJJGU0Thr8QKkw" +
            "tnTQG-88_0fbkukbVL7HmN9lU3I7KB8K5s7WVXvVvIKzJ6RHCV-dpr" +
            "qvDe8REaYaXD9-bW7Hx7-G5t7rZdxSsbfaB40VWsZ5enUE6NpqFKOaO4wRGAn";*/
    public static final String TOKEN = "cXizuAkKUaQ:APA91bH2hs1y8Q6uQbH" +
            "-2Dbd6B4cAWvchTdGTbLUnMvLrxhD2WAbjR7QYKjjqOOWrKeeJJOl2TlpPsWipFcJBY49Jtnelg" +
            "b771kpLrJB784EgQpgWk46-TXZLEkGiw8AZ2r4IzqIYfPN";
    public static final String MESSAGE = "Gcm Message !!!!";
    public static void main(String[] args) {
        if (args.length < 1 || args.length > 2 || args[0] == null) {
            System.err.println("usage: ./gradlew run -Pargs=\"MESSAGE[,"+TOKEN+"]");
            System.err.println("");
            System.err.println("Specify a test message to broadcast via GCM. If a device's GCM registration token is\n" +
                    "specified, the message will only be sent to that device. Otherwise, the message \n" +
                    "will be sent to all devices subscribed to the \"global\" topic.");
            System.err.println("");
            System.err.println("Example (Broadcast):\n" +
                    "On Windows:   .\\gradlew.bat run -Pargs=\"<Your_Message>\"\n" +
                    "On Linux/Mac: ./gradlew run -Pargs=\"<Your_Message>\"");
            System.err.println("");
            System.err.println("Example (Unicast):\n" +
                    "On Windows:   .\\gradlew.bat run -Pargs=\"<Your_Message>,<Your_Token>\"\n" +
                    "On Linux/Mac: ./gradlew run -Pargs=\"<Your_Message>,<Your_Token>\"");
           // System.exit(1);
        }
        try {
            // Prepare JSON containing the GCM message content. What to send and where to send.
            JSONObject jGcmData = new JSONObject();
            JSONObject jData = new JSONObject();
            jData.put("message","Fuck u GCM !!!");
            // Where to send GCM message.cZdMVk5mYWY:APA91bEOfPsCxmGyyyW7xiyv97NDc0Mcz7KRt8IBecYM2l8lQBsXU6ZfpkEp2kz_sGbm76h-g97GaKNog9AyZNR09suf4HFFKesxZgrOgfMikVsbXsTBjYjupL5KLdHiT5MG7O6_voNe
           /* if (args.length > 1 && args[1] != null) {
                jGcmData.put("to", args[1].trim());
            } else {*/
            jGcmData.put("to", "/topics/global");
            jGcmData.put("to", TOKEN);
           // }
            // What to send in GCM message.
            jGcmData.put("data", jData);

            // Create connection to send GCM Message request.
            URL url = new URL("https://android.googleapis.com/gcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "key=" + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Send GCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jGcmData.toString().getBytes());

            // Read GCM response.
            InputStream inputStream = conn.getInputStream();
            String resp = IOUtils.toString(inputStream);
            System.out.println(resp);
            System.out.println("Check your device/emulator for notification or logcat for " +
                    "confirmation of the receipt of the GCM message.");
        } catch (IOException e) {
            System.out.println("Unable to send GCM message.");
            System.out.println("Please ensure that API_KEY has been replaced by the server " +
                    "API key, and that the device's registration token is correct (if specified).");
            e.printStackTrace();
        }
    }

}
