package dataObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

//When call to list match do not send all the data but just the name of the match
//transfert optimisation purpose
public class ListMatchName implements Serializable {

	private static final long serialVersionUID = -8663297309570067180L;
	//not thread safe
	private HashMap<Integer,TinyMatch> tinyMatchList = new HashMap<Integer,TinyMatch>();
	
	public ListMatchName(Match[] allMatch) {
		for (int i = 0; i < allMatch.length; i++) {
			if(allMatch[i] != null){
				Match currentMatch = allMatch[i];
				TinyMatch matchSentence = new TinyMatch();
				matchSentence.setTeamA(currentMatch.getDomicile().getName());
				matchSentence.setTeamB(currentMatch.getExterieur().getName());
				matchSentence.setTime(currentMatch.getTime());
				tinyMatchList.put( i,matchSentence  );
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public HashMap<Integer, String> getMatchName() {
		HashMap<Integer,String> matchName = new HashMap<Integer,String>();
		int i = 0;
		Iterator<Entry<Integer, TinyMatch>> it = tinyMatchList.entrySet().iterator();
	    while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			matchName.put(i, pair.toString());
			i++;
	    }
	    return matchName;
	}

	@Override
	public String toString() {
		return "ListMatchName [matchName=" + getMatchName() + "]";
	}
	
	public HashMap<Integer,TinyMatch> getList(){
		return this.tinyMatchList;
	}
	
}
