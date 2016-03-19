(defproject todos "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [cider/cider-nrepl "0.9.1"]
                 [compojure "1.5.0"]
                 [hiccup "1.0.5"]
                 [mysql/mysql-connector-java "5.1.8"]
                 [org.clojure/java.jdbc "0.4.2"]
                 [refactor-nrepl "1.1.0"]
                 [ring/ring-defaults "0.1.5"]]
  :plugins [[cider/cider-nrepl "0.9.1"]
            [refactor-nrepl "1.1.0"]
            [lein-ring "0.9.7"]]
  :ring {:handler todos.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
