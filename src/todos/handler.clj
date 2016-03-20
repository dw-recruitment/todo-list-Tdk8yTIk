(ns todos.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.response :as response]
            [todos.data.db :as db]
            [todos.views.about :as about]
            [todos.views.index :as index]))

;; Hint: One way to break the app is enter a TODO text that is longer than 50 ;-)
(defn- wrap-exception-handler
  "Given a handler, return a function which wraps request handler
   with exception handler by showing animated gif for 500."
  [handler]
  (fn [request]
    (try
      (handler request)
      ;; TODO: Allow this page to accept a string for (.getMessage e)
      (catch Exception e {:status 500 :body (slurp "resources/public/500-gif.html")}))))

(defn- add
  "Given todo as string, insert a newe todo if not empty,
   then redirect to '/'."
  [todo]
  ;; TODO: Change to (when (and (seq todo) (<= (count todo) business/max-todo-text-length))...
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
  (wrap-exception-handler (wrap-defaults app-routes site-defaults)))
