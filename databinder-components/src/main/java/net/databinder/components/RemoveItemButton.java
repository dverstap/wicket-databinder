/*
 * Databinder: a simple bridge from Wicket to Hibernate
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

import java.util.List;

import org.apache.wicket.markup.html.list.ListItem;

/** Removes the given item from its list. */
public class RemoveItemButton extends ListItemButton {
	public RemoveItemButton(String id, ListItem item) {
		super(id, item, getTrashImage());
	}
	@Override
	public void onSubmit() {
		List list = getListView().getList();
		int index = item.getIndex();
		if (index != -1)
		{
			getListView().modelChanging();
			list.remove(index);
			getListView().modelChanged();
		}
	}
}
