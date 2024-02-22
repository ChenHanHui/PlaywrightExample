package com.chh.playwrightexample.example;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

/**
 * @author 一碗情深
 * @CSDN https://blog.csdn.net/xiaohuihui1400
 */
public class CsdnLoginPage {

    private final Page page;
    private final Locator username;
    private final Locator password;
    private final Locator readConsent;

    private final String loginUrl = "https://passport.csdn.net/login?code=applets";

    public CsdnLoginPage(Page page) {
        this.page = page;
        this.username = page.locator("input[placeholder='手机号/邮箱/用户名']");
        this.password = page.locator("input[placeholder='密码']");
        this.readConsent = page.locator("i.icon.icon-nocheck");
    }

    public void open() {
        page.navigate(loginUrl);
    }

    /**
     * 登录操作
     */
    public void login(String user, String pwd) {
        // 打开网页
        open();

        // 这在“网络空闲”之后处理
        page.waitForLoadState(LoadState.NETWORKIDLE);

        // 获取响应内容
        System.out.println(page.content());

        // 选择密码登录方式
        ElementHandle passwordLoginElement = page.querySelector("span:has-text('密码登录')");
        passwordLoginElement.click();

        // 聚焦用户名输入框
        username.focus();
        // 填充用户名
        username.fill(user);
        // 聚焦密码输入框
        password.focus();
        // 填充密码
        password.fill(pwd);

        // 勾选协议
        readConsent.click();

        // 点击登录按钮
        ElementHandle loginBtnElement = page.evaluateHandle("Array.from(document.querySelectorAll('button')).find(element => element.innerText.includes('登录'))").asElement();
        loginBtnElement.click();

        // 这在“网络空闲”之后处理
        // page.waitForLoadState(LoadState.NETWORKIDLE);

        // 增加等待时间
        // page.waitForTimeout(1000);

        // 当前访问的链接
        // String currentURL = page.url();
        // System.out.println(currentURL);

        // 获取Local storage（本地存储）方式一
        // 获取 HW_ha_analyticsEnabled 值
        // String HWHaAnalyticsEnabled = page.evaluate("() => { return window.localStorage.getItem('HW_ha_analyticsEnabled'); }").toString();
        // System.out.println("localStorage HW_ha_analyticsEnabled value: " + HWHaAnalyticsEnabled);
        // System.out.println("==================================");

        // 获取Local storage（本地存储）方式二
        // 获取 HW_ha_isFirstRun 值
        // String HWHaIsFirstRun = page.evaluate("window.localStorage.getItem('HW_ha_isFirstRun')").toString();
        // System.out.println("localStorage HW_ha_isFirstRun value: " + HWHaIsFirstRun);
        // System.out.println("==================================");

        // 获取当前页面的所有 cookie，并打印出每个 cookie 的名称、值、域名、路径、过期时间、是否安全、是否仅限于 HTTP、SameSite 设置等信息
        // List<Cookie> cookies = page.context().cookies();
        // for (Cookie cookie : cookies) {
        //     System.out.println("Name: " + cookie.name);
        //     System.out.println("Value: " + cookie.value);
        //     System.out.println("Domain: " + cookie.domain);
        //     System.out.println("Path: " + cookie.path);
        //     System.out.println("Expires: " + cookie.expires);
        //     System.out.println("Secure: " + cookie.secure);
        //     System.out.println("HttpOnly: " + cookie.httpOnly);
        //     System.out.println("SameSite: " + cookie.sameSite);
        //     System.out.println("==================================");
        // }
    }
}
