package ch.silvanv.evolving;

import static ch.silvanv.evolving.Probe.Factory.probe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {
  private Logger logger = LoggerFactory.getLogger(Client.class);

  public Client() {
    Probe dummy = probe(1, "arg");
    logger.info(dummy.toString());
  }
}
