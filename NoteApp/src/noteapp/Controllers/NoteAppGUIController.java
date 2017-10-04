/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author don
 */
package noteapp.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class NoteAppGUIController {

    @FXML
    private void removeNote(ActionEvent e){
        System.out.println("Remove button");
        // TODO 
    }
    
    @FXML
    private void newNote(ActionEvent e){
        System.out.println("New button");
    }
    
    @FXML
    private void updateNote(ActionEvent e){
        System.out.println("Update button");
    }
    
    @FXML
    private JFXTextField searchField;
    @FXML
    private JFXComboBox<?> sortBox;
    @FXML
    private JFXTextField noteTitle;
    @FXML
    private JFXToggleButton favToggle;
    @FXML
    private JFXButton updateBtn;
    @FXML
    private JFXButton newBtn;
    @FXML
    private JFXButton removeBtn;
    @FXML
    private JFXListView<?> noteListViem;
    @FXML
    private JFXTextArea notePad;

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
}
