package com.weilaizhe.merchant.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.constant.RedisKeyConst;
import com.weilaizhe.common.enums.StatusCode;
import com.weilaizhe.common.exception.ErrorPageException;
import com.weilaizhe.common.exception.ResultInfo;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.util.CommonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * @dateTime 2019-03-26 17:53
 * @author: dameizi
 * @description: 登录处理
 */
@Component
public class LoginHandler implements HandlerInterceptor {

    public static Logger logger = LogManager.getLogger(LoginHandler.class);

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //上下文路径请求路径判断
        if(CommonConst.ERROR_PATH.equals(request.getServletPath())) {
            throw new ErrorPageException(CommonConst.REQUEST_PATH_ERROR, StatusCode.STATUS_404.getStatus());
        }
        //COOKIE 和 SESSION用户的校验
        String token = CommonUtil.getToken(RedisKeyConst.MERCHANT_TOKEN_COOKIE);
        String redisSessionKey = MessageFormat.format(RedisKeyConst.REDIS_MERCHANT_SESSION_KEY, token);
        RBucket<MerchantVO> merchantVO = redissonClient.getBucket(redisSessionKey);
        merchantVO.expire(30, TimeUnit.MINUTES);
        MerchantVO user = merchantVO.get();
        if (StringUtils.isEmpty(token) || user == null) {
            // 跳转到登录页面，把用户请求的url作为参数传递给登录页面
            returnResult(response, new ResultInfo(StatusCode.STATUS_401.getStatus(),CommonConst.LOGIN_PAST));
            return false;
        }
        //单账号登录后台,否则删除上一个会话
        String redisToeknKey = MessageFormat.format(RedisKeyConst.REDIS_MERCHANT_TOKEN, user.getAccount());
        RBucket<String> buck =  redissonClient.getBucket(redisToeknKey);
        String reidsToken;
        if(buck != null) {
            reidsToken = buck.get().toString();
            //表示不是同一会话登录 注销会话
            if (!reidsToken.equals(token)) {
                // redisson会话过期设置
                merchantVO.delete();
                returnResult(response, new ResultInfo(StatusCode.STATUS_401.getStatus(), "账号已在其他地方登陆,请重新登录"));
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {}

    /**
     * @author: dameizi
     * @dateTime: 2019/3/26 0026 下午 17:50
     * @description: 输出resut结果集
     * @param: [response, result]
     * @return: void
     */
    private void returnResult(HttpServletResponse response, ResultInfo result){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null ;
        try{
            // 对象转为json字符串，再转为json
            String toJSONString = JSONObject.toJSONString(result);
            JSONObject jsonObject = JSONObject.parseObject(toJSONString);
            out = response.getWriter();
            response.getWriter().print(jsonObject);
        }
        catch (Exception e){
            logger.error(e);
        }finally {
            out.close();
        }
    }

}