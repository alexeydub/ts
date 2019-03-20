package com.etrusted.interview.demo.rest.dto;

public class Alive {
  private boolean status;

  public Alive(boolean status) {
    this.status = status;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }
}
