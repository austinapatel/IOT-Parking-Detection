

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.codec.binary.Base64;


public class Decode {

    public static byte[] decodeImage(String imageDataString)
    {
        return Base64.decodeBase64(imageDataString);
    }

    public static String encodeImage(byte[] imageByteArray)
    {
        return Base64.encodeBase64URLSafeString(imageByteArray);
    }

    public static void main(String[] args)
    {
//		  ImageToString();
        System.out.println("test");
    }

    public static void ImageToString()
    {
        File file = new File("whatever.jpg");

        try {
            FileInputStream imageInFile = new FileInputStream(file);
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            String imageDataString = encodeImage(imageData);
        }
        catch(Exception e)
        {

        }
    }

    public static void StringToImage()
    {
        String fileAsString = "";
        try
        {
            InputStream is = new FileInputStream("test.txt"); //test.txt is the file contaiting the image data
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();
            while(line != null)
            {
                sb.append(line).append("\n");
                line = buf.readLine();
            }
            fileAsString = sb.toString();
            System.out.println("Contents : " + fileAsString);
        }
        catch (Exception e)
        {

        }
        try
        {
            byte[] imageByteArray = decodeImage(fileAsString);
            FileOutputStream imageOutFile = new FileOutputStream("newpic.png");//new pic is the data coverted to img
            imageOutFile.write(imageByteArray);
            imageOutFile.close();
        }
        catch(Exception e2)
        {

        }
    }
}