<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Convertor</title>
    <link rel="stylesheet" th:href="@{/css/convertor.css}">
    <link rel="stylesheet" th:href="@{/css/navbar.css}">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>

<div class="navbar" th:insert="navbar :: navbar"></div>

<div class="wrapper">

    <!-- INPUT -->
    <div class="form-container">
        <div class="form-inner">
            <form th:action="@{/convertor}" th:object="${TextRequest}" class="convert" method="POST">

                <div class="field">
                    <legend>Select mode:</legend>
                    <div>
                        <input type="radio" id="ENCRYPT" name="mode" value="ENCRYPT"
                               checked>
                        <label for="ENCRYPT">ENCRYPT</label>
                    </div>
                    <div>
                        <input type="radio" id="DECRYPT" name="mode" value="DECRYPT">
                        <label for="DECRYPT">DECRYPT</label>
                    </div>
                </div>
                <div class="field">
                    <label>Input:</label>
                </div>
                <div class="field key inputKey hide">
                    <input class="inputLabel" name="key" type="text" placeholder="Key for Decrypt" minlength="3">
                </div>
                <div class="inputField">
                    <textarea id="inputText" name="inputText" rows="8" cols="120" minlength="3" placeholder="Input text"
                              required></textarea>
                </div>

                <div class="field">
                    <legend>Select algorithm:</legend>
                    <div>
                        <input type="radio" id="RSA" name="algorithm" value="RSA"
                               checked>
                        <label for="RSA">RSA</label>
                    </div>
                    <div>
                        <input type="radio" id="DES" name="algorithm" value="DES">
                        <label for="DES">DES</label>
                    </div>
                    <div>
                        <input type="radio" id="AES" name="algorithm" value="AES">
                        <label for="AES">AES</label>
                    </div>
                </div>

                <div class="field btn">
                    <div class="btn-layer"></div>
                    <input type="submit" value="Convert">
                </div>
                <div class="error" th:text="${response.error}"></div>
            </form>

            <!-- OUTPUT -->
            <div class="field output" th:classappend="${response.hideKey} ? hide">
                <label>Key:</label>
            </div>
            <div class="field key" th:classappend="${response.hideKey} ? hide">
                <input name="key" type="text" th:value="${response.key}">
            </div>
            <div class="field output" th:classappend="${response.hide} ? hide">
                <label>Output text:</label>
            </div>
            <div class="outputField" th:classappend="${response.hide} ? hide">
                <textarea id="outputText" name="outputText" rows="8" cols="120" th:text="${response.outputText}"></textarea>
            </div>

        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        $('#ENCRYPT').click(function () {
            $('.inputKey').addClass('hide').prop('required', false);
            $('.inputLabel').prop('required', false);

        });
        $('#DECRYPT').click(function () {
            $('.inputKey').removeClass('hide');
            $('.inputLabel').prop('required', true);
        });
    });
</script>
</body>
</html>
