package dataObject;

import java.io.Serializable;

public class Bet implements Serializable {
	
    private static final long serialVersionUID = 1337133713371337L;
    private String betID = null; 
    private int matchID = 0;
    private String teamName = null;
    private String clientFirstName = null;
    private String clientLastName = null;
    private String clientAddress = null;
    private byte[] clientIPAddress = new byte[4];
    private float betAmount = 0;

    public Bet(int matchID, String teamName, float betAmount) {
       this.matchID = matchID;
       this.teamName = teamName;
       this.betAmount = betAmount;        
    }

    public void setTeamName(String teamID) {
        this.teamName = teamName;
    }
 
    public void setBetID(String betID) {
        this.betID = betID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public void setClientIPAddress(byte[] clientIPAddress) {
        this.clientIPAddress = clientIPAddress;
    }
    
    public void setBetAmount(float betAmount) {
        this.betAmount = betAmount;
    }

    public String getBetID() {
        return betID;
    }

    public int getMatchID() {
        return matchID;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public byte[] getClientIPAddress() {
        return clientIPAddress;
    }
    
     public float getBetAmount() {
        return betAmount;
    }
}