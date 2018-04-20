<template>
  <v-content>
    <div v-if="this.$store.getters.username">
      <div>Username: {{ this.$store.state.session.token.info.user_name }}</div>
      <div>Roles: {{ this.$store.state.session.token.info.authorities }}</div>
      <v-btn @click="logout">Logout</v-btn>
    </div>
    <v-form v-else v-model="valid">
      <v-text-field
        label="Username"
        v-model="username"
        :rules="usernameRules"
        required
      ></v-text-field>
      <v-text-field
        label="Password"
        v-model="password"
        :rules="passwordRules"
        required
      ></v-text-field>
      <v-btn @click="authenticate" color="info">Login</v-btn>
      <v-btn>Register</v-btn>
    </v-form>

    <div>{{ this.$store.state.session.token }}</div>
  </v-content>
</template>
<script>
  import VForm from 'vuetify/es5/components/VForm'
  import VTextField from 'vuetify/es5/components/VTextField'

  export default {
    components: {
      VForm,
      VTextField
    },
    data: () => ({
      valid: false,
      username: '',
      usernameRules: [
        v => !!v || 'Username is required',
        v => v.length >= 3 || 'Username must be bigger than 3 characters',
        v => /^[a-zA-Z0-9]+([_.][a-zA-Z0-9]+)*$/.test(v) || 'Username must be composed mainly by letters and digits. Additionaly can contain non-repeating "."(dots) or "_"(underscores) in the middle.'
      ],
      password: '',
      passwordRules: [
        v => !!v || 'Password is required'
        // v => /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail must be valid'
      ]
    }),
    methods: {
      authenticate () {
        this.$store.dispatch('LOGIN_USER', {username: this.username, password: this.password})
      },
      logout () {
        this.$store.dispatch('LOGOUT')
      }
    }
  }
</script>
