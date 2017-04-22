package org.apache.spark.sql.execution.joins.dns;
/**
 * In this join, we are going to implement an algorithm similar to symmetric hash join.
 * However, instead of being provided with two input relations, we are instead going to
 * be using a single dataset and obtaining the other data remotely -- in this case by
 * asynchronous HTTP requests.
 * <p>
 * The dataset that we are going to focus on reverse DNS, latitude-longitude lookups.
 * That is, given an IP address, we are going to try to obtain the geographical
 * location of that IP address. For this end, we are going to use a service called
 * telize.com, the owner of which has graciously allowed us to bang on his system.
 * <p>
 * For that end, we have provided a simple library that makes asynchronously makes
 * requests to telize.com and handles the responses for you. You should read the
 * documentation and method signatures in DNSLookup.scala closely before jumping into
 * implementing this.
 * <p>
 * The algorithm will work as follows:
 * We are going to be a bounded request buffer -- that is, we can only have a certain number
 * of unanswered requests at a certain time. When we initialize our join algorithm, we
 * start out by filling up our request buffer. On a call to next(), you should take all
 * the responses we have received so far and materialize the results of the join with those
 * responses and return those responses, until you run out of them. You then materialize
 * the next batch of joined responses until there are no more input tuples, there are no
 * outstanding requests, and there are no remaining materialized rows.
 * <p>
 */
public  interface DNSJoin {
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> leftKeys () ;
  public  org.apache.spark.sql.execution.SparkPlan left () ;
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () ;
  public  org.apache.spark.sql.catalyst.expressions.Projection leftKeyGenerator () ;
  public  int requestBufferSize () ;
  /**
   * The main logic for DNS join. You do not need to implement anything outside of this method.
   * This method takes in an input iterator of IP addresses and returns a joined row with the location
   * data for each IP address.
   * <p>
   * If you find the method definitions provided to be counter-intuitive or constraining, feel free to change them.
   * However, note that if you do:
   *  1. we will have a harder time helping you debug your code.
   *  2. Iterators must implement next and hasNext. If you do not implement those two methods, your code will not compile.
   * <p>
   * **NOTE**: You should return JoinedRows, which take two input rows and returns the concatenation of them.
   * e.g., <code>new JoinedRow(row1, row2)</code>
   * <p>
   * @param input the input iterator
   * @return the result of the join
   */
  public  scala.collection.Iterator<org.apache.spark.sql.catalyst.expressions.Row> hashJoin (scala.collection.Iterator<org.apache.spark.sql.catalyst.expressions.Row> input) ;
}
