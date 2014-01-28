package ch.silvanv.evolving;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Probe2 implements Probe {
  private static final long serialVersionUID = 1L;

  private final Integer arg1; // type has changed from String to Integer
  private final String arg2; // new property has been added

  public Probe2(Integer arg1, String arg2) {
    this.arg1 = arg1;
    this.arg2 = arg2;
  }

  @Override
  public Integer arg1() {
    return arg1;
  }

  @Override
  public String arg2() {
    return arg2;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
  }
}