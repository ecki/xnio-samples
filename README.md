xnio-samples
============

Sample code for XNIO 3.0/3.1

This work is based on the XNIO2 samples from David Lloyd on the XNIO Quick Start page on
https://community.jboss.org/wiki/XNIOQuickStart.


Starting the samples
--------------------
    C:\ws\github\org.xnio.samples>set JB=%USERPROFILE%\.m2\repository\org\jboss
    C:\ws\github\org.xnio.samples>set CP     =%JB%\xnio\xnio-api\3.0.8.GA\xnio-api-3.0.8.GA.jar
    C:\ws\github\org.xnio.samples>set CP=%CP%;%JB%\xnio\xnio-nio\3.0.8.GA\xnio-nio-3.0.8.GA.jar
    C:\ws\github\org.xnio.samples>set CP=%CP%;%JB%\logging\jboss-logging\3.1.2.GA\jboss-logging-3.1.2.GA.jar
    C:\ws\github\org.xnio.samples>set CP=%CP%;target\xnio-samples-0.0.1-SNAPSHOT.jar
    C:\ws\github\org.xnio.samples>"%JAVA_HOME%\bin\java" -cp %CP% org.xnio.samples.SimpleEchoServer
    Listening on /0:0:0:0:0:0:0:0:12345
    accepted /0:0:0:0:0:0:0:1:53380

