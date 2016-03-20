(ns todos.views.index
  (require [hiccup.core :refer :all]
           [hiccup.form :as form]
           [ring.util.anti-forgery :as anti-forgery]
           [todos.views.page :refer [page]]))

(defn- todo-status
  "Given done? as boolean, return 'Done' if true; 'TODO', otherwise."
  [done?]
  (if done? "Done" "TODO"))

(defn- todo-item
  "Given a todo item as a map with id, text, and done keys,
   return a list of td tags for table to be displayed in a row."
  [{:keys [id text done]}]
  (list [:td.center nil id]
        [:td nil text]
        [:td.center nil (todo-status done)]
        ;; TODO: Ajaxify
        [:td.center nil (form/form-to [:post "/toggle?"]
                                      (anti-forgery/anti-forgery-field)
                                      (form/hidden-field "id" id)
                                      (form/submit-button "Complete"))]))

(defn contents
  "Given todo list data as a vector (or a sequence),
  return a html5 contents which displays a todo list table as a string."
  [data]
  (page
   "TODO List"

   [:div
    ;; TODO List
    [:div#todolist
     [:h1 "TODO List"]
     [:table nil
      [:thead nil
       [:tr nil '([:th nil "ID"] [:th nil "TODO"] [:th nil "Done?"] [:th nil "Toggle Status"])]]
      [:tbody nil
       (for [row data]
         [:tr nil (todo-item row)])]]]

    ;; FORM
    [:div#form
     (form/form-to [:post "/"]
                   ;; anti-forgery token needed for POST
                   (anti-forgery/anti-forgery-field)
                   (form/text-field "todo")
                   (form/submit-button "Add TODO"))]]))
