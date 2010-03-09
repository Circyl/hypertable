/**
 * Copyright (C) 2010 Doug Judd (Hypertable, Inc.)
 *
 * This file is part of Hypertable.
 *
 * Hypertable is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
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

package org.hypertable.hadoop.mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;


/**
 * Utility for {@link TableMapper} and {@link TableReducer}
 */
@SuppressWarnings("unchecked")
public class Helper {
  
  /**
   * Use this before submitting a TableMap job. It will appropriately set up 
   * the job.
   * 
   * @param table  The table name to read from.
   * @param scan  The scan instance with the columns, time range etc.
   * @param mapper  The mapper class to use.
   * @param outputKeyClass  The class of the output key.
   * @param outputValueClass  The class of the output value.
   * @param job  The current job to adjust.
   * @throws IOException When setting up the details fails.
   */
  public static void initMapperJob(String table, ScanSpec scan_spec,
      Class<? extends Mapper> mapper, 
      Class<? extends WritableComparable> outputKeyClass, 
      Class<? extends Writable> outputValueClass, Job job) throws IOException {
    job.setInputFormatClass(InputFormat.class);
    if (outputValueClass != null)
      job.setMapOutputValueClass(outputValueClass);
    if (outputKeyClass != null)
      job.setMapOutputKeyClass(outputKeyClass);
    job.setMapperClass(mapper);
    job.getConfiguration().set(InputFormat.TABLE, table);
    job.getConfiguration().set(InputFormat.SCAN_SPEC, scan_spec.toSerializedText());
  }

  /**
   * Use this before submitting a TableReduce job. It will
   * appropriately set up the JobConf.
   * 
   * @param table  The output table.
   * @param reducer  The reducer class to use.
   * @param job  The current job to adjust.
   * @throws IOException When determining the region count fails. 
   */
  public static void initReducerJob(String table,
    Class<? extends Reducer> reducer, Job job) throws IOException {

    Configuration conf = job.getConfiguration();
    job.setOutputFormatClass(OutputFormat.class);
    if (reducer != null)
      job.setReducerClass(reducer);
    conf.set(OutputFormat.TABLE, table);
    job.setOutputKeyClass(KeyWritable.class);
    job.setOutputValueClass(BytesWritable.class);
  }
  
}