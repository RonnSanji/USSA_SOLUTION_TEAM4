call setenv.bat

@echo off&setlocal
for %%i in ("%~dp0..") do set "folder=%%~fi"
echo %folder%

"%PATH%\javac" -sourcepath %folder%\src\*.java -d %folder%\classes


pause