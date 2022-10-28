<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%--
左边菜单栏
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="layui-side layui-bg-black" id="admin-side">
    <div class="layui-side-scroll" id="admin-navbar-side" lay-filter="side">
        <ul class="layui-nav layui-nav-tree" lay-filter="test">
            <!-- 侧边导航: <ul class="layui-nav layui-nav-tree layui-nav-side"> -->
            <shiro:hasPermission name="article:menu">

            <li class="layui-nav-item layui-nav-itemed">
                <a href="javascript:;"><i class="layui-icon">&#xe614;</i>&nbsp;文章管理</a>
                <dl class="layui-nav-child">
                    <shiro:hasPermission name="article:type">
                        <dd><a href="${ctx}/admin/article/type/list" target="contentIframe">文章分类</a></dd>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="article:list">
                        <dd><a href="${ctx}/admin/article/list" target="contentIframe">文章列表</a></dd>
                    </shiro:hasPermission>
                </dl>
            </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="order:menu">
            <li class="layui-nav-item ">
                <a href="javascript:;"><i class="layui-icon">&#xe614;</i>&nbsp;订单管理</a>
                <dl class="layui-nav-child">
                    <shiro:hasPermission name="order:list">
                        <dd><a href="${ctx}/admin/orders/list" target="contentIframe">订单列表</a></dd>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="order:ticke">
                        <dd><a href="${ctx}/admin/orderTicket/ticket" target="contentIframe">订单核销</a></dd>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="order:refund">
                        <dd><a href="${ctx}/admin/orders/refund" target="contentIframe">订单待退款</a></dd>
                    </shiro:hasPermission>
                </dl>
            </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="spu:menu">
            <li class="layui-nav-item">
                <a href="javascript:;"><i class="layui-icon">&#xe614;</i>&nbsp;商品管理</a>
                <dl class="layui-nav-child">
                    <shiro:hasPermission name="spu:add">
                        <dd><a href="${ctx}/admin/spu/add" target="contentIframe">添加商品</a></dd>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="spu:list">
                        <dd><a href="${ctx}/admin/spu/list" target="contentIframe">商品列表</a></dd>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="spu:type">
                        <dd><a href="${ctx}/admin/cate/list" target="contentIframe">商品分类</a></dd>
                    </shiro:hasPermission>
                    <%--<dd><a href="http://www.baidu.com" target="contentIframe">商品设置</a></dd>--%>
                </dl>
            </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="admin:menu">
            <li class="layui-nav-item">
                <a href="javascript:;"><i class="layui-icon">&#xe614;</i>&nbsp;员工管理</a>
                <dl class="layui-nav-child">
                    <shiro:hasPermission name="admin:add">
                        <dd><a href="${ctx}/admin/add" target="contentIframe">添加员工</a></dd>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="admin:list">
                        <dd><a href="${ctx}/admin/list" target="contentIframe">员工列表</a></dd>
                    </shiro:hasPermission>
                </dl>
            </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="user:menu">
            <li class="layui-nav-item">
                <a href="javascript:;"><i class="layui-icon">&#xe614;</i>&nbsp;用户管理</a>
                <dl class="layui-nav-child">
                    <shiro:hasPermission name="user:website">
                        <dd><a href="${ctx}/admin/user/list" target="contentIframe">网站用户</a></dd>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="user:follow">
                        <dd><a href="${ctx}/admin/user/wxlist" target="contentIframe">关注用户</a></dd>
                    </shiro:hasPermission>
                </dl>
            </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="role:menu">
            <li class="layui-nav-item">
                <a href="javascript:;"><i class="layui-icon">&#xe614;</i>&nbsp;权限管理</a>
                <dl class="layui-nav-child">
                    <shiro:hasPermission name="role:list">
                       <dd><a href="${ctx}/admin/role/list" target="contentIframe">角色列表</a></dd>
                    </shiro:hasPermission>
                </dl>
            </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="ticKet:menu">
            <li class="layui-nav-item">
                <a href="javascript:;"><i class="layui-icon">&#xe614;</i>&nbsp;演出管理</a>
                <dl class="layui-nav-child">
                    <shiro:hasPermission name="ticket:cancel">
                    <dd><a href="/admin/ticketcancel/add" target="contentIframe">取消演出</a></dd>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="ticket:history">
                    <dd><a href="/admin/ticketcancel/list" target="contentIframe">取演历史</a></dd>
                    </shiro:hasPermission>
                </dl>
            </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="echart:menu">
            <li class="layui-nav-item">
                <a href="javascript:;"><i class="layui-icon">&#xe614;</i>&nbsp;统计报表</a>
                <dl class="layui-nav-child">
                    <%--<dd><a href="/admin/echart/fans" target="contentIframe">粉丝统计</a></dd>--%>
                    <shiro:hasPermission name="echart:user">
                         <dd><a href="/admin/echart/users" target="contentIframe">用户统计</a></dd>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="echart:order">
                        <dd><a href="/admin/echart/orders" target="contentIframe">订单统计</a></dd>
                    </shiro:hasPermission>
                </dl>
            </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="setting:menu">
            <li class="layui-nav-item">
                <a href="javascript:;"><i class="layui-icon">&#xe614;</i>&nbsp;系统设置</a>
                <dl class="layui-nav-child">
                    <shiro:hasPermission name="setting:pc">
                        <dd><a href="/admin/setting/set" target="contentIframe">网站设置</a></dd>
                    </shiro:hasPermission>

                    <shiro:hasPermission name="setting:wechat">
                        <dd><a href="/admin/setting/wxpay" target="contentIframe">公众号配置</a></dd>
                    </shiro:hasPermission>
             <%--       <dd><a href="/admin/wechat/menu" target="contentIframe">公众号菜单配置</a></dd>--%>
                    <%--<dd><a href="/admin/setting/sms" target="contentIframe">短信接入</a></dd>
                    <dd><a href="/druid/index.html" target="contentIframe">系统监控</a></dd>--%>
                </dl>
            </li>
            </shiro:hasPermission>
        </ul>
    </div>
</div>
