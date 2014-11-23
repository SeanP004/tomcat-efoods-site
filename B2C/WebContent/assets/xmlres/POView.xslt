<?xml version="1.0" encoding="UTF-8" ?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <xsl:output method="html" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes" />

    <xsl:template match="/">
        <html>
            <head>
                <meta charset="UTF-8" />
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1" />
                <title>POReport</title>
                <link rel="stylesheet" type="text/css" href="/eFoods/assets/css/main.css" />
            </head>
            <body>
                <header>
                    <nav role="navigation" class="navbar">
                        <div class="container">
                            <div class="navbar-header">
                                <div class="col-xs-6">
                                    <a href="/eFoods/" class="navbar-brand">Foods R Us!</a>
                                </div>
                            </div>
                        </div>
                    </nav>
                </header>
                <div class="page">
                    <div class="container">
                        <xsl:apply-templates />
                    </div>
                    <footer class="footer">
                        <div class="container">
                            <div class="row">
                                <div class="col-sm-6 col-md-4">
                                    <h2>Foods R Us!</h2>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6 col-md-4">
                                    <p>Copyright <xsl:text disable-output-escaping='yes'>&#169;</xsl:text> 2014.</p>
                                    <p>Designed and developed by Vincent Chu, Michael
                                        Leung, Manusha Patabendi for Building E-Commerce
                                        Systems (EECS 4413 Section A, Fall 2014) with Professor
                                        H. Roumani.</p>
                                    <p>License under the <a href=
                                        "http://opensource.org/licenses/MIT" target=
                                        "_blank">MIT License</a>.</p>
                                </div>
                                <div class="col-sm-6 cod-md-8">
                                    <h4>Attributions:</h4>
                                    <ol class="small">
                                        <li>Styling based on Bootstrap 3 from <a href=
                                            "http://www.getbootstrap.com" title="Bootstrap">
                                            www.getbootstrap.com</a>. Code and
                                            documentation copyright 2011-2014 Twitter, Inc.
                                            Code licensed\ under <a href=
                                            "https://github.com/twbs/bootstrap/blob/master/LICENSE"
                                            title="Bootstrap License">MIT</a>,
                                            documentation under <a href=
                                            "http://creativecommons.org/licenses/by/3.0/"
                                            title="Creative Commons BY 3.0">CC BY 3.0.</a>
                                        </li>
                                        <li>Some icons made by Freepik, OCHA, Linh Pham,
                                            Google from <a href="http://www.flaticon.com"
                                            title="Flaticon">www.flaticon.com</a> is
                                            licensed by <a href=
                                            "http://creativecommons.org/licenses/by/3.0/"
                                            title="Creative Commons BY 3.0">CC BY 3.0</a>
                                        </li>
                                    </ol>
                                </div>
                            </div>
                        </div>
                    </footer>
                </div>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="order">
        <div class="row">
            <div class="col-sm-12">
                <div class="page-header text-center">
                    <h1>Order Number: <xsl:value-of select="./@id" /></h1>
                </div>
                <div class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Date Submitted:</label>
                        <div class="col-sm-8">
                            <p class="form-control-static"><xsl:value-of select="./@submitted" /></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Account Name:</label>
                        <div class="col-sm-8">
                            <p class="form-control-static"><xsl:value-of select="./customer/name" /></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Account ID:</label>
                        <div class="col-sm-8">
                            <p class="form-control-static"><xsl:value-of select="./customer/@account" /></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Items:</label>
                        <div class="col-sm-8">
                            <table class="table">
                                <tr>
                                    <th>Number</th>
                                    <th>Name</th>
                                    <th>Price</th>
                                    <th>Quatity</th>
                                    <th>Extended</th>
                                </tr>
                                <xsl:apply-templates select="./items/item"/>
                            </table>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Sub Total:</label>
                        <div class="col-sm-8">
                            <p class="form-control-static"><xsl:value-of select="./total" /></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Shipping Cost:</label>
                        <div class="col-sm-8">
                            <p class="form-control-static"><xsl:value-of select="./shipping" /></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Tax:</label>
                        <div class="col-sm-8">
                            <p class="form-control-static"><xsl:value-of select="./HST" /></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Total:</label>
                        <div class="col-sm-8">
                            <p class="form-control-static"><xsl:value-of select="./grandTotal" /></p>
                        </div>
                    </div>
                </div>
            </div>
         </div>
    </xsl:template>

    <xsl:template match="item">
        <tr>
            <td><xsl:value-of select="./@number" /></td>
            <td><xsl:value-of select="./name" /></td>
            <td><xsl:value-of select="./price" /></td>
            <td><xsl:value-of select="./quantity" /></td>
            <td><xsl:value-of select="./extended" /></td>
        </tr>
    </xsl:template>

</xsl:stylesheet>
