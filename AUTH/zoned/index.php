<?php

    function getFullName($account) {
        $users = explode("\n", file_get_contents('/etc/passwd'));
        foreach (preg_grep('/'.$account.'/', $users) as $record) {
            return explode(':', $record)[4];
        }
    }

    if (!isset($_SERVER['PHP_AUTH_USER'])) {
        header('WWW-Authenticate: Basic realm="My Realm"');
        header('HTTP/1.0 401 Unauthorized');
        echo json_encode(array( 'error' => 'Access Denied.' ));
        exit;
    }

    echo json_encode(array(
        'account' => $_SERVER['PHP_AUTH_USER'],
        'name' => getFullName($_SERVER['PHP_AUTH_USER']),
    ));
