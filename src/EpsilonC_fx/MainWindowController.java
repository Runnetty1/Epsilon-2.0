/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EpsilonC_fx;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.HTMLEditor;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Runnetty
 */
public class MainWindowController implements Initializable {

    @FXML
    private TreeView<String> locationTreeView;
    
    @FXML
    private HTMLEditor htmleditor;
    
    private TreeItem<String> root;

    private String currentFile = "Sogndal.Kommune", lastSaved = "";
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTreeItems();
        
    }
    
    public void close() {
        System.exit(0);
    }

    public void loadTreeItems() {
        htmleditor = new HTMLEditor();
        XMLReader reader = new XMLReader();
        System.out.println("Loading treeitems");
        try {
            root = nodesfromXMLDoc(reader.getXMLDoc().getDocumentElement());
            root.setExpanded(true);
           
            locationTreeView.setRoot(root);
            locationTreeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            locationTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                    TreeItem treeItem = (TreeItem) newValue;
                    System.out.println("Selected item is " + treeItem.getValue());
                    //Open the file and show in text area or save
                    //check(treeItem);   
                    
                    if (!lastSaved.equalsIgnoreCase(htmleditor.getHtmlText())) {
                        System.out.println("TEST");
                        int n = JOptionPane.showConfirmDialog(null, "Do you want to save "
                                + "before you open a new file?", "Save Notification", JOptionPane.YES_NO_OPTION);
                        if (n == JOptionPane.YES_OPTION) {
                            save(currentFile, htmleditor.getHtmlText());
                            //Overwrite current open file
                            currentFile = treeItem.getValue().toString();
                        } else if (n == JOptionPane.NO_OPTION) {
                        }

                        load(treeItem.getValue().toString());

                    } else {
                        load(treeItem.getValue().toString());
                    }
                }
            });
        } catch (Exception exc) {
            System.out.println("Error: " + exc.getMessage());
        }
    }

    public TreeItem nodesfromXMLDoc(Node node) {

        //System.out.println(node.getNodeName());
        TreeItem<String> a = new TreeItem<String>(node.getNodeName());
        NodeList nodeList = node.getChildNodes();

        if (1 > nodeList.getLength()) {
            //System.out.println("This is a end branch so add the node to root");
            a.setValue(a.getValue());
        }

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                //calls this method for all the children which is Element
                a.getChildren().add(nodesfromXMLDoc(currentNode));
            }
        }
        return a;
    }

    public boolean save(String fileName, String content) {

        FileHandler fh = new FileHandler();
        fh.saveFile(fh.getDefaultFilePath() + fh.sep + "NodeInfo" + fh.sep
                + fileName + ".bin", enc(content.getBytes()));
        return true;

    }

    public void load(String fileName) {
        FileHandler fh = new FileHandler();

 
        File f = new File(fh.getDefaultFilePath() + fh.sep + "NodeInfo" + fh.sep + fileName + ".bin");
        byte[] t = null;
        if (f.exists()) {
            t = dec(f);
            htmleditor.setHtmlText(new String(t));  
        }else{
            htmleditor.setHtmlText("");
        }

        lastSaved = htmleditor.getHtmlText();
        currentFile = fileName;

    }

    private byte[] dec(File f) {
        FileHandler fh = new FileHandler();
        try {
            Cipher decrypter = Cipher.getInstance("DES/ECB/PKCS5Padding");
            decrypter.init(Cipher.DECRYPT_MODE, fh.readKey());
            byte[] decryptedText = decrypter.doFinal(fh.getFileContent(f.getCanonicalPath()));
            return decryptedText;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private byte[] enc(byte[] content) {
        try {
            FileHandler fh = new FileHandler();
            // get a DES cipher object and print the provider
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            //System.out.println("\n" + cipher.getProvider().getInfo());
            System.out.println("\nStart encryption");
            cipher.init(Cipher.ENCRYPT_MODE, fh.readKey());
            byte[] cipherText = cipher.doFinal(content);
            System.out.println("Finish encryption: ");
            //System.out.println(new String(cipherText, "UTF8"));
            return cipherText;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
