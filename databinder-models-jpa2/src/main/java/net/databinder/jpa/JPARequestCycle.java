package net.databinder.jpa;

/**
 * Request cycle that should be notified on the first use of a data session.
 */
public interface JPARequestCycle {
  public void dataEntityManagerRequested(String persistenceUnitName);
}
