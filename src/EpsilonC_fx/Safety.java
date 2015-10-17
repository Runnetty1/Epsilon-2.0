/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EpsilonC_fx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;

/**
 *
 * @author Runnetty
 */
public class Safety {
    public void generatekey() {
        FileHandler fh = new FileHandler();
        if (!new File(fh.getDefaultFilePath() + fh.sep + "NodeInfo" + fh.sep + "key.bin").exists()) {
            try {
                System.out.println("\nStart generating DES key");
                
                KeyGenerator keyGen = KeyGenerator.getInstance("DES");
                keyGen.init(56);
                Key key = keyGen.generateKey();
                System.out.println("Finish generating DES key");
                byte[] keyAsByte = key.getEncoded();
                FileOutputStream keyfos = new FileOutputStream(fh.getDefaultFilePath() + fh.sep + "NodeInfo" + fh.sep + "key.bin");
                keyfos.write(keyAsByte);
                keyfos.close();
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(EpsilonC.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(EpsilonC.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(EpsilonC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
