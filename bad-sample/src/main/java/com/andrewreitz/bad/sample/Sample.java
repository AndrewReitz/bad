package com.andrewreitz.bad.sample;

import com.andrewreitz.bad.Bad;

public class Sample {
  @Bad
  int testing;

  @Bad
  public Sample() {
    System.out.println("Inside of sample class");
  }
}
