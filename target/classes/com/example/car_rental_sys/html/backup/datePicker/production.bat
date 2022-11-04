@echo off
echo USER: %Innis%
echo TIME: %2022-11-01%
echo.

title start-http-server

rem
if "%1" == "h" goto begin
mshta vbscript:createobject("wscript.shell").run("%~nx0 h",8)(window.close)&&exit
:begin
rem
http-server dir