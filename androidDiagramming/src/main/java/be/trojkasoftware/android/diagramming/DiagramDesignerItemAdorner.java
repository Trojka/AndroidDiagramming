package be.trojkasoftware.android.diagramming;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class DiagramDesignerItemAdorner implements IDrawable, IItemMovedEventListener {

	public DiagramDesignerItemAdorner(Context context)
	{
		this.context = context;
	}

	public DiagramDesignerItemAdorner(Context context, DiagramDesignerItem adornedItem)
	{
		this.context = context;
		attach(adornedItem);
	}
	
	public DiagramDesignerItem getAdornedItem()
	{
		return adornedItem;
	}
	
	public void attach(DiagramDesignerItem adornedItem)
	{
		this.adornedItem = adornedItem;
		this.adornedItem.addEventListener(this);
	}
	
	public void detach()
	{
		adornedItem.removeEventListener(this);
	}
	
	@Override
	public void handleItemMovedEvent(ItemMovedEvent evt) {
		setTemplateDrawableBounds();
	}
	
	@Override
	public void Draw(Canvas canvas) {
    	getTemplate().draw(canvas);
	}
	
	protected String getDrawableResourceName()
	{
		return null;
	}
	
	private Drawable getTemplate()
	{
		if(mDrawable == null)
		{
		    Resources res = context.getResources();
		    try {
		    	int id = context.getResources().getIdentifier(getDrawableResourceName(), "drawable", context.getPackageName());
		    	mDrawable = (Drawable) res.getDrawable(id);
		    } catch (Exception ex) {
		       Log.e("Error", "Exception loading drawable: " + ex.getMessage());
		    }
		    setTemplateDrawableBounds();
		}
		return mDrawable;
	}
	
	protected void setTemplateDrawableBounds()
	{
		if(mDrawable != null)
		{
			Rect adornedItemBounds = adornedItem.getBounds();
			adornedItemBounds.inset(-1 * padding/2, -1 * padding/2);
			
			mDrawable.setBounds(adornedItemBounds);
		}	
	}
	
	private int padding = 10;
	
	private Drawable mDrawable;
	
	private Context context;
	DiagramDesignerItem adornedItem;
}
