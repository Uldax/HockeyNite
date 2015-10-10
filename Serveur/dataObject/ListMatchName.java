package dataObject;

import java.io.Serializable;
import java.util.HashMap;

//When call to list match do not send all the data but just the name of the match
//transfert optimisation purpose
public class ListMatchName implements Serializable {

	private static final long serialVersionUID = -8663297309570067180L;
	//not thread safe
	private HashMap<Integer,String> matchName = new HashMap<Integer,String>();
	
	public ListMatchName(Match[] allMatch) {
		for (int i = 0; i < allMatch.length; i++) {
			if(allMatch[i] != null){
				Match currentMatch = allMatch[i];
				int[] currentTime = currentMatch.splitToTimes();
				String matchSentence = currentMatch.getDomicile().getName() + " VS " +currentMatch.getExterieur().getName() + " Timer :  "; 
				for( int j = 0; j < 3 ; j++)	{	
					matchSentence +=currentTime[j];
					if(j<2) {
						matchSentence+= " : ";
					}
				}
				matchName.put( i,matchSentence  );
			}
		}
	}
	
	public HashMap<Integer, String> getMatchName() {
		return matchName;
	}

	@Override
	public String toString() {
		return "ListMatchName [matchName=" + matchName + "]";
	}
	
}
