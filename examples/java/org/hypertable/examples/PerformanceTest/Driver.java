/**
 * Copyright (C) 2010 Doug Judd (Hypertable, Inc.)
 *
 * This file is part of Hypertable.
 *
 * Hypertable is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or any later version.
 *
 * Hypertable is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

package org.hypertable.examples.PerformanceTest;

import java.io.IOException;
import java.lang.Math;
import java.util.logging.Logger;

import org.hypertable.Common.DiscreteRandomGeneratorZipf;

public abstract class Driver {

  static final Logger log = Logger.getLogger("org.hypertable.examples.PerformanceTest");

  public void setup(Setup setup) {
    mSetup = setup;
    mResult = new Result();

    if (mSetup.distribution == Setup.Distribution.ZIPFIAN) {
      if (mSetup.distributionRange == 0) {
        System.out.println("Distribution range must be specified for Zipfian random distribution");
        System.exit(-1);
      }
      mZipf = new DiscreteRandomGeneratorZipf(0, (int)mSetup.distributionRange, 1, 0.8);
      mZipf.setSeed( System.nanoTime() );
      mZipfianMultiplier = mSetup.keyMax / mSetup.distributionRange;
    }
  }

  protected long getRandomLong() {
    return Math.abs(mCommon.random.nextLong());
  }

  public abstract void teardown();

  public abstract void runTask(Task task) throws IOException;

  public Result getResult() { return mResult; }

  protected Result mResult;
  protected DriverCommon mCommon = new DriverCommon();
  protected Setup mSetup;
  protected DiscreteRandomGeneratorZipf mZipf;
  protected long mZipfianMultiplier = 0;
}
