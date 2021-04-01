# Example mpConfig-2.0 app
An example Open Liberty application using the new features in MicroProfile Config 2.0.

This is the application used throughout https://mediacenter.ibm.com/media/t/1_8sp0zqk9

To start the application in [dev mode](https://openliberty.io/docs/21.0.0.2/development-mode.html), run:
```mvn liberty:dev```

To start the application in dev mode, with the [Config Profile](https://download.eclipse.org/microprofile/microprofile-config-2.0/microprofile-config-spec-2.0.html#configprofile) set to "testing", run:
```mvn liberty:dev -Dliberty.var.mp.config.profile="testing"```

With the application running, to view the example endpoints go to:
http://localhost:9080/openapi/ui/
