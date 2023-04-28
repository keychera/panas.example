# Examples that uses [`panas.reload`][1]

Though this project has become where I experiment all sort of things with clojure(babashka) and htmx


## How to use

```
cd <any-project-folder>
bb panas.reload
```

`babashka-httpkit` is an example project that:
- uses [sakuracss](https://github.com/oxalorg/sakura)
- contains [mermaid.js](https://mermaid.js.org/) example
- contains (currently) 11 [htmx](https://htmx.org/examples/) examples, more will be added

`babaskka-file-server` is an example project that extend [`babashka/http-server`][2] to serve static files with live reload. You can also uses the [bbin][3] alternative to serve static files

[1]: https://github.com/keychera/panas.reload
[2]: https://github.com/babashka/http-server
[3]: https://github.com/keychera/panas.reload#with-bbin