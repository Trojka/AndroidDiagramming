package be.trojkasoftware.android.gestures;

public class CheckRelationMilliSecondsOfTimingIdCondition implements IGestureCondition {

	public CheckRelationMilliSecondsOfTimingIdCondition(RelationType relationType, int rangeValue, String key) {
		
		this.relationType = relationType;
		this.range = rangeValue;
		this.dataKey = key;
	}
	
	@Override
	public boolean checkCondition(GestureEvent motion, TouchGesture gesture) {
		Long actionOnTime = (Long)gesture.getContext(dataKey);
		if(actionOnTime != null)
		{
			
			switch(relationType)
			{
			case Equal:
				return (long)(motion.getTime() - actionOnTime.longValue()) == range;
			case Within:
				return (long)(motion.getTime() - actionOnTime.longValue()) <= range;
			case Exceed:
				return (long)(motion.getTime() - actionOnTime.longValue()) >= range;
			}
			
		}

		return true;
	}

	private RelationType relationType;
	private int range;
	private String dataKey;
}