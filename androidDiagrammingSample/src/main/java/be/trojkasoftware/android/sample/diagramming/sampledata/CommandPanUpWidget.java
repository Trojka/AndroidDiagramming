package be.trojkasoftware.android.sample.diagramming.sampledata;

import be.trojkasoftware.android.diagramming.CommandWidget;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;

/**
 * Created by SergeDesmedt on 6/10/2015.
 */
public class CommandPanUpWidget extends CommandWidget {

    public CommandPanUpWidget(DiagramDesignerView view) {
        super(view);

        this.setCommand(new CommandPanUp(view));
    }

    protected String getDrawableResourceName()
    {
        return "pan_up";
    }

}
