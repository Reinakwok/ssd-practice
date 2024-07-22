<?php
session_start();
if (!isset($_SESSION['password'])) {
    header('Location: index.php');
    exit();
}
?>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome Page</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <div class="container-dashboard">
        <h1>Welcome</h1>
        <p>Your password: <?php echo htmlspecialchars($_SESSION['password']); ?></p>
        <a class="logout-link" href="logout.php">Logout</a>
    </div>
</body>
</html>
