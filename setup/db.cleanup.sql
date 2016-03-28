--<ScriptOptions statementTerminator=';'/>

CONNECT 'jdbc:derby:eFoods;create=true';
SET SCHEMA ROUMANI;

DROP TABLE CATEGORY;
DROP TABLE ITEM;

DISCONNECT;
EXIT;
