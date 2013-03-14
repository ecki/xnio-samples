xnio-samples
============

Sample code for XNIO 3.x

Starting the samples
--------------------
    C:\ws\github\org.xnio.samples>set JB=%USERPROFILE%\.m2\repository\org\jboss
    C:\ws\github\org.xnio.samples>set CP=%JB%\xnio\xnio-api\3.0.7.GA\xnio-api-3.0.7.GA.jar;%JB%\logging\jboss-logging\3.1.2.GA\jboss-logging-3.1.2.GA.jar;%JB%\xnio\xnio-nio\3.0.7.GA\xnio-nio-3.0.7.GA.jar
    C:\ws\github\org.xnio.samples>"%JAVA_HOME%\bin\java" -cp %CP%;target\xnio-samples-0.0.1-SNAPSHOT.jar org.xnio.samples.SimpleEchoServer
    Listening on /0:0:0:0:0:0:0:0:12345
    accepted /0:0:0:0:0:0:0:1:53380
