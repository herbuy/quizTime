package libraries.android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetBitmapFromUrl {
    public static Bitmap where(String urlString) {
        if(urlString == null || urlString.trim().equalsIgnoreCase("")){

            return null;
        }
        urlString = urlString.trim();

        try{
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
        catch (Exception ex){

            return null;
        }

    }

    private static String getClassName() {
        return GetBitmapFromUrl.class.getSimpleName();
    }
}
