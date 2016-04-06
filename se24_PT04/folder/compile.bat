call setenv.bat

@echo off&setlocal
for %%i in ("%~dp0..") do set "folder=%%~fi"
echo %folder%

"%PATH%\javac" -verbose -sourcepath %folder%\src\ %folder%\src\sg\edu\nus\iss\ssa\main\*.java -d %folder%\classes


Echo Compilation DONE !


pause