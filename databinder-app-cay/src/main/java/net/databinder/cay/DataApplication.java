package net.databinder.cay;

import net.databinder.DataApplicationBase;

import org.apache.wicket.IRequestCycleProvider;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.cycle.RequestCycleContext;

/**
 * Application base for Cayenne.
 */
public abstract class DataApplication extends DataApplicationBase {

  /** Does nothing, no init required. */
  @Override
  protected void dataInit() {
  }
  @Override
  protected void internalInit() {
    super.internalInit();
    setRequestCycleProvider(new IRequestCycleProvider() {

      public RequestCycle get(final RequestCycleContext context) {
        return new DataRequestCycle(context);
      }
    });
  }
  
}
