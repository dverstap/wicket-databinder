package net.databinder.conv;

import net.databinder.jpa.conv.components.ConversationPage;

import org.apache.wicket.util.tester.WicketTester;

/**
 * @author fbencosme@kitsd.com
 */
public class ConversationPageTest {

    public void testRender() {
    	final WicketTester tester = new WicketTester();
    	tester.startPage(ConversationPage.class);
    }

}
