<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <div class="container">
        <h1>Login</h1>
        <form method="POST" action="index.php">
            <div class="field-container">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit">Login</button>
            <?php
            if ($_SERVER['REQUEST_METHOD'] == 'POST') {
                $password = $_POST['password'];
                $blockedPasswords = file('10_million_password_list-top-1000.txt', FILE_IGNORE_NEW_LINES);

                // OWASP Proactive Controls C6: Enforce password complexity
                $passwordPattern = '/^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[\W]).{8,}$/';

                if (in_array($password, $blockedPasswords)) {
                    echo '<div class="error-msg">Common password detected. Please use a different password.</div>';
                } elseif (!preg_match($passwordPattern, $password)) {
                    echo '<div class="error-msg">Password does not meet complexity requirements.</div>';
                } else {
                    session_start();
                    $_SESSION['password'] = $password;
                    header('Location: dashboard.php');
                    exit();
                }
            }
            ?>
        </form>
    </div>
</body>
</html>
