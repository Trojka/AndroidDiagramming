package be.trojkasoftware.android.gestures.dsl;

import be.trojkasoftware.android.gestures.RelationType;

public class RangeTypeSelector {
	
	public RangeTypeSelector(RelationType relationType)
	{
		this.relationType = relationType;
	}

	public DistanceRangeStartSelector milliMeters(int range)
	{
		DistanceRangeStartSelector result = new DistanceRangeStartSelector(relationType, range);
		return result;
	}
	
	public TimeRangeStartSelector seconds(int range)
	{
		TimeRangeStartSelector result = new TimeRangeStartSelector(relationType, 1000 * range);
		return result;
	}
	
	private RelationType relationType;
}
