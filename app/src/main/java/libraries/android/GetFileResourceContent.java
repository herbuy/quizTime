package libraries.android;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class GetFileResourceContent {

    public static String where(Context context, int resourceId) {

        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    context.getResources().openRawResource(resourceId)
            ));

            String result = "";
            String lineSeparator = "";

            String data = bufferedReader.readLine();
            while(data != null){
                result += lineSeparator + data;
                data = bufferedReader.readLine();
                lineSeparator = "\r\n";
            }
            return result;
        }
        catch (Exception ex){

            return "";
        }
    }
}
