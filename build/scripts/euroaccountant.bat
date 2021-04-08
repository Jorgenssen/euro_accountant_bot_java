@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  euroaccountant startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and EUROACCOUNTANT_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\euroaccountant-0.0.1-SNAPSHOT.jar;%APP_HOME%\lib\telegrambots-spring-boot-starter-4.1.2.jar;%APP_HOME%\lib\spring-boot-devtools-2.2.2.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-web-2.2.2.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-security-2.2.2.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-json-2.2.2.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-validation-2.2.2.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-2.2.2.RELEASE.jar;%APP_HOME%\lib\spring-boot-autoconfigure-2.2.2.RELEASE.jar;%APP_HOME%\lib\spring-boot-2.2.2.RELEASE.jar;%APP_HOME%\lib\log4j-core-2.12.1.jar;%APP_HOME%\lib\spring-boot-starter-logging-2.2.2.RELEASE.jar;%APP_HOME%\lib\log4j-to-slf4j-2.12.1.jar;%APP_HOME%\lib\log4j-api-2.12.1.jar;%APP_HOME%\lib\spring-webmvc-5.2.2.RELEASE.jar;%APP_HOME%\lib\spring-security-web-5.2.1.RELEASE.jar;%APP_HOME%\lib\spring-web-5.2.2.RELEASE.jar;%APP_HOME%\lib\spring-security-config-5.2.1.RELEASE.jar;%APP_HOME%\lib\spring-security-core-5.2.1.RELEASE.jar;%APP_HOME%\lib\spring-context-5.2.2.RELEASE.jar;%APP_HOME%\lib\spring-expression-5.2.2.RELEASE.jar;%APP_HOME%\lib\spring-aop-5.2.2.RELEASE.jar;%APP_HOME%\lib\spring-beans-5.2.2.RELEASE.jar;%APP_HOME%\lib\spring-core-5.2.2.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-tomcat-2.2.2.RELEASE.jar;%APP_HOME%\lib\telegrambots-4.1.2.jar;%APP_HOME%\lib\spring-jcl-5.2.2.RELEASE.jar;%APP_HOME%\lib\jersey-container-grizzly2-http-2.29.1.jar;%APP_HOME%\lib\jersey-server-2.29.1.jar;%APP_HOME%\lib\jersey-media-json-jackson-2.29.1.jar;%APP_HOME%\lib\jersey-client-2.29.1.jar;%APP_HOME%\lib\jersey-media-jaxb-2.29.1.jar;%APP_HOME%\lib\jersey-common-2.29.1.jar;%APP_HOME%\lib\jakarta.annotation-api-1.3.5.jar;%APP_HOME%\lib\snakeyaml-1.25.jar;%APP_HOME%\lib\jackson-datatype-jdk8-2.10.1.jar;%APP_HOME%\lib\jackson-datatype-jsr310-2.10.1.jar;%APP_HOME%\lib\jackson-module-parameter-names-2.10.1.jar;%APP_HOME%\lib\telegrambots-meta-4.1.2.jar;%APP_HOME%\lib\jackson-jaxrs-json-provider-2.10.1.jar;%APP_HOME%\lib\jackson-jaxrs-base-2.10.1.jar;%APP_HOME%\lib\jackson-module-jaxb-annotations-2.10.1.jar;%APP_HOME%\lib\jackson-databind-2.10.1.jar;%APP_HOME%\lib\tomcat-embed-websocket-9.0.29.jar;%APP_HOME%\lib\tomcat-embed-core-9.0.29.jar;%APP_HOME%\lib\tomcat-embed-el-9.0.29.jar;%APP_HOME%\lib\jakarta.validation-api-2.0.1.jar;%APP_HOME%\lib\hibernate-validator-6.0.18.Final.jar;%APP_HOME%\lib\jackson-annotations-2.10.1.jar;%APP_HOME%\lib\json-20180813.jar;%APP_HOME%\lib\httpmime-4.5.10.jar;%APP_HOME%\lib\httpclient-4.5.10.jar;%APP_HOME%\lib\commons-io-2.5.jar;%APP_HOME%\lib\logback-classic-1.2.3.jar;%APP_HOME%\lib\jul-to-slf4j-1.7.29.jar;%APP_HOME%\lib\jackson-core-2.10.1.jar;%APP_HOME%\lib\jboss-logging-3.4.1.Final.jar;%APP_HOME%\lib\classmate-1.5.1.jar;%APP_HOME%\lib\guice-4.2.2.jar;%APP_HOME%\lib\jersey-entity-filtering-2.29.1.jar;%APP_HOME%\lib\jakarta.inject-2.6.1.jar;%APP_HOME%\lib\grizzly-http-server-2.4.4.jar;%APP_HOME%\lib\jakarta.ws.rs-api-2.1.6.jar;%APP_HOME%\lib\httpcore-4.4.12.jar;%APP_HOME%\lib\commons-codec-1.13.jar;%APP_HOME%\lib\logback-core-1.2.3.jar;%APP_HOME%\lib\slf4j-api-1.7.29.jar;%APP_HOME%\lib\javax.inject-1.jar;%APP_HOME%\lib\aopalliance-1.0.jar;%APP_HOME%\lib\guava-25.1-android.jar;%APP_HOME%\lib\jakarta.xml.bind-api-2.3.2.jar;%APP_HOME%\lib\jakarta.activation-api-1.2.1.jar;%APP_HOME%\lib\osgi-resource-locator-1.0.3.jar;%APP_HOME%\lib\grizzly-http-2.4.4.jar;%APP_HOME%\lib\jsr305-3.0.2.jar;%APP_HOME%\lib\checker-compat-qual-2.0.0.jar;%APP_HOME%\lib\error_prone_annotations-2.1.3.jar;%APP_HOME%\lib\j2objc-annotations-1.1.jar;%APP_HOME%\lib\animal-sniffer-annotations-1.14.jar;%APP_HOME%\lib\grizzly-framework-2.4.4.jar

@rem Execute euroaccountant
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %EUROACCOUNTANT_OPTS%  -classpath "%CLASSPATH%" com.euroaccountant.ApplicationMainClass %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable EUROACCOUNTANT_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%EUROACCOUNTANT_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
