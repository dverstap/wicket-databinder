package net.databinder.auth.ao;

import java.io.Serializable;

import net.databinder.auth.AuthDataSessionBase;
import net.databinder.auth.data.ao.DataUserEntity;
import net.databinder.models.ao.EntityModel;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.http.WebRequest;

/** Session to hold DataUser. */
public class AuthDataSession<T extends DataUserEntity<K>, K extends Serializable>
		extends AuthDataSessionBase<T> {
	public AuthDataSession(final WebRequest request) {
		super(request);
	}

	@Override
	public IModel<T> createUserModel(final T user) {
		return (IModel<T>) new EntityModel<T, K>(user);
	}
}
