package be.trojkasoftware.android.gestures;

public interface IGestureCondition {
	boolean checkCondition(GestureEvent motion, TouchGesture gesture);
}
