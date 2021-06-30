package com.weilaizhe.payment.cloudflare.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.exception.DescribeException;
import com.weilaizhe.common.util.CommonUtil;
import com.weilaizhe.common.util.CurlUtil;
import com.weilaizhe.payment.pojo.DomainAnalysisVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author dameizi
 * @description 美国云盾
 * @dateTime 2019-06-07 16:45
 * @className com.weilaizhe.payment.cloudflare.controller.CloudflareController
 */
@RestController
@RequestMapping("/cloudflare")
@Api(value = "CloudflareController", tags = "Cloudflare", description = "美国云盾")
public class CloudflareController {

    private static final Logger logger = LoggerFactory.getLogger(CloudflareController.class);

    /** 系统类型 */
    private static String osName = System.getProperty("os.name").toLowerCase();

    /** 解析初始值 */
    private int page = 1;
    private int per_page = 20;
    private String order = "status";
    private String order2 = "type";
    private String direction = "desc";
    private String match = "all";

    @Autowired
    private RedissonClient redissonClient;

    /**
     * @author: dameizi
     * @dateTime: 2019-06-08 20:34
     * @description: 获取账号列表
     * @param: []
     * @return: java.lang.Object
     */
    @CrossOrigin
    @GetMapping("/accountList")
    @ApiOperation(value = "获取账号列表", notes = "无需参数")
    public Object accountList() {
        RMap<String, DomainAnalysisVO> map = redissonClient.getMap("cloudflareAccount");
        List<DomainAnalysisVO> list = new ArrayList<>();
        for (Map.Entry<String, DomainAnalysisVO> entry: map.entrySet()){
            list.add(entry.getValue());
        }
        return list;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-08 20:34
     * @description: 删除账号列表
     * @param: [domainAnalysisVO]
     * @return: java.lang.Object
     */
    @CrossOrigin
    @GetMapping("/accountDelete")
    @ApiOperation(value = "获取账号列表", notes = "必传参数：邮箱名称")
    public Object accountDelete(DomainAnalysisVO domainAnalysisVO) {
        RMap<String, DomainAnalysisVO> map = redissonClient.getMap("cloudflareAccount");
        map.remove(domainAnalysisVO.getEmailAddress());
        return 1;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-08 20:34
     * @description: 添加账号
     * @param: [domainAnalysisVO]
     * @return: java.lang.Object
     */
    @CrossOrigin
    @PostMapping("/insertAccount")
    @ApiOperation(value = "添加账号", notes = "必填参数：账号邮箱、全球API键、账号ID")
    public Object insertAccount(@RequestBody DomainAnalysisVO domainAnalysisVO){
        if (StringUtils.isEmpty(domainAnalysisVO.getEmailAddress())){
            return MessageFormat.format(CommonConst.ERROR, "邮箱不能为空");
        }
        if (StringUtils.isEmpty(domainAnalysisVO.getGlobalAPIKey())){
            return MessageFormat.format(CommonConst.ERROR, "全球API键不能为空");
        }
        if (StringUtils.isEmpty(domainAnalysisVO.getAccountID())){
            return MessageFormat.format(CommonConst.ERROR, "账号ID不能为空");
        }
        RMap<String, DomainAnalysisVO> map = redissonClient.getMap("cloudflareAccount");
        if (map.get(domainAnalysisVO.getEmailAddress()) != null){
            return MessageFormat.format(CommonConst.ERROR, "账号已存在");
        }
        map.put(domainAnalysisVO.getEmailAddress(), domainAnalysisVO);
        return 1;
    }

    @CrossOrigin
    @GetMapping("/pageAlwaysUseHttps")
    @ApiOperation(value = "获取始终使用HTTPS结果", notes = "必填参数：全球API键")
    public Object pageAlwaysUseHttps(DomainAnalysisVO domainAnalysisVO) {
        RList<Object> list = redissonClient.getList("AlwaysUseHttps" + domainAnalysisVO.getGlobalAPIKey());
        return list;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-10 15:51
     * @description: 始终使用HTTPS
     * @param: [file, domainAnalysisVO]
     * @return: java.lang.Object
     */
    @CrossOrigin
    @PostMapping("/alwaysUseHttps")
    @ApiOperation(value = "始终使用HTTPS", notes = "必填参数：账号邮箱、全球API键、域名文件")
    public Object alwaysUseHttps(@RequestParam(value = "file", required = false) MultipartFile file, DomainAnalysisVO domainAnalysisVO) {
        if (file == null){
            return MessageFormat.format(CommonConst.ERROR, "域名文件不能为空");
        }
        try {
            InputStream inputStream = file.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(inputStreamReader);
            BufferedInputStream bufferedInputStream = null;
            String line;
            int count = 1;
            // 一次读入一行数据
            while ((line = br.readLine()) != null) {
                if (count > 1){
                    if (!StringUtils.isEmpty(domainAnalysisVO.getBufferTime())){
                        Thread.sleep(domainAnalysisVO.getBufferTime());
                    }
                }
                line = line.trim();
                logger.info(MessageFormat.format("始终使用HTTPS处理第{0}行域名{1}", count, line));
                // 获取ZoneID
                String zoneID = getZoneID(domainAnalysisVO, line);
                if (!zoneID.equals("")){
                    // 开启HTTPS
                    String curl;
                    if (osName.contains("windows")){
                        String action = "curl -X PATCH -H \"X-Auth-Key:{0}\" -H \"X-Auth-Email:{1}\" -H \"Content-Type: application/json\" \"https://api.cloudflare.com/client/v4/zones/{2}/settings/always_use_https\" --data '{\\\"value\\\":\\\"'{3}'\\\"}'";
                        curl = MessageFormat.format(action, domainAnalysisVO.getGlobalAPIKey(), domainAnalysisVO.getEmailAddress(), zoneID, domainAnalysisVO.getValue());
                        curl = curl.replace("data {\\\"","data \"{\\\"");
                        curl = curl.replace("}","}\"");
                    }else{
                        String action = "curl -X PATCH -H \"X-Auth-Key:{0}\" -H \"X-Auth-Email:{1}\" -H \"Content-Type: application/json\" \"https://api.cloudflare.com/client/v4/zones/{2}/settings/always_use_https\" --data '{\"value\":\"'{3}'\"}'";
                        curl = MessageFormat.format(action, domainAnalysisVO.getGlobalAPIKey(), domainAnalysisVO.getEmailAddress(), zoneID, domainAnalysisVO.getValue());
                        curl = curl.replace("data {\"","data \'{\"");
                        curl = curl.replace("}","}\'");
                    }
                    ProcessBuilder pb = new ProcessBuilder(CurlUtil.getOsCmd(curl));
                    pb.redirectErrorStream(true);
                    Process p = pb.start();
                    bufferedInputStream = new BufferedInputStream(p.getInputStream());
                    String result = IOUtils.toString(bufferedInputStream,"gb2312");
                    p.waitFor();
                    p.destroy();
                    // 成功才返回result
                    if (result.contains("{\"result\":{")){
                        result = result.substring(result.indexOf("{\"result\":{"),result.length());
                    }else {
                        result = result.substring(result.indexOf("{\"success\":"),result.length());
                    }
                    String resultMessage = MessageFormat.format("始终使用HTTPS处理第{0}行域名{1}结果{2}", count, line, result);
                    RList<Object> list = redissonClient.getList("AlwaysUseHttps" + domainAnalysisVO.getGlobalAPIKey());
                    list.add(resultMessage);
                    list.expire(24, TimeUnit.HOURS);
                    logger.info(resultMessage);
                }else{
                    logger.info(MessageFormat.format("始终使用HTTPS处理第{0}行域名{1}不存在", count, line));
                    continue;
                }
                count++;
            }
            if(null != bufferedInputStream){
                bufferedInputStream.close();
            }
        }catch (Exception e){
            return MessageFormat.format(CommonConst.ERROR, "处理异常");
        }
        return "处理中，耐心等待";
    }

    @CrossOrigin
    @GetMapping("/pageBatchDomainDelete")
    @ApiOperation(value = "获取批量域名删除结果", notes = "必填参数：全球API键")
    public Object pageBatchDomainDelete(DomainAnalysisVO domainAnalysisVO) {
        RList<Object> list = redissonClient.getList("DomainDelete" + domainAnalysisVO.getGlobalAPIKey());
        return list;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-08 13:27
     * @description: 批量域名删除
     * @param: [file, domainAnalysisVO]
     * @return: java.lang.Object
     */
    @CrossOrigin
    @PostMapping("/batchDomainDelete")
    @ApiOperation(value = "批量域名删除", notes = "必填参数：账号邮箱、全球API键、域名文件")
    public Object batchDomainDelete(@RequestParam(value = "file", required = false) MultipartFile file, DomainAnalysisVO domainAnalysisVO){
        if (file == null){
            return MessageFormat.format(CommonConst.ERROR, "域名文件不能为空");
        }
        try {
            InputStream inputStream = file.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(inputStreamReader);
            BufferedInputStream bufferedInputStream = null;
            String line;
            int count = 1;
            // 一次读入一行数据
            while ((line = br.readLine()) != null) {
                if (count > 1){
                    if (!StringUtils.isEmpty(domainAnalysisVO.getBufferTime())){
                        Thread.sleep(domainAnalysisVO.getBufferTime());
                    }
                }
                line = line.trim();
                logger.info(MessageFormat.format("批量域名删除处理第{0}行域名{1}", count, line));
                // 获取ZoneID
                String zoneID = getZoneID(domainAnalysisVO, line);
                if (!zoneID.equals("")){
                    // 操作删除
                    String action = "curl -X DELETE -H \"X-Auth-Key:{0}\" -H \"X-Auth-Email:{1}\" -H \"Content-Type: application/json\" \"https://api.cloudflare.com/client/v4/zones/{2}\"";
                    String curl = MessageFormat.format(action, domainAnalysisVO.getGlobalAPIKey(), domainAnalysisVO.getEmailAddress(), zoneID);
                    ProcessBuilder pb = new ProcessBuilder(CurlUtil.getOsCmd(curl));
                    pb.redirectErrorStream(true);
                    Process p = pb.start();
                    bufferedInputStream = new BufferedInputStream(p.getInputStream());
                    String result = IOUtils.toString(bufferedInputStream,"gb2312");
                    p.waitFor();
                    p.destroy();
                    // 成功才返回result
                    if (result.contains("{\"result\":{")){
                        result = result.substring(result.indexOf("{\"result\":{"),result.length());
                    }else {
                        result = result.substring(result.indexOf("{\"success\":"),result.length());
                    }
                    String resultMessage = MessageFormat.format("批量域名删除处理第{0}行域名{1}结果{2}", count, line, result);
                    RList<Object> list = redissonClient.getList("DomainDelete" + domainAnalysisVO.getGlobalAPIKey());
                    list.add(resultMessage);
                    list.expire(24, TimeUnit.HOURS);
                    logger.info(resultMessage);
                }else{
                    logger.info(MessageFormat.format("批量域名删除处理第{0}行域名{1}不存在", count, line));
                    continue;
                }
                count++;
            }
            if(null != bufferedInputStream){
                bufferedInputStream.close();
            }
        }catch (Exception e){
            return MessageFormat.format(CommonConst.ERROR, "处理异常");
        }
        return "处理中，耐心等待";
    }


    @CrossOrigin
    @GetMapping("/pageBatchAnalysisDelete")
    @ApiOperation(value = "批量解析删除结果", notes = "必填参数：全球API键")
    public Object pageBatchAnalysisDelete(DomainAnalysisVO domainAnalysisVO) {
        RList<Object> list = redissonClient.getList("DeleteDNSRecord" + domainAnalysisVO.getGlobalAPIKey());
        return list;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-08 13:27
     * @description: 批量解析删除
     * @param: [file, domainAnalysisVO]
     * @return: java.lang.Object
     */
    @CrossOrigin
    @PostMapping("/batchAnalysisDelete")
    @ApiOperation(value = "批量解析删除", notes = "必填参数：账号邮箱、全球API键、域名文件")
    public Object batchAnalysisDelete(@RequestParam(value = "file", required = false) MultipartFile file, DomainAnalysisVO domainAnalysisVO){
        if (file == null){
            return MessageFormat.format(CommonConst.ERROR, "域名文件不能为空");
        }
        try {
            InputStream inputStream = file.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(inputStreamReader);
            BufferedInputStream bufferedInputStream = null;
            String line;
            int count = 1;
            // 一次读入一行数据
            while ((line = br.readLine()) != null) {
                if (count > 1){
                    if (!StringUtils.isEmpty(domainAnalysisVO.getBufferTime())){
                        Thread.sleep(domainAnalysisVO.getBufferTime());
                    }
                }
                line = line.trim();
                logger.info(MessageFormat.format("批量解析删除处理第{0}行域名{1}", count, line));
                // 获取ZoneID
                String zoneID;
                // 从域名倒数第二个点截取
                String site = substringSite(line.trim());
                if ("".equals(domainAnalysisVO.getStatus())){
                    zoneID = getZoneID(domainAnalysisVO, site);
                }else{
                    zoneID = queryZoneID(domainAnalysisVO, site);
                }
                if (!zoneID.equals("")){
                    // 获取identifier
                    List<String> identifierList = getIdentifierListDelete(domainAnalysisVO, zoneID);
                    for (int i=0; i<identifierList.size(); i++){
                        // 操作删除
                        String action = "curl -X DELETE -H \"X-Auth-Key:{0}\" -H \"X-Auth-Email:{1}\" -H \"Content-Type: application/json\" \"https://api.cloudflare.com/client/v4/zones/{2}/dns_records/{3}\"";
                        String curl = MessageFormat.format(action, domainAnalysisVO.getGlobalAPIKey(), domainAnalysisVO.getEmailAddress(), zoneID, identifierList.get(i));
                        ProcessBuilder pb = new ProcessBuilder(CurlUtil.getOsCmd(curl));
                        pb.redirectErrorStream(true);
                        Process p = pb.start();
                        bufferedInputStream = new BufferedInputStream(p.getInputStream());
                        String result = IOUtils.toString(bufferedInputStream,"gb2312");
                        p.waitFor();
                        p.destroy();
                        // 成功才返回result
                        if (result.contains("{\"result\":{")){
                            result = result.substring(result.indexOf("{\"result\":{"),result.length());
                        }else {
                            result = result.substring(result.indexOf("{\"success\":"),result.length());
                        }
                        String resultMessage = MessageFormat.format("批量解析删除处理第{0}行域名{1}结果{2}", count, line, result);
                        RList<Object> list = redissonClient.getList("DeleteDNSRecord" + domainAnalysisVO.getGlobalAPIKey());
                        list.add(resultMessage);
                        list.expire(24, TimeUnit.HOURS);
                        logger.info(resultMessage);
                    }
                }else{
                    logger.info(MessageFormat.format("批量解析删除处理第{0}行域名{1}不存在", count, line));
                    continue;
                }
                count++;
            }
            if(null != bufferedInputStream){
                bufferedInputStream.close();
            }
        }catch (Exception e){
            return MessageFormat.format(CommonConst.ERROR, "处理异常");
        }
        return "处理中，耐心等待";
    }

    private List<String> getIdentifierListDelete(DomainAnalysisVO domainAnalysisVO, String zoneID) throws IOException, InterruptedException {
        // 获取identifier
        BufferedInputStream bufferedInputStream;
        String curl;
        if ("".equals(domainAnalysisVO.getType())){
            String action = "curl -X GET -H \"X-Auth-Key:{0}\" -H \"X-Auth-Email:{1}\" -H \"Content-Type: application/json\" \"https://api.cloudflare.com/client/v4/zones/{2}/dns_records\"";
            curl = MessageFormat.format(action, domainAnalysisVO.getGlobalAPIKey(), domainAnalysisVO.getEmailAddress(), zoneID);
        }else{
            String action = "curl -X GET -H \"X-Auth-Key:{0}\" -H \"X-Auth-Email:{1}\" -H \"Content-Type: application/json\" \"https://api.cloudflare.com/client/v4/zones/{2}/dns_records?type={3}\"";
            curl = MessageFormat.format(action, domainAnalysisVO.getGlobalAPIKey(), domainAnalysisVO.getEmailAddress(), zoneID, domainAnalysisVO.getType());
        }
        ProcessBuilder pb = new ProcessBuilder(CurlUtil.getOsCmd(curl));
        pb.redirectErrorStream(true);
        Process p = pb.start();
        bufferedInputStream = new BufferedInputStream(p.getInputStream());
        String result = IOUtils.toString(bufferedInputStream,"gb2312");
        p.waitFor();
        p.destroy();
        if (result.contains("{\"result\":[")){
            result = result.substring(result.indexOf("{\"result\":["),result.length());
        }else{
            throw new DescribeException("获取解析id失败"+result , -1);
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray jsonArray = (JSONArray)jsonObject.get("result");
        List<String> list = new ArrayList<String>();
        for (int i=0; i<jsonArray.size(); i++){
            list.add((String) ((JSONObject) jsonArray.get(i)).get("id"));
        }
        if(null != bufferedInputStream){
            bufferedInputStream.close();
        }
        return list;
    }

    @CrossOrigin
    @GetMapping("/pageBatchAnalysisUpdate")
    @ApiOperation(value = "获取批量解析更新结果", notes = "必填参数：全球API键")
    public Object pageBatchAnalysisUpdate(DomainAnalysisVO domainAnalysisVO) {
        RList<Object> list = redissonClient.getList("UpdateDNSRecord" + domainAnalysisVO.getGlobalAPIKey());
        return list;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-08 13:16
     * @description: 批量解析更新
     * @param: [file, domainAnalysisVO]
     * @return: java.lang.Object
     */
    @CrossOrigin
    @PostMapping("/batchAnalysisUpdate")
    @ApiOperation(value = "批量解析更新", notes = "必填参数：记录类型、服务器IP、地区ID、域名文件")
    public Object batchAnalysisUpdate(@RequestParam(value = "file", required = false) MultipartFile file, DomainAnalysisVO domainAnalysisVO){
        if (file == null){
            return MessageFormat.format(CommonConst.ERROR, "域名文件不能为空");
        }
        if (!CommonUtil.ipCheck(domainAnalysisVO.getContent())){
            return MessageFormat.format(CommonConst.ERROR, "服务器IP格式错误");
        }
        try {
            InputStream inputStream = file.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(inputStreamReader);
            BufferedInputStream bufferedInputStream = null;
            String line;
            int count = 1;
            // 一次读入一行数据
            while ((line = br.readLine()) != null) {
                if (count > 1){
                    if (!StringUtils.isEmpty(domainAnalysisVO.getBufferTime())){
                        Thread.sleep(domainAnalysisVO.getBufferTime());
                    }
                }
                line = line.trim();
                logger.info(MessageFormat.format("批量解析更新处理第{0}行域名{1}", count, line));
                // 获取ZoneID
                String zoneID;
                String site = substringSite(line.trim());
                if ("".equals(domainAnalysisVO.getStatus())){
                    zoneID = getZoneID(domainAnalysisVO, site);
                }else{
                    zoneID = queryZoneID(domainAnalysisVO, site);
                }
                if (!zoneID.equals("")){
                    // 获取identifier
                    List<String> identifierList = getIdentifierList(domainAnalysisVO, zoneID, line);
                    for (int i=0; i<identifierList.size(); i++){
                        // 操作更新
                        String curl;
                        if (osName.contains("windows")){
                            String action = "curl -X PUT -H \"X-Auth-Key:{0}\" -H \"X-Auth-Email:{1}\" -H \"Content-Type: application/json\" \"https://api.cloudflare.com/client/v4/zones/{2}/dns_records/{3}\" --data '{\\\"type\\\":\\\"'{4}'\\\",\\\"name\\\":\\\"'{5}'\\\",\\\"content\\\":\\\"'{6}'\\\",\\\"ttl\\\":'{7}',\\\"proxied\\\":'{8}'}'";
                            curl = MessageFormat.format(action, domainAnalysisVO.getGlobalAPIKey(), domainAnalysisVO.getEmailAddress(), zoneID, identifierList.get(i), domainAnalysisVO.getType(), line, domainAnalysisVO.getContent(), domainAnalysisVO.getTtl(), domainAnalysisVO.getProxied());
                            curl = curl.replace("data {\\\"","data \"{\\\"");
                            curl = curl.replace("}","}\"");
                        }else{
                            String action = "curl -X PUT -H \"X-Auth-Key:{0}\" -H \"X-Auth-Email:{1}\" -H \"Content-Type: application/json\" \"https://api.cloudflare.com/client/v4/zones/{2}/dns_records/{3}\" --data '{\"type\":\"'{4}'\",\"name\":\"'{5}'\",\"content\":\"'{6}'\",\"ttl\":'{7}',\"proxied\":'{8}'}'";
                            curl = MessageFormat.format(action, domainAnalysisVO.getGlobalAPIKey(), domainAnalysisVO.getEmailAddress(), zoneID, identifierList.get(i), domainAnalysisVO.getType(), line, domainAnalysisVO.getContent(), domainAnalysisVO.getTtl(), domainAnalysisVO.getProxied());
                            curl = curl.replace("data {\"","data \'{\"");
                            curl = curl.replace("}","}\'");
                        }
                        ProcessBuilder pb = new ProcessBuilder(CurlUtil.getOsCmd(curl));
                        pb.redirectErrorStream(true);
                        Process p = pb.start();
                        bufferedInputStream = new BufferedInputStream(p.getInputStream());
                        String result = IOUtils.toString(bufferedInputStream,"gb2312");
                        p.waitFor();
                        p.destroy();
                        // 成功才返回result
                        if (result.contains("{\"result\":{")){
                            result = result.substring(result.indexOf("{\"result\":{"),result.length());
                        }else {
                            result = result.substring(result.indexOf("{\"success\":"),result.length());
                        }
                        String resultMessage = MessageFormat.format("批量解析更新处理第{0}行域名{1}结果{2}", count, line, result);
                        RList<Object> list = redissonClient.getList("UpdateDNSRecord" + domainAnalysisVO.getGlobalAPIKey());
                        list.add(resultMessage);
                        list.expire(24, TimeUnit.HOURS);
                        logger.info(resultMessage);
                    }
                }else{
                    logger.info(MessageFormat.format("批量解析更新处理第{0}行域名{1}不存在", count, line));
                    continue;
                }
                count++;
            }
            if(null != bufferedInputStream){
                bufferedInputStream.close();
            }
        }catch (Exception e){
            return MessageFormat.format(CommonConst.ERROR, "处理异常");
        }
        return "处理中，耐心等待";
    }

    private List<String> getIdentifierList(DomainAnalysisVO domainAnalysisVO, String zoneID, String line) throws IOException, InterruptedException {
        // 获取identifier
        BufferedInputStream bufferedInputStream;
        String action = "curl -X GET -H \"X-Auth-Key:{0}\" -H \"X-Auth-Email:{1}\" -H \"Content-Type: application/json\" \"https://api.cloudflare.com/client/v4/zones/{2}/dns_records?type={3}&name={4}\"";
        String curl = MessageFormat.format(action, domainAnalysisVO.getGlobalAPIKey(), domainAnalysisVO.getEmailAddress(), zoneID, domainAnalysisVO.getType(), line);
        ProcessBuilder pb = new ProcessBuilder(CurlUtil.getOsCmd(curl));
        pb.redirectErrorStream(true);
        Process p = pb.start();
        bufferedInputStream = new BufferedInputStream(p.getInputStream());
        String result = IOUtils.toString(bufferedInputStream,"gb2312");
        p.waitFor();
        p.destroy();
        if (result.contains("{\"result\":[")){
            result = result.substring(result.indexOf("{\"result\":["),result.length());
        }else{
            throw new DescribeException("获取解析id失败"+result , -1);
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray jsonArray = (JSONArray)jsonObject.get("result");
        List<String> list = new ArrayList<String>();
        for (int i=0; i<jsonArray.size(); i++){
            list.add((String) ((JSONObject) jsonArray.get(i)).get("id"));
        }
        if(null != bufferedInputStream){
            bufferedInputStream.close();
        }
        return list;
    }

    @CrossOrigin
    @GetMapping("/pageBatchAnalysis")
    @ApiOperation(value = "获取批量解析域名结果", notes = "必填参数：全球API键")
    public Object pageBatchAnalysis(DomainAnalysisVO domainAnalysisVO) {
        RList<Object> list = redissonClient.getList("CreateDNSRecord" + domainAnalysisVO.getGlobalAPIKey());
        return list;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-07 21:50
     * @description: 批量解析域名
     * @param: [file, domainAnalysisVO]
     * @return: java.lang.Object
     */
    @CrossOrigin
    @PostMapping("/batchAnalysisDomain")
    @ApiOperation(value = "批量解析域名", notes = "必填参数：账号邮箱、全球API键、账号ID、记录类型、服务器IP、状态、域名文件")
    public Object batchAnalysisDomain(@RequestParam(value = "file", required = false) MultipartFile file, DomainAnalysisVO domainAnalysisVO){
        if (file == null){
            return MessageFormat.format(CommonConst.ERROR, "域名文件不能为空");
        }
        if (!CommonUtil.ipCheck(domainAnalysisVO.getContent())){
            return MessageFormat.format(CommonConst.ERROR, "服务器IP格式错误");
        }
        try {
            InputStream inputStream = file.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(inputStreamReader);
            BufferedInputStream bufferedInputStream = null;
            String line;
            int count = 1;
            // 一次读入一行数据
            while ((line = br.readLine()) != null) {
                if (count > 1){
                    if (!StringUtils.isEmpty(domainAnalysisVO.getBufferTime())){
                        Thread.sleep(domainAnalysisVO.getBufferTime());
                    }
                }
                line = line.trim();
                logger.info(MessageFormat.format("批量解析处理第{0}行域名{1}", count, line));
                // 获取ZoneID
                String zoneID;
                // 从域名倒数第二个点截取
                String site = substringSite(line.trim());
                if ("".equals(domainAnalysisVO.getStatus())){
                    zoneID = getZoneID(domainAnalysisVO, site);
                }else{
                    zoneID = queryZoneID(domainAnalysisVO, site);
                }
                if (!zoneID.equals("")){
                    // 解析操作
                    String curl;
                    if (osName.contains("windows")){
                        String action = "curl -X POST -H \"X-Auth-Key:{0}\" -H \"X-Auth-Email:{1}\" -H \"Content-Type: application/json\" \"https://api.cloudflare.com/client/v4/zones/{2}/dns_records\" --data '{\\\"type\\\":\\\"'{3}'\\\",\\\"name\\\":\\\"'{4}'\\\",\\\"content\\\":\\\"'{5}'\\\",\\\"ttl\\\":'{6}',\\\"priority\\\":'{7}',\\\"proxied\\\":'{8}'}'";
                        curl = MessageFormat.format(action, domainAnalysisVO.getGlobalAPIKey(), domainAnalysisVO.getEmailAddress(), zoneID, domainAnalysisVO.getType(), line, domainAnalysisVO.getContent(), domainAnalysisVO.getTtl(), domainAnalysisVO.getPriority(), domainAnalysisVO.getProxied());
                        curl = curl.replace("data {\\\"","data \"{\\\"");
                        curl = curl.replace("}","}\"");
                    }else{
                        String action = "curl -X POST -H \"X-Auth-Key:{0}\" -H \"X-Auth-Email:{1}\" -H \"Content-Type: application/json\" \"https://api.cloudflare.com/client/v4/zones/{2}/dns_records\" --data '{\"type\":\"'{3}'\",\"name\":\"'{4}'\",\"content\":\"'{5}'\",\"ttl\":'{6}',\"priority\":'{7}',\"proxied\":'{8}'}'";
                        curl = MessageFormat.format(action, domainAnalysisVO.getGlobalAPIKey(), domainAnalysisVO.getEmailAddress(), zoneID, domainAnalysisVO.getType(), line, domainAnalysisVO.getContent(), domainAnalysisVO.getTtl(), domainAnalysisVO.getPriority(), domainAnalysisVO.getProxied());
                        curl = curl.replace("data {\"","data \'{\"");
                        curl = curl.replace("}","}\'");
                    }
                    ProcessBuilder pb = new ProcessBuilder(CurlUtil.getOsCmd(curl));
                    pb.redirectErrorStream(true);
                    Process p = pb.start();
                    bufferedInputStream = new BufferedInputStream(p.getInputStream());
                    String result = IOUtils.toString(bufferedInputStream,"gb2312");
                    p.waitFor();
                    p.destroy();
                    // 成功才返回result
                    if (result.contains("{\"result\":{")){
                        result = result.substring(result.indexOf("{\"result\":{"),result.length());
                    }else {
                        result = result.substring(result.indexOf("{\"success\":"),result.length());
                    }
                    String resultMessage = MessageFormat.format("批量解析处理第{0}行域名{1}结果{2}", count, line, result);
                    RList<Object> list = redissonClient.getList("CreateDNSRecord" + domainAnalysisVO.getGlobalAPIKey());
                    list.add(resultMessage);
                    list.expire(24, TimeUnit.HOURS);
                    logger.info(resultMessage);
                }else{
                    logger.info(MessageFormat.format("批量解析处理第{0}行域名{1}不存在", count, line));
                    continue;
                }
                count++;
            }
            if(null != bufferedInputStream){
                bufferedInputStream.close();
            }
        }catch (Exception e){
            return MessageFormat.format(CommonConst.ERROR, "处理异常");
        }
        return "处理中，耐心等待";
    }

    private String substringSite(String line) {
        return line.substring(line.substring(0,line.lastIndexOf(".")).lastIndexOf(".")+1);
    }

    private String getZoneID(DomainAnalysisVO domainAnalysisVO, String line) throws IOException, InterruptedException {
        // 获取zoneID
        BufferedInputStream bufferedInputStream;
        String action = "curl -X GET -H \"X-Auth-Key:{0}\" -H \"X-Auth-Email:{1}\" -H \"Content-Type: application/json\" \"https://api.cloudflare.com/client/v4/zones?name={2}&account.id={3}&account.name={4}\"";
        String curl = MessageFormat.format(action, domainAnalysisVO.getGlobalAPIKey(), domainAnalysisVO.getEmailAddress(), line, domainAnalysisVO.getAccountID(), domainAnalysisVO.getEmailAddress());
        ProcessBuilder pb = new ProcessBuilder(CurlUtil.getOsCmd(curl));
        pb.redirectErrorStream(true);
        Process p = pb.start();
        bufferedInputStream = new BufferedInputStream(p.getInputStream());
        String result = IOUtils.toString(bufferedInputStream,"gb2312");
        p.waitFor();
        p.destroy();
        String zoneID;
        if (result.contains("{\"result\":[{")){
            result = result.substring(result.indexOf("{\"result\":[{"),result.length());
            JSONObject jsonObject = JSONObject.parseObject(result);
            JSONArray jsonArray = (JSONArray)jsonObject.get("result");
            zoneID = (String)((JSONObject) jsonArray.get(0)).get("id");
        }else{
            zoneID = "";
        }
        if(null != bufferedInputStream){
            bufferedInputStream.close();
        }
        return zoneID;
    }

    private String queryZoneID(DomainAnalysisVO domainAnalysisVO, String line) throws IOException, InterruptedException {
        // 获取zoneID
        BufferedInputStream bufferedInputStream;
        String action = "curl -X GET -H \"X-Auth-Key:{0}\" -H \"X-Auth-Email:{1}\" -H \"Content-Type: application/json\" \"https://api.cloudflare.com/client/v4/zones?name={2}&status={3}&account.id={4}&account.name={5}&page={6}&per_page={7}&order={8}&direction={9}&match={10}\"";
        String curl = MessageFormat.format(action, domainAnalysisVO.getGlobalAPIKey(), domainAnalysisVO.getEmailAddress(), line, domainAnalysisVO.getStatus(), domainAnalysisVO.getAccountID(), domainAnalysisVO.getEmailAddress(), page, per_page, order, direction, match);
        ProcessBuilder pb = new ProcessBuilder(CurlUtil.getOsCmd(curl));
        pb.redirectErrorStream(true);
        Process p = pb.start();
        bufferedInputStream = new BufferedInputStream(p.getInputStream());
        String result = IOUtils.toString(bufferedInputStream,"gb2312");
        p.waitFor();
        p.destroy();
        String zoneID;
        if (result.contains("{\"result\":[{")){
            result = result.substring(result.indexOf("{\"result\":[{"),result.length());
            JSONObject jsonObject = JSONObject.parseObject(result);
            JSONArray jsonArray = (JSONArray)jsonObject.get("result");
            zoneID = (String)((JSONObject) jsonArray.get(0)).get("id");
        }else{
            zoneID = "";
        }
        if(null != bufferedInputStream){
            bufferedInputStream.close();
        }
        return zoneID;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-07 20:37
     * @description: 获取批量添加结果
     * @param: [domainAnalysisVO]
     * @return: java.lang.Object
     */
    @CrossOrigin
    @GetMapping("/pageAddDomain")
    @ApiOperation(value = "获取批量添加结果", notes = "必填参数：全球API键")
    public Object batchAddDomain(DomainAnalysisVO domainAnalysisVO) {
        RList<Object> list = redissonClient.getList("domain" + domainAnalysisVO.getGlobalAPIKey());
        return list;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-07 19:00
     * @description: 批量添加域名
     * @param: [file, domainAnalysisVO]
     * @return: java.lang.Object
     */
    @CrossOrigin
    @PostMapping("/batchAddDomain")
    @ApiOperation(value = "批量添加域名", notes = "必填参数：账号邮箱、全球API键、账号ID、域名文件")
    public Object batchAddDomain(@RequestParam(value = "file", required = false) MultipartFile file, DomainAnalysisVO domainAnalysisVO){
        if (file == null){
            return MessageFormat.format(CommonConst.ERROR, "域名文件不能为空");
        }
        try {
            InputStream inputStream = file.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(inputStreamReader);
            BufferedInputStream bufferedInputStream = null;
            String line;
            int count = 1;
            // 一次读入一行数据
            while ((line = br.readLine()) != null) {
                if (count > 1){
                    if (!StringUtils.isEmpty(domainAnalysisVO.getBufferTime())){
                        Thread.sleep(domainAnalysisVO.getBufferTime());
                    }
                }
                line = line.trim();
                logger.info(MessageFormat.format("批量添加处理第{0}行域名{1}", count, line));
                String curl;
                if (osName.contains("windows")){
                    String action = "curl -X POST -H \"X-Auth-Key:{0}\" -H \"X-Auth-Email:{1}\" -H \"Content-Type: application/json\" \"https://api.cloudflare.com/client/v4/zones\" --data '{\\\"account\\\": {\\\"id\\\": \\\"'{2}'\\\"}, \\\"name\\\":\\\"'{3}'\\\",\\\"jump_start\\\":true}'";
                    curl = MessageFormat.format(action, domainAnalysisVO.getGlobalAPIKey(), domainAnalysisVO.getEmailAddress(), domainAnalysisVO.getAccountID(), line);
                    curl = curl.replace("data {\\\"","data \"{\\\"");
                    curl = curl.replace(":true}",":true}\"");
                }else{
                    String action = "curl -X POST -H \"X-Auth-Key:{0}\" -H \"X-Auth-Email:{1}\" -H \"Content-Type: application/json\" \"https://api.cloudflare.com/client/v4/zones\" --data '{\"account\": {\"id\": \"'{2}'\"}, \"name\":\"'{3}'\",\"jump_start\":true}'";
                    curl = MessageFormat.format(action, domainAnalysisVO.getGlobalAPIKey(), domainAnalysisVO.getEmailAddress(), domainAnalysisVO.getAccountID(), line);
                    curl = curl.replace("data {\"","data \'{\"");
                    curl = curl.replace(":true}",":true}\'");
                    logger.info("批量添加域名URL:{}", curl);
                }
                ProcessBuilder pb = new ProcessBuilder(CurlUtil.getOsCmd(curl));
                pb.redirectErrorStream(true);
                Process p = pb.start();
                bufferedInputStream = new BufferedInputStream(p.getInputStream());
                String result = IOUtils.toString(bufferedInputStream,"gb2312");
                p.waitFor();
                p.destroy();
                count++;
                // 成功才返回result
                if (result.contains("{\"result\":{")){
                    result = result.substring(result.indexOf("{\"result\":{"),result.length());
                }else {
                    result = result.substring(result.indexOf("{\"success\":"),result.length());
                }
                String resultMessage = MessageFormat.format("批量添加处理第{0}行域名{1}结果{2}", count, line, result);
                RList<Object> list = redissonClient.getList("domain" + domainAnalysisVO.getGlobalAPIKey());
                list.add(resultMessage);
                list.expire(24, TimeUnit.HOURS);
                logger.info(resultMessage);
            }
            if(null != bufferedInputStream){
                bufferedInputStream.close();
            }
        }catch (Exception e){
            return MessageFormat.format(CommonConst.ERROR, "处理异常");
        }
        return "处理中，耐心等待";
    }

    // 获取单个不可用
    private String getIdentifier(DomainAnalysisVO domainAnalysisVO, String zoneID, String line) throws IOException, InterruptedException {
        // 获取单个identifier
        BufferedInputStream bufferedInputStream;
        // 多条件查询，注意IP
        //String action = "curl -X GET -H \"X-Auth-Key:{0}\" -H \"X-Auth-Email:{1}\" -H \"Content-Type: application/json\" \"https://api.cloudflare.com/client/v4/zones/{2}/dns_records?type={3}&name={4}&content={5}&page={6}&per_page={7}&order={8}&direction={9}&match={10}\"";
        //String curl = MessageFormat.format(action, domainAnalysisVO.getGlobalAPIKey(), domainAnalysisVO.getEmailAddress(), zoneID, domainAnalysisVO.getType(), line, domainAnalysisVO.getContent(), page, per_page, order2, direction, match);
        String action = "curl -X GET -H \"X-Auth-Key:{0}\" -H \"X-Auth-Email:{1}\" -H \"Content-Type: application/json\" \"https://api.cloudflare.com/client/v4/zones/{2}/dns_records?type={3}&name={4}&page={6}&per_page={7}&order={8}&direction={9}&match={10}\"";
        String curl = MessageFormat.format(action, domainAnalysisVO.getGlobalAPIKey(), domainAnalysisVO.getEmailAddress(), zoneID, domainAnalysisVO.getType(), line, page, per_page, order2, direction, match);
        ProcessBuilder pb = new ProcessBuilder(CurlUtil.getOsCmd(curl));
        pb.redirectErrorStream(true);
        Process p = pb.start();
        bufferedInputStream = new BufferedInputStream(p.getInputStream());
        String result = IOUtils.toString(bufferedInputStream,"gb2312");
        p.waitFor();
        p.destroy();
        if (result.contains("{\"result\":[")){
            result = result.substring(result.indexOf("{\"result\":["),result.length());
        }else{
            throw new DescribeException("获取解析id失败"+result , -1);
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray jsonArray = (JSONArray)jsonObject.get("result");
        if(null != bufferedInputStream){
            bufferedInputStream.close();
        }
        return (String) ((JSONObject) jsonArray.get(0)).get("id");
    }

}
