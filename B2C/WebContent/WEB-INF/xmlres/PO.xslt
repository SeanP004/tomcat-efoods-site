<?xml version="1.0" encoding="UTF-8" ?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <!-- the identity template (copies your input verbatim) -->

    <xsl:template match="@* | node()">
        <xsl:copy>
            <xsl:apply-templates select="@* | node()" />
        </xsl:copy>
    </xsl:template>

    <!-- special templates only for things that need them -->

    <xsl:template match="order">
        <order id="{@orderId}" submitted="{@submitDate}">
            <xsl:element name="customer">
                <xsl:attribute name="account">
                    <xsl:value-of select="./customer/@accountID" />
                </xsl:attribute>
                <name><xsl:value-of select="./customer/accountName" /></name>
            </xsl:element>
            <items>
                <xsl:apply-templates select="cart/cart-elements/cart-element"/>
            </items>
            <xsl:apply-templates select="cart/cost"/>
        </order>
    </xsl:template>

    <xsl:template match="cart-element">
        <xsl:element name="item">
            <xsl:attribute name="number">
                <xsl:value-of select="./item/@number" />
            </xsl:attribute>
            <name><xsl:value-of select="./item/name" /></name>
            <price><xsl:value-of select="./item/price" /></price>
            <quantity><xsl:value-of select="@quantity" /></quantity>
            <extended><xsl:value-of select="@extendedCost" /></extended>
        </xsl:element>
    </xsl:template>

    <xsl:template match="cost">
        <total><xsl:value-of select="./discountedTotal" /></total>
        <shipping><xsl:value-of select="./shipping" /></shipping>
        <HST><xsl:value-of select="./tax" /></HST>
        <grandTotal><xsl:value-of select="./grandTotal" /></grandTotal>
    </xsl:template>

</xsl:stylesheet>
