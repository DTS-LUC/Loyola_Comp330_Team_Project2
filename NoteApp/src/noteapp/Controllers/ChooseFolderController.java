/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noteapp.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author don
 */
public class ChooseFolderController implements Initializable {

    @FXML
    void getDirectory(ActionEvent e) {
      // 1. Get filname from text box
      // 2. Send to Model/NoteRetriever
      //    - This is where filesystem is called and Notes will be loaded
      // 3. If error - Ask again
      // 4. Else change scenes
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    

}
