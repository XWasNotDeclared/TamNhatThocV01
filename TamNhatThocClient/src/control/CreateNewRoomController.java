package control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Message;
import model.RoomClient;

public class CreateNewRoomController {
	private Communication commu;

    public Communication getCommu() {
		return commu;
	}


	public void setCommu(Communication commu) {
		this.commu = commu;
	}

	
	
	

    @FXML
    private Button createNewRoom;

    @FXML
    private TextField numPeopleText;

    @FXML
    private TextField numSeedText;

    @FXML
    private TextField numTypeText;

    @FXML
    void creatRoomClick(ActionEvent event) {
    	
    	int numPeople = Integer.valueOf(numPeopleText.getText());
    	int numSeed = Integer.valueOf(numSeedText.getText());
    	int numTypeSeed = Integer.valueOf(numTypeText.getText());
    	int time_match = 30;
    	String date_play = "temp_fake_day";
    	
    	RoomClient newRoom = new RoomClient("name_room_here",numPeople,numSeed,numTypeSeed,time_match,date_play);
    	
    	Message msg = new Message("CREATE_ROOM",newRoom);
    	try {
			commu.sendMessage(msg);
			System.out.println("Đã gui thong tin tao phong!!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @FXML
    void goBackButtonCick(ActionEvent event) {
    	commu.getNavigation().switchTo("Home.fxml");
    	
    	
    	
    }

}