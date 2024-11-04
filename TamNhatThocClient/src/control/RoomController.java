package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Message;
import model.User;
import utils.Util;

public class RoomController {
	private List<User> u;
	
	
	
	public List<User> getU() {
		return u;
	}


	public void setU(List<User> u) {
		this.u = u;
	}

	private Communication commu;

    public Communication getCommu() {
		return commu;
	}


	public void setCommu(Communication commu) {
		this.commu = commu;
	}

    @FXML
    private Button StartButton;

    @FXML
    private Label UserName1;

    @FXML
    private Label UserName2;
    @FXML
    private Text score1;

    @FXML
    private Text score2;
    @FXML
    private ListView<String> chatList;
    @FXML
    private TextField chatTextField;
    @FXML
    private Button sendChatBtn;

    @FXML
    void sendFriendRequest(ActionEvent event) {
    	if(u.size()>1) {
    		User sendUser = new User(null, null);
    		for (User user: u) {
    			if (user.getUid() != commu.getCurrentUser().getUid()) {
    				sendUser = user;
    				break;
    			}
    		}
    		
    		
        	if (sendUser != null) {
            	try {
        			commu.sendMessage(new Message("SEND_FRIEND_REQUEST", sendUser));
        			Util.showError("Đã gửi lời mời tới " + sendUser.getUsername());
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        	}
    	}


    }
    
    private ObservableList<String> chatListObsl;
    

    public RoomController() {
		super();
		chatListObsl = FXCollections.observableArrayList();
	}
	@FXML
    void initialize() {
		chatList.setItems(chatListObsl);
    }
	public void addChatRoomMsg(String chatMsg) {
		chatListObsl.add(0,chatMsg);
	}

	@FXML
    void startButtonClick(ActionEvent event) {
    	start();	
    }
    @FXML
    void goBackButtunClick(ActionEvent event) {
    	leaveRoom();
		HomeController homeController = commu.getNavigation().switchTo("Home.fxml");
		homeController.updateUserInfor(commu.getCurrentUser().getUsername(), commu.getCurrentUser().getScore());
    	UserName1.setText("Waiting1");
    	UserName2.setText("Waiting2");
		homeController.sendReLoadTopPlayerRequest();
		commu.getNavigation().resetScene("Room.fxml");
    }
    
    
    
    
	public void setUserName1(String userName1) {
		UserName1.setText(userName1);
	}


	public void setUserName2(String userName2) {
		UserName2.setText(userName2);
	}
	
	private void leaveRoom() {
		try {
			commu.sendMessage(new Message("LEAVE_ROOM",commu.getCurrentUser().getUsername()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setUserText() {
		System.out.println(u);
		if (u.size()>=2) {
			UserName1.setText(u.get(0).getUsername());
			score1.setText(String.valueOf(u.get(0).getScore()));
			UserName2.setText(u.get(1).getUsername());
			score2.setText(String.valueOf(u.get(1).getScore()));
		}
		else {
			UserName1.setText(u.get(0).getUsername());
			score1.setText(String.valueOf(u.get(0).getScore()));
			UserName2.setText("Wait");
			score2.setText("???");
		}
	
	}
	public void start() {
		if (u.size()<=1) {
			Platform.runLater(() -> {
				Util.showError("Phong chua du nguoi");
			});
		}
		else {
	    	try {
				commu.sendMessage(new Message("START",commu.getCurrentUser().getUsername()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	
	}
	public void setButtonStartText(String txt) {
	    StartButton.setText(txt);
	}
    @FXML
    void sendChatBtnClick(ActionEvent event) {
    	String chatMessage = commu.getCurrentUser().getUsername() +": " + chatTextField.getText().trim(); // Lấy và loại bỏ khoảng trắng

        if (!chatMessage.isEmpty()) {
            try {
                // Tạo thông điệp chat để gửi đến server
                Message message = new Message("CHAT_TO_ROOM_MESSAGE", chatMessage);

                // Gửi thông điệp qua lớp Communication
                commu.sendMessage(message);

                // Thêm tin nhắn vào ListView để hiển thị
//                chatList.getItems().add("You: " + chatMessage);

                // Xóa nội dung trong chatTextField sau khi gửi
                chatTextField.clear();
            } catch (IOException e) {
                e.printStackTrace();
                Util.showError("Không thể gửi tin nhắn. Vui lòng thử lại.");
            }
        }
    }

}
