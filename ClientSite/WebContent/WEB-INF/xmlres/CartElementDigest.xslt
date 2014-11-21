<?xml version="1.0" encoding="UTF-8" ?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="@* | node()">
        <xsl:copy>
            <xsl:apply-templates select="@* | node()" />
        </xsl:copy>
    </xsl:template>
    <xsl:template match="cart-element">
        <xsl:copy>
            <xsl:attribute name="item">
                <xsl:value-of select="./item/@number" /> 
            </xsl:attribute>
            <xsl:copy-of select="@*"></xsl:copy-of>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
