@echo off
setlocal

echo Descargando reactor-netty-1.1.15...

curl -O https://repo1.maven.org/maven2/io/projectreactor/reactor-netty/1.1.15/reactor-netty-1.1.15.jar
curl -O https://repo1.maven.org/maven2/io/projectreactor/reactor-netty/1.1.15/reactor-netty-1.1.15.pom

if exist reactor-netty-1.1.15.jar (
    echo Instalando en el repositorio local...
    mvn install:install-file ^
        -Dfile=reactor-netty-1.1.15.jar ^
        -DpomFile=reactor-netty-1.1.15.pom ^
        -DgroupId=io.projectreactor ^
        -DartifactId=reactor-netty ^
        -Dversion=1.1.15 ^
        -Dpackaging=jar

    echo Instalación completada.
) else (
    echo ERROR: No se pudo descargar el archivo JAR.
)

endlocal
pause
