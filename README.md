# Examples that uses [`panas.reload`][1]

Initially these are examples for panas.reload, now this has become where I experiment all sort of things with clojure(babashka) and htmx

## Prerequisite

install [babashka](https://book.babashka.org/#_installation)

## How to use

```
bb --config <any-project-dir>/bb.edn panas.reload
```

for example, running the project `babashka-httpkit`

```
bb --config babashka-httpkit/bb.edn panas.reload
```
## What each project directory is

`babashka-httpkit` is an example project that:
- uses [sakuracss](https://github.com/oxalorg/sakura)
- contains all 23 examples featured in [htmx website](https://htmx.org/examples/)
- contains [mermaid.js](https://mermaid.js.org/) example

`babaskka-file-server` is an example project that extend [`babashka/http-server`][2] to serve static files with live reload. You can also uses the [bbin][3] alternative to serve static files

[1]: https://github.com/keychera/panas.reload
[2]: https://github.com/babashka/http-server
[3]: https://github.com/keychera/panas.reload#with-bbin