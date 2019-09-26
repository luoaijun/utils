package com.luoaijun.utils.net; /**
 * @dec
 * @Version 1.0
 * @Author aijun.luo
 * @Date 14:43
 */

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.log4j.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class RestUtils {
    private static Logger logger = Logger.getLogger(RestUtils.class);

    /**
     * TODO
     *
     * @param url
     * @param param
     * @return
     */
    public static String getMethodByParam(String url, String param) {
        return getMethod(url + param);
    }

    /**
     * TODO get 数据接口
     *
     * @param url
     * @return
     */
    public static String getMethod(String url) {
        URL restURL = null;
        String line = null;
        try {
            restURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
            conn.setRequestMethod("GET"); // POST GET PUT DELETE
            conn.setRequestProperty("Accept", "application/json");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((line = br.readLine()) != null) {
                br.close();
                conn.disconnect();
                if (line.contains("StatusCode")) logger.error("GET URL: " + url + "response: " + line);
                else {
                    logger.info("GET URL :" + url + " response: " + "success");
                    return line;
                }
            }
            br.close();
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * TODO post 数据接口
     *
     * @param url
     * @param query
     * @return
     */
    public static String postMethod(String url, String query) {

        URL restURL = null;
        String line = null;

        try {
            restURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            PrintStream ps = new PrintStream(conn.getOutputStream());
            ps.print(query);
            ps.flush();
            ps.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = br.readLine()) != null) {
                if (!line.equals("success")) logger.error("URL:" + url + " POST " + line);
                else logger.info("URL:" + url + " POST " + line);
            }

            br.close();
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    /**
     * TODO put
     *
     * @param parm
     * @param Url
     * @return
     */
    public static String putMethod(String parm, String Url) {
        try {
            URL url = new URL(Url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 此时cnnection只是为一个连接对象,待连接中
            connection.setDoOutput(true);  // 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
            connection.setDoInput(true);  // 设置连接输入流为true
            connection.setRequestMethod("PUT");  // 设置请求方式为post
            connection.setUseCaches(false);   // post请求缓存设为false
            connection.setInstanceFollowRedirects(true);   // 设置该HttpURLConnection实例是否自动执行重定向
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            connection.connect();  // 建立连接
            // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
            DataOutputStream dataOut = new DataOutputStream(connection.getOutputStream());
            dataOut.writeBytes(parm);
            dataOut.flush();
            dataOut.close();
            BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            StringBuilder sb = new StringBuilder(); // 用来存储响应数据
            // 循环读取流,若不到结尾处
            while ((line = bf.readLine()) != null) {
                sb.append(line).append(System.getProperty("line.separator"));
            }
            bf.close();
            connection.disconnect(); // 销毁连接
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * TODO delete
     *
     * @param url
     * @return
     */
    public static String deleteMethod(String url) {
        HttpClient httpClient = new HttpClient();
        DeleteMethod deleteMethod = new DeleteMethod(url);
        try {
            int statusCode = httpClient.executeMethod(deleteMethod);
            if (statusCode != HttpStatus.SC_OK) {
                logger.error("Method failed: " + deleteMethod.getStatusLine());
                ;
            }
            byte[] responseBody = deleteMethod.getResponseBody();
            logger.info("DELETE URL :" + url + " response: " + responseBody);
            return new String(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            deleteMethod.releaseConnection();
        }
        return null;
    }

}
