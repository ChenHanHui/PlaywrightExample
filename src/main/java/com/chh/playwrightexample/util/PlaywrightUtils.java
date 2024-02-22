package com.chh.playwrightexample.util;

import com.chh.playwrightexample.enums.BrowserOptions;
import com.microsoft.playwright.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * @author 一碗情深
 * @CSDN https://blog.csdn.net/xiaohuihui1400
 */
@Slf4j
public class PlaywrightUtils {

    private final Playwright playwright;
    private final Browser browser;
    private final BrowserContext context;
    @Getter
    private Page page;

    public PlaywrightUtils() {
        this.playwright = initPlaywright();
        this.browser = initBrowser(BrowserOptions.chromium, false, 120 * 1000);
        this.context = initContext();
        this.page = createPage();
    }

    public PlaywrightUtils(BrowserOptions browserOptions, boolean headless, double timeout) {
        this.playwright = initPlaywright();
        this.browser = initBrowser(browserOptions, headless, timeout);
        this.context = initContext();
        this.page = createPage();
    }

    /**
     * 初始化Playwright
     */
    private Playwright initPlaywright() {
        try {
            return Playwright.create();
        } catch (Exception e) {
            log.error("初始化playwright失败", e);
            throw new RuntimeException("初始化playwright失败");
        }
    }

    /**
     * 初始化浏览器
     *
     * @param browserOptions 浏览器类型
     * @param headless       是否无头模式启动（默认值是true，即无头模式）
     * @param timeout        打开浏览器超时时间（毫秒）（默认值是 30000，即 30 秒）
     * @return Browser对象
     */
    private Browser initBrowser(BrowserOptions browserOptions, boolean headless, double timeout) {
        try {
            return switch (browserOptions) {
                case firefox ->
                        playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless).setTimeout(timeout));
                case chromium ->
                        playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless).setTimeout(timeout));
                case webkit ->
                        playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless).setTimeout(timeout));
            };
        } catch (Exception e) {
            log.error("create browser error", e);
        }
        return null;
    }

    /**
     * 初始化浏览器上下文
     */
    private BrowserContext initContext() {
        return browser.newContext(new Browser.NewContextOptions()
                .setIgnoreHTTPSErrors(true)
                .setJavaScriptEnabled(true)
                .setViewportSize(1920, 1080)
                .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.130 Safari/537.36"));
    }

    /**
     * 创建页面
     */
    public Page createPage() {
        return context.newPage();
    }

    /**
     * 打开链接
     */
    public void open(String url) {
        page.navigate(url);
    }

    /**
     * 指定页面打开链接
     */
    public void openNew(Page page, String url) {
        page.navigate(url);
    }

    /**
     * 暂停
     */
    public void pause(){
        page.pause();
    }

    /**
     * 后退
     */
    public void goBack(){
        page.goBack();
    }

    /**
     * 前进
     */
    public void goForward(){
        page.goForward();
    }

    /**
     * 刷新
     */
    public void reload(){
        page.reload();
    }

    /**
     * 获取响应内容
     */
    public String getContent() {
        return getContent(page);
    }

    /**
     * 指定页面获取响应内容
     */
    public String getContent(Page page) {
        try {
            return page.content();
        } catch (Exception e) {
            log.error("get content error", e);
        }
        return "";
    }

    /**
     * 关闭指定页面
     */
    public void close(Page... pages) {
        for (Page page : pages) {
            page.close();
        }
    }

    /**
     * 关闭浏览器
     */
    public void close() {
        // 用于关闭当前页面（可选）
        page.close();
        // 用于关闭当前上下文（可选）
        context.close();
        // 用于关闭当前浏览器实例。必须操作的，因为它会关闭整个浏览器实例，包括其中的所有上下文和页面。如果你不关闭浏览器，它可能会一直保持打开状态，占用系统资源。
        browser.close();
        // 关闭 Playwright 实例
        playwright.close();
    }

    /**
     * 拦截请求和响应
     */
    public void handleOn(Page page, Consumer<Request> requestConsumer, Consumer<Response> responseConsumer) {
        page.onRequest(requestConsumer);
        page.onResponse(responseConsumer);
    }
}
