//package com.clean.applet.common.utils.http;
//
//import com.sun.deploy.net.HttpResponse;
//import io.netty.channel.ConnectTimeoutException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpEntity;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.net.ssl.SSLContext;
//import java.io.IOException;
//import java.net.SocketTimeoutException;
//import java.net.URI;
//import java.nio.charset.Charset;
//import java.security.KeyManagementException;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//
//
//public class HttpRequestUtil {
//    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);
//
//    /**
//     * 使用HTTPClient进行POST请求
//     *
//     * @param api_url 请求路径
//     * @param param   请求格式有name1=value1&name2=value2、json、xml、map或其他形式，具体要看接收方的取值
//     * @return
//     */
//    public static String sendGet(String api_url, String param) {
//        logger.info("请求URL：" + api_url + "，参数：" + param);
//
//        CloseableHttpClient httpClient = null;
//        try {
//            httpClient = HttpClients.createDefault();   // 生成一个httpclient对象
//            HttpGet httpGet = new HttpGet(api_url + "?" + param);
//            // 连接超时时间，10秒, 传输超时时间，30秒，不设置超时的话，一旦服务器没有响应，等待时间N久(>24小时)。
//            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(30000).build();
//            httpGet.setConfig(requestConfig);   // 设置请求器的配置
//
//            // 配置请求的HEADER部分
//            httpGet.addHeader(HttpHeaders.ACCEPT, "application/xml");
//
//            HttpResponse response = httpClient.execute(httpGet);    //执行请求Http
//            HttpEntity entity = response.getEntity();
//
////          logger.info("执行请求：" + httpPost.getRequestLine());
////          logger.info("响应的所有HEADER内容：" + Arrays.toString(response.getAllHeaders()));
//
//            // 判断响应实体是否为空
//            if (entity != null) {
//                String content = EntityUtils.toString(entity, "UTF-8");
//                logger.info("响应内容：" + content);
//                return content;
//            }
//        } catch (ConnectionPoolTimeoutException e) {
//            e.printStackTrace();
//            logger.error("http get throw ConnectionPoolTimeoutException(等待超时)");
//        } catch (ConnectTimeoutException e) {
//            e.printStackTrace();
//            logger.error("http get throw ConnectTimeoutException(联接超时)");
//        } catch (SocketTimeoutException e) {
//            e.printStackTrace();
//            logger.error("http get throw SocketTimeoutException(通讯超时)");
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("http get throw Exception");
//        } finally {
//            if (httpClient != null) {
//                try {
//                    httpClient.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return "";
//    }
//
//    /**
//     * 使用HTTPClient进行POST请求
//     *
//     * @param api_url 请求路径
//     * @param param   请求格式有name1=value1&name2=value2、json、xml、map或其他形式，具体要看接收方的取值
//     * @return
//     */
//    public static String sendPost(String api_url, String param) {
//        logger.info("请求URL：" + api_url + "，参数：" + param);
//
//        CloseableHttpClient httpClient = null;
//        try {
//            httpClient = HttpClients.createDefault();   // 生成一个httpclient对象
//            HttpPost httpPost = new HttpPost(api_url);
//            // 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
//            StringEntity stringEntity = new StringEntity(param, "UTF-8");
//            stringEntity.setContentType("application/x-www-form-urlencoded");
////            httpPost.addHeader("Content-Type", "text/xml");
//            httpPost.setEntity(stringEntity);
//
//            // 连接超时时间，10秒, 传输超时时间，30秒，4.3版本不设置超时的话，一旦服务器没有响应，等待时间N久(>24小时)。
//            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(30000).build();
//            httpPost.setConfig(requestConfig);// 设置请求器的配置
//
//            HttpResponse response = httpClient.execute(httpPost);   //执行请求Http
//            HttpEntity entity = response.getEntity();
//
////          logger.info("执行请求：" + httpPost.getRequestLine());
////          logger.info("响应的所有HEADER内容：" + Arrays.toString(response.getAllHeaders()));
//
//            // 判断响应实体是否为空
//            if (entity != null) {
//                String content = EntityUtils.toString(entity, "UTF-8");
//                logger.info("响应内容：" + content);
//                return content;
//            }
//        } catch (ConnectionPoolTimeoutException e) {
//            e.printStackTrace();
//            logger.error("http get throw ConnectionPoolTimeoutException(等待超时)");
//        } catch (ConnectTimeoutException e) {
//            e.printStackTrace();
//            logger.error("http get throw ConnectTimeoutException(联接超时)");
//        } catch (SocketTimeoutException e) {
//            e.printStackTrace();
//            logger.error("http get throw SocketTimeoutException(通讯超时)");
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("http get throw Exception");
//        } finally {
//            if (httpClient != null) {
//                try {
//                    httpClient.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return "";
//    }
//
//
//    public static CloseableHttpClient createSSLClientDefault() {
////      ManagedHttpClientConnectionFactory
//        try {
//            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
//                // 信任所有
//                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                    return true;
//                }
//            }).build();
//            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
//            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        }
//        return HttpClients.createDefault();
//    }
//
//    /**
//     * 请求Https请求
//     *
//     * @param url
//     * @return
//     */
//    public static String requestHttps(String url) {
//        logger.info("请求URL：" + url);
//
//        CloseableHttpClient httpClient = createSSLClientDefault();
//        HttpGet httpGet = new HttpGet();
//        try {
//            httpGet.setURI(new URI(url));
//
//            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(30000).build();
//            httpGet.setConfig(requestConfig);   // 设置请求器的配置
//
//            // 配置请求的HEADER部分
//            httpGet.addHeader(HttpHeaders.ACCEPT, "application/xml");
//
//            HttpResponse response = httpClient.execute(httpGet);    //执行请求Http
//            HttpEntity entity = response.getEntity();
//
////          logger.info("执行请求：" + httpPost.getRequestLine());
////          logger.info("响应的所有HEADER内容：" + Arrays.toString(response.getAllHeaders()));
//
//            // 判断响应实体是否为空
//            if (entity != null) {
//                String content = EntityUtils.toString(entity, "UTF-8");
//                logger.info("响应内容：" + content);
//                return content;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    public static String sendHttpPost(String httpUrl, MultipartFile file) {
//        try {
//            CloseableHttpClient client = HttpClients.createDefault();
//            HttpPost httpPost = new HttpPost(httpUrl);
//
//            MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
//            mEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//            mEntityBuilder.setCharset(Charset.forName("utf-8"));
//            //二进制参数
//            if (file != null && !file.isEmpty()) {
//                String fileName = file.getOriginalFilename();
//                mEntityBuilder.addBinaryBody("file", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
//            }
//            httpPost.setEntity(mEntityBuilder.build());
//
//            HttpResponse response = client.execute(httpPost);
//            HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                String content = EntityUtils.toString(entity, "UTF-8");
//                logger.info("响应内容：" + content);
//                return content;
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//}
