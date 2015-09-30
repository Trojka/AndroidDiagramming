package be.trojkasoftware.android.gestures;

public class CheckUntilTrueAndAcceptAlways implements IGestureCondition, IResetable {

	public CheckUntilTrueAndAcceptAlways(IGestureCondition condition) {
		checkCondition = condition;
	}
	
	@Override
	public boolean checkCondition(GestureEvent motion, TouchGesture gesture) {
		if(!isTrue)
		{
			isTrue = checkCondition.checkCondition(motion, gesture);
		}
		return isTrue;
	}
	
	@Override
	public void reset() {
		isTrue = false;
	}
	
	IGestureCondition checkCondition;
	boolean isTrue = false;
}