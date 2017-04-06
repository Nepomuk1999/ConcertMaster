<%@ tag description="main page" pageEncoding="UTF-8" %>
<%@ attribute name="includes" fragment="true" %>
<%@ attribute name="content" fragment="true" required="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Plan</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/stylesheets/main.css" />
    <script src='${pageContext.request.contextPath}/assets/javascripts/jquery/jquery.js'></script>
    <script src='${pageContext.request.contextPath}/assets/javascripts/jquery-ui/jquery-ui.js'></script>
    <script src='${pageContext.request.contextPath}/assets/javascripts/bootstrap/bootstrap.js'></script>
    <script src='${pageContext.request.contextPath}/assets/javascripts/moment/moment-with-locales.js'></script>
    <jsp:invoke fragment="includes"/>
</head>
<body>
    <div id="header">
    </div>

    <aside id="sidebar">
    </aside>

    <div id="main_content">
        <jsp:invoke fragment="content"/>
    </div>

    <div id="footer">
    </div>
</body>
</html>