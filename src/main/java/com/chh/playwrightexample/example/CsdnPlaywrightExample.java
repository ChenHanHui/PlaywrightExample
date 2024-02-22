package com.chh.playwrightexample.example;

import com.chh.playwrightexample.util.PlaywrightUtils;
import com.microsoft.playwright.Page;

/**
 * @author 一碗情深
 * @CSDN https://blog.csdn.net/xiaohuihui1400
 */
public class CsdnPlaywrightExample {

    public static void main(String[] args) {

        PlaywrightUtils playwrightUtils = new PlaywrightUtils();

        CsdnLoginPage csdnLoginPage = new CsdnLoginPage(playwrightUtils.getPage());

        // 打开CSDN登录页面输入账号密码
        csdnLoginPage.login("test", "123456");

        // 调用接口形式，CSDN搜索数据，'Java' 关键字
        Object searchData = playwrightUtils.getPage().evaluate("() => { return fetch('https://so.csdn.net/api/v3/search?q=Java').then(response => response.json()); }");
        System.out.println(searchData);

        // 调用接口更改数据示例
        // Object updateData = page.evaluate("() => { return fetch('http://localhost:8080/test', { method: 'PUT', body: JSON.stringify({ id: 1, enabled: true }), headers: { 'Content-Type': 'application/json' } }); }");
        // System.out.println(updateData);

        // 创建新页面
        Page newPage = playwrightUtils.createPage();
        // 指定新页面打开的链接
        playwrightUtils.openNew(newPage, "https://blog.csdn.net/xiaohuihui1400");
        // 获取新页面的内容
        String content = playwrightUtils.getContent(newPage);
        System.out.println("==================================================================");
        System.out.println(content);

        // 关闭新页面
        // playwrightUtils.close(newPage);

        // 关闭浏览器
        // playwrightUtils.close();
    }

}
