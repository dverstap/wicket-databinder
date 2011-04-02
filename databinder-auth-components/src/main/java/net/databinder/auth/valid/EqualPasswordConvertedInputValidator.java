/*
 * Databinder: a simple bridge from Wicket to JPA Copyright (C) 2006 Nathan
 * Hamblen nathan@technically.us This library is free software; you can
 * redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version. This library is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 */
package net.databinder.auth.valid;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.util.lang.Objects;

/** Compares post-conversion input, match is valid. */
public class EqualPasswordConvertedInputValidator<T> extends
EqualPasswordInputValidator {

  /** */
  private static final long serialVersionUID = 1L;

  public EqualPasswordConvertedInputValidator(final FormComponent<T> comp1,
      final FormComponent<T> comp2) {
    super(comp1, comp2);
  }

  @Override
  public void validate(final Form<?> form) {
    final FormComponent<?>[] components = getDependentFormComponents();
    final FormComponent<?> formComponent1 = components[0];
    final FormComponent<?> formComponent2 = components[1];

    if (!Objects.equal(formComponent1.getConvertedInput(),
        formComponent2.getConvertedInput())) {
      error(formComponent2);
    }
  }

  @Override
  protected String resourceKey() {
    return Classes.simpleName(EqualPasswordInputValidator.class);
  }

}
