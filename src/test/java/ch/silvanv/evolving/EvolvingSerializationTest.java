package ch.silvanv.evolving;

import static ch.silvanv.evolving.Probe.Factory.probe;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class EvolvingSerializationTest {
  @Test
  public void testMarshaller() {
    // Given
    final Probe probe = probe(3, "arg");

    // When
    final Probe ret = unmarshal(marshal(probe));

    // Then
    assertThat(ret.arg1(), is(3));
    assertThat(ret.arg2(), is("arg"));
  }

  @Test
  public void testUnmarshalPreviousVersion() {
    // Given
    final Path serializedProbeFilePath = Paths.get("probe1.ser");

    // When
    final Probe ret = unmarshalFromFile(serializedProbeFilePath);

    // Then
    assertThat(ret.arg1(), is(1));
    assertThat(ret.arg2(), is("default arg2"));
  }

  // support

  public byte[] marshal(Object o) {
    try (final ByteArrayOutputStream binOut = new ByteArrayOutputStream();
        final ObjectOutputStream objectOut = new ObjectOutputStream(binOut)) {
      objectOut.writeObject(o);
      return binOut.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public <T> T unmarshal(byte[] i) {
    try (final ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(i))) {
      return (T) in.readObject();
    } catch (ClassNotFoundException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void marshalToFile(final Object o, final Path path) {
    try (final ObjectOutputStream objectOut = new ObjectOutputStream(Files.newOutputStream(path))) {
      objectOut.writeObject(o);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public <T> T unmarshalFromFile(final Path path) {
    try (final ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
      return (T) in.readObject();
    } catch (ClassNotFoundException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  /** Initially serialize the origin instance to a file. */
  public static void main(final String[] args) {
    // final EvolvingSerializationTest inst = new EvolvingSerializationTest();
    // inst.marshalToFile(probe("1"), Paths.get("probe1.ser"));
  }
}
