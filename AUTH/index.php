<?php

    require "compat.php";

    //error_reporting(E_ALL);
    //ini_set('display_errors', 1);

    $initParams  = parse_ini_file('config.ini');
    $authBaseUri = 'https://'.$_SERVER['SERVER_NAME'].':'.$_SERVER['SERVER_PORT'];
    $authUri     = $authBaseUri.dirname($_SERVER['PHP_SELF']).'/zoned';

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

    try {
        if ($_SERVER['REQUEST_METHOD'] === 'GET') {
            if (isset($_GET['ref']) && isset($_GET['signer'])) {
                $referrer  = $_GET['ref'];
                $signature = strtolower($_GET['signer']);
            } else {
                throw new Exception("Error: ref or signer is undefined.");
            }
            if (isset($_GET['callback'])) {
                $callback = $_GET['callback'];
            }
        } else {
            if (isset($_POST['ref']) && isset($_POST['signer'])) {
                $referrer = urldecode($_POST['ref']);
                $signature = strtolower($_POST['signer']);
            } else {
                throw new Exception("Error: ref or signer is undefined.");
            }
            if (isset($_POST['callback'])) {
                $callback = urldecode($_POST['callback']);
            }
            if (isset($_POST['username']) && isset($_POST['password'])) {
                $res = curl($authUri, $_POST['username'], $_POST['password']);
                if ($res['status'] == 200) {
                    $json = json_decode($res['data']);
                    $signer = md5($json->account.';'.$json->name.';'.$referrer.';'.$initParams['secret']);
                    $params = implode('&', array(
                        'account='.urlencode($json->account),
                        'name='.urlencode($json->name),
                        'signer='.urlencode($signer),
                        'ref='.urlencode($referrer)
                    ));
                    if (!isset($callback)) {
                        $callback = str_replace('localhost', $_SERVER['SERVER_NAME'], $initParams['uri']);
                        if ($signature !== md5($referrer.';'.$initParams['secret'])) {
                            echo $signature . "\n";
                            echo md5($referrer.';'.$callback.';'.$initParams['secret']) . "\n";
                            throw new Exception("Error: digital signature is incorrect.");
                        }
                    } else {
                        if ($signature !== md5($referrer.';'.$callback.';'.$initParams['secret'])) {
                            echo $signature . "\n";
                            echo md5($referrer.';'.$callback.';'.$initParams['secret']) . "\n";
                            throw new Exception("Error: digital signature is incorrect.");
                        }
                    }
                    header('Location: '.$callback.'?'.$params);
                    exit();
                }
            }
            $error = 'That username and password are incorrect.';
        }
        $included = true;
        include 'view.php';
    } catch (Exception $e) {
        http_response_code(BAD_REQUEST);
        echo $e->getMessage();
        die();
    }
