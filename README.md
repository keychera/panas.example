# Examples that uses [`panas.reload`][1]

Initially these are examples for panas.reload, now this has become where I experiment all sort of things with clojure(babashka) and htmx

## Prerequisite

install [babashka](https://book.babashka.org/#_installation)

quick babashka installation
```bash
bash < <(curl -s https://raw.githubusercontent.com/babashka/babashka/master/install)
```

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

## Why not Hiccup?

[Hiccup](https://github.com/weavejester/hiccup) is one of HTML data representation in Clojure. I think almost everwhere where there is html and Clojure together, hiccup is the one that is used. However, the reason why this project does not use it because:

- when I first started this to learn htmx, I was still also a newbie at Clojure (barely around 3 months of using it). I knew about Hiccup but I still didn't know well about tooling around clojure and hiccup, so it was a hassle to convert htmx examples from html to hiccup (but eventually I found one of the Calva's [feature](https://calva.io/hiccup/) that helps this tremendously).
- I need VSCode's features working with html such as code completion and everything that helps writing JavaScript in html. I also need tailwind plugin niceties in html since another clojure+htmx project of mine uses tailwind so the habit of using html was shared between these two project (thought later I managed to make tailwind plugin works with hiccup as well).

So, currently I know more about dealing with hiccup and probably will use more hiccup in my projects. I want to convert `babashka-httpkit` to using hiccup as well in the future, but leaving the one using html intact. I had received some advice that in some projects that might involve non-clojure people, using html might makes more sense because html is more familiar and more supported in terms of tooling. So, there will be two identical project but one uses html and the other uses hiccup.


[1]: https://github.com/keychera/panas.reload
[2]: https://github.com/babashka/http-server
[3]: https://github.com/keychera/panas.reload#with-bbin
