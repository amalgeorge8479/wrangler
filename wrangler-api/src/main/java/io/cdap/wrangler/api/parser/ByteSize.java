package io.cdap.wrangler.api.parser;

import java.util.HashMap;
import java.util.Map;

public class ByteSize extends Token {
  private static final Map<String, Long> UNIT_MAP = new HashMap<>();

  static {
    UNIT_MAP.put("b", 1L);
    UNIT_MAP.put("kb", 1024L);
    UNIT_MAP.put("mb", 1024L * 1024L);
    UNIT_MAP.put("gb", 1024L * 1024L * 1024L);
    UNIT_MAP.put("tb", 1024L * 1024L * 1024L * 1024L);
  }

  private final long bytes;

  public ByteSize(String value) {
    super(TokenType.BYTE_SIZE, value);
    value = value.trim().toLowerCase();
    String numberPart = value.replaceAll("[a-zA-Z]", "");
    String unitPart = value.replaceAll("[0-9.]", "");

    if (!UNIT_MAP.containsKey(unitPart)) {
      throw new IllegalArgumentException("Unknown byte unit: " + unitPart);
    }

    this.bytes = (long) (Double.parseDouble(numberPart) * UNIT_MAP.get(unitPart));
  }

  public long getBytes() {
    return bytes;
  }
}
