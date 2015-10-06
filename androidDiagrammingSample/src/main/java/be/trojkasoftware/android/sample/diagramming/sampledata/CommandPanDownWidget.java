package be.trojkasoftware.android.sample.diagramming.sampledata;

import be.trojkasoftware.android.diagramming.CommandWidget;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;

/**
 * Created by SergeDesmedt on 6/10/2015.
 */
public class CommandPanDownWidget extends CommandWidget {

    public CommandPanDownWidget(DiagramDesignerView view) {
        super(view);

        this.setCommand(new CommandPanDown(view));
    }

    protected String getDrawableResourceName()
    {
        return "pan_down";
    }

}
