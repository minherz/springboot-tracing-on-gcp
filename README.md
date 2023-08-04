## Build Images

Use [Cloud Build] to build service images.
Run the following command while substituting `VALUE` with the names of the services: `apiservice`, `consulting`:

```bash
gcloud builds submit "https://github.com/minherz/springboot-tracing-on-gcp" \
       --git-source-dir="src/VALUE" \
       --substitutions="_SERVICE_NAME=VALUE"
```

The command can be run from any location.

> [!NOTE]
> The command will build the latest version in the `main` branch.
> Use `--git-source-revision` argument to sepcify different Git branch or tag.

A word of warning: the `gcloud` command will fail if you run the build with insufficient credentials or
if the project you use to run the build does not have permissions to push to `us-docker.pkg.dev/minherz/examples` repo. 

Cloud Build configuration was composed following [documentation].

[documentation]: https://cloud.google.com/build/docs/building/build-containerize-java
