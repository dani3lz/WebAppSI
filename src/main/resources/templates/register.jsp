<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>REGISTER</title>
    <link rel="stylesheet" th:href="@{/css/login.css}">
    <link rel="stylesheet" th:href="@{/css/navbar.css}">
</head>
<body>

<div class="navbar" th:insert="navbar :: navbar"></div>

<div class="wrapper">
    <div class="form-container">
        <div class="form-inner">

            <form th:action="@{/register}" th:object="${RegisterRequest}" class="signup" method="POST">
                <div class="field">
                    <input name="username" type="text" placeholder="Username" required>
                </div>
                <div class="field">
                    <input name="password" type="password" placeholder="Password" required>
                </div>
                <div class="field">
                    <input name="confirmPassword" type="password" placeholder="Confirm password" required>
                </div>

                <div class="pass-link" th:text="${info}"></div>

                <div class="field btn">
                    <div class="btn-layer"></div>
                    <input type="submit" value="Register">
                </div>
                <div class="signup-link">You are already registered? <a href="/login">Login</a></div>
            </form>

        </div>
    </div>
</div>

</body>
</html>
