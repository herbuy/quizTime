package libraries.android;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class GetAppSignatures {

    public static ArrayList<String> where(Context context) {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        ArrayList<String> hashedSignatures = new ArrayList<>();

        try {
            populateHashedSignatures(contextWrapper, hashedSignatures);
        } catch (Exception e) {
            Log.e(tag(), "Package not found", e);
        }
        return hashedSignatures;
    }

    private static void populateHashedSignatures(ContextWrapper contextWrapper, ArrayList<String> appSignaturesHashs) throws PackageManager.NameNotFoundException {
        for (
                Signature signature : getUnhashedSignatures(
                contextWrapper.getPackageName(),
                contextWrapper.getPackageManager()
        )) {
            String signatureHash = getSignatureHash(
                    contextWrapper.getPackageName(),
                    signature.toCharsString()
            );
            if (signatureHash != null) {
                appSignaturesHashs.add(String.format("%s", signatureHash));
            }
        }
    }

    private static Signature[] getUnhashedSignatures(String packageName, PackageManager packageManager) throws PackageManager.NameNotFoundException {
        return packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNATURES
        ).signatures;
    }


    @TargetApi(19)
    private static String getSignatureHash(String packageName, String signature) {

        try {
            return doGetSignatureHash(
                    appInfo(packageName, signature),
                    hashType(),
                    numHashedBytes(),
                    numBase64Char()
            );

        } catch (NoSuchAlgorithmException e) {
            Log.e(tag(), "No Such Algorithm Exception", e);
        }
        return null;
    }

    private static String appInfo(String packageName, String signature) {
        return packageName + " " + signature;
    }

    private static int numBase64Char() {
        return 11;
    }

    private static int numHashedBytes() {
        return 9;
    }

    private static String hashType() {
        return "SHA-256";
    }

    private static String doGetSignatureHash(String appInfo, String HASH_TYPE, int NUM_HASHED_BYTES, int NUM_BASE64_CHAR) throws NoSuchAlgorithmException {
        return base64Hash(
                hashAsBytes(appInfo, HASH_TYPE, NUM_HASHED_BYTES),
                NUM_BASE64_CHAR
        );
    }

    private static byte[] hashAsBytes(String appInfo, String HASH_TYPE, int NUM_HASHED_BYTES) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(HASH_TYPE);
        messageDigest.update(appInfo.getBytes(StandardCharsets.UTF_8));
        byte[] hashSignature = messageDigest.digest();

        // truncated into NUM_HASHED_BYTES
        hashSignature = Arrays.copyOfRange(hashSignature, 0, NUM_HASHED_BYTES);
        return hashSignature;
    }

    private static String base64Hash(byte[] hashSignature, int NUM_BASE64_CHAR) {
        String base64Hash =
                Base64.encodeToString(hashSignature, Base64.NO_PADDING | Base64.NO_WRAP);

        base64Hash = base64Hash.substring(0, NUM_BASE64_CHAR);
        return base64Hash;
    }

    private static String tag() {
        return GetAppSignatures.class.getSimpleName();
    }
}
