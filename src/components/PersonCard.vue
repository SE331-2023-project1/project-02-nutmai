<script setup lang="ts">
import type { PersonModel } from "@/models/personModel";
import { type StudentModel } from "@/models/studentModel";
import router from "@/router";
import { type PropType } from "vue";

const props = defineProps({
  person: Object as PropType<PersonModel>,
  handleView: Function as PropType<() => void>,
});

</script>

<template>
  <div class="w-full h-full flex flex-col items-center" >
    <div class="flex justify-center h-3/4 w-3/5" v-on:click="handleView">
      <img :src="person?.img" alt="" class="w-full h-full object-cover"/>
    </div>
    <div class="flex justify-start flex-col text-xs sm:text-sm w-3/5 mt-2">
      <div class="flex gap-2">
        <span class="font-bold">Name:</span><span>{{ person?.name }} {{ person?.surname }}</span>
      </div>
      <div class="flex gap-2">
        <span class="font-bold">Identifier:</span><span>{{ person?.id }}</span>
      </div>
      <div class="flex gap-2">
        <span class="font-bold">Register to:</span><span>{{ person?.courses.length }} courses</span>
      </div>
    </div>
  </div>
</template>
