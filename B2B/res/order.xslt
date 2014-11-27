<?xml version="1.0"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <xsl:output method="html" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes" />

    <!-- the identity template (copies your input verbatim) -->

    <xsl:template match="@* | node()">
        <xsl:copy>
            <xsl:apply-templates select="@* | node()" />
        </xsl:copy>
    </xsl:template>

    <!-- special templates only for things that need them -->

    <xsl:template match="CompletedOrderList">
    <html>
        <body>
        <p>Date : <xsl:value-of select="./@date" /></p>
        
        <table>
            <tr>
                <th>Item Number</th>
                <th>Price</th>
                <th>Confirmation</th>
                <th>Wholesaler</th>
            </tr>
            <xsl:apply-templates select="orders"/>
        </table>
        </body>
    </html>
    </xsl:template>

    <xsl:template match="orders">
        <tr>
            <td><xsl:value-of select="./@itemNo" /></td>
            <td><xsl:value-of select="./price" /></td>
            <td><xsl:value-of select="./confirm" /></td>
            <td><xsl:value-of select="./wholesaler" /></td>
            
        </tr>
    </xsl:template>


</xsl:stylesheet>

