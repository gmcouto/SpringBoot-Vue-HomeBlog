<template>
  <v-container
          fluid
          style="min-height: 0;"
          grid-list-lg
        >
    <v-layout row wrap>
      <v-btn v-if="hasRole('WRITER') && !compose"
        absolute
        dark
        fab
        right
        color="pink"
        @click="compose = {}"
      >
        <v-icon>add</v-icon>
      </v-btn>
      <v-flex xs12 v-if="compose">
        <v-card>
      <v-btn
        absolute
        dark
        fab
        bottom
        right
        color="green"
        @click="postCompose"
      >
        <v-icon>done</v-icon>
      </v-btn>
      <v-btn
        absolute
        dark
        fab
        bottom
        left
        color="red"
        @click="compose = false"
      >
        <v-icon>remove</v-icon>
      </v-btn>
          <v-card-title primary-title>
          <v-text-field
            label="Title"
            v-model="compose.title"
            required
          ></v-text-field>
          </v-card-title>
          <v-card-text>
            <v-text-field
              label="Body"
              v-model="compose.body"
              required
            ></v-text-field>
          </v-card-text>
        </v-card>
      </v-flex>
      <v-flex xs12 v-for="post in posts" :key="post.id">
        <v-card>
          <v-card-title primary-title>
            <div>
              {{post.title}}
            </div>
          </v-card-title>
          <v-card-text><div>{{post.body}}</div></v-card-text>
          <v-btn v-if="hasRole('ADMIN')"
            absolute
            dark
            fab
            bottom
            left
            color="red"
            @click="deletePost(post.id)"
          >
            <v-icon>remove</v-icon>
          </v-btn>
        </v-card>
      </v-flex>
    </v-layout>
    {{compose}}
  </v-container>
</template>

<script>
import api from '../axios/api'
import {
  VCard,
  VCardMedia,
  VCardTitle,
  VCardActions,
  VCardText
  } from 'vuetify/es5/components/VCard'
import VTextField from 'vuetify/es5/components/VTextField'

export default {
  components: {
    VCard,
    VCardMedia,
    VCardTitle,
    VCardActions,
    VCardText,
    VTextField
  },
  data () {
    return {
      compose: false,
      posts: [ ]
    }
  },
  computed: {
  },
  methods: {
    deletePost (id) {
      api.delete('/api/posts/' + id)
      .then(() => {
        this.fetchPosts()
      })
      .catch((err) => {
        console.log(err)
      })
    },
    fetchPosts () {
      api.get('/api/posts')
      .then(({ data }) => {
        this.posts = data
      })
      .catch((err) => {
        console.log(err)
      })
    },
    postCompose () {
      api.post('/api/posts', this.compose)
      .then((response) => {
        console.log(response)
        this.fetchPosts()
      })
      .catch((err) => {
        console.log(err)
      })
    },
    hasRole (role) {
      try {
        return this.$store.state.session.token.info.authorities.includes('ROLE_' + role)
      } catch (e) {
        return false
      }
    }
  },
  mounted: function () {
    this.fetchPosts()
  }
}
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
