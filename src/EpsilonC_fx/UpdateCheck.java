package EpsilonC_fx;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Audun Moseng
 *
 */
public class UpdateCheck {

    protected String clientVersion;
    protected String serverVersion;
    protected String defaultServerPatchURL;
    private String projectName = "EpsilonC";

    UpdateCheck() {
        this.defaultServerPatchURL = "http://goat-2-goat.com/OnlineUpdater/" + this.projectName + "/Patches/";
        this.clientVersion = "";
        this.serverVersion = "";
    }

    public boolean serverHasNewerVersion() {
        try {
            // open a connection to the site
            URL url = new URL("http://www.goat-2-goat.com/OnlineUpdater/" + this.projectName + "/index.php");
            URLConnection con = url.openConnection();
            // activate the output
            con.setDoOutput(true);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
            String line = null;
            String strUnFormated = null;
            //while theres lines left under my current line continue to nex
            while ((line = in.readLine()) != null) {
                // System.out.println(line);
                strUnFormated += line;
            }
            if (checkVersions(formatHTML(strUnFormated))) {
                return true;
            } else {
                return false;
            }

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return false;
    }

    private String formatHTML(String str) {
        String[] strSplit = str.split("%");
        //ServerVersion
        System.out.println("Server version:" + strSplit[1]);
        //checkVersions(strSplit[1]);
        this.serverVersion = strSplit[1];
        return strSplit[1];
    }

    private boolean checkVersions(String srvVer) {
        FileHandler fh = new FileHandler();
        File clientVersionFile = new File(fh.getDefaultFilePath() + fh.sep + "Patch" + fh.sep + "clientVersion.txt");
        if (clientVersionFile.exists()) {
                this.clientVersion = fh.readFile(clientVersionFile);
            if (!this.clientVersion.equals(srvVer)) {
                System.out.println("Client Version: " + this.clientVersion);
                System.out.println("Version missmatch, attempting to update...");
                return true;
            } else {
                System.out.println("Up to date");
                return false;
            }
        } else {
            File folder = new File(fh.getDefaultFilePath() + fh.sep + "Patch");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            fh.writeFileContent(fh.getDefaultFilePath() + fh.sep + "Patch" + fh.sep + "clientVersion.txt", "0");
            System.out.println("atemting again");
            if(checkVersions(srvVer)){
                return true;
            }
            return false;
        }
    }

    public void getUpdate() {
        try {
            Process proc = Runtime.getRuntime().exec("java -jar " + "EpsilonU.jar");
           
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Could not find updater, please reinstall the program to fix this.", "Warning",
                    JOptionPane.PLAIN_MESSAGE);
        }
         System.exit(0);
    }
}