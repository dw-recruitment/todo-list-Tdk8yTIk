(ns todos.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [todos.business :as business]
            [todos.data.db :refer [initial-db-setup]]))

(defroutes app-routes
  (GET "/" [] (slurp  "resources/public/under-construction.html"))
  (GET "/about" [] (slurp "resources/public/about.html"))
  (route/resources "/")
  (route/not-found "Not Found"))

(defonce db-setup (initial-db-setup business/db-settings))

(def app
  (wrap-defaults app-routes site-defaults))
