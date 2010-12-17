package pl.touk.wonderfulsecurity.gwt.client.ui.infrastructure.mvp.handlers;

import pl.touk.wonderfulsecurity.gwt.client.ui.infrastructure.mvp.handlers.HasSelected;


public interface IsLikeButton extends HasSelected {

	String getText();
	void setText(String text);
	
	boolean isEnabled();
	void setEnabled(boolean enabled);
	
}
