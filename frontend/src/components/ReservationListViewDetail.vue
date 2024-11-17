<template>

    <v-card outlined>
        <v-card-title>
            ReservationList # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <Number label="ReserveId" v-model="item.reserveId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="ReserveStatus" v-model="item.reserveStatus" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="UserId" v-model="item.userId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="MovieId" v-model="item.movieId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="Count" v-model="item.count" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'ReservationListViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/reservationLists/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>

