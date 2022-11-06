<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
    <meta charset="utf-8">
    <title>Coming soon...</title>
    <link rel="stylesheet" th:href="@{/css/soon.css}">
</head>
<body>
<div class="bgimg">
    <div class="topleft">
        <form th:action="@{/logoutQuery}" method="post">
            <div>
                <input type="submit" value="Logout">
            </div>
        </form>
    </div>
    <div class="middle">
        <h1>COMING SOON</h1>
        <hr>
    </div>
    <div class="bottomleft">
        <p>Securitatea Informationala, Laboratorul nr.4</p>
    </div>
</div>
</body>
</html>
