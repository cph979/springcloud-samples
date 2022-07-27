package com.example.consumer.web;

import com.example.commons.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * @author cph
 * @date 2022/07/17
 */
@RestController
public class UserController {

    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;

    @Resource(name = "restTemplateLoadBalanced")
    private RestTemplate restTemplateLoadBalanced;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/hello")
    public String helloConsumer() throws Exception {
        HttpURLConnection con;

        /* 动态获取服务提供者地址 */
        List<ServiceInstance> list = discoveryClient.getInstances("provider");
        ServiceInstance serviceInstance = list.get(0);
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();

        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(host).append(":").append(port)
                .append("/hello");
        URL url = new URL(sb.toString());
        con = (HttpURLConnection) url.openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        return br.readLine();
    }

    @GetMapping("/hello2")
    public String helloConsumer2() {
        /* 动态获取服务提供者地址 */
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("provider");
        ServiceInstance serviceInstance = serviceInstances.get(0);
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();

        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(host).append(":").append(port)
                .append("/hello");

        return restTemplate.getForObject(sb.toString(), String.class);
    }

    @GetMapping("/hello3")
    public String helloConsumer3() {
        return restTemplateLoadBalanced.getForObject("http://provider/hello", String.class);
    }

    @GetMapping("/hello4")
    public void helloConsumer4() {
        System.out.println("restTemplateLoadBalanced.getForEntity(\"http://provider/hello\", String.class) = "
                + restTemplateLoadBalanced.getForEntity("http://provider/hello", String.class));

        System.out.println("restTemplateLoadBalanced.getForObject(\"http://provider/hello2?name={1}\", String.class, \"CPH\") = "
                + restTemplateLoadBalanced.getForObject("http://provider/hello2?name={1}", String.class, "CPH"));
    }

    /**
     * 三种请求重载方法
     */
    @GetMapping("/hello5")
    public void helloConsumer5() throws UnsupportedEncodingException {

        // uri 传递参数（get）需要转码
        URI uri = URI.create("http://provider/hello2?name=" + URLEncoder.encode("李四", "UTF-8"));
        System.out.println(restTemplateLoadBalanced.getForEntity(uri, String.class));

        System.out.println(restTemplateLoadBalanced.getForEntity("http://provider/hello2?name={1}", String.class, "jjb2"));

        HashMap<String, String> map = new HashMap<String, String>(2) {{
            put("name", "张三");
        }};
        // 参数属性名需要与 map 里对应
        System.out.println(restTemplateLoadBalanced.getForEntity("http://provider/hello2?name={name}", String.class, map));

    }

    /**
     * post 演示
     *
     * @return
     */
    @GetMapping("hello6")
    public String hello6() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap(4) {
            {
                add("id", 1);
                add("username", "cph");
                add("password", "cph_pwd");
            }
        };

        /* 对象参数传递，就是使用 JSON 的方式：接口方 @RequestBody。 map 方式：即 kv 方式 */

        User u = restTemplateLoadBalanced.postForObject("http://provider/hello4", map, User.class);
        System.out.println(u);

        u.setId(2);
        System.out.println(restTemplateLoadBalanced.postForObject("http://provider/hello5", u, User.class));
        return u.toString();

        // postForLocation 。调用重定向接口使用。即接口 status code = 302
        /*URI uri = restTemplateLoadBalanced.postForLocation("http://provider/register", map);
        System.out.println(uri);
        String forObject = restTemplateLoadBalanced.getForObject(uri, String.class);
        return forObject;*/
    }

    /* put 请求。没有返回值。接口方不需要返回，void。大体类似 post */

    /* delete 请求。没有返回值。接口方不需要返回，void。大体类似 post */
    @GetMapping("hello7")
    public void hello7() {
        restTemplateLoadBalanced.delete("http://provider/hello6?id={1}", 9);
        restTemplateLoadBalanced.delete("http://provider/hello7/{1}", 99);
    }
}
