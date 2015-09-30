package be.trojkasoftware.android.diagramming.adorners;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import be.trojkasoftware.android.diagramming.DiagramDesignerItem;
import be.trojkasoftware.android.diagramming.DiagramDesignerItemAdorner;
import be.trojkasoftware.android.diagramming.DiagramDesignerSelectionAdorner;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.ItemMovedEvent;


public class MoveAdorner extends DiagramDesignerSelectionAdorner {

	public MoveAdorner(Context context, DiagramDesignerView view) {
		super(context, view);
	}
	
	@Override
	public void Draw(Canvas canvas) {
	    
		Paint paint = new Paint();

		paint.setColor(android.graphics.Color.RED);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);

		
		Rect itemBounds = getBoundingBox();
		
		Path moveUpArrow = new Path();
		moveUpArrow.moveTo(itemBounds.centerX(), itemBounds.top - padding - arrowSize);
        moveUpArrow.lineTo(itemBounds.centerX() + arrowSize/2, itemBounds.top - padding);
        moveUpArrow.lineTo(itemBounds.centerX() - arrowSize/2, itemBounds.top - padding);
        moveUpArrow.close();
		
		Path moveRightArrow = new Path();
		moveRightArrow.moveTo(itemBounds.right + padding + arrowSize, itemBounds.centerY());
		moveRightArrow.lineTo(itemBounds.right + padding, itemBounds.centerY() + arrowSize/2);
		moveRightArrow.lineTo(itemBounds.right + padding, itemBounds.centerY() - arrowSize/2);
		moveRightArrow.close();
		
		Path moveDownArrow = new Path();
		moveDownArrow.moveTo(itemBounds.centerX(), itemBounds.bottom + padding + arrowSize);
		moveDownArrow.lineTo(itemBounds.centerX() - arrowSize/2, itemBounds.bottom + padding);
		moveDownArrow.lineTo(itemBounds.centerX() + arrowSize/2, itemBounds.bottom + padding);
		moveDownArrow.close();
		
		Path moveLeftArrow = new Path();
		moveLeftArrow.moveTo(itemBounds.left - padding - arrowSize, itemBounds.centerY());
		moveLeftArrow.lineTo(itemBounds.left - padding, itemBounds.centerY() - arrowSize/2);
		moveLeftArrow.lineTo(itemBounds.left - padding, itemBounds.centerY() + arrowSize/2);
		moveLeftArrow.close();
        
        canvas.drawPath(moveUpArrow, paint);
        canvas.drawPath(moveRightArrow, paint);
        canvas.drawPath(moveDownArrow, paint);
        canvas.drawPath(moveLeftArrow, paint);


	}

	private int arrowSize = 20;
	private int padding = 2;
}
