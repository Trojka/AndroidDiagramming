package be.trojkasoftware.android.diagramming.commands;

import java.util.List;

import be.trojkasoftware.android.diagramming.HitDefinition;

public interface IActOnSelection {
	void setSelection(List<HitDefinition> selection);
}
