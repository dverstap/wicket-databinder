package net.databinder.hib.conv.components;

import org.hibernate.Session;

public interface IConversationPage {
	public Session getConversationSession(Object key);
	public void setConversationSession(Object key, Session conversationSession);
}
