package io.cdap.wrangler.api.parser;

import java.util.HashMap;
import java.util.Map;

public class TimeDuration extends Token {
  private static final Map<String, Long> UNIT_MAP = new HashMap<>();

  static {
    UNIT_MAP.put("ns", 1L);
    UNIT_MAP.put("ms", 1_000_000L);
    UNIT_MAP.put("s", 1_000_000_000L);
    UNIT_MAP.put("m", 60_000_000_000L);
    UNIT_MAP.put("h", 3_600_000_000_000L);
  }

  private final long nanoseconds;

  public TimeDuration(String value) {
    super(TokenType.TIME_DURATION, value);
    value = value.trim().toLowerCase();
    String numberPart = value.replaceAll("[a-zA-Z]", "");
    String unitPart = value.replaceAll("[0-9.]", "");

    if (!UNIT_MAP.containsKey(unitPart)) {
      throw new IllegalArgumentException("Unknown time unit: " + unitPart);
    }

    this.nanoseconds = (long) (Double.parseDouble(numberPart) * UNIT_MAP.get(unitPart));
  }

  public long getNanoseconds() {
    return nanoseconds;
  }

  public long getMilliseconds() {
    return nanoseconds / 1_000_000L;
  }
}
