package be.trojkasoftware.android.diagramming;

import java.util.EventObject;

public class ItemMovedEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ItemMovedEvent(Object source) {
        super(source);
    }
}
