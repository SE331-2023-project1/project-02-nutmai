<script lang="ts" setup>
import nProgress from "nprogress";
import { ref } from "vue";
import { getCommentService, getAllRepliesByCommentIdService } from "@/services/commentService";
import { useRoute } from "vue-router";
import type { CommentModel } from "@/models/commentModel";
import type { ReplyModel } from "@/models/replyModel";
import Navbar from "@/components/Navbar.vue";

const route = useRoute();

let commentId = route.params.id;

let comment: CommentModel = ref();
let replies: ReplyModel[] = ref([]);

nProgress.start();
getCommentService(commentId as string).then((res) => {
  comment.value = res;
  getAllRepliesByCommentIdService(commentId as string)
    .then((res) => {
      replies.value = res;
    })
    .finally(() => {
      nProgress.done();
    });
});
</script>

<template>
  <Navbar />
  <div class="flex flex-col w-[40%]">
    <div class="flex justify-start items-center space-x-2">
      <div class="flex items-center space-x-2">
        <img :src="comment.createdBy.img" alt="avatar" class="w-8 h-8 rounded-full" />
        <span class="text-lg font-bold"
          >{{ comment.createdBy.title }} {{ comment.createdBy.name }} {{ comment.createdBy.surname }}</span
        >
      </div>
      <div class="text-gray-500 text-sm">
        <span
          >{{ new Date(comment.createdAt).toLocaleTimeString() }}
          {{ new Date(comment.createdAt).toLocaleDateString() }}</span
        >
      </div>
    </div>
    <div class="bg-gray-100 p-4 rounded-lg mt-2">
      <p>{{ comment.text }}</p>
    </div>
    <div class="ml-4 space-y-2 flex flex-col gap-2">
      <div class="flex items-center space-x-2 w-full" v-for="reply in replies" :key="reply.id">
        <img :src="reply.createdBy.img" alt="avatar" class="w-8 h-8 rounded-full" />
        <div class="bg-gray-100 p-4 rounded-lg w-3/5">
          <p>{{ reply.text }}</p>
        </div>
        <div class="flex flex-col">
          <span class="text-sm font-bold">{{ reply.createdBy.name }} {{ reply.createdBy.surname }}</span>
          <span class="text-xs text-gray-500"
            >{{ new Date(reply.createdAt).toLocaleTimeString() }}
            {{ new Date(reply.createdAt).toLocaleDateString() }}</span
          >
        </div>
      </div>
    </div>
  </div>
</template>
