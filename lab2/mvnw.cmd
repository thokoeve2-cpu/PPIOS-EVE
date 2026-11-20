@echo off
where mvn >nul 2>nul
if %ERRORLEVEL% EQU 0 (
  mvn %*
  exit /b %ERRORLEVEL%
)
echo Maven is not installed. Use the Unix mvnw script on macOS/Linux or install Apache Maven on Windows.
exit /b 1
