package helper;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Ominext on 8/7/2017.
 */

public class FileHelper {
    /* final static String fileName = "data.txt";
     final static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/instinctcoder/readwrite/" ;*/
    final static String TAG = FileHelper.class.getName();

    public static String ReadFile(Context context, String path, String fileName) {
        String line = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path + fileName));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + System.getProperty("line.separator"));
            }
            fileInputStream.close();
            line = stringBuilder.toString();

            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        } catch (IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return line;
    }

    public static boolean saveToFile(String path, String fileName, String data) {
        try {
            File file2 = new File(path);
            if (!file2.exists()) {
                file2.mkdirs();
            }
            /*if (new File(path).mkdir()) {*/
            File file = new File(path + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write((data + System.getProperty("line.separator")).getBytes());

            return true;
        } catch (FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        } catch (IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return false;
    }

    public static boolean deleteAnEmployee(String path, String fileNameInPut, String fileNameOutPut, String lineToRemove) {
        //đọc file ra 1 chuỗi json rồi xóa
        File input = new File(path + fileNameInPut);
        File output = new File(path + fileNameOutPut);
        FileInputStream inputStream;
        FileOutputStream outputStream;
        String currentLine;
        boolean successful = false;
        try {
            //file Input, tên file truyền vào phải là nv1.txt
            inputStream = new FileInputStream(input);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            //file Output
            outputStream = new FileOutputStream(output);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);
            while ((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();
                if (trimmedLine.equals(lineToRemove)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            successful = output.renameTo(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return successful;
    }

    //sửa file text theo id
    public static boolean editFile(String path, String fileName, String data) {
        String line = null;
        String verify, putData;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(path + fileName));
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                verify = reader.readLine();
                if (verify != null) {
                    putData = verify.replace("here", "there");
//                    .write(putData);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
