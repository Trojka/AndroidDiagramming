package be.trojkasoftware.android.diagramming.commands.widgets;

import be.trojkasoftware.android.diagramming.CommandWidget;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.commands.DeleteConnectionsCommand;
import be.trojkasoftware.android.diagramming.commands.DeleteItemsCommand;


public class DeleteSelectedConnectionsCommandWidget extends CommandWidget {
	
	public DeleteSelectedConnectionsCommandWidget(DiagramDesignerView view) {
		super(view);
		
		this.setCommand(new DeleteConnectionsCommand(view));
	}

	protected String getDrawableResourceName()
	{
		return "remove";
	}

}
