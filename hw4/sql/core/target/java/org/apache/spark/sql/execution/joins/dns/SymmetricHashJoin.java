package org.apache.spark.sql.execution.joins.dns;
/**
 * ***** TASK 1 ******
 * <p>
 * Symmetric hash join is a join algorithm that is designed primarily for data streams.
 * In this algorithm, you construct a hash table for _both_ sides of the join, not just
 * one side as in regular hash join.
 * <p>
 * We will be implementing a version of symmetric hash join that works as follows:
 * We will start with the "left" table as the inner table and "right" table as the outer table
 * and begin by streaming tuples in from the inner relation. For every tuple we stream in
 * from the inner relation, we insert it into its corresponding hash table. We then check if
 * there are any matching tuples in the other relation -- if there are, then we join this
 * tuple with the corresponding matches. Otherwise, we switch the inner
 * and outer relations -- that is, the old inner becomes the new outer and the old outer
 * becomes the new inner, and we proceed to repeat this algorithm, streaming from the
 * new inner.
 */
public  interface SymmetricHashJoin {
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> leftKeys () ;
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> rightKeys () ;
  public  org.apache.spark.sql.execution.SparkPlan left () ;
  public  org.apache.spark.sql.execution.SparkPlan right () ;
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () ;
  public  org.apache.spark.sql.catalyst.expressions.Projection leftKeyGenerator () ;
  public  org.apache.spark.sql.catalyst.expressions.Projection rightKeyGenerator () ;
  /**
   * The main logic for symmetric hash join. You do not need to worry about anything other than this method.
   * This method takes in two iterators (one for each of the input relations), and returns an iterator (
   * representing the result of the join).
   * <p>
   * If you find the method definitions provided to be counter-intuitive or constraining, feel free to change them.
   * However, note that if you do:
   *  1. we will have a harder time helping you debug your code.
   *  2. Iterators must implement next and hasNext. If you do not implement those two methods, your code will not compile.
   * <p>
   * **NOTE**: You should return JoinedRows, which take two input rows and returns the concatenation of them.
   * e.g., <code>new JoinedRow(row1, row2)</code>
   * <p>
   * @param leftIter an iterator for the left input
   * @param rightIter an iterator for th right input
   * @return iterator for the result of the join
   */
  public  scala.collection.Iterator<org.apache.spark.sql.catalyst.expressions.Row> symmetricHashJoin (scala.collection.Iterator<org.apache.spark.sql.catalyst.expressions.Row> leftIter, scala.collection.Iterator<org.apache.spark.sql.catalyst.expressions.Row> rightIter) ;
}
