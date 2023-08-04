package Sobolev.SpringDemoBot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import javax.xml.crypto.Data;
import java.security.Timestamp;
import java.sql.Date;

@Entity(name = "usersDataTable")
public class User {

    @Id
    private Long chatId;

    private String firstName;

    private String lastName;

    private String userName;

    //private Timestamp registeredAt;
    private Date registeredData;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


//    public Timestamp getRegisteredAt() {
//        return registeredAt;
//    }

//    public void setRegisteredAt(Timestamp registeredAt) {
//        this.registeredAt = registeredAt;
//    }

    public Date getRegisteredData() {
        return registeredData;
    }

    public void setRegisteredData(Date registeredData) {
        this.registeredData = registeredData;
    }

    @Override
    public String toString() {
        return "User{" +
                "chatId=" + chatId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", registeredData=" + registeredData +
                '}';
    }
}
