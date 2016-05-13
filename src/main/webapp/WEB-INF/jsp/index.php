<?php
/**
 * VFM - veno file manager index
 *
 * PHP version >= 5.3
 *
 * @category  PHP
 * @package   VenoFileManager
 * @author    Nicola Franchini <info@veno.it>
 * @copyright 2013-2016 Nicola Franchini
 * @license   Standard License http://codecanyon.net/licenses/standard
 * @link      http://filemanager.veno.it/
 */
error_reporting(E_ALL ^ E_NOTICE);
// error_reporting(E_ALL);
// ini_set('display_errors', 1);

require_once 'vfm-admin/include/head.php';
?>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><?php print $setUp->getConfig("appname"); ?></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    
    <meta http-equiv="Content-Language" content="<?php print $encodeExplorer->lang; ?>" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="shortcut icon" href="vfm-admin/images/favicon.ico">
    <meta name="description" content="file manager">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1">
    <link rel="stylesheet" href="vfm-admin/css/bootstrap.min.css">
    <link rel="stylesheet" href="vfm-admin/vfm-style.css">
    <?php 
    if ($setUp->getConfig("txt_direction") == "RTL") { ?>
        <link rel="stylesheet" href="vfm-admin/css/bootstrap-rtl.min.css">
    <?php 
    } ?>
    <link rel="stylesheet" href="vfm-admin/css/font-awesome.min.css">


    <link rel="stylesheet" href="vfm-admin/skins/<?php print $setUp->getConfig('skin') ?>">
    <script type="text/javascript" src="vfm-admin/js/jquery-2.1.1.min.js"></script>

    <!--[if lt IE 9]>
    <script src="vfm-admin/js/html5.js" type="text/javascript"></script>
    <script src="vfm-admin/js/respond.min.js" type="text/javascript"></script>
    <![endif]-->
    <script type="text/javascript" src="vfm-admin/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="vfm-admin/js/app.js"></script>
    <?php 
    $bodyclass = 'vfm-body';
    if ($setUp->getConfig('inline_thumbs') == true) {
        $bodyclass .= ' inlinethumbs';
    } ?>
</head>
    <body id="uparea" class="<?php echo $bodyclass; ?>">
        <div class="overdrag"></div>
            <?php 
            /**
            * ************************************************
            * ******************** HEADER ********************
            * ************************************************
            */ 
            $template->getPart('activate');

            $template->getPart('navbar');
            $template->getPart('header');
            ?>
        <div class="container">
            <?php    
            /**
            * ************************************************
            * **************** Response messages *************
            * ************************************************
            */
            ?>
            <div id="error">
                <noscript>
                    <div class="response boh">
                        <span><i class="fa fa-exclamation-triangle"></i> 
                            Please activate JavaScript</span>
                    </div>
                </noscript>
                <?php
                if (isset($_ERROR) && strlen($_ERROR) > 0) {
                    print "<div class=\"response nope\">"
                    .$_ERROR."<i class=\"fa fa-times closealert\"></i></div>";
                }
                if (isset($_WARNING) && strlen($_WARNING) > 0) {
                    print "<div class=\"response boh\">"
                    .$_WARNING."<i class=\"fa fa-times closealert\"></i></div>";
                } 
                if (isset($_SUCCESS) && strlen($_SUCCESS) > 0) {
                    print "<div class=\"response yep\">"
                    .$_SUCCESS."<i class=\"fa fa-times closealert\"></i></div>";
                }
                ?>
            </div>

            <?php 
            if ($getfilelist) :
                /**
                * ************************************************
                * ********* SHOW FILE SHARING DOWNLOADER *********
                * ************************************************
                */
                $template->getPart('downloader');


            elseif ($getrp) :
                /**
                * ************************************************
                * **************** PASSWORD RESET ****************
                * ************************************************
                */
                $template->getPart('reset');

            else :

                /**
                * ************************************************
                * **************** SHOW FILEMANAGER **************
                * ************************************************
                */

                if (!$getreg || $setUp->getConfig("registration_enable") == false) {
                    $template->getPart('user-redirect');
                    $template->getPart('notify-users');
                    $template->getPart('uploadarea');
                    $template->getPart('breadcrumbs');
                    $template->getPart('list-folders');
                    $template->getPart('list-files');
                    $template->getPart('disk-space');
                }
                if ($getreg && $setUp->getConfig("registration_enable") == true) {
                    $template->getPart('register');
                } else {
                    $template->getPart('login');
                }
                $template->getPart('modals');

            endif; ?>
        </div> <!-- .vfmwrapper -->
        <?php
                /**
                * ************************************************
                * ******************** FOOTER ********************
                * ************************************************
                */
                $template->getPart('footer');
            ?>

    </body>
</html>