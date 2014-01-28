package ch.silvanv.evolving;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Probe1 implements Probe {
  private static final long serialVersionUID = 1L;

  private final String arg1;

  public Probe1(String arg1) {
    this.arg1 = arg1;
  }

  @Override
  public Integer arg1() {
    return Integer.valueOf(arg1); // adapt type
  }

  @Override
  public String arg2() {
    return "default arg2"; // provide default
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
  }
}
