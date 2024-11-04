package control;



import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import model.*;

public class HomeController {
	private Communication commu;
    public Communication getCommu() {
		return commu;
	}
	public void setCommu(Communication commu) {
		this.commu = commu;
	}
	@FXML
    private ComboBox<?> comboBox;

    @FXML
    private ListView<User>  listViewFriend;

    @FXML
    private ListView<User> listViewTopPlayer;

    @FXML
    private Button newRoom;

    @FXML
    private Text scoreText;

    @FXML
    private Text userNameText;
    @FXML
    private Button logOutButton;
    private ObservableList<User> topPlayerList;
    private ObservableList<User> friendList;

    @FXML
    void newRoomClick(ActionEvent event) {
    	CreateNewRoomController createNewRoomController = commu.getNavigation().switchTo("CreateNewRoom.fxml");
    	createNewRoomController.setCommu(commu);
    }
    @FXML
    void logOutButtonClick(ActionEvent event) {
    	commu.getNavigation().switchTo("Login.fxml");
    	try {
			commu.sendMessage(new Message("LOG_OUT",commu.getCurrentUser()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }   
    @FXML
    void findRoomButtonClick(ActionEvent event) {	
    	try {
			commu.sendMessage(new Message("GET_ROOM_LIST", null));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	

    }
    
    
    @FXML
    void reLoadTopPlayer(ActionEvent event) {
    	sendReLoadTopPlayerRequest();
    }
    
    public void sendReLoadTopPlayerRequest() {
    	try {
			commu.sendMessage(new Message("GET_TOP_PLAYER_LIST", null));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void reLoadFriend(ActionEvent event) {
    	sendReLoadFriendRequest();
    }
    
    public void sendReLoadFriendRequest() {
    	try {
			commu.sendMessage(new Message("GET_FRIEND_LIST", null));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    
    public HomeController() {
		super();
		topPlayerList = FXCollections.observableArrayList();
		friendList = FXCollections.observableArrayList();
	}

	@FXML
    void initialize() {
    	listViewTopPlayer.setItems(topPlayerList);
    	 listViewTopPlayer.setCellFactory(lv -> new ListCell<User>() {
    	        @Override
    	        protected void updateItem(User user, boolean empty) {
    	            super.updateItem(user, empty);
    	            if (empty || user == null) {
    	                setText(null);
    	            } else {
    	                // Hiển thị tên và điểm số của người chơi
    	                setText(user.getUsername() + "   ||   " + user.getScore());
    	            }
    	        }
    	    });
     	listViewFriend.setItems(friendList);
     	listViewFriend.setCellFactory(lv -> new ListCell<User>() {
   	        @Override
   	        protected void updateItem(User user, boolean empty) {
   	            super.updateItem(user, empty);
   	            if (empty || user == null) {
   	                setText(null);
   	            } else {
   	                // Hiển thị tên và điểm số của người chơi
   	                setText(user.getUsername());
   	            }
   	        }
   	    });
//    	///fake data test//
//    	roomList.add("Fake room data 1");
//    	roomList.add("Fake room data 2");
//    	roomList.add("Fake room data 3");
    }
    public void setlistViewTopPlayer(List<User> topPlayerList) {
    	this.topPlayerList.setAll(topPlayerList);
    }
    public void setlistViewFriend(List<User> friendList) {
    	this.friendList.setAll(friendList);
    }
	
	public void updateUserInfor (String userName, float score) {
		userNameText.setText(userName);
		scoreText.setText(String.valueOf(score));
	}
	

}
