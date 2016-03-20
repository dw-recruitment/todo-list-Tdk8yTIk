(ns todos.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.response :as response]
            [todos.data.db :as db]
            [todos.views.about :as about]
            [todos.views.index :as index]))

(defn- add
  "Given todo as string, insert a newe todo if not empty,
   then redirect to '/'."
  [todo]
  (when (seq todo)
    (db/add-todo todo))
  (response/redirect "/"))

(defn- toggle
  "Given id, toggle doneness status of a TODO item."
  [id]
  (db/toggle-status id)
  (response/redirect "/"))

(defn- delete
  "Given id, delete a TODO item."
  [id]
  (db/delete-todo id)
  (response/redirect "/"))

(defroutes app-routes
  (GET "/" [] (-> (db/get-todolist) index/contents))
  (GET "/about" [] (about/contents))
  (POST "/" [todo] (add todo))
  (POST "/toggle" [id] (toggle id))
  (POST "/delete" [id] (delete id))
  (route/resources "/")
  (route/not-found "Not Found"))

(defonce db-setup (db/initial-db-setup))

;; TODO: Consider handling of POST (see handler_test.clj)
(def app
  (wrap-defaults app-routes site-defaults))
