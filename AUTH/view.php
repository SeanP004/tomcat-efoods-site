<?php if (!isset($included)) {die();} ?>
<!DOCTYPE html>

<html>
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
        <title>EECS Login</title>
        <link rel="stylesheet" type="text/css" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="assets/css/main.css" />
        <link rel="shortcut icon" href="assets/images/favicon.ico" />
        <script src="assets/js/main.js" async="async"></script>
    </head>
    <body>

        <header>
            <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
                <div class="container">
                    <div class="navbar-header">
                        <a class="navbar-brand" href="http://yorku.ca">
                            <img class="yorklogo" src="assets/images/yorku-logo.jpg" border="0" alt="York University" />
                        </a>
                    </div>
                    <div class="navbar-collapse collapse">
                        <p class="navbar-text text-right pull-right">Building E-Commerce Systems
                            <br/>EECS 4413 Project C</p>
                    </div>
                </div>
            </nav>
        </header>

        <div class="main container">
            <div class="row login">
                <div class="login-box">
                    <div class="form-wrap panel panel-default">
                        <div class="panel-heading"><h1>Log in with your EECS Account</h1></div>
                        <form class="form panel-body" role="form" action="" method="post" autocomplete="off">
                            <?php if (isset($error)): ?>
                            <div class="alert alert-danger" role="alert"><?php echo $error ?></div>
                            <?php else: ?>
                            <div class="alert alert-danger hidden" role="alert"></div>
                            <?php endif ?>
                            <div class="form-group has-feedback">
                                <label for="username" class="sr-only">Username</label>
                                <input type="text" name="username" class="form-control"
                                       placeholder="Username" aria-describedby="usernameErrorStatus" />
                                <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                                <span id="uErrorStatus" class="sr-only"></span>
                            </div>
                            <div class="form-group has-feedback">
                                <label for="password" class="sr-only">Password</label>
                                <input type="password" name="password" class="form-control"
                                       placeholder="Password" aria-describedby="passwordErrorStatus" />
                                <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                                <span id="pErrorStatus" class="sr-only"></span>
                            </div>
                            <?php if (isset($referrer)): ?>
                            <input type="hidden" name="ref" value="<?php echo $referrer ?>" />
                            <?php endif ?>
                            <?php if (isset($callback)): ?>
                            <input type="hidden" name="callback" value="<?php echo $callback ?>" />
                            <?php endif ?>
                            <?php if (isset($signature)): ?>
                            <input type="hidden" name="signer" value="<?php echo $signature ?>" />
                            <?php endif ?>
                            <input type="submit" class="btn btn-custom btn-lg btn-block" value="Log in" />
                        </form>
                        <div class="panel-footer text-center">
                            <p>Designed and developed by Vincent Chu, Michael Leung,
                                Manusha Patabendi for Building E-Commerce Systems
                                (EECS 4413 Section A, Fall 2014) with Professor H.
                                Roumani.</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
