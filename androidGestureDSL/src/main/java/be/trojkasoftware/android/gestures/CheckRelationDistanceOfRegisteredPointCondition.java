package be.trojkasoftware.android.gestures;

import android.util.Log;
import be.trojkasoftware.android.ScreenVector;


public class CheckRelationDistanceOfRegisteredPointCondition implements IGestureCondition {

	public CheckRelationDistanceOfRegisteredPointCondition(RelationType relationType, int rangeValue, String key) {
		
		this.relationType = relationType;
		this.range = rangeValue;
		this.dataKey = key;
	}
	
	@Override
	public boolean checkCondition(GestureEvent motion, TouchGesture gesture) {
		ScreenVector actionDown = (ScreenVector)gesture.getContext(dataKey);
		if(actionDown != null)
		{
			boolean result = false;
			int distance = (int)(actionDown.distance(motion.getPosition()));
			Log.i("CheckRelationDistanceOfRegisteredPointCondition", "distance[" + distance + "]?range[" + range + "]");
			switch(relationType)
			{
			case Equal:
				result = (distance == range);
				Log.i("CheckRelationDistanceOfRegisteredPointCondition", "Equal="+(result?"TRUE":"FALSE"));
				break;
			case Within:
				result = (distance <= range);
				Log.i("CheckRelationDistanceOfRegisteredPointCondition", "Within="+(result?"TRUE":"FALSE"));
				break;
			case Exceed:
				result = (distance >= range);
				Log.i("CheckRelationDistanceOfRegisteredPointCondition", "Exceed="+(result?"TRUE":"FALSE"));
				break;
			}
			
			return result;
		}

		return true;
	}

	private RelationType relationType;
	private int range;
	private String dataKey;
}