package be.trojkasoftware.android.gestures;

import java.util.ArrayList;

public class TouchEvent {
	public static final int TOUCH_DOWN = 1;
	public static final int TOUCH_MOVE = 2;
	public static final int TOUCH_UP = 3;
	
	public int event;
	public boolean isOptional = false;
	public ArrayList<IfThenClause> conditionList = new ArrayList<IfThenClause>();
}
