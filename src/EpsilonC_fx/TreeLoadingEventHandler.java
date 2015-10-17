/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EpsilonC_fx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;


/**
 * small helper class for handling tree loading events.
 */
public class TreeLoadingEventHandler implements EventHandler<ActionEvent> {

    private MainWindowController controller;
    private int idx = 0;

    TreeLoadingEventHandler(MainWindowController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(ActionEvent t) {
        this.controller.loadTreeItems();
    }
}
