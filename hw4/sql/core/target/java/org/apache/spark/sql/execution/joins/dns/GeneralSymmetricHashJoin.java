package org.apache.spark.sql.execution.joins.dns;
/**
 * This is just Spark-magic to create this operator.
 * @param leftKeys
 * @param rightKeys
 * @param left
 * @param right
 */
public  class GeneralSymmetricHashJoin extends org.apache.spark.sql.execution.SparkPlan implements org.apache.spark.sql.execution.BinaryNode, org.apache.spark.sql.execution.joins.dns.SymmetricHashJoin, scala.Product, scala.Serializable {
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> leftKeys () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> rightKeys () { throw new RuntimeException(); }
  public  org.apache.spark.sql.execution.SparkPlan left () { throw new RuntimeException(); }
  public  org.apache.spark.sql.execution.SparkPlan right () { throw new RuntimeException(); }
  // not preceding
  public   GeneralSymmetricHashJoin (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> leftKeys, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> rightKeys, org.apache.spark.sql.execution.SparkPlan left, org.apache.spark.sql.execution.SparkPlan right) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.physical.Partitioning outputPartitioning () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> execute () { throw new RuntimeException(); }
}
