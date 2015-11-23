/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataObject;

import java.io.Serializable;

/**
 *
 * @author JUASP-G73-Android
 */
public class BetRespond implements Serializable {
    private static final long serialVersionUID = 1327132713372467L;
    private String betID = null;
    private int matchID = 0;
    private int status = 0; // 0(did not win) , 1(won) , 2(match isnt finish yet)
    private float betAmount = 0;
    private float winningAmount = 0; 
    
      public BetRespond(String betID, int matchID, int status) {
        this.betID = betID;
        this.matchID = matchID;
        this.status = status;      
    }
    
    public BetRespond(String betID, int matchID, int status, float betAmount, float winningAmount) {
        this.betID = betID;
        this.matchID = matchID;
        this.status = status; 
        this.betAmount = betAmount;
        this.winningAmount = winningAmount;
    }

    @Override
    public String toString() {
        return "BetRespond{" + "betID=" + betID + ", matchID=" + matchID + ", status=" + status + ", betAmount=" + betAmount + ", winningAmount=" + winningAmount + '}';
    }

  

    public String getBetID() {
        return betID;
    }

    public void setBetID(String betID) {
        this.betID = betID;
    }

    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(float betAmount) {
        this.betAmount = betAmount;
    }

    public float getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(float winningAmount) {
        this.winningAmount = winningAmount;
    }
    
}
