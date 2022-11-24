<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Convertor</title>
    <link rel="stylesheet" th:href="@{/css/convertor.css}">
    <link rel="stylesheet" th:href="@{/css/navbar.css}">
</head>
<body>

<div class="navbar" th:insert="navbar :: navbar"></div>

<div class="wrapper">

    <!-- INPUT -->
    <div class="form-container">
        <div class="form-inner">
            <form th:action="@{/convertor}" th:object="${ConvertorRequest}" class="convert" method="POST">

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
                <div class="field key">
                    <input name="privateKey" type="text" placeholder="Key for Decrypt" required>
                </div>
                <div class="inputField">
                    <textarea id="inputText" name="inputText" rows="8" cols="120" placeholder="Input text" required></textarea>
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
            </form>

            <!-- OUTPUT -->
            <div class="field output">
            <label>Output:</label>
            </div>
            <div class="field key">
                <input name="privateKey" type="text" placeholder="Private Key">
            </div>
            <div class="field key">
                <input name="publicKey" type="text" placeholder="Public Key">
            </div>
            <div class="outputField">
                <textarea id="outputText" name="outputText" rows="8" cols="120"></textarea>
            </div>

        </div>
    </div>
</div>

</body>
</html>
