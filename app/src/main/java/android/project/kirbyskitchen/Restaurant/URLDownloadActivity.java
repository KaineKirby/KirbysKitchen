package android.project.kirbyskitchen.Restaurant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class URLDownloadActivity {
    public String readURL(String currURL) throws IOException {
        String currData = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(currURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            iStream = urlConnection.getInputStream();
            BufferedReader buffRead = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer strBuff = new StringBuffer();

            String ln = "";
            while((ln = buffRead.readLine()) != null) {
                strBuff.append(ln);
            }

            currData = strBuff.toString();
            buffRead.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            iStream.close();
            urlConnection.disconnect();
        }
    return currData;
    }
}
