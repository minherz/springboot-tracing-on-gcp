## Build Images

Use [Cloud Build] to build service images.
Run the following command while substituting `VALUE` with the names of the services: `apiservice`, `consulting`:

```bash
gcloud builds submit "https://github.com/minherz/springboot-tracing-on-gcp" \
       --git-source-dir="src/VALUE" \
       --git-source-revision="main" \
       --substitutions="_SERVICE_NAME=VALUE"
```

If you want to build from a different branch/tag of the repo, replace `main` with the name of the branch/tag.

> [!NOTE]
> `gcloud` command will fail if you run the build with insufficient credentials or
> if the project you use to run the build does not have permissions to push to `us-docker.pkg.dev/minherz/examples` repo. 

Cloud Build configuration was composed following [documentation].

[documentation]: https://cloud.google.com/build/docs/building/build-containerize-java
