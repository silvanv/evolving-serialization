package ch.silvanv.evolving;

import java.io.Serializable;

/** Sample probe, which may evolve. */
public interface Probe extends Serializable {
  Integer arg1(); // was previously String

  String arg2(); // has been added

  // implementations

  class Factory {
    public static Probe probe(final Integer arg1, final String arg2) {
      return new Probe2(arg1, arg2);
    }
  }

  /** Actual version */
  class Probe2 implements Probe {
    private static final long serialVersionUID = 2L;

    private final Integer arg1; // type has changed from String to Integer
    private final String arg2; // new property has been added

    private Probe2(Integer arg1, String arg2) {
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
  }

  /** Previous/Origin version */
  class Probe1 implements Probe {
    private static final long serialVersionUID = 1L;

    private final String arg1;

    private Probe1(String arg1) {
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
  }
}
