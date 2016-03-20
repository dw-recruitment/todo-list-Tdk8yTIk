(ns todos.views.about
  (require [hiccup.core :refer :all]
           [todos.views.page :refer [page]]))

(defn contents
  "Returns a html5 contents for About page."
  []
  (page
   "About"
   [:div
    [:h1 "About This Project"]

    [:h3 "This project intends to provide TODO List with the following features:"]

    [:ul
     [:li "Each TODO item keeps track of its text and current status."]
     [:li "The current status is initially TODO/not done followed by Done - shown as striked through"]
     [:li "A TODO item can be deleted via a Delete button"]
     [:li "A TODO item's current status can be toggled via clicking on the item/text"]]]))
