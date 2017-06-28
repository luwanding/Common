package com.chyjr.platform.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.util.CookiesUtil.java] 
 * @ClassName:    [CookiesUtil]  
 * @Description:  [Cookies 操作工具类]  
 * @Author:       [wangxiaohua]  
 * @CreateDate:   [Oct 14, 2014 4:52:24 PM]  
 * @UpdateUser:   [wangxiaohua]  
 * @UpdateDate:   [Oct 14, 2014 4:52:24 PM]  
 * @UpdateRemark: [] 
 * @Version:      [v1.0]
 *
 */
public class CookiesUtil {

    private final static String HISTORY_PATH = "/"; //Cookie的路径
    private  static int viewNum = 7; //页面显示的个数（默认显示10）
    private final static int TIME = 3600 * 24 * 30; //30天
    /**
     * 清除所有浏览
     * @param request
     * @param response
     * @param cookieName 
     */
    public static void removeAllHistoryCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (null == cookies || cookies.length == 0) {
            return;
        }
        for (Cookie thisCookie : cookies) {
            if (thisCookie.getName().startsWith(cookieName)) {
                thisCookie.setMaxAge(0); // 删除这个cookie
                thisCookie.setPath(HISTORY_PATH);
                response.addCookie(thisCookie);
            }
        }
    }

    /**
     * 清除所有Cookies
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    public static void removeAll(HttpServletRequest request, HttpServletResponse response) {
            Cookie[] cookies = request.getCookies();
            if (null == cookies || cookies.length == 0) {
                return;
            }
            for (Cookie thisCookie : cookies) {
                Cookie cookie = new Cookie(thisCookie.getName(), null);
                cookie.setMaxAge(0); // 删除这个cookie
                cookie.setPath(HISTORY_PATH);
                response.addCookie(cookie);
            }
    }
    
    /**
     * 清除单个浏览记录
     * @param request
     * @param response
     * @param Id
     * @param cookieName
     */
    public static void removeHistoryCookie(HttpServletRequest request, HttpServletResponse response, String Id, String cookieName) {
        if (Id == null) {
            return;
        }
        Cookie[] cookies = request.getCookies();
        if (null == cookies || cookies.length == 0) {
            return;
        }
        for (Cookie thisCookie : cookies) {
            if (thisCookie.getName().equals(cookieName + Id)) {
                thisCookie.setMaxAge(0); // 删除这个cookie
                thisCookie.setPath(HISTORY_PATH);


                response.addCookie(thisCookie);
            }
        }
    }
    
    /**
     * 返回Cookie内容
     * @param cookies
     * @param cookieName
     * @return
     */
    public static String getCookieAll(Cookie[] cookies, String cookieName) {
        String bookid = "";
        if (null == cookieName || null == cookies || cookies.length == 0) {
            return "";
        }
        for (Cookie thisCookie : cookies) {
            if (thisCookie.getName().startsWith(cookieName)) {
                bookid += thisCookie.getValue() + ",";
            }
        }
        return bookid;
    }
    
    /**
     * 设置历史cookie
     * @param response
     * @param Id
     * @param cookieName
     */
    public static void setHistoryCookie(HttpServletResponse response, String Id, String cookieName) {
        if (Id == null) {
            return;
        }
        String nameString = cookieName + Id;
        Cookie cookie = new Cookie(nameString, Id);
        cookie.setPath(HISTORY_PATH);


        if(cookieName.startsWith("record_")){
            cookie.setMaxAge(303);
        }else{
            cookie.setMaxAge(TIME);
        }
        response.addCookie(cookie);
    }

    /**
     * 放入Cookie
     * @param response HttpServletResponse
     * @param
     * @param cookieName String
     */
    public static void setCookie(HttpServletResponse response, String value, String cookieName) {
            if (value == null) {
                return;
            }
            Cookie cookie = new Cookie(cookieName, value);
            cookie.setPath(HISTORY_PATH);
            cookie.setMaxAge(TIME);


            response.addCookie(cookie);
    }
  
    /**
     * 返回Cookie内容
     * @param cookies Cookie[]
     * @param cookieName String
     * @return String
     */
    public static String getCookieValue(Cookie[] cookies, String cookieName) {
        String cookieValue = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return cookieValue;
    }
    
    /**
     * 添加cookies
     * @param request
     * @param response
     * @param Id
     * @param cookieName
     */
    public static void addCookie(HttpServletRequest request, HttpServletResponse response,
                                        String Id, String cookieName) {
        if (Id == null||Id.equals("")) {
            return;
        }
       if(cookieName.startsWith("record_")){
            viewNum = 44;
       }
        String nameString = cookieName + Id;
        Cookie[] cookies = request.getCookies();
        int historyCookieNum = 0; //历史浏览的当前 cookie 数目
        if (null != cookies && cookies.length > 0) {
            for (Cookie thisCookie : cookies) {
                if (thisCookie.getName().startsWith(cookieName)) {
                    historyCookieNum++;
                }
            }
        }
        if (historyCookieNum < viewNum) { //小于显示数目时候
            String flagexist = "no"; //标记cookie中是否存在相同的历史浏览商品
            for (Cookie thisCookie : cookies) {
                if (thisCookie.getName().equalsIgnoreCase(nameString)) { //如果有相同的记录先移去 再加上
                    removeHistoryCookie(request, response, Id, cookieName);
                    setHistoryCookie(response, Id, cookieName);
                    flagexist = "yes";
                    break;
                }
            }
            if (flagexist.equals("no")) { //cookie中不存在则直接加入cookie
                setHistoryCookie(response, Id, cookieName);
            }
        }
        if (historyCookieNum == viewNum) { //等于显示数目的时候移去最旧一个记录 加入新的记录
            List<String> list = new ArrayList<String>(); //存放当前的历史cookie
            for (Cookie thisCookie : cookies) {
                if (thisCookie.getName().startsWith(cookieName)) { //
                    String oldGoodsId = thisCookie.getValue(); //
                    list.add(oldGoodsId);
                }
            }
            if (list.contains(Id)) { //判断（防止详情页面不断刷新调用addcookie方法）
                removeHistoryCookie(request, response, Id, cookieName);
                setHistoryCookie(response, Id, cookieName);
            } else {
                for (Cookie thisCookie : cookies) {
                    if (thisCookie.getName().startsWith(cookieName)) { //去掉第一条旧的历史记录
                        String oldGoodsId = thisCookie.getValue(); //旧ID
                        removeHistoryCookie(request, response, oldGoodsId, cookieName);
                        setHistoryCookie(response, Id, cookieName);
                        break;
                    }
                }
            }
        }
        if (historyCookieNum > viewNum) { //考虑后台重新设置显示数目变小后的情况，历史cookie中存在的数目大于设置显示的数目后要重新获取前viewNum条历史记录
            removeAllHistoryCookie(request, response, cookieName); //先移除所有历史浏览记录
            int addNewNum = 0;
            for (int i = cookies.length - 1; i > 0; i--) {
                if (viewNum == addNewNum) { //获取到设置数目的记录后跳出
                    break;
                }
                if (cookies[i].getName().startsWith(cookieName)) {
                    String getnewgoodsId = cookies[i].getValue();
                    if (null != getnewgoodsId && getnewgoodsId.length() > 0) {
                        setHistoryCookie(response, getnewgoodsId, cookieName);
                        addNewNum++;
                    }
                }
            }
        }
    }
    
    /**
     * 生成jsessionid
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param cookiename String
     * @return String
     */
    public static String getJseesion(HttpServletRequest request,
                                     HttpServletResponse response,String cookiename) {
        Cookie[] coo = request.getCookies();
        String jsessionid = "";
        if(coo!=null&&coo.length>0){
            for (int i = 0; i < coo.length; i++) {
                Cookie c1 = coo[i];
                String name = c1.getName();
                String value = c1.getValue();
                if (cookiename.equals(name)) {
                    jsessionid = value;
                }
            }
        }
        if ("".equals(jsessionid) || jsessionid.length() == 0) {
            UUID uuid = UUID.randomUUID();
            jsessionid = uuid.toString().replaceAll("-", "");
            setCookie(response, jsessionid, cookiename);
        }
        return jsessionid;
    }

}
