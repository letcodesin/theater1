
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import ReservationReservationManager from "./components/listers/ReservationReservationCards"
import ReservationReservationDetail from "./components/listers/ReservationReservationDetail"

import MovieMovieManager from "./components/listers/MovieMovieCards"
import MovieMovieDetail from "./components/listers/MovieMovieDetail"

import NotificationNotificationManager from "./components/listers/NotificationNotificationCards"
import NotificationNotificationDetail from "./components/listers/NotificationNotificationDetail"



export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/reservations/reservations',
                name: 'ReservationReservationManager',
                component: ReservationReservationManager
            },
            {
                path: '/reservations/reservations/:id',
                name: 'ReservationReservationDetail',
                component: ReservationReservationDetail
            },

            {
                path: '/movies/movies',
                name: 'MovieMovieManager',
                component: MovieMovieManager
            },
            {
                path: '/movies/movies/:id',
                name: 'MovieMovieDetail',
                component: MovieMovieDetail
            },

            {
                path: '/notifications/notifications',
                name: 'NotificationNotificationManager',
                component: NotificationNotificationManager
            },
            {
                path: '/notifications/notifications/:id',
                name: 'NotificationNotificationDetail',
                component: NotificationNotificationDetail
            },




    ]
})
