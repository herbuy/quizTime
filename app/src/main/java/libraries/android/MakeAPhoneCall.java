package libraries.android;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class MakeAPhoneCall {

    public static void where(Context context, String telephoneNumber) {
        try{
            context.startActivity(dialPhoneNumberIntent(context,telephoneNumber));
        }
        catch (Exception ex){

        }
    }


    private static Intent dialPhoneNumberIntent(Context context, String telephoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(uriToCall(telephoneNumber));
        return intent;
    }

    private static Uri uriToCall(String telephoneNumber) {
        return Uri.parse("tel:"+cleanTelephone(telephoneNumber));
    }

    private static String cleanTelephone(String telephoneNumber) {
        return telephoneNumber.replace("-","");
    }
}
