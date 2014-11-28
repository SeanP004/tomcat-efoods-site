<?php
/**
 *  Compat.php -- Backward Compatibility
 *
 *  This script provides polyfill implementations
 *  for missing functions in older versions of PHP.
 *  For backward compatibility.
 *
 *  @author     Vince Chu <vchu@yorku.ca>
 *  @version    1.0 (2014.06.19)
 *
 */

    /**
     *  HTTP response code
     *  Constant definitions.
     */

    define('HTTP_CONTINUE'                , 100);
    define('SWITCHING_PROTOCOLS'          , 101);
    define('OK'                           , 200);
    define('CREATED'                      , 201);
    define('ACCEPTED'                     , 202);
    define('NON_AUTHORITATIVE_INFORMATION', 203);
    define('NO_CONTENT'                   , 204);
    define('RESET_CONTENT'                , 205);
    define('PARTIAL_CONTENT'              , 206);
    define('MULTIPLE_CHOICES'             , 300);
    define('MOVED_PERMANENTLY'            , 301);
    define('MOVED_TEMPORARILY'            , 302);
    define('SEE_OTHER'                    , 303);
    define('NOT_MODIFIED'                 , 304);
    define('USE_PROXY'                    , 305);
    define('BAD_REQUEST'                  , 400);
    define('UNAUTHORIZED'                 , 401);
    define('PAYMENT_REQUIRED'             , 402);
    define('FORBIDDEN'                    , 403);
    define('NOT_FOUND'                    , 404);
    define('METHOD_NOT_ALLOWED'           , 405);
    define('NOT_ACCEPTABLE'               , 406);
    define('PROXY_AUTHENTICATION_REQUIRED', 407);
    define('REQUEST_TIME_OUT'             , 408);
    define('CONFLICT'                     , 409);
    define('GONE'                         , 410);
    define('LENGTH_REQUIRED'              , 411);
    define('PRECONDITION_FAILED'          , 412);
    define('REQUEST_ENTITY_TOO_LARGE'     , 413);
    define('REQUEST_URI_TOO_LARGE'        , 414);
    define('UNSUPPORTED_MEDIA_TYPE'       , 415);
    define('INTERNAL_SERVER_ERROR'        , 500);
    define('NOT_IMPLEMENTED'              , 501);
    define('BAD_GATEWAY'                  , 502);
    define('SERVICE_UNAVAILABLE'          , 503);
    define('GATEWAY_TIME_OUT'             , 504);
    define('HTTP_VERSION_NOT_SUPPORTED'   , 505);

    /**
     *  int http_response_code ([ int $response_code ] )
     *
     *  Get or Set the HTTP response code (PHP 5 >= 5.4.0)
     *  If you pass no parameters then http_response_code will
     *  get the current status code. If you pass a parameter it
     *  will set the response code.
     *
     *
     *  @param $response_code   The optional response_code will
     *                          set the response code.
     *  @return                 The current response code. By
     *                          default the return value is
     *                          int(200).
     */

    if (!function_exists('http_response_code')) {
        function http_response_code($code = NULL) {
            if ($code !== NULL) {
                switch ($code) {
                    case 100: $text = 'Continue'; break;
                    case 101: $text = 'Switching Protocols'; break;
                    case 200: $text = 'OK'; break;
                    case 201: $text = 'Created'; break;
                    case 202: $text = 'Accepted'; break;
                    case 203: $text = 'Non-Authoritative Information'; break;
                    case 204: $text = 'No Content'; break;
                    case 205: $text = 'Reset Content'; break;
                    case 206: $text = 'Partial Content'; break;
                    case 300: $text = 'Multiple Choices'; break;
                    case 301: $text = 'Moved Permanently'; break;
                    case 302: $text = 'Moved Temporarily'; break;
                    case 303: $text = 'See Other'; break;
                    case 304: $text = 'Not Modified'; break;
                    case 305: $text = 'Use Proxy'; break;
                    case 400: $text = 'Bad Request'; break;
                    case 401: $text = 'Unauthorized'; break;
                    case 402: $text = 'Payment Required'; break;
                    case 403: $text = 'Forbidden'; break;
                    case 404: $text = 'Not Found'; break;
                    case 405: $text = 'Method Not Allowed'; break;
                    case 406: $text = 'Not Acceptable'; break;
                    case 407: $text = 'Proxy Authentication Required'; break;
                    case 408: $text = 'Request Time-out'; break;
                    case 409: $text = 'Conflict'; break;
                    case 410: $text = 'Gone'; break;
                    case 411: $text = 'Length Required'; break;
                    case 412: $text = 'Precondition Failed'; break;
                    case 413: $text = 'Request Entity Too Large'; break;
                    case 414: $text = 'Request-URI Too Large'; break;
                    case 415: $text = 'Unsupported Media Type'; break;
                    case 500: $text = 'Internal Server Error'; break;
                    case 501: $text = 'Not Implemented'; break;
                    case 502: $text = 'Bad Gateway'; break;
                    case 503: $text = 'Service Unavailable'; break;
                    case 504: $text = 'Gateway Time-out'; break;
                    case 505: $text = 'HTTP Version not supported'; break;
                    default:
                        exit('Unknown http status code "' . htmlentities($code) . '"');
                    break;
                }
                $protocol = (isset($_SERVER['SERVER_PROTOCOL']) ? $_SERVER['SERVER_PROTOCOL'] : 'HTTP/1.0');
                header("$protocol $code $text");
                $GLOBALS['http_response_code'] = $code;
            } else {
                $code = (isset($GLOBALS['http_response_code']) ? $GLOBALS['http_response_code'] : 200);
            }
            return $code;
        } // http_response_code
    }
