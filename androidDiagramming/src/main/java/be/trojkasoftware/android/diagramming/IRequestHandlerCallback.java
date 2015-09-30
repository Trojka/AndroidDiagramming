package be.trojkasoftware.android.diagramming;

import android.content.Intent;

public interface IRequestHandlerCallback {
	void HandleRequestResult(int requestCode, int resultCode, Intent intent);
}
