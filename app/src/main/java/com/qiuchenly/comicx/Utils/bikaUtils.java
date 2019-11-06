package com.qiuchenly.comicx.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.Response;

public class bikaUtils {

    public static void handleSSLHandshake(OkHttpClient.Builder builder) {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("TLS");
            // trustAllCerts信任所有的证书
            sc.init(null, trustAllCerts, new SecureRandom());
            builder.sslSocketFactory(sc.getSocketFactory());
        } catch (Exception ignored) {
        }
    }

    private static final String TAG = "bikaUtils";

    public static boolean isEmailValid(String email) {
        return Pattern.compile("^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE).matcher(email).matches();
    }

    public static String getNonce() {
        try {
            String sign = "80458243308201aca00302010202045816e7ec300d06092a864886f70d01010505003066310d300b0603550406130450696361310d300b0603550408130450696361310d300b060355040713045069636131133011060355040a130a5069636120436f6d696331133011060355040b130a5069636120436f6d6963310d300b0603550403130452554646301e170d3136313033313036343235325a170d3431313032353036343235325a3066310d300b0603550406130450696361310d300b0603550408130450696361310d300b060355040713045069636131133011060355040a130a5069636120436f6d696331133011060355040b130a5069636120436f6d6963310d300b060355040313045255464630819f300d06092a864886f70d010101050003818d0030818902818100907a73e634417ec44986ea4a553e5efcba5a65480507ad5a994d621fd1ea1eb5edd87e8fab306a781514d9c740bc7dfef76fb09d82ba5db30377fd77f752a6437a20dbeb423e30716da0cc62244317cbeba9c64bec0584e4813316d69d5438a357ff456920f9c9f558bb7cc4c4a40037b646e098ca96d0b8a7cf19097fbc8f9b0203010001300d06092a864886f70d0101050500038181001338c00449fce7f36c4aa2c398b974ecd9f87b1c957a6e0ea03affd6e8488c7b3cf5943e122f75bc53a69bbdaf3cfc94dd95d3fb46b7c852034dd6da2173da19fca8bf44f0fbed46355a1bc1e310f0533bb049c69a8b6a2c1329b5c657cdeed600ea70625935c21dac8e4a386d79e83106793ac2139b6142570b0cf840c7895c";
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(sign.getBytes());
            String cs = Base64.encodeToString(md.digest(), 0);
            return cs.substring(0, cs.length() - 1);
        } catch (Exception e) {
        }
        return "";
    }

    public static String replaceAllSpace(String inputString) {
        return inputString.replace(" ", "%20");
    }

    public static String encodeToBase64(Bitmap image, CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), 0);
    }

    public static String encodeToBase64(String path) {
        if (path == null || path.equalsIgnoreCase("")) {
            return null;
        }
        File file = new File(path);
        byte[] bytes = new byte[((int) file.length())];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (bytes != null) {
            return Base64.encodeToString(bytes, 0);
        }
        return null;
    }

    public static File decodeBase64ToFile(String base64String, String filePath) {
        byte[] decodedBytes = Base64.decode(base64String, 0);
        File file = new File(filePath);
        try {
            BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
            writer.write(decodedBytes);
            writer.flush();
            writer.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String base64Conversion(Bitmap image) {
        if (image == null) {
            return "";
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);
        String encodedImage = Base64.encodeToString(baos.toByteArray(), 2);
        Log.d(TAG, "BASE64 = " + encodedImage);
        return "data:image/jpeg;base64," + encodedImage;
    }

    public static Bitmap parseUriToBitmap(Context context, Uri imageUri, int targetWidth, int targetHeight) {
        if (imageUri == null) {
            return null;
        }
        try {
            InputStream imageStream = context.getContentResolver().openInputStream(imageUri);
            if (targetWidth <= 0 || targetHeight <= 0) {
                return BitmapFactory.decodeStream(imageStream);
            }
            Bitmap bm = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(imageStream), targetWidth, targetHeight, true);
            Log.d(TAG, "Height = " + bm.getHeight() + " Width = " + bm.getWidth());
            return bm;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap parseUriToBitmap(Context context, Uri imageUri) {
        return parseUriToBitmap(context, imageUri, -1, -1);
    }

    public static Bitmap parseUriToBitmap(Context context, Uri imageUri, int squareWidth) {
        return parseUriToBitmap(context, imageUri, squareWidth, squareWidth);
    }

    public static String saveBitmapImage(Context context, Bitmap bitmap) {
        if (context == null) {
            return null;
        }
        String ImagePath = null;
        try {
            ImagePath = Media.insertImage(context.getContentResolver(), bitmap, "pica_" + System.currentTimeMillis(), "Pica Image");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "ImagePath = " + ImagePath);
        if (ImagePath == null) {
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
            return ImagePath;
        }
        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
        return ImagePath;
    }

    public static int getMaxExpByLevel(int level) {
        return ((((level * 2) - 1) * ((level * 2) - 1)) - 1) * 25;
    }

    public static boolean downloadFile(String url, File outputFile, boolean force) throws Exception {
        Response response = new OkHttpClient().newCall(new Builder().url(url).build()).execute();
        if (response.isSuccessful()) {
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(response.body().bytes());
            fos.close();
            return false;
        }
        throw new IOException("Failed to download file: " + response);
    }

    public static void DeleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                DeleteRecursive(child);
            }
        }
        fileOrDirectory.delete();
    }

    public static int checkUserPermission(Context context, String userEmail) {
        if (userEmail == null || !userEmail.endsWith("@picacomic.com")) {
            return 0;
        }
        if (userEmail.startsWith("ruff")) {
            return 2;
        }
        return 1;
    }
}