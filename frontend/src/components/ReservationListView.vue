<template>

    <v-data-table
        :headers="headers"
        :items="reservationList"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'ReservationListView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
                { text: "reserveId", value: "reserveId" },
                { text: "reserveStatus", value: "reserveStatus" },
                { text: "userId", value: "userId" },
                { text: "movieName", value: "movieName" },
                { text: "count", value: "count" },
            ],
            reservationList : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/reservationLists'))

            temp.data._embedded.reservationLists.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.reservationList = temp.data._embedded.reservationLists;
        },
        methods: {
        }
    }
</script>

