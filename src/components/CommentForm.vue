<script setup lang="ts">
import type { CommentModel } from "@/models/commentModel";
import type { PropType } from "vue";
import { ref } from "vue";
import { useStore } from "@/stores";
import { getTeachersService } from "@/services/teacherService";
import { v4 as uuidv4 } from "uuid";
import type { StudentModel, TeacherModel } from "@/models/schoolModel";
defineProps<{
  student: StudentModel;
}>();

function handleAddComment(comment: CommentModel) {
  const { commentStore } = useStore();
  commentStore.addComment(comment);
}

const teachers = (await getTeachersService()) as TeacherModel[];

const comment = ref("");
const teacherID = ref(teachers[0].id);
</script>

<template>
  <div class="flex flex-col space-y-2">
    <div class="flex flex-col space-y-1">
      <textarea
        class="border border-gray-300 rounded-md p-2"
        placeholder="Write a comment..."
        v-model="comment"
      ></textarea>
      <select v-model="teacherID" class="border border-gray-300 rounded-md p-2">
        <option v-for="teacher in teachers" :key="teacher.id" :value="teacher.id">
          {{ teacher.name }} {{ teacher.surname }}
        </option>
      </select>
      <button
        class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
        v-on:click="
          () =>
            handleAddComment({
              id: uuidv4(),
              text: comment,
              student: student,
              createdAt: new Date(),
              createdBy: teachers.find((t) => t.id === teacherID) as TeacherModel,
            })
        "
      >
        Add comment
      </button>
    </div>
  </div>
</template>
