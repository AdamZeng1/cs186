package org.apache.spark.sql.catalyst.expressions;
public  class MinFunction extends org.apache.spark.sql.catalyst.expressions.AggregateFunction implements scala.Product, scala.Serializable {
  public  org.apache.spark.sql.catalyst.expressions.Expression expr () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.AggregateExpression base () { throw new RuntimeException(); }
  // not preceding
  public   MinFunction (org.apache.spark.sql.catalyst.expressions.Expression expr, org.apache.spark.sql.catalyst.expressions.AggregateExpression base) { throw new RuntimeException(); }
  public   MinFunction () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.MutableLiteral currentMin () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.GreaterThan cmp () { throw new RuntimeException(); }
  public  void update (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
  public  Object eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
}
