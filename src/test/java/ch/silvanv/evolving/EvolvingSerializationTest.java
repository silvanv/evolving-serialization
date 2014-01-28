package ch.silvanv.evolving;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvolvingSerializationTest {
  public final Logger logger = LoggerFactory.getLogger(EvolvingSerializationTest.class);

  @Test
  public void test() {
    // Given
    final Probe probe = new Probe2(2, "arg");

    // When
    final Probe ret = unmarshal(marshal(probe));

    // Then
    assertThat(ret.arg1(), is(2));
    assertThat(ret.arg2(), is("arg"));

    logger.info(ret.toString());
  }

  @Test
  public void testOldVersionFromFile() {
    // Given
    final Path serializedProbe1FilePath = Paths.get("probe1.ser");

    // When
    final Probe ret = unmarshalFromFile(serializedProbe1FilePath);

    // Then
    assertThat(ret.arg1(), is(Integer.valueOf(1)));
    assertThat(ret.arg2(), is("default arg2"));

    logger.info(ret.toString());
  }

  // support

  public static void main(final String[] args) {
    final EvolvingSerializationTest inst = new EvolvingSerializationTest();
    inst.marshalToFile(new Probe1("1"), Paths.get("probe1.ser"));
    inst.marshalToFile(new Probe2(2, "arg2"), Paths.get("probe2.ser"));
  }

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
}
