package animator.model.enums;

/**
 * Type for valid shape reference points and their string representations.
 */
public enum RefPointType {
  CENTER("Center"),
  MINCORNER("Min-corner");

  private final String stringValue;

  RefPointType(String stringValue) {
    this.stringValue = stringValue;
  }

  public String getStringValue() {
    return stringValue;
  }

}