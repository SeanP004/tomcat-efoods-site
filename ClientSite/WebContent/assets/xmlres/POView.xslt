<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html" omit-xml-declaration="yes" doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"/>

    <!-- the identity template (copies your input verbatim) -->

    <xsl:template match="@* | node()">
        <xsl:copy>
            <xsl:apply-templates select="@* | node()" />
        </xsl:copy>
    </xsl:template>

    <!-- special templates only for things that need them -->

    <xsl:template match="order">
        <html>
            <body>
            <p>Order Number: <xsl:value-of select="./@id" /></p>
            <p>Date Submitted: <xsl:value-of select="./@submitted" /></p>
            <p>Account Name: <xsl:value-of select="./customer/name" /></p>
            <p>Account ID: <xsl:value-of select="./customer/@account" /></p>
            <table>
                <tr>
                    <th>Number</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Quatity</th>
                    <th>Extended</th>
                </tr>
                <xsl:apply-templates select="./items/item"/>
            </table>
            <p>Sub Total: <xsl:value-of select="./total" /></p>
            <p>Shipping Cost: <xsl:value-of select="./shipping" /></p>
            <p>Tax: <xsl:value-of select="./HST" /></p>
            <p>Total: <xsl:value-of select="./grandTotal" /></p>
            </body>
        </html>
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
