<script setup lang="ts">
import PersonCard from "@/components/PersonCard.vue";
import { useStore } from "@/stores";
import { getTeacherById } from "@/services/teacherService";
import CommentFormVue from "@/components/CommentForm.vue";
import ModalVue from "@/components/Modal.vue";
import { storeToRefs } from "pinia";
import { type UserModel } from "@/models/userModel";
import { ref } from "vue";
import { useToast } from "vue-toast-notification";
import { v4 as uuidv4 } from "uuid";
import Navbar from "@/components/Navbar.vue";
import { addReplyService } from "@/services/commentService";
import router from "@/router";

const { studentStore, userStore, teacherStore } = useStore();
const student = storeToRefs(studentStore).getStudent;
const teacher = storeToRefs(teacherStore).teacher;
const comments = student.value?.comments;
const currentUser = storeToRefs(userStore).getCurrentUser;
const showAdvisorModal = ref(false);
const showCommentsModal = ref(false);

const $toast = useToast();

let replyText = ref("");

async function handleReply(commentId: string) {

  if(!replyText.value) return $toast.error("Reply text cannot be empty", { position: "top-right" });

  // currentUser id is same as student id, if not then cannot reply
  if (currentUser.value.id !== student.value?.id) {
    return $toast.error("You cannot reply to this comment", { position: "top-right" });
  }

  let res = await addReplyService({
    id: uuidv4(),
    text: replyText.value,
    createdByUserId: currentUser.value.id,
    createdAt: new Date(),
    commentId: commentId,
  });
  if (res) {
    $toast.success("Reply added successfully", { position: "top-right" });
    return window.location.reload();
  } else {
    $toast.error("Reply failed to add", { position: "top-right" });
  }

  replyText.value = "";
}
</script>

<template>
  <Navbar />
  <div class="w-[60%] flex flex-col">
    <header class="w-full flex h-3/5 gap-4">
      <div class="w-[25%] h-full overflow-hidden border border-black">
        <img :src="student?.img" class="w-full h-full object-cover" />
      </div>
      <div class="w-[75%] h-full flex flex-col">
        <div class="flex gap-2">
          <h1 class="text-lg font-bold">Student ID:</h1>
          <h2 class="text-lg">{{ student?.id }}</h2>
        </div>
        <div class="flex gap-2">
          <h1 class="text-lg font-bold">Name:</h1>
          <h2 class="text-lg">{{ student?.title }} {{ student?.name }} {{ student?.surname }}</h2>
        </div>
        <div class="flex gap-2">
          <h1 class="text-lg font-bold">Department:</h1>
          <h2 class="text-lg">{{ student?.department }}</h2>
        </div>
        <div class="flex gap-2">
          <h1 class="text-lg font-bold">Advisor:</h1>
          <h2 class="text-lg">
            <button class="text-blue-600 underline" v-on:click="showAdvisorModal = true">View</button>
          </h2>
        </div>
        <div class="flex gap-2">
          <h1 class="text-lg font-bold">Comments:</h1>
          <h2 class="text-lg">
            <button class="text-blue-600 underline" v-on:click="showCommentsModal = true">View</button>
          </h2>
        </div>
        <CommentFormVue
          :student="(student as UserModel)"
          v-if="currentUser.role === 'TEACHER' || currentUser.role === 'ADMIN'"
        />
      </div>
    </header>
    <section class="mt-4 flex flex-col gap-2">
      <h1 class="text-2xl font-bold">Courses</h1>
      <table class="min-w-full divide-y divide-gray-200">
        <thead>
          <tr>
            <th
              class="px-6 py-3 bg-gray-50 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider border border-black"
            >
              Course No.
            </th>
            <th
              class="px-6 py-3 bg-gray-50 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider border border-black"
            >
              Course Name
            </th>
            <th
              class="px-6 py-3 bg-gray-50 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider border border-black"
            >
              Instructor
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(course, index) in student?.studyCourses" :key="index">
            <td class="px-6 py-4 whitespace-no-wrap border border-gray-300">
              {{ course?.id }}
            </td>
            <td class="px-6 py-4 whitespace-no-wrap border border-gray-300">
              {{ course?.name }}
            </td>
            <td class="px-6 py-4 whitespace-no-wrap border border-gray-300">
              {{ course.teacher?.title }} {{ course.teacher?.name }} {{ course.teacher?.surname }}
            </td>
          </tr>
        </tbody>
      </table>
    </section>
  </div>

  <ModalVue :show="showAdvisorModal" @close="showAdvisorModal = false">
    <h1 v-if="!teacher">No advisor assigned yet.</h1>
    <div class="flex space-x-4" v-if="teacher">
      <div class="w-[40%]">
        <img :src="teacher?.img" class="w-full" />
      </div>
      <div class="w-[60%]">
        <div class="flex flex-col space-y-1">
          <div class="flex gap-2">
            <h1 class="text-lg font-bold">Name:</h1>
            <h2 class="text-lg">{{ teacher?.title }} {{ teacher?.name }} {{ teacher?.surname }}</h2>
          </div>
          <div class="flex gap-2">
            <h1 class="text-lg font-bold">Department:</h1>
            <h2 class="text-lg">{{ teacher?.department }}</h2>
          </div>
          <div class="flex gap-2">
            <h1 class="text-lg font-bold">Academic Position:</h1>
            <h2 class="text-lg">{{ teacher?.academicPosition }}</h2>
          </div>
        </div>
        <table class="min-w-full divide-y divide-gray-200 mt-4">
          <thead>
            <tr>
              <th
                class="px-6 py-3 bg-gray-50 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider border border-black"
              >
                Course No.
              </th>
              <th
                class="px-6 py-3 bg-gray-50 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider border border-black"
              >
                Course Name
              </th>
              <th
                class="px-6 py-3 bg-gray-50 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider border border-black"
              >
                Instructor
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(course, index) in teacher?.teachCourses" :key="index">
              <td class="px-6 py-4 whitespace-no-wrap border border-gray-300">
                {{ course?.id }}
              </td>
              <td class="px-6 py-4 whitespace-no-wrap border border-gray-300">
                {{ course?.name }}
              </td>
              <td class="px-6 py-4 whitespace-no-wrap border border-gray-300">
                {{ course.teacher?.title }} {{ course.teacher?.name }} {{ course.teacher?.surname }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </ModalVue>

  <ModalVue :show="showCommentsModal" @close="showCommentsModal = false">
    <div class="flex flex-col items-center space-y-2">
      <h1 class="text-lg font-bold">Comments</h1>
      <ul>
        <li v-for="(comment, index) in comments" :key="index" class="mt-4 first-of-type:mt-0">
          <div class="flex flex-col space-y-1 border border-black px-4 py-4">
            <div class="flex gap-2">
              <h2 class="text-lg font-bold">Comment:</h2>
              <h2 class="text-lg">{{ comment.text }}</h2>
              <button
                class="text-blue-600 underline"
                v-on:click="router.push({ name: 'comment=replies', params: { id: comment.id } })"
              >View Replies</button>
            </div>
            <div class="flex gap-2">
              <h2 class="text-lg font-bold">Author:</h2>
              <h2 class="text-lg">
                {{ comment.createdBy.title }} {{ comment.createdBy.name }} {{ comment.createdBy.surname }}
              </h2>
            </div>
            <div class="flex gap-2">
              <h2 class="text-lg font-bold">Date:</h2>
              <h2 class="text-lg">
                {{ new Date(comment.createdAt).toLocaleDateString() }}
                {{ new Date(comment.createdAt).toLocaleTimeString() }}
              </h2>
            </div>
          </div>
          <div v-if="currentUser.role === 'STUDENT' && currentUser.id === student?.id">
            <input type="text" v-model="replyText"
              placeholder="Write reply..."
              class="mb-2 p-2 border rounded"
            />
            <button
              class="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-700"
              @click="() => handleReply(comment.id as string)"
            >
              Reply
            </button>
          </div>
        </li>
      </ul>
      <div v-if="comments?.length === 0" class="text-lg">This student currently has no comments yet.</div>
    </div>
  </ModalVue>
</template>
