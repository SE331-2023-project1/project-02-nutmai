<script setup lang="ts">
import type { CommentModel } from "@/models/commentModel";
import { ref } from "vue";
import { useStore } from "@/stores";
import {addCommentService} from "@/services/commentService";
import { v4 as uuidv4 } from "uuid";
import type { UserModel } from "@/models/userModel";
import { storeToRefs } from "pinia";
defineProps<{
  student: UserModel;
}>();

const { userStore } = useStore();

async function handleAddComment(comment: any) {
  const res = await addCommentService(comment);
  if(res) {
    window.location.reload();
  }
}

const currentUser = storeToRefs(userStore).getCurrentUser;
console.log(currentUser.value)
const comment = ref("");
</script>

<template>
  <div class="flex flex-col space-y-2">
    <div class="flex flex-col space-y-1">
      <textarea
        class="border border-gray-300 rounded-md p-2 h-28"
        placeholder="Write a comment..."
        v-model="comment"
      ></textarea>
      <button
        class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
        v-on:click="
          () =>
            handleAddComment({
              id: uuidv4(),
              text: comment,
              userId: student.id,
              createdAt: new Date(),
              createdById: currentUser.id,
              replyIds: [],
            })
        "
      >
        Add comment
      </button>
    </div>
  </div>
</template>
