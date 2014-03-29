/*
 * Copyright 2014 Andrew Reitz
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

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
