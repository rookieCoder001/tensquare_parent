package com.tensquare.filter;

import com.google.common.base.Charsets;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import com.tensquare.rsa.RsaKeys;
import com.tensquare.service.RsaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 对请求参数进行rsa解码的过滤器
 *
 * @Author luo
 * @Date 2020/4/5 20:44
 */
@Component
public class RsaDecodeFilter extends ZuulFilter {


    @Autowired
    private RsaService rsaService;

    /*
     PRE: 该类型的filters在Request routing到源web-service之前执行。用来实现Authentication、选择源服务地址等
     ROUTING：该类型的filters用于把Request routing到源web-service，源web-service是实现业务逻辑的服务。这里使用HttpClient请求web-service。
     POST：该类型的filters在ROUTING返回Response后执行。用来实现对Response结果进行修改，收集统计数据以及把Response传输会客户端。
     ERROR：上面三个过程中任何一个出现错误都交由ERROR类型的filters进行处理。
     */
    @Override//拦截时机 FilterConstants.PRE_TYPE=”“”“"pre" 表示在请求到达具体资源之前访问
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override//拦截器执行顺序  返回数字越小 优先级越高
    public int filterOrder() {
        return 0;
    }

    @Override//是否开启拦截器 true表示开启
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 对请求数据进行rsa解密后返回到对应的服务资源中
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {

        //获取请求和响应对象 这里使用zuul内置api
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpServletResponse response = currentContext.getResponse();

        //请求参数
        String RequestParam = null;
        //解码后的参数
        String decodeRequestParam = null;
        try {
            //获取请求参数
            RequestParam = StreamUtils.copyToString(request.getInputStream(), Charsets.UTF_8);
            System.out.println("请求参数；"+RequestParam);
            if (!StringUtils.isEmpty(RequestParam)) {
                //例用私钥进行解码
                decodeRequestParam = rsaService.RSADecryptDataPEM(RequestParam, RsaKeys.getServerPrvKeyPkcs8());
                System.out.println("解码后的参数为"+decodeRequestParam);
                //将解码后的参数转换字节数组
                final byte[] bytes = decodeRequestParam.getBytes();
                //将解码参数替换原参数
                currentContext.setRequest(new HttpServletRequestWrapper(request) {
                    @Override
                    public ServletInputStream getInputStream() throws IOException {
                        return new ServletInputStreamWrapper(bytes);
                    }

                    @Override
                    public int getContentLength() {
                        return bytes.length;
                    }

                    @Override
                    public long getContentLengthLong() {
                        return bytes.length;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //因为传递的数据类型是Json 所以需要设置content-type
        currentContext.addZuulRequestHeader("Content-Type","application/json;charset=utf-8");
        return null;
    }
}
