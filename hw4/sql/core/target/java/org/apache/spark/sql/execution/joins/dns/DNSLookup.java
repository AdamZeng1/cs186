package org.apache.spark.sql.execution.joins.dns;
// no position
public  class DNSLookup {
  static public  java.lang.String BASE_IP_URL () { throw new RuntimeException(); }
  /**
   * This method takes in a request number, an IP address, and two concurrent hash maps (because we are multi threading
   * when using Futures). It makes the request. If it succeeds, it inserts the resulting Row into the responses. Else,
   * it removes the corresponding row from requests.
   * <p>
   * @param requestNumber The request number in order to match requests and responses
   * @param IP the ip we are doing the look up for
   * @param responses the data structure used to update responses
   * @param requests the data structure used to keep track of requests
   */
  static public  void lookup (int requestNumber, java.lang.String IP, java.util.concurrent.ConcurrentHashMap<java.lang.Object, org.apache.spark.sql.catalyst.expressions.Row> responses, java.util.concurrent.ConcurrentHashMap<java.lang.Object, org.apache.spark.sql.catalyst.expressions.Row> requests) { throw new RuntimeException(); }
  static public  org.apache.spark.sql.catalyst.expressions.Row convertJson (java.lang.String json) { throw new RuntimeException(); }
  /**
   * Credit for this code snippet goes to this blog: https://coderwall.com/p/o--apg/easy-json-un-marshalling-in-scala-with-jackson
   */
  static public  com.fasterxml.jackson.databind.ObjectMapper mapper () { throw new RuntimeException(); }
  static public <V extends java.lang.Object> scala.collection.immutable.Map<java.lang.String, V> toMap (java.lang.String json, scala.reflect.Manifest<V> m) { throw new RuntimeException(); }
  static public <T extends java.lang.Object> T fromJson (java.lang.String json, scala.reflect.Manifest<T> m) { throw new RuntimeException(); }
}
