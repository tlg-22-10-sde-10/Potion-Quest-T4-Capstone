package com.potionquest.gui.entity;

import org.junit.Assert;
import org.testng.annotations.Test;

public class EntityTest {

  @Test
  public void imageFetchInvalid() {
    Entity entity = new Entity();
    String invalidPath = "sword.png";

    var image = entity.imageFetch(invalidPath);

    Assert.assertNull(image);
  }

  @Test
  public void imageFetchValid() {
    Entity entity = new Entity();
    String validPath = "/sword.png";

    var image = entity.imageFetch(validPath);
    Assert.assertNotNull(image);
  }
}