<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>LOGIN</title>
    <link rel="stylesheet" th:href="@{/css/login.css}">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<div class="wrapper">
    <div class="form-container">
        <div class="form-inner">

            <form th:action="@{/authenticateTheUser}" th:object="${UserRequest}" class="login" method="POST">
                <div class="field">
                    <input name="username" type="text" placeholder="Username" required>
                </div>
                <div class="field">
                    <input name="password" type="password" placeholder="Password" required>
                </div>

                <div class="pass-link" th:text="${info}"></div>

                <div class="field btn">
                    <div class="btn-layer"></div>
                    <input type="submit" value="Login">
                </div>
                <div class="signup-link">Not a member? <a href="/register">Register now</a></div>
            </form>

        </div>
    </div>
</div>

</body>
</html>
