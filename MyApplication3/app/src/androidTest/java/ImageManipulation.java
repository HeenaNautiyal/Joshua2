import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



/**
 * Created by Heena on 2/6/2017.
 */
public class ImageManipulation {

    public static void main(String[] args) {

        File file = new File("D:\\heena\\Images folder\\D2DApp\\icon.png");

        try {
            // Reading a Image file from file system
            FileInputStream imageInFile = new FileInputStream(file);
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);

            // Converting Image byte array into Base64 String
            String imageDataString = encodeImage(imageData);

            // Converting a Base64 String into Image byte array
            byte[] imageByteArray = decodeImage(imageDataString);

            // Write a image byte array into file system
            FileOutputStream imageOutFile = new FileOutputStream(
                    "D:\\heena\\Images folder\\D2DApp\\icon.png");

            imageOutFile.write(imageByteArray);

            imageInFile.close();
            imageOutFile.close();

            System.out.println("Image Successfully Manipulated!");
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
    }

   /* private static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64URLSafeString(imageDataString);
    }

    private static String encodeImage(byte[] imageData) {
        return Base64.encodeBase64URLSafeString(imageData);
    }*/

    /**
     * Encodes the byte array into base64 string
     *
     * @param imageByteArray - byte array
     * @return String a {@link java.lang.String}
     */
   /* public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeBase64URLSafeString(imageByteArray);
    }*/

    /**
     * Decodes the base64 string into byte array
     *
     * @param imageDataString - a {@link java.lang.String}
     * @return byte array
     */
   /* public static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }*/

}
