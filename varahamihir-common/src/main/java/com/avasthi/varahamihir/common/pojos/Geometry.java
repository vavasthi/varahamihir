package com.avasthi.varahamihir.common.pojos;

import java.util.List;

public class Geometry {
  private enum TYPE {
    Polygon
  };

  public Geometry() {
  }

  public Geometry(TYPE type, List<List<Float>> coordinates) {
    this.type = type;
    this.coordinates = coordinates;
  }

  public TYPE getType() {
    return type;
  }

  public void setType(TYPE type) {
    this.type = type;
  }

  public List<List<Float>> getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(List<List<Float>> coordinates) {
    this.coordinates = coordinates;
  }

  private TYPE type;
  private List<List<Float>> coordinates;
}
