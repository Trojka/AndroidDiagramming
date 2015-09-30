package be.trojkasoftware.android.diagramming.commands.widgets;

import be.trojkasoftware.android.diagramming.CommandWidget;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.commands.DeleteItemsCommand;


public class DeleteSelectedItemsCommandWidget extends CommandWidget {
	
	public DeleteSelectedItemsCommandWidget(DiagramDesignerView view) {
		super(view);
		
		this.setCommand(new DeleteItemsCommand(view));
	}

	protected String getDrawableResourceName()
	{
		return "remove";
	}

}
