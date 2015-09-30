package be.trojkasoftware.android.gestures;

import java.util.ArrayList;
import java.util.List;

public class DoMultipleAction implements IGestureAction {

	public DoMultipleAction(IGestureAction action1, IGestureAction action2) {
		actionList.add(action1);
		actionList.add(action2);
	}

	public DoMultipleAction(IGestureAction action1, IGestureAction action2, IGestureAction action3) {
		actionList.add(action1);
		actionList.add(action2);
		actionList.add(action3);
	}

	public DoMultipleAction(IGestureAction action1, IGestureAction action2, IGestureAction action3, IGestureAction action4) {
		actionList.add(action1);
		actionList.add(action2);
		actionList.add(action3);
		actionList.add(action4);
	}

	public DoMultipleAction(IGestureAction action1, IGestureAction action2, IGestureAction action3, IGestureAction action4, IGestureAction action5) {
		actionList.add(action1);
		actionList.add(action2);
		actionList.add(action3);
		actionList.add(action4);
		actionList.add(action5);
	}

	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		for(IGestureAction action:actionList)
		{
			action.executeAction(motion, gesture);
		}
	}
	
	private List<IGestureAction> actionList = new ArrayList<IGestureAction>();

}
