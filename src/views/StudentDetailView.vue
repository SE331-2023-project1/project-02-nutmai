<script lang="ts">
import PersonCard from "@/components/PersonCard.vue";
import type { StudentModel } from "@/models/studentModel";
import {useStore} from "@/stores";
import { getTeacherById } from "@/services/teacherService";
import type { PersonModel } from "@/models/personModel";

export default {
  
  async setup() {
    
    const {commentStore, studentStore, teacherStore} = useStore();
    const student = studentStore.getStudent();
    const teacher = teacherStore.getTeacher();
    const comments = await Promise.all(
      commentStore.getComments(student?.id).map(async (c) => {
        return {
          ...c,
          teacher: await getTeacherById(c.teacherID),
          date: new Date(c.date).toLocaleDateString(),
        };
      })
    );

    return {
      student,
      teacher,
      comments,
    };
  },

  components: {
    PersonCard,
  },


};
</script>

<template>
  <div class="flex h-full w-[60%]">
    <div class="flex flex-col w-[30%] items-center">
      <div class="w-full h-1/2">
        <PersonCard :person="(student as PersonModel)" ></PersonCard>
      </div>
      <div class="w-3/5 h-1/2">
        <h4 class="text-[15px] font-semibold">Comment:</h4>
        <div class="flex flex-col space-y-2">
          <div v-for="comment in comments" :key="comment.id" class="flex flex-col space-y-1">
            <div class="flex justify-between">
              <span class="text-[12px] font-semibold">{{ comment.teacher.name }} {{ comment.teacher.surname }}</span>
              <span class="text-[12px] font-semibold">{{ comment.date }}</span>
            </div>
            <span class="text-[12px]">{{ comment.content }}</span>
          </div>
        </div>
      </div>
      <div class="flex flex-col w-[70%]">

      </div>
    </div>
  </div>
</template>
