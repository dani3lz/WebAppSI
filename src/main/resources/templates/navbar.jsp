<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
    <meta charset="UTF-8">
    <title>NavigationBar</title>
</head>
<body>

<div th:fragment="navbar" class="navbar">
    <div class="nav-logo">
        <a class="navbar-brand" th:href="@{/}">MY LOGO</a>
    </div>
    <div class="nav-link">
        <a th:href="@{/}">Home</a>
        <a th:href="@{/convertor}" th:if="${isLogged == true}">Convertor</a>

    </div>
    <div class="nav-auth" th:if="${isLogged == false}">
        <a th:href="@{/login}">Login</a>
        <a th:href="@{/register}">Register</a>
    </div>
    <div class="nav-auth" th:unless="${isLogged == false}">
        <form th:action="@{/logout}" method="POST">
            <div>
                <input type="submit" value="Logout">
            </div>
        </form>
    </div>
</div>

</body>
</html>