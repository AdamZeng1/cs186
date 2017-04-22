package org.apache.spark.sql.execution;
public  class ComplicatedRecord implements scala.Product, scala.Serializable {
  public  int a () { throw new RuntimeException(); }
  public  java.lang.String b () { throw new RuntimeException(); }
  public  int c () { throw new RuntimeException(); }
  // not preceding
  public   ComplicatedRecord (int a, java.lang.String b, int c) { throw new RuntimeException(); }
}
