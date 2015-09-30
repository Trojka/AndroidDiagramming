package be.trojkasoftware.android;

import android.util.TypedValue;
import android.view.View;

public class ScreenVector {
	public ScreenVector(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public ScreenVector(WorldVectorMetric wvm, View vw)
	{
    	//Size_in_mm = Size_in_inches * 25.4;
    	//Size_in_inches = Size_in_mm / 25.4;
    	//Size_in_dp = Size_in_inches * 160;
    	//Size_in_dp = (Size_in_mm / 25.4) * 160;
    	//Size_in_inches = Size_in_dp / 160;    
    	x = (int) TypedValue.applyDimension(
    			TypedValue.COMPLEX_UNIT_DIP, 
    			(float)(wvm.x * 160 / 25.4), 
    			vw.getResources().getDisplayMetrics());
    	y = (int) TypedValue.applyDimension(
    			TypedValue.COMPLEX_UNIT_DIP, 
    			(float)(wvm.y * 160 / 25.4), 
    			vw.getResources().getDisplayMetrics());
	}
	
	public ScreenVector subtract(ScreenVector toSubtract)
	{
		return new ScreenVector(x-toSubtract.x, y-toSubtract.y);
	}
	
	public ScreenVector add(ScreenVector toAdd)
	{
		return new ScreenVector(x+toAdd.x, y+toAdd.y);
	}
	
	public int size()
	{
		return (int)Math.sqrt((x*x) + (y*y));
	}
	
	public int distance(ScreenVector toAdd)
	{
		return (int)Math.sqrt(((toAdd.x - x) * (toAdd.x - x)) + ((toAdd.y - y) * (toAdd.y - y)));
	}
	
	public int x;
	public int y;
}
