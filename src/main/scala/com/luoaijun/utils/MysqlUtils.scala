package com.luoaijun.utils

import better.files._
import better.files.File._
import java.sql.{Connection, DriverManager, Statement}
import java.util.Properties

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object MysqlUtils {
  val properties = new Properties()
  val path = this.getClass.getClassLoader.getResourceAsStream("db.properties")
  val local_url = properties.getProperty("mysql_url")
  val local_username = properties.getProperty("mysql_username")
  val local_password = properties.getProperty("mysql_password")

  /**
    * TODO 连接并获取一个mysql statement
    */
  def getConnection(url: String, username: String, password: String): Connection = {
    if (!Utils.isEmpty(url)) {
      classOf[com.mysql.jdbc.Driver]
      DriverManager.getConnection(url, username, password)
    } else DriverManager.getConnection(local_url, local_username, local_password)
  }

  /**
    * TODO close connection
    *
    * @param conn
    */
  def close(conn: Connection): Unit = {
    try {
      if (!conn.isClosed() || conn != null) {
        conn.close()
      }
    } catch {
      case ex: Exception => {
        ex.printStackTrace()
      }
    }
  }

  /**
    * TODO update
    *
    * @param sql
    * @param conn
    */
  def update(sql: String, conn: Connection): Int = {

    val statement = conn.createStatement()
    val resultSet = statement.executeUpdate(sql)
    resultSet
  }

  /**
    * TODO  query
    *
    * @param sql
    * @param field
    * @param tag
    * @param conn
    * @return
    */
  def query(sql: String, field: String, tag: String, conn: Connection): mutable.HashMap[Any, Any] = {
    val result = new mutable.HashMap[Any, Any]()
    val statement = conn.createStatement()
    val resultSet = statement.executeQuery(sql)
    while (resultSet.next()) {
      var content = new mutable.HashMap[Any, Any]
      val fields = field.split(",").iterator
      while (fields.hasNext) {
        var field = fields.next().toString().trim()
        content.put(field, resultSet.getString(field))
      }
      result.put(resultSet.getString(tag), content)
    }
    result
  }

  def main(args: Array[String]) {
    //....
    val field = " title,categories,content "
    val condition = ""
    val sql = "select" + field + "from dw_contents " + condition
    val tag = "title"
    val result = query(sql, field, tag, getConnection("jdbc:mysql://39.106.53.34:3306/dw_cdes", "root", "luoaijun"))
    val fileIter = result.iterator
    var next: Any = 0

    while (fileIter.hasNext) {
      next = fileIter.next()
      val categories = next.asInstanceOf[Tuple2[Any, Any]]._1
      val cupe = next.asInstanceOf[Tuple2[Any, Any]]._2
      val catalogName = cupe.asInstanceOf[mutable.HashMap[Any, Any]].get("categories").asInstanceOf[Some[Any]].value
      val content = cupe.asInstanceOf[mutable.HashMap[Any, Any]].get("content").asInstanceOf[Some[Any]].value
      val path = "C:\\work\\CDES\\code\\test\\chinese_text_classification\\train_corpus\\"
      val dir: File = (path+catalogName).toString
        .toFile
        .createIfNotExists(true)
      val writer: File = ((path+catalogName) + "\\" + Utils.randomHashcode() + ".txt")
        .toFile
        .createIfNotExists()
        .write(content.toString)

    }
  }
}
