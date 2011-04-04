/*
 * Databinder: a simple bridge from Wicket to JPA
 * Copyright (C) 2006  Nathan Hamblen nathan@technically.us

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
package net.databinder.components;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.model.IModel;

/**
 * TextField that can be told to focus itself on the next request.  Works in conjunction with
 * the onload handler.
 */
public class FocusableTextField<T> extends TextField<T> {
  private boolean wantsFocus = false;

  /**
   * 	@param id Wicket id
   * @param model text field model
   */
  public FocusableTextField(final String id, final IModel<T> model) {
    super (id, model);
    add(ScriptLink.headerContributor(FocusableTextField.class));
  }

  /**
   * @param id Wicket id
   */
  public FocusableTextField(final String id) {
    this(id, (IModel<T>) null);
  }

  @Override
  public void renderHead(final HtmlHeaderContainer container) {
    super.renderHead(container);
    container.getHeaderResponse().renderOnLoadJavascript("initFocusableTextField();");
  }

  /**
   * Request focus on next rendering.
   */
  public void requestFocus() {
    wantsFocus = true;
  }

  /** Adds flagging id attribute if focus has been requested. */
  @Override
  protected void onComponentTag(final ComponentTag tag) {
    if (wantsFocus) {
      tag.put("id", "focusMe");
      wantsFocus = false;
    }
    super.onComponentTag(tag);
  }
}