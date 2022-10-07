package org.aibles.okrs.core_business.contants;

public enum Status {
  NOT_STARTED,
  IN_PROGRESS,
  DONE,
  UNKNOWN;

  public static boolean contains(String test) {

    for (Status c : Status.values()) {
      if (c.name().equals(test)) {
        return true;
      }
    }
    return false;
  }
}
