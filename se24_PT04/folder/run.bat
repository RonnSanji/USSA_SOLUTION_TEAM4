call setenv.bat

@echo off&setlocal
for %%i in ("%~dp0..") do set "folder=%%~fi"
echo %folder%
cd..

"%PATH%\java" -classpath %folder%\classes sg.edu.nus.iss.ssa.main.SouvenirStoreApp
