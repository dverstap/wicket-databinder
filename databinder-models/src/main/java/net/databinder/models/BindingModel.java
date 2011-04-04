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
package net.databinder.models;

import org.apache.wicket.model.IModel;


/**
 * Model associated with an object that can be "bound" to persistent storage.
 * @author Nathan Hamblen
 */
public interface BindingModel<T> extends IModel<T> {
  /**
   * @return true if object is bound to persistent storage
   */
  public boolean isBound();

  /**
   * Discard any attached object and reset to a new unsaved object
   */
  public void unbind();
}
