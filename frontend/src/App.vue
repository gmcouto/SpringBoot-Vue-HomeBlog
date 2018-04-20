<template>
  <v-app light>
    <v-toolbar class="white">
      <v-toolbar-title v-text="title"></v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items class="hidden-sm-and-down">
        <v-btn flat to='/'>Home</v-btn>
        <v-btn flat to='/posts'>Blog</v-btn>
        <v-btn flat to='/login'>{{ this.$store.getters.username || 'Login'}}</v-btn>
      </v-toolbar-items>
    </v-toolbar>
    <v-content>
      <v-container fluid>
        <router-view></router-view>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
import ParallaxHome from '@/components/ParallaxHome'
export default {
  components: {
    ParallaxHome
  },
  data () {
    return {
      title: 'Your Logo',
      clipped: false,
      drawer: true,
      fixed: false,
      items: [{
        icon: 'bubble_chart',
        title: 'Inspire'
      }],
      miniVariant: false,
      right: true,
      rightDrawer: false
    }
  },
  name: 'App'
}
</script>
