package control;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import model.Message;
import model.RoomClient;
import utils.Util;

public class FindRoomController {
	private Communication commu;
    public void setCommu(Communication commu) {
		this.commu = commu;
	}

	@FXML
    private ListView<RoomClient> RoomsListView;

    @FXML
    private Button goBackButton;
    
    private ObservableList<RoomClient> roomList;
    
    
    
    

    public FindRoomController() {
    	roomList = FXCollections.observableArrayList();
	}
    
    @FXML
    void initialize() {
    	RoomsListView.setItems(roomList);
//    	///fake data test//
//    	roomList.add("Fake room data 1");
//    	roomList.add("Fake room data 2");
//    	roomList.add("Fake room data 3");
    }
    public void setRoomList(List<RoomClient> roomList) {
    	this.roomList.setAll(roomList);
    }


	@FXML
    void goBacKButtonClick(ActionEvent event) {
		commu.getNavigation().switchTo("Home.fxml");
    }
	
    @FXML
    void onMouseClickedListRoomView(MouseEvent event) {
    	RoomClient selectedRoom = RoomsListView.getSelectionModel().getSelectedItem();
    	if (selectedRoom != null) {
    		System.out.println(selectedRoom);
    		if (selectedRoom.getNumPeople()-selectedRoom.getUsers().size()>0) {
            	try {
        			commu.sendMessage(new Message("JOIN_ROOM",selectedRoom));
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
    		} else {
    			Util.showError("Phong da day");
    		}
    	}

    }

}
