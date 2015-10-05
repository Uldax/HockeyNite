package server;
import org.apache.log4j.Logger;


import dataObject.Match;
import dataObject.Team;
public class DAO {
	private static final Logger logger = Logger.getLogger("DAO");
	private Match ListMatch[] = null;
	
	//Initialisation with some ramdom value	
	public DAO(){
		Team t1 =  new Team("A");
		Team t2 =  new Team("B");
		Team t3 =  new Team("C");
		Team t4 =  new Team("D");
		ListMatch[0] = new Match(t1,t2);
		ListMatch[1]  = new Match(t3,t4);
		logger.info("Dao ini");	
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
		return ListMatch;
	}
}
