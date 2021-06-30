package com.weilaizhe.merchant.illegalvisit.Controller;

import com.weilaizhe.common.util.CommonUtil;
import com.weilaizhe.common.util.RedissonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dameizi
 * @description 非法访问
 * @dateTime 2019-05-31 13:39
 * @className IllegalVisitController
 */
@RestController
@RequestMapping("/illegalVisit")
@Api(value="IllegalVisitController", tags="IllegalVisit", description="非法访问")
public class IllegalVisitController {

    /**
     * @author: dameizi
     * @dateTime: 2019-05-31 13:36
     * @description: 非法IP
     * @param: [request]
     * @return: java.lang.Object
     */
    @CrossOrigin
    @RequestMapping(value = "/ip", method = RequestMethod.POST)
    @ApiOperation(value="列表查询银行编码", notes="必填字段：Authorization")
    public Object illegalVisitIP(HttpServletRequest request){
        String remortIP = CommonUtil.getIpAddr(request);
        String ipAllowed = RedissonUtil.getContextMerchantInfo().getIpAllowed();
        if (!StringUtils.isEmpty(ipAllowed)){
            if (!ipAllowed.contains(remortIP)){
                return false;
            }
            return true;
        }else{
            return false;
        }
    }

}
