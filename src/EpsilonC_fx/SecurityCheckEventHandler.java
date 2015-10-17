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
public class SecurityCheckEventHandler implements EventHandler<ActionEvent> {

    private LoginPromptController controller;
    private int idx = 0;

    SecurityCheckEventHandler(LoginPromptController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(ActionEvent t) {
        this.controller.initialize(null, null);
    }
}
