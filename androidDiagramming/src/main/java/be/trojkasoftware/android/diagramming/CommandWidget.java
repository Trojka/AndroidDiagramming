package be.trojkasoftware.android.diagramming;

import be.trojkasoftware.android.diagramming.commands.ICommand;

import be.trojkasoftware.android.ScreenVector;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class CommandWidget implements IDrawable {
	

	public CommandWidget()
	{
	}

	public CommandWidget(DiagramDesignerView view)
	{
		this.mView = view;
	}
	
	public void setDesigner(DiagramDesignerView view)
	{
		this.mView = view;
	}
	
	public DiagramDesignerView getDesigner()
	{
		return mView;
	}
	
	public ICommand getCommand()
	{
		return command;
	}
	
	protected void setCommand(ICommand command)
	{
		this.command = command;
	}
	
	public void setPosition(ScreenVector pos)
	{
		position = pos;
		
		setDrawableBounds();
	}
	
	public int getWidth()
	{
		if(mDrawable == null)
		{
			getTemplate();
		}
		return width;
	}
	
	public int getHeight()
	{
		if(mDrawable == null)
		{
			getTemplate();
		}
		return height;
	}
	
	public Rect getBounds()
	{
		if(mDrawable == null)
		{
			getTemplate();
		}
		Rect result = new Rect();
		
		result.left = position.x - width/2;
		result.right = position.x + width/2;
		result.top = position.y - height/2;
		result.bottom = position.y + height/2;
		
		return result;
	}
	
	public HitDefinition isHit(ScreenVector hitPoint, ScreenVector selectionWindow)
	{
		Rect selectionRect = new Rect(hitPoint.x-selectionWindow.x/2, hitPoint.y-selectionWindow.x/2, hitPoint.x+selectionWindow.x/2, hitPoint.y+selectionWindow.x/2);
		HitDefinition result = null;
		Rect bounds = getTemplate().getBounds();
		if (Rect.intersects(bounds, selectionRect))
		{
			result = new HitDefinition();
			result.command = this;
		}
				
		return result;
	}
	
	protected String getDrawableResourceName()
	{
		return "information";
	}
	
	public void Draw(Canvas canvas)
	{
    	getTemplate().draw(canvas);
    }
	
	private Drawable getTemplate()
	{
		if(mDrawable == null)
		{
		    Resources res = mView.getResources();
		    try {
                String packageName = mView.getContext().getPackageName();
		    	int id = res.getIdentifier(getDrawableResourceName(), "drawable", packageName);
		    	mDrawable = (Drawable) res.getDrawable(id);
		    } catch (Exception ex) {
		       Log.e("Error", "Exception loading drawable: " + ex.getMessage());
		    }
			if(mDrawable instanceof BitmapDrawable)
			{
				width = ((BitmapDrawable)mDrawable).getBitmap().getWidth();
				height = ((BitmapDrawable)mDrawable).getBitmap().getHeight();
			}
		    setTemplateDrawableBounds();
		}
		return mDrawable;
	}
	
	private void setDrawableBounds()
	{
		setTemplateDrawableBounds();
	}
	
	private void setTemplateDrawableBounds()
	{
		if(mDrawable != null)
		{
			mDrawable.setBounds(
					position.x - width/2, 
					position.y - height/2, 
					position.x + width/2, 
					position.y + height/2);
		}	
	}

	private ICommand command;
	
	private Drawable mDrawable;
	private DiagramDesignerView mView;
	ScreenVector position = new ScreenVector(100, 100);
    int width = 16;
    int height = 16;
}
