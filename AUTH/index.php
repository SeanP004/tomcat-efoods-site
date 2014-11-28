<?php

    //error_reporting(E_ALL);
    //ini_set('display_errors', 1);

    $initParams = parse_ini_file('config.ini');
    $authUri    = 'https://'.$_SERVER['SERVER_NAME'].dirname($_SERVER['PHP_SELF']).'/zoned';

    function curl($uri, $username, $password) {
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $uri);
        curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 2);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, TRUE);
        curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, FALSE);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE);
        curl_setopt($ch, CURLOPT_USERPWD, $username . ":" . $password);
        curl_setopt($ch, CURLOPT_HEADER, 0);
        $data = curl_exec($ch);
        $http_status = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        curl_close($ch);
        return array(
            'status' => $http_status,
            'data'   => $data
        );
    }

    if (isset($_POST['username']) && isset($_POST['password'])) {
        $res = curl($authUri, $_POST['username'], $_POST['password']);
        if ($res['status'] == 200) {
            $json = json_decode($res['data']);
            $signer = md5($json->account.':'.$json->name.':'.$initParams['secret']);
            $params = implode('&', array(
                'account='.urlencode($json->account),
                'name='.urlencode($json->name),
                'signer='.urlencode($signer)
            ));
            if (isset($_POST['ref'])) {
                $params .= '&ref='.urlencode($_POST['ref']);
            }
            header('Location: '.$initParams['uri'].'?'.$params);
            exit();
        } else {
            if (isset($_POST['ref'])) {
                $referrer = $_POST['ref'];
                $error = 'That username and password are incorrect.';
                $included = true;
                include 'view.php';
            }
        }
    } else {
        if (isset($_GET['ref'])) {
            $referrer = $_GET['ref'];
        }
        $included = true;
        include 'view.php';
    }
