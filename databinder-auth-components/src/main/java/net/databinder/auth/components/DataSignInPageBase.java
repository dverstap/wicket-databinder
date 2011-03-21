/*
 * Databinder: a simple bridge from Wicket to JPA
 * Copyright (C) 2006  Nathan Hamblen nathan@technically.us
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package net.databinder.auth.components;

import net.databinder.auth.AuthApplication;
import net.databinder.auth.AuthSession;
import net.databinder.auth.data.DataUser;
import net.databinder.components.DataStyleLink;
import net.databinder.components.SourceList;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.IClusterable;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.UnauthorizedInstantiationException;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.ResourceModel;

/**
 * Sign in and registration page.
 * Replaceable String resources: <pre>
 * data.auth.title.sign_in
 * data.auth.pre_register_link
 * data.auth.register_link
 * data.auth.pre_sign_in_link
 * data.auth.sign_in_link
 * or a subclass of this panel.
 */
public abstract class DataSignInPageBase<T extends DataUser> extends WebPage {
  private SourceList sourceList;

  private Component profileSocket, signinSocket;

  private SourceList.SourceLink profileLink, signinLink;

  public interface ReturnPage extends IClusterable {
    Page get();
  }

  /** Displays sign in page. */
  public DataSignInPageBase(final PageParameters params) {
    this(params, null);
  }

  public DataSignInPageBase(final ReturnPage returnPage) {
    this(null, returnPage);
  }

  public DataSignInPageBase(final PageParameters params, final ReturnPage returnPage) {
    AuthApplication<T> app = null;
    try { app = (AuthApplication<T>)Application.get(); } catch (final ClassCastException e) { }
    // make sure the user is not trying to sign in or register with the wrong page
    if (app == null || !app.getSignInPageClass().isInstance(this)) {
      throw new UnauthorizedInstantiationException(DataSignInPageBase.class);
    }

    if (params != null) {
      final String username = params.getString("username");
      final String token = params.getString("token");
      // e-mail auth, for example
      if (username != null && token != null) {
        final T user = app.getUser(username);

        if (user != null && app.getToken(user).equals(token)) {
          getAuthSession().signIn(user, true);
        }
        setResponsePage(((Application)app).getHomePage());
        setRedirect(true);
        return;
      }
    }

    add(new Label("title", new ResourceModel("data.auth.title.sign_in", "Please sign in")));

    add(new DataStyleLink("dataStylesheet"));

    sourceList = new SourceList();

    add(profileSocket = profileSocket("profileSocket", returnPage));
    add(new WebMarkupContainer("profileLinkWrapper") {
      @Override
      public boolean isVisible() {
        return profileLink.isEnabled();
      }
    }.add(
        (profileLink = sourceList.new SourceLink("profileLink", profileSocket)).add(
            new Label("text", getString("data.auth.register_link", null, "Register now"))
        )
    ).add(
        new Label("text", getString("data.auth.pre_register_link", null, "Don't have an account?"))
    ));

    add(signinSocket = signinSocket("signinSocket", returnPage));
    add(new WebMarkupContainer("signinLinkWrapper") {
      @Override
      public boolean isVisible() {
        return signinLink.isEnabled();
      }
    }.add(
        new Label("text", getString("data.auth.pre_sign_in_link", null, "Already have an account?"))
    ).add((signinLink = sourceList.new SourceLink("signinLink", signinSocket)).add(
        new Label("text", getString("data.auth.sign_in_link", null, "Sign in"))
    )));
    signinLink.onClick();	// show sign in first
  }

  /**
   * Default returns DataSignInPanel.
   * @return component (usually panel) to display for sign in
   * @see DataSignInPanel
   */
  protected Component signinSocket(final String id, final ReturnPage returnPage) {
    return new DataSignInPanel(id, returnPage);
  }

  /** @return new component to display for profile / registration */
  protected abstract Component profileSocket(String id, ReturnPage returnPage);

  /** @return casted session */
  protected AuthSession<T> getAuthSession() {
    return (AuthSession<T>) Session.get();
  }
}
