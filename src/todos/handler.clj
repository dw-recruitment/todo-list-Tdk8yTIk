(ns todos.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [todos.data.db :refer [initial-db-setup get-todolist]]
            [todos.views.index :as index]))

(defroutes app-routes
  (GET "/" [] (-> (get-todolist) index/contents))
  (GET "/about" [] (slurp "resources/public/about.html"))
  (route/resources "/")
  (route/not-found "Not Found"))

(defonce db-setup (initial-db-setup))

(def app
  (wrap-defaults app-routes site-defaults))
