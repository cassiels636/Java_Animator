package animator.model.enums;

/**
 * Type for possible shapes and their string representations.
 */
public enum ShapeType {
  RECTANGLE("rectangle"),
  SQUARE("square"),
  OVAL("oval"),
  CIRCLE("circle"),
  POLYGON("polygon");

  private final String stringValue;

  ShapeType(String stringValue) {
    this.stringValue = stringValue;
  }

  public String getStringValue() {
    return stringValue;
  }
}
