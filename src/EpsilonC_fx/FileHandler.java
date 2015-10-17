/*
 ***************************************************************************
 * AUTHOR: AUDUN MOSENG & MATS HARWISS LAST EDITED: 15.12.2014 
 * LAST EDITED BY: MATS HARWISS
 *
 * CLASS PURPOSE: READ AND GET XML NODES FROM EXTERNAL FILE
 ***************************************************************************
 */
package EpsilonC_fx;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.security.Key;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.spec.SecretKeySpec;

public class FileHandler {

    public String sep = System.getProperty("file.separator");

    public String getDefaultFilePath() {
        String filePath = null;
        filePath = System.getProperty("user.home") + sep + "Documents" + sep + "EpsilonC";
        return filePath;
    }

    public String getWorkingPath() {
        String filePath = null;
        try {
            filePath = new File(EpsilonC.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).toString();
            filePath = filePath.substring(0, filePath.length() - 13);
        } catch (URISyntaxException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filePath;
    }

    protected void generateFolder(String newFolderLocation) {
        //create output directory is not exists

        File folder = new File(newFolderLocation);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public Key readKey() {

        FileInputStream keyFis = null;
        try {
            keyFis = new FileInputStream(getDefaultFilePath() + sep + "NodeInfo" + sep + "key.bin");
            byte[] encKey = new byte[keyFis.available()];
            keyFis.read(encKey);
            keyFis.close();
            Key keyFromFile = new SecretKeySpec(encKey, "DES");
            return keyFromFile;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                keyFis.close();
            } catch (IOException ex) {
                Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void saveFile(String fileName, byte[] content) {
        try {
            FileOutputStream fs = new FileOutputStream(fileName);
            fs.write(content);
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] getFileContent(String fileName) {
        FileInputStream encryptedTextFis = null;
        System.out.println("Reading: " + fileName);
        try {
            encryptedTextFis = new FileInputStream(fileName);
            byte[] encText = new byte[encryptedTextFis.available()];
            encryptedTextFis.read(encText);
            encryptedTextFis.close();
            return encText;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                encryptedTextFis.close();
            } catch (IOException ex) {
                Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public String readFile(File f) {
        String text = "";
        int read, N = 1024 * 1024;
        char[] buffer = new char[N];
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            while (true) {
                read = br.read(buffer, 0, N);
                text += new String(buffer, 0, read);
                if (read < N) {
                    break;
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return text;
    }
    public boolean writeFileContent(String fileName, String content) {
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
            writer.write(content);
            writer.close();
            return true;
        } catch (IOException e) {
            System.err.println(e);
        }
        return false;
    }
}
