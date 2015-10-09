package server;
import org.apache.log4j.Logger;


import dataObject.Match;
import dataObject.Team;
public class DAO {
	private static final Logger logger = Logger.getLogger(DAO.class);
	public final static int MAX_MATCH = 10;
	private Match ListMatch[] = new Match[MAX_MATCH];
	public static int MODIF_TIME = 5000;
	
	//Initialisation with some ramdom value	
	public DAO(){
		Team t1 =  new Team("A");
		Team t2 =  new Team("B");
		Team t3 =  new Team("C");
		Team t4 =  new Team("D");
		Match M1 = new Match(t1,t2);
		Match M2 = new Match(t3,t4);
		ListMatch[0] = M1;
		ListMatch[1]  = M2;
		logger.info("Dao ini");	
		this.startEventManager();
		
	}
	public Match getMatch(int index){
		if(ListMatch[index] != null){
			return ListMatch[index];
		} else {
			logger.error("undefined index");
			return null;
		}
		
	}
	public Match[] getAllMatch(){
		if (ListMatch.length < 1) {
		    return null;
		}
		return ListMatch;
	}
	
	private void startEventManager(){
		new Thread(new EventManager(this)).start();
	}
}
