package net.databinder.components;

import java.util.Collection;

import org.apache.wicket.markup.repeater.Item;

/** Removes the given item from its collection. */
public class RemoveRepeaterItemButton extends RepeaterItemButton {
   
   public RemoveRepeaterItemButton(String id, Item item) {
      super(id, item, getTrashImage());
   }
   
   @Override
   public void onSubmit() {
      Collection c = (Collection) getView().getDefaultModelObject();
      if (c != null) {
         getView().modelChanging();
         // depends on correct equals()!
         c.remove(item.getModelObject());
         getView().modelChanged();
      }
   }
}